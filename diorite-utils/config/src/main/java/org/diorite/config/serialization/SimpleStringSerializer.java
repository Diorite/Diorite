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

import java.util.function.Function;

class SimpleStringSerializer<T> implements StringSerializer<T>
{
    private final Class<T>            type;
    private final Function<String, T> deserializer;
    private final Function<T, String> serializer;

    SimpleStringSerializer(Class<T> type, Function<String, T> deserializer, Function<T, String> serializer)
    {
        this.type = type;
        this.deserializer = deserializer;
        this.serializer = serializer;
    }

    @Override
    public Class<T> getType()
    {
        return this.type;
    }

    @Override
    public Function<String, T> deserializerFunction()
    {
        return this.deserializer;
    }

    @Override
    public Function<T, String> serializerFunction()
    {
        return this.serializer;
    }

    @Override
    public T deserialize(String data)
    {
        return this.deserializer.apply(data);
    }

    @Override
    public String serialize(T data)
    {
        return this.serializer.apply(data);
    }
}
