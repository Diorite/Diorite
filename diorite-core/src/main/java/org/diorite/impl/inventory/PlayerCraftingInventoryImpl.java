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

package org.diorite.impl.inventory;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerSetSlot;
import org.diorite.impl.entity.EntityImpl;
import org.diorite.impl.entity.ItemImpl;
import org.diorite.impl.inventory.item.ItemStackImpl;
import org.diorite.impl.inventory.item.ItemStackImplArray;
import org.diorite.Diorite;
import org.diorite.inventory.InventoryType;
import org.diorite.inventory.PlayerCraftingInventory;
import org.diorite.inventory.item.BaseItemStack;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.RecipeManager;
import org.diorite.inventory.recipe.craft.Recipe;
import org.diorite.inventory.recipe.craft.RecipeCheckResult;
import org.diorite.utils.DioriteUtils;

import gnu.trove.TShortCollection;
import gnu.trove.iterator.TShortIterator;
import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;
import gnu.trove.set.hash.TShortHashSet;

public class PlayerCraftingInventoryImpl extends PlayerInventoryPartImpl implements PlayerCraftingInventory
{
    protected PlayerCraftingInventoryImpl(final PlayerInventoryImpl playerInventory)
    {
        super(playerInventory, playerInventory.getArray().getSubArray(0, InventoryType.PLAYER_CRAFTING.getSize()));
    }

    public PlayerCraftingInventoryImpl(final PlayerInventoryImpl playerInventory, final ItemStackImplArray content)
    {
        super(playerInventory, content);
    }

    @Override
    public ItemStack getResult()
    {
        return this.content.get(0);
    }

    @Override
    public ItemStack setResult(final ItemStack result)
    {
        return this.content.getAndSet(0, ItemStackImpl.wrap(result));
    }

    @Override
    public boolean replaceResult(final ItemStack excepted, final ItemStack result)
    {
        ItemStackImpl.validate(excepted);
        return this.content.compareAndSet(0, (ItemStackImpl) excepted, ItemStackImpl.wrap(result));
    }

    @Override
    public ItemStack[] getCraftingSlots()
    {
        return this.content.getSubArray(1).toArray(new ItemStack[this.content.length() - 1]);
    }

    private transient RecipeCheckResult recipe;

    @SuppressWarnings("ObjectEquality")
    public void confirmRecipe(final boolean all)
    {
        if (this.recipe == null)
        {
            return;
        }

        // fix ghost items (custom recipes), meh mojang...
        final TShortCollection possibleBugs;
        if (! this.recipe.getRecipe().isVanilla())
        {
            final ItemStack[] items = DioriteUtils.compact(false, this.recipe.getItemsToConsume());
            possibleBugs = new TShortHashSet(items.length);
            short k = 0;
            short bugSlot;
            short startIndex = - 1;
            do
            {
                bugSlot = (short) this.playerInventory.firstNotFull(items[k].getMaterial());
                if (bugSlot != - 1)
                {
                    short bugSlot2 = (short) this.playerInventory.firstNotFull(items[k]);
                    if (bugSlot != bugSlot2)
                    {
                        if (bugSlot2 != - 1)
                        {
                            possibleBugs.add(bugSlot2);
                        }
                        else
                        {
                            bugSlot2 = (short) this.playerInventory.first((ItemStack) null, startIndex + 1);
                            if (bugSlot2 != - 1)
                            {
                                startIndex = bugSlot;
                                possibleBugs.add(bugSlot2);
                            }
                        }
                    }
                    possibleBugs.add(bugSlot);
                }
                else
                {
                    bugSlot = (short) this.playerInventory.first((ItemStack) null, startIndex + 1);
                    if (bugSlot != - 1)
                    {
                        startIndex = bugSlot;
                        possibleBugs.add(bugSlot);
                    }
                }
                k++;
            } while (k < items.length);
        }
        else
        {
            possibleBugs = null;
        }


        if (all)
        {
            final Recipe recipe = this.recipe.getRecipe();
            ItemStack result = null;
            final TShortObjectMap<ItemStack> onCraft = new TShortObjectHashMap<>(2, .5F, Short.MIN_VALUE);
            while ((this.recipe != null) && (recipe == this.recipe.getRecipe())) // as long as recipe is this same.
            {
                if (this.removeItems(true, this.recipe.getItemsToConsume()).length != 0)
                {
                    throw new IllegalArgumentException("Crafting failed for recipe: " + this.recipe.getRecipe() + ", and inventory: " + this);
                }
                if (result == null)
                {
                    result = this.recipe.getResult().clone();
                }
                else
                {
                    result.setAmount(result.getAmount() + this.recipe.getResult().getAmount());
                }
                this.recipe.getOnCraft().forEachEntry((a, b) -> {
                    final ItemStack old = onCraft.get(a);
                    if (old == null)
                    {
                        onCraft.put(a, b.clone());
                    }
                    else
                    {
                        old.setAmount(old.getAmount() + b.getAmount());
                    }
                    return true;
                });
                this.checkRecipe(false);
            }
            this.getPlayerInventory().addFromEnd(result);
            onCraft.forEachEntry(this::addReplacment);
        }
        else
        {
            final ItemStack cursor = this.getPlayerInventory().getCursorItem();
            if ((cursor != null) && (! cursor.isSimilar(this.recipe.getResult()) || ((this.recipe.getResult().getAmount() + cursor.getAmount()) > this.recipe.getResult().getMaterial().getMaxStack())))
            {
                return; // can't craft more.
            }
            if (this.removeItems(true, this.recipe.getItemsToConsume()).length != 0)
            {
                throw new IllegalArgumentException("Crafting failed for recipe: " + this.recipe.getRecipe() + ", and inventory: " + this);
            }
            final ItemStack result = this.recipe.getResult().clone();
            result.setAmount(result.getAmount() + ((cursor == null) ? 0 : cursor.getAmount()));
            this.getPlayerInventory().replaceCursorItem(cursor, result);
            this.recipe.getOnCraft().forEachEntry(this::addReplacment);

            this.checkRecipe(false);
        }

        // fix ghost items (custom recipes), meh mojang...
        if (possibleBugs != null)
        {
            final PacketPlayServerSetSlot[] packets = new PacketPlayServerSetSlot[possibleBugs.size()];
            int i = 0;
            for (final TShortIterator it = possibleBugs.iterator(); it.hasNext(); )
            {
                final short slot = it.next();
                final ItemStackImpl item = this.playerInventory.getItem(slot);
                packets[i++] = new PacketPlayServerSetSlot(this.playerInventory.getWindowId(), slot, item);
            }
            this.getHolder().getNetworkManager().sendPackets(packets);
        }
    }

