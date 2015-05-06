package org.diorite.impl.inventory.item;

import java.util.Optional;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.inventory.Inventory;
import org.diorite.inventory.item.ItemStack;
import org.diorite.material.Material;

public class ItemStackImpl implements ItemStack
{
    // TODO: lore, name and other stuff
    private Material     material;
    private int          amount;
    private short        durability;
    private Inventory    location;
    private ItemMetaImpl itemMeta;

    public ItemStackImpl(final Material material, final int amount, final int durability)
    {
        Validate.notNull(material, "Material can't be null.");
        this.material = material;
        this.amount = amount;
        this.durability = (short) durability;
    }

    public ItemStackImpl(final Material material)
    {
        this(material, 1);
    }

    public ItemStackImpl(final Material material, final int amount)
    {
        this(material, amount, 0);
    }

    @Override
    public Material getMaterial()
    {
        return this.material;
    }

    public void setMaterial(final Material material)
    {
        this.material = material;
    }

    @Override
    public int getAmount()
    {
        return this.amount;
    }

    public void setAmount(final int amount)
    {
        this.amount = amount;
    }

    @Override
    public short getDurability()
    {
        return this.durability;
    }

    public void setDurability(final short durability)
    {
        this.durability = durability;
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

    @Override
    public ItemMetaImpl getItemMeta()
    {
        // TODO: never return null, and always return valid type
        if (this.itemMeta == null)
        {
            this.itemMeta = new SimpleItemMetaImpl(null);
        }
        return this.itemMeta;
    }

    public void setItemMeta(final ItemMetaImpl itemMeta)
    {
        // TODO: add type check
        this.itemMeta = itemMeta;
    }

    public void update()
    {
        // TODO
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof ItemStackImpl))
        {
            return false;
        }

        final ItemStackImpl itemStack = (ItemStackImpl) o;

        return (this.amount == itemStack.amount) && (this.durability == itemStack.durability) && ! (this.material != null ? ! this.material.equals(itemStack.material) : itemStack.material != null) && itemMeta.equals(itemStack.itemMeta);

    }

    @Override
    public int hashCode()
    {
        int result = (this.material != null) ? this.material.hashCode() : 0;
        result = (31 * result) + this.amount;
        result = (31 * result) + (int) this.durability;
        result = (31 * result) + this.itemMeta.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("material", this.material).append("amount", this.amount).append("durability", this.durability).append("location", this.location).append("itemMeta", this.itemMeta).toString();
    }
}
