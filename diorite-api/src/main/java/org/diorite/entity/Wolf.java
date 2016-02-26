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
     * Returns true if a wolf is sitting.
     *
     * @return true if a wolf is sitting.
     */
    boolean getSitting();
    
    /**
     * Sets if a wolf is sitting
     *
     * @param canExplode if a wolf is sitting.
     */
    void setSitting(boolean sitting);
    
    /**
     * Returns true if a wolf is angry.
     *
     * @return true if a wolf is angry.
     */
    boolean isAngry();
    
    /**
     * Sets if a wolf is angry.
     *
     * @param angry if a wolf is angry.
     */
    void setAngry(boolean angry);
    
    /**
     * Returns a color of the wolf's collar.
     *
     * @return a color of the wolf's collar.
     */
    DyeColor getCollarColor();
    
    /**
     * Sets a color of the wolf's collar.
     *
     * @param color to define collar's color.
     */
    void setCollarColor(DyeColor color);
}
