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

import org.diorite.commons.reflections.MethodInvoker;
import org.diorite.config.AbstractPropertyAction;
import org.diorite.config.ConfigPropertyActionInstance;
import org.diorite.config.ConfigPropertyTemplate;

public class EqualsPropertyAction extends AbstractPropertyAction
{
    protected EqualsPropertyAction()
    {
        super("isEqualsTo", "isEqualsTo(?<property>[A-Z0-9].*)", "areEqualsTo(?<property>[A-Z0-9].*)", "(?<property>[A-Z0-9].*)isEqualsTo",
              "(?<property>[A-Z0-9].*)areEqualsTo");
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
        return "return $rawValue == var1";
    }
}
