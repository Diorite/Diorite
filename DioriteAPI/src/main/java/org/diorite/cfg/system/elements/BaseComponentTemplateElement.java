package org.diorite.cfg.system.elements;

import java.io.IOException;

import org.diorite.cfg.system.CfgEntryData;
import org.diorite.chat.component.BaseComponent;
import org.diorite.chat.component.TextComponent;
import org.diorite.chat.component.serialize.ComponentSerializer;

/**
 * Template handler for all BaseComponent objects.
 *
 * @see BaseComponent
 */
public class BaseComponentTemplateElement extends TemplateElement<BaseComponent>
{
    /**
     * Instance of template to direct-use.
     */
    public static final BaseComponentTemplateElement INSTANCE = new BaseComponentTemplateElement();

    /**
     * Construct new default template handler.
     */
    public BaseComponentTemplateElement()
    {
        super(BaseComponent.class, obj -> {
            if (obj instanceof String)
            {
                try
                {
                    return ComponentSerializer.safeParseOne((String) obj, '&');
                } catch (final Exception e)
                {
                    return TextComponent.fromLegacyText((String) obj);
                }
            }
            throw new UnsupportedOperationException("Can't convert object (" + obj.getClass().getName() + ") to BaseComponent: " + obj);
        }, c -> BaseComponent.class.isAssignableFrom(c) || String.class.isAssignableFrom(c));
    }

    @Override
    protected BaseComponent convertDefault0(final Object def, final Class<?> fieldType)
    {
        if (def instanceof BaseComponent)
        {
            return (BaseComponent) def;
        }
        if (def instanceof String)
        {
            try
            {
                return ComponentSerializer.parseOne((String) def);
            } catch (final Exception e)
            {
                return TextComponent.fromLegacyText((String) def);
            }
        }
        throw new UnsupportedOperationException("Can't convert default value (" + def.getClass().getName() + "): " + def);
    }

    @Override
    protected void appendValue(final Appendable writer, final CfgEntryData field, final Object source, final Object elementRaw, final int level, final ElementPlace elementPlace) throws IOException
    {
        final BaseComponent element = (elementRaw instanceof BaseComponent) ? ((BaseComponent) elementRaw) : this.validateType(elementRaw);
        StringTemplateElement.INSTANCE.appendValue(writer, field, source, StringTemplateElement.INSTANCE.validateType(element.toString()), level, elementPlace);
    }

}
