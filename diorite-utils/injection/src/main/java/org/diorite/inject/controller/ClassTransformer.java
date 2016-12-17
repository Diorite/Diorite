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
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import org.diorite.inject.InjectionController;
import org.diorite.inject.utils.Constants;
import org.diorite.unsafe.AsmUtils;

import net.bytebuddy.description.ByteCodeElement;
import net.bytebuddy.description.type.TypeDescription;

public class ClassTransformer implements Opcodes
{
    //TODO
    private final ClassNode classNode;
    private final ClassData classData;

    private final Map<MethodNode, MethodInsnNode> inits = new LinkedHashMap<>(3);
    private MethodNode clinit;

    private final Map<String, MethodPair> methods = new LinkedHashMap<>(5);
    private final Map<String, FieldPair>  fields  = new LinkedHashMap<>(5);

    @Nullable
    private MemberPair firstStatic = null;
    @Nullable
    private MemberPair lastStatic  = null;
    @Nullable
    private MemberPair firstObject = null;
    @Nullable
    private MemberPair lastObject  = null;

    public ClassTransformer(byte[] bytecode, ClassData classData)
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

    @SuppressWarnings("unchecked")
    public void run()
    {
        for (org.diorite.inject.controller.MemberData<?> memberData : this.classData.getMembers())
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
            if (method.name.equals(InjectionController.STATIC_BLOCK_NAME))
            {
                this.clinit = method;
            }
            if (method.name.equals(InjectionController.CONSTRUCTOR_NAME))
            {
                this.inits.put(method, this.findSuperNode(method));
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

        if (this.clinit == null)
        {
            this.clinit = new MethodNode(8, "<clinit>", "()V", null, null);
            this.classNode.methods.add(this.clinit);
        }
//        this.findAllFieldInitMethods();

        // collect class after/before invokes
        Collection<String> before = this.classData.getBefore();
        Collection<String> after = this.classData.getAfter();
        MethodNode staticBefore = new MethodNode();
        MethodNode objectBefore = new MethodNode();
        MethodNode staticAfter = new MethodNode();
        MethodNode objectAfter = new MethodNode();
        for (String s : before)
        {
            MethodPair methodPair = this.methods.get("()V" + s);
            if (methodPair == null)
            {
                throw new GeneratedCodeError("Can't find method for invoke before: " + s + " in " + this.classNode.name);
            }
            boolean isStatic = Modifier.isStatic(methodPair.node.access);
            InvokerGenerator.printMethod(isStatic ? staticBefore : objectBefore, this.classNode.name, s, isStatic, - 1);
        }
        for (String s : after)
        {
            MethodPair methodPair = this.methods.get("()V" + s);
            if (methodPair == null)
            {
                throw new GeneratedCodeError("Can't find method for invoke after: " + s + " in " + this.classNode.name);
            }
            boolean isStatic = Modifier.isStatic(methodPair.node.access);
            InvokerGenerator.printMethod(isStatic ? staticAfter : objectAfter, this.classNode.name, s, isStatic, - 1);
        }

        // add invokes to constructors
        for (Entry<MethodNode, MethodInsnNode> entry : this.inits.entrySet())
        {
            MethodNode init = entry.getKey();
            MethodInsnNode superNode = entry.getValue();
            if (superNode != null)
            {
                init.instructions.insert(superNode, objectBefore.instructions);
            }
            ListIterator<AbstractInsnNode> iterator = init.instructions.iterator();
            while (iterator.hasNext())
            {
                AbstractInsnNode next_ = iterator.next();
                if (next_.getOpcode() == Opcodes.RETURN)
                {
                    init.instructions.insertBefore(next_, objectAfter.instructions);
                }
            }
        }
        // and static init
        {
            this.clinit.instructions.insert(staticBefore.instructions);
            ListIterator<AbstractInsnNode> iterator = this.clinit.instructions.iterator();
            while (iterator.hasNext())
            {
                AbstractInsnNode next_ = iterator.next();
                if (next_.getOpcode() == Opcodes.RETURN)
                {
                    this.clinit.instructions.insertBefore(next_, staticAfter.instructions);
                }
            }
        }


        // process rest:
//        if (firstStatic != null)
//        {
//            this.processUnprocessed(firstStatic, Collections.singleton(this.clinit));
//        }
//        if (firstObject != null)
//        {
//            this.processUnprocessed(firstObject, this.inits);
//        }

    }

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
                if (! next.name.equals("<init>"))
                {
                    continue;
                }
                if (! objects.remove(next.owner) && next.owner.equals(this.classNode.superName))
                {
                    return next;
                }
            }
        }
        return null;
    }

    private void trackClinit()
    {
        MethodNode clinit = this.clinit;
        MemberPair firstStatic = this.firstStatic;
        if (firstStatic == null)
        {
            return;
        }
        AbstractInsnNode previous = firstStatic.injectStart.getPrevious().getPrevious();
        if (previous.getOpcode() == 34)
        {
            return;
        }
    }

    private void trackInjections()
    {

    }

