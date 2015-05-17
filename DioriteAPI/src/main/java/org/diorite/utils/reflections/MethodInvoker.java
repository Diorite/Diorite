package org.diorite.utils.reflections;

import java.lang.reflect.Method;

/**
 * Class used to access/invoke previously prepared methods,
 * methods used by this class must be accessible.
 */
public class MethodInvoker
{
    private final Method method;

    /**
     * Construct new invoker for given method, it don't check its accessible status.
     *
     * @param method method to wrap.
     */
    public MethodInvoker(final Method method)
    {
        this.method = method;
    }

    /**
     * Invoke method and create new object.
     *
     * @param target    target object, use null for static fields.
     * @param arguments arguments for method.
     *
     * @return method invoke result.
     */
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

    /**
     * @return wrapped method.
     */
    public Method getMethod()
    {
        return this.method;
    }
}
