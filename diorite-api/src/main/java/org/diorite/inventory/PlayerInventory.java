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

import org.diorite.inventory.item.ItemStack;

public interface PlayerInventory extends Inventory, PlayerArmorInventory, PlayerCraftingInventory, PlayerFullEqInventory
{
    @Override
    default PlayerInventory getPlayerInventory()
    {
        return this;
    }

    /**
     * Get item hold in cursor by player.
     *
     * @return item hold in cursor by player.
     */
    ItemStack getCursorItem();

    /**
     * Set cursor to given item, and return old cursor item.
     *
     * @param cursorItem new cursor item.
     *
     * @return old cursor item.
     */
    ItemStack setCursorItem(ItemStack cursorItem);

    /**
     * Returns drag controller for player of this inventory.
     *
     * @return drag controller for player of this inventory.
     */
    DragController getDragController();

    /**
     * Put the given ItemStack into the helmet slot if it matches a excepted one.
     * NOTE: this is atomic operation.
     *
     * @param excepted excepted item to replace.
     * @param cursorItem   The ItemStack to use as cursor
     *
     * @return true if item was replaced.
     *
     * @throws IllegalArgumentException if excepted item isn't impl version of ItemStack, so it can't be == to any item from inventory.
     */
    boolean replaceCursorItem(ItemStack excepted, ItemStack cursorItem);

    /**
     * Returns sub-part of player inventory, contains only
     * slots for armor stuff, indexed from 0.
     *
     * @return sub-part of player inventory.
     */
    PlayerArmorInventory getArmorInventory();

    /**
     * Returns sub-part of player inventory, contains only
     * slots for crafting stuff, indexed from 0.
     *
     * @return sub-part of player inventory.
     */
    PlayerCraftingInventory getCraftingInventory();

    /**
     * Returns sub-part of player inventory, contains only
     * eq (with hotbar) slots, indexed from 0.
     *
     * @return sub-part of player inventory.
     */
    PlayerFullEqInventory getFullEqInventory();

    @Override
    default InventoryType getType()
    {
        return InventoryType.PLAYER;
    }

    @Override
    default int getRows()
    {
        return 4;
    }

    @Override
    default int getColumns()
    {
        return 9;
    }
}
