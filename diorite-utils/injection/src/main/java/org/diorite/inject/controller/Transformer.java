/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.inject.controller;

import javax.annotation.Nullable;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

import org.diorite.inject.InjectionController;
import org.diorite.inject.controller.TransformerInjectTracker.PlaceholderType;
import org.diorite.inject.data.WithMethods;
import org.diorite.inject.utils.Constants;
import org.diorite.unsafe.AsmUtils;

import net.bytebuddy.description.ByteCodeElement;

class Transformer implements Opcodes
{
    final ClassNode           classNode;
    final ControllerClassData classData;

    final Map<MethodNode, TransformerInitMethodData> inits = new LinkedHashMap<>(3);
//    private MethodNode clinit;

    final Map<String, TransformerMethodPair> methods = new LinkedHashMap<>(5);
    final Map<String, TransformerFieldPair>  fields  = new LinkedHashMap<>(5);

    @SuppressWarnings("rawtypes") @Nullable
    TransformerMemberPair firstStatic = null;
    @SuppressWarnings("rawtypes") @Nullable
    TransformerMemberPair lastStatic  = null;
    @SuppressWarnings("rawtypes") @Nullable
    TransformerMemberPair firstObject = null;
    @SuppressWarnings("rawtypes") @Nullable
    TransformerMemberPair lastObject  = null;

    Transformer(byte[] bytecode, ControllerClassData classData)
    {
        this.classData = classData;
        this.classNode = new ClassNode(Opcodes.ASM6);
        ClassReader cr = new ClassReader(bytecode);
        cr.accept(this.classNode, 0);
    }

