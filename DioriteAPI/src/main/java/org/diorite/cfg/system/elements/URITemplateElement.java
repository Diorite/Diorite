package org.diorite.cfg.system.elements;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

import org.diorite.cfg.system.CfgEntryData;

/**
 * Template handler for all uri objects.
 *
 * @see URI
 */
@SuppressWarnings({"rawtypes", "unchecked", "ObjectEquality"})
public class URITemplateElement extends TemplateElement<URI>
{
    /**
     * Instance of template to direct-use.
     */
    public static final URITemplateElement INSTANCE = new URITemplateElement();

    /**
     * Construct new default template handler.
     */
    public URITemplateElement()
    {
        super(URI.class, obj -> {
            throw new UnsupportedOperationException("Can't convert object (" + obj.getClass().getName() + ") to URI: " + obj);
        }, c -> false);
    }

    @Override
    protected URI convertDefault(final Object def)
    {
        if (def instanceof URI)
        {
            return ((URI) def);
        }
        if (def instanceof URL)
        {
            try
            {
                return ((URL) def).toURI();
            } catch (final URISyntaxException e)
            {
                throw new RuntimeException("Can't convert default value (" + def.getClass().getName() + "): " + def, e);
            }
        }
        if (def instanceof Path)
        {
            return ((Path) def).toUri();
        }
        if (def instanceof File)
        {
            return ((File) def).toURI();
        }
        if (def instanceof String)
        {
            try
            {
                return new URI(def.toString());
            } catch (final URISyntaxException e)
            {
                throw new RuntimeException("Can't convert default value (" + def.getClass().getName() + "): " + def, e);
            }
        }
        throw new UnsupportedOperationException("Can't convert default value (" + def.getClass().getName() + "): " + def);
    }

    @Override
    protected void appendValue(final Appendable writer, final CfgEntryData field, final Object source, final Object elementRaw, final int level, final ElementPlace elementPlace) throws IOException
    {
        final URI element = (elementRaw instanceof URI) ? ((URI) elementRaw) : this.validateType(elementRaw);
        StringTemplateElement.INSTANCE.appendValue(writer, field, source, StringTemplateElement.INSTANCE.validateType(element.toString()), level, elementPlace);
    }

}
