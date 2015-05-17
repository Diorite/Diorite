package org.diorite.cfg.system.elements.primitives;

/**
 * Template used by floats.
 */
public class FloatTemplateElement extends SimpleTemplateElement<Float>
{
    /**
     * Instance of template to direct-use.
     */
    public static final FloatTemplateElement INSTANCE = new FloatTemplateElement();

    /**
     * Construct new float template
     */
    public FloatTemplateElement()
    {
        super(float.class, obj -> {
            if (obj instanceof Number)
            {
                return ((Number) obj).floatValue();
            }
            throw new UnsupportedOperationException("Can't convert object to Float: " + obj);
        }, Number.class::isAssignableFrom);
    }

    @Override
    protected Float convertDefault(final Object def)
    {
        if (def instanceof Number)
        {
            return ((Number) def).floatValue();
        }
        throw new UnsupportedOperationException("Can't convert default value (" + def.getClass().getName() + "): " + def);
    }
}
