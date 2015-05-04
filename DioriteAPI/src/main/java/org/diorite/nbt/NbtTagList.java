package org.diorite.nbt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class NbtTagList extends NbtAbstractTag implements NbtAnonymousTagContainer
{
    protected List<NbtTag> tagList;

    public NbtTagList()
    {
        this.tagList = new ArrayList<>(1);
    }

    public NbtTagList(final String name)
    {
        super(name);
        this.tagList = new ArrayList<>(8);
    }

    public NbtTagList(final String name, final int size)
    {
        super(name);
        this.tagList = new ArrayList<>(size);
    }

    public NbtTagList(final String name, final Collection<? extends NbtTag> tagList)
    {
        super(name);
        this.tagList = new ArrayList<>(tagList);
    }

    @Override
    public NbtTagType getTagType()
    {
        return NbtTagType.LIST;
    }

    @Override
    public void addTag(final NbtTag tag)
    {
        this.tagList.add(tag);
    }

    @Override
    public List<NbtTag> getTags()
    {
        return new ArrayList<>(this.tagList);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends NbtTag> List<T> getTags(final Class<T> tagClass)
    {
        final List<T> list = new ArrayList<>(this.tagList.size());
        for (final NbtTag tag : this.tagList)
        {
            if (! tagClass.isInstance(tag))
            {
                continue;
            }
            list.add((T) tag);
        }
        return list;
    }

    @Override
    public void setTag(final int i, final NbtTag tag)
    {
        this.tagList.set(i, tag);
    }

    @Override
    public void removeTag(final NbtTag tag)
    {
        this.tagList.remove(tag);
    }

    @Override
    public void write(final NbtOutputStream outputStream, final boolean anonymous) throws IOException
    {
        super.write(outputStream, anonymous);
        outputStream.writeByte((! this.tagList.isEmpty() ? this.tagList.get(0).getTagID() : NbtTagType.END.getTypeID()));
        outputStream.writeInt(this.tagList.size());
        for (final NbtTag tag : this.tagList)
        {
            tag.write(outputStream, true);
        }
    }

    @Override
    public void read(final NbtInputStream inputStream, final boolean anonymous, final NbtLimiter limiter) throws IOException
    {
        super.read(inputStream, anonymous, limiter);
        limiter.incrementElementsCount(1);
        limiter.incrementComplexity();

        this.tagList = new ArrayList<>(8);
        final byte type = inputStream.readByte();
        final NbtTagType tagType = NbtTagType.valueOf(type);
        final int size = inputStream.readInt();
        if (tagType == NbtTagType.END)
        {
            return;
        }

        limiter.incrementElementsCount(size);

        for (int i = 0; i < size; i++)
        {
            this.addTag(inputStream.readTag(tagType, true, limiter));
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("tagList", this.tagList).toString();
    }
}