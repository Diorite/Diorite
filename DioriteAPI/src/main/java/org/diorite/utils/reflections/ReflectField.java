package org.diorite.utils.reflections;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

class ReflectField<E> extends FieldAccessor<E> implements ReflectGetter<E>, ReflectSetter<E>
{
    ReflectField(final Field field)
    {
        super(DioriteReflectionUtils.getAccess(field));
    }

    ReflectField(final FieldAccessor<?> accessor)
    {
        super(accessor.getField());
    }

    @Override
    public Type getGenericType()
    {
        return this.field.getGenericType();
    }
}
