package org.diorite.utils.reflections;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Class that connect {@link ReflectGetter} and {@link ReflectSetter},
 * allowing to get and set values using one object.
 *
 * @param <E> type of value.
 */
public class ReflectElement<E> implements ReflectGetter<E>, ReflectSetter<E>
{
    private final ReflectGetter<E> getter;
    private final ReflectSetter<E> setter;

    /**
     * Construct new reflect element using given getter and setter instance.
     *
     * @param getter getter for value.
     * @param setter setter for value.
     */
    public ReflectElement(final ReflectGetter<E> getter, final ReflectSetter<E> setter)
    {
        this.getter = getter;
        this.setter = setter;
    }

    ReflectElement(final FieldAccessor<?> accessor)
    {
        final ReflectField<E> reflectField = new ReflectField<>(accessor);
        this.getter = reflectField;
        this.setter = reflectField;
    }

    ReflectElement(final MethodInvoker getter, final MethodInvoker setter)
    {
        this.getter = new ReflectMethodGetter<>(getter);
        this.setter = new ReflectMethodSetter<>(setter);
    }

    ReflectElement(final MethodInvoker getter, final FieldAccessor<?> accessor)
    {
        this.getter = new ReflectMethodGetter<>(getter);
        this.setter = new ReflectField<>(accessor);
    }

    ReflectElement(final FieldAccessor<?> accessor, final MethodInvoker setter)
    {
        this.getter = new ReflectField<>(accessor);
        this.setter = new ReflectMethodSetter<>(setter);
    }

    @Override
    public E get(final Object src)
    {
        return this.getter.get(src);
    }

    @Override
    public void set(final Object src, final Object obj)
    {
        this.setter.set(src, obj);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("getter", this.getter).append("setter", this.setter).toString();
    }
}
