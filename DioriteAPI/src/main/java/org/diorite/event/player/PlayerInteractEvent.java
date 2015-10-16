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
import org.diorite.world.Block;

/**
 * Fired when player interact
 */
public class PlayerInteractEvent extends PlayerEvent
{
    protected final Action action;
    protected final Block  block;

    /**
     * Construct new player interact event.
     *
     * @param player player related to event, can't be null.
     * @param action action that has been done
     * @param block  block that has been clicked or null if the block is air
     */
    public PlayerInteractEvent(final Player player, final Action action, final Block block)
    {
        super(player);
        this.action = action;
        this.block = block;
    }

    /**
     * Returns an action that has been done
     *
     * @return an action enum
     */
    public Action getAction()
    {
        return this.action;
    }

    /**
     * @return targeted block or null if the block is air
     */
    public Block getBlock()
    {
        return this.block;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("block", this.block).append("action", this.action).toString();
    }

    /**
     * An enum that describing an action that has been done
     */
    public enum Action
    {
        LEFT_CLICK_ON_BLOCK,
        LEFT_CLICK_ON_AIR,
        RIGHT_CLICK_ON_BLOCK,
        RIGHT_CLICK_ON_AIR
    }
}