//    private void processUnprocessed(MemberPair<?, ?> memberPair, Iterable<MethodNode> rootNodes)
//    {
//        if (! memberPair.isProcessed())
//        {
//            if (memberPair instanceof FieldPair)
//            {
//                FieldPair fieldPair = (FieldPair) memberPair;
//                MethodNode tempNode = new MethodNode();
//                AbstractInsnNode[] nodes = InvokerGenerator.generateFieldInjection(this.classData, fieldPair.data, tempNode, true, - 1, - 1);
//                for (MethodNode rootNode : rootNodes)
//                {
//                    if (rootNode.equals("<init>"))
//                    {
//                        MethodInsnNode superNode = findSuperNode(rootNode);
//                        rootNode.instructions.insert(superNode, tempNode.instructions);
//                    }
//                }
//                fieldPair.injectNode = injectNode;
//                fieldPair.injectStart = nodes[0];
//                fieldPair.injectEnd = nodes[1];
//            }
//            else if (memberPair instanceof MethodPair)
//            {
//                MethodPair methodPair = (MethodPair) memberPair; // TODO
//            }
//        }
//
//        do
//        {
//            if (memberPair.isProcessed())
//            {
//                continue;
//            }
//            if (memberPair instanceof FieldPair)
//            {
//                FieldPair fieldPair = (FieldPair) memberPair;
//                MemberPair<?, ?> processedNode = fieldPair.prev;
//                MethodNode tempNode = new MethodNode();
//                AbstractInsnNode[] nodes = InvokerGenerator.generateFieldInjection(this.classData, fieldPair.data, tempNode, true, - 1, - 1);
//
//                MethodNode injectNode;
//                AbstractInsnNode injectionNode;
//
//                if (processedNode != null)
//                {
//                    injectNode = processedNode.injectNode;
//                    injectionNode = processedNode.injectEnd;
//                    injectNode.instructions.insert(injectionNode, tempNode.instructions);
//                }
//                else
//                {
//                    do
//                    {
//                        processedNode = fieldPair.next;
//                    } while (! processedNode.isProcessed());
//
//                    injectNode = processedNode.injectNode;
//                    injectionNode = processedNode.injectStart;
//                    injectNode.instructions.insertBefore(injectionNode, tempNode.instructions);
//                }
//
//                fieldPair.injectNode = injectNode;
//                fieldPair.injectStart = nodes[0];
//                fieldPair.injectEnd = nodes[1];
//            }
//            else if (memberPair instanceof MethodPair)
//            {
//                MethodPair methodPair = (MethodPair) memberPair; // TODO
//            }
//        } while ((memberPair = memberPair.next) != null);
//    }

    @SuppressWarnings("unchecked")
