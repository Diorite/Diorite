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

package org.diorite.inject.impl.controller;

import javax.annotation.Nullable;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import org.diorite.unsafe.AsmUtils;

final class TransformerInjectTracker implements Opcodes
{
    private final Transformer   transformer;
    private final FieldInsnNode fieldInsnNode;
    private final boolean       isStatic;
    private       InsnList      resultNodeList;
    private final InsnList      initNodeList;

    private InjectionType   injectionType   = InjectionType.UNKNOWN;
    private PlaceholderType placeholderType = PlaceholderType.UNKNOWN;

    @Nullable private LoadCode       loadCode        = null;
    @Nullable private MethodInsnNode placeholderNode = null;

    private TransformerInjectTracker(Transformer transformer, FieldInsnNode fieldInsnNode, InsnList insnList)
    {
        this.transformer = transformer;
        this.fieldInsnNode = fieldInsnNode;
        this.isStatic = fieldInsnNode.getOpcode() == PUTSTATIC;
        this.resultNodeList = insnList;
        this.initNodeList = insnList;
    }

    public static TransformerInjectTracker trackFromField(Transformer transformer, FieldInsnNode fieldInsnNode, InsnList insnList)
    {
        TransformerInjectTracker injectTracker = new TransformerInjectTracker(transformer, fieldInsnNode, insnList);
        injectTracker.run();
        return injectTracker;
    }

    public InjectionType getInjectionType()
    {
        return this.injectionType;
    }

    public PlaceholderType getPlaceholderType()
    {
        return this.placeholderType;
    }

    public FieldInsnNode getFieldInsnNode()
    {
        return this.fieldInsnNode;
    }

    public InsnList getInitNodeList()
    {
        return this.initNodeList;
    }

    public InsnList getResultNodeList()
    {
        return this.resultNodeList;
    }

    public MethodInsnNode getResult()
    {
        if (this.placeholderNode == null)
        {
            throw new IllegalStateException("Can't get result from invalid tracker!");
        }
        return this.placeholderNode;
    }

    private AbstractInsnNode skipCheckCastBackwards(AbstractInsnNode node)
    {
        // skip possible (?) ALOAD 0 if not static
        if (! this.isStatic && (node instanceof VarInsnNode) && (node.getOpcode() == ALOAD) && (((VarInsnNode) node).var == 0))
        {
            node = node.getPrevious();
        }

        // skip possible check cast
        if ((node instanceof TypeInsnNode) && (node.getOpcode() == CHECKCAST))
        {
            node = node.getPrevious();
        }

        // skip possible (?) ALOAD 0 if not static
        if (! this.isStatic && (node instanceof VarInsnNode) && (node.getOpcode() == ALOAD) && (((VarInsnNode) node).var == 0))
        {
            node = node.getPrevious();
        }
        return node;
    }

    private AbstractInsnNode getPrevious(boolean skipCheckCast)
    {
        AbstractInsnNode previous = this.fieldInsnNode.getPrevious();
        if (skipCheckCast)
        {
            previous = this.skipCheckCastBackwards(previous);
        }
        return previous;
    }

    // example:
    //    ALOAD 0 // might be in other place?
    //    INVOKESTATIC org/diorite/inject/InjectionLibrary.inject ()Ljava/lang/Object;
    //    CHECKCAST org/diorite/inject/injections/Module // optional?
    //    PUTFIELD/PUTSTATIC org/diorite/inject/injections/AdvancedExampleObject.module3 : Lorg/diorite/inject/injections/Module;
    private InjectionType checkDirect()
    {
        AbstractInsnNode previous = this.getPrevious(true);
        PlaceholderType injectPlaceholder = Transformer.isInjectPlaceholder(previous);
        if (injectPlaceholder != PlaceholderType.INVALID)
        {
            assert previous instanceof MethodInsnNode;
            this.placeholderNode = (MethodInsnNode) previous;
            this.placeholderType = injectPlaceholder;
            this.injectionType = InjectionType.DIRECT;
            return this.injectionType;
        }
        return InjectionType.UNKNOWN;
    }

