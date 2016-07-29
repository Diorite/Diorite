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

package org.diorite.entity;

public interface Horse extends AnimalEntity
{
    HorseType getHorseType();

    void setHorseType(final HorseType type);

    default HorseColor getHorseColor()
    {
        return HorseColor.values()[this.getVariant() % 256];
    }

    void setHorseColor(final HorseColor color);

    default HorseMarkings getHorseMarkings()
    {
        final int variant = this.getVariant();
        final int color = variant % 256;
        return HorseMarkings.values()[(variant % 15) - color];
    }

    void setHorseMarkings(final HorseMarkings markings);

    int getVariant();

    default void setVariant(final int variant)
    {
        final int color = variant % 256;
        final int marks = (variant % 15) - color;

        setHorseColor(HorseColor.values()[color]);
        setHorseMarkings(HorseMarkings.values()[marks]);
    }

    enum HorseType
    {
        HORSE,
        DONKEY,
        MULE,
        ZOMBIE,
        SKELETON
    }

    enum HorseColor
    {
        WHITE,
        CREAMY,
        CHESTNUT,
        BROWN,
        BLACK,
        GRAY,
        DARK_BROWN
    }

    enum HorseMarkings
    {
        NONE,
        WHITE,
        WHITE_FIELDS,
        WHITE_DOTS,
        BLACK_DOTS
    }
}
