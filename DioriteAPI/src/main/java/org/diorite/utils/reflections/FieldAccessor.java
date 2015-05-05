package org.diorite.utils.reflections;

import java.lang.reflect.Field;

public final class FieldAccessor<T>
{
    private final Field field;

    public FieldAccessor(final Field field)
    {
        this.field = field;
    }

    @SuppressWarnings("unchecked")
    public T get(final Object target)
    {
        try
        {
            return (T) this.field.get(target);
        } catch (final IllegalAccessException e)
        {
            throw new RuntimeException("Cannot access reflection.", e);
        }
    }

    public void set(final Object target, final Object value)
    {
        try
        {
            this.field.set(target, value);
        } catch (final IllegalAccessException e)
        {
            throw new RuntimeException("Cannot access reflection.", e);
        }
    }

    public boolean hasField(final Object target)
    {
        return this.field.getDeclaringClass().isAssignableFrom(target.getClass());
    }

    public Field getField()
    {
        return this.field;
    }
}
