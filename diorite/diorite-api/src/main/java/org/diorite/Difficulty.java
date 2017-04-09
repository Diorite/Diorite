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

package org.diorite;

import org.diorite.commons.enums.DynamicEnum;
import org.diorite.config.serialization.Serialization;

/**
 * Represent server difficulty levels.
 */
public class Difficulty extends DynamicEnum<Difficulty>
{
    public static final Difficulty PEACEFUL = $();
    public static final Difficulty EASY     = $();
    public static final Difficulty NORMAL   = $();
    public static final Difficulty HARD     = $();

    /**
     * Returns all enum values.
     *
     * @return all enum values.
     */
    public static Difficulty[] values() { return DynamicEnum.values(Difficulty.class);}

    /**
     * Returns enum value of given name, case-sensitive.
     *
     * @param name
     *         name of element.
     *
     * @return value of given name, or exception is thrown.
     */
    public static Difficulty valueOf(String name) {return DynamicEnum.valueOf(Difficulty.class, name);}
}