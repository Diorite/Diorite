package org.diorite.cfg.system.elements.primitives;

/**
 * Template used by bytes.
 */
public class ByteTemplateElement extends SimpleTemplateElement<Byte>
{
    /**
     * Instance of template to direct-use.
     */
    public static final ByteTemplateElement INSTANCE = new ByteTemplateElement();

    /**
     * Construct new byte template
     */
    public ByteTemplateElement()
    {
        super(byte.class, obj -> {
            if (obj instanceof Number)
            {
                return ((Number) obj).byteValue();
            }
            throw new UnsupportedOperationException("Can't convert object (" + obj.getClass().getName() + ") to Byte: " + obj);
        }, Number.class::isAssignableFrom);
    }

    @Override
    protected Byte convertDefault(final Object def)
    {
        if (def instanceof Number)
        {
            return ((Number) def).byteValue();
        }
        throw new UnsupportedOperationException("Can't convert default value (" + def.getClass().getName() + "): " + def);
    }
}
