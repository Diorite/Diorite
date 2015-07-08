package org.diorite.impl.pipelines.event.player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.diorite.impl.connection.packets.play.out.PacketPlayOutTransaction;
import org.diorite.impl.entity.EntityImpl;
import org.diorite.impl.entity.ItemImpl;
import org.diorite.impl.entity.PlayerImpl;
import org.diorite.impl.inventory.PlayerInventoryImpl;
import org.diorite.impl.inventory.item.ItemStackImpl;
import org.diorite.GameMode;
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
            //evt.getPlayer().sendMessage(evt.toString());
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
        final PlayerInventoryImpl inv = player.getInventory(); // TODO

        final ItemStackImpl clicked = ItemStackImpl.wrap(e.getClickedItem(), inv, slot);
        if (clicked != null)
        clicked.setAmount(e.getClickedItem().getAmount()); // FIXME
        if (Objects.equals(ct, ClickType.MOUSE_LEFT))
        {
            player.sendMessage("Mouse left clicked:" + clicked);
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

            // FIXME zbroja wskakuje na pola od armoru - tylko jesli sa wolne
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
            if (! Objects.equals(player.getGameMode(), GameMode.CREATIVE))
            {
                return true; // true? false?
            }

            if ((cursor != null) && (clicked != null))
            {
                return true;
            }

            final ItemStack newCursor = new ItemStack(clicked);
            newCursor.setAmount(64);
            return inv.atomicReplaceCursorItem(null, newCursor);
        }
        else if (Objects.equals(ct, ClickType.MOUSE_LEFT_DRAG_START) || Objects.equals(ct, ClickType.MOUSE_RIGHT_DRAG_START))
        {
            return inv.getDragController().start(ct.getButton() == ClickType.MOUSE_RIGHT_DRAG_START.getButton());
        }
        else if (Objects.equals(ct, ClickType.MOUSE_LEFT_DRAG_ADD) || Objects.equals(ct, ClickType.MOUSE_RIGHT_DRAG_ADD))
        {
            return inv.getDragController().addSlot(ct.getButton() == ClickType.MOUSE_RIGHT_DRAG_ADD.getButton(), slot);
        }
        else if (Objects.equals(ct, ClickType.MOUSE_LEFT_DRAG_END) || Objects.equals(ct, ClickType.MOUSE_RIGHT_DRAG_END))
        {
            final boolean isRightClick = ct.getButton() == ClickType.MOUSE_RIGHT_DRAG_END.getButton();
            final Collection<Integer> result = inv.getDragController().end(isRightClick);
            if ((cursor == null) || (result == null))
            {
                return false;
            }

            ItemStack newCursor = new ItemStack(cursor); // TODO do not clone :(

            final int perSlot = isRightClick ? 1 : (cursor.getAmount() / result.size());
            //player.sendMessage("Cursor " + newCursor);
            //player.sendMessage("Per slot "+perSlot);
            for (final int dragSlot : result)
            {
                final ItemStackImpl oldItem = inv.getItem(dragSlot);
                if ((oldItem == null) || cursor.isSimilar(oldItem))
                {
                    final ItemStack itemStackToCombine = new ItemStack(cursor);
                    itemStackToCombine.setAmount(perSlot);

                    int combined = 0;

                    if (oldItem != null)
                    {
                        final int uncombined = (oldItem.combine(itemStackToCombine) == null) ? 0 : oldItem.combine(itemStackToCombine).getAmount();
                        combined = perSlot - uncombined;
                    }
                    else
                    {
                        combined = perSlot;
                        inv.setItem(dragSlot, new ItemStack(newCursor.getMaterial(), combined)); // FIXME item lose metadata
                    }

                    //player.sendMessage("Slot combined amount: " + combined + "slot id: " + dragSlot);

                    newCursor.setAmount(newCursor.getAmount() - combined);
                    if (newCursor.getAmount() == 0)
                    {
                        newCursor = null;
                        break;
                    }
                }
            }

            //player.sendMessage("New cursor: " + newCursor);

            return inv.atomicReplaceCursorItem(cursor, newCursor);
        }
        // TODO remember about throwing item on cursor to ground when closing eq
        else
        {
            return false; // Action not supported
                          // TODO perhaps only DOUBLE_CLICK
        }

        return true;
    }

    protected int getAmountToStayInHand(final int amount)
    {
        return ((amount % 2) == 0) ? (amount / 2) : ((amount / 2) + 1);
    }
}
