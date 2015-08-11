package org.diorite.utils.reflections;

import java.lang.reflect.Field;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Class used to access previously prepared fields,
 * fields used by this class must be accessible.
 *
 * @param <T> type of field.
 */
public class FieldAccessor<T>
{
    protected final Field field;

    /**
     * Construct new invoker for given constructor, it don't check its accessible status.
     *
     * @param field field to wrap.
     */
    public FieldAccessor(final Field field)
    {
        this.field = field;
    }

    /**
     * get value of this field.
     *
     * @param target target object, use null for static fields.
     *
     * @return value of this field.
     */
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

    /**
     * set value fo this field.
     *
     * @param target target object, use null for static fields.
     * @param value  new value.
     */
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

    /**
     * Check if given object class contains this field.
     *
     * @param target object to check.
     *
     * @return true if class contains this field.
     */
    public boolean hasField(final Object target)
    {
        return this.field.getDeclaringClass().isAssignableFrom(target.getClass());
    }

    /**
     * @return wrapped field.
     */
    public Field getField()
    {
        return this.field;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("field", this.field).toString();
    }
}
