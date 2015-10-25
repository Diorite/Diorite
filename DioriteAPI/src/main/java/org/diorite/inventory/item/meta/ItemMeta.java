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

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.diorite.inventory.item.enchantments.Enchantment;
import org.diorite.nbt.NbtTagCompound;

import gnu.trove.TDecorators;
import gnu.trove.map.TObjectShortMap;

/**
 * Represent meta data of item stack.
 */
public interface ItemMeta
{
    /**
     * Returns copy of nbt compound tag of this item meta.
     *
     * @return copy of nbt compound tag of this item meta.
     */
    NbtTagCompound getRawData();

    /**
     * Set raw nbt data of this item to given tag.
     *
     * @param tag tag with meta data.
     */
    void setRawData(NbtTagCompound tag);

    /**
     * check if this item meta affect item,
     * if item don't have any lore, name and other stuff,
     * then this method will return true.
     *
     * @return true if item meta don't affect item.
     */
    default boolean isDefault()
    {
        return this.getRawData().getTags().isEmpty();
    }

    /**
     * Checks for existence of a display name.
     *
     * @return true if this has a display name
     */
    boolean hasDisplayName();

    /**
     * Gets the display name that is set.
     * <br>
     * Returns null if display name isn't used.
     *
     * @return the display name that is set
     */
    String getDisplayName();

    /**
     * Sets the display name.
     *
     * @param name the name to set
     */
    void setDisplayName(String name);

    /**
     * Remove display name from item.
     */
    default void removeDisplayName()
    {
        this.setDisplayName(null);
    }

    /**
     * Checks for existence of lore.
     *
     * @return true if this has lore
     */
    boolean hasLore();

    /**
     * Gets the lore that is set.
     * <br>
     * Returns null if lore isn't used.
     *
     * @return a list of lore that is set.
     */
    List<String> getLore();

    /**
     * Sets the lore for this item.
     *
     * @param lore the lore that will be set
     */
    void setLore(List<String> lore);

    /**
     * Remove lore from this item.
     */
    default void removeLore()
    {
        this.setLore(null);
    }

    /**
     * Checks for the existence of any enchantments.
     *
     * @return true if an enchantment exists on this meta
     */
    boolean hasEnchants();

    /**
     * Checks for existence of the specified enchantment.
     *
     * @param enchantment enchantment to check
     *
     * @return true if this enchantment exists for this meta
     */
    boolean hasEnchant(Enchantment enchantment);

    /**
     * Checks for the level of the specified enchantment.
     *
     * @param enchantment enchantment to check
     *
     * @return The level that the specified enchantment has, or 0 if none
     */
    int getEnchantLevel(Enchantment enchantment);

    /**
     * Returns a copy the enchantments in this ItemMeta. <br>
     * Returns an empty map if none.
     *
     * @return An copy of the enchantments
     */
    TObjectShortMap<Enchantment> getEnchants();

    /**
     * Returns a copy the enchantments in this ItemMeta. <br>
     * Returns an empty map if none. <br>
     * Plugins should use {@link #getEnchants()} where possible.
     *
     * @return An copy of the enchantments
     */
    default Map<Enchantment, Short> getEnchantsMap()
    {
        return TDecorators.wrap(this.getEnchants());
    }

    /**
     * Adds the specified enchantment to this item meta. <br>
     * If enchantment already exist level will be updated, if level was this same, false will be returned.
     *
     * @param enchantment            Enchantment to add
     * @param level                  Level for the enchantment
     * @param ignoreLevelRestriction this indicates the enchantment should be
     *                               applied, ignoring the level limit
     *
     * @return true if the item meta changed as a result of this call, false
     * otherwise
     */
    boolean addEnchant(Enchantment enchantment, int level, boolean ignoreLevelRestriction);

    /**
     * Removes the specified enchantment from this item meta.
     *
     * @param enchantment Enchantment to remove
     *
     * @return true if the item meta changed as a result of this call, false
     * otherwise
     */
    boolean removeEnchant(Enchantment enchantment);

    /**
     * Checks if the specified enchantment conflicts with any enchantments in
     * this ItemMeta.
     *
     * @param enchantment enchantment to test
     *
     * @return true if the enchantment conflicts, false otherwise
     */
    boolean hasConflictingEnchant(Enchantment enchantment);

    /**
     * Remove all enchantments from this meta.
     */
    void removeEnchantments();

    /**
     * Set itemflags which should be ignored when rendering a ItemStack in the Client. This Method does silently ignore double set itemFlags.
     *
     * @param hideFlags The hideflags which shouldn't be rendered
     */
    void addHideFlags(HideFlag... hideFlags);

    /**
     * Remove specific set of itemFlags. This tells the Client it should render it again. This Method does silently ignore double removed itemFlags.
     *
     * @param hideFlags Hideflags which should be removed
     */
    void removeHideFlags(HideFlag... hideFlags);

    /**
     * Get current set itemFlags. The collection returned is unmodifiable.
     *
     * @return A set of all itemFlags set
     */
    Set<HideFlag> getHideFlags();

    /**
     * Check if the specified flag is present on this item.
     *
     * @param flag the flag to check
     *
     * @return if it is present
     */
    boolean hasHideFlag(HideFlag flag);

    /**
     * Remove all item flags from this meta.
     */
    void removeHideFlags();

    /**
     * Close this item meta.
     *
     * @return clone of this item meta.
     */
    ItemMeta clone();

//    default boolean equals(final ItemMeta b)
//    {
//        if (b == null)
//        {
//            return this.isDefault();
//        }
//        if (b.isDefault())
//        {
//            return this.isDefault();
//        }
//        return b.getRawData().equals(this.getRawData());
//    }
}
