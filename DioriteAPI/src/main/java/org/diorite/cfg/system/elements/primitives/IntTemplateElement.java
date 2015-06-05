package org.diorite.cfg.system.elements.primitives;

/**
 * Template used by ints.
 */
public class IntTemplateElement extends SimpleTemplateElement<Integer>
{
    /**
     * Instance of template to direct-use.
     */
    public static final IntTemplateElement INSTANCE = new IntTemplateElement();

    /**
     * Construct new int template
     */
    public IntTemplateElement()
    {
        super(int.class, obj -> {
            if (obj instanceof Number)
            {
                return ((Number) obj).intValue();
            }
            throw new UnsupportedOperationException("Can't convert object (" + obj.getClass().getName() + ") to Int: " + obj);
        }, Number.class::isAssignableFrom);
    }

    @Override
    protected Integer convertDefault(final Object def, final Class<?> fieldType)
    {
        if (def instanceof Number)
        {
            return ((Number) def).intValue();
        }
        throw new UnsupportedOperationException("Can't convert default value (" + def.getClass().getName() + "): " + def);
    }
}
