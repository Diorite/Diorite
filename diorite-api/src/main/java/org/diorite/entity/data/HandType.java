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

package org.diorite.entity.data;

/**
 * Represent hand type, main or off hand. <br>
 * Every player may use other (left or right) hand as main.
 */
public enum HandType
{
    /**
     * Main hand of player.
     */
    MAIN("mainhand"),
    /**
     * Off hand of player.
     */
    OFF("offhand");
    /**
     * String representation of hand, used in NBT etc..
     */
    private final String stringType;

    HandType(final String stringType)
    {
        this.stringType = stringType;
    }

    /**
     * Returns string id of hand type, used in nbt etc...
     *
     * @return string id of hand type, used in nbt etc...
     */
    public String getStringType()
    {
        return this.stringType;
    }

    /**
     * Get one of HandType entry by its string id/type.
     *
     * @param id id of entry.
     *
     * @return one of entry or null.
     */
    public static HandType getByStringType(final String id)
    {
        if (id.equalsIgnoreCase(MAIN.stringType))
        {
            return MAIN;
        }
        if (id.equalsIgnoreCase(OFF.stringType))
        {
            return OFF;
        }
        return null;
    }

    /**
     * Returns opposite hand type, like off for main.
     *
     * @return opposite hand type, like off for main.
     */
    public HandType getOpposite()
    {
        switch (this)
        {
            case MAIN:
                return OFF;
            case OFF:
                return MAIN;
            default:
                throw new AssertionError();
        }
    }
}
