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

import org.diorite.impl.inventory.item.ItemStackImpl;
import org.diorite.impl.inventory.item.ItemStackImplArray;
import org.diorite.entity.Player;
import org.diorite.inventory.InventoryType;
import org.diorite.inventory.PlayerFullEqInventory;
import org.diorite.inventory.item.ItemStack;
import org.diorite.material.Material;

public class PlayerFullEqInventoryImpl extends PlayerInventoryPartImpl implements PlayerFullEqInventory
{
    public PlayerFullEqInventoryImpl(final PlayerInventoryImpl playerInventory)
    {
        super(playerInventory, playerInventory.getArray().getSubArray(9, InventoryType.PLAYER_FULL_EQ.getSize()));
    }

    public PlayerFullEqInventoryImpl(final PlayerInventoryImpl playerInventory, final ItemStackImplArray content)
    {
        super(playerInventory, content);
    }

    @Override
    public int firstEmpty()
    {
        return this.playerInventory.firstEmpty();
    }

    @Override
    public int first(final Material material)
    {
        return this.playerInventory.first(material);
    }

    @Override
    public int first(final ItemStack item, final boolean withAmount)
    {
        return this.playerInventory.first(item, withAmount);
    }

    @Override
    public int first(final ItemStack item, final int startIndex, final boolean withAmount)
    {
        return this.playerInventory.first(item, startIndex, withAmount);
    }

    @Override
    public ItemStack[] add(final ItemStack... items)
    {
        return this.playerInventory.add(items);
    }

    @Override
    public ItemStack getItemInHand()
    {
        final Player holder = this.getHolder();
        if (holder == null)
        {
            return null;
        }
        return this.content.get(holder.getHeldItemSlot());
    }

    @Override
    public ItemStack setItemInHand(final ItemStack stack)
    {
        final Player holder = this.getHolder();
        if (holder == null)
        {
            return null;
        }
        return this.content.getAndSet(holder.getHeldItemSlot(), ItemStackImpl.wrap(stack));
    }

    @Override
    public boolean replaceItemInHand(final ItemStack excepted, final ItemStack stack) throws IllegalArgumentException
    {
        ItemStackImpl.validate(excepted);
        final Player holder = this.getHolder();
        return (holder != null) && this.content.compareAndSet(holder.getHeldItemSlot(), (ItemStackImpl) excepted, ItemStackImpl.wrap(stack));
    }
}
