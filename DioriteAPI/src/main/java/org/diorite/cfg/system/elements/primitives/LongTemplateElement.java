package org.diorite.cfg.system.elements.primitives;

/**
 * Template used by longs.
 */
public class LongTemplateElement extends SimpleTemplateElement<Long>
{
    /**
     * Instance of template to direct-use.
     */
    public static final LongTemplateElement INSTANCE = new LongTemplateElement();

    /**
     * Construct new long template
     */
    public LongTemplateElement()
    {
        super(long.class, obj -> {
            if (obj instanceof Number)
            {
                return ((Number) obj).longValue();
            }
            throw new UnsupportedOperationException("Can't convert object (" + obj.getClass().getName() + ") to Long: " + obj);
        }, Number.class::isAssignableFrom);
    }

    @Override
    protected Long convertDefault(final Object def, final Class<?> fieldType)
    {
        if (def instanceof Number)
        {
            return ((Number) def).longValue();
        }
        throw new UnsupportedOperationException("Can't convert default value (" + def.getClass().getName() + "): " + def);
    }
}
