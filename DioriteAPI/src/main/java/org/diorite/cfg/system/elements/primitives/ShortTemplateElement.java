package org.diorite.cfg.system.elements.primitives;

/**
 * Template used by shorts.
 */
public class ShortTemplateElement extends SimpleTemplateElement<Short>
{
    /**
     * Instance of template to direct-use.
     */
    public static final ShortTemplateElement INSTANCE = new ShortTemplateElement();

    /**
     * Construct new short template
     */
    public ShortTemplateElement()
    {
        super(short.class, obj -> {
            if (obj instanceof Number)
            {
                return ((Number) obj).shortValue();
            }
            throw new UnsupportedOperationException("Can't convert object to Short: " + obj);
        }, Number.class::isAssignableFrom);
    }

    @Override
    protected Short convertDefault(final Object def)
    {
        if (def instanceof Number)
        {
            return ((Number) def).shortValue();
        }
        throw new UnsupportedOperationException("Can't convert default value (" + def.getClass().getName() + "): " + def);
    }
}