    // indirect example:
    //    INVOKESTATIC org/diorite/inject/InjectionLibrary.inject ()Ljava/lang/Object;
    //    CHECKCAST org/diorite/inject/injections/Module
    //    XSTORE 1 // inject result
    // <code to skip, or track local variable changes?>
    //    XLOAD 0 // this
    //    XLOAD 1 // inject result
    //    PUTFIELD org/diorite/inject/injections/AdvancedExampleObject.module2 : Lorg/diorite/inject/injections/Module;
    private InjectionType checkIndirect()
    {
        AbstractInsnNode previous = this.getPrevious(true);

        // find var index
        if ((previous instanceof VarInsnNode) && AsmUtils.isLoadCode(previous.getOpcode()))
        {
            this.loadCode = new LoadCode((VarInsnNode) previous);

            // XLOAD 0 don't exist here in any case.
            if (! this.isStatic && (this.loadCode.getIndex() == 0))
            {
                throw new AnalyzeError("Unexpected loading of `this` variable!");
            }
            previous = previous.getPrevious();
        }
        else
        {
            return InjectionType.UNKNOWN;
        }
        // track all local variable changes
        previous = this.trackLoadBackwards(previous);

        // final check for placeholder method
        PlaceholderType injectPlaceholder = Transformer.isInjectPlaceholder(previous);

        if (injectPlaceholder != PlaceholderType.INVALID)
        {
            assert previous instanceof MethodInsnNode;
            this.placeholderNode = (MethodInsnNode) previous;
            this.placeholderType = injectPlaceholder;
            this.injectionType = InjectionType.INDIRECT;
            return this.injectionType;
        }
        return InjectionType.UNKNOWN;
    }

    private AbstractInsnNode trackLoadBackwards(AbstractInsnNode node)
    {
        assert this.loadCode != null;
        // skip checkcast and possible XLOAD 0
        node = this.skipCheckCastBackwards(node);

        // find compatible STORE node.
        while ((! (node instanceof VarInsnNode)) || ! this.loadCode.isCompatible((VarInsnNode) node))
        {
            node = node.getPrevious();
        }
        // one more to skip STORE
        node = node.getPrevious();

        // node is STORE opcode for our variable, there might be checkcast here:
        node = this.skipCheckCastBackwards(node);

        // now there might be INVOKESTATIC or some other indirections:

        // if there is XLOAD different than 0 (if not static), then we have next layer of indirect store.
        if ((node instanceof VarInsnNode) && (! this.isStatic && (((VarInsnNode) node).var != 0)) && (((VarInsnNode) node).var != this.loadCode.getIndex()) &&
            AsmUtils.isLoadCode(node.getOpcode()))
        {
            this.loadCode = new LoadCode((VarInsnNode) node);
            node = this.trackLoadBackwards(node);
        }
        // but if there is INVOKE* instead, then we need to go inside invoked method and keep looking for injection placeholder! But skip void methods.
        else if ((node instanceof MethodInsnNode) && AsmUtils.isInvokeCode(node.getOpcode()) && ! ((MethodInsnNode) node).desc.endsWith("V"))
        {
            MethodInsnNode methodInvoke = (MethodInsnNode) node;

            // maybe this is already placeholder invoke?
            if (Transformer.isInjectPlaceholder(methodInvoke) != PlaceholderType.INVALID)
            {
                return node;
            }

            node = this.trackMethod(methodInvoke);
        }
        else
        {
            throw new AnalyzeError("Can't track given field!");
        }

        // now there should be finally INVOKESTATIC, so we can return.
        return node;
    }

    // track method invoke
    private AbstractInsnNode trackMethod(MethodInsnNode methodInvoke)
    {
        // it must be from this same class
        if (! methodInvoke.owner.equals(this.fieldInsnNode.owner))
        {
            throw new AnalyzeError("Can't use injections across different classes! Found indirect injection from " + methodInvoke.owner + "#" +
                                   methodInvoke.name + " " + methodInvoke.desc + ".");
        }
        // get and check method
        MethodNode method = this.transformer.getMethod(methodInvoke);
        if (method == null)
        {
            throw new AnalyzeError("Can't find delegated injection method! Found indirect injection from " + methodInvoke.owner + "#" +
                                   methodInvoke.name + " " + methodInvoke.desc + ".");
        }

        // this is our node, probably
        return this.trackMethod(method);
    }

    // when tracing delegated methods we do this from top to bottom, so this time we need to find first placeholder invoke, and it should be it.
    private AbstractInsnNode trackMethod(MethodNode methodNode)
    {
        // get first instruction and go down until invoke is found.
        AbstractInsnNode node = methodNode.instructions.getFirst();
        do
        {
            if (node == null)
            {
                throw new AnalyzeError("Can't track given field in delegated method (" + methodNode.name + " " + methodNode.desc +
                                       ")! Missing invoke placeholder! Remember that you can't use multiple method delegations!");
            }
            node = node.getNext();
            if (Transformer.isInjectPlaceholder(node) != PlaceholderType.INVALID)
            {
                this.resultNodeList = methodNode.instructions;
                return node;
            }
        }
        while (true);
    }

    // if injection is delegated to other method, we need to track this method.
//    INVOKESPECIAL org/diorite/inject/injections/AdvancedExampleObject.injectEdit (I)Lorg/diorite/inject/injections/Module;
//    PUTFIELD org/diorite/inject/injections/AdvancedExampleObject.edit : Lorg/diorite/inject/injections/Module;
    private InjectionType checkDelegated()
    {
        // skip any checks and get previous node
        AbstractInsnNode previous = this.getPrevious(true);

        // now this should be INVOKE* opcode, if it isn't we are all doomed.
        if (! (previous instanceof MethodInsnNode) || ! AsmUtils.isInvokeCode(previous.getOpcode()))
        {
            return InjectionType.UNKNOWN;
        }
        MethodInsnNode methodInvoke = (MethodInsnNode) previous;
        previous = this.trackMethod(methodInvoke);

        // final check for placeholder method
        PlaceholderType injectPlaceholder = Transformer.isInjectPlaceholder(previous);

        if (injectPlaceholder != PlaceholderType.INVALID)
        {
            assert previous instanceof MethodInsnNode;
            this.placeholderNode = (MethodInsnNode) previous;
            this.placeholderType = injectPlaceholder;
            this.injectionType = InjectionType.DELEGATED;
            return this.injectionType;
        }
        return InjectionType.UNKNOWN;
    }

    public void run()
    {
        if (this.isStatic)
        {
            throw new AnalyzeError("Can't inject static field!");
        }

        this.checkDirect();
        if (this.injectionType == InjectionType.DIRECT)
        {
            return;
        }
        this.checkIndirect();
        if (this.injectionType == InjectionType.INDIRECT)
        {
            return;
        }
        this.checkDelegated();
        if (this.injectionType == InjectionType.UNKNOWN)
        {
            throw new AnalyzeError("Can't find inject invoke, make sure that your code is valid, maybe try use simpler structure for this field!");
        }
    }

    @SuppressWarnings("rawtypes")
    abstract class VarCode<T extends VarCode>
    {
        final VarInsnNode varInsnNode;

        VarCode(VarInsnNode varInsnNode)
        {
            this.varInsnNode = varInsnNode;
        }

        public VarInsnNode getVarInsnNode()
        {
            return this.varInsnNode;
        }

        public int getOpcode()
        {
            return this.varInsnNode.getOpcode();
        }

        public int getIndex()
        {
            return this.varInsnNode.var;
        }

