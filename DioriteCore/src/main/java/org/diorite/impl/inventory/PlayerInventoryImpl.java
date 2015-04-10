package org.diorite.impl.inventory;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.inventory.item.ItemStack;

public class PlayerInventoryImpl extends InventoryImpl
{
    final ItemStack[] armor   = new ItemStack[4];
    final ItemStack[] content = new ItemStack[4 * 9];

    @Override
    public int remove(final ItemStack itemStack)
    {
        for (int i = 0; i < this.armor.length; i++)
        {
            final ItemStack is = this.armor[i];
            if (is.equals(itemStack))
            {
                this.armor[i] = null;
                return i;
            }
        }
        return - 1;
    }

    @Override
    public boolean removeItem(final ItemStack itemStack)
    {
        return false;
    }

    @Override
    public boolean contains(final ItemStack itemStack)
    {
        return false;
    }

    @Override
    public boolean containsAtLeast(final ItemStack itemStack)
    {
        return false;
    }

    @Override
    public boolean add(final ItemStack... itemStack)
    {
        return false;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("armor", this.armor).append("content", this.content).toString();
    }
}
