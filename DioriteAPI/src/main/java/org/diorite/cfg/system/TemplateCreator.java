package org.diorite.cfg.system;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import org.diorite.cfg.annotations.CfgClass;
import org.diorite.cfg.annotations.CfgComment;
import org.diorite.cfg.annotations.CfgComments;
import org.diorite.cfg.annotations.CfgCommentsArray;
import org.diorite.cfg.annotations.CfgExclude;
import org.diorite.cfg.annotations.CfgField;
import org.diorite.cfg.annotations.CfgFooterComment;
import org.diorite.cfg.annotations.CfgFooterComments;
import org.diorite.cfg.annotations.CfgFooterCommentsArray;
import org.diorite.utils.reflections.DioriteReflectionUtils;

/**
 * Class for generating config templates, with simple cache system.
 */
public final class TemplateCreator
{
    private static final Map<Class<?>, Template<?>> templateMap = new ConcurrentHashMap<>(20, 0.1f, 4);

    private TemplateCreator()
    {
    }

    /**
     * Create new template for given class if it is unknown type.
     * (not map/array/iterable etc..)
     *
     * @param c class to check
     */
    @SuppressWarnings("ObjectEquality")
    public static void checkTemplate(Class<?> c)
    {
        do
        {
            if ((c == null) || (c == Object.class) || (c == Object[].class) || (Map.class.isAssignableFrom(c)) || (Iterable.class.isAssignableFrom(c)) || (Iterator.class.isAssignableFrom(c)) || String.class.isAssignableFrom(c) || DioriteReflectionUtils.getPrimitive(c).isPrimitive() || (c.isArray() && (DioriteReflectionUtils.getPrimitive(c.getComponentType()).isPrimitive() || String.class.isAssignableFrom(c.getComponentType()))))
            {
                return;
            }
            // generate and cache tempate

            if (c.isArray())
            {
                while (c.isArray())
                {
                    c = c.getComponentType();
                }
                checkTemplate(c);
            }
            getTemplate(c, true, true, false);
            c = c.getSuperclass();
        } while (true);
    }

    /**
     * Get template for given class.
     *
     * @param clazz    class to get template.
     * @param create   if template should be created if it don't exisit yet.
     * @param cache    if template should be saved to memory if created.
     * @param recreate if template should be force re-created even if it exisit.
     * @param <T>      Type of template class
     *
     * @return template class or null.
     */
    @SuppressWarnings("unchecked")
    public static <T> Template<T> getTemplate(final Class<T> clazz, final boolean create, final boolean cache, final boolean recreate)
    {
        if (! recreate)
        {
            final Template<T> template = (Template<T>) templateMap.get(clazz);
            if (template != null)
            {
                return template;
            }
            if (! create)
            {
                return null;
            }
        }
        final boolean allFields;
//        final boolean superFields;
        final boolean ignoreTransient;
        final String name;
        final String header;
        final String footer;
        final Collection<String> excludedFields = new HashSet<>(5);
        {
            final CfgClass cfgInfo = clazz.getAnnotation(CfgClass.class);
            if (cfgInfo != null)
            {
                allFields = cfgInfo.allFields();
//                superFields = cfgInfo.superFields();
                ignoreTransient = cfgInfo.ignoreTransient();
                name = (cfgInfo.name() != null) ? cfgInfo.name() : clazz.getSimpleName();
                Collections.addAll(excludedFields, cfgInfo.excludeFields());
            }
            else
            {
                allFields = true;
//                superFields = true;
                ignoreTransient = true;
                name = clazz.getSimpleName();
            }
        }
        {
            final String[] comments = readComments(clazz);
            header = comments[0];
            footer = comments[1];
        }

        final Set<ConfigField> fields = new TreeSet<>();

        {
            final Collection<Class<?>> classes = new ArrayList<>(5);
            {
                Class<?> fieldsSrc = clazz;
                do
                {
                    classes.add(fieldsSrc);
                    fieldsSrc = fieldsSrc.getSuperclass();
                    final CfgClass cfgInfo = fieldsSrc.getAnnotation(CfgClass.class);
                    if ((cfgInfo != null) && ! cfgInfo.superFields())
                    {
                        break;
                    }
                } while (! fieldsSrc.equals(Object.class));
            }

            for (final Class<?> fieldsSrc : classes)
            {
                int i = 0;
                for (final Field field : fieldsSrc.getDeclaredFields())
                {
                    if (field.isAnnotationPresent(CfgField.class) || (! field.isAnnotationPresent(CfgExclude.class) && ! field.isSynthetic() && (! ignoreTransient || ! Modifier.isTransient(field.getModifiers())) && (allFields || field.isAnnotationPresent(CfgField.class)) && ! excludedFields.contains(field.getName())))
                    {
                        fields.add(new ConfigField(field, i++));
                    }
                }
            }
        }
        final Template<T> template = new Template<>(name, clazz, header, footer, fields);
        if (cache)
        {
            templateMap.put(clazz, template);
        }
        return template;
    }

