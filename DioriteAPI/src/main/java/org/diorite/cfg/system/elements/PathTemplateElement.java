package org.diorite.cfg.system.elements;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;

import org.diorite.cfg.system.CfgEntryData;

/**
 * Template handler for all path/file objects.
 *
 * @see Path
 * @see File
 */
@SuppressWarnings({"rawtypes", "unchecked", "ObjectEquality"})
public class PathTemplateElement extends TemplateElement<Path>
{
    /**
     * Instance of template to direct-use.
     */
    public static final PathTemplateElement INSTANCE = new PathTemplateElement();

    /**
     * Construct new default template handler.
     */
    public PathTemplateElement()
    {
        super(Path.class, obj -> {
            if (obj instanceof File)
            {
                return ((File) obj).toPath();
            }
            throw new UnsupportedOperationException("Can't convert object (" + obj.getClass().getName() + ") to Path: " + obj);
        }, c -> Path.class.isAssignableFrom(c) || File.class.isAssignableFrom(c));
    }

    @Override
    protected Path convertDefault0(final Object def, final Class<?> fieldType)
    {
        try
        {
            if (def instanceof Path)
            {
                return (Path) def;
            }
            if (def instanceof File)
            {
                return ((File) def).toPath();
            }
            if (def instanceof String)
            {
                return new File(def.toString()).toPath();
            }
            if (def instanceof URI)
            {
                return new File(((URI) def).toURL().getFile()).toPath();
            }
            if (def instanceof URL)
            {
                return new File(((URL) def).getFile()).toPath();
            }
        } catch (final MalformedURLException e)
        {
            throw new RuntimeException("Can't convert default value (" + def.getClass().getName() + "): " + def, e);
        }
        throw new UnsupportedOperationException("Can't convert default value (" + def.getClass().getName() + "): " + def);
    }

    @Override
    protected void appendValue(final Appendable writer, final CfgEntryData field, final Object source, final Object elementRaw, final int level, final ElementPlace elementPlace) throws IOException
    {
        final Path element = (elementRaw instanceof Path) ? ((Path) elementRaw) : this.validateType(elementRaw);
        StringTemplateElement.INSTANCE.appendValue(writer, field, source, StringTemplateElement.INSTANCE.validateType(element.toString()), level, elementPlace);
    }

}
