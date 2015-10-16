/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.cfg.system.elements;

import java.io.IOException;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.system.CfgEntryData;
import org.diorite.cfg.system.ConfigField;
import org.diorite.cfg.system.FieldOptions;
import org.diorite.cfg.system.Template;
import org.diorite.utils.reflections.DioriteReflectionUtils;
import org.diorite.utils.reflections.ReflectGetter;

/**
 * Base class for all template elements handlers.
 *
 * @param <T> type of supproted/handled element.
 */
public abstract class TemplateElement<T>
{
    /**
     * type of supproted/handled element.
     */
    protected final Class<T>            fieldType;
    /**
     * function used to convert other types to this type (may throw errors)
     */
    protected final Function<Object, T> function;
    /**
     * returns true for classes that can be converted into supproted type.
     */
    protected final Predicate<Class<?>> classPredicate;


    /**
     * Construct new template for given class, convert function and class type checking function.
     *
     * @param fieldType      type of supproted template element.
     * @param function       function used to convert other types to this type (may throw errors)
     * @param classPredicate returns true for classes that can be converted into supproted type.
     */
    public TemplateElement(final Class<T> fieldType, final Function<Object, T> function, final Predicate<Class<?>> classPredicate)
    {
        this.fieldType = fieldType;
        this.function = function;
        this.classPredicate = classPredicate;
    }

    /**
     * @return tyle of template element
     */
    public Class<T> getFieldType()
    {
        return this.fieldType;
    }

    /**
     * Check if given class is compatible with this template.
     *
     * @param clazz class to check.
     *
     * @return true if class can be used in this template.
     */
    public boolean isValidType(final Class<?> clazz)
    {
        return DioriteReflectionUtils.getPrimitive(this.fieldType).isAssignableFrom(clazz) || DioriteReflectionUtils.getWrapperClass(this.fieldType).isAssignableFrom(clazz);
    }

    /**
     * Check if given class can be compatible with this template after using convert function..
     *
     * @param clazz class to check.
     *
     * @return true if class can be used in this template.
     */
    public boolean canBeConverted(final Class<?> clazz)
    {
        return this.isValidType(clazz) || ((this.classPredicate != null) && this.classPredicate.test(clazz));
    }

    /**
     * Convert object from default-value annotation to compatible type. <br>
     * May throw error if object can't be converted.<br>
     * This method don't need to check if object is already good one.
     *
     * @param def       object to convert.
     * @param fieldType expected type of returned object.
     *
     * @return converted object.
     */
    protected abstract T convertDefault0(Object def, Class<?> fieldType);

    /**
     * Convert object from default-value annotation to compatible type.<br>
     * May throw error if object can't be converted.
     *
     * @param def       object to convert.
     * @param fieldType expected type of returned object.
     *
     * @return converted object.
     */
    @SuppressWarnings("unchecked")
    public T convertDefault(final Object def, final Class<?> fieldType)
    {
        if (def == null)
        {
            return null;
        }
        if (DioriteReflectionUtils.getWrapperClass(fieldType).isAssignableFrom(def.getClass()))
        {
            return (T) def;
        }
        return this.convertDefault0(def, fieldType);
    }

    /**
     * Wrtie header/footer comments, field name (key) and value to slected writer ({@link Appendable}) using this template.
     *
     * @param writer             {@link Appendable} to use, all data will be added here.
     * @param field              config field with basic field data and options.
     * @param object             object contains this field.
     * @param invoker            getter for field value.
     * @param level              current indent level.
     * @param addComments        if comments should be added to node.
     * @param elementPlace       element place, used in many templates to check current style and choose valid format.
     * @param forceDefaultValues if true, all values will be set to default ones.
     *
     * @throws IOException from {@link Appendable}
     */
    public void write(final Appendable writer, final ConfigField field, final Object object, final ReflectGetter<?> invoker, final int level, final boolean addComments, final ElementPlace elementPlace, final boolean forceDefaultValues) throws IOException
    {
        Object element = invoker.get(object);
        if ((forceDefaultValues || (element == null)) && field.hasDefaultValue())
        {
            final Object def = field.getDefaultValue();
            if (def != null)
            {
                element = def;
            }
        }
        if (element != null)
        {
            writer.append('\n');
            if (addComments && (field.getHeader() != null))
            {
                Template.appendComment(writer, field.getHeader(), level, false);
                writer.append('\n');
            }

            appendElement(writer, level, field.getName());
            writer.append(": ");
            this.appendValue(writer, field, object, this.validateType(element), level, elementPlace);

            if (addComments && (field.getFooter() != null))
            {
                if (field.getOption(FieldOptions.OTHERS_FOOTER_NO_NEW_LINE, false))
                {
                    Template.appendComment(writer, field.getFooter(), level, true);
                }
                else
                {
                    writer.append('\n');
                    Template.appendComment(writer, field.getFooter(), level, false);
                }
            }
            writer.append('\n');
        }
    }

