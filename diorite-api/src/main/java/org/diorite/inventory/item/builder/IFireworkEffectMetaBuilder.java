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

import org.diorite.firework.FireworkEffect;
import org.diorite.inventory.item.meta.FireworkEffectMeta;

/**
 * Interface of builder of firework effect item meta data.
 *
 * @param <B> type of builder.
 * @param <M> type of item meta.
 */
public interface IFireworkEffectMetaBuilder<B extends IFireworkEffectMetaBuilder<B, M>, M extends FireworkEffectMeta> extends IMetaBuilder<B, M>
{
    /**
     * Sets the firework effect for this meta.
     *
     * @param effect the effect to set.
     *
     * @return builder for method chains.
     */
    default B effect(final FireworkEffect effect)
    {
        this.meta().setEffect(effect);
        return this.getBuilder();
    }

    /**
     * Sets the firework effect for this meta.
     *
     * @param src source item meta to copy data from it.
     *
     * @return builder for method chains.
     */
    default B effect(final FireworkEffectMeta src)
    {
        this.meta().setEffect(src.getEffect());
        return this.getBuilder();
    }

    /**
     * Remove effect from this meta.
     *
     * @return builder for method chains.
     */
    default B removeEffect()
    {
        this.meta().removeEffect();
        return this.getBuilder();
    }
}
