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

import java.util.Map;

import org.diorite.enchantments.EnchantmentType;

import gnu.trove.TDecorators;
import gnu.trove.map.TObjectShortMap;

/**
 * EnchantmentStorageMeta is specific to items that can store enchantments, as opposed to being enchanted. {@link org.diorite.material.Material#ENCHANTED_BOOK} is an example of an item with enchantment storage.
 */

public interface EnchantmentStorageMeta extends ItemMeta
{
    /**
     * Checks for the existence of any stored enchantments.
     *
     * @return true if an enchantment exists on this meta
     */
    boolean hasStoredEnchants();

    /**
     * Checks for storage of the specified enchantment.
     *
     * @param ench enchantment to check
     *
     * @return true if this enchantment is stored in this meta
     */
    boolean hasStoredEnchant(EnchantmentType ench);

    /**
     * Checks for the level of the stored enchantment.
     *
     * @param ench enchantment to check
     *
     * @return The level that the specified stored enchantment has, or 0 if
     * none
     */
    int getStoredEnchantLevel(EnchantmentType ench);

    /**
     * Gets a copy the stored enchantments in this ItemMeta.
     *
     * @return An immutable copy of the stored enchantments
     */
    TObjectShortMap<EnchantmentType> getStoredEnchants();

    /**
     * Gets a copy the stored enchantments in this ItemMeta.
     *
     * @return An immutable copy of the stored enchantments
     */
    default Map<EnchantmentType, Short> getStoredEnchantsMap()
    {
        return TDecorators.wrap(this.getStoredEnchants());
    }

    /**
     * Stores the specified enchantment in this item meta.
     *
     * @param ench                   EnchantmentType to store
     * @param level                  Level for the enchantment
     * @param ignoreLevelRestriction this indicates the enchantment should be
     *                               applied, ignoring the level limit
     *
     * @return true if the item meta changed as a result of this call, false
     * otherwise
     */
    default boolean addStoredEnchant(final EnchantmentType ench, final int level, final boolean ignoreLevelRestriction)
    {
        return this.addStoredEnchant(ench, level, ignoreLevelRestriction, false);
    }

    /**
     * Stores the specified enchantment in this item meta.
     *
     * @param ench                   EnchantmentType to store
     * @param level                  Level for the enchantment
     * @param ignoreLevelRestriction this indicates the enchantment should be
     *                               applied, ignoring the level limit
     * @param ignoreDuplicates       if true, new enchant is always added
     *                               even if there is other enchantment with time same type.
     *
     * @return true if the item meta changed as a result of this call, false
     * otherwise
     */
    boolean addStoredEnchant(EnchantmentType enchantment, int level, boolean ignoreLevelRestriction, boolean ignoreDuplicates);

    /**
     * Remove the specified stored enchantment from this item meta.
     *
     * @param ench EnchantmentType to remove
     *
     * @return true if the item meta changed as a result of this call, false
     * otherwise
     */
    boolean removeStoredEnchant(EnchantmentType ench) throws IllegalArgumentException;

    /**
     * Remove all stored enchants.
     */
    void removeStoredEnchants();

    /**
     * Checks if the specified enchantment conflicts with any enchantments in
     * this ItemMeta.
     *
     * @param ench enchantment to test
     *
     * @return true if the enchantment conflicts, false otherwise
     */
    boolean hasConflictingStoredEnchant(EnchantmentType ench);

    @Override
    EnchantmentStorageMeta clone();
}