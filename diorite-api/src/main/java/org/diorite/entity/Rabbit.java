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

/**
 * Represent rabbit entity.
 */
public interface Rabbit extends AnimalEntity
{
    RabbitType getRabbitType();

    void setRabbitType(RabbitType type);

    enum RabbitType
    {
        BROWN,
        WHITE,
        BLACK,
        BLACK_AND_WHITE,
        GOLD,
        SALT_AND_PEPPER,
        KILLER(99);
        private int id;

        RabbitType()
        {
            this.id = this.ordinal();
        }

        RabbitType(int id)
        {
            this.id = id;
        }

        public int getId()
        {
            return this.id;
        }

        // Replacement for values()[99]
        public static RabbitType get(int id)
        {
            for (final RabbitType type : values())
            {
                if (type.id == id)
                {
                    return type;
                }
            }

            return null;
        }
    }
}
