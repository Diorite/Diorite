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

/**
 * Represents possible key bindings.
 */
public class KeyBind extends DynamicEnum<KeyBind>
{
    public static final KeyBind KEY_INVENTORY = $("key.inventory");

    private final String name;

    KeyBind(String name) {this.name = name;}

    /**
     * Returns name of key.
     *
     * @return name of key.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Returns all enum values.
     *
     * @return all enum values.
     */
    public static KeyBind[] values() {return DynamicEnum.values(KeyBind.class);}

    /**
     * Returns enum value of given name, case-sensitive.
     *
     * @param name
     *         name of element.
     *
     * @return value of given name, or exception is thrown.
     */
    public static KeyBind valueOf(String name) {return DynamicEnum.valueOf(KeyBind.class, name);}
}