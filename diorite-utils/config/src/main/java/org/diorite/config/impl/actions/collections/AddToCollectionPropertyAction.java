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
import java.util.Map.Entry;

import org.diorite.commons.reflections.MethodInvoker;
import org.diorite.config.ConfigPropertyActionInstance;
import org.diorite.config.ConfigPropertyTemplate;
import org.diorite.config.AbstractPropertyAction;

@SuppressWarnings({"unchecked", "rawtypes"})
public class AddToCollectionPropertyAction extends AbstractPropertyAction
{
    public AddToCollectionPropertyAction()
    {
        super("addToCollection", "(?:addTo|putIn)(?<property>[A-Z0-9].*)");
    }

    @Override
    protected boolean matchesAction0(MethodInvoker method, Class<?>[] parameters)
    {
        return (parameters.length == 1) || (parameters.length == 2);
    }

    @Override
    protected String getGroovyImplementation0(MethodInvoker method, ConfigPropertyTemplate<?> propertyTemplate, ConfigPropertyActionInstance actionInstance)
    {
        StringBuilder methodBuilder = new StringBuilder(100);
        // language=groovy
        methodBuilder.append("$rawType v = $rawValue\n")
                     .append("if (v == null)\n" +
                             "{\n" +
                             "    v = ($type) org.diorite.config.serialization.snakeyaml.YamlCollectionCreator.createCollection($propType, 5);\n" +
                             "    $rawValue = v;\n" +
                             "}\n");
        if (Collection.class.isAssignableFrom(propertyTemplate.getRawType()))
        {
            // language=groovy
            if (method.isVarArgs())
            {
                methodBuilder.append("$returnOrNothing Collections.addAll(v, var1)");
            }
            else
            {
                methodBuilder.append("$returnOrNothing v.add(var1)");
            }
        }
        else if (Map.class.isAssignableFrom(propertyTemplate.getRawType()))
        {
            // language=groovy
            if (method.isVarArgs())
            {
                if (Entry[].class.isAssignableFrom(method.getParameterTypes()[0]))
                {
                    methodBuilder.append("for (Map.Entry entry : var1)\n" +
                                         "{\n" +
                                         "    v.put(entry.getKey(), entry.getValue()) \n" +
                                         "}\n" +
                                         "$returnOrNothing var1.length != 0");
                }
                else
                {
                    methodBuilder.append("if ((var1.length % 2) != 0)\n" +
                                         "{\n" +
                                         "    throw new IllegalStateException(\"Expected key-value arguments: $v\") \n" +
                                         "}\n" +
                                         "for (int i = 0; i < var1.length; i += 2)\n" +
                                         "{\n" +
                                         "    v.put(var1[i], var1[i+1]) \n" +
                                         "}\n" +
                                         "$returnOrNothing var1.length != 0");
                }
            }
            else
            {
                methodBuilder.append("$returnOrNothing v.put(var1, var2)");
            }
        }
        else
        {
            throw new IllegalStateException("Unsupported type of property: " + propertyTemplate.getGenericType());
        }
        return methodBuilder.toString();
    }
}