//    private void findAllFieldInitMethods()
//    {
//        this.findAllFieldInitMethods(this.clinit);
//        for (MethodNode init : this.inits)
//        {
//            this.findAllFieldInitMethods(init);
//        }
//    }

    private void findAllMethodInjections()
    {

    }

    @SuppressWarnings("unchecked")
    private void findAllFieldInitMethods(MethodNode rootNode)
    {
        InsnList instructions = rootNode.instructions;
        ListIterator<AbstractInsnNode> iterator = instructions.iterator();
        while (iterator.hasNext())
        {
            AbstractInsnNode next_ = iterator.next();
            // we need to find put field or other local method invoke
            if (next_ instanceof MethodInsnNode)
            {
                MethodInsnNode next = (MethodInsnNode) next_;
                if (next.getOpcode() != INVOKESTATIC)// skip non static invokes
                {
                    continue;
                }
                if (! next.owner.equals(this.classNode.name)) // skip unknown methods
                {
                    continue;
                }
                String id = next.desc + next.name;
                MethodPair methodPair = this.methods.get(id);
                if (methodPair == null)
                {
                    continue;
                }
                this.findAllFieldInitMethods(methodPair.node);
            }
            else if (next_ instanceof FieldInsnNode)
            {
                // find init method
                FieldInsnNode next = (FieldInsnNode) next_;
                if (! next.owner.equals(this.classNode.name))
                {
                    continue;
                }
                if ((next.getOpcode() != PUTFIELD) && (next.getOpcode() != PUTSTATIC))
                {
                    continue;
                }
                String id = next.desc + next.name;
                FieldPair fieldPair = this.fields.get(id);
                if ((fieldPair == null) || ! fieldPair.isInjected())
                {
                    continue;
                }
                MethodInsnNode initInvoke = this.findInitInvoke(next, fieldPair);
                if (initInvoke == null)
                {
                    // direct inject

                    LinkedList<AbstractInsnNode> injected = this.findPlaceholderInvokes(rootNode, fieldPair);
                    if (injected.size() != 1)
                    {
                        throw new GeneratedCodeError("Found " + injected.size() + " direct injections, 1 expected.");
                    }
                    fieldPair.injectNode = rootNode;
                    // add invoke before, all invokes should be added before inject placeholder method
                    // add invoke after, all invokes should be added after put field
                    this.printMethodsForInjectedField(fieldPair, rootNode, next, injected.getFirst());
                    continue;
                }
                String methodId = initInvoke.desc + initInvoke.name;
                MethodPair initMethodPair = this.methods.get(methodId);
                if (initMethodPair == null)
                {
                    throw new GeneratedCodeError("Invalid method pair for " + methodId);
                }
                MethodNode initMethodNode = initMethodPair.node;
                // now we need find placeholder invoke and replace it with own method
                {
                    // add invoke before, all invokes should be added before init method.
                    // add invoke after, all invokes should be added after init method.
                    this.printMethodsForInjectedField(fieldPair, rootNode, next, initInvoke);
                }
                this.findPlaceholderInvokes(initMethodNode, fieldPair);
            }
        }
    }

    private void printMethodsForInjectedField(FieldPair fieldPair, MethodNode rootNode, AbstractInsnNode next, AbstractInsnNode initInvoke)
    {
        fieldPair.injectNode = rootNode;
        MethodNode temp = new MethodNode();
        printMethods(temp, this.classNode.name, fieldPair.data.getBefore(), this, - 1);
        if (temp.instructions.size() == 0)
        {
            fieldPair.injectStart = initInvoke;
        }
        else
        {
            fieldPair.injectStart = temp.instructions.getFirst();
            rootNode.instructions.insertBefore(initInvoke, temp.instructions);
        }

        temp = new MethodNode();
        printMethods(temp, this.classNode.name, fieldPair.data.getAfter(), this, - 1);
        if (temp.instructions.size() == 0)
        {
            fieldPair.injectEnd = next;
        }
        else
        {
            fieldPair.injectEnd = temp.instructions.getLast();
            rootNode.instructions.insert(next, temp.instructions);
        }
    }

    @SuppressWarnings("unchecked")
    private LinkedList<AbstractInsnNode> findPlaceholderInvokes(MethodNode node, FieldPair fieldPair)
    {
        InsnList instructions = node.instructions;
        ListIterator<AbstractInsnNode> iterator = instructions.iterator();
        LinkedList<AbstractInsnNode> result = new LinkedList<>();
        while (iterator.hasNext())
        {
            AbstractInsnNode next_ = iterator.next();
            if (! (next_ instanceof MethodInsnNode))
            {
                continue;
            }
            MethodInsnNode next = (MethodInsnNode) next_;
            if (! isInjectPlaceholder(next.getOpcode(), next.owner, next.name, next.desc))
            {
                continue;
            }
            MethodNode tempNode = new MethodNode();
            // TODO find better way to track this
            // we need to check if this belongs to given field
            {
                TypeDescription typeDefinitions = fieldPair.data.getMember().getType().asErasure();
                int storeCode = AsmUtils.getStoreCode(typeDefinitions);
                int loadCode = AsmUtils.getLoadCode(typeDefinitions);
                AbstractInsnNode check_ = next.getNext();
                if (check_ instanceof TypeInsnNode)
                {
                    check_ = check_.getNext();
                }
                if (check_ instanceof VarInsnNode)
                {
                    int var = ((VarInsnNode) check_).var;
                    AbstractInsnNode store = this.findStoreNode(check_, storeCode, var);
                    if (store != null)
                    {
                        check_ = store.getNext();
                        if (check_ instanceof TypeInsnNode)
                        {
                            check_ = check_.getNext();
                        }
                    }
                }
                if (check_ instanceof FieldInsnNode)
                {
                    FieldInsnNode check = (FieldInsnNode) check_;
                    if (! check.owner.equals(this.classNode.name) || ! check.name.equals(fieldPair.node.name) || ! check.desc.equals(fieldPair.node.desc))
                    {
                        continue;
                    }
                }
                else
                {
                    continue;
                }
            }
            InvokerGenerator.generateFieldInjection(this.classData, fieldPair.data, tempNode, false, - 1, Integer.MIN_VALUE);
            AbstractInsnNode changedNode = tempNode.instructions.getFirst();
            instructions.insert(next, tempNode.instructions);
            instructions.remove(next);
            result.add(changedNode);
        }
        return result;
    }

    private VarInsnNode findLoadNode(AbstractInsnNode check_, int loadCode, int var)
    {
        AbstractInsnNode next_ = check_.getNext();
        do
        {
            next_ = next_.getNext();
            if (next_ instanceof VarInsnNode)
            {
                VarInsnNode next = (VarInsnNode) next_;
                if ((next.getOpcode() != loadCode) || (next.var != var))
                {
                    continue;
                }
                return next;
            }
        } while (next_ != null);
        return null;
    }

    private VarInsnNode findStoreNode(AbstractInsnNode check_, int storeCode, int var)
    {
        AbstractInsnNode prev_ = check_.getPrevious();
        do
        {
            prev_ = prev_.getPrevious();
            if (prev_ instanceof VarInsnNode)
            {
                VarInsnNode prev = (VarInsnNode) prev_;
                if ((prev.getOpcode() != storeCode) || (prev.var != var))
                {
                    continue;
                }
                return prev;
            }
        } while (prev_ != null);
        return null;
    }

    private MethodInsnNode findInitInvoke(FieldInsnNode node, FieldPair fieldPair)
    {

        TypeDescription fieldType = fieldPair.data.getMember().getType().asErasure();

        // there are 2 cases: prev instruction is directly what we are looking for, or ir is LOAD code.
        AbstractInsnNode previous_ = node.getPrevious();
        AbstractInsnNode scan_ = null;
        if (previous_ instanceof VarInsnNode) // load code
        {
            int loadCode = AsmUtils.getLoadCode(fieldType);
            int storeCode = AsmUtils.getStoreCode(fieldType);
            if (previous_.getOpcode() != loadCode)
            {
                throw new GeneratedCodeError("Expected load opcode for " + fieldType.getInternalName() + "(" + loadCode + "), found " + previous_.getOpcode() +
                                             ", Field: " + fieldPair.getFullName());
            }
            VarInsnNode varInsnNode = (VarInsnNode) previous_;
            int var = varInsnNode.var;
            // find store
            AbstractInsnNode store = this.findStoreNode(varInsnNode, storeCode, var);
            if (store == null)
            {
                throw new GeneratedCodeError("Can't find store opcode for field: " + this.classNode.name + " " + fieldPair.getFullName());
            }
            scan_ = store.getPrevious();
        }
        else
        {
            scan_ = previous_;
        }
        if (! (scan_ instanceof MethodInsnNode))
        {
            return null;
        }
        MethodInsnNode methodIns = (MethodInsnNode) scan_;
        if (! methodIns.owner.equals(this.classNode.name))
        {
            throw new GeneratedCodeError("Init method must be in this same class (" + this.classNode.name + ") as field! " + fieldPair.getFullName());
        }
        return methodIns;
    }

    public static boolean isInjectPlaceholder(int opcode, String owner, String name, String desc)
    {
        return (opcode == INVOKESTATIC) && owner.equals(Constants.DI_LIBRARY.getInternalName()) && name.equals("inject") && desc.equals("()Ljava/lang/Object;");
    }

    @SuppressWarnings("rawtypes")
    abstract static class MemberPair<DATA, NODE>
    {
        DATA             data;
        NODE             node;
        int              index;
        AbstractInsnNode injectStart;
        AbstractInsnNode injectEnd;
        MethodNode       injectNode;
        MemberPair       next;
        MemberPair       prev;
        boolean          isStatic;

        boolean isInjected()
        {
            return this.data != null;
        }

        boolean isProcessed()
        {
            return this.injectEnd != null;
        }

        abstract String getFullName();

        MemberPair(DATA data)
        {
            this.data = data;
        }
    }

    static class MethodPair extends MemberPair<MethodData, MethodNode>
    {
        @Override
        String getFullName()
        {
            return this.node.name + " " + this.node.desc;
        }

        MethodPair(MethodData data)
        {
            super(data);
        }
    }

    static class FieldPair extends MemberPair<FieldData<?>, FieldNode>
    {
        @Override
        String getFullName()
        {
            return this.node.name + " " + this.node.desc;
        }

        FieldPair(FieldData<?> fieldData)
        {
            super(fieldData);
        }
    }

    private static int printMethods(MethodNode mv, String clazz, Iterable<String> methods, ClassTransformer transformer, int lineNumber)
    {
        for (String method : methods)
        {
            MethodPair methodPair = transformer.methods.get("()V" + method);
            if (methodPair == null)
            {
                continue;
            }
            lineNumber = InvokerGenerator.printMethod(mv, clazz, method, Modifier.isStatic(methodPair.node.access), lineNumber);
        }
        return lineNumber;
    }

}
