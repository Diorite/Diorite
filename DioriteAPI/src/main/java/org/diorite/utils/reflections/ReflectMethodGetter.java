package org.diorite.utils.reflections;

import java.lang.reflect.Method;

class ReflectMethodGetter<E> extends MethodInvoker implements ReflectGetter<E>
{
    ReflectMethodGetter(final Method method)
    {
        super(method);
    }

    ReflectMethodGetter(final MethodInvoker invoker)
    {
        super(invoker.getMethod());
    }

    @Override
    public E get(final Object src)
    {
        //noinspection unchecked
        return (E) super.invoke(src);
    }
}
