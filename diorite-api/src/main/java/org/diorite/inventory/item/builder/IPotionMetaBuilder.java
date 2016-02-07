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

import org.diorite.effect.StatusEffect;
import org.diorite.inventory.item.meta.PotionMeta;

/**
 * Interface of builder of potion item meta data.
 *
 * @param <B> type of builder.
 * @param <M> type of item meta.
 */
public interface IPotionMetaBuilder<B extends IPotionMetaBuilder<B, M>, M extends PotionMeta> extends IMetaBuilder<B, M>
{
    /**
     * Set custom effects of this potion.
     *
     * @param effects collection of effects to set.
     *
     * @return builder for method chains.
     */
    default B setCustomEffects(final Collection<StatusEffect> effects)
    {
        this.meta().setCustomEffects(effects);
        return this.getBuilder();
    }

    /**
     * Set custom effects of this potion.
     *
     * @param src source item meta to copy data from it.
     *
     * @return builder for method chains.
     */
    default B setCustomEffects(final PotionMeta src)
    {
        final List<StatusEffect> effects = src.getCustomEffects();
        if (effects == null)
        {
            return this.getBuilder();
        }
        this.meta().setCustomEffects(effects);
        return this.getBuilder();
    }

    /**
     * Adds a custom potion effect to this potion.
     *
     * @param effect the potion effect to add.
     *
     * @return builder for method chains.
     */
    default B addCustomEffect(final StatusEffect effect)
    {
        this.meta().addCustomEffect(effect, true);
        return this.getBuilder();
    }

    /**
     * Adds a custom potion effect to this potion.
     *
     * @param src source item meta to copy data from it.
     *
     * @return builder for method chains.
     */
    default B addCustomEffects(final PotionMeta src)
    {
        final List<StatusEffect> effects = src.getCustomEffects();
        if ((effects == null) || effects.isEmpty())
        {
            return this.getBuilder();
        }
        final M meta = this.meta();
        effects.forEach(e -> meta.addCustomEffect(e, true));
        return this.getBuilder();
    }

    /**
     * Adds a custom potion effect to this potion. This may add multiple effects of this same type.
     *
     * @param effect the potion effect to add.
     *
     * @return builder for method chains.
     */
    default B forceAddCustomEffect(final StatusEffect effect)
    {
        this.meta().addCustomEffect(effect, false);
        return this.getBuilder();
    }

    /**
     * Adds a custom potion effect to this potion. This may add multiple effects of this same type.
     *
     * @param src source item meta to copy data from it.
     *
     * @return builder for method chains.
     */
    default B forceAddCustomEffects(final PotionMeta src)
    {
        final List<StatusEffect> effects = src.getCustomEffects();
        if ((effects == null) || effects.isEmpty())
        {
            return this.getBuilder();
        }
        final M meta = this.meta();
        effects.forEach(e -> meta.addCustomEffect(e, false));
        return this.getBuilder();
    }

    /**
     * Removes all custom potion effects from this potion.
     *
     * @return builder for method chains.
     */
    default B clearCustomEffects()
    {
        this.meta().clearCustomEffects();
        return this.getBuilder();
    }

    /**
     * Set string id of potion, used to set display name etc... <br>
     * This class contains variables with possible potion ids. {@link org.diorite.inventory.item.meta.PotionMeta.PotionTypes}
     *
     * @param id new id of potion.
     *
     * @return builder for method chains.
     */
    default B potionID(final String id)
    {
        this.meta().setPotionID(id);
        return this.getBuilder();
    }

    /**
     * Set string id of potion, used to set display name etc... <br>
     * This class contains variables with possible potion ids. {@link org.diorite.inventory.item.meta.PotionMeta.PotionTypes}
     *
     * @param src source item meta to copy data from it.
     *
     * @return builder for method chains.
     */
    default B potionID(final PotionMeta src)
    {
        this.meta().setPotionID(src.getPotionID());
        return this.getBuilder();
    }
}
