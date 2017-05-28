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

import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.Map;

import org.diorite.commons.reflections.MethodInvoker;
import org.diorite.config.ConfigPropertyActionInstance;
import org.diorite.config.ConfigPropertyTemplate;
import org.diorite.config.AbstractPropertyAction;

@SuppressWarnings({"unchecked", "rawtypes"})
public class RemoveFromCollectionPropertyAction extends AbstractPropertyAction
{
    public RemoveFromCollectionPropertyAction()
    {
        super("removeFromCollection", "removeFrom(?<property>[A-Z0-9].*)");
    }

    @Override
    protected boolean matchesAction0(MethodInvoker method, Class<?>[] parameters)
    {
        return parameters.length == 1;
    }

    @Override
    protected String getGroovyImplementation0(MethodInvoker method, ConfigPropertyTemplate<?> propertyTemplate, ConfigPropertyActionInstance actionInstance)
    {
        // language=groovy
        StringBuilder methodBuilder = new StringBuilder(500);
        methodBuilder.append("$rawType v = $rawValue\n")
                     .append("if ($value == null) return $nullOrNothing\n");
        Parameter parameter = method.getParameters()[0];
        if (Collection.class.isAssignableFrom(propertyTemplate.getRawType()))
        {
            // language=groovy
            if (method.isVarArgs())
            {
                methodBuilder.append("$returnOrNothing v.removeAll(Arrays.asList(var1))\n");
            }
            else
            {
                methodBuilder.append("$returnOrNothing v.remove(var1)\n");
            }
        }
        else if (Map.class.isAssignableFrom(propertyTemplate.getRawType()))
        {
            // language=groovy
            if (parameter.isVarArgs())
            {
                methodBuilder.append("if (var1.length == 0) $returnOrNothing Collections.emptyList()\n");
                methodBuilder.append("List removed = new ArrayList() \n" +
                                     "for (Object anArg : var1)\n" +
                                     "{\n" +
                                     "    removed.add(v.remove(anArg)) \n" +
                                     "}\n" +
                                     "$returnOrNothing ($returnType) removed");
            }
            else
            {
                methodBuilder.append("$returnOrNothing ($returnType) v.remove(var1)");
            }
        }
        else
        {
            throw new IllegalStateException("Unsupported type of property: " + propertyTemplate.getGenericType());
        }
        return methodBuilder.toString();
    }
}
