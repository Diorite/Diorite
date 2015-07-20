package org.diorite.inventory.item;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.Material;

/**
 * Interface for item stack representation.
 */
public class BaseItemStack implements ItemStack
{
    // TODO: lore, name and other stuff
    protected Material material;
    protected int      amount;
    protected ItemMeta itemMeta;

    public BaseItemStack(final Material material, final int amount)
    {
        Validate.notNull(material, "Material can't be null.");
        this.material = material.ensureValidInventoryItem();
        this.amount = (this.material == null) ? 0 : amount;
    }

    public BaseItemStack(final Material material)
    {
        this(material, 1);
    }

    public BaseItemStack(final ItemStack item)
    {
        this(item.getMaterial(), item.getAmount());
//        this.itemMeta =  TODO: clone item meta
    }

    @Override
    public Material getMaterial()
    {
        return this.material;
    }

    @Override
    public void setMaterial(final Material material)
    {
        this.material = material.ensureValidInventoryItem();
        if (this.material == null)
        {
            this.amount = 0;
            this.itemMeta = null;
        }
    }

    @Override
    public ItemMeta getItemMeta()
    {
        return this.itemMeta;
    }

    @Override
    public void setItemMeta(final ItemMeta itemMeta)
    {
        // TODO: add type check
        this.itemMeta = itemMeta;
    }

    @Override
    public int getAmount()
    {
        return this.amount;
    }

    @Override
    public void setAmount(final int amount)
    {
        this.amount = amount;
    }

    @Override
    public void update()
    {
        // TODO
    }

    @Override
    public boolean isAir()
    {
        return (this.material == null) || this.material.simpleEquals(Material.AIR);
    }

    @Override
    public boolean isValid()
    {
        return this.amount <= this.material.getMaxStack();
    }

    @Override
    public boolean isSimilar(final ItemStack b)
    {
        if (b == null)
        {
            return this.material.equals(Material.AIR);
        }
        return this.material.equals(b.getMaterial()) && ItemMeta.equals(this.itemMeta, b.getItemMeta());
    }

    @Override
    public BaseItemStack split(final int size)
    {
        if (size > this.amount)
        {
            throw new IllegalArgumentException();
        }

        if (this.amount == 1)
        {
            return null;
        }

        final BaseItemStack temp = new BaseItemStack(this);

        this.amount -= size;
        temp.setAmount(size);

        return temp;
    }

    @Override
    public BaseItemStack combine(final ItemStack other)
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

            final BaseItemStack temp = new BaseItemStack(this);
            temp.setAmount(pendingItems);
            return temp;
        }
        else
        {
            this.amount += other.getAmount();
            return null;
        }
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public BaseItemStack clone()
    {
        return new BaseItemStack(this);
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
        if (! (o instanceof BaseItemStack))
        {
            return false;
        }

        final BaseItemStack itemStack = (BaseItemStack) o;

        return (this.amount == itemStack.amount) && ! ((this.material != null) ? ! this.material.equals(itemStack.material) : (itemStack.material != null)) && ((this.itemMeta == null) || this.itemMeta.equals(itemStack.itemMeta));

    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("material", this.material).append("amount", this.amount).append("itemMeta", this.itemMeta).toString();
    }

}
