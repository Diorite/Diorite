package org.diorite.impl.entity.meta.entry;

@SuppressWarnings("ClassHasNoToStringMethod")
public abstract class EntityMetadataObjectEntry<T> extends EntityMetadataEntry<T>
{
    private T data;

    public EntityMetadataObjectEntry(final int index, final T data)
    {
        super(index);
        this.data = data;
    }

    @Override
    public T getData()
    {
        return this.data;
    }

    @Override
    public void setData(final T data)
    {
        this.data = data;
    }
}