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

package org.diorite.inventory.item;

import org.diorite.nbt.NbtTagCompound;

public interface ItemMeta
{
    NbtTagCompound getRawData();

    void setRawData(NbtTagCompound tag);

    /**
     * check if this item meta affect item,
     * if item don't have any lore, name and other stuff,
     * then this method will return true.
     *
     * @return true if item meta don't affect item.
     */
    default boolean isDefault()
    {
        return this.getRawData().getTags().isEmpty();
    }

    default boolean equals(final ItemMeta b)
    {
        if (b == null)
        {
            return this.isDefault();
        }
        if (b.isDefault())
        {
            return this.isDefault();
        }
        return b.getRawData().equals(this.getRawData());
    }

    static boolean equals(final ItemMeta a, final ItemMeta b)
    {
        //noinspection ObjectEquality
        if (a == b)
        {
            return true;
        }
        if (a != null)
        {
            return a.equals(b);
        }
        return b.equals(null);
    }
}
