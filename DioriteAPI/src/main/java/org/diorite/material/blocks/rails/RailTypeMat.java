/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.material.blocks.rails;

public enum RailTypeMat
{
    FLAT_NORTH_SOUTH(0x00),
    FLAT_WEST_EAST(0x01),
    ASCENDING_EAST(0x02),
    ASCENDING_WEST(0x03),
    ASCENDING_NORTH(0x04),
    ASCENDING_SOUTH(0x05),
    CURVED_SOUTH_EAST(0x06),
    CURVED_SOUTH_WEST(0x07),
    CURVED_NORTH_WEST(0x08),
    CURVED_NORTH_EAST(0x09);

    private final byte flag;

    RailTypeMat(final int flag)
    {
        this.flag = (byte) flag;
    }

    public byte getFlag()
    {
        return this.flag;
    }
}
