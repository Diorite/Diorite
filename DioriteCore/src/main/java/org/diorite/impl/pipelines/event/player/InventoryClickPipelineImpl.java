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
    private static final int HOTBAR_BEGIN_ID = 36;

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
                if (cursor.isSimilar(clicked))
                {
                    final ItemStack newCursor = clicked.combine(cursor);

                    if (! inv.setCursorItem(cursor, newCursor))
                    {
                        return false;
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
        }
        else if (Objects.equals(ct, ClickType.MOUSE_RIGHT))
        {
            if (cursor == null)
            {
                if (clicked == null)
                {
                    return true;
                }

                final ItemStack splitted;

                if (clicked.getAmount() == 1)
                {
                    splitted = clicked;
                    if (! inv.replace(slot, clicked, null))
                    {
                        return false;
                    }
                }
                else
                {
                    splitted = clicked.split(this.getAmountToStayInHand(clicked.getAmount()));
                }

                if (splitted != null)
                {
                    if (! inv.setCursorItem(null, splitted))
                    {
                        return false;
                    }
                }
                else
                {
                    if (! inv.setCursorItem(null, clicked))
                    {
                        return false;
                    }
                }
            }
            else
            { // cursor != null
                if (clicked == null)
                {
                    final ItemStack splitted = cursor.split(1);
                    if (splitted == null)
                    {
                        if (! inv.replace(slot, null, cursor))
                        {
                            return false;
                        }
                    }
                    else
                    {
                        if (! inv.replace(slot, null, splitted))
                        {
                            return false;
                        }
                    }
                }
                else
                {
                    if (clicked.isSimilar(cursor))
                    {
                        if (! inv.setCursorItem(cursor, clicked.combine(cursor)))
                        {
                            return false;
                        }
                    }
                    else
                    {
                        if (! inv.replace(slot, clicked, cursor) || ! inv.setCursorItem(cursor, clicked))
                        {
                            return false;
                        }
                    }
                }
            } // end cursor != null
        } // end MOUSE_RIGHT
        else if (Objects.equals(ct, ClickType.SHIFT_MOUSE_LEFT) || Objects.equals(ct, ClickType.SHIFT_MOUSE_RIGHT))
        {
            if (clicked == null)
            {
                return true;
            }

            // TODO zbroja wskakuje na pola od armoru - tylko jesli sa wolne
            // TODO dopracowac to bo ledwo dziala

            if (slot >= HOTBAR_BEGIN_ID)
            {
                // clicked on hotbar
                if (! inv.replace(slot, clicked, null))
                {
                    return false;
                }
                inv.getFullEqInventory().add(clicked);
            }
            else
            {
                // clicked on other slot
                if (! inv.replace(slot, clicked, null))
                {
                    return false;
                }
                inv.getHotbarInventory().add(clicked);
            }
        }
        else if (Objects.equals(ct, ClickType.DROP_KEY))
        {
            if (clicked.getAmount() == 1)
            {
                if (! inv.replace(slot, clicked, null))
                {
                    return false;
                }
                // TODO wywalic itemek na ziemie
            }
            else
            {
                final ItemStack toDrop = clicked.split(1);
                // TODO wywalic itemek toDrop na ziemie
            }
        }
        else if (Objects.equals(ct, ClickType.CTRL_DROP_KEY))
        {
            if (! inv.replace(slot, clicked, null))
            {
                return false;
            }

            // TODO Gdy juz beda entities to wywalic itemek na ziemie. Aktualnie po prostu znika
        }
        else if (Objects.equals(ct, ClickType.NUM_KEY_1))
        {
            return this.handleNumKey(slot, inv, clicked, 1);
        }
        else if (Objects.equals(ct, ClickType.NUM_KEY_2))
        {
            return this.handleNumKey(slot, inv, clicked, 2);
        }
        else if (Objects.equals(ct, ClickType.NUM_KEY_3))
        {
            return this.handleNumKey(slot, inv, clicked, 3);
        }
        else if (Objects.equals(ct, ClickType.NUM_KEY_4))
        {
            return this.handleNumKey(slot, inv, clicked, 4);
        }
        else if (Objects.equals(ct, ClickType.NUM_KEY_5))
        {
            return this.handleNumKey(slot, inv, clicked, 5);
        }
        else if (Objects.equals(ct, ClickType.NUM_KEY_6))
        {
            return this.handleNumKey(slot, inv, clicked, 6);
        }
        else if (Objects.equals(ct, ClickType.NUM_KEY_7))
        {
            return this.handleNumKey(slot, inv, clicked, 7);
        }
        else if (Objects.equals(ct, ClickType.NUM_KEY_8))
        {
            return this.handleNumKey(slot, inv, clicked, 8);
        }
        else if (Objects.equals(ct, ClickType.NUM_KEY_9))
        {
            return this.handleNumKey(slot, inv, clicked, 9);
        }
        // TODO all other click types, and remember about throwing item on cursor to ground when closing eq
        else
        {
            return false; // Action not supported
        }

        return true;
    }

    protected int getAmountToStayInHand(final int amount)
    {
        return ((amount % 2) == 0) ? (amount / 2) : ((amount / 2) + 1);
    }

    protected boolean handleNumKey(final int slot, final PlayerInventoryImpl inv, final ItemStack clicked, int id)
    {
        id -= 1;
        final ItemStack inHeldSlot = inv.getHotbarInventory().getItem(id);
        return inv.replace(slot, clicked, inHeldSlot) && inv.getHotbarInventory().replace(id, inHeldSlot, clicked);
    }
}
