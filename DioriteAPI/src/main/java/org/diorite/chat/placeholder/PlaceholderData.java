/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.chat.placeholder;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.Diorite;
import org.diorite.chat.component.BaseComponent;

/**
 * Represent placeholder found in string, it contains full string to replace, object name and placeholder item.
 *
 * @param <T> type of placeholder item.
 */
public class PlaceholderData<T>
{
    private static final Map<String, PlaceholderData<?>> cache = new HashMap<>(100, .2f);

    private final String             fullName;
    private final String             objectName;
    private final PlaceholderItem<T> item;

    private PlaceholderData(final String fullName, final String objectName, final PlaceholderItem<T> item)
    {
        this.fullName = fullName;
        this.objectName = objectName;
        this.item = item;
    }

    /**
     * Returns full string to replace.
     *
     * @return full string to replace.
     */
    public String getFullName()
    {
        return (this.fullName == null) ? this.item.getType().getId() : this.fullName;
    }

    /**
     * Returns object name, like "player" for player.name, or "killer" for player#killer.name.
     *
     * @return object name.
     */
    public String getObjectName()
    {
        return this.objectName;
    }

    /**
     * Returns placeholder item used in this placeholder.
     *
     * @return placeholder item used in this placeholder.
     */
    public PlaceholderItem<T> getItem()
    {
        return this.item;
    }

    /**
     * Delegated method from {@link PlaceholderItem}
     *
     * @param obj object to fetch the data needed for placeholder.
     *
     * @return Object to use instead of placeholder.
     *
     * @see PlaceholderItem#apply(Object)
     */
    public Object apply(final T obj)
    {
        return this.item.apply(obj);
    }

    /**
     * Uses this placeholder on given string.
     *
     * @param str string to use.
     * @param obj object to fetch the data needed for placeholder.
     *
     * @return string with replaced placeholder.
     */
    public String replace(final String str, final T obj)
    {
        final Object result = this.apply(obj);
        if (result instanceof BaseComponent)
        {
            return StringUtils.replace(str, this.fullName, ((BaseComponent) result).toLegacyText());
        }
        else
        {
            return StringUtils.replace(str, this.fullName, result.toString());
        }
    }

