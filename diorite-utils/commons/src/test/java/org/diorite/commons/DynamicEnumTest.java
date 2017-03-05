/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.commons;

import org.junit.Assert;
import org.junit.Test;

import org.diorite.commons.enums.DynamicEnum;

public class DynamicEnumTest
{
    @Test
    public void testEnum()
    {
        Assert.assertSame(EnumExample.A, EnumExample.values()[0]);
        Assert.assertSame(EnumExample.B, EnumExample.values()[1]);
        Assert.assertSame(EnumExample.C, EnumExample.values()[2]);
        Assert.assertSame(EnumExample.A, EnumExample.valueOf("A"));

        EnumExample d = new EnumExample();
        EnumExample e = new EnumExample() {};
        Assert.assertSame(3, EnumExample.addEnumElement("D", d));
        Assert.assertSame(4, EnumExample.addEnumElement("E", e));
        Assert.assertSame(d, EnumExample.values()[3]);
        Assert.assertSame(e, EnumExample.values()[4]);
        Assert.assertSame(e, EnumExample.valueOf("E"));


        // 0 -> A, 1 -> B, 2 -> C, 3 -> D, 4 -> E
        for (EnumExample enumExample : EnumExample.values())
        {
            System.out.println(enumExample.ordinal() + " -> " + enumExample.name());
        }
    }

    static class EnumExample extends DynamicEnum<EnumExample>
    {
        public static final EnumExample A = $();
        public static final EnumExample B = $();
        public static final EnumExample C = new EnumExample()
        {
            public int doSomething() {return 7;}
        };

        public int doSomething() {return 5;}

        public static EnumExample[] values() {return DynamicEnum.values(EnumExample.class);}

        public static EnumExample valueOf(String name) {return DynamicEnum.valueOf(EnumExample.class, name);}
    }
}
