package org.diorite.impl.inventory;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.inventory.item.ItemStack;

public class PlayerInventoryImpl extends InventoryImpl
{
    final ItemStack[] armor = new ItemStack[4];

    public PlayerInventoryImpl(final int size)
    {
        super(size);
    }

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
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("armor", this.armor).toString();
    }
}
