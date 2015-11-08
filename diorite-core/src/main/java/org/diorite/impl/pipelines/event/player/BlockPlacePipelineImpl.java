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

package org.diorite.impl.pipelines.event.player;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerBlockChange;
import org.diorite.event.pipelines.event.player.BlockPlacePipeline;
import org.diorite.event.player.PlayerBlockPlaceEvent;
import org.diorite.inventory.item.ItemStack;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.pipeline.SimpleEventPipeline;

public class BlockPlacePipelineImpl extends SimpleEventPipeline<PlayerBlockPlaceEvent> implements BlockPlacePipeline
{
    @Override
    public void reset_()
    {
        this.addLast("Diorite|PlaceBlock", (evt, pipeline) -> {
            if (evt.isCancelled())
            {
                return;
            }
            final ItemStack item = evt.getPlayer().getInventory().getItemInHand();
            if (item == null)
            {
                evt.setCancelled(true);
                return;
            }

            if (!(item.getMaterial() instanceof BlockMaterialData)) // TODO check if material can be placed
            {
                return;
            }

            if (item.getAmount() == 1)
            {
                evt.getPlayer().getInventory().getHotbarInventory().replace(evt.getPlayer().getHeldItemSlot(), item, null);
            }
            else
            {
                item.setAmount(item.getAmount() - 1);
            }

            evt.getBlock().setType((BlockMaterialData) item.getMaterial());
            DioriteCore.getInstance().getPlayersManager().forEach(p -> p.getWorld().equals(evt.getBlock().getWorld()), new PacketPlayServerBlockChange(evt.getBlock().getLocation(), evt.getBlock().getType()));
        });
    }
}
