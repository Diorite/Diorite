package org.diorite.cfg.system.elements;

import java.io.IOException;

import org.diorite.cfg.system.CfgEntryData;
import org.diorite.utils.SimpleEnum;
import org.diorite.utils.math.DioriteMathUtils;

/**
 * Template handler for all sime enum based objects.
 *
 * @see SimpleEnum
 */
@SuppressWarnings({"rawtypes", "unchecked", "ObjectEquality"})
public class SimpleEnumTemplateElement extends TemplateElement<SimpleEnum>
{
    /**
     * Instance of template to direct-use.
     */
    public static final SimpleEnumTemplateElement INSTANCE = new SimpleEnumTemplateElement();

    /**
     * Construct new default template handler.
     */
    public SimpleEnumTemplateElement()
    {
        super(SimpleEnum.class, obj -> {
            throw new UnsupportedOperationException("Can't convert object (" + obj.getClass().getName() + ") to SimpleEnum: " + obj);
        }, c -> false);
    }

    @Override
    protected SimpleEnum convertDefault0(final Object def, final Class<?> fieldType)
    {
        if (def instanceof String)
        {
            return SimpleEnum.getSimpleEnumValueSafe(def.toString(), DioriteMathUtils.asInt(def.toString(), - 1), (Class<SimpleEnum>) fieldType);
        }
        throw new UnsupportedOperationException("Can't convert default value (" + def.getClass().getName() + "): " + def);
    }

    @Override
    protected void appendValue(final Appendable writer, final CfgEntryData field, final Object source, final Object elementRaw, final int level, final ElementPlace elementPlace) throws IOException
    {
        final SimpleEnum element = (elementRaw instanceof SimpleEnum) ? ((SimpleEnum) elementRaw) : this.validateType(elementRaw);
        StringTemplateElement.INSTANCE.appendValue(writer, field, source, StringTemplateElement.INSTANCE.validateType(element.name()), level, elementPlace);

    }
}
