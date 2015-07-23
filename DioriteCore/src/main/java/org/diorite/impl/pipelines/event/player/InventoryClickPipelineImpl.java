package org.diorite.impl.pipelines.event.player;

import java.util.Collection;
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
import org.diorite.inventory.item.BaseItemStack;
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
        ItemStackImpl.validate(e.getCursorItem());
        final ItemStackImpl cursor = (ItemStackImpl) e.getCursorItem();
        final int slot = e.getClickedSlot();
        final PlayerInventoryImpl inv = player.getInventory(); // TODO

        final ItemStackImpl clicked = ItemStackImpl.wrap(e.getClickedItem());
        try
        {
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
                    final ItemStack newCursor = clicked.split(this.getAmountToStayInHand(clicked.getAmount()));
                    if (clicked.getAmount() == 0)
                    {
                        if (! inv.atomicReplace(slot, clicked, null))
                        {
                            return false;
                        }
                    }
                    if (! inv.atomicReplaceCursorItem(/*cursor*/ null, newCursor))
                    {
                        return false;
                    }
                }
                else // cursor != null
                {
                    if (clicked == null)
                    {
                        final ItemStack splitted = cursor.split(1);
                        if (cursor.getAmount() == 0)
                        {
                            if (! inv.atomicReplaceCursorItem(cursor, null))
                            {
                                return false;
                            }
                        }

                        if (! inv.atomicReplace(slot,/*clicked*/ null, splitted))
                        {
                            return false;
                        }
                    }
                    else // clicked != null
                    {
                        if (clicked.isSimilar(cursor))
                        {
                            if (clicked.getAmount() >= clicked.getMaterial().getMaxStack())
                            {
                                return true; // no place to add more items.
                            }
                            final ItemStack rest = clicked.addFrom(cursor, 1);
                            if (cursor.getAmount() == 0)
                            {
                                if (! inv.atomicReplaceCursorItem(cursor, null))
                                {
                                    return false;
                                }
                            }
                            if (rest != null)
                            {
                                return false; // it should never happen as we are only adding 1 item.
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
                item.setItemStack(new BaseItemStack(cursor.getMaterial(), cursor.getAmount()));
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
                item.setItemStack(new BaseItemStack(cursor.getMaterial(), 1));
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

                final ItemStack newCursor = new BaseItemStack(clicked);
                newCursor.setAmount(newCursor.getMaterial().getMaxStack());
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

                ItemStack newCursor = new BaseItemStack(cursor);

                final int perSlot = isRightClick ? 1 : (cursor.getAmount() / result.size());
                for (final int dragSlot : result)
                {
                    final ItemStackImpl oldItem = inv.getItem(dragSlot);
                    if ((oldItem == null) || cursor.isSimilar(oldItem))
                    {
                        final ItemStack itemStackToCombine = new BaseItemStack(cursor);
                        itemStackToCombine.setAmount(perSlot);

                        final int combined;

                        if (oldItem != null)
                        {
                            final ItemStackImpl uncomb = oldItem.combine(itemStackToCombine);
                            combined = perSlot - ((uncomb == null) ? 0 : uncomb.getAmount());
                        }
                        else
                        {
                            combined = perSlot;
                            inv.setItem(dragSlot, new BaseItemStack(newCursor.getMaterial(), combined)); // FIXME item lose metadata
                        }

                        newCursor.setAmount(newCursor.getAmount() - combined);
                        if (newCursor.getAmount() == 0)
                        {
                            newCursor = null;
                            break;
                        }
                    }
                }

                return inv.atomicReplaceCursorItem(cursor, newCursor);
            }

            // TODO remember about throwing item on cursor to ground when closing eq
            else
            {
                return false; // Action not supported
            }
            return true;
        } finally
        {
            if (clicked != null)
            {
                clicked.setClean();
            }
            if (cursor != null)
            {
                cursor.setClean();
            }
        }
    }

    protected int getAmountToStayInHand(final int amount)
    {
        return ((amount % 2) == 0) ? (amount / 2) : ((amount / 2) + 1);
    }
}
