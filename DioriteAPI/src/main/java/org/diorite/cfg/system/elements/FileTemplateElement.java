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
public class FileTemplateElement extends TemplateElement<File>
{
    /**
     * Instance of template to direct-use.
     */
    public static final FileTemplateElement INSTANCE = new FileTemplateElement();

    /**
     * Construct new default template handler.
     */
    public FileTemplateElement()
    {
        super(File.class, obj -> {
            if (obj instanceof Path)
            {
                return ((Path) obj).toFile();
            }
            throw new UnsupportedOperationException("Can't convert object (" + obj.getClass().getName() + ") to File: " + obj);
        }, c -> Path.class.isAssignableFrom(c) || File.class.isAssignableFrom(c));
    }

    @Override
    protected File convertDefault(final Object def)
    {
        try
        {
            if (def instanceof File)
            {
                return (File) def;
            }
            if (def instanceof Path)
            {
                return ((Path) def).toFile();
            }
            if (def instanceof String)
            {
                return new File(def.toString());
            }
            if (def instanceof URI)
            {
                return new File(((URI) def).toURL().getFile());
            }
            if (def instanceof URL)
            {
                return new File(((URL) def).getFile());
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
        final File element = (elementRaw instanceof File) ? ((File) elementRaw) : this.validateType(elementRaw);
        StringTemplateElement.INSTANCE.appendValue(writer, field, source, StringTemplateElement.INSTANCE.validateType(element.getPath()), level, elementPlace);
    }

}
