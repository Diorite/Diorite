package org.diorite.impl.inventory.item;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.inventory.item.BaseItemStack;
import org.diorite.inventory.item.ItemMeta;
import org.diorite.inventory.item.ItemStack;
import org.diorite.material.Material;
import org.diorite.utils.others.Dirtable;

public class ItemStackImpl implements Dirtable, ItemStack
{
    private final ItemStack wrapped;
    private       boolean   dirty;

    protected ItemStackImpl(final ItemStack wrapped)
    {
        Validate.isTrue(! (wrapped instanceof ItemStackImpl), "Can't wrap wrapper");
        this.wrapped = wrapped;
        this.setDirty();
    }

    public ItemStack getWrapped()
    {
        return this.wrapped;
    }

    @Override
    public boolean isSimilar(final ItemStack b)
    {
        return this.wrapped.isSimilar(b);
    }

    @Override
    public boolean isValid()
    {
        return this.wrapped.isValid();
    }

    @Override
    public boolean isAir()
    {
        return this.wrapped.isAir();
    }

    @Override
    public void update()
    {
        this.wrapped.update();
    }

    @Override
    public int getAmount()
    {
        return this.wrapped.getAmount();
    }

    @Override
    public ItemMeta getItemMeta()
    {
        return this.wrapped.getItemMeta();
    }

    @Override
    public Material getMaterial()
    {
        return this.wrapped.getMaterial();
    }

    @Override
    public void setMaterial(final Material material)
    {
        this.wrapped.setMaterial(material);
        this.setDirty();
    }

    @Override
    public void setItemMeta(final ItemMeta itemMeta)
    {
        this.wrapped.setItemMeta(itemMeta);
        this.setDirty();
    }

    @Override
    public void setAmount(final int amount)
    {
        this.wrapped.setAmount(amount);
        this.setDirty();
    }

    @Override
    public ItemStackImpl combine(final ItemStack other)
    {
        this.setDirty();
        ItemStack combined = this.wrapped.combine(other);
        return (combined == null) ? null:new ItemStackImpl(combined);
    }

    @Override
    public BaseItemStack split(final int size)
    {
        if (size > this.getAmount())
        {
            throw new IllegalArgumentException();
        }

        if (this.getAmount() == 1)
        {
            return null;
        }

        final BaseItemStack temp = new BaseItemStack(this);

        this.wrapped.setAmount(this.wrapped.getAmount() - size);
        this.setDirty();
        temp.setAmount(size);

        return temp;
    }

    @Override
    public boolean isDirty()
    {
        return this.dirty;
    }

    @Override
    public boolean setDirty(final boolean dirty)
    {
        final boolean b = this.dirty;
        this.dirty = dirty;
        return b;
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public ItemStackImpl clone()
    {
        return new ItemStackImpl(this.wrapped.clone());
    }

    public static ItemStackImpl wrap(final ItemStack item)
    {
        if (item == null)
        {
            return null;
        }
        if (item instanceof ItemStackImpl)
        {
            return (ItemStackImpl) item;
        }
        return new ItemStackImpl(item);
    }

    public static void validate(final ItemStack excepted)
    {
        if ((excepted != null) && ! (excepted instanceof ItemStackImpl))
        {
            throw new IllegalArgumentException("ItemStackImpl must be a type of excepted item");
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("wrapped", this.wrapped).append("dirty", this.dirty).toString();
    }
}