    private void dropArray(final ItemStack[] rest)
    {
        if (rest.length != 0)
        {
            for (final ItemStack itemStack : rest)
            {
                final ItemImpl itemEntity = new ItemImpl(UUID.randomUUID(), DioriteCore.getInstance(), EntityImpl.getNextEntityID(), this.getHolder().getLocation().addX(2));  // TODO:velocity + some .spawnEntity method
                itemEntity.setItemStack(new BaseItemStack(itemStack));
                this.getHolder().getWorld().addEntity(itemEntity);
            }
        }
    }

    private boolean addReplacment(final short a, final ItemStack b)
    {
        final ItemStack item = this.getItem(a);
        if (item == null)
        {
            if (b.getAmount() > b.getMaterial().getMaxStack())
            {
                final ItemStack fullB = b.clone();
                fullB.setAmount(b.getMaterial().getMaxStack());
                this.setItem(a, fullB);
                b.setAmount(b.getAmount() - fullB.getAmount());
                this.dropArray(this.playerInventory.addFromEnd(b));
            }
            else
            {
                this.setItem(a, b);
            }
        }
        else if (item.isSimilar(b))
        {
            int newAmount = item.getAmount() + b.getAmount();
            if (newAmount > item.getMaterial().getMaxStack())
            {
                item.setAmount(item.getMaterial().getMaxStack());
                this.setItem(a, item);
                newAmount -= item.getMaterial().getMaxStack();
                final ItemStack item2 = item.clone();
                item2.setAmount(newAmount);
                this.dropArray(this.playerInventory.addFromEnd(item2));
            }
            else
            {
                item.setAmount(newAmount);
                this.setItem(a, item);
            }
        }
        else
        {
            this.dropArray(this.playerInventory.addFromEnd(b));
        }
        return true;
    }

    public void checkRecipe(final boolean onlyResult)
    {
        final RecipeManager rm = Diorite.getServerManager().getRecipeManager();
        synchronized (this)
        {
            if (onlyResult)
            {
                if (this.recipe == null)
                {
                    this.setResult(null);
                }
                else
                {
                    if (! this.recipe.getResult().equals(this.getResult()))
                    {
                        this.setResult(this.recipe.getResult());
                    }
                }
                return;
            }

            if (this.recipe == null) // don't check empty eq for recipes
            {
                int nonNull = 0;
                for (final ItemStack itemStack : this.getContents())
                {
                    if (itemStack != null)
                    {
                        nonNull++;
                    }
                }
                if (nonNull == 0)
                {
                    return;
                }
                else if ((nonNull == 1) && (this.getResult() != null))
                {
                    this.setResult(null);
                    return;
                }
            }

            final RecipeCheckResult result = rm.matchRecipe(this);
            if (result == null)
            {
                if (this.recipe == null)
                {
                    return;
                }
                this.recipe = null;
                if (this.getResult() != null)
                {
                    this.setResult(null);
                }
                return;
            }
            this.recipe = result;
            if (! this.recipe.getResult().equals(this.getResult()))
            {
                this.setResult(this.recipe.getResult());
            }
        }
    }

    @Override
    public int getSlotIndex(final int row, final int column)
    {
        if ((column >= this.getColumns()) || (row >= this.getRows()))
        {
            return - 1;
        }
        return 1 + (column + (row * this.getColumns()));
    }
}
