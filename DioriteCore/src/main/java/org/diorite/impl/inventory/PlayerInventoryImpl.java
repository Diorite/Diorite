package org.diorite.impl.inventory;

import java.util.HashMap;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Player;
import org.diorite.inventory.InventoryType;
import org.diorite.inventory.PlayerArmorInventory;
import org.diorite.inventory.PlayerCraftingInventory;
import org.diorite.inventory.PlayerEqInventory;
import org.diorite.inventory.PlayerFullEqInventory;
import org.diorite.inventory.PlayerHotbarInventory;
import org.diorite.inventory.PlayerInventory;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.item.ItemStackArray;
import org.diorite.material.Material;
import org.diorite.utils.DioriteUtils;

import gnu.trove.TIntCollection;
import gnu.trove.list.array.TIntArrayList;

public class PlayerInventoryImpl extends InventoryImpl<Player> implements PlayerInventory
{
    private final Player holder;
    private final ItemStackArray content = ItemStackArray.create(InventoryType.PLAYER.getSize());

    public PlayerInventoryImpl(final Player holder)
    {
        super(holder);
        this.holder = holder;
    }

    @Override
    public int remove(final ItemStack itemStack)
    {
        if (itemStack == null)
        {
            return - 1;
        }
        for (int i = 0, size = this.content.length(); i < size; i++)
        {
            final ItemStack item = this.content.get(i);
            if (Objects.equals(item, itemStack))
            {
                this.content.compareAndSet(i, item, null);
                return i;
            }
        }
        return - 1;
    }

    @Override
    public int[] removeAll(final ItemStack itemStack)
    {
        if (itemStack == null)
        {
            return DioriteUtils.EMPTY_INT;
        }
        final TIntCollection list = new TIntArrayList(10);
        for (int i = 0, size = this.content.length(); i < size; i++)
        {
            final ItemStack item = this.content.get(i);
            if (Objects.equals(item, itemStack))
            {
                this.content.compareAndSet(i, item, null);
                list.add(i);
            }
        }
        if (list.isEmpty())
        {
            return DioriteUtils.EMPTY_INT;
        }
        return list.toArray();
    }

    @Override
    public int remove(final Material material)
    {
        return this.remove(material, false);
    }

    @Override
    public int[] removeAll(final Material material)
    {
        return this.removeAll(material, false);
    }

    @Override
    public int remove(final Material material, final boolean ignoreType)
    {
        if (material == null)
        {
            return - 1;
        }
        for (int i = 0, size = this.content.length(); i < size; i++)
        {
            final ItemStack item = this.content.get(i);
            if (item == null)
            {
                continue;
            }
            final Material mat = item.getMaterial();
            if (ignoreType)
            {
                if ((material.getId() != mat.getId()))
                {
                    continue;
                }
            }
            else if (! Objects.equals(mat, material))
            {
                continue;
            }
            this.content.compareAndSet(i, item, null);
            return i;
        }
        return - 1;
    }

    @Override
    public int[] removeAll(final Material material, final boolean ignoreType)
    {
        if (material == null)
        {
            return DioriteUtils.EMPTY_INT;
        }
        final TIntCollection list = new TIntArrayList(10);
        for (int i = 0, size = this.content.length(); i < size; i++)
        {
            final ItemStack item = this.content.get(i);
            if (item == null)
            {
                continue;
            }
            final Material mat = item.getMaterial();
            if (ignoreType)
            {
                if ((material.getId() != mat.getId()))
                {
                    continue;
                }
            }
            else if (! Objects.equals(mat, material))
            {
                continue;
            }
            this.content.compareAndSet(i, item, null);
            list.add(i);
        }
        if (list.isEmpty())
        {
            return DioriteUtils.EMPTY_INT;
        }
        return list.toArray();
    }

    @Override
    public int replace(final ItemStack excepted, final ItemStack newItem)
    {
        return 0;
    }

    @Override
    public int[] replaceAll(final ItemStack excepted, final ItemStack newItem)
    {
        return new int[0];
    }

    @Override
    public boolean replace(final int slot, final ItemStack excepted, final ItemStack newItem)
    {
        return false;
    }

    @Override
    public ItemStackArray getContent()
    {
        return this.content;
    }

    @Override
    public void setContent(final ItemStackArray items)
    {
        if (items.length() == this.content.length())
        {
           // this.content = items;
        }
        else
        {
         //   this.content = ItemStackArray.create(this.content.length());
            for (int i = 0, size = items.length(); i < size; i++)
            {
                this.content.compareAndSet(i, null, items.get(i));
            }
        }
    }

    @Override
    public ItemStack getHelmet()
    {
        return this.content.get(5);
    }

    @Override
    public ItemStack getChestplate()
    {
        return this.content.get(6);
    }

    @Override
    public ItemStack getLeggings()
    {
        return this.content.get(7);
    }

