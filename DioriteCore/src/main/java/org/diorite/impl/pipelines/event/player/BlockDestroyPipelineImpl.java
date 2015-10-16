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

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerBlockChange;
import org.diorite.impl.entity.EntityImpl;
import org.diorite.impl.entity.ItemImpl;
import org.diorite.impl.world.WorldImpl;
import org.diorite.GameMode;
import org.diorite.event.EventPriority;
import org.diorite.event.pipelines.event.player.BlockDestroyPipeline;
import org.diorite.event.player.PlayerBlockDestroyEvent;
import org.diorite.inventory.item.ItemStack;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.utils.pipeline.SimpleEventPipeline;
import org.diorite.world.Block;

public class BlockDestroyPipelineImpl extends SimpleEventPipeline<PlayerBlockDestroyEvent> implements BlockDestroyPipeline
{
    @Override
    public void reset_()
    {
        // TODO Drop item on ground, not to eq
        this.addBefore(EventPriority.NORMAL, "Diorite|RemoveBlock", (evt, pipeline) -> {
            if (evt.isCancelled())
            {
                return;
            }
            evt.getWorld().setBlock(evt.getLocation(), Material.AIR);
            DioriteCore.getInstance().getPlayersManager().forEach(p -> p.getWorld().equals(evt.getWorld()), new PacketPlayServerBlockChange(evt.getLocation(), Material.AIR));
        });

        /*this.addAfter(EventPriority.NORMAL, "Diorite|AddToEq", (evt, pipeline) ->
        {
            if (evt.isCancelled())
            {
                return;
            }
            evt.getPlayer().getInventory().getFullEqInventory().add(new ItemStack(evt.getBlock().getType()));
            evt.getPlayer().getInventory().update(); // TODO This shouldn't be needed
        });*/

        this.addAfter(EventPriority.NORMAL, "Diorite|DropItem", (evt, pipeline) -> {
            if (evt.isCancelled() || evt.getPlayer().getGameMode().equals(GameMode.CREATIVE))
            {
                return;
            }
            // TODO Drop naturally
            final Block block = evt.getBlock();
            final BlockMaterialData type = block.getType();
            for (final ItemStack itemStack : type.getPossibleDrops().simulateDrop(evt.getPlayer(), evt.getPlayer().getRandom(), evt.getItemInHand(), block))
            {
                final ItemImpl item = new ItemImpl(UUID.randomUUID(), (DioriteCore) this.getCore(), EntityImpl.getNextEntityID(), evt.getBlock().getLocation().addY(1).toLocation());
                item.setItemStack(itemStack);
                ((WorldImpl) evt.getBlock().getLocation().getWorld()).addEntity(item);
            }
        });
    }
}
