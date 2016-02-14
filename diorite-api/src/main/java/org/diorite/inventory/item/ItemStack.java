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

package org.diorite.inventory.item;

import java.util.ArrayList;
import java.util.List;

import org.diorite.inventory.item.meta.ItemMeta;
import org.diorite.material.Material;

public interface ItemStack extends Cloneable
{
    /**
     * Empty array of itemstacks.
     */
    ItemStack[] EMPTY_ITEM_STACK = new ItemStack[0];

    /**
     * @return material of itemstack.
     */
    Material getMaterial();

    /**
     * Change material of itemstack.
     *
     * @param material new material.
     */
    void setMaterial(Material material);

    /**
     * ItemMeta contains data like name, lore, enchantments of item.
     *
     * @return ItemMeta of itemstack.
     */
    ItemMeta getItemMeta();

    /**
     * Check if item stack contains item meta
     *
     * @return true if item stack contains item meta.
     */
    boolean hasItemMeta();

    /**
     * Change itemmeta of itemstack.
     *
     * @param itemMeta new itemmeta.
     */
    void setItemMeta(ItemMeta itemMeta);

    /**
     * @return amout of material in itemstack.
     */
    int getAmount();

    /**
     * Change amout of itemstack in material.
     *
     * @param amount new amount.
     */
    void setAmount(int amount);

//    /**
//     *
//     */
//    void update();

    /**
     * @return true if this is air itemstack.
     */
    boolean isAir();

    /**
     * Check if this itemstack have valid amout of items in it.
     *
     * @return true if amount is smaller or equal to max stack size of material.
     */
    boolean isValid();

    /**
     * Check if items are similar, items are similar if they are made from this
     * same material and have this same item meta, but they can have different size.
     * (amount of items in itemstack)
     *
     * @param b item to check.
     *
     * @return true if items are similar.
     */
    boolean isSimilar(ItemStack b);

    /**
     * Subtract the specified number of items and creates a new ItemStack with given amount of items
     *
     * @param size Number of items which should be removed from this itemstack and moved to new
     *
     * @return ItemStack with specified amount of items
     * null when number of items in this ItemStack is 1
     *
     * @throws IllegalArgumentException when size is greater than amount of items in this ItemStack
     */
    ItemStack split(int size);

    /**
     * Adds one ItemStack to another and returns the remainder
     *
     * @param other ItemStack to add
     *
     * @return All of which failed to add
     */
    ItemStack combine(ItemStack other);

    /**
     * Adds part of one ItemStack to another and returns the remainder
     *
     * @param other  ItemStack to add
     * @param amount amount of ItemStack to add
     *
     * @return All of which failed to add
     */
    ItemStack addFrom(ItemStack other, int amount);

    /**
     * Clone this itemstack (deep clone).
     *
     * @return cloned itemstack.
     */
    ItemStack clone();

    /**
     * Compact given array, it will create the smallest possible array with given items,
     * so it will join duplicated items etc.
     *
     * @param respectStackSize if method should respect max stack size.
     * @param itemsToCopact    item to compact.
     *
     * @return compacted array of items.
     */
    static ItemStack[] compact(final boolean respectStackSize, final ItemStack... itemsToCopact)
    {
        final ItemStack[] items = new ItemStack[itemsToCopact.length];
        int j = 0;
        for (final ItemStack itemStack : itemsToCopact)
        {
            items[j++] = (itemStack == null) ? null : itemStack.clone();
        }

        for (int i = 0, itemsLength = items.length; i < itemsLength; i++)
        {
            final ItemStack item = items[i];
            if ((item == null) || item.isAir())
            {
                continue;
            }
            for (int k = i + 1; k < itemsLength; k++)
            {
                final ItemStack item2 = items[k];
                if (item.isSimilar(item2))
                {
                    if (respectStackSize)
                    {
                        final int space = item.getMaterial().getMaxStack() - item.getAmount();
                        if (space > 0)
                        {
                            final int toAdd = item2.getAmount();
                            if (space > toAdd)
                            {
                                item.setAmount(item.getAmount() + toAdd);
                                items[k] = null;
                            }
                            else
                            {
                                item.setAmount(item.getAmount() + space);
                                item2.setAmount(toAdd - space);
                            }
                        }
                    }
                    else
                    {
                        item.setAmount(item.getAmount() + item2.getAmount());
                        items[k] = null;
                    }
                }

            }
        }
        final List<ItemStack> result = new ArrayList<>(items.length);
        for (final ItemStack item : items)
        {
            if ((item == null) || item.isAir())
            {
                continue;
            }
            result.add(item);
        }
        return result.toArray(new ItemStack[result.size()]);
    }
}
