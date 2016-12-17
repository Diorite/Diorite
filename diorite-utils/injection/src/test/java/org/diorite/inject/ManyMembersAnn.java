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

package org.diorite.inject;

import static org.diorite.inject.ManyMembersAnn.TestEnum._1;
import static org.diorite.inject.ManyMembersAnn.TestEnum._2;
import static org.diorite.inject.ManyMembersAnn.TestEnum._3;


import javax.inject.Qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface ManyMembersAnn
{
    String string_() default "string_";

    boolean boolean_() default true;

    byte byte_() default 1;

    short short_() default 2;

    char char_() default 3;

    int int_() default 4;

    long long_() default Long.MAX_VALUE;

    float float_() default 0.12F;

    double double_() default 0.1234;

    String[] stringArray() default {"a1", "b2", "c3"};

    boolean[] booleanArray() default {true, false, true};

    byte[] byteArray() default {1, 2, 3};

    short[] shortArray() default {4, 5, 6};

    char[] charArray() default {'a', 'b', 'c'};

    int[] intArray() default {7, 8, 9};

    long[] longArray() default {10, Long.MIN_VALUE, Long.MAX_VALUE};

    float[] floatArray() default {1.23F, 4.56F, 7.89F};

    double[] doubleArray() default {1.2345, 6.7890, 11.1213};

    Class<?> class_() default List.class;

    TestEnum enum_() default _1;

    Class<?>[] classArray() default {List.class, ArrayList.class, Map.class};

    TestEnum[] enumArray() default {_1, _2, _3};

    enum TestEnum
    {
        _1,
        _2,
        _3
    }
}
