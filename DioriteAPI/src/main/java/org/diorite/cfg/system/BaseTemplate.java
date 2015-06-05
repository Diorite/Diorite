package org.diorite.cfg.system;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.system.elements.TemplateElement.ElementPlace;
import org.diorite.cfg.system.elements.TemplateElements;
import org.diorite.utils.reflections.DioriteReflectionUtils;
import org.diorite.utils.reflections.ReflectElement;

/**
 * Object representation of class yaml template, used to generate
 * string with yaml, from given object.
 *
 * @param <T> type of object.
 */
public class BaseTemplate<T> implements Template<T>
{
    // TODO: add methods for loading data from YAML
    private final String                              name;
    private final Class<T>                            type;
    private final String                              header;
    private final String                              footer;
    private final Map<ConfigField, ReflectElement<?>> fields;

    /**
     * Construct new template for given class and fields.
     *
     * @param name   name of template/object, should be class name.
     * @param type   type of object/template.
     * @param header header comment, may be null.
     * @param footer footer comment, may be null.
     * @param fields collection of fields data.
     */
    public BaseTemplate(final String name, final Class<T> type, final String header, final String footer, final Collection<ConfigField> fields)
    {
        Validate.notNull(type, "Class can't be null!");
        this.name = (name == null) ? type.getSimpleName() : name;
        this.type = type;
        this.header = header;
        this.footer = footer;
        this.fields = new LinkedHashMap<>(fields.size());
        for (final ConfigField cf : fields)
        {
            this.fields.put(cf, DioriteReflectionUtils.getReflectElement(cf));
        }
    }

    /**
     * Construct new template for given class and fields.
     *
     * @param name   name of template/object, should be class name.
     * @param type   type of object/template.
     * @param header header comment, may be null.
     * @param footer footer comment, may be null.
     * @param fields map of fields data and {@link ReflectElement} for that fields.
     */
    public BaseTemplate(final String name, final Class<T> type, final String header, final String footer, final Map<ConfigField, ReflectElement<?>> fields)
    {
        Validate.notNull(type, "Class can't be null!");
        this.name = (name == null) ? type.getSimpleName() : name;
        this.type = type;
        this.header = header;
        this.footer = footer;
        this.fields = new LinkedHashMap<>(fields);
    }

    /**
     * @return name of template.
     */
    @Override
    public String getName()
    {
        return this.name;
    }

    /**
     * @return type of template.
     */
    @Override
    public Class<T> getType()
    {
        return this.type;
    }

    /**
     * @return header comment, may be null.
     */
    @Override
    public String getHeader()
    {
        return this.header;
    }

    /**
     * @return footer comment, may be null.
     */
    @Override
    public String getFooter()
    {
        return this.footer;
    }

    /**
     * @return copy of map contains all fields data.
     */
    @Override
    public Map<ConfigField, ReflectElement<?>> getFields()
    {
        return new LinkedHashMap<>(this.fields);
    }

    /**
     * dump object to selected {@link Appendable}.
     *
     * @param writer        {@link Appendable} to use, all data will be added here.
     * @param object        object to dump. (will be represented as YAML string in writer)
     * @param level         current indent level.
     * @param writeComments if comments should be added to node.
     * @param elementPlace  element place, used in many templates to check current style and choose valid format.
     * @param <E>           exact type of appendable, used to return this same type as given.
     *
     * @return this same appendalbe as given, after adding string yaml representation of given object.
     *
     * @throws IOException from {@link Appendable}
     */
    @Override
    public <E extends Appendable> E dump(final E writer, final T object, final int level, final boolean writeComments, final ElementPlace elementPlace) throws IOException
    {
        if (writeComments)
        {
            Template.appendComment(writer, this.header, level, false);
            writer.append("\n");
        }

        for (final Entry<ConfigField, ReflectElement<?>> entry : this.fields.entrySet())
        {
            final ConfigField field = entry.getKey();
            TemplateElements.getElement(field).write(writer, field, object, entry.getValue(), level, true, elementPlace);
            writer.append('\n');
        }

        if (writeComments)
        {
            writer.append("\n\n");
            Template.appendComment(writer, this.footer, level, false);
        }
        return writer;
    }

    @Override
    public void dump(final File file, final T object, final Charset charset) throws IOException
    {
        try (final OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file), charset))
        {
            this.dump(out, object);
            out.flush();
            out.close();
        }
    }

    /**
     * dump object to YAML string.
     *
     * @param object object to dump. (will be represented as YAML string in writer)
     *
     * @return string yaml representation of given object.\
     */
    @Override
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

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof BaseTemplate))
        {
            return false;
        }

        final BaseTemplate template = (BaseTemplate) o;
        return this.name.equals(template.name) && ! (! this.type.equals(template.type) || ((this.header != null) ? ! this.header.equals(template.header) : (template.header != null)) || ((this.footer != null) ? ! this.footer.equals(template.footer) : (template.footer != null))) && this.fields.equals(template.fields);

    }

    @Override
    public int hashCode()
    {
        int result = this.name.hashCode();
        result = (31 * result) + this.type.hashCode();
        result = (31 * result) + ((this.header != null) ? this.header.hashCode() : 0);
        result = (31 * result) + ((this.footer != null) ? this.footer.hashCode() : 0);
        result = (31 * result) + this.fields.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).append("type", this.type).append("header", this.header).append("footer", this.footer).append("fields", this.fields).toString();
    }
}
