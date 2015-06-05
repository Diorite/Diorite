package org.diorite.cfg.system.elements;

import java.io.IOException;

import org.diorite.cfg.system.CfgEntryData;

/**
 * Template handler for all enum based objects.
 *
 * @see Enum
 */
@SuppressWarnings({"rawtypes", "unchecked", "ObjectEquality"})
public class EnumTemplateElement extends TemplateElement<Enum>
{
    /**
     * Instance of template to direct-use.
     */
    public static final EnumTemplateElement INSTANCE = new EnumTemplateElement();

    /**
     * Construct new default template handler.
     */
    public EnumTemplateElement()
    {
        super(Enum.class, obj -> {
            throw new UnsupportedOperationException("Can't convert object (" + obj.getClass().getName() + ") to Enum: " + obj);
        }, c -> false);
    }

    @Override
    protected Enum convertDefault0(final Object def, final Class<?> fieldType)
    {
        if (def instanceof String)
        {
            return Enum.valueOf((Class<Enum>) fieldType, def.toString());
        }
        throw new UnsupportedOperationException("Can't convert default value (" + def.getClass().getName() + "): " + def);
    }

    @Override
    protected void appendValue(final Appendable writer, final CfgEntryData field, final Object source, final Object elementRaw, final int level, final ElementPlace elementPlace) throws IOException
    {
        final Enum element = (elementRaw instanceof Enum) ? ((Enum) elementRaw) : this.validateType(elementRaw);
        StringTemplateElement.INSTANCE.appendValue(writer, field, source, StringTemplateElement.INSTANCE.validateType(element.name()), level, elementPlace);
    }

}
