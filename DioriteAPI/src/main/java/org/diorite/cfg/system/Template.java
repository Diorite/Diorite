package org.diorite.cfg.system;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.system.elements.TemplateElement.ElementPlace;
import org.diorite.cfg.system.elements.TemplateElements;
import org.diorite.utils.reflections.DioriteReflectionUtils;
import org.diorite.utils.reflections.ReflectElement;

public class Template<T>
{
    private final String                              name;
    private final Class<T>                            clazz;
    private final String                              header;
    private final String                              footer;
    private final Map<ConfigField, ReflectElement<?>> fields;

    public Template(final String name, final Class<T> clazz, final String header, final String footer, final Collection<ConfigField> fields)
    {
        Validate.notNull(clazz, "Class can't be null!");
        this.name = (name == null) ? clazz.getSimpleName() : name;
        this.clazz = clazz;
        this.header = header;
        this.footer = footer;
        this.fields = new LinkedHashMap<>(fields.size());
        for (final ConfigField cf : fields)
        {
            this.fields.put(cf, DioriteReflectionUtils.getReflectElement(cf));
        }
    }

    public Template(final String name, final Class<T> clazz, final String header, final String footer, final Map<ConfigField, ReflectElement<?>> fields)
    {
        Validate.notNull(clazz, "Class can't be null!");
        this.name = (name == null) ? clazz.getSimpleName() : name;
        this.clazz = clazz;
        this.header = header;
        this.footer = footer;
        this.fields = new LinkedHashMap<>(fields);
    }

    public String getName()
    {
        return this.name;
    }

    public Class<T> getClazz()
    {
        return this.clazz;
    }

    public String getHeader()
    {
        return this.header;
    }

    public String getFooter()
    {
        return this.footer;
    }

    public Map<ConfigField, ReflectElement<?>> getFields()
    {
        return this.fields;
    }

    public <E extends Appendable> E dump(final E writter, final T object) throws IOException
    {
        appendComment(writter, this.header, false);
        writter.append("\n");

        for (final Entry<ConfigField, ReflectElement<?>> entry : this.fields.entrySet())
        {
            final ConfigField field = entry.getKey();
            TemplateElements.getElement(field).write(writter, field, object, entry.getValue(), 0, true, ElementPlace.NORMAL);
        }

        writter.append("\n\n");
        appendComment(writter, this.footer, false);
        return writter;
    }

    public String dumpAsString(final T object)
    {
        final StringBuilder builder = new StringBuilder(this.fields.size() << 7);
        try
        {
            this.dump(builder, object);
        } catch (final IOException ignored)
        {
            throw new AssertionError("IOException on StringBuilder?", ignored); // not possible?
        }
        return builder.toString();
    }

    public static void appendComment(final Appendable writer, final String string, final boolean addSpace) throws IOException
    {
        if (string != null)
        {
            final String[] split = StringUtils.split(string, '\n');
            for (int i = 0, splitLength = split.length; i < splitLength; i++)
            {
                final String str = split[i];
                if (addSpace)
                {
                    writer.append(" # ");
                }
                else
                {
                    writer.append("# ");
                }
                writer.append(str);
                if ((i + 1) < splitLength)
                {
                    writer.append('\n');
                }
            }
        }
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof Template))
        {
            return false;
        }

        final Template template = (Template) o;
        return this.name.equals(template.name) && ! (! this.clazz.equals(template.clazz) || ((this.header != null) ? ! this.header.equals(template.header) : (template.header != null)) || ((this.footer != null) ? ! this.footer.equals(template.footer) : (template.footer != null))) && this.fields.equals(template.fields);

    }

    @Override
    public int hashCode()
    {
        int result = this.name.hashCode();
        result = (31 * result) + this.clazz.hashCode();
        result = (31 * result) + ((this.header != null) ? this.header.hashCode() : 0);
        result = (31 * result) + ((this.footer != null) ? this.footer.hashCode() : 0);
        result = (31 * result) + this.fields.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).append("clazz", this.clazz).append("header", this.header).append("footer", this.footer).append("fields", this.fields).toString();
    }
}
