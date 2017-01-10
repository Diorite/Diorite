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

package org.diorite.core.protocol;

public class PacketType
{
    private final Class<?> type;
    private final String name;
    private final int id;
    private final int minSize;
    private final int maxSize;

    public PacketType(Class<?> type, String name, int id, int minSize, int maxSize) {
        this.type = type;
        this.name = name;
        this.id = id;
        this.minSize = minSize;
        this.maxSize = maxSize;
    }
    public PacketType(Class<?> type) {
        this.type = type;
        this.name = name;
        this.id = id;
        this.minSize = minSize;
        this.maxSize = maxSize;
    }


    public Class<?> getType()
    {
        return this.type;
    }

    public String getName()
    {
        return this.name;
    }

    public int getId()
    {
        return this.id;
    }

    public int getMinSize()
    {
        return this.minSize;
    }

    public int getMaxSize()
    {
        return this.maxSize;
    }
}
