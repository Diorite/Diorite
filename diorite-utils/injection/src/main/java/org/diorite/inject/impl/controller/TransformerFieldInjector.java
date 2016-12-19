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

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import org.diorite.unsafe.AsmUtils;

final class TransformerFieldInjector
{
    private final Transformer injectTransformer;

    private TransformerFieldInjector(Transformer injectTransformer)
    {
        this.injectTransformer = injectTransformer;
    }

    public static TransformerFieldInjector run(Transformer injectTransformer)
    {
        TransformerFieldInjector injectorTransformer = new TransformerFieldInjector(injectTransformer);
        injectorTransformer.injectFields();
        return injectorTransformer;
    }

    public Transformer getInjectTransformer()
    {
        return this.injectTransformer;
    }

    private void injectFields()
    {
//        assert this.clinit != null;
//        this.findAllFieldInitMethods(this.clinit);
        for (MethodNode init : this.injectTransformer.inits.keySet())
        {
            this.injectFieldsIn(init);
        }
    }

    private void injectFieldsIn(MethodNode rootNode)
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
            this.trackFieldToInject((FieldInsnNode) node, rootNode.instructions);
            node = node.getNext();
        }
    }

    private void trackFieldToInject(FieldInsnNode fieldInsnNode, InsnList insnList)
    {
        // check if field is in this same class
        if (! fieldInsnNode.owner.equals(this.injectTransformer.classNode.name))
        {
            return;
        }
        // first check if field is injected
        TransformerFieldPair fieldPair = this.injectTransformer.getFieldPair(fieldInsnNode);
        if ((fieldPair == null) || (fieldPair.node == null) || (fieldPair.data == null))
        {
            return;
        }

        TransformerInjectTracker injectTracker = TransformerInjectTracker.trackFromField(this.injectTransformer, fieldInsnNode, insnList);
        fieldPair.placeholderType = injectTracker.getPlaceholderType();
        this.injectField(fieldPair, injectTracker);
    }

    private void injectField(TransformerFieldPair fieldPair, TransformerInjectTracker result)
    {
        ControllerFieldData<?> fieldData = fieldPair.data;
        assert fieldData != null;

        MethodInsnNode injectionInvokeNode = result.getResult();
        FieldInsnNode putfieldNode = result.getFieldInsnNode();

        // insert invoke methods:
        MethodNode codeBefore = new MethodNode();
        MethodNode codeAfter = new MethodNode();
        this.injectTransformer.fillMethodInvokes(codeBefore, codeAfter, fieldData);

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
            TransformerInvokerGenerator.generateFieldInjection(this.injectTransformer.classData, fieldData, tempNode, - 1, fieldPair.placeholderType);
            resultNodeList.insertBefore(injectionInvokeNode, tempNode.instructions);
            resultNodeList.remove(injectionInvokeNode);
        }
    }
}