        public boolean isCompatible(VarInsnNode varInsnNode)
        {
            VarCode varCode;
            if (AsmUtils.isLoadCode(varInsnNode.getOpcode()))
            {
                if (AsmUtils.isLoadCode(this.getOpcode()))
                {
                    return false;
                }
                return ((StoreCode) this).isCompatible(new LoadCode(varInsnNode));
            }
            else if (AsmUtils.isStoreCode(varInsnNode.getOpcode()))
            {
                if (AsmUtils.isStoreCode(this.getOpcode()))
                {
                    return false;
                }
                return ((LoadCode) this).isCompatible(new StoreCode(varInsnNode));
            }
            else
            {
                throw new AnalyzeError("Unexpected VarInsnNode Opcode!");
            }
        }

        public boolean isCompatible(T varCode)
        {
            return this.getIndex() == varCode.getIndex();
        }
    }

    class LoadCode extends VarCode<StoreCode>
    {

        LoadCode(VarInsnNode varInsnNode)
        {
            super(varInsnNode);
            if (! AsmUtils.isLoadCode(this.getOpcode()))
            {
                throw new IllegalArgumentException("Expected STORE opcode");
            }
        }

        @Override
        public boolean isCompatible(StoreCode varCode)
        {
            if (! super.isCompatible(varCode))
            {
                return false;
            }
            switch (this.getOpcode())
            {
                case ALOAD:
                    return varCode.getOpcode() == ASTORE;
                case ILOAD:
                    return varCode.getOpcode() == ISTORE;
                case LLOAD:
                    return varCode.getOpcode() == LSTORE;
                case FLOAD:
                    return varCode.getOpcode() == FSTORE;
                case DLOAD:
                    return varCode.getOpcode() == DSTORE;
                default:
                    return false;
            }
        }
    }

    class StoreCode extends VarCode<LoadCode>
    {
        StoreCode(VarInsnNode varInsnNode)
        {
            super(varInsnNode);
            if (! AsmUtils.isStoreCode(this.getOpcode()))
            {
                throw new IllegalArgumentException("Expected STORE opcode");
            }
        }

        @Override
        public boolean isCompatible(LoadCode varCode)
        {
            if (! super.isCompatible(varCode))
            {
                return false;
            }
            switch (this.getOpcode())
            {
                case ASTORE:
                    return varCode.getOpcode() == ALOAD;
                case ISTORE:
                    return varCode.getOpcode() == ILOAD;
                case LSTORE:
                    return varCode.getOpcode() == LLOAD;
                case FSTORE:
                    return varCode.getOpcode() == FLOAD;
                case DSTORE:
                    return varCode.getOpcode() == DLOAD;
                default:
                    return false;
            }
        }
    }

    enum InjectionType
    {
        UNKNOWN,

        // when you directly put result of injection into field.
        DIRECT,

        // when result is first saved to some variable.
        INDIRECT,

        // when result is returned by other method
        DELEGATED,
    }

    enum PlaceholderType
    {
        UNKNOWN,
        INVALID,
        NULLABLE,
        NONNULL
    }

    String fieldNodeToString()
    {
        return this.fieldInsnNode.owner + "#" + this.fieldInsnNode.name + " " + this.fieldInsnNode.desc;
    }

    public class AnalyzeError extends InternalError
    {
        private static final long serialVersionUID = 0;

        public AnalyzeError()
        {
            this(fixErrorMessage(TransformerInjectTracker.this, "AnalyzeError"));
        }

        public AnalyzeError(String message)
        {
            super(fixErrorMessage(TransformerInjectTracker.this, message));
        }

        public AnalyzeError(String message, Throwable cause)
        {
            super(fixErrorMessage(TransformerInjectTracker.this, message), cause);
        }

        public AnalyzeError(Throwable cause)
        {
            super(cause);
        }

    }

    static String fixErrorMessage(TransformerInjectTracker tracker, String message)
    {
        return message + " (Source: " + tracker.fieldNodeToString() + ")";
    }
}
