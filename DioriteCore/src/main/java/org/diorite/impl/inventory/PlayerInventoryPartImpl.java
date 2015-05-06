package org.diorite.impl.inventory;

import java.util.HashMap;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Player;
import org.diorite.inventory.PlayerInventory;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.item.ItemStackArray;
import org.diorite.material.Material;

public abstract class PlayerInventoryPartImpl extends InventoryImpl<Player> implements org.diorite.inventory.PlayerInventoryPart
{
    protected final PlayerInventory playerInventory;
    protected final ItemStackArray sub;

    protected PlayerInventoryPartImpl(final PlayerInventory playerInventory, final ItemStackArray sub)
    {
        super(playerInventory.getHolder());
        this.sub = sub;
        Validate.notNull(playerInventory, "Base player inventory can't be null!");
        this.playerInventory = playerInventory;
    }

    @Override
    public Player getHolder()
    {
        return this.playerInventory.getHolder();
    }

    @Override
    public PlayerInventory getPlayerInventory()
    {
        return this.playerInventory;
    }


    @Override
    public ItemStackArray getContent()
    {
        return this.sub;
    }

    @Override
    public void setContent(final ItemStackArray items)
    {
        for (int i = 0, size = this.sub.length(); i < size; i++)
        {
            this.sub.set(i, items.getOrNull(i));
        }
    }

    @Override
    public void setContent(final ItemStack[] items)
    {
        for (int i = 0, size = this.sub.length(); i < size; i++)
        {
            this.sub.set(i, (i >= items.length) ? null : items[i]);
        }
    }

    @Override
    public void clear(final int index)
    {
        this.sub.set(index, null);
    }

    @Override
    public void clear()
    {
        for (int i = 0, size = this.sub.length(); i < size; i++)
        {
            this.sub.set(i, null);
        }
    }

    @Override
    public int remove(final ItemStack itemStack)
    {
        return 0;
    }

    @Override
    public int[] removeAll(final ItemStack itemStack)
    {
        return new int[0];
    }

    @Override
    public int remove(final Material material)
    {
        return 0;
    }

    @Override
    public int[] removeAll(final Material material)
    {
        return new int[0];
    }

    @Override
    public int remove(final Material material, final boolean ignoreType)
    {
        return 0;
    }

    @Override
    public int[] removeAll(final Material material, final boolean ignoreType)
    {
        return new int[0];
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
    public ItemStack[] removeItems(final boolean ifContains, final ItemStack... itmes)
    {
        return new ItemStack[0];
    }

    @Override
    public ItemStack getItem(final int index)
    {
        return null;
    }

    @Override
    public ItemStack setItem(final int index, final ItemStack item)
    {
        return null;
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
    public void update(final Player player) throws IllegalArgumentException
    {
        this.playerInventory.update(player);
    }

    @Override
    public void update()
    {
        this.playerInventory.update();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("playerInventory", this.playerInventory).toString();
    }
}
