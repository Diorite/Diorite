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

/**
 * Represent config value.
 *
 * @param <T>
 *         type of value.
 */
public interface ConfigPropertyValue<T>
{
    /**
     * Returns property template.
     *
     * @return property template.
     */
    ConfigPropertyTemplate<T> getProperty();

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
    void setRawValue(@Nullable T value);

    /**
     * Look for nested value on given path and set their value to given one.
     *
     * @param path
     *         path of value.
     * @param value
     *         new value.
     *
     * @throws IllegalStateException
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
     * @throws IllegalStateException
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
     * @throws IllegalStateException
     *         if property do not contain given path, or property can not nbe removed.
     */
    @Nullable
    Object remove(String[] path) throws IllegalStateException;
}
