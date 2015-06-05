package org.diorite.cfg.system.elements.primitives;

/**
 * Template used by booleans.
 */
public class BooleanTemplateElement extends SimpleTemplateElement<Boolean>
{
    /**
     * Instance of template to direct-use.
     */
    public static final BooleanTemplateElement INSTANCE = new BooleanTemplateElement();

    /**
     * Construct new boolean template
     */
    public BooleanTemplateElement()
    {
        super(boolean.class, obj -> {
            if (obj instanceof Boolean)
            {
                return (boolean) obj;
            }
            throw new UnsupportedOperationException("Can't convert object (" + obj.getClass().getName() + ") to Boolean: " + obj);
        }, Boolean.class::isAssignableFrom);
    }

    @Override
    protected Boolean convertDefault0(final Object def, final Class<?> fieldType)
    {
        throw new UnsupportedOperationException("Can't convert default value (" + def.getClass().getName() + "): " + def);
    }
}
