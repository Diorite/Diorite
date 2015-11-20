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

import org.diorite.impl.connection.packets.play.server.PacketPlayServerSetSlot;
import org.diorite.impl.inventory.item.ItemStackImpl;
import org.diorite.impl.inventory.item.ItemStackImplArray;
import org.diorite.Diorite;
import org.diorite.inventory.InventoryType;
import org.diorite.inventory.PlayerCraftingInventory;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.RecipeManager;
import org.diorite.inventory.recipe.craft.Recipe;
import org.diorite.inventory.recipe.craft.RecipeCheckResult;
import org.diorite.utils.DioriteUtils;

import gnu.trove.TShortCollection;
import gnu.trove.iterator.TShortIterator;
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
            short i;
            short startIndex = - 1;
            do
            {
                i = (short) this.playerInventory.firstNotFull(items[k].getMaterial());
                if (i != - 1)
                {
                    short ii = (short) this.playerInventory.firstNotFull(items[k]);
                    if (i != ii)
                    {
                        if (ii != - 1)
                        {
                            possibleBugs.add(ii);
                        }
                        else
                        {
                            ii = (short) this.playerInventory.first((ItemStack) null, startIndex + 1);
                            if (ii != - 1)
                            {
                                startIndex = i;
                                possibleBugs.add(ii);
                            }
                        }
                    }
                    possibleBugs.add(i);
                }
                else
                {
                    i = (short) this.playerInventory.first((ItemStack) null, startIndex + 1);
                    if (i != - 1)
                    {
                        startIndex = i;
                        possibleBugs.add(i);
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
                    this.setItem(a, b);
                    return true;
                });
                this.checkRecipe(false);
            }
            this.getPlayerInventory().addFromEnd(result);
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
            this.recipe.getOnCraft().forEachEntry((a, b) -> {
                this.setItem(a, b);
                return true;
            });


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