    @Override
    public ItemStack getBoots()
    {
        return this.content.get(8);
    }

    @Override
    public ItemStack setHelmet(final ItemStack helmet)
    {
        return this.content.getAndSet(5, helmet);
    }

    @Override
    public ItemStack setChestplate(final ItemStack chestplate)
    {
        return this.content.getAndSet(6, chestplate);
    }

    @Override
    public ItemStack setLeggings(final ItemStack leggings)
    {
        return this.content.getAndSet(7, leggings);
    }

    @Override
    public ItemStack setBoots(final ItemStack boots)
    {
        return this.content.getAndSet(8, boots);
    }

    @Override
    public boolean replaceHelmet(final ItemStack excepted, final ItemStack helmet)
    {
        return this.content.compareAndSet(5, excepted, helmet);
    }

    @Override
    public boolean replaceChestplate(final ItemStack excepted, final ItemStack chestplate)
    {
        return this.content.compareAndSet(6, excepted, chestplate);
    }

    @Override
    public boolean replaceLeggings(final ItemStack excepted, final ItemStack leggings)
    {
        return this.content.compareAndSet(7, excepted, leggings);
    }

    @Override
    public boolean replaceBoots(final ItemStack excepted, final ItemStack boots)
    {
        return this.content.compareAndSet(8, excepted, boots);
    }

    @Override
    public ItemStack getItemInHand()
    {
        if (this.holder == null)
        {
            return null;
        }
        return this.content.get(this.holder.getHeldItemSlot());
    }

    @Override
    public ItemStack setItemInHand(final ItemStack stack)
    {
        if (this.holder == null)
        {
            return null;
        }
        return this.content.getAndSet(this.holder.getHeldItemSlot(), stack);
    }

    @Override
    public boolean replaceItemInHand(final ItemStack excepted, final ItemStack stack)
    {
        return (this.holder != null) && this.content.compareAndSet(this.holder.getHeldItemSlot(), excepted, stack);
    }

    @Override
    public int getHeldItemSlot()
    {
        if (this.holder == null)
        {
            return - 1;
        }
        return this.holder.getHeldItemSlot();
    }

    @Override
    public void setHeldItemSlot(final int slot)
    {
        if (this.holder == null)
        {
            return;
        }
        this.holder.setHeldItemSlot(slot);
    }

    @Override
    public ItemStack[] removeItems(final boolean ifContains, final ItemStack... itmes)
    {
        return new ItemStack[0];
    }

    @Override
    public ItemStack getItem(final int index)
    {
        return this.content.get(index);
    }

    @Override
    public ItemStack setItem(final int index, final ItemStack item)
    {
        return this.content.getAndSet(index, item);
    }

    @Override
    public HashMap<Integer, ? extends ItemStack> all(final Material material)
    {
        return null;
    }

    @Override
    public HashMap<Integer, ? extends ItemStack> all(final ItemStack item)
    {
        return null;
    }

    @Override
    public int first(final Material material)
    {
        return 0;
    }

    @Override
    public int first(final ItemStack item)
    {
        return 0;
    }

    @Override
    public int firstEmpty()
    {
        return 0;
    }

    @Override
    public boolean contains(final Material material)
    {
        return false;
    }

    @Override
    public boolean contains(final ItemStack item)
    {
        return false;
    }

    @Override
    public boolean contains(final Material material, final int amount)
    {
        return false;
    }

    @Override
    public boolean contains(final ItemStack item, final int amount)
    {
        return false;
    }

    @Override
    public boolean containsAtLeast(final ItemStack item, final int amount)
    {
        return false;
    }

    @Override
    public ItemStack[] add(final ItemStack... itemStack)
    {
        return new ItemStack[0];
    }

    @Override
    public void clear(final int index)
    {
        this.content.set(index, null);
    }

    @Override
    public void clear()
    {
       // this.content = ItemStackArray.create(this.content.length());
        this.update();
    }

    @Override
    public void update(final Player player) throws IllegalArgumentException
    {
        if (! this.viewers.contains(player))
        {
            throw new IllegalArgumentException("Player must be a viewer of inventoy.");
        }
        // TODO: implement
    }

    @Override
    public void update()
    {
        for (final Player player : this.viewers)
        {
            // TODO: implement
        }
    }

    @Override
    public Player getHolder()
    {
        return this.holder;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("holder", this.holder).append("content", this.content).toString();
    }

    @Override
    public PlayerArmorInventory getArmorInventory()
    {
        return null;
    }

    @Override
    public PlayerCraftingInventory getCraftingInventory()
    {
        return null;
    }

    @Override
    public PlayerFullEqInventory getFullEqInventory()
    {
        return null;
    }

    @Override
    public PlayerEqInventory getEqInventory()
    {
        return null;
    }

    @Override
    public PlayerHotbarInventory getHotbarInventory()
    {
        return null;
    }
}
