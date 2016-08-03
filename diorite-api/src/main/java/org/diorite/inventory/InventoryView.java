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

package org.diorite.inventory;

import org.diorite.entity.Player;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.slot.Slot;

/**
 *
 */
public interface InventoryView
{
    int getId();

    InventoryType getType();

    Player getPlayer();

    Inventory getUpperInventory();

    Inventory getLowerInventory();

    default DragController getDragController()
    {
        return this.getPlayer().getInventory().getDragController();
    }

    default boolean hasUpperInventory()
    {
        return this.getUpperInventory() != null;
    }

    default ItemStack setCursorItem(final ItemStack newCursor)
    {
        return this.getPlayer().getInventory().setCursorItem(newCursor);
    }

    default boolean replaceCursorItem(final ItemStack excepted, final ItemStack cursorItem)
    {
        return this.getPlayer().getInventory().replaceCursorItem(excepted, cursorItem);
    }

    /**
     * Checks if slot with specified ID is placed in upper inventory
     *
     * @param slotId slot ID to check
     * @return true if slots is in upper inventory
     */
    default boolean isUpperInventory(final int slotId)
    {
        return this.hasUpperInventory() && this.getUpperInventory().size() < slotId;

    }

    default int translateSlotToLowerInventory(final int slotId)
    {
        if (! this.hasUpperInventory())
        {
            return slotId;
        }
        return this.getUpperInventory().size() - slotId;
    }

    default Slot getSlot(final int slotId)
    {
        if (this.isUpperInventory(slotId))
        {
            return this.getUpperInventory().getSlot(slotId);
        }
        return this.getLowerInventory().getSlot(this.translateSlotToLowerInventory(slotId));
    }

    default ItemStack getItem(final int slotId)
    {
        if (this.isUpperInventory(slotId))
        {
            return this.getUpperInventory().getItem(slotId);
        }
        return this.getLowerInventory().getItem(this.translateSlotToLowerInventory(slotId));
    }

    default void setItem(final int slotId, final ItemStack item)
    {
        if (this.isUpperInventory(slotId))
        {
            this.getUpperInventory().setItem(slotId, item);
        }
        this.getLowerInventory().setItem(this.translateSlotToLowerInventory(slotId), item);
    }

    default boolean replace(final int slot, final ItemStack excepted, final ItemStack newItem)
    {
        if (this.isUpperInventory(slot))
        {
            return this.getUpperInventory().replace(slot, excepted, newItem);
        }
        else
        {
            return this.getLowerInventory().replace(this.translateSlotToLowerInventory(slot), excepted, newItem);
        }
    }
}
