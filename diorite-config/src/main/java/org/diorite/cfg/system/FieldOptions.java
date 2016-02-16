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

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.function.BiFunction;

import org.diorite.cfg.annotations.CfgCollectionStyle;
import org.diorite.cfg.annotations.CfgCollectionStyle.CollectionStyle;
import org.diorite.cfg.annotations.CfgCollectionType;
import org.diorite.cfg.annotations.CfgCollectionType.CollectionType;
import org.diorite.cfg.annotations.CfgCommentOptions;
import org.diorite.cfg.annotations.CfgFooterNoNewLine;
import org.diorite.cfg.annotations.CfgStringArrayMultilineThreshold;
import org.diorite.cfg.annotations.CfgStringStyle;
import org.diorite.cfg.annotations.CfgStringStyle.StringStyle;
import org.diorite.utils.SimpleEnum.ASimpleEnum;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

/**
 * Simple enum with field annotation-based options.
 */
@SuppressWarnings({"ClassHasNoToStringMethod"})
public class FieldOptions extends ASimpleEnum<FieldOptions>
{
    /**
     * @see CfgStringStyle
     */
    public static final FieldOptions STRING_STYLE                     = new FieldOptions("STRING_STYLE", CfgStringStyle.class, (f, a) -> (a != null) ? a.value() : StringStyle.DEFAULT);
    /**
     * @see CfgStringArrayMultilineThreshold
     */
    public static final FieldOptions STRING_ARRAY_MULTILINE_THRESHOLD = new FieldOptions("STRING_ARRAY_MULTILINE_THRESHOLD", CfgStringArrayMultilineThreshold.class, (f, a) -> (a != null) ? a.value() : 25);
    /**
     * @see CfgCollectionStyle
     */
    public static final FieldOptions COLLECTION_STYLE                 = new FieldOptions("COLLECTION_STYLE", CfgCollectionStyle.class, (f, a) -> (a != null) ? a.value() : CollectionStyle.DEFAULT);
    /**
     * @see CfgCollectionType
     */
    public static final FieldOptions COLLECTION_TYPE                  = new FieldOptions("COLLECTION_TYPE", CfgCollectionType.class, (f, a) -> (a != null) ? a.value() : CollectionType.UNKNOWN);
    /**
     * @see CfgCommentOptions
     */
    public static final FieldOptions OTHERS_COMMENT_EVERY_ELEMENT     = new FieldOptions("OTHERS_COMMENT_EVERY_ELEMENT", CfgCommentOptions.class, (f, a) -> (a != null) && a.commentEveryElement());
    /**
     * @see CfgFooterNoNewLine
     */
    public static final FieldOptions OTHERS_FOOTER_NO_NEW_LINE        = new FieldOptions("OTHERS_FOOTER_NO_NEW_LINE", CfgFooterNoNewLine.class, (f, a) -> (a != null) && a.value());

    static
    {
        init(FieldOptions.class, 6);
    }

    private final Class<? extends Annotation>                 clazz;
    private final BiFunction<ConfigField, Annotation, Object> func;

    /**
     * Construct new field option with given name/id, annotation class and function.
     *
     * @param enumName name of option.
     * @param enumId   id of option, must be unique.
     * @param clazz    used annotation class.
     * @param func     function to get value of option.
     */
    public FieldOptions(final String enumName, final int enumId, final Class<? extends Annotation> clazz, final BiFunction<ConfigField, Annotation, Object> func)
    {
        super(enumName, enumId);
        this.clazz = clazz;
        this.func = func;
    }

    /**
     * Construct new field option with given name/id, annotation class and function.
     *
     * @param enumName        name of option.
     * @param annotationClass used annotation class.
     * @param func            function to get value of option.
     * @param <T>             type of annotation.
     */
    public <T extends Annotation> FieldOptions(final String enumName, final Class<T> annotationClass, final BiFunction<ConfigField, T, Object> func)
    {
        super(enumName);
        this.clazz = annotationClass;
        //noinspection unchecked
        this.func = (BiFunction<ConfigField, Annotation, Object>) func;
    }

    /**
     * Get value of field option for given field.
     *
     * @param f   config field instance.
     * @param a   annotation to check.
     * @param <T> type of annotation.
     *
     * @return value of option.
     */
    public <T extends Annotation> Object get(final ConfigField f, final T a)
    {
        return this.func.apply(f, a);
    }

    /**
     * Get value of field option for given field.
     *
     * @param f       config field instance.
     * @param element element with annotation to check.
     * @param <T>     type of annotation.
     *
     * @return value of option.
     */
    public <T extends Annotation> Object get(final ConfigField f, final AnnotatedElement element)
    {
        return this.func.apply(f, element.getAnnotation(this.clazz));
    }

    /**
     * Check fi given {@link AnnotatedElement} contains annotation used by this option.
     *
     * @param element element to checl
     *
     * @return true if it contains needed annotation.
     */
    public boolean contains(final AnnotatedElement element)
    {
        return element.isAnnotationPresent(this.clazz);
    }

    /**
     * Register new {@link FieldOptions} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final FieldOptions element)
    {
        ASimpleEnum.register(FieldOptions.class, element);
    }

    /**
     * Get one of {@link FieldOptions} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static FieldOptions getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(FieldOptions.class, ordinal);
    }

    /**
     * Get one of {@link FieldOptions} entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static FieldOptions getByEnumName(final String name)
    {
        return getByEnumName(FieldOptions.class, name);
    }

    /**
     * @return all values in array.
     */
    public static FieldOptions[] values()
    {
        final Int2ObjectMap<FieldOptions> map = getByEnumOrdinal(FieldOptions.class);
        return map.values().toArray(new FieldOptions[map.size()]);
    }

    static
    {
        FieldOptions.register(STRING_STYLE);
        FieldOptions.register(STRING_ARRAY_MULTILINE_THRESHOLD);
        FieldOptions.register(COLLECTION_STYLE);
        FieldOptions.register(COLLECTION_TYPE);
        FieldOptions.register(OTHERS_COMMENT_EVERY_ELEMENT);
        FieldOptions.register(OTHERS_FOOTER_NO_NEW_LINE);
    }
}
