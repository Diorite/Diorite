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

import org.diorite.entity.Player;
import org.diorite.inventory.item.ItemStack;

public interface PlayerHotbarInventory extends Inventory, PlayerInventoryPart
{
    /**
     * Returns the IItemStack currently hold
     *
     * @return The currently held IItemStack
     */
    ItemStack getItemInHand();

    /**
     * Sets the item in hand
     *
     * @param stack Stack to set
     *
     * @return previous itemstack in hand.
     */
    ItemStack setItemInHand(final ItemStack stack);

    /**
     * Replace the item in hand, if it matches a excepted one.
     *
     * @param excepted excepted item to replace.
     * @param stack    Stack to set
     *
     * @return true if item was replaced.
     *
     * @throws IllegalArgumentException if excepted item isn't impl version of IItemStack, so it can't be == to any item from inventory.
     */
    boolean replaceItemInHand(final ItemStack excepted, final ItemStack stack) throws IllegalArgumentException;

    /**
     * Get the slot number of the currently held item
     *
     * @return Held item slot number
     */
    default int getHeldItemSlot()
    {
        final Player holder = this.getHolder();
        if (holder == null)
        {
            return - 1;
        }
        return holder.getHeldItemSlot();
    }

    /**
     * Set the slot number of the currently held item.
     *
     * @param slot The new slot number
     */
    default void setHeldItemSlot(final int slot)
    {
        final Player holder = this.getHolder();
        if (holder == null)
        {
            return;
        }
        holder.setHeldItemSlot(slot);
    }

    @Override
    default InventoryType getType()
    {
        return InventoryType.PLAYER_HOTBAR;
    }
}
