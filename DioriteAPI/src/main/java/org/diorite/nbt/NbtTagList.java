package org.diorite.nbt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.collect.ImmutableList;

public class NbtTagList extends NbtAbstractTag<NbtTagList>
{
    protected final Class<?>                type;
    protected       List<NbtAbstractTag<?>> tagList;

    public NbtTagList()
    {
        this.type = null;
        this.tagList = new CopyOnWriteArrayList<>();
    }

    public NbtTagList(final String name)
    {
        super(name);
        this.type = null;
        this.tagList = new CopyOnWriteArrayList<>();
    }

    public NbtTagList(final String name, final Class<NbtAbstractTag<?>> type)
    {
        super(name);
        this.type = type;
        this.tagList = new CopyOnWriteArrayList<>();
    }

    public NbtTagList(final String name, final List<NbtAbstractTag<?>> tagList)
    {
        super(name);
        this.type = null;
        this.tagList = tagList;
    }

    public NbtTagList(final String name, final Class<NbtAbstractTag<?>> type, final List<NbtAbstractTag<?>> tagList)
    {
        super(name);
        this.type = type;
        this.tagList = new CopyOnWriteArrayList<>(tagList);
    }

    public NbtTagList(final String name, final NbtTagCompound parent, final Class<NbtAbstractTag<?>> type)
    {
        super(name, parent);
        this.type = type;
        this.tagList = new CopyOnWriteArrayList<>();
    }

    public NbtTagList(final String name, final NbtTagCompound parent, final List<NbtAbstractTag<?>> tagList)
    {
        super(name, parent);
        this.type = null;
        this.tagList = new CopyOnWriteArrayList<>(tagList);
    }

    public NbtTagList(final String name, final NbtTagCompound parent, final Class<NbtAbstractTag<?>> type, final List<NbtAbstractTag<?>> tagList)
    {
        super(name, parent);
        this.type = type;
        this.tagList = new CopyOnWriteArrayList<>(tagList);
    }

    @SuppressWarnings("unchecked")
    @Override
    public NbtTagList read(final NbtInputStream inputStream, final boolean hasName) throws IOException
    {
        super.read(inputStream, hasName);
        final NbtTagType tagType = NbtTagType.valueOf(inputStream.readByte());
        final int size = inputStream.readInt();
        if (tagType == NbtTagType.END)
        {
            return this;
        }
        final List<NbtAbstractTag<?>> temp = new ArrayList<>(size);
        for (int i = 0; i < size; i++)
        {
            try
            {
                temp.add(inputStream.readTag(tagType, false));
            } catch (final Exception e)
            {
                throw new UnexpectedTagTypeException(e);
            }
        }
        this.tagList = new CopyOnWriteArrayList<>(temp);
        return this;
    }

    @Override
    public NbtTagList write(final NbtOutputStream outputStream, final boolean hasName) throws IOException
    {
        super.write(outputStream, hasName);
        outputStream.writeByte(this.getElementsType().getTypeID());
        outputStream.writeInt(this.tagList.size());
        for (final NbtAbstractTag<?> tag : this.tagList)
        {
            if (this.isVaildType(tag))
            {
                tag.write(outputStream, false);
            }
        }
        return this;
    }

    @Override
    public NbtTagType getType()
    {
        return NbtTagType.LIST;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("tagList", this.tagList).append("type", this.type).toString();
    }

    public NbtTagType getElementsType()
    {
        if ((this.tagList == null) || this.tagList.isEmpty())
        {
            return NbtTagType.END;
        }
        final NbtTagType type;
        if ((this.type != null) && ((type = NbtTagType.valueOf(this.type)) != null))
        {
            return type;
        }
        return this.tagList.get(0).getType();
    }

    public boolean isVaildType(final Object object)
    {
        Validate.notNull(object, "object can't be null");
        return this.isVaildType(object.getClass());
    }

    public boolean isVaildType(final Class<?> clazz)
    {
        Validate.notNull(clazz, "class can't be null");
        return (this.type == null) || this.type.isAssignableFrom(clazz);
    }

    public void addTag(final NbtAbstractTag<?> tag)
    {
        Validate.notNull(tag, "tag can't be null");
        if (! this.isVaildType(tag))
        {
            throw new UnexpectedTagTypeException();
        }
        this.tagList.add(tag);
    }

    public List<NbtAbstractTag<?>> getTags()
    {
        return new ImmutableList.Builder<NbtAbstractTag<?>>().addAll(this.tagList).build();
    }

    public void setTags(final List<NbtAbstractTag<?>> tags)
    {
        this.tagList = new CopyOnWriteArrayList<>(tags);
    }

    public List<NbtAbstractTag<?>> getMutableTags()
    {
        return this.tagList;
    }

    @SuppressWarnings("unchecked")
    public <E extends NbtAbstractTag<?>> List<E> getTags(final Class<E> tagClass) throws UnexpectedTagTypeException
    {
        final ImmutableList.Builder<E> builder = new ImmutableList.Builder<>();
        for (final NbtAbstractTag<?> tag : this.tagList)
        {
            if (! tagClass.isInstance(tag))
            {
                throw new UnexpectedTagTypeException("The list entry should be of type " + tagClass.getSimpleName() + ", but is of type " + tag.getClass().getSimpleName());
            }
            builder.add((E) tag);
        }
        return builder.build();
    }

    public void removeTag(final Object tag)
    {
        this.tagList.remove(tag);
    }

    public void setTag(final int i, final NbtAbstractTag<?> tag)
    {
        this.tagList.set(i, tag);
    }
}