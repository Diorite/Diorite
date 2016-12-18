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
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

import org.diorite.inject.InjectionController;
import org.diorite.inject.controller.InjectTracker.PlaceholderType;
import org.diorite.inject.data.WithMethods;
import org.diorite.inject.utils.Constants;
import org.diorite.unsafe.AsmUtils;

import net.bytebuddy.description.ByteCodeElement;

public class InjectTransformer implements Opcodes
{
    private final ClassNode classNode;
    private final ClassData classData;

    private final Map<MethodNode, InitPair> inits = new LinkedHashMap<>(3);
//    private MethodNode clinit;

    private final Map<String, MethodPair> methods = new LinkedHashMap<>(5);
    private final Map<String, FieldPair>  fields  = new LinkedHashMap<>(5);

    @SuppressWarnings("rawtypes") @Nullable
    private MemberPair firstStatic = null;
    @SuppressWarnings("rawtypes") @Nullable
    private MemberPair lastStatic  = null;
    @SuppressWarnings("rawtypes") @Nullable
    private MemberPair firstObject = null;
    @SuppressWarnings("rawtypes") @Nullable
    private MemberPair lastObject  = null;

    public InjectTransformer(byte[] bytecode, ClassData classData)
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
        for (MemberData<?> memberData : this.classData.getMembers())
        {
            ByteCodeElement member = memberData.getMember();
            String id = member.getDescriptor() + member.getName();
            MemberPair memberPair;
            if (memberData instanceof FieldData)
            {
                FieldData<?> fieldData = (FieldData<?>) memberData;
                FieldPair fieldPair = new FieldPair(fieldData);
                memberPair = fieldPair;
                this.fields.put(id, fieldPair);
            }
            else
            {
                MethodData methodData = (MethodData) memberData;
                MethodPair methodPair = new MethodPair(methodData);
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
                InitPair initPair = new InitPair(method, superNode);
                this.findReturns(initPair);
                this.inits.put(method, initPair);
            }
            String id = method.desc + method.name;
            MethodPair methodPair = this.methods.computeIfAbsent(id, k -> new MethodPair(null));
            methodPair.node = method;
            methodPair.index = i;
        }
        List<FieldNode> fields = this.classNode.fields;
        for (int i = 0, fieldsSize = fields.size(); i < fieldsSize; i++)
        {
            FieldNode field = fields.get(i);
            String id = field.desc + field.name;
            FieldPair fieldPair = this.fields.computeIfAbsent(id, k -> new FieldPair(null));
            fieldPair.node = field;
            fieldPair.index = i;
        }
    }

    private void findReturns(InitPair initPair)
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

    private void findAllFieldInitMethods()
    {
//        assert this.clinit != null;
//        this.findAllFieldInitMethods(this.clinit);
        for (MethodNode init : this.inits.keySet())
        {
            this.findAllFieldInitMethods(init);
        }
    }

    private void trackField(FieldInsnNode fieldInsnNode, InsnList insnList)
    {
        // check if field is in this same class
        if (! fieldInsnNode.owner.equals(this.classNode.name))
        {
            return;
        }
        // first check if field is injected
        FieldPair fieldPair = this.getFieldPair(fieldInsnNode);
        if ((fieldPair == null) || (fieldPair.node == null) || (fieldPair.data == null))
        {
            return;
        }

        InjectTracker injectTracker = InjectTracker.trackFromField(this, fieldInsnNode, insnList);
        this.injectField(fieldPair, injectTracker);
    }

    private void injectField(FieldPair fieldPair, InjectTracker result)
    {
        FieldData<?> fieldData = fieldPair.data;
        assert fieldData != null;

        MethodInsnNode injectionInvokeNode = result.getResult();
        FieldInsnNode putfieldNode = result.getFieldInsnNode();

        // insert invoke methods:
        MethodNode codeBefore = new MethodNode();
        MethodNode codeAfter = new MethodNode();
        this.fillMethodInvokes(codeBefore, codeAfter, fieldData);

        // node list for PUTFIELD
        InsnList initNodeList = result.getInitNodeList();
        // node list for invoke execution
        InsnList resultNodeList = result.getResultNodeList();

        if (codeBefore.instructions.size() != 0)
        {
            // invoke before should be added before PUTFIELD and before INVOKE
            resultNodeList.insertBefore(injectionInvokeNode, codeBefore.instructions);
        }
        if (codeAfter.instructions.size() != 0)
        {
            // invoke after should be added after PUTFIELD
            initNodeList.insert(putfieldNode, codeAfter.instructions);
        }

        // and replace placeholder node with real injections:
        {
            MethodNode tempNode = new MethodNode();
            InvokerGenerator.generateFieldInjection(this.classData, fieldData, tempNode, - 1);
            resultNodeList.insertBefore(injectionInvokeNode, tempNode.instructions);
            resultNodeList.remove(injectionInvokeNode);
        }
    }

    private void findAllFieldInitMethods(MethodNode rootNode)
    {
        InsnList instructions = rootNode.instructions;
        if (instructions.size() == 0)
        {
            return;
        }
        AbstractInsnNode node = instructions.getFirst();
        while (node != null)
        {
            while (! (node instanceof FieldInsnNode) || ! AsmUtils.isPutField(node.getOpcode()))
            {
                node = node.getNext();
                if (node == null)
                {
                    return;
                }
            }
            this.trackField((FieldInsnNode) node, rootNode.instructions);
            node = node.getNext();
        }
    }

    public <T> void run()
    {
        this.createMappings();
        this.findAllFieldInitMethods();


        this.addGlobalInjectInvokes();
    }

    private void addGlobalInjectInvokes()
    {
        MethodNode codeBefore = new MethodNode();
        MethodNode codeAfter = new MethodNode();
        this.fillMethodInvokes(codeBefore, codeAfter, this.classData);
        for (Entry<MethodNode, InitPair> initEntry : this.inits.entrySet())
        {
            MethodNode init = initEntry.getKey();
            InitPair initPair = initEntry.getValue();
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

    private void fillMethodInvokes(MethodNode codeBefore, MethodNode codeAfter, WithMethods member)
    {
        Collection<String> before = member.getBefore();
        Collection<String> after = member.getAfter();
        for (String s : before)
        {
            MethodPair methodPair = this.methods.get("()V" + s);
            if ((methodPair == null) || (methodPair.node == null))
            {
                throw new TransformerError("Can't find method for invoke before: " + s + " in " + this.classNode.name);
            }
            boolean isStatic = Modifier.isStatic(methodPair.node.access);
            InvokerGenerator.printMethod(codeBefore, this.classNode.name, s, isStatic, - 1);
        }
        for (String s : after)
        {
            MethodPair methodPair = this.methods.get("()V" + s);
            if ((methodPair == null) || (methodPair.node == null))
            {
                throw new TransformerError("Can't find method for invoke after: " + s + " in " + this.classNode.name);
            }
            boolean isStatic = Modifier.isStatic(methodPair.node.access);
            InvokerGenerator.printMethod(codeAfter, this.classNode.name, s, isStatic, - 1);
        }
    }

    @Nullable
    MethodPair getMethodPair(MethodInsnNode methodInsnNode)
    {
        return this.methods.get(methodInsnNode.desc + methodInsnNode.name);
    }

    @Nullable
    FieldPair getFieldPair(FieldInsnNode fieldInsnNode)
    {
        return this.fields.get(fieldInsnNode.desc + fieldInsnNode.name);
    }

    @Nullable
    MethodNode getMethod(MethodInsnNode methodInsnNode)
    {
        MethodPair methodPair = this.getMethodPair(methodInsnNode);
        if (methodPair == null)
        {
            return null;
        }
        return methodPair.node;
    }

    @Nullable
    FieldNode getField(FieldInsnNode fieldInsnNode)
    {
        FieldPair fieldPair = this.getFieldPair(fieldInsnNode);
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

    static class InitPair
    {
        MethodNode     node;
        MethodInsnNode superInvoke;
        Collection<InsnNode> returns = new LinkedList<>();

        InitPair(MethodNode node, MethodInsnNode superInvoke)
        {
            this.node = node;
            this.superInvoke = superInvoke;
        }
    }

    @SuppressWarnings("rawtypes")
    abstract static class MemberPair<DATA, NODE>
    {
        @Nullable DATA data;
        @Nullable NODE node;
        int index;
        @Nullable MemberPair next;
        @Nullable MemberPair prev;
        boolean isStatic;

        boolean isInjected()
        {
            return this.data != null;
        }

        abstract String getFullName();

        MemberPair(@Nullable DATA data)
        {
            this.data = data;
        }
    }

    static class MethodPair extends MemberPair<MethodData, MethodNode>
    {
        @Override
        String getFullName()
        {
            if (this.node == null)
            {
                throw new TransformerError("Node not set yet.");
            }
            return this.node.name + " " + this.node.desc;
        }

        MethodPair(@Nullable MethodData data)
        {
            super(data);
        }
    }

    static class FieldPair extends MemberPair<FieldData<?>, FieldNode>
    {
        @Override
        String getFullName()
        {
            if (this.node == null)
            {
                throw new TransformerError("Node not set yet.");
            }
            return this.node.name + " " + this.node.desc;
        }

        FieldPair(@Nullable FieldData<?> fieldData)
        {
            super(fieldData);
        }
    }

    private static int printMethods(MethodNode mv, String clazz, Iterable<String> methods, InjectTransformer transformer, int lineNumber)
    {
        for (String method : methods)
        {
            MethodPair methodPair = transformer.methods.get("()V" + method);
            if (methodPair == null)
            {
                throw new TransformerError("Unknown method: " + method + " for " + clazz);
            }
            if (methodPair.node == null)
            {
                throw new TransformerError("Node not set yet.");
            }
            lineNumber = InvokerGenerator.printMethod(mv, clazz, method, Modifier.isStatic(methodPair.node.access), lineNumber);
        }
        return lineNumber;
    }

}
