package org.diorite.impl.pipelines.event.player;

import java.util.Objects;
import java.util.UUID;

import org.diorite.impl.connection.packets.play.out.PacketPlayOutTransaction;
import org.diorite.impl.entity.EntityImpl;
import org.diorite.impl.entity.ItemImpl;
import org.diorite.impl.entity.PlayerImpl;
import org.diorite.impl.inventory.PlayerInventoryImpl;
import org.diorite.impl.inventory.item.ItemStackImpl;
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
        final ItemStackImpl cursor = ItemStackImpl.wrap(e.getCursorItem(), 0);
        final int slot = e.getClickedSlot();
        final PlayerInventoryImpl inv = player.getInventory();

        final ItemStackImpl clicked = ItemStackImpl.wrap(e.getClickedItem(), inv, slot);
        if (Objects.equals(ct, ClickType.MOUSE_LEFT))
        {
            if (cursor == null)
            {
                if ((clicked != null) && (! inv.atomicReplace(slot, clicked, null) || ! inv.atomicReplaceCursorItem(null, clicked)))
                {
                    return false; // item changed before we made own change
                }
            }
            else
            {
                if (cursor.isSimilar(clicked))
                {
                    final ItemStack newCursor = clicked.combine(cursor);

                    if (! inv.atomicReplaceCursorItem(cursor, newCursor))
                    {
                        return false;
                    }
                }
                else
                {
                    if (slot == 0) // result slot, TODO: slot type, finish for other clicks
                    {
                        return true;
                    }
                    if (! inv.atomicReplace(slot, clicked, cursor) || ! inv.atomicReplaceCursorItem(cursor, clicked))
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
                    if (! inv.atomicReplace(slot, clicked, null))
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
                    if (! inv.atomicReplaceCursorItem(null, splitted))
                    {
                        return false;
                    }
                }
                else
                {
                    if (! inv.atomicReplaceCursorItem(null, clicked))
                    {
                        return false;
                    }
                }
            }
            else // cursor != null
            {
                if (clicked == null)
                {
                    final ItemStack splitted = cursor.split(1);
                    if (splitted == null)
                    {
                        if (! inv.atomicReplace(slot, null, cursor))
                        {
                            return false;
                        }
                    }
                    else
                    {
                        if (! inv.atomicReplace(slot, null, splitted))
                        {
                            return false;
                        }
                    }
                }
                else
                {
                    if (clicked.isSimilar(cursor))
                    {
                        if (! inv.atomicReplaceCursorItem(cursor, clicked.combine(cursor)))
                        {
                            return false;
                        }
                    }
                    else
                    {
                        if (! inv.atomicReplace(slot, clicked, cursor) || ! inv.atomicReplaceCursorItem(cursor, clicked))
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
                if (! inv.atomicReplace(slot, clicked, null))
                {
                    return false;
                }
                inv.getFullEqInventory().add(clicked);
            }
            else
            {
                // clicked on other slot
                if (! inv.atomicReplace(slot, clicked, null))
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
                if (! inv.atomicReplace(slot, clicked, null))
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
            if (! inv.atomicReplace(slot, clicked, null))
            {
                return false;
            }

            // TODO Gdy juz beda entities to wywalic itemek na ziemie. Aktualnie po prostu znika
        }
        else if (ct.getMode() == 2) // 2 -> hot bar action
        {
            if ((ct.getButton() < 0) || (ct.getButton() > 8))
            {
                return false;
            }
            final ItemStack inHeldSlot = inv.getHotbarInventory().getItem(ct.getButton());
            return inv.atomicReplace(slot, clicked, inHeldSlot) && inv.getHotbarInventory().atomicReplace(ct.getButton(), inHeldSlot, clicked);
        }
        else if (Objects.equals(ct, ClickType.MOUSE_LEFT_OUTSIDE))
        {
            if ((cursor == null) || (cursor.getAmount() <= 0))
            {
                inv.setCursorItem(null);
                return true;
            }
            if (! inv.atomicReplaceCursorItem(cursor, null))
            {
                return false;
            }
            final ItemImpl item = new ItemImpl(UUID.randomUUID(), player.getServer(), EntityImpl.getNextEntityID(), player.getLocation().addX(2));  // TODO:velocity + some .spawnEntity method
            item.setItemStack(new ItemStack(cursor.getMaterial(), cursor.getAmount()));
            player.getWorld().addEntity(item);
            return true;
        }
        else if (Objects.equals(ct, ClickType.MOUSE_RIGHT_OUTSIDE))
        {
            if ((cursor == null) || (cursor.getAmount() <= 0))
            {
                inv.setCursorItem(null);
                return true;
            }
            final ItemImpl item = new ItemImpl(UUID.randomUUID(), player.getServer(), EntityImpl.getNextEntityID(), player.getLocation().addX(2));  // TODO:velocity + some .spawnEntity method
            item.setItemStack(new ItemStack(cursor.getMaterial(), 1));
            if (cursor.getAmount() == 1)
            {
                if (! inv.atomicReplaceCursorItem(cursor, null))
                {
                    return false;
                }
                player.getWorld().addEntity(item);
                return true;
            }
            cursor.setAmount(cursor.getAmount() - 1);
            player.getWorld().addEntity(item);
            return true;
        }
        else if (Objects.equals(ct, ClickType.MOUSE_MIDDLE))
        {
            // TODO Dowiedziec sie co to robi, ale prawdopodobnie jest to sensowne tylko na creative
            return true;
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
}
