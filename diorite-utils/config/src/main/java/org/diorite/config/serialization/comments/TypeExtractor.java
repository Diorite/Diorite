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

package org.diorite.config.serialization.comments;

import javax.annotation.Nullable;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.diorite.commons.reflections.DioriteReflectionUtils;
import org.diorite.config.annotations.Comment;
import org.diorite.config.annotations.Footer;
import org.diorite.config.annotations.Header;
import org.diorite.config.annotations.PredefinedComment;

final class TypeExtractor
{
    private static final Map<Class<?>, DocumentComments> cache = new ConcurrentHashMap<>(20);

    private final Class<?> type;

    private final DocumentComments comments;

    @SuppressWarnings("Java8MapApi") // might cause IllegalStateException: Recursive update
    static DocumentComments getAnnotationData(@Nullable Class<?> clazz)
    {
        if ((clazz == null) || clazz.isPrimitive() || (clazz == Object.class) || (clazz.getClassLoader() == null) || (clazz.getPackageName() == null) ||
            clazz.getPackageName().startsWith("java.") || clazz.getPackageName().startsWith("jdk."))
        {
            return DocumentComments.empty();
        }
        DocumentComments documentComments = cache.get(clazz);
        if (documentComments == null)
        {
            documentComments = new TypeExtractor(clazz).scan();
            documentComments.trim();
            cache.put(clazz, documentComments);
        }
        return documentComments;
    }

    private Set<Class<?>> scanned = new HashSet<>(20);

    private DocumentComments getAnnotationDataSafe(@Nullable Class<?> type)
    {
        if ((type == null) || this.scanned.contains(type))
        {
            return DocumentComments.empty();
        }
        return getAnnotationData(type);
    }

    private TypeExtractor(Class<?> type)
    {
        this.type = type;
        this.comments = DocumentComments.create();
    }

    private DocumentComments scan()
    {
        Header header = this.type.getAnnotation(Header.class);
        if (header != null)
        {
            this.comments.setHeader(header.value());
        }
        Footer footer = this.type.getAnnotation(Footer.class);
        if (footer != null)
        {
            this.comments.setFooter(footer.value());
        }
        this.scanPredefined(this.type);

        this.scan(this.type);

        return this.comments;
    }

    private void scanPredefined(Class<?> type)
    {
        for (PredefinedComment annotation : type.getAnnotationsByType(PredefinedComment.class))
        {
            this.comments.setComment(annotation.path(), annotation.value());
        }
        for (Class<?> subtype : type.getInterfaces())
        {
            DocumentComments annotationData = this.getAnnotationDataSafe(subtype);
            this.comments.join(annotationData);
        }
        DocumentComments annotationData = this.getAnnotationDataSafe(type.getSuperclass());
        this.comments.join(annotationData);
    }

    private void scan(Class<?> clazz)
    {
        if (! this.scanned.add(clazz))
        {
            return;
        }
        for (Field field : clazz.getDeclaredFields())
        {
            this.scan(field);
        }
        for (Method method : clazz.getDeclaredMethods())
        {
            this.scan(method);
        }
    }

    private void scan(Field member)
    {
        String name = member.getName();
        this.scan(member, member.getName());
        DocumentComments annotationData = this.getAnnotationDataSafe(member.getType());
        this.comments.join(name, annotationData);
    }

    @SuppressWarnings("IfStatementWithIdenticalBranches")
    private void scan(Method member)
    {
        boolean isGetter = false;
        boolean isSetter = false;

        String methodName = member.getName();
        if (methodName.startsWith("set") && (methodName.length() > 3) && (member.getParameterCount() == 1))
        {
            char firstChar = methodName.charAt(3);
            methodName = Character.toLowerCase(firstChar) + methodName.substring(4);
            isSetter = true;
        }
        else if (methodName.startsWith("get") && (methodName.length() > 3) && (member.getReturnType() != void.class))
        {
            char firstChar = methodName.charAt(3);
            methodName = Character.toLowerCase(firstChar) + methodName.substring(4);
            isGetter = true;
        }
        else if (methodName.startsWith("has") && (methodName.length() > 3) && (DioriteReflectionUtils.getPrimitive(member.getReturnType()) == boolean.class))
        {
            char firstChar = methodName.charAt(3);
            methodName = Character.toLowerCase(firstChar) + methodName.substring(4);
            isGetter = true;
        }
        else if (methodName.startsWith("is") && (methodName.length() > 2) && (DioriteReflectionUtils.getPrimitive(member.getReturnType()) == boolean.class))
        {
            char firstChar = methodName.charAt(2);
            methodName = Character.toLowerCase(firstChar) + methodName.substring(3);
            isGetter = true;
        }
        if (! isGetter && ! isSetter)
        {
            return;
        }


        this.scan(member, methodName);
        if (isGetter)
        {
            DocumentComments annotationData = this.getAnnotationDataSafe(member.getReturnType());
            this.comments.join(methodName, annotationData);
        }
        if (isSetter)
        {
            DocumentComments annotationData = this.getAnnotationDataSafe(member.getParameterTypes()[0]);
            this.comments.join(methodName, annotationData);
        }
//        this.genericTypeExtractor(member.getGenericReturnType(), name, new HashSet<>(5));
//        for (Type type : member.getGenericParameterTypes())
//        {
//            this.genericTypeExtractor(type, name, new HashSet<>(5));
//        }
    }

    private <T extends Member & AnnotatedElement> void scan(T member, String name)
    {
        Comment comment = member.getAnnotation(Comment.class);
        String nodeName = name;
        if (comment != null)
        {
            String specialName = comment.name();
            if (! specialName.isEmpty())
            {
                nodeName = specialName;
            }
            this.comments.setComment(nodeName, comment.value());
        }
        this.comments.join(nodeName, this.getAnnotationDataSafe(member.getDeclaringClass()));
    }
}
