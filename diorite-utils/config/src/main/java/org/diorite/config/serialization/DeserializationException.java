/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.config.serialization;

import javax.annotation.Nullable;

/**
 * Serialization exception, used if deserialization fails.
 */
public class DeserializationException extends RuntimeException
{
    private static final long serialVersionUID = 0;

    public DeserializationException(Class<?> clazz, @Nullable DeserializationData data)
    {
        super(fixMessage(clazz, data, null));
    }

    public DeserializationException(Class<?> clazz, @Nullable DeserializationData data, @Nullable String message)
    {
        super(fixMessage(clazz, data, message));
    }

    public DeserializationException(Class<?> clazz, @Nullable DeserializationData data, @Nullable String message, Throwable cause)
    {
        super(fixMessage(clazz, data, message), cause);
    }

    public DeserializationException(Class<?> clazz, @Nullable DeserializationData data, Throwable cause)
    {
        super(fixMessage(clazz, data, null), cause);
    }

    public DeserializationException(Class<?> clazz, @Nullable DeserializationData data, @Nullable String message, Throwable cause, boolean enableSuppression,
                                    boolean writableStackTrace)
    {
        super(fixMessage(clazz, data, message), cause, enableSuppression, writableStackTrace);
    }

    public DeserializationException(Class<?> clazz, String data)
    {
        super(fixMessage(clazz, data, null));
    }

    public DeserializationException(Class<?> clazz, String data, @Nullable String message)
    {
        super(fixMessage(clazz, data, message));
    }

    public DeserializationException(Class<?> clazz, String data, @Nullable String message, Throwable cause)
    {
        super(fixMessage(clazz, data, message), cause);
    }

    public DeserializationException(Class<?> clazz, String data, Throwable cause)
    {
        super(fixMessage(clazz, data, null), cause);
    }

    public DeserializationException(Class<?> clazz, String data, @Nullable String message, Throwable cause, boolean enableSuppression,
                                    boolean writableStackTrace)
    {
        super(fixMessage(clazz, data, message), cause, enableSuppression, writableStackTrace);
    }

    private static String fixMessage(Class<?> clazz, @Nullable DeserializationData data, @Nullable String message)
    {
        StringBuilder sb = new StringBuilder(100);
        sb.append("Can't deserialize ").append(clazz.getName()).append(" class instance!");
        if (data != null)
        {
            sb.append("\n  Data map:\n    ").append(data);
        }
        sb.append("\n");
        if (message == null)
        {
            return sb.toString();
        }
        return sb.append("  Error: ").append(message).toString();
    }

    private static String fixMessage(Class<?> clazz, String data, @Nullable String message)
    {
        StringBuilder sb = new StringBuilder(100);
        sb.append("Can't deserialize ").append(clazz.getName()).append(" class instance from string: '").append(data).append("'!");
        if (message == null)
        {
            return sb.toString();
        }
        return sb.append("  Error: ").append(message).toString();
    }
}
