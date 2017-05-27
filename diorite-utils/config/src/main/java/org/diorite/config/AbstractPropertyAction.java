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

package org.diorite.config;

import javax.annotation.RegEx;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.intellij.lang.annotations.Language;

import org.diorite.commons.reflections.MethodInvoker;

/**
 * Abstract property action contains basic implementation of {@link ConfigPropertyAction} making creation of new actions much simpler.
 *
 * @see #getGroovyImplementation0(MethodInvoker, ConfigPropertyTemplate, ConfigPropertyActionInstance)
 */
public abstract class AbstractPropertyAction implements ConfigPropertyAction
{
    protected final String              name;
    protected final Collection<Pattern> patterns;

    /**
     * Construct new action with given name and regex patterns.
     *
     * @param name
     *         name of action.
     * @param strPatterns
     *         regex patterns to validate name of method.
     */
    protected AbstractPropertyAction(String name, @RegEx String... strPatterns)
    {
        this.name = name;
        Pattern[] patterns = new Pattern[strPatterns.length];
        for (int i = 0; i < strPatterns.length; i++)
        {
            patterns[i] = Pattern.compile("^" + strPatterns[i] + "$");
        }
        this.patterns = List.of(patterns);
    }

    /**
     * Construct new action with given name and regex patterns.
     *
     * @param name
     *         name of action.
     * @param patterns
     *         regex patterns to validate name of method.
     */
    protected AbstractPropertyAction(String name, Pattern... patterns)
    {
        this.name = name;
        this.patterns = List.of(patterns);
    }

    /**
     * Internal method to validate if given method can be used for this action, more checks can be done later in {@link #getGroovyImplementation0(MethodInvoker,
     * ConfigPropertyTemplate, ConfigPropertyActionInstance)}
     *
     * @param method
     *         method to validate
     * @param parameters
     *         parameters of method (for easier access)
     *
     * @return true if this is valid method.
     */
    protected abstract boolean matchesAction0(MethodInvoker method, Class<?>[] parameters);

    private static final ActionMatcherResult FAIL = new ActionMatcherResult(false, null);

    @Override
    public final ActionMatcherResult matchesAction(Method method)
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
    public String getGroovyImplementation(MethodInvoker method, ConfigPropertyTemplate<?> propertyTemplate, ConfigPropertyActionInstance actionInstance)
    {
        String groovyImplementation = "@Override\n" +
                                      actionInstance.getMethodSignature() + "\n" +
                                      "{\n" +
                                      "" + addMethodIndent(this.getGroovyImplementation0(method, propertyTemplate, actionInstance)) +
                                      "}\n";
        groovyImplementation = StringUtils.replace(groovyImplementation, "$type", propertyTemplate.getGenericType().getTypeName());
        groovyImplementation = StringUtils.replace(groovyImplementation, "$property", propertyTemplate.getOriginalName());
        groovyImplementation = StringUtils.replaceIgnoreCase(groovyImplementation, "$propName", propertyTemplate.getName());
        groovyImplementation = StringUtils.replaceIgnoreCase(groovyImplementation, "$value", "this.@" + propertyTemplate.getOriginalName() + ".propertyValue");
        groovyImplementation = StringUtils.replaceIgnoreCase(groovyImplementation, "$rawValue", "this.@" + propertyTemplate.getOriginalName() + ".rawValue");
        groovyImplementation = StringUtils.replaceIgnoreCase(groovyImplementation, "$propType", "this.@" + propertyTemplate.getOriginalName() + ".rawType");
        groovyImplementation = StringUtils.replaceIgnoreCase(groovyImplementation, "$returnOrNothing", (method.getReturnType() == void.class) ? "" : "return");
        groovyImplementation = StringUtils.replaceIgnoreCase(groovyImplementation, "$nullOrNothing", (method.getReturnType() == void.class) ? "" : "null");
        return groovyImplementation;
    }

    @SuppressWarnings("MagicNumber")
    private static String addMethodIndent(String classCode)
    {
        StringBuilder implStr = new StringBuilder(classCode.length() + (classCode.length() / 30));
        for (String s : StringUtils.splitPreserveAllTokens(classCode, '\n'))
        {
            implStr.append("    ").append(s).append("\n");
        }
        return implStr.toString();
    }

    /**
     * Simplified method that should be implemented to generate groovy implementation, string returned by this method can contain many placeholders that will be
     * later replaced with valid code: <br>
     * <ol><li>
     * $type - replaced with type name.
     * </li><li>
     * $property - replaced with property original name.
     * </li><li>
     * $propName - replaced with property name.
     * </li><li>
     * $value - replaced with code that access property value. {@code this.@$property.propertyValue}
     * </li><li>
     * $rawValue - replaced with code that access property value. {@code this.@$property.rawValue}
     * </li><li>
     * $propType - replaced with code that access property raw type (Class instance) {@code this.@$property.rawType}
     * </li><li>
     * $returnOrNothing - replaced with nothing if method return type is void, and with 'return' if it isn't void.
     * </li><li>
     * $nullOrNothing - replaced with nothing if method return type is void. and with 'null' if it isn't void.
     * </li></ol>
     * <br>
     * This method can also validate given types and throw exception if it can't implement it.
     *
     * @param method
     *         instance of method to implement.
     * @param propertyTemplate
     *         instance od property template to implement.
     * @param actionInstance
     *         instance of action to implement.
     *
     * @return groovy source code with placeholders.
     */
    @Language("groovy")
    protected String getGroovyImplementation0(MethodInvoker method, ConfigPropertyTemplate<?> propertyTemplate, ConfigPropertyActionInstance actionInstance)
    {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public String getActionName()
    {
        return this.name;
    }

    @Override
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (! (object instanceof AbstractPropertyAction))
        {
            return false;
        }
        AbstractPropertyAction that = (AbstractPropertyAction) object;
        return Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.name);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("name", this.name).append("patterns", this.patterns)
                                        .toString();
    }
}

