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

package org.diorite.inventory.item.meta;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public enum ItemFlag
{
    HIDE_ENCHANTS(1),
    HIDE_ATTRIBUTES(2),
    HIDE_UNBREAKABLE(4),
    HIDE_DESTROYS(8),
    HIDE_PLACED_ON(16),
    HIDE_POTION_EFFECTS(32);

    private final byte value;

    ItemFlag(final int value)
    {
        this.value = (byte) value;
    }

    /**
     * Returns int representation of this flag.
     *
     * @return int representation of this flag.
     */
    public int getValue()
    {
        return this.value;
    }

    /**
     * Returns set of flags represented by given value.
     *
     * @param value int value of flags.
     *
     * @return set of flags represented by given value.
     */
    public static Set<ItemFlag> getFlags(int value)
    {
        if (value == 0)
        {
            return new HashSet<>(1);
        }
        final Set<ItemFlag> flagsSet = new HashSet<>(6);
        final ItemFlag[] flags = ItemFlag.values();
        for (int i = flags.length - 1; i >= 0; i--)
        {
            final ItemFlag flag = flags[i];
            if (value >= flag.value)
            {
                flagsSet.add(flag);
                value -= flag.value;
                if (value == 0)
                {
                    return flagsSet;
                }
            }
        }
        return flagsSet;
    }

    /**
     * Returns int representation of given flags,
     * sum of all values without duplicates.
     *
     * @param flags flags to sum.
     *
     * @return int representation of given flags.
     */
    public static int join(final ItemFlag... flags)
    {
        int i = 0;
        final Collection<ItemFlag> flagsSet = new HashSet<>(flags.length);
        for (final ItemFlag flag : flags)
        {
            if (flagsSet.add(flag))
            {
                i += flag.value;
            }
        }
        return i;
    }


    /**
     * Returns int representation of given flags,
     * sum of all values without duplicates.
     *
     * @param flags flags to sum.
     *
     * @return int representation of given flags.
     */
    public static int join(final Iterable<ItemFlag> flags)
    {
        int i = 0;
        final Collection<ItemFlag> flagsSet = new HashSet<>(10);
        for (final ItemFlag flag : flags)
        {
            if (flagsSet.add(flag))
            {
                i += flag.value;
            }
        }
        return i;
    }


    /**
     * Returns int representation of given flags,
     * sum of all values without duplicates.
     *
     * @param flags flags to sum.
     *
     * @return int representation of given flags.
     */
    @SuppressWarnings("TypeMayBeWeakened")
    public static int join(final Set<ItemFlag> flags)
    {
        int i = 0;
        for (final ItemFlag flag : flags)
        {
            i += flag.value;
        }
        return i;
    }
}
