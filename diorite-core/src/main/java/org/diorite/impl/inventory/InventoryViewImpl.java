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

package org.diorite.impl.inventory;

import org.diorite.impl.entity.IPlayer;
import org.diorite.block.BlockContainer;
import org.diorite.inventory.Inventory;
import org.diorite.inventory.InventoryType;
import org.diorite.inventory.InventoryView;

public class InventoryViewImpl implements InventoryView
{
    private final IPlayer   player;
    private final Inventory upperInventory;

    public InventoryViewImpl(final IPlayer player)
    {
        this.player = player;
        this.upperInventory = null;
    }

    public InventoryViewImpl(final IPlayer player, final BlockContainer blockContainer)
    {
        this.player = player;
        this.upperInventory = blockContainer.getInventory();
    }

    public InventoryViewImpl(final IPlayer player, final Inventory inventory)
    {
        this.player = player;
        this.upperInventory = inventory;
    }

    @Override
    public int getId()
    {
        if (this.hasUpperInventory())
        {
            return this.getUpperInventory().getWindowId();
        }
        return 0; // This inventory view represents only player inventory (closed or opened)
    }

    @Override
    public InventoryType getType()
    {
        if (this.upperInventory == null)
        {
            return InventoryType.PLAYER;
        }
        return this.upperInventory.getType();
    }

    @Override
    public IPlayer getPlayer()
    {
        return this.player;
    }

    @Override
    public Inventory getUpperInventory()
    {
        return this.upperInventory;
    }

    @Override
    public Inventory getLowerInventory()
    {
        if (this.hasUpperInventory())
        {
            return this.getPlayer().getInventory().getFullEqInventory();
        }
        return this.getPlayer().getInventory();
    }
}
