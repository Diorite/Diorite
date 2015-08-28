package org.diorite.cfg.system.elements;

import java.io.IOException;
import java.util.Locale;

import org.diorite.cfg.system.CfgEntryData;

/**
 * Template handler for all locale objects.
 *
 * @see java.util.Locale
 */
public class LocaleTemplateElement extends TemplateElement<Locale>
{
    /**
     * Instance of template to direct-use.
     */
    public static final LocaleTemplateElement INSTANCE = new LocaleTemplateElement();

    /**
     * Construct new default template handler.
     */
    public LocaleTemplateElement()
    {
        super(Locale.class, obj -> {
            if (obj instanceof String)
            {
                Locale loc = Locale.forLanguageTag((String) obj);
                if (loc.getDisplayName().isEmpty())
                {
                    loc = new Locale((String) obj);
                }
                return loc;
            }
            throw new UnsupportedOperationException("Can't convert object (" + obj.getClass().getName() + ") to Locale: " + obj);
        }, c -> Locale.class.isAssignableFrom(c) || String.class.isAssignableFrom(c));
    }

    @Override
    protected Locale convertDefault0(final Object def, final Class<?> fieldType)
    {
        if (def instanceof Locale)
        {
            return (Locale) def;
        }
        if (def instanceof String)
        {
            Locale loc = Locale.forLanguageTag((String) def);
            if (loc.getDisplayName().isEmpty())
            {
                loc = new Locale((String) def);
            }
            return loc;
        }
        throw new UnsupportedOperationException("Can't convert default value (" + def.getClass().getName() + "): " + def);
    }

    @Override
    protected void appendValue(final Appendable writer, final CfgEntryData field, final Object source, final Object elementRaw, final int level, final ElementPlace elementPlace) throws IOException
    {
        final Locale element = (elementRaw instanceof Locale) ? ((Locale) elementRaw) : this.validateType(elementRaw);
        StringTemplateElement.INSTANCE.appendValue(writer, field, source, StringTemplateElement.INSTANCE.validateType(element.toString()), level, elementPlace);
    }

}
