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

package org.diorite.unsafe;

import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.function.Predicate;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

class ListenerKeyAnnotation implements ListenerKey
{
    private final Type annotationType;

    ListenerKeyAnnotation(Class<? extends Annotation> annotationType)
    {
        this.annotationType = Type.getType(annotationType);
    }

    @Override
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (! (object instanceof ListenerKeyAnnotation))
        {
            return false;
        }
        ListenerKeyAnnotation that = (ListenerKeyAnnotation) object;
        return Objects.equals(this.annotationType, that.annotationType);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.annotationType);
    }

    @Override
    public Predicate<ClassDefinitionImpl> createPredicate()
    {
        return def ->
        {
            ClassReader classReader = def.getClassReader();
            AnnotationClassVisitor annotationClassVisitor = new AnnotationClassVisitor();
            classReader.accept(annotationClassVisitor, 0);
            return annotationClassVisitor.result;
        };
    }

    private class AnnotationClassVisitor extends ClassVisitor implements Opcodes
    {
        boolean result = false;

        AnnotationClassVisitor()
        {
            super(ASM6);
        }

        @Override
        public AnnotationVisitor visitAnnotation(String desc, boolean visible)
        {
            if (Type.getType(desc).equals(ListenerKeyAnnotation.this.annotationType))
            {
                this.result = true;
            }
            return super.visitAnnotation(desc, visible);
        }
    }
}
