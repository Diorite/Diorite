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

import org.diorite.map.MapIcon;

/**
 * Represents a map that can be scalable.
 */
public interface MapMeta extends ItemMeta
{
    /**
     * Checks to see if this map is scaling.
     *
     * @return true if this map is scaling.
     */
    boolean isScaling();

    /**
     * Sets if this map is scaling or not.
     *
     * @param value true to scale
     */
    void setScaling(boolean value);

    /**
     * Returns collection of all icons on map.
     *
     * @return collection of all icons on map.
     */
    Collection<MapIcon> getIcons();

    /**
     * Adds new map icon to this meta.
     *
     * @param icon icon to be added.
     */
    void addIcon(MapIcon icon);

    /**
     * Remove map icon from this meta.
     *
     * @param icon icon to be removed.
     */
    void removeIcon(MapIcon icon);

    /**
     * Remove map icon from this meta.
     *
     * @param icon icon to be removed.
     */
    void removeIcon(String icon);

    /**
     * Remove all map icon from this meta.
     */
    void removeIcons();

    @Override
    MapMeta clone();
}
