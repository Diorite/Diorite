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

import org.diorite.BlockLocation;
import org.diorite.entity.Player;
import org.diorite.inventory.item.ItemStack;
import org.diorite.world.Block;
import org.diorite.world.World;

public class PlayerBlockDestroyEvent extends PlayerEvent
{
    protected final ItemStack itemInHand;
    protected       Block     block;

    public PlayerBlockDestroyEvent(final Player player, final Block block)
    {
        super(player);
        this.block = block;
        final ItemStack item = player.getInventory().getItemInHand();
        this.itemInHand = (item == null) ? null : item.clone();
    }

    public Block getBlock()
    {
        return this.block;
    }

    public void setBlock(final Block block)
    {
        this.block = block;
    }

    public BlockLocation getLocation()
    {
        return this.block.getLocation();
    }

    public World getWorld()
    {
        return this.block.getLocation().getWorld();
    }

    /**
     * Returns clone of ItemStack used when destroying block.
     *
     * @return clone of ItemStack used when destroying block.
     */
    public ItemStack getItemInHand()
    {
        return this.itemInHand;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("block", this.block).toString();
    }
}
