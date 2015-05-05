package org.diorite.utils.reflections;

import java.lang.reflect.Method;

public final class MethodInvoker
{
    private final Method method;

    public MethodInvoker(final Method method)
    {
        this.method = method;
    }

    public Object invoke(final Object target, final Object... arguments)
    {
        try
        {
            return this.method.invoke(target, arguments);
        } catch (final Exception e)
        {
            throw new RuntimeException("Cannot invoke method " + this.method, e);
        }
    }

    public Method getMethod()
    {
        return this.method;
    }
}
