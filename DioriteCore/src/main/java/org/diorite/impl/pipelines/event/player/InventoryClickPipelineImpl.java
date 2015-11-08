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

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

import org.diorite.impl.connection.packets.play.server.PacketPlayServerTransaction;
import org.diorite.impl.entity.EntityImpl;
import org.diorite.impl.entity.ItemImpl;
import org.diorite.impl.entity.PlayerImpl;
import org.diorite.impl.inventory.PlayerInventoryImpl;
import org.diorite.impl.inventory.item.ItemStackImpl;
import org.diorite.GameMode;
import org.diorite.event.pipelines.event.player.InventoryClickPipeline;
import org.diorite.event.player.PlayerInventoryClickEvent;
import org.diorite.inventory.ClickType;
import org.diorite.inventory.Inventory;
import org.diorite.inventory.item.BaseItemStack;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.slot.Slot;
import org.diorite.inventory.slot.SlotType;
import org.diorite.material.items.ArmorMat;
import org.diorite.utils.pipeline.SimpleEventPipeline;

@SuppressWarnings("ObjectEquality")
public class InventoryClickPipelineImpl extends SimpleEventPipeline<PlayerInventoryClickEvent> implements InventoryClickPipeline
{
    private static final int HOTBAR_BEGIN_ID = 36;

