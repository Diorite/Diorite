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

/**
 * Object representation of class yaml template, used to generate
 * string with yaml, from given object.
 *
 * @param <T> type of object.
 */
public class Template<T>
{
    /**
     * name of template/object.
     */
    private final String                              name;
    /**
     * type of object/template.
     */
    private final Class<T>                            clazz;
    /**
     * header comment, may be ny
     */
    private final String                              header;
    private final String                              footer;
    private final Map<ConfigField, ReflectElement<?>> fields;

    /**
     * Construct new template for given class and fields.
     *
     * @param name   name of template/object, should be class name.
     * @param clazz  type of object/template.
     * @param header header comment, may be null.
     * @param footer footer comment, may be null.
     * @param fields collection of fields data.
     */
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

    /**
     * Construct new template for given class and fields.
     *
     * @param name   name of template/object, should be class name.
     * @param clazz  type of object/template.
     * @param header header comment, may be null.
     * @param footer footer comment, may be null.
     * @param fields map of fields data and {@link ReflectElement} for that fields.
     */
    public Template(final String name, final Class<T> clazz, final String header, final String footer, final Map<ConfigField, ReflectElement<?>> fields)
    {
        Validate.notNull(clazz, "Class can't be null!");
        this.name = (name == null) ? clazz.getSimpleName() : name;
        this.clazz = clazz;
        this.header = header;
        this.footer = footer;
        this.fields = new LinkedHashMap<>(fields);
    }

    /**
     * @return name of template.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * @return type of template.
     */
    public Class<T> getClazz()
    {
        return this.clazz;
    }

    /**
     * @return header comment, may be null.
     */
    public String getHeader()
    {
        return this.header;
    }

    /**
     * @return footer comment, may be null.
     */
    public String getFooter()
    {
        return this.footer;
    }

    /**
     * @return copy of map contains all fields data.
     */
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
    public <E extends Appendable> E dump(final E writer, final T object, final int level, final boolean writeComments, final ElementPlace elementPlace) throws IOException
    {
        if (writeComments)
        {
            appendComment(writer, this.header, level, false);
            writer.append("\n");
        }

        for (final Entry<ConfigField, ReflectElement<?>> entry : this.fields.entrySet())
        {
            final ConfigField field = entry.getKey();
            TemplateElements.getElement(field).write(writer, field, object, entry.getValue(), level, true, elementPlace);
        }

        if (writeComments)
        {
            writer.append("\n\n");
            appendComment(writer, this.footer, level, false);
        }
        return writer;
    }

    /**
     * dump object to selected {@link Appendable}.
     *
     * @param writer        {@link Appendable} to use, all data will be added here.
     * @param object        object to dump. (will be represented as YAML string in writer)
     * @param level         current indent level.
     * @param writeComments if comments should be added to node.
     * @param <E>           exact type of appendable, used to return this same type as given.
     *
     * @return this same appendalbe as given, after adding string yaml representation of given object.
     *
     * @throws IOException from {@link Appendable}\
     */
    public <E extends Appendable> E dump(final E writer, final T object, final int level, final boolean writeComments) throws IOException
    {
        return this.dump(writer, object, level, writeComments, ElementPlace.NORMAL);
    }

    /**
     * dump object to selected {@link Appendable}.
     *
     * @param writer {@link Appendable} to use, all data will be added here.
     * @param object object to dump. (will be represented as YAML string in writer)
     * @param level  current indent level.
     * @param <E>    exact type of appendable, used to return this same type as given.
     *
     * @return this same appendalbe as given, after adding string yaml representation of given object.
     *
     * @throws IOException from {@link Appendable}\
     */
    public <E extends Appendable> E dump(final E writer, final T object, final int level) throws IOException
    {
        return this.dump(writer, object, level, true, ElementPlace.NORMAL);
    }

    /**
     * dump object to selected {@link Appendable}.
     *
     * @param writer {@link Appendable} to use, all data will be added here.
     * @param object object to dump. (will be represented as YAML string in writer)
     * @param <E>    exact type of appendable, used to return this same type as given.
     *
     * @return this same appendalbe as given, after adding string yaml representation of given object.
     *
     * @throws IOException from {@link Appendable}\
     */
    public <E extends Appendable> E dump(final E writer, final T object) throws IOException
    {
        return this.dump(writer, object, 0, true, ElementPlace.NORMAL);
    }

    /**
     * dump object to YAML string.
     *
     * @param object object to dump. (will be represented as YAML string in writer)
     *
     * @return string yaml representation of given object.\
     */
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

    /**
     * Append indent (2 spaces per level)
     *
     * @param writer {@link Appendable} to use, all data will be added here.
     * @param level  current indent level.
     *
     * @throws IOException from {@link Appendable}
     */
    protected static void spaces(final Appendable writer, final int level) throws IOException
    {
        if (level <= 0)
        {
            return;
        }
        for (int i = 0; i < level; i++)
        {
            writer.append("  ");
        }
    }

    /**
     * Append comment to given {@link Appendable}
     *
     * @param writer   {@link Appendable} to use, all data will be added here.
     * @param string   comment string to add, may be multi-line.
     * @param level    current indent level.
     * @param addSpace if true, additional space will be added before first # and no indent for first line.
     *
     * @throws IOException from {@link Appendable}
     */
    public static void appendComment(final Appendable writer, final String string, final int level, final boolean addSpace) throws IOException
    {
        if (string != null)
        {
            final String[] split = StringUtils.split(string, '\n');
            boolean first = true;
            for (int i = 0, splitLength = split.length; i < splitLength; i++)
            {
                final String str = split[i];
                if (! first || ! addSpace)
                {
                    spaces(writer, level);
                    writer.append("# ");
                }
                else
                {
                    first = false;
                    writer.append(" # ");
                }
                writer.append(str);
                if ((i + 1) < splitLength)
                {
                    writer.append('\n');
                }
            }
        }
    }

    @SuppressWarnings("rawtypes")
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
