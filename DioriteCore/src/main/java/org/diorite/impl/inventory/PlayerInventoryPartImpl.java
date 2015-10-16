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

package org.diorite.impl.inventory;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.inventory.item.ItemStackImplArray;
import org.diorite.entity.Player;
import org.diorite.inventory.PlayerInventory;
import org.diorite.inventory.PlayerInventoryPart;
import org.diorite.inventory.slot.Slot;

public abstract class PlayerInventoryPartImpl extends InventoryImpl<Player> implements PlayerInventoryPart
{
    protected final PlayerInventoryImpl playerInventory;
    protected final ItemStackImplArray  content;

    protected PlayerInventoryPartImpl(final PlayerInventoryImpl playerInventory, final ItemStackImplArray content)
    {
        super(playerInventory.getHolder());
        this.content = content;
        Validate.notNull(playerInventory, "Base player inventory can't be null!");
        this.playerInventory = playerInventory;
    }

    @Override
    public Player getHolder()
    {
        return this.playerInventory.getHolder();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("playerInventory", this.playerInventory).toString();
    }

    @Override
    public PlayerInventory getPlayerInventory()
    {
        return this.playerInventory;
    }

    @Override
    public Slot getSlot(final int slot)
    {
        return this.playerInventory.getSlot(this.getSlotOffset() + slot);
    }

    @Override
    public ItemStackImplArray getArray()
    {
        return this.content;
    }

    @Override
    public void update(final Player player) throws IllegalArgumentException
    {
        this.playerInventory.update(player);
    }

    @Override
    public void update()
    {
        this.playerInventory.update();
    }

    @Override
    public int getSlotOffset()
    {
        return this.content.offset();
    }

    @Override
    public void softUpdate()
    {
        throw new UnsupportedOperationException("soft update should be called only for root EQ.");
    }
}
