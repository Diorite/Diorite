package org.diorite.utils.reflections;

import java.lang.reflect.Field;

class ReflectField<E> extends FieldAccessor<E> implements ReflectGetter<E>, ReflectSetter<E>
{
    ReflectField(final Field field)
    {
        super(field);
    }

    ReflectField(final FieldAccessor<?> accessor)
    {
        super(accessor.getField());
    }
}
