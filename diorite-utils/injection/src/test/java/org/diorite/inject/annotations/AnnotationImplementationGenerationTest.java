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

package org.diorite.inject.annotations;

import java.lang.annotation.Annotation;

import org.junit.Assert;
import org.junit.Test;

import org.diorite.inject.EmptyAnn;
import org.diorite.inject.ManyMembersAnn;
import org.diorite.inject.OneMemberAnn;
import org.diorite.inject.Qualifiers;

public class AnnotationImplementationGenerationTest
{
    @Test
    @EmptyAnn
    public void testEmptyAnn()
    {
        this.testAnn(EmptyAnn.class);
    }

    @Test
    @OneMemberAnn()
    public void testOneMemberAnn()
    {
        this.testAnn(OneMemberAnn.class);
    }

    @Test
    @ManyMembersAnn
    public void testManyMembersAnn()
    {
        this.testAnn(ManyMembersAnn.class);
    }

    private <T extends Annotation> void testAnn(Class<T> type)
    {
        T inst = AnnotationUtils.getAnnInstance(type, 1);
        try
        {
            T generated = Qualifiers.of(type);
            //not this same class
            Assert.assertNotSame(generated.getClass(), inst.getClass());

            // but this same data
            AnnotationUtils.assertEquals(inst, generated);

            // unproxy test
            T unproxy = Qualifiers.unproxy(inst);

            // this same class
            Assert.assertEquals(generated.getClass(), unproxy.getClass());

            // this same data
            AnnotationUtils.assertEquals(inst, unproxy);
            AnnotationUtils.assertEquals(generated, unproxy);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Assert.fail();
        }
    }

}
