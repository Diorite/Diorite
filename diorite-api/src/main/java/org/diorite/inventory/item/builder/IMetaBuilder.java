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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.diorite.enchantments.EnchantmentType;
import org.diorite.entity.attrib.AttributeModifier;
import org.diorite.inventory.item.HideFlag;
import org.diorite.inventory.item.meta.ItemMeta;

import it.unimi.dsi.fastutil.objects.Object2ShortMap;
import it.unimi.dsi.fastutil.objects.Object2ShortMap.Entry;

/**
 * Helper interface, used by all meta builders.
 *
 * @param <B> type of builder.
 * @param <M> type of item meta.
 */
public interface IMetaBuilder<B extends IMetaBuilder<B, M>, M extends ItemMeta>
{
    /**
     * Set name of item.
     *
     * @param name new name of item.
     *
     * @return builder for method chains.
     */
    default B name(final String name)
    {
        this.meta().setDisplayName(name);
        return this.getBuilder();
    }

    /**
     * Set name of item.
     *
     * @param src source item meta to copy name from it.
     *
     * @return builder for method chains.
     */
    default B name(final ItemMeta src)
    {
        this.meta().setDisplayName(src.getDisplayName());
        return this.getBuilder();
    }

    /**
     * Set lore of item.
     *
     * @param lore new lore of item.
     *
     * @return builder for method chains.
     */
    default B lore(final List<String> lore)
    {
        this.meta().setLore(lore);
        return this.getBuilder();
    }

    /**
     * Set lore of item.
     *
     * @param lore new lore of item.
     *
     * @return builder for method chains.
     */
    default B lore(final String... lore)
    {
        this.meta().setLore(Arrays.asList(lore));
        return this.getBuilder();
    }

    /**
     * Adds lore lines to item.
     *
     * @param lore new lore lines to add.
     *
     * @return builder for method chains.
     */
    default B addLore(final String... lore)
    {
        return this.addLore(Arrays.asList(lore));
    }

    /**
     * Adds lore lines to item.
     *
     * @param lore new lore lines to add.
     *
     * @return builder for method chains.
     */
    default B addLore(final Collection<String> lore)
    {
        final List<String> newLore = new ArrayList<>(lore.size() + 5);
        {
            final List<String> oldLore = this.meta().getLore();
            if (oldLore != null)
            {
                newLore.addAll(oldLore);
            }
        }
        newLore.addAll(lore);
        this.meta().setLore(newLore);
        return this.getBuilder();
    }

    /**
     * Set lore of item.
     *
     * @param src source item meta to copy lore from it.
     *
     * @return builder for method chains.
     */
    default B lore(final ItemMeta src)
    {
        this.meta().setLore(src.getLore());
        return this.getBuilder();
    }

    /**
     * Adds lore lines to item.
     *
     * @param src source item to copy lore from it.
     *
     * @return builder for method chains.
     */
    default B addLore(final ItemMeta src)
    {
        return this.addLore(src.getLore());
    }

    /**
     * Adds enchant to item.
     *
     * @param type  type of enchant.
     * @param level level of enchant.
     *
     * @return builder for method chains.
     */
    default B enchant(final EnchantmentType type, final int level)
    {
        this.meta().addEnchant(type, level, true);
        return this.getBuilder();
    }

    /**
     * Adds enchant to item. Accept duplicates.
     *
     * @param type  type of enchant.
     * @param level level of enchant.
     *
     * @return builder for method chains.
     */
    default B forceEnchant(final EnchantmentType type, final int level)
    {
        this.meta().addEnchant(type, level, true, true);
        return this.getBuilder();
    }

    /**
     * Remove all exisiting enchants.
     *
     * @return builder for method chains.
     */
    default B clearEnchants()
    {
        this.meta().removeEnchants();
        return this.getBuilder();
    }

    /**
     * Adds enchant to item.
     *
     * @param src source item meta to copy enchants from it.
     *
     * @return builder for method chains.
     */
    default B enchant(final ItemMeta src)
    {
        final ItemMeta meta = this.meta();
        for (final Entry<EnchantmentType> entry : src.getEnchants().object2ShortEntrySet())
        {
            meta.addEnchant(entry.getKey(), entry.getShortValue(), true);
        }
        return this.getBuilder();
    }

    /**
     * Adds enchant to item. Accept duplicates.
     *
     * @param src source item meta to copy enchants from it.
     *
     * @return builder for method chains.
     */
    default B forceEnchant(final ItemMeta src)
    {
        final ItemMeta meta = this.meta();
        for (final Entry<EnchantmentType> entry : src.getEnchants().object2ShortEntrySet())
        {
            meta.addEnchant(entry.getKey(), entry.getShortValue(), true, true);
        }
        return this.getBuilder();
    }

