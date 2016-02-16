/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.cfg.system;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

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
    private final String                              name;
    private final Class<T>                            type;
    private final String                              header;
    private final String                              footer;
    private final Map<ConfigField, ReflectElement<?>> fields;
    private final Map<String, ConfigField>            nameFields;
    private final ClassLoader                         defaultClassLoader;
    private final Supplier<T>                         def;


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
        this(name, type, header, footer, fields, BaseTemplate.class.getClassLoader());
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
        this(name, type, header, footer, fields, BaseTemplate.class.getClassLoader());
    }

    /**
     * Construct new template for given class and fields.
     *
     * @param name   name of template/object, should be class name.
     * @param type   type of object/template.
     * @param header header comment, may be null.
     * @param footer footer comment, may be null.
     * @param fields collection of fields data.
     * @param def    default config object.
     */
    public BaseTemplate(final String name, final Class<T> type, final String header, final String footer, final Collection<ConfigField> fields, final Supplier<T> def)
    {
        this(name, type, header, footer, fields, BaseTemplate.class.getClassLoader(), def);
    }

    /**
     * Construct new template for given class and fields.
     *
     * @param name   name of template/object, should be class name.
     * @param type   type of object/template.
     * @param header header comment, may be null.
     * @param footer footer comment, may be null.
     * @param fields map of fields data and {@link ReflectElement} for that fields.
     * @param def    default config object.
     */
    public BaseTemplate(final String name, final Class<T> type, final String header, final String footer, final Map<ConfigField, ReflectElement<?>> fields, final Supplier<T> def)
    {
        this(name, type, header, footer, fields, BaseTemplate.class.getClassLoader(), def);
    }

    /**
     * Construct new template for given class and fields.
     *
     * @param name        name of template/object, should be class name.
     * @param type        type of object/template.
     * @param header      header comment, may be null.
     * @param footer      footer comment, may be null.
     * @param fields      collection of fields data.
     * @param classLoader default class loader to use when creating objects.
     */
    public BaseTemplate(final String name, final Class<T> type, final String header, final String footer, final Collection<ConfigField> fields, final ClassLoader classLoader)
    {
        this(name, type, header, footer, fields, classLoader, null);
    }

    /**
     * Construct new template for given class and fields.
     *
     * @param name        name of template/object, should be class name.
     * @param type        type of object/template.
     * @param header      header comment, may be null.
     * @param footer      footer comment, may be null.
     * @param fields      collection of fields data.
     * @param classLoader default class loader to use when creating objects.
     * @param def         default config object.
     */
    public BaseTemplate(final String name, final Class<T> type, final String header, final String footer, final Collection<ConfigField> fields, final ClassLoader classLoader, final Supplier<T> def)
    {
        Validate.notNull(type, "Class can't be null!");
        this.name = (name == null) ? type.getSimpleName() : name;
        this.type = type;
        this.header = header;
        this.footer = footer;
        this.fields = new LinkedHashMap<>(fields.size());
        for (final ConfigField cf : fields)
        {
            this.fields.put(cf, DioriteReflectionUtils.getReflectElement(cf.getField(), cf.getField().getDeclaringClass()));
        }
        this.nameFields = new HashMap<>(fields.size());
        for (final ConfigField cf : fields)
        {
            this.nameFields.put(cf.getName(), cf);
        }
        this.defaultClassLoader = classLoader;
        this.def = def;
    }

    /**
     * Construct new template for given class and fields.
     *
     * @param name        name of template/object, should be class name.
     * @param type        type of object/template.
     * @param header      header comment, may be null.
     * @param footer      footer comment, may be null.
     * @param fields      map of fields data and {@link ReflectElement} for that fields.
     * @param classLoader default class loader to use when creating objects.
     */
    public BaseTemplate(final String name, final Class<T> type, final String header, final String footer, final Map<ConfigField, ReflectElement<?>> fields, final ClassLoader classLoader)
    {
        this(name, type, header, footer, fields, classLoader, null);
    }

    /**
     * Construct new template for given class and fields.
     *
     * @param name        name of template/object, should be class name.
     * @param type        type of object/template.
     * @param header      header comment, may be null.
     * @param footer      footer comment, may be null.
     * @param fields      map of fields data and {@link ReflectElement} for that fields.
     * @param classLoader default class loader to use when creating objects.
     * @param def         default config object.
     */
    public BaseTemplate(final String name, final Class<T> type, final String header, final String footer, final Map<ConfigField, ReflectElement<?>> fields, final ClassLoader classLoader, final Supplier<T> def)
    {
        Validate.notNull(type, "Class can't be null!");
        this.name = (name == null) ? type.getSimpleName() : name;
        this.type = type;
        this.header = header;
        this.footer = footer;
        this.fields = new LinkedHashMap<>(fields);
        this.nameFields = new HashMap<>(fields.size());
        for (final ConfigField cf : this.fields.keySet())
        {
            this.nameFields.put(cf.getName(), cf);
        }
        this.defaultClassLoader = classLoader;
        this.def = def;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public Class<T> getType()
    {
        return this.type;
    }

    @Override
    public String getHeader()
    {
        return this.header;
    }

    @Override
    public String getFooter()
    {
        return this.footer;
    }

    @Override
    public Map<ConfigField, ReflectElement<?>> getFields()
    {
        return new LinkedHashMap<>(this.fields);
    }

    @Override
    public Map<String, ConfigField> getFieldsNameMap()
    {
        return new HashMap<>(this.nameFields);
    }

    @Override
    public ClassLoader getDefaultClassLoader()
    {
        return this.defaultClassLoader;
    }

    @Override
    public T load(final String str, final ClassLoader classLoader)
    {
        return TemplateYamlConstructor.getInstance(classLoader).loadAs(str, this.type);
    }

    @Override
    public T load(final Reader reader, final ClassLoader classLoader)
    {
        return TemplateYamlConstructor.getInstance(classLoader).loadAs(reader, this.type);
    }

    @Override
    public T load(final InputStream is, final ClassLoader classLoader)
    {
        return TemplateYamlConstructor.getInstance(classLoader).loadAs(is, this.type);
    }

    @Override
    public <E extends Appendable> E dump(final E writer, final T object, final int level, final boolean writeComments, final ElementPlace elementPlace, final boolean forceDefaultValues) throws IOException
    {
        if (writeComments)
        {
            Template.appendComment(writer, this.header, level, false);
            writer.append("\n");
        }

        for (final Entry<ConfigField, ReflectElement<?>> entry : this.fields.entrySet())
        {
            final ConfigField field = entry.getKey();
            TemplateElements.getElement(field).write(writer, field, object, entry.getValue(), level, true, elementPlace, forceDefaultValues);
        }

        if (writeComments)
        {
            writer.append("\n");
            Template.appendComment(writer, this.footer, level, false);
        }
        return writer;
    }

    @Override
    public void dump(final File file, final T object, final Charset charset, final boolean forceDefaultValues) throws IOException
    {
        try (final OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file), charset))
        {
            this.dump(out, object, forceDefaultValues);
            out.flush();
            out.close();
        }
    }

    @Override
    public String dumpAsString(final T object, final boolean forceDefaultValues)
    {
        final StringBuilder builder = new StringBuilder(this.fields.size() << 7);
        try
        {
            this.dump(builder, object, forceDefaultValues);
        } catch (final IOException ignored)
        {
            throw new AssertionError("IOException on StringBuilder?", ignored); // not possible?
        }
        return builder.toString();
    }

    @Override
    public T fillDefaults(T obj)
    {
        if (obj == null)
        {
            obj = (this.def == null) ? null : this.def.get();
        }
        for (final Entry<ConfigField, ReflectElement<?>> entry : this.fields.entrySet())
        {
            final ConfigField field = entry.getKey();
            final ReflectElement<?> element = entry.getValue();
            if (! field.hasDefaultValue() || (element == null))
            {
                continue;
            }
            element.set(obj, field.getDefaultValue());
        }
        return obj;
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
