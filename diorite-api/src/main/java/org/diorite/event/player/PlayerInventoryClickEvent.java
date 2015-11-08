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

package org.diorite.event.player;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Player;
import org.diorite.inventory.ClickType;
import org.diorite.inventory.item.ItemStack;

public class PlayerInventoryClickEvent extends PlayerEvent
{
    protected final int       windowId;
    protected final int       clickedSlot;
    protected final short     actionNumber;
    protected final ClickType clickType;

    protected final ItemStack clickedItem;
    protected final ItemStack cursorItem;

    /**
     * Construct new player event.
     *
     * @param player       player related to event, can't be null.
     * @param actionNumber action number send by client.
     * @param windowId     id of inventory window.
     * @param clickedSlot  clicked slot number.
     * @param clickType    type of click.
     */
    public PlayerInventoryClickEvent(final Player player, final short actionNumber, final int windowId, final int clickedSlot, final ClickType clickType)
    {
        super(player);
        this.windowId = windowId;
        this.clickedSlot = clickedSlot;
        this.actionNumber = actionNumber;
        this.clickType = clickType;

        ItemStack clickedItem;
        try
        {
            clickedItem = player.getInventory().getItem(clickedSlot);
        } catch (final IndexOutOfBoundsException ignored)
        {
            clickedItem = null;
        }

        this.clickedItem = clickedItem;
        this.cursorItem = player.getInventory().getCursorItem();
    }

    public int getWindowId()
    {
        return this.windowId;
    }

    public int getClickedSlot()
    {
        return this.clickedSlot;
    }

    public short getActionNumber()
    {
        return this.actionNumber;
    }

    public ClickType getClickType()
    {
        return this.clickType;
    }

    public ItemStack getClickedItem()
    {
        return this.clickedItem;
    }

    public ItemStack getCursorItem()
    {
        return this.cursorItem;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("windowId", this.windowId).append("clickedSlot", this.clickedSlot).append("actionNumber", this.actionNumber).append("clickType", this.clickType).toString();
    }
}
