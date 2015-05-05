package org.diorite.utils.reflections;

import java.lang.reflect.Constructor;

public final class ConstructorInvoker
{
    private final Constructor<?> constructor;

    public ConstructorInvoker(final Constructor<?> constructor)
    {
        this.constructor = constructor;
    }

    public Object invoke(final Object... arguments)
    {
        try
        {
            return this.constructor.newInstance(arguments);
        } catch (final Exception e)
        {
            throw new RuntimeException("Cannot invoke constructor " + this.constructor, e);
        }
    }

    public Constructor<?> getConstructor()
    {
        return this.constructor;
    }
}
