/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.config.impl.actions.collections;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.diorite.commons.reflections.MethodInvoker;
import org.diorite.config.AbstractPropertyAction;
import org.diorite.config.ConfigPropertyActionInstance;
import org.diorite.config.ConfigPropertyTemplate;

@SuppressWarnings({"unchecked", "rawtypes"})
public class RemoveFromCollectionIfPropertyAction extends AbstractPropertyAction
{
    public RemoveFromCollectionIfPropertyAction()
    {
        super("removeFromCollectionIf", "removeFrom(?<property>[A-Z0-9].*?)If", "remove(?<property>[A-Z0-9].*?)If");
    }

    @Override
    protected boolean matchesAction0(MethodInvoker method, Class<?>[] parameters)
    {
        if (parameters.length != 1)
        {
            return false;
        }
        if (method.isVarArgs())
        {
            return false;
        }
        Class<?> parameter = parameters[0];
        if (! Predicate.class.isAssignableFrom(parameter) && ! BiPredicate.class.isAssignableFrom(parameter))
        {
            return false;
        }
        Class<?> returnType = method.getReturnType();
        return (returnType == void.class) || (returnType == boolean.class);
    }

    @Override
    protected String getGroovyImplementation0(MethodInvoker method, ConfigPropertyTemplate<?> propertyTemplate, ConfigPropertyActionInstance actionInstance)
    {
        StringBuilder methodBuilder = new StringBuilder(500);
        // language=groovy
        methodBuilder.append("def v = $rawValue\n")
                     .append("if (v == null) $returnOrNothing false\n");

        Class<?> parameter = method.getParameterTypes()[0];
        if (Collection.class.isAssignableFrom(propertyTemplate.getRawType()))
        {
            if (BiPredicate.class.isAssignableFrom(parameter))
            {
                throw new IllegalStateException("BiPredicate can be only used on maps, not on: " + propertyTemplate.getGenericType());
            }
            // language=groovy
            methodBuilder.append("$returnOrNothing v.removeIf(var1)\n");
        }
        else if (Map.class.isAssignableFrom(propertyTemplate.getRawType()))
        {
            if (Predicate.class.isAssignableFrom(parameter))
            {
                // language=groovy
                methodBuilder.append("boolean any = false \n" +
                                     "for (Iterator<Map.Entry> iterator = v.entrySet().iterator(); iterator.hasNext(); )\n" +
                                     "{\n" +
                                     "    Map.Entry entry = iterator.next() \n" +
                                     "    if (var1.test(entry))\n" +
                                     "    {\n" +
                                     "        iterator.remove() \n" +
                                     "        any = true \n" +
                                     "    }\n" +
                                     "}\n" +
                                     "$returnOrNothing any");
            }
            else
            {
                // language=groovy
                methodBuilder.append("boolean any = false \n" +
                                     "for (Iterator<Map.Entry> iterator = v.entrySet().iterator(); iterator.hasNext(); )\n" +
                                     "{\n" +
                                     "    Map.Entry entry = iterator.next() \n" +
                                     "    if (var1.test(entry.getKey(), entry.getValue()))\n" +
                                     "    {\n" +
                                     "        iterator.remove() \n" +
                                     "        any = true \n" +
                                     "    }\n" +
                                     "}\n" +
                                     "$returnOrNothing any");
            }
        }
        else
        {
            throw new IllegalStateException("Unsupported type of property: " + propertyTemplate.getGenericType());
        }
        return methodBuilder.toString();
    }
}