    @Override
    public void reset_()
    {
        this.addLast("Diorite|Handle", (evt, pipeline) -> {
            if (evt.isCancelled())
            {
                ((PlayerImpl) evt.getPlayer()).getNetworkManager().sendPacket(new PacketPlayServerTransaction(evt.getWindowId(), evt.getActionNumber(), false));
                return;
            }
//            System.out.println(evt.toString());
            final boolean accepted = this.handleClick(evt);

            ((PlayerImpl) evt.getPlayer()).getNetworkManager().sendPacket(new PacketPlayServerTransaction(evt.getWindowId(), evt.getActionNumber(), accepted));

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

        final Slot slotProp = inv.getSlot(slot);
        try
        {
            if (Objects.equals(ct, ClickType.MOUSE_LEFT))
            {
                if (slot == - 1) // click in non-slot place, like inventory border.
                {
                    return true;
                }
                if (cursor == null)
                {
                    if ((clicked != null) && (! inv.replace(slot, clicked, null) || ! inv.replaceCursorItem(null, clicked)))
                    {
                        return false; // item changed before we made own change
                    }
                }
                else
                {
                    if (cursor.isSimilar(clicked))
                    {
                        final ItemStack newCursor = clicked.combine(cursor);

                        if (! inv.replaceCursorItem(cursor, newCursor))
                        {
                            return false;
                        }
                    }
                    else
                    {
                        final ItemStack item = slotProp.canHoldItem(cursor);
                        if ((item == null) && (item != cursor))
                        {
                            return true;
                        }
                        else if (! inv.replace(slot, clicked, cursor) || ! inv.replaceCursorItem(cursor, clicked))
                        {
                            return false; // item changed before we made own change
                        }
                    }
                }
            }
            else if (Objects.equals(ct, ClickType.MOUSE_RIGHT))
            {
                if (slot == - 1) // click in non-slot place, like inventory border.
                {
                    return true;
                }
                if (cursor == null)
                {
                    if (clicked == null)
                    {
                        return true;
                    }
                    final ItemStack newCursor = clicked.split(this.getAmountToStayInHand(clicked.getAmount()));
                    if (clicked.getAmount() == 0)
                    {
                        if (! inv.replace(slot, clicked, null))
                        {
                            return false;
                        }
                    }
                    if (! inv.replaceCursorItem(/*cursor*/ null, newCursor))
                    {
                        return false;
                    }
                }
                else // cursor != null
                {
                    if (clicked == null)
                    {
                        final ItemStack item = slotProp.canHoldItem(cursor);
                        if (item == null)
                        {
                            return true;
                        }
                        final ItemStack splitted = cursor.split(1);
                        if (cursor.getAmount() == 0)
                        {
                            if (! inv.replaceCursorItem(cursor, null))
                            {
                                return false;
                            }
                        }

                        if (! inv.replace(slot,/*clicked*/ null, splitted))
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

                            final ItemStack temp = new BaseItemStack(clicked);
                            temp.setAmount(temp.getAmount() + 1);
                            final ItemStack item = slotProp.canHoldItem(temp);
                            if (temp != item) // this slot can't hold more items as canHold returned other stack than given when used bigger stack.
                            {
                                return true;
                            }
                            final ItemStack rest = clicked.addFrom(cursor, 1);
                            if (cursor.getAmount() == 0)
                            {
                                if (! inv.replaceCursorItem(cursor, null))
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
                            final ItemStack item = slotProp.canHoldItem(cursor);
                            if ((item == null) && (item != cursor))
                            {
                                return true;
                            }
                            else if (! inv.replace(slot, clicked, cursor) || ! inv.replaceCursorItem(cursor, clicked))
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

                final ItemStack[] rest;

                if ((slotProp.getSlotType().equals(SlotType.CONTAINER) || slotProp.getSlotType().equals(SlotType.HOTBAR)) && (clicked.getMaterial() instanceof ArmorMat))
                {
                    return ! ((ArmorMat) clicked.getMaterial()).getArmorType().setItem(inv, clicked) || inv.replace(slot, clicked, null);
                }
                if (slotProp.getSlotType().equals(SlotType.CONTAINER))
                {
                    // clicked on other slot
                    rest = inv.getHotbarInventory().add(clicked);
                }
                else
                {
                    // clicked on hotbar
                    rest = inv.getEqInventory().add(clicked);
                }
                if (rest.length == 0)
                {
                    if (! inv.replace(slot, clicked, null))
                    {
                        return false;
                    }
                }
                else
                {
                    clicked.setAmount(rest[0].getAmount());
                }
            }
            else if (Objects.equals(ct, ClickType.DROP_KEY))
            {
                if (clicked == null)
                {
                    return true;
                }
                if (clicked.getAmount() == 1)
                {
                    if (! inv.replace(slot, clicked, null))
                    {
                        return false;
                    }
                    final ItemImpl item = new ItemImpl(UUID.randomUUID(), player.getCore(), EntityImpl.getNextEntityID(), player.getLocation().addX(2));  // TODO:velocity + some .spawnEntity method
                    item.setItemStack(new BaseItemStack(clicked));
                    player.getWorld().addEntity(item);
                }
                else
                {
                    final ItemStack toDrop = clicked.split(1);
                    final ItemImpl item = new ItemImpl(UUID.randomUUID(), player.getCore(), EntityImpl.getNextEntityID(), player.getLocation().addX(2));  // TODO:velocity + some .spawnEntity method
                    item.setItemStack(new BaseItemStack(toDrop));
                    player.getWorld().addEntity(item);
                }
            }
            else if (Objects.equals(ct, ClickType.CTRL_DROP_KEY))
            {
                if (! inv.replace(slot, clicked, null))
                {
                    return false;
                }
                final ItemImpl item = new ItemImpl(UUID.randomUUID(), player.getCore(), EntityImpl.getNextEntityID(), player.getLocation().addX(2));  // TODO:velocity + some .spawnEntity method
                item.setItemStack(new BaseItemStack(clicked));
                player.getWorld().addEntity(item);
            }
            else if (ct.getMode() == 2) // 2 -> hot bar action
            {
                if ((ct.getButton() < 0) || (ct.getButton() > 8))
                {
                    return false;
                }
                final ItemStack inHeldSlot = inv.getHotbarInventory().getItem(ct.getButton());
                return inv.replace(slot, clicked, inHeldSlot) && inv.getHotbarInventory().replace(ct.getButton(), inHeldSlot, clicked);
            }
            else if (Objects.equals(ct, ClickType.MOUSE_LEFT_OUTSIDE))
            {
                if ((cursor == null) || (cursor.getAmount() <= 0))
                {
                    inv.setCursorItem(null);
                    return true;
                }
                if (! inv.replaceCursorItem(cursor, null))
                {
                    return false;
                }
                final ItemImpl item = new ItemImpl(UUID.randomUUID(), player.getCore(), EntityImpl.getNextEntityID(), player.getLocation().addX(2));  // TODO:velocity + some .spawnEntity method
                item.setItemStack(new BaseItemStack(cursor));
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
                final ItemImpl item = new ItemImpl(UUID.randomUUID(), player.getCore(), EntityImpl.getNextEntityID(), player.getLocation().addX(2));  // TODO:velocity + some .spawnEntity method
                final ItemStack it = new BaseItemStack(cursor.getMaterial(), 1);
                it.setItemMeta(cursor.getItemMeta().clone());
                item.setItemStack(it);
                if (cursor.getAmount() == 1)
                {
                    if (! inv.replaceCursorItem(cursor, null))
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
                    return true;
                }

                if ((cursor != null) && (clicked != null))
                {
                    return true;
                }

                final ItemStack newCursor = new BaseItemStack(clicked);
                newCursor.setAmount(newCursor.getMaterial().getMaxStack());
                return inv.replaceCursorItem(null, newCursor);
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
                            final ItemStack it = new BaseItemStack(newCursor.getMaterial(), combined);
                            it.setItemMeta(newCursor.getItemMeta().clone());
                            inv.setItem(dragSlot, it);
                        }

                        newCursor.setAmount(newCursor.getAmount() - combined);
                        if (newCursor.getAmount() == 0)
                        {
                            newCursor = null;
                            break;
                        }
                    }
                }

                return inv.replaceCursorItem(cursor, newCursor);
            }
            else if (Objects.equals(ct, ClickType.DOUBLE_CLICK))
            {
                if (cursor == null)
                {
                    return true;
                }

                final int slots = 44;

                int firstFullSlot = - 1; // minecraft skip full stacks and use them only if there is no smaller ones
                for (int i = 0; (i < slots) && (cursor.getAmount() < cursor.getMaterial().getMaxStack()); i++)
                {
                    final ItemStack item = inv.getItem(i);
                    if ((item == null) || ! cursor.isSimilar(item) /* || slot type == RESULT */)
                    {
                        continue;
                    }
                    if (item.getAmount() >= item.getMaterial().getMaxStack())
                    {
                        if (firstFullSlot == - 1)
                        {
                            firstFullSlot = i;
                        }
                        continue;
                    }

                    if (! doubleClick(inv, i, cursor))
                    {
                        return false;
                    }
                }
                if ((firstFullSlot != - 1) && (cursor.getAmount() < cursor.getMaterial().getMaxStack()))
                {
                    if (! doubleClick(inv, firstFullSlot, cursor))
                    {
                        return false;
                    }
                }
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
                clicked.setDirty();
            }
            if (cursor != null)
            {
                cursor.setDirty();
            }
        }
    }

    private static boolean doubleClick(final Inventory inv, final int slot, final ItemStack cursor)
    {
        final ItemStack item = inv.getItem(slot);
        final int newCursor = cursor.getAmount() + Math.min(item.getAmount(), cursor.getMaterial().getMaxStack() - cursor.getAmount());
        final int newItem = item.getAmount() - (newCursor - cursor.getAmount());
        cursor.setAmount(newCursor);
        if (newItem <= 0)
        {
            if (! inv.replace(slot, item, null))
            {
                return false;
            }
        }
        else
        {
            item.setAmount(newItem);
        }
        return true;
    }

    protected int getAmountToStayInHand(final int amount)
    {
        return ((amount % 2) == 0) ? (amount / 2) : ((amount / 2) + 1);
    }
}
