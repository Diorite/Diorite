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

        return (this.amount == itemStack.amount) && ! ((this.material != null) ? ! this.material.equals(itemStack.material) : (itemStack.material != null)) && this.itemMeta.equals(itemStack.itemMeta);

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
