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

import org.diorite.commons.reflections.MethodInvoker;
import org.diorite.config.ConfigPropertyActionInstance;
import org.diorite.config.ConfigPropertyTemplate;
import org.diorite.config.AbstractPropertyAction;

@SuppressWarnings({"unchecked", "rawtypes"})
public class ContainsInCollectionPropertyNegatedAction extends AbstractPropertyAction
{
    public ContainsInCollectionPropertyNegatedAction()
    {
        super("excludesInCollection", "(?:(notContains|excludes)(?:Key?)(?:In?)|isNotIn)(?<property>[A-Z0-9].*)",
              "(?:(notContains|excludes)(?:In?)|isNotIn)(?<property>[A-Z0-9].*)",
              "(?:notContains|excludes|isNotIn)(?<property>[A-Z0-9].*)");
    }

    @Override
    protected boolean matchesAction0(MethodInvoker method, Class<?>[] parameters)
    {
        if (parameters.length != 1)
        {
            return false;
        }
        Class<?> returnType = method.getReturnType();
        return (returnType == boolean.class);
    }

    @Override
    protected String getGroovyImplementation0(MethodInvoker method, ConfigPropertyTemplate<?> propertyTemplate, ConfigPropertyActionInstance actionInstance)
    {
        StringBuilder methodBuilder = new StringBuilder(500);
        // language=groovy
        methodBuilder.append("$rawType v = $rawValue\n" +
                             "if (v == null) return true\n");
        if (Collection.class.isAssignableFrom(propertyTemplate.getRawType()))
        {
            // language=groovy
            if (method.isVarArgs())
            {
                methodBuilder.append("List notIn = Arrays.asList(var1) \n" +
                                     "if (notIn.isEmpty())\n" +
                                     "{\n" +
                                     "    return true \n" +
                                     "}\n" +
                                     "for (Object o : v)\n" +
                                     "{\n" +
                                     "    if (notIn.contains(o))\n" +
                                     "    {\n" +
                                     "        return false \n" +
                                     "    }\n" +
                                     "}\n" +
                                     "return true");
            }
            else
            {
                methodBuilder.append("return ! v.contains(var1)");
            }
        }
        else if (Map.class.isAssignableFrom(propertyTemplate.getRawType()))
        {
            // language=groovy
            if (method.isVarArgs())
            {
                methodBuilder.append("List notIn = Arrays.asList(var1) \n" +
                                     "if (notIn.isEmpty())\n" +
                                     "{\n" +
                                     "    return true \n" +
                                     "}\n" +
                                     "for (Object o : map.keySet())\n" +
                                     "{\n" +
                                     "    if (notIn.contains(o))\n" +
                                     "    {\n" +
                                     "        return false \n" +
                                     "    }\n" +
                                     "}\n" +
                                     "return true \n");
            }
            else
            {
                methodBuilder.append("return ! v.containsKey(var1)");
            }
        }
        else
        {
            throw new IllegalStateException("Unsupported type of property: " + propertyTemplate.getGenericType());
        }
        return methodBuilder.toString();
    }
}
