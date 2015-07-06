package org.diorite.impl.inventory.item;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.inventory.EntityEquipmentImpl;
import org.diorite.impl.inventory.InventoryImpl;
import org.diorite.inventory.item.ItemMeta;
import org.diorite.inventory.item.ItemStack;
import org.diorite.material.Material;
import org.diorite.utils.concurrent.atomic.AtomicArrayPart;
import org.diorite.utils.function.ShortConsumer;
import org.diorite.utils.others.Dirtable;

public class ItemStackImpl extends ItemStack implements Dirtable
{
    private final ItemStack     wrapped;
    private       boolean       dirty;
    private       short         slot;
    private       ShortConsumer onDirty;

    protected ItemStackImpl(final ItemStack wrapped, final short slot)
    {
        super(wrapped.getMaterial(), wrapped.getAmount());
        Validate.isTrue(! (wrapped instanceof ItemStackImpl), "Can't wrap wrapper");
        this.wrapped = wrapped;
        this.slot = slot;
        this.setDirty();
    }

    protected ItemStackImpl(final ItemStack wrapped, final short slot, final ShortConsumer onDirty)
    {
        super(wrapped.getMaterial(), wrapped.getAmount());
        Validate.isTrue(! (wrapped instanceof ItemStackImpl), "Can't wrap wrapper");
        this.wrapped = wrapped;
        this.slot = slot;
        this.onDirty = onDirty;
        this.setDirty();
    }

    public ShortConsumer getOnDirty()
    {
        return this.onDirty;
    }

    public void setOnDirty(final ShortConsumer onDirty)
    {
        this.onDirty = onDirty;
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

    public short getSlot()
    {
        return this.slot;
    }

    public void setSlot(final short slot)
    {
        this.slot = slot;
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
        this.wrapped.combine(other);
        return this;
    }

    @Override
    public ItemStack split(final int size)
    {
        if (size > this.getAmount())
        {
            throw new IllegalArgumentException();
        }

        if (this.amount == 1)
        {
            return null;
        }

        final ItemStack temp = new ItemStack(this);

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
        if (this.onDirty != null)
        {
            this.onDirty.accept(this.slot);
        }
        return b;
    }

    public static ItemStackImpl wrap(final ItemStack item, final InventoryImpl<?> inventory, final short slot)
    {
        if (item == null)
        {
            return null;
        }
        if (item instanceof ItemStackImpl)
        {
            return (ItemStackImpl) item;
        }
        final short i;
        final ItemStackImplArray arr = inventory.getArray();
        if (arr instanceof AtomicArrayPart)
        {
            //noinspection rawtypes
            i = (short) (((AtomicArrayPart) arr).getOffset() + slot);
        }
        else
        {
            i = slot;
        }
        return wrap(item, i, inventory::addDirty);
    }

    public static ItemStackImpl wrap(final ItemStack item, final EntityEquipmentImpl eq, final short slot)
    {
        return wrap(item, slot, i -> eq.setDirty());
    }

    public static ItemStackImpl wrap(final ItemStack item, final short slot)
    {
        return wrap(item, slot, null);
    }

    public static ItemStackImpl wrap(final ItemStack item, final short slot, final ShortConsumer onDirty)
    {
        if (item == null)
        {
            return null;
        }
        if (item instanceof ItemStackImpl)
        {
            return (ItemStackImpl) item;
        }
        return new ItemStackImpl(item, slot, onDirty);
    }

    public static ItemStackImpl wrap(final ItemStack item, final InventoryImpl<?> inventory, final int slot)
    {
        return wrap(item, inventory, (short) slot);
    }

    public static ItemStackImpl wrap(final ItemStack item, final EntityEquipmentImpl eq, final int slot)
    {
        return wrap(item, (short) slot, i -> eq.setDirty());
    }

    public static ItemStackImpl wrap(final ItemStack item, final int slot)
    {
        return wrap(item, (short) slot, null);
    }

    public static ItemStackImpl wrap(final ItemStack item, final int slot, final ShortConsumer onDirty)
    {
        return wrap(item, (short) slot, onDirty);
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("dirty", this.dirty).toString();
    }
}
