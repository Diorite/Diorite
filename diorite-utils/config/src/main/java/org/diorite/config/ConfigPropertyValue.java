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

import javax.annotation.Nullable;

import java.lang.reflect.Type;

import org.diorite.config.exceptions.ValidationException;
import org.diorite.config.serialization.DeserializationData;
import org.diorite.config.serialization.SerializationData;

/**
 * Represent config value.
 *
 * @param <T>
 *         type of value.
 */
public interface ConfigPropertyValue<T>
{
    /**
     * Returns instance of config where this value is declared.
     *
     * @return instance of config where this value is declared.
     */
    Config getDeclaringConfig();

    /**
     * Returns property template.
     *
     * @return property template.
     */
    ConfigPropertyTemplate<T> getProperty();

    /**
     * Returns property value after needed transformations by template.
     *
     * @return property value after needed transformations by template.
     */
    @Nullable
    default T getPropertyValue()
    {
        return this.getProperty().get(this);
    }

    /**
     * Sets property value after needed transformations by template.
     *
     * @param value
     *         new value.
     */
    default void setPropertyValue(@Nullable T value) throws ValidationException
    {
        this.getProperty().set(this, value);
    }

    /**
     * Returns raw property value.
     *
     * @return raw property value.
     */
    @Nullable
    T getRawValue();

    /**
     * Set raw property value.
     *
     * @param value
     *         new value.
     */
    void setRawValue(@Nullable T value) throws ValidationException;

    /**
     * Look for nested value on given path and set their value to given one.
     *
     * @param path
     *         path of value.
     * @param value
     *         new value.
     *
     * @exception IllegalStateException
     *         if property do not contain given path.
     */
    void set(String[] path, @Nullable Object value) throws IllegalStateException;

    /**
     * Returns object on given nested path.
     *
     * @param path
     *         path of value.
     *
     * @return object on given nested path.
     *
     * @exception IllegalStateException
     *         if property do not contain given path.
     */
    @Nullable
    Object get(String[] path) throws IllegalStateException;

    /**
     * Removes and returns object on given nested path.
     *
     * @param path
     *         path of value.
     *
     * @return removed object on given nested path.
     *
     * @exception IllegalStateException
     *         if property do not contain given path, or property can not nbe removed.
     */
    @Nullable
    Object remove(String[] path) throws IllegalStateException;

    /**
     * Returns raw type of config property.
     *
     * @return raw type of config property.
     */
    default Class<T> getRawType()
    {
        return this.getProperty().getRawType();
    }

    /**
     * Returns generic type of config property.
     *
     * @return generic type of config property.
     */
    default Type getGenericType()
    {
        return this.getProperty().getGenericType();
    }

    /**
     * Returns name of property.
     *
     * @return name of property.
     */
    default String getName()
    {
        return this.getProperty().getName();
    }

    /**
     * Generate and returns default value for this template. <br>
     * This value should never return null for primitive types!
     *
     * @return default value for this template.
     */
    @Nullable
    default T getDefault()
    {
        return this.getProperty().getDefault(this.getDeclaringConfig());
    }

    /**
     * Serialize given value to given serialization object using valid style and type.
     *
     * @param data
     *         serialization data instance.
     */
    default void serialize(SerializationData data)
    {
        this.getProperty().serialize(data, this);
    }

    /**
     * Deserialize value from given deserialization object.
     *
     * @param data
     *         deserialization data instance.
     */
    default void deserialize(DeserializationData data)
    {
        this.getProperty().deserialize(data, this);
    }
}
