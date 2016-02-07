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
import java.util.List;

import org.diorite.firework.FireworkEffect;
import org.diorite.inventory.item.meta.FireworkMeta;

/**
 * Interface of builder of firework item meta data.
 *
 * @param <B> type of builder.
 * @param <M> type of item meta.
 */
public interface IFireworkMetaBuilder<B extends IFireworkMetaBuilder<B, M>, M extends FireworkMeta> extends IMetaBuilder<B, M>
{
    /**
     * Add effect to this firework.
     *
     * @param effect effect to be added.
     *
     * @return builder for method chains.
     */
    default B addEffect(final FireworkEffect effect)
    {
        this.meta().addEffect(effect);
        return this.getBuilder();
    }

    /**
     * Add effects to this firework.
     *
     * @param effects effects to be added.
     *
     * @return builder for method chains.
     */
    default B addEffects(final FireworkEffect... effects)
    {
        this.meta().addEffects(effects);
        return this.getBuilder();
    }

    /**
     * Add effects to this firework.
     *
     * @param effects effects to be added.
     *
     * @return builder for method chains.
     */
    default B addEffects(final Iterable<FireworkEffect> effects)
    {
        this.meta().addEffects(effects);
        return this.getBuilder();
    }

    /**
     * Add effects to this firework.
     *
     * @param src source item meta to copy data from it.
     *
     * @return builder for method chains.
     */
    default B addEffects(final FireworkMeta src)
    {
        final List<FireworkEffect> effects = src.getEffects();
        if ((effects == null) || effects.isEmpty())
        {
            return this.getBuilder();
        }
        this.meta().addEffects(effects);
        return this.getBuilder();
    }

    /**
     * Sets effects to this firework.
     *
     * @param effects effects to be set.
     *
     * @return builder for method chains.
     */
    default B setEffects(final Collection<FireworkEffect> effects)
    {
        this.meta().setEffects(effects);
        return this.getBuilder();
    }

    /**
     * Sets effects to this firework.
     *
     * @param src source item meta to copy data from it.
     *
     * @return builder for method chains.
     */
    default B setEffects(final FireworkMeta src)
    {
        this.meta().setEffects(src.getEffects());
        return this.getBuilder();
    }

    /**
     * Remove all effects from this firework.
     *
     * @return builder for method chains.
     */
    default B removeEffects()
    {
        this.meta().removeEffects();
        return this.getBuilder();
    }

    /**
     * Sets the power of the firework. <br>
     * Each level of power is half a second of flight time.
     *
     * @param power the power of the firework, from 0-128.
     *
     * @return builder for method chains.
     */
    default B power(final int power)
    {
        this.meta().setPower(power);
        return this.getBuilder();
    }

    /**
     * Sets the power of the firework. <br>
     * Each level of power is half a second of flight time.
     *
     * @param src source item meta to copy data from it.
     *
     * @return builder for method chains.
     */
    default B power(final FireworkMeta src)
    {
        this.meta().setPower(src.getPower());
        return this.getBuilder();
    }
}
