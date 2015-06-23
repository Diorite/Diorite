package org.diorite.impl.pipelines.event.player;

import org.diorite.impl.connection.packets.play.out.PacketPlayOutTransaction;
import org.diorite.impl.entity.PlayerImpl;
import org.diorite.event.EventPriority;
import org.diorite.event.pipelines.event.player.InventoryClickPipeline;
import org.diorite.event.player.PlayerInventoryClickEvent;
import org.diorite.inventory.ClickType;
import org.diorite.inventory.PlayerInventory;
import org.diorite.inventory.item.ItemStack;
import org.diorite.utils.pipeline.SimpleEventPipeline;

public class InventoryClickPipelineImpl extends SimpleEventPipeline<PlayerInventoryClickEvent> implements InventoryClickPipeline
{
    @Override
    public void reset_()
    {
        this.addBefore(EventPriority.NORMAL, "Diorite|HandleInv", (evt, pipeline) ->
        {
            if (evt.isCancelled())
            {
                return;
            }
            evt.getPlayer().sendMessage(evt.toString());
            final boolean accepted = this.handleClick(evt);

            ((PlayerImpl) evt.getPlayer()).getNetworkManager().sendPacket(new PacketPlayOutTransaction(evt.getWindowId(), evt.getActionNumber(), accepted));

            if (!accepted)
            {
                System.out.println("Rejected inventory click action from player " + evt.getPlayer().getName());
            }
        });
    }

    @SuppressWarnings("ObjectEquality")
    protected boolean handleClick(final PlayerInventoryClickEvent e)
    {
        final PlayerImpl player = (PlayerImpl) e.getPlayer();
        final ClickType ct = e.getClickType();
        final ItemStack cur = e.getPlayer().getCursor();
        final int slot = e.getClickedSlot();
        final PlayerInventory inv = player.getInventory();

        if (ct == ClickType.MOUSE_LEFT)
        {
            if (cur == null)
            {
                if (inv.getItem(slot) != null)
                {
                    player.setCursorItem(inv.getItem(slot));
                    inv.setItem(slot, null);
                }
            }
            else
            {
                if (inv.getItem(slot) == null)
                {
                    inv.setItem(slot, player.getCursor());
                    player.setCursorItem(null);
                }
                else
                {
                    final ItemStack temp = inv.getItem(slot);
                    inv.setItem(slot, player.getCursor());
                    player.setCursorItem(temp);
                }
            }
        }
        // TODO all other click types
        else
        {
            return false;
        }

        return true;
    }
}
