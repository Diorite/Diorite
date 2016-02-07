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

import org.diorite.inventory.item.meta.LeatherArmorMeta;
import org.diorite.utils.Color;

/**
 * Interface of builder of leather armor item meta data.
 *
 * @param <B> type of builder.
 * @param <M> type of item meta.
 */
public interface ILeatherArmorMetaBuilder<B extends ILeatherArmorMetaBuilder<B, M>, M extends LeatherArmorMeta> extends IRepairableMetaBuilder<B, M>
{
    /**
     * Sets color of armor.
     *
     * @param color new color of armor.
     *
     * @return builder for method chains.
     */
    default B color(final Color color)
    {
        this.meta().setColor(color);
        return this.getBuilder();
    }

    /**
     * Sets color of armor.
     *
     * @param src source item meta to copy color from it.
     *
     * @return builder for method chains.
     */
    default B color(final LeatherArmorMeta src)
    {
        this.meta().setColor(src.getColor());
        return this.getBuilder();
    }

    /**
     * Removes custom color from armor.
     *
     * @return builder for method chains.
     */
    default B removeColor()
    {
        this.meta().removeColor();
        return this.getBuilder();
    }
}
