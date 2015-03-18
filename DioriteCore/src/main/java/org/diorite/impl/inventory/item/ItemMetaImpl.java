package org.diorite.impl.inventory.item;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.inventory.item.ItemMeta;
import org.diorite.nbt.NbtTagCompound;

public abstract class ItemMetaImpl implements ItemMeta
{
    private NbtTagCompound tag;

    public ItemMetaImpl(final NbtTagCompound tag)
    {
        this.tag = tag;
    }

    public NbtTagCompound getTag()
    {
        return this.tag;
    }

    public void setTag(final NbtTagCompound tag)
    {
        this.tag = tag;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("tag", this.tag).toString();
    }
}
