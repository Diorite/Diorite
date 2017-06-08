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

package org.diorite.config.impl.actions;

import org.intellij.lang.annotations.RegExp;

import org.diorite.commons.reflections.DioriteReflectionUtils;
import org.diorite.commons.reflections.MethodInvoker;
import org.diorite.config.AbstractPropertyAction;
import org.diorite.config.ConfigPropertyActionInstance;
import org.diorite.config.ConfigPropertyTemplate;

@SuppressWarnings({"rawtypes", "unchecked"})
public class NumericPropertyAction extends AbstractPropertyAction
{
    private final String operation;

    public NumericPropertyAction(String name, String operation, @RegExp String... pattern)
    {
        super(name, pattern);
        this.operation = operation;
    }

    @Override
    protected boolean matchesAction0(MethodInvoker method, Class<?>[] parameters)
    {
        Class<?> returnType = method.getReturnType();
        if (parameters.length != 1)
        {
            return false;
        }
        Class<?> parameterType = parameters[0];

        if ((returnType != void.class) && (! returnType.isAssignableFrom(parameterType) && ! (returnType.isPrimitive() && parameterType.isPrimitive())))
        {
            return false;
        }

        return Number.class.isAssignableFrom(DioriteReflectionUtils.getWrapperClass(parameterType));
    }

    @Override
    protected String getGroovyImplementation0(MethodInvoker method, ConfigPropertyTemplate<?> propertyTemplate, ConfigPropertyActionInstance actionInstance)
    {
        StringBuilder methodBuilder = new StringBuilder(100);
        // language=groovy
        {
            methodBuilder.append("def v = $value\n");
            if (! propertyTemplate.getRawType().isPrimitive())
            {
                methodBuilder.append("if (v == null) v = 0\n");
            }
            methodBuilder.append("$value = ($type) (v ").append(this.operation).append(" ($type) var1)\n");
            if (method.getReturnType() != void.class)
            {
                methodBuilder.append("return v");
            }
        }
        return methodBuilder.toString();
    }
}
