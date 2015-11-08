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

import org.diorite.utils.Color;

public interface LeatherArmorMeta extends ItemMeta
{
    /**
     * Default color of leather armor.
     */
    Color DEFAULT_LEATHER_COLOR = Color.fromRGB(10511680);

    /**
     * Returns true if armor is using custom color.
     *
     * @return true if armor is using custom color.
     */
    boolean hasColor();

    /**
     * Returns custom color of armor, may return null.
     *
     * @return custom color of armor, may return null.
     */
    Color getColor();

    /**
     * Set custom color of armor.
     *
     * @param color custom color of armor to set.
     */
    void setColor(Color color);

    /**
     * Remove custom color from armor.
     */
    default void removeColor()
    {
        this.setColor(null);
    }

    @Override
    LeatherArmorMeta clone();
}