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
        super(boolean.class);
    }

    @Override
    protected Boolean convertDefault(final Object def)
    {
        throw new UnsupportedOperationException("Can't convert default value (" + def.getClass().getName() + "): " + def);
    }
}