    /**
     * Wrtie value to slected writer ({@link Appendable}) using this template.
     *
     * @param writer       {@link Appendable} to use, all data will be added here.
     * @param field        config field with basic field data and options.
     * @param object       object contains this field.
     * @param element      element to write.
     * @param level        current indent level.
     * @param addComments  if comments should be added to node.
     * @param elementPlace element place, used in many templates to check current style and choose valid format.
     *
     * @throws IOException from {@link Appendable}
     */
    public void writeValue(final Appendable writer, final CfgEntryData field, final Object object, final Object element, final int level, final boolean addComments, final ElementPlace elementPlace) throws IOException
    {
        if (element != null)
        {
            if (addComments && (field.getHeader() != null) && (elementPlace == ElementPlace.NORMAL))
            {
                Template.appendComment(writer, field.getHeader(), level, false);
                writer.append('\n');
            }

            this.appendValue(writer, field, object, this.validateType(element), level, elementPlace);

//            if (elementPlace == ElementPlace.NORMAL)
//            {
//               writer.append('\n');
//            }
            if (addComments && (field.getFooter() != null))
            {
                Template.appendComment(writer, field.getFooter(), level, false);
                writer.append('\n');
            }
        }
    }

    /**
     * Abstract method implemented by templates to write string representation of given element.
     *
     * @param writer       {@link Appendable} to use, all data will be added here.
     * @param field        config field with basic field data and options.
     * @param source       object contains this element.
     * @param element      element to write/represent.
     * @param level        current indent level.
     * @param elementPlace element place, used in many templates to check current style and choose valid format.
     *
     * @throws IOException from {@link Appendable}
     */
    protected abstract void appendValue(final Appendable writer, final CfgEntryData field, final Object source, final Object element, final int level, final ElementPlace elementPlace) throws IOException;

    protected T validateType(final Object obj)
    {
        if (this.fieldType.isAssignableFrom(obj.getClass()))
        {
            //noinspection unchecked
            return (T) obj;
        }
        return this.function.apply(obj);
    }

    /**
     * Append given char sequence with proper indent.
     *
     * @param writer  {@link Appendable} to use, all data will be added here.
     * @param level   current indent level.
     * @param element text to append.
     *
     * @throws IOException from {@link Appendable}
     */
    protected static void appendElement(final Appendable writer, final int level, final CharSequence element) throws IOException
    {
        spaces(writer, level);
        writer.append(element);
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
     * Enum with possible element places.
     */
    public enum ElementPlace
    {
        /**
         * Normal key: value part of yaml.
         * <br>
         * <pre>keyOne: valueOne
         * keyTwo:
         *   subKeyOne: v1
         *   subKeyTwo: 10</pre>
         */
        NORMAL,
        /**
         * Mulit-line styled yaml list.
         * <br>
         * <pre>list:
         * - element1
         * - element2</pre>
         */
        LIST,
        /**
         * Single-line styled yaml list or map.
         * <br>
         * <pre>list: [element1, element2, map: {keyOne: value, keyTwo: value}]
         * map: keyOne: value, keyTwo: value, list: [element1, element2]}</pre>
         */
        SIMPLE_LIST_OR_MAP,;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("fieldType", this.fieldType).toString();
    }
}
