package org.diorite.inventory.item;

import org.diorite.nbt.NbtTagCompound;

public interface ItemMeta
{
    NbtTagCompound getRawData();

    void setRawData(NbtTagCompound tag);

    /**
     * check if this item meta affect item,
     * if item don't have any lore, name and other stuff,
     * then this method will return true.
     *
     * @return true if item meta don't affect item.
     */
    default boolean isDefault()
    {
        return this.getRawData().getTags().isEmpty();
    }

    default boolean equals(final ItemMeta b)
    {
        if (b == null)
        {
            return this.isDefault();
        }
        if (b.isDefault())
        {
            return this.isDefault();
        }
        return b.getRawData().equals(this.getRawData());
    }

    static boolean equals(final ItemMeta a, final ItemMeta b)
    {
        //noinspection ObjectEquality
        if (a == b)
        {
            return true;
        }
        if (a != null)
        {
            return a.equals(b);
        }
        return b.equals(null);
    }
}
