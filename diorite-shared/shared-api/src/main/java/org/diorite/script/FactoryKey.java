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

package org.diorite.script;

import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;

import org.diorite.plugin.Plugin;

/**
 * Represents factory key.
 */
public final class FactoryKey
{
    public static String WILDCARD = "*";

    private final String namespace;
    private final String language;

    private FactoryKey(String namespace, String language)
    {
        this.namespace = namespace;
        this.language = language;
    }

    public String getNamespace()
    {
        return this.namespace;
    }

    public String getLanguage()
    {
        return this.language;
    }

    /**
     * Returns true if this key matches given factory.
     *
     * @param factory
     *         factory to check.
     *
     * @return true if this key matches given factory.
     */
    public boolean isMatching(ScriptEngineFactory factory)
    {
        String namespace = this.namespace;
        if (namespace.equals(WILDCARD))
        {
            namespace = factory.getPlugin().getName();
        }
        return (namespace.equalsIgnoreCase(factory.getPlugin().getName()) && this.language.equalsIgnoreCase(factory.getLanguage()));
    }

    /**
     * Returns key representing any factory that supports given language.
     *
     * @param language
     *         language of script engine factory.
     *
     * @return key representing any factory that supports given language.
     */
    public static FactoryKey any(String language)
    {
        return new FactoryKey(WILDCARD, language);
    }

    /**
     * Returns key representing factory that supports given language provided by given plugin.
     *
     * @param plugin
     *         plugin that provides factory.
     * @param language
     *         language of script engine factory.
     *
     * @return key representing factory that supports given language provided by given plugin.
     */
    public static FactoryKey implementedBy(Plugin plugin, String language)
    {
        return implementedBy(plugin.getName(), language);
    }

    /**
     * Returns key representing factory that supports given language provided by given plugin.
     *
     * @param plugin
     *         plugin that provides factory.
     * @param language
     *         language of script engine factory.
     *
     * @return key representing factory that supports given language provided by given plugin.
     */
    public static FactoryKey implementedBy(String plugin, String language)
    {
        return new FactoryKey(plugin, language);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof FactoryKey))
        {
            return false;
        }
        FactoryKey that = (FactoryKey) o;
        return Objects.equals(this.namespace, that.namespace) &&
               Objects.equals(this.language, that.language);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.namespace, this.language);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("namespace", this.namespace).append("language", this.language).toString();
    }
}
