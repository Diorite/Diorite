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

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import org.diorite.inject.ManyMembersAnn;
import org.diorite.inject.OneMemberAnn;
import org.diorite.inject.OneMemberNonDefaultAnn;
import org.diorite.inject.Qualifiers;
import org.diorite.inject.ManyMembersAnn.TestEnum;

public class AnnotationCreationTest
{
    @Test
    @OneMemberAnn
    public void testOneMemberAnn() throws Exception
    {
        Object defaultValue = OneMemberAnn.class.getDeclaredMethod("value").getDefaultValue();
        OneMemberAnn of = Qualifiers.of(OneMemberAnn.class, defaultValue);
        AnnotationUtils.assertEquals(AnnotationUtils.getAnnInstance(OneMemberAnn.class), of);
    }

    @Test
    @OneMemberAnn("test")
    public void testOneMemberAnnCustom() throws Exception
    {
        Object defaultValue = OneMemberAnn.class.getDeclaredMethod("value").getDefaultValue();
        OneMemberAnn invalid = Qualifiers.of(OneMemberAnn.class, defaultValue);
        OneMemberAnn valid = Qualifiers.of(OneMemberAnn.class, "test");
        AnnotationUtils.assertNotEquals(AnnotationUtils.getAnnInstance(OneMemberAnn.class), invalid);
        AnnotationUtils.assertEquals(AnnotationUtils.getAnnInstance(OneMemberAnn.class), valid);
    }

    @Test
    @OneMemberNonDefaultAnn("test")
    public void testOneMemberNonDefaultAnn() throws Exception
    {
        OneMemberNonDefaultAnn invalid = Qualifiers.of(OneMemberNonDefaultAnn.class, "invalid");
        OneMemberNonDefaultAnn valid = Qualifiers.of(OneMemberNonDefaultAnn.class, "value", "test");
        AnnotationUtils.assertNotEquals(AnnotationUtils.getAnnInstance(OneMemberNonDefaultAnn.class), invalid);
        AnnotationUtils.assertEquals(AnnotationUtils.getAnnInstance(OneMemberNonDefaultAnn.class), valid);

        try
        {
            Qualifiers.of(OneMemberNonDefaultAnn.class);
        }
        catch (IllegalArgumentException e)
        {
            Assert.assertEquals("Missing required annotation value: value", e.getMessage());
        }
        try
        {
            Qualifiers.of(OneMemberNonDefaultAnn.class, "notValue", "value");
        }
        catch (IllegalArgumentException e)
        {
            Assert.assertEquals("Missing required annotation value: value", e.getMessage());
        }
    }

    @Test
    @ManyMembersAnn(enum_ = TestEnum._3, int_ = 5)
    public void testManyMembersAnn() throws Exception
    {
        ManyMembersAnn invalid = Qualifiers.of(ManyMembersAnn.class);
        ManyMembersAnn valid = Qualifiers.of(ManyMembersAnn.class, Map.of("int_", 5, "enum_", TestEnum._3));
        AnnotationUtils.assertNotEquals(AnnotationUtils.getAnnInstance(ManyMembersAnn.class), invalid);
        AnnotationUtils.assertEquals(AnnotationUtils.getAnnInstance(ManyMembersAnn.class), valid);
    }
}
