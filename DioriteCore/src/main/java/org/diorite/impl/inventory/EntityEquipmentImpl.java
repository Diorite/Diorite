package org.diorite.impl.inventory;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.inventory.item.ItemStackImpl;
import org.diorite.impl.inventory.item.ItemStackImplArray;
import org.diorite.entity.ArmoredEntity;
import org.diorite.inventory.EntityEquipment;
import org.diorite.inventory.item.IItemStack;
import org.diorite.utils.others.Dirtable;

public class EntityEquipmentImpl implements EntityEquipment, Dirtable
{
    protected final ItemStackImplArray content = ItemStackImplArray.create(4);
    protected final ArmoredEntity entity;
    protected       boolean       dirty;

    public EntityEquipmentImpl(final ArmoredEntity entity)
    {
        this.entity = entity;
    }

    @Override
    public IItemStack[] getContents()
    {
        return this.content.toArray(new IItemStack[this.content.length()]);
    }

    @Override
    public IItemStack getHelmet()
    {
        return this.content.get(0);
    }

    @Override
    public IItemStack getChestplate()
    {
        return this.content.get(1);
    }

    @Override
    public IItemStack getLeggings()
    {
        return this.content.get(2);
    }

    @Override
    public IItemStack getBoots()
    {
        return this.content.get(3);
    }

    @Override
    public IItemStack setHelmet(final IItemStack helmet)
    {
        return this.content.getAndSet(0, ItemStackImpl.wrap(helmet));
    }

    @Override
    public IItemStack setChestplate(final IItemStack chestplate)
    {
        return this.content.getAndSet(1, ItemStackImpl.wrap(chestplate));
    }

    @Override
    public IItemStack setLeggings(final IItemStack leggings)
    {
        return this.content.getAndSet(2, ItemStackImpl.wrap(leggings));
    }

    @Override
    public IItemStack setBoots(final IItemStack boots)
    {
        return this.content.getAndSet(3, ItemStackImpl.wrap(boots));
    }

    @Override
    public boolean replaceHelmet(final IItemStack excepted, final IItemStack helmet) throws IllegalArgumentException
    {
        ItemStackImpl.validate(excepted);
        return this.content.compareAndSet(0, (ItemStackImpl) excepted, ItemStackImpl.wrap(helmet));
    }

    @Override
    public boolean replaceChestplate(final IItemStack excepted, final IItemStack chestplate) throws IllegalArgumentException
    {
        ItemStackImpl.validate(excepted);
        return this.content.compareAndSet(1, (ItemStackImpl) excepted, ItemStackImpl.wrap(chestplate));
    }

    @Override
    public boolean replaceLeggings(final IItemStack excepted, final IItemStack leggings) throws IllegalArgumentException
    {
        ItemStackImpl.validate(excepted);
        return this.content.compareAndSet(2, (ItemStackImpl) excepted, ItemStackImpl.wrap(leggings));
    }

    @Override
    public boolean replaceBoots(final IItemStack excepted, final IItemStack boots) throws IllegalArgumentException
    {
        ItemStackImpl.validate(excepted);
        return this.content.compareAndSet(3, (ItemStackImpl) excepted, ItemStackImpl.wrap(boots));
    }

    /**
     * Completely replaces the inventory's contents. Removes all existing
     * contents and replaces it with the ItemStacks given in the array.
     *
     * @param items A complete replacement for the contents; the length must
     *              be less than or equal to {@link #size()}.
     *
     * @throws IllegalArgumentException If the array has more items than the
     *                                  inventory.
     */
    public void setContent(final ItemStackImplArray items)
    {
        final ItemStackImplArray content = this.content;
        for (int i = 0, size = content.length(); i < size; i++)
        {
            content.set(i, items.getOrNull(i));
        }
    }

    @Override
    public void setContent(final IItemStack[] items)
    {
        final ItemStackImplArray content = this.content;
        for (int i = 0, size = content.length(); i < size; i++)
        {
            content.set(i, (i >= items.length) ? null : ItemStackImpl.wrap(items[i]));
        }
    }

    @Override
    public int size()
    {
        return this.content.length();
    }

    @Override
    public ArmoredEntity getEquipmentHolder()
    {
        return this.entity;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("content", this.content).append("entity", this.entity.getId()).toString();
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
}
