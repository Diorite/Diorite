package org.diorite.utils.reflections;

import java.lang.reflect.Method;

class ReflectMethodSetter<E> extends MethodInvoker implements ReflectSetter<E>
{
    ReflectMethodSetter(final Method method)
    {
        super(DioriteReflectionUtils.getAccess(method));
    }

    ReflectMethodSetter(final MethodInvoker invoker)
    {
        super(invoker.getMethod());
    }

    @Override
    public void set(final Object src, final Object obj)
    {
        super.invoke(src, obj);
    }
}
