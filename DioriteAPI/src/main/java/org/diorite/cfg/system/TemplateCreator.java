package org.diorite.cfg.system;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.PriorityQueue;

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

public class TemplateCreator
{
    public Template getTemplate(final Class<?> clazz)
    {
        final boolean allFields;
        final boolean superFields;
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
                superFields = cfgInfo.superFields();
                ignoreTransient = cfgInfo.ignoreTransient();
                name = (cfgInfo.name() != null) ? cfgInfo.name() : clazz.getSimpleName();
                Collections.addAll(excludedFields, cfgInfo.excludeFields());
            }
            else
            {
                allFields = true;
                superFields = true;
                ignoreTransient = true;
                name = clazz.getSimpleName();
            }
        }
        {
            final String[] comments = readComments(clazz);
            header = comments[0];
            footer = comments[1];
        }

        final PriorityQueue<ConfigField> fields = new PriorityQueue<>(clazz.getDeclaredFields().length);

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
                    if (field.isSynthetic() || (ignoreTransient && Modifier.isTransient(field.getModifiers())) || (! allFields && ! field.isAnnotationPresent(CfgField.class)) || excludedFields.contains(field.getName()) || field.isAnnotationPresent(CfgExclude.class))
                    {
                        continue;
                    }
                    fields.add(new ConfigField(field, i++));
                }
            }
        }

        for (final ConfigField cfgField : fields)
        {
        }

        if (footer != null)
        {
        }

        return null;
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
