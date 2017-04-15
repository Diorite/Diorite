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

package org.diorite.message;

/**
 * Represent message variable, if name isn't provided name of class will be used, or name provided by annotation. <br>
 * This interface can be implemented by different classes to simplify usage of them as message values.
 *
 * @param <T>
 *         Type of message value.
 */
public interface MessageData<T>
{
    /**
     * Returns key used in message to access this message value.
     *
     * @return key used in message to access this message value.
     */
    String getMessageKey();

    /**
     * Returns message value instance, if class is implementing this interface it should return itself. (default implementation)
     *
     * @return message value instance.
     */
    @SuppressWarnings("unchecked")
    default T getMessageValue()
    {
        return (T) this;
    }

    /**
     * Returns new message data with this same object but different key.
     *
     * @param key
     *         key used in message to access this message value.
     *
     * @return new message data instance.
     */
    default MessageData<T> asMessageData(String key)
    {
        return of(key, this.getMessageValue());
    }

    /**
     * Returns new message data for given key and object.
     *
     * @param name
     *         key used in message to access this message value.
     * @param object
     *         message value instance.
     * @param <T>
     *         type of value.
     *
     * @return new message data instance.
     */
    static <T> MessageData<T> of(String name, T object)
    {
        return new SimpleMessageData<>(name, object);
    }
}