    /**
     * Uses this placeholder on given {@link BaseComponent}. <br>
     * NOTE: placeholder is replaced without duplicating given component, it will make all changes on given component.
     *
     * @param component component to use.
     * @param obj       object to fetch the data needed for placeholder.
     *
     * @return this same component as given.
     */
    public BaseComponent replace(final BaseComponent component, final T obj)
    {
        final Object result = this.apply(obj);
        if (result instanceof BaseComponent)
        {
            component.replace(this.fullName, (BaseComponent) result);
        }
        else
        {
            component.replace(this.fullName, result.toString());
        }
        return component;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof PlaceholderData))
        {
            return false;
        }

        final PlaceholderData<?> that = (PlaceholderData<?>) o;

        return this.fullName.equals(that.fullName);
    }

    @Override
    public int hashCode()
    {
        return this.fullName.hashCode();
    }

    @SuppressWarnings("unchecked")
    public static <T> PlaceholderData<T> valueOf(final String fullName, final String objectName, final PlaceholderItem<T> item)
    {
        {
            final PlaceholderData<?> cached = cache.get(fullName);
            if (cached != null)
            {
                return (PlaceholderData<T>) cached;
            }
        }
        final PlaceholderData<T> result = new PlaceholderData<>(fullName, objectName, item);
        cache.put(fullName, result);
        return result;
    }

    /**
     * Get collection of used placeholders in given string.
     *
     * @param str  string to parse.
     * @param warn if true, method will print warnings about invalid placeholders to console.
     *
     * @return collection of used placeholders, {@link PlaceholderData}
     *
     * @throws NullPointerException of string is null.
     */
    public static Collection<PlaceholderData<?>> getPlaceholdersKeys(final String str, final boolean warn)
    {
        Validate.notNull(str, "String can't be null");
        final Collection<PlaceholderData<?>> keys = new HashSet<>(10, .2f);
        StringBuilder key = null;
        char lastChar = '\u0000';
        for (final char c : str.toCharArray())
        {
            if (key == null)
            {
                if (c == '$')
                {
                    if (lastChar != '\\')
                    {
                        lastChar = c;
                    }
                    continue;
                }
                if ((lastChar == '$') && (c == '<'))
                {
                    key = new StringBuilder(64);
                    lastChar = c;
                    continue;
                }
                lastChar = c;
                continue;
            }
            if (c == '>')
            {
                final String string = key.toString();
                int index = string.indexOf('.');
                final String value = string.substring(index + 1);
                final String typeID;
                final String typeName;
                String fullName = "$<";
                {
                    final String type = string.substring(0, index);
                    index = type.indexOf('#');
                    if (index == - 1)
                    {
                        fullName += type;
                        typeID = type;
                        typeName = type;
                    }
                    else
                    {
                        typeID = type.substring(0, index);
                        typeName = type.substring(index + 1);
                        fullName += typeID + "#" + typeName;
                    }
                }
                fullName += ">";
                final PlaceholderData<?> cached = cache.get(fullName);
                if (cached == null)
                {
                    final PlaceholderType<?> placeholderType = PlaceholderType.get(typeID);
                    if (placeholderType != null)
                    {
                        final PlaceholderItem<?> item = placeholderType.getItem(value);
                        if (item != null)
                        {
                            keys.add(valueOf(fullName, typeName, item));
                        }
                        else if (warn)
                        {
                            Diorite.getCore().getLogger().warn("Unknown placeholder item (" + value + ") in: " + fullName + ", in string: " + str);
                        }
                    }
                    else if (warn)
                    {
                        Diorite.getCore().getLogger().warn("Unknown placeholder type (" + typeID + ") in: " + fullName + ", in string: " + str);
                    }
                }
                key = null;
            }
            else if (((c >= '0') && (c <= '9')) || ((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z')) || (c == '_') || (c == '.'))
            {
                key.append(c);
            }
            else
            {
                key = null;
            }
            lastChar = c;
        }
        return keys;
    }

    /**
     * Get collectiona of used placeholders grouped by object name in given string. <br>
     * Will return null if null string is given.
     *
     * @param str  string to parse.
     * @param warn if true, method will print warnings about invalid placeholders to console.
     *
     * @return collection of used placeholders. {@link PlaceholderData}
     */
    public static Map<String, Collection<PlaceholderData<?>>> parseString(final String str, final boolean warn)
    {
        if (str == null)
        {
            return null;
        }
        final Map<String, Collection<PlaceholderData<?>>> result = new HashMap<>(3, .1f);
        StringBuilder key = null;
        char lastChar = '\u0000';
        for (final char c : str.toCharArray())
        {
            if (key == null)
            {
                if (c == '$')
                {
                    if (lastChar != '\\')
                    {
                        lastChar = c;
                    }
                    continue;
                }
                if ((lastChar == '$') && (c == '<'))
                {
                    key = new StringBuilder(64);
                    lastChar = c;
                    continue;
                }
                lastChar = c;
                continue;
            }
            if (c == '>')
            {
                final String string = key.toString();
                int index = string.indexOf('.');
                final String value = string.substring(index + 1);
                final String typeID;
                final String typeName;
                String fullName = "$<";
                {
                    final String type = string.substring(0, index);
                    index = type.indexOf('#');
                    if (index == - 1)
                    {
                        fullName += type;
                        typeID = type;
                        typeName = type;
                    }
                    else
                    {
                        typeID = type.substring(0, index);
                        typeName = type.substring(index + 1);
                        fullName += typeID + "#" + typeName;
                    }
                }
                fullName += "." + value + ">";
                PlaceholderData<?> data = cache.get(fullName);
                if (data == null)
                {
                    final PlaceholderType<?> placeholderType = PlaceholderType.get(typeID);
                    if (placeholderType != null)
                    {
                        final PlaceholderItem<?> item = placeholderType.getItem(value);
                        if (item != null)
                        {
                            data = valueOf(fullName, typeName, item);
                        }
                        else if (warn)
                        {
                            Diorite.getCore().getLogger().warn("Unknown placeholder item (" + value + ") in: " + fullName + ", in string: " + str);
                        }
                    }
                    else if (warn)
                    {
                        Diorite.getCore().getLogger().warn("Unknown placeholder type (" + typeID + ") in: " + fullName + ", in string: " + str);
                    }
                }
                if (data != null)
                {
                    Collection<PlaceholderData<?>> collection = result.get(data.objectName);
                    if (collection == null)
                    {
                        collection = new HashSet<>(5, .1f);
                        result.put(data.objectName, collection);
                    }
                    collection.add(data);
                }
                key = null;
            }
            else if (((c >= '0') && (c <= '9')) || ((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z')) || (c == '_') || (c == '.'))
            {
                key.append(c);
            }
            else
            {
                key = null;
            }
            lastChar = c;
        }
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("fullName", this.fullName).append("type", this.item.getType().getType().getName()).toString();
    }
}
