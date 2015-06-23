package org.diorite.impl.pipelines.event.player;

import java.util.Objects;

import org.diorite.impl.connection.packets.play.out.PacketPlayOutTransaction;
import org.diorite.impl.entity.PlayerImpl;
import org.diorite.impl.inventory.PlayerInventoryImpl;
import org.diorite.event.pipelines.event.player.InventoryClickPipeline;
import org.diorite.event.player.PlayerInventoryClickEvent;
import org.diorite.inventory.ClickType;
import org.diorite.inventory.item.ItemStack;
import org.diorite.utils.pipeline.SimpleEventPipeline;

public class InventoryClickPipelineImpl extends SimpleEventPipeline<PlayerInventoryClickEvent> implements InventoryClickPipeline
{
    @Override
    public void reset_()
    {
        this.addLast("Diorite|Handle", (evt, pipeline) -> {
            if (evt.isCancelled())
            {
                ((PlayerImpl) evt.getPlayer()).getNetworkManager().sendPacket(new PacketPlayOutTransaction(evt.getWindowId(), evt.getActionNumber(), false));
                return;
            }
            evt.getPlayer().sendMessage(evt.toString());
            System.out.println(evt.toString());
            final boolean accepted = this.handleClick(evt);

            ((PlayerImpl) evt.getPlayer()).getNetworkManager().sendPacket(new PacketPlayOutTransaction(evt.getWindowId(), evt.getActionNumber(), accepted));

            if (! accepted)
            {
                System.out.println("Rejected inventory click action from player " + evt.getPlayer().getName());
            }
        });
    }

    protected boolean handleClick(final PlayerInventoryClickEvent e)
    {
        final PlayerImpl player = (PlayerImpl) e.getPlayer();
        final ClickType ct = e.getClickType();
        final ItemStack cursor = e.getCursorItem();
        final int slot = e.getClickedSlot();
        final PlayerInventoryImpl inv = player.getInventory();

        final ItemStack clicked = e.getClickedItem();
        if (Objects.equals(ct, ClickType.MOUSE_LEFT))
        {
            if (cursor == null)
            {
                if ((clicked != null) && (! inv.replace(slot, clicked, null) || ! inv.setCursorItem(null, clicked)))
                {
                    return false; // item changed before we made own change
                }
            }
            else
            {
                if (! inv.replace(slot, clicked, cursor) || ! inv.setCursorItem(cursor, clicked))
                {
                    return false; // item changed before we made own change
                }
            }
        }
        // TODO all other click types, and remember about throwing item on cursor to ground when closing eq
        else// if (Objects.equals(ct, ClickType.MOUSE))
        {
            return false;
        }

        return true;
    }
}
