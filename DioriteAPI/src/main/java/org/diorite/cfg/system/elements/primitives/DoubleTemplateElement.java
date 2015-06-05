package org.diorite.cfg.system.elements.primitives;

/**
 * Template used by doubles.
 */
public class DoubleTemplateElement extends SimpleTemplateElement<Double>
{
    /**
     * Instance of template to direct-use.
     */
    public static final DoubleTemplateElement INSTANCE = new DoubleTemplateElement();

    /**
     * Construct new double template
     */
    public DoubleTemplateElement()
    {
        super(double.class, obj -> {
            if (obj instanceof Number)
            {
                return ((Number) obj).doubleValue();
            }
            throw new UnsupportedOperationException("Can't convert object (" + obj.getClass().getName() + ") to Double: " + obj);
        }, Number.class::isAssignableFrom);
    }

    @Override
    protected Double convertDefault0(final Object def, final Class<?> fieldType)
    {
        if (def instanceof Number)
        {
            return ((Number) def).doubleValue();
        }
        throw new UnsupportedOperationException("Can't convert default value (" + def.getClass().getName() + "): " + def);
    }
}
