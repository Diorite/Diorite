package org.diorite.nbt;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class NbtTagEnd extends NbtAbstractTag
{
    @Override
    public NbtTagType getTagType()
    {
        return NbtTagType.END;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).toString();
    }
}