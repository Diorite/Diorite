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

package org.diorite.inventory;

import org.diorite.entity.ArmoredEntity;
import org.diorite.inventory.item.ItemStack;
import org.diorite.material.ArmorType;

public interface EntityEquipment
{
    /**
     * @return An array of ItemStacks from the eq.
     */
    ItemStack[] getContents();

    /**
     * @return The ItemStack in the helmet slot
     */
    ItemStack getHelmet();

    /**
     * @return The ItemStack in the chestplate slot
     */
    ItemStack getChestplate();

    /**
     * @return The ItemStack in the leg slot
     */
    ItemStack getLeggings();

    /**
     * @return The ItemStack in the boots slot
     */
    ItemStack getBoots();

    /**
     * Put the given ItemStack into the given armor slot. This does not check if
     * the ItemStack is a valid type for this slot.
     *
     * @param type  type of armor to find slot.
     * @param armor The ItemStack to use as armor.
     *
     * @return true if item was set.
     */
    default boolean setArmor(final ArmorType type, final ItemStack armor)
    {
        return type.setItem(this, armor);
    }

    /**
     * Put the given ItemStack into the helmet slot. This does not check if
     * the ItemStack is a helmet
     *
     * @param helmet The ItemStack to use as helmet
     *
     * @return previous itemstack used as helmet.
     */
    ItemStack setHelmet(final ItemStack helmet);

    /**
     * Put the given ItemStack into the chestplate slot. This does not check
     * if the ItemStack is a chestplate
     *
     * @param chestplate The ItemStack to use as chestplate
     *
     * @return previous itemstack used as chestplate.
     */
    ItemStack setChestplate(final ItemStack chestplate);

    /**
     * Put the given ItemStack into the leg slot. This does not check if the
     * ItemStack is a pair of leggings
     *
     * @param leggings The ItemStack to use as leggings
     *
     * @return previous itemstack used as leggings.
     */
    ItemStack setLeggings(final ItemStack leggings);

    /**
     * Put the given ItemStack into the boots slot. This does not check if the
     * ItemStack is a boots
     *
     * @param boots The ItemStack to use as boots
     *
     * @return previous itemstack used as boots.
     */
    ItemStack setBoots(final ItemStack boots);

    /**
     * Put the given ItemStack into the helmet slot if it matches a excepted one.
     * NOTE: this is atomic operation.
     *
     * @param excepted excepted item to replace.
     * @param helmet   The ItemStack to use as helmet
     *
     * @return true if item was replaced.
     *
     * @throws IllegalArgumentException if excepted item isn't impl version of ItemStack, so it can't be == to any item from inventory.
     */
    boolean replaceHelmet(final ItemStack excepted, final ItemStack helmet) throws IllegalArgumentException;

    /**
     * Put the given ItemStack into the chestplate slot if it matches a excepted one.
     * NOTE: this is atomic operation.
     *
     * @param excepted   excepted item to replace.
     * @param chestplate The ItemStack to use as chestplate
     *
     * @return true if item was replaced.
     *
     * @throws IllegalArgumentException if excepted item isn't impl version of ItemStack, so it can't be == to any item from inventory.
     */
    boolean replaceChestplate(final ItemStack excepted, final ItemStack chestplate) throws IllegalArgumentException;

    /**
     * Put the given ItemStack into the leggings slot if it matches a excepted one.
     * NOTE: this is atomic operation.
     *
     * @param excepted excepted item to replace.
     * @param leggings The ItemStack to use as leggings
     *
     * @return true if item was replaced.
     *
     * @throws IllegalArgumentException if excepted item isn't impl version of ItemStack, so it can't be == to any item from inventory.
     */
    boolean replaceLeggings(final ItemStack excepted, final ItemStack leggings) throws IllegalArgumentException;

    /**
     * Put the given ItemStack into the boots slot if it matches a excepted one.
     * NOTE: this is atomic operation.
     *
     * @param excepted excepted item to replace.
     * @param boots    The ItemStack to use as boots
     *
     * @return true if item was replaced.
     *
     * @throws IllegalArgumentException if excepted item isn't impl version of ItemStack, so it can't be == to any item from inventory.
     */
    boolean replaceBoots(final ItemStack excepted, final ItemStack boots) throws IllegalArgumentException;

    /**
     * Completely replaces the inventory's contents. Removes all existing
     * contents and replaces it with the ItemStacks given in the array.
     *
     * @param items A complete replacement for the contents; the length must
     *              be less than or equal to {@link #size()}.
     *
     * @throws IllegalArgumentException If the array has more items than the
     *                                  inventory.
     */
    void setContent(final ItemStack[] items);

    /**
     * Gets the entity belonging to this equipment.
     *
     * @return The holder of the inventory; null if it has no holder.
     */
    ArmoredEntity getEquipmentHolder();

    /**
     * @return The size of the inventory
     */
    int size();

}
