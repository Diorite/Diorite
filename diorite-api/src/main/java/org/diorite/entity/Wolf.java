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

import org.diorite.DyeColor;

/**
 * Represent wolf entity.
 */
public interface Wolf extends AnimalEntity
{
    /**
     * Returns taken damage
     *
     * @return taken damage
     */
    double getTakenDamage();

    /**
     * Sets taken damage
     *
     * @param damage for taken damage value
     */
    void setTakenDamage(double damage);

    /**
     * Returns true if a wolf is begging
     *
     * @return true if a wolf is begging
     */
    boolean getBegging();

    /**
     * Sets if a wolf is begging
     *
     * @param begging if a wolf is begging
     */
    void setBegging(boolean begging);

    /**
     * Returns the collar's color
     *
     * @return the collar's color
     */
    DyeColor getCollarColor();

    /**
     * Sets the collar's color
     *
     * @param collarColor defines the collar's color
     */
    void setCollarColor(DyeColor collarColor);
}
