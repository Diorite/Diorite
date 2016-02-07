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

import org.diorite.enchantments.EnchantmentType;
import org.diorite.inventory.item.meta.EnchantmentStorageMeta;

import it.unimi.dsi.fastutil.objects.Object2ShortMap;
import it.unimi.dsi.fastutil.objects.Object2ShortMap.Entry;

/**
 * Interface of builder of enchantment storage item meta data.
 *
 * @param <B> type of builder.
 * @param <M> type of item meta.
 */
public interface IEnchantmentStorageMetaBuilder<B extends IEnchantmentStorageMetaBuilder<B, M>, M extends EnchantmentStorageMeta> extends IMetaBuilder<B, M>
{
    /**
     * Stores the specified enchantment in this item meta.
     *
     * @param ench  EnchantmentType to store
     * @param level Level for the enchantment
     *
     * @return builder for method chains.
     */
    default B addStoredEnchant(final EnchantmentType ench, final int level)
    {
        this.meta().addStoredEnchant(ench, level, true);
        return this.getBuilder();
    }

    /**
     * Stores the specified enchantment in this item meta.
     *
     * @param enchantment EnchantmentType to store
     * @param level       Level for the enchantment
     *
     * @return builder for method chains.
     */
    default B forceAddStoredEnchant(final EnchantmentType enchantment, final int level)
    {
        this.meta().addStoredEnchant(enchantment, level, true, true);
        return this.getBuilder();
    }

    /**
     * Set stored enchants of item.
     *
     * @param src source item meta to copy enchants from it.
     *
     * @return builder for method chains.
     */
    default B setStoredEnchants(final EnchantmentStorageMeta src)
    {
        final M meta = this.meta();
        meta.removeStoredEnchants();
        final Object2ShortMap<EnchantmentType> map = src.getStoredEnchants();
        if (map == null)
        {
            return this.getBuilder();
        }
        for (final Entry<EnchantmentType> entry : map.object2ShortEntrySet())
        {
            meta.addStoredEnchant(entry.getKey(), entry.getShortValue(), true);
        }
        return this.getBuilder();
    }

    /**
     * Remove all stored enchants.
     *
     * @return builder for method chains.
     */
    default B clearStoredEnchants()
    {
        this.meta().removeStoredEnchants();
        return this.getBuilder();
    }

}