    /**
     * Get template for given class.
     * If template don't exisit it will be creted and saved to cache.
     *
     * @param clazz class to get template.
     * @param <T>   Type of template class
     *
     * @return template class or null.
     *
     * @see #getTemplate(Class, boolean, boolean, boolean)
     */
    public static <T> Template<T> getTemplate(final Class<T> clazz)
    {
        return getTemplate(clazz, true, true, false);
    }

    /**
     * Get template for given class.
     *
     * @param clazz  class to get template.
     * @param create if template should be created if it don't exisit yet.
     * @param <T>    Type of template class
     *
     * @return template class or null.
     *
     * @see #getTemplate(Class, boolean, boolean, boolean)
     */
    public static <T> Template<T> getTemplate(final Class<T> clazz, final boolean create)
    {
        return getTemplate(clazz, create, true, false);
    }

    /**
     * Get template for given class.
     *
     * @param clazz  class to get template.
     * @param create if template should be created if it don't exisit yet.
     * @param cache  if template should be saved to memory if created.
     * @param <T>    Type of template class
     *
     * @return template class or null.
     *
     * @see #getTemplate(Class, boolean, boolean, boolean)
     */
    public static <T> Template<T> getTemplate(final Class<T> clazz, final boolean create, final boolean cache)
    {
        return getTemplate(clazz, create, cache, false);
    }

    static String[] readComments(final AnnotatedElement element)
    {
        final String header;
        final String footer;
        final Collection<String> headerTemp = new ArrayList<>(5);
        final Collection<String> footerTemp = new ArrayList<>(5);
        for (final Annotation annotation : element.getAnnotations())
        {
            if (annotation instanceof CfgComment)
            {
                headerTemp.add(((CfgComment) annotation).value());
            }
            if (annotation instanceof CfgCommentsArray)
            {
                for (final CfgComment comment : ((CfgCommentsArray) annotation).value())
                {
                    headerTemp.add(comment.value());
                }
            }
            if (annotation instanceof CfgComments)
            {
                Collections.addAll(headerTemp, ((CfgComments) annotation).value());
            }
            if (annotation instanceof CfgFooterComment)
            {
                footerTemp.add(((CfgFooterComment) annotation).value());
            }
            if (annotation instanceof CfgFooterCommentsArray)
            {
                for (final CfgFooterComment comment : ((CfgFooterCommentsArray) annotation).value())
                {
                    footerTemp.add(comment.value());
                }
            }
            if (annotation instanceof CfgFooterComments)
            {
                Collections.addAll(footerTemp, ((CfgFooterComments) annotation).value());
            }
        }
        if (headerTemp.isEmpty())
        {
            header = null;
        }
        else
        {
            header = StringUtils.join(headerTemp, '\n');
        }
        if (footerTemp.isEmpty())
        {
            footer = null;
        }
        else
        {
            footer = StringUtils.join(footerTemp, '\n');
        }
        return new String[]{header, footer};
    }
}
