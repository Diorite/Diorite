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

import javax.annotation.RegEx;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.diorite.commons.reflections.MethodInvoker;
import org.diorite.config.ActionMatcherResult;
import org.diorite.config.ConfigPropertyAction;

public abstract class AbstractPropertyAction implements ConfigPropertyAction
{
    protected final String              name;
    protected final Collection<Pattern> patterns;

    protected AbstractPropertyAction(String name, @RegEx String... strPatterns)
    {
        this.name = name;
        Pattern[] patterns = new Pattern[strPatterns.length];
        for (int i = 0; i < strPatterns.length; i++)
        {
            patterns[i] = Pattern.compile("^" + strPatterns[i] + "$");
        }
        this.patterns = Set.of(patterns);
    }

    protected AbstractPropertyAction(String name, Pattern... patterns)
    {
        this.name = name;
        this.patterns = Set.of(patterns);
    }

    protected abstract boolean matchesAction0(MethodInvoker method, Class<?>[] parameters);

    private static ActionMatcherResult FAIL = new ActionMatcherResult(false, null);

    @Override
    public ActionMatcherResult matchesAction(Method method)
    {
        MethodInvoker methodInvoker = new MethodInvoker(method);
        if (methodInvoker.isStatic() || methodInvoker.isNative())
        {
            return FAIL;
        }
        for (Pattern pattern : this.patterns)
        {
            Matcher matcher = pattern.matcher(method.getName());
            if (matcher.matches() && (matcher.groupCount() > 0))
            {
                String property = matcher.group("property");
                if ((property == null) || property.isEmpty())
                {
                    return FAIL;
                }
                char firstChar = property.charAt(0);
                if (Character.isUpperCase(firstChar))
                {
                    property = Character.toLowerCase(firstChar) + property.substring(1);
                }
                return new ActionMatcherResult(this.matchesAction0(methodInvoker, methodInvoker.getParameterTypes()), property);
            }
        }
        return FAIL;
    }

    @Override
    public String getActionName()
    {
        return this.name;
    }
}