    /**
     * Set enchants of item.
     *
     * @param src source item meta to copy enchants from it.
     *
     * @return builder for method chains.
     */
    default B setEnchants(final ItemMeta src)
    {
        final ItemMeta meta = this.meta();
        meta.removeEnchants();
        final Object2ShortMap<EnchantmentType> map = src.getEnchants();
        if (map == null)
        {
            return this.getBuilder();
        }
        for (final Entry<EnchantmentType> entry : map.object2ShortEntrySet())
        {
            meta.addEnchant(entry.getKey(), entry.getShortValue(), true);
        }
        return this.getBuilder();
    }

    /**
     * Adds HideFlags to this item.
     *
     * @param hideFlags flags to add.
     *
     * @return builder for method chains.
     */
    default B addHideFlags(final HideFlag... hideFlags)
    {
        this.meta().addHideFlags(hideFlags);
        return this.getBuilder();
    }

    /**
     * Adds HideFlags to this item.
     *
     * @param hideFlags flags to add.
     *
     * @return builder for method chains.
     */
    default B addHideFlags(final Collection<HideFlag> hideFlags)
    {
        if ((hideFlags == null) || hideFlags.isEmpty())
        {
            return this.getBuilder();
        }
        this.meta().addHideFlags(hideFlags.toArray(new HideFlag[hideFlags.size()]));
        return this.getBuilder();
    }

    /**
     * Adds HideFlags to this item.
     *
     * @param src source item meta to copy hide flags from it.
     *
     * @return builder for method chains.
     */
    default B addHideFlags(final ItemMeta src)
    {
        return this.addHideFlags(src.getHideFlags());
    }

    /**
     * Set HideFlags of this item.
     *
     * @param hideFlags flags to set.
     *
     * @return builder for method chains.
     */
    default B setHideFlags(final HideFlag... hideFlags)
    {
        this.meta().removeHideFlags();
        this.meta().addHideFlags(hideFlags);
        return this.getBuilder();
    }

    /**
     * Set HideFlags of this item.
     *
     * @param hideFlags flags to set.
     *
     * @return builder for method chains.
     */
    default B setHideFlags(final Collection<HideFlag> hideFlags)
    {
        this.meta().removeHideFlags();
        if ((hideFlags == null) || hideFlags.isEmpty())
        {
            return this.getBuilder();
        }
        this.meta().addHideFlags(hideFlags.toArray(new HideFlag[hideFlags.size()]));
        return this.getBuilder();
    }

    /**
     * Set HideFlags of this item.
     *
     * @param src source item meta to copy hide flags from it.
     *
     * @return builder for method chains.
     */
    default B setHideFlags(final ItemMeta src)
    {
        return this.setHideFlags(src.getHideFlags());
    }

    /**
     * Removes all attrubite modifiers including default ones.
     *
     * @return builder for method chains.
     */
    default B emptyAttributeModifiers()
    {
        this.meta().setAttributeModifiers(new ArrayList<>(1), true);
        return this.getBuilder();
    }

    /**
     * Adds new attribute modifier to item, use {@link #emptyAttributeModifiers()} before adding attributes to remove default attributes.
     *
     * @param modifier new attribute to add.
     *
     * @return builder for method chains.
     */
    default B addAttributeModifier(final AttributeModifier modifier)
    {
        this.meta().addAttributeModifier(modifier);
        return this.getBuilder();
    }

    /**
     * Adds new attribute modifier to item, use {@link #emptyAttributeModifiers()} before adding attributes to remove default attributes.
     *
     * @param src source item meta to copy attributes from it.
     *
     * @return builder for method chains.
     */
    default B addAttributeModifier(final ItemMeta src)
    {
        final List<AttributeModifier> mods = src.getAttributeModifiers();
        if ((mods == null) || mods.isEmpty())
        {
            return this.getBuilder();
        }
        final ItemMeta meta = this.meta();
        mods.forEach(meta::addAttributeModifier);
        return this.getBuilder();
    }

    /**
     * Set attribute modifiers of this item.
     *
     * @param modifiers list of attribute modifiers to use.
     *
     * @return builder for method chains.
     */
    default B setAttributeModifier(final List<AttributeModifier> modifiers)
    {
        final ItemMeta meta = this.meta();
        if (modifiers == null)
        {
            meta.removeAttributeModifiers();
            return this.getBuilder();
        }
        meta.setAttributeModifiers(modifiers, true);
        return this.getBuilder();
    }

    /**
     * Set attribute modifiers of this item.
     *
     * @param src source item meta to copy attributes from it.
     *
     * @return builder for method chains.
     */
    default B setAttributeModifier(final ItemMeta src)
    {
        return this.setAttributeModifier(src.getAttributeModifiers());
    }

    /**
     * Returns created item meta.
     *
     * @return created item meta.
     */
    @SuppressWarnings("unchecked")
    default M build()
    {
        return (M) this.meta().clone();
    }

    /**
     * Returns wrapped meta, used only by builder classes.
     *
     * @return wrapped meta, used only by builder classes.
     */
    M meta();

    /**
     * Returns builder.
     *
     * @return builder.
     */
    B getBuilder();
}
