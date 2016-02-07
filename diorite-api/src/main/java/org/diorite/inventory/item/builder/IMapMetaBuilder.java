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

package org.diorite.inventory.item.builder;

import java.util.Collection;

import org.diorite.inventory.item.meta.MapMeta;
import org.diorite.map.MapIcon;

/**
 * Interface of builder of map item meta data.
 *
 * @param <B> type of builder.
 * @param <M> type of item meta.
 */
public interface IMapMetaBuilder<B extends IMapMetaBuilder<B, M>, M extends MapMeta> extends IMetaBuilder<B, M>
{
    /**
     * Sets scale of this map.
     *
     * @param value true to scale
     *
     * @return builder for method chains.
     */
    default B scaling(final int value)
    {
        this.meta().setScaling(value);
        return this.getBuilder();
    }

    /**
     * Sets scale of this map.
     *
     * @param src source item meta to copy data from it.
     *
     * @return builder for method chains.
     */
    default B scaling(final MapMeta src)
    {
        this.meta().setScaling(src.getScaling());
        return this.getBuilder();
    }

    /**
     * Adds new map icon to this meta.
     *
     * @param icon icon to be added.
     *
     * @return builder for method chains.
     */
    default B addIcon(final MapIcon icon)
    {
        this.meta().addIcon(icon);
        return this.getBuilder();
    }

    /**
     * Adds new map icon to this meta.
     *
     * @param src source item meta to copy data from it.
     *
     * @return builder for method chains.
     */
    default B addIcons(final MapMeta src)
    {
        final Collection<MapIcon> icons = src.getIcons();
        if ((icons == null) || icons.isEmpty())
        {
            return this.getBuilder();
        }
        final M meta = this.meta();
        icons.forEach(meta::addIcon);
        return this.getBuilder();
    }

    /**
     * Sets new map icons to this meta.
     *
     * @param icons icons to be set.
     *
     * @return builder for method chains.
     */
    default B setIcons(final Collection<MapIcon> icons)
    {
        final M meta = this.meta();
        meta.removeIcons();
        icons.forEach(meta::addIcon);
        return this.getBuilder();
    }

    /**
     * Sets new map icons to this meta.
     *
     * @param src source item meta to copy data from it.
     *
     * @return builder for method chains.
     */
    default B setIcons(final MapMeta src)
    {
        this.meta().removeIcons();
        return this.addIcons(src);
    }

    /**
     * Remove all map icon from this meta.
     *
     * @return builder for method chains.
     */
    default B removeIcons()
    {
        this.meta().removeIcons();
        return this.getBuilder();
    }
}
