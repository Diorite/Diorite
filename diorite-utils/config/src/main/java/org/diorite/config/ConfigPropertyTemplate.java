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

import org.diorite.config.serialization.DeserializationData;
import org.diorite.config.serialization.SerializationData;

/**
 * Represents config property, each property can support multiple config property actions. <br>
 * Config properties don't need to support get or set operations at all.
 *
 * @param <T>
 *         type of property.
 */
public interface ConfigPropertyTemplate<T>
{
    /**
     * Returns raw type of config property.
     *
     * @return raw type of config property.
     */
    Class<T> getRawType();

    /**
     * Returns generic type of config property.
     *
     * @return generic type of config property.
     */
    Type getGenericType();

    /**
     * Returns name of property.
     *
     * @return name of property.
     */
    String getName();

    /**
     * Append new validator to this property template.
     *
     * @param validator
     *         validator of property.
     */
    void appendValidator(ValidatorFunction<Config, T> validator);

    /**
     * Prepend new validator to this property template.
     *
     * @param validator
     *         validator of property.
     */
    void prependValidator(ValidatorFunction<Config, T> validator);

    ValidatorFunction<Config, T> getValidator();

    /**
     * Returns original name of property, if name of property was changed by same annotation, this method will still return original one.
     *
     * @return original name of property.
     */
    String getOriginalName();

    /**
     * Generate and returns default value for this template. <br>
     * This value should never return null for primitive types!
     *
     * @param config
     *         config instance.
     *
     * @return default value for this template.
     */
    @Nullable
    T getDefault(Config config);

    /**
     * Set given value to given property value, template might edit given value according to template settings.
     *
     * @param propertyValue
     *         template value instance.
     * @param value
     *         new value to set.
     */
    void set(ConfigPropertyValue<T> propertyValue, @Nullable T value);

    /**
     * Get balue from given property value, template might edit value before returning according to template settings.
     *
     * @param propertyValue
     *         template value instance.
     *
     * @return value of property value.
     */
    @Nullable
    T get(ConfigPropertyValue<T> propertyValue);

    /**
     * Serialize given value to given serialization object using valid style and type.
     *
     * @param data
     *         serialization data instance.
     * @param value
     *         config property value.
     */
    void serialize(SerializationData data, ConfigPropertyValue<T> value);

    /**
     * Deserialize value from given deserialization object.
     *
     * @param data
     *         deserialization data instance.
     * @param value
     *         config property value.
     */
    void deserialize(DeserializationData data, ConfigPropertyValue<T> value);
}
