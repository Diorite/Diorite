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

public interface PlayerInventoryPart extends Inventory
{
    /**
     * @return player inventory with this part.
     */
    PlayerInventory getPlayerInventory();

    /**
     * Gets the player belonging to the open inventory
     *
     * @return The holder of the inventory; null if it has no holder.
     */
    @Override
    Player getHolder();

    @Override
    default int getWindowId()
    {
        return this.getPlayerInventory().getWindowId();
    }

    /**
     * Returns offset of player inventory part. <br>
     * Always 0 for root inventory.
     *
     * @return Offset of player inventory part
     */
    int getSlotOffset();

    /**
     * Returns offset of player inventory part relative to given part. <br>
     * May return negative number if used on part that don't contains given part.
     *
     * @param part part to get offset.
     *
     * @return Offset of player inventory part relative to given part
     */
    default int getSlotOffset(final PlayerInventoryPart part)
    {
        return part.getSlotOffset() - this.getSlotOffset();
    }
}