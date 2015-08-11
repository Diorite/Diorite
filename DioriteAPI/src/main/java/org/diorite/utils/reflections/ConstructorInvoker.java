package org.diorite.utils.reflections;

import java.lang.reflect.Constructor;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Class used to invoke previously prepared constructors,
 * constructors used by this class must be accessible.
 */
public class ConstructorInvoker
{
    private final Constructor<?> constructor;

    /**
     * Construct new invoker for given constructor, it don't check its accessible status.
     *
     * @param constructor constructor to wrap.
     */
    public ConstructorInvoker(final Constructor<?> constructor)
    {
        this.constructor = constructor;
    }

    /**
     * Invoke constructor and create new object.
     *
     * @param arguments arguments for constructor.
     *
     * @return new object.
     */
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

    /**
     * @return wrapped constructor.
     */
    public Constructor<?> getConstructor()
    {
        return this.constructor;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("constructor", this.constructor).toString();
    }
}
