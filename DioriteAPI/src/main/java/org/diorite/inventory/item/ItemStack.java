package org.diorite.inventory.item;

import java.util.Optional;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.inventory.Inventory;
import org.diorite.material.Material;

/**
 * Interface for item stack representation.
 */
public class ItemStack
{
    // TODO: lore, name and other stuff
    private Material  material;
    private int       amount;
    private Inventory location;
    private ItemMeta  itemMeta;

    public ItemStack(final Material material, final int amount)
    {
        Validate.notNull(material, "Material can't be null.");
        this.material = material;
        this.amount = amount;
    }

    public ItemStack(final Material material)
    {
        this(material, 1);
    }

    public ItemStack(final ItemStack item)
    {
        this(item.getMaterial(), item.getAmount());
//        this.itemMeta =  TODO: clone item meta
    }

    /**
     * @return material of itemstack.
     */
    public Material getMaterial()
    {
        return this.material;
    }

    /**
     * Change material of itemstack.
     *
     * @param material new material.
     */
    public void setMaterial(final Material material)
    {
        this.material = material;
    }

    /**
     * ItemMeta contains data like name, lore, enchantments of item.
     *
     * @return ItemMeta of itemstack, may be null.
     */
    public ItemMeta getItemMeta()
    {
        return this.itemMeta;
    }

    public void setItemMeta(final ItemMeta itemMeta)
    {
        // TODO: add type check
        this.itemMeta = itemMeta;
    }

    /**
     * @return amout of material in itemstack.
     */
    public int getAmount()
    {
        return this.amount;
    }

    /**
     * Change amout of itemstack in material.
     *
     * @param amount new amount.
     */
    public void setAmount(final int amount)
    {
        this.amount = amount;
    }

    public Optional<Inventory> getLocation()
    {
        return Optional.ofNullable(this.location);
    }

    public void setLocation(final Inventory inventory)
    {
        if (! inventory.contains(this))
        {
            inventory.add(this);
        }
        this.location = inventory;
    }

    public void update()
    {
        // TODO
    }

    /**
     * @return true if this is air itemstack.
     */
    public boolean isAir()
    {
        return this.material.equals(Material.AIR);
    }

    /**
     * Check if this itemstack have valid amout of items in it.
     *
     * @return true if amount is smaller or equal to max stack size of material.
     */
    public boolean isValid()
    {
        return this.amount <= this.material.getMaxStack();
    }

    /**
     * Check if items are similar, items are similar if they are made from this
     * same material and have this same item meta, but they can have different size.
     * (amount of items in itemstack)
     *
     * @param b item to check.
     *
     * @return true if items are similar.
     */
    public boolean isSimilar(final ItemStack b)
    {
        if (b == null)
        {
            return this.material.equals(Material.AIR);
        }
        return this.material.equals(b.getMaterial()) && ItemMeta.equals(this.itemMeta, b.getItemMeta());
    }

    /**
     * Subtract the specified number of items and creates a new ItemStack with given amount of items
     *
     * @param size Number of items which should be removed from this itemstack and moved to new
     *
     * @return ItemStack with specified amount of items
     * null when number of items in this ItemStack is 1
     *
     * @throws IllegalArgumentException when size is greater than amount of items in this ItemStack
     */
    public ItemStack split(final int size)
    {
        if (size > this.amount)
        {
            throw new IllegalArgumentException();
        }

        if (this.amount == 1)
        {
            return null;
        }

        final ItemStack temp = new ItemStack(this);

        this.amount -= size;
        temp.setAmount(size);

        return temp;
    }

    /**
     * Adds one ItemStack to another and returns the remainder
     *
     * @param other ItemStack to add
     *
     * @return All of which failed to add
     */
    public ItemStack combine(final ItemStack other)
    {
        if (! this.isSimilar(other))
        {
            throw new IllegalArgumentException();
        }

        final int maxStack = this.material.getMaxStack();
        if ((this.amount + other.getAmount()) > maxStack)
        {
            final int pendingItems = (this.amount + other.getAmount()) - maxStack;
            this.amount = maxStack;

            final ItemStack temp = new ItemStack(this);
            temp.setAmount(pendingItems);
            return temp;
        }
        else
        {
            this.amount += other.getAmount();
            return null;
        }
    }

    @Override
    public int hashCode()
    {
        int result = (this.material != null) ? this.material.hashCode() : 0;
        result = (31 * result) + this.amount;
        result = (31 * result) + ((this.itemMeta != null) ? this.itemMeta.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof ItemStack))
        {
            return false;
        }

        final ItemStack itemStack = (ItemStack) o;

        return (this.amount == itemStack.amount) && ! ((this.material != null) ? ! this.material.equals(itemStack.material) : (itemStack.material != null)) && ((this.itemMeta == null) || this.itemMeta.equals(itemStack.itemMeta));

    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("material", this.material).append("amount", this.amount).append("location", this.location).append("itemMeta", this.itemMeta).toString();
    }

    static boolean isSimilar(final ItemStack a, final ItemStack b)
    {
        //noinspection ObjectEquality
        if (a == b)
        {
            return true;
        }
        if (a != null)
        {
            return a.isSimilar(b);
        }
        return b.isSimilar(null);
    }
}