    public ClassWriter getWriter()
    {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        this.classNode.accept(cw);
        return cw;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void createMappings()
    {
        // add all methods to object maps.
        for (ControllerMemberData<?> memberData : this.classData.getMembers())
        {
            ByteCodeElement member = memberData.getMember();
            String id = member.getDescriptor() + member.getName();
            TransformerMemberPair memberPair;
            if (memberData instanceof ControllerFieldData)
            {
                ControllerFieldData<?> fieldData = (ControllerFieldData<?>) memberData;
                TransformerFieldPair fieldPair = new TransformerFieldPair(fieldData);
                memberPair = fieldPair;
                this.fields.put(id, fieldPair);
            }
            else
            {
                ControllerMethodData methodData = (ControllerMethodData) memberData;
                TransformerMethodPair methodPair = new TransformerMethodPair(methodData);
                memberPair = methodPair;
                this.methods.put(id, methodPair);
            }
            memberPair.isStatic = Modifier.isStatic(member.getModifiers());
            if (memberPair.isStatic)
            {
                if (this.lastStatic != null)
                {
                    this.lastStatic.next = memberPair;
                    memberPair.prev = this.lastStatic;
                    this.lastStatic = memberPair;
                }
                else
                {
                    this.lastStatic = memberPair;
                }
                if (this.firstStatic == null)
                {
                    this.firstStatic = this.lastStatic;
                }
            }
            else
            {

                if (this.lastObject != null)
                {
                    this.lastObject.next = memberPair;
                    memberPair.prev = this.lastObject;
                    this.lastObject = memberPair;
                }
                else
                {
                    this.lastObject = memberPair;
                }
                if (this.firstObject == null)
                {
                    this.firstObject = this.lastObject;
                }
            }
        }
        List<MethodNode> methods = this.classNode.methods;
        for (int i = 0, methodsSize = methods.size(); i < methodsSize; i++)
        {
            MethodNode method = methods.get(i);
//            if (method.name.equals(InjectionController.STATIC_BLOCK_NAME))
//            {
//                this.clinit = method;
//            }
            if (method.name.equals(InjectionController.CONSTRUCTOR_NAME))
            {
                MethodInsnNode superNode = this.findSuperNode(method);
                TransformerInitMethodData initPair = new TransformerInitMethodData(method, superNode);
                this.findReturns(initPair);
                this.inits.put(method, initPair);
            }
            String id = method.desc + method.name;
            TransformerMethodPair methodPair = this.methods.computeIfAbsent(id, k -> new TransformerMethodPair(null));
            methodPair.node = method;
            methodPair.index = i;
        }
        List<FieldNode> fields = this.classNode.fields;
        for (int i = 0, fieldsSize = fields.size(); i < fieldsSize; i++)
        {
            FieldNode field = fields.get(i);
            String id = field.desc + field.name;
            TransformerFieldPair fieldPair = this.fields.computeIfAbsent(id, k -> new TransformerFieldPair(null));
            fieldPair.node = field;
            fieldPair.index = i;
        }
    }

    private void findReturns(TransformerInitMethodData initPair)
    {
        MethodNode init = initPair.node;
        AbstractInsnNode node = init.instructions.getFirst();
        while (node != null)
        {
            if ((node instanceof InsnNode) && AsmUtils.isReturnCode(node.getOpcode()))
            {
                initPair.returns.add((InsnNode) node);
            }
            node = node.getNext();
            if (node == null)
            {
                break;
            }
        }
    }

    @SuppressWarnings("unchecked")
    private MethodInsnNode findSuperNode(MethodNode init)
    {
        // we need to find super(...) invoke, it should be first super.<init> invoke, but it might be invoke to some new created object of super
        // type, so we need to track created objects
        Collection<String> objects = new LinkedList<>();
        ListIterator<AbstractInsnNode> iterator = init.instructions.iterator();
        while (iterator.hasNext())
        {
            AbstractInsnNode next_ = iterator.next();
            if (next_.getOpcode() == Opcodes.NEW)
            {
                TypeInsnNode next = (TypeInsnNode) next_;
                objects.add(next.desc);
            }
            else if (next_.getOpcode() == Opcodes.INVOKESPECIAL)
            {
                MethodInsnNode next = (MethodInsnNode) next_;
                if (! next.name.equals(InjectionController.CONSTRUCTOR_NAME))
                {
                    continue;
                }
                if (! objects.remove(next.owner) && next.owner.equals(this.classNode.superName))
                {
                    return next;
                }
            }
        }
        throw new TransformerError("Can't find super() invoke for constructor!");
    }

    public <T> void run()
    {
        this.createMappings();
        TransformerFieldInjector.run(this);
        TransformerMethodInjector.run(this);


        this.addGlobalInjectInvokes();
    }

    private void addGlobalInjectInvokes()
    {
        MethodNode codeBefore = new MethodNode();
        MethodNode codeAfter = new MethodNode();
        this.fillMethodInvokes(codeBefore, codeAfter, this.classData);
        for (Entry<MethodNode, TransformerInitMethodData> initEntry : this.inits.entrySet())
        {
            MethodNode init = initEntry.getKey();
            TransformerInitMethodData initPair = initEntry.getValue();
            MethodInsnNode superInvoke = initPair.superInvoke;

            if (codeAfter.instructions.size() > 0)
            {
                for (InsnNode node : initPair.returns)
                {
                    init.instructions.insertBefore(node, codeAfter.instructions);
                }
            }
            if (codeBefore.instructions.size() > 0)
            {
                init.instructions.insert(superInvoke, codeBefore.instructions);
            }
        }
    }

    void fillMethodInvokes(MethodNode codeBefore, MethodNode codeAfter, WithMethods member)
    {
        Collection<String> before = member.getBefore();
        Collection<String> after = member.getAfter();
        for (String s : before)
        {
            TransformerMethodPair methodPair = this.methods.get("()V" + s);
            if ((methodPair == null) || (methodPair.node == null))
            {
                throw new TransformerError("Can't find method for invoke before: " + s + " in " + this.classNode.name);
            }
            boolean isStatic = Modifier.isStatic(methodPair.node.access);
            TransformerInvokerGenerator.printMethod(codeBefore, this.classNode.name, s, isStatic, - 1);
        }
        for (String s : after)
        {
            TransformerMethodPair methodPair = this.methods.get("()V" + s);
            if ((methodPair == null) || (methodPair.node == null))
            {
                throw new TransformerError("Can't find method for invoke after: " + s + " in " + this.classNode.name);
            }
            boolean isStatic = Modifier.isStatic(methodPair.node.access);
            TransformerInvokerGenerator.printMethod(codeAfter, this.classNode.name, s, isStatic, - 1);
        }
    }

    @Nullable
    TransformerMethodPair getMethodPair(MethodInsnNode methodInsnNode)
    {
        return this.methods.get(methodInsnNode.desc + methodInsnNode.name);
    }

    @Nullable
    TransformerFieldPair getFieldPair(FieldInsnNode fieldInsnNode)
    {
        return this.fields.get(fieldInsnNode.desc + fieldInsnNode.name);
    }

    @Nullable
    MethodNode getMethod(MethodInsnNode methodInsnNode)
    {
        TransformerMethodPair methodPair = this.getMethodPair(methodInsnNode);
        if (methodPair == null)
        {
            return null;
        }
        return methodPair.node;
    }

    @Nullable
    FieldNode getField(FieldInsnNode fieldInsnNode)
    {
        TransformerFieldPair fieldPair = this.getFieldPair(fieldInsnNode);
        if (fieldPair == null)
        {
            return null;
        }
        return fieldPair.node;
    }

    public static PlaceholderType isInjectPlaceholder(AbstractInsnNode node)
    {
        if (! (node instanceof MethodInsnNode))
        {
            return PlaceholderType.INVALID;
        }
        MethodInsnNode mNode = (MethodInsnNode) node;
        return isInjectPlaceholder(mNode.getOpcode(), mNode.owner, mNode.name, mNode.desc);
    }

    public static PlaceholderType isInjectPlaceholder(int opcode, String owner, String name, String desc)
    {
        if ((opcode == INVOKESTATIC) && owner.equals(Constants.INJECTION_LIBRARY.getInternalName()) && (desc.equals("()Ljava/lang/Object;")))
        {
            switch (name)
            {
                case "injectNullable":
                    return PlaceholderType.NULLABLE;
                case "inject":
                    return PlaceholderType.NONNULL;
                default:
                    return PlaceholderType.INVALID;
            }
        }
        return PlaceholderType.INVALID;
    }

    private static int printMethods(MethodNode mv, String clazz, Iterable<String> methods, Transformer transformer, int lineNumber)
    {
        for (String method : methods)
        {
            TransformerMethodPair methodPair = transformer.methods.get("()V" + method);
            if (methodPair == null)
            {
                throw new TransformerError("Unknown method: " + method + " for " + clazz);
            }
            if (methodPair.node == null)
            {
                throw new TransformerError("Node not set yet.");
            }
            lineNumber = TransformerInvokerGenerator.printMethod(mv, clazz, method, Modifier.isStatic(methodPair.node.access), lineNumber);
        }
        return lineNumber;
    }

}
