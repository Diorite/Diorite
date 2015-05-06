package org.diorite.impl.inventory;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.diorite.entity.Player;
import org.diorite.inventory.Inventory;
import org.diorite.inventory.InventoryHolder;
import org.diorite.inventory.InventoryType;
import org.diorite.inventory.item.ItemStack;
import org.diorite.material.Material;

public abstract class InventoryImpl implements Inventory
{ // TODO: finish.
    private final ItemStack[] content;

    public InventoryImpl(final int size)
    {
        this.content = new ItemStack[size];
    }

    @Override
    public ItemStack[] getContent()
    {
        return this.content;
    }

    @Override
    public void setContent(final ItemStack[] items)
    {
        if (items.length > this.content.length)
        {
            throw new IllegalArgumentException("Given array is too big");
        }
        System.arraycopy(items, 0, this.content, 0, items.length);
        if (items.length < this.content.length)
        {
            for (int i = items.length; i < this.content.length; i++)
            {
                this.content[i] = null;
            }
        }
    }

    @Override
    public int remove(final ItemStack itemStack)
    {
        for (int i = 0, size = this.content.length; i < size; i++)
        {
            final ItemStack item = this.content[i];
            if (Objects.equals(item, itemStack))
            {
                this.content[i] = null;
                return i;
            }
        }
        return - 1;
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
    public void setItem(final int index, final ItemStack item)
    {

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

    }

    @Override
    public void clear()
    {

    }

    @Override
    public List<Player> getViewers()
    {
        return null;
    }

    @Override
    public String getTitle()
    {
        return null;
    }

    @Override
    public void setTitle(final String str)
    {

    }

    @Override
    public void update(final Player player) throws IllegalArgumentException
    {

    }

    @Override
    public void update()
    {

    }

    @Override
    public InventoryType getType()
    {
        return null;
    }

    @Override
    public InventoryHolder getHolder()
    {
        return null;
    }

    @Override
    public int size()
    {
        return 0;
    }

    @Override
    public InventoryIterator iterator()
    {
        return new InventoryIterator(this);
    }
}
