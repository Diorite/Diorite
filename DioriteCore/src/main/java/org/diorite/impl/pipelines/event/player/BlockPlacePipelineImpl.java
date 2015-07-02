package org.diorite.impl.pipelines.event.player;

import org.diorite.impl.ServerImpl;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutBlockChange;
import org.diorite.event.EventPriority;
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
        this.addBefore(EventPriority.NORMAL, "Diorite|PlaceBlock", (evt, pipeline) ->
        {
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

            if (item.getAmount() == 1)
            {
                evt.getPlayer().getInventory().getHotbarInventory().atomicReplace(evt.getPlayer().getHeldItemSlot(), item, null);
            }
            else
            {
                item.setAmount(item.getAmount() - 1);
            }

            evt.getBlock().setType((BlockMaterialData) item.getMaterial());
            ServerImpl.getInstance().getPlayersManager().forEach(p -> p.getWorld().equals(evt.getBlock().getWorld()), new PacketPlayOutBlockChange(evt.getBlock().getLocation(), evt.getBlock().getType()));
        });
    }
}
