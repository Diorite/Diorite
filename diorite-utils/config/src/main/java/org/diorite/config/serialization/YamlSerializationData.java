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

package org.diorite.config.serialization;

import javax.annotation.Nullable;

import org.diorite.config.serialization.comments.DocumentComments;

public class YamlSerializationData extends SimpleSerializationData
{
    YamlSerializationData(Serialization serialization, Class<?> type)
    {
        super(SerializationType.YAML, serialization, type);
    }

    public <T> void addRaw(String key, @Nullable T value)
    {
        this.validateMap(key);
        if (value == null)
        {
            this.dataMap.put(key, null);
            return;
        }
        if (Serialization.isSimple(value))
        {
            this.dataMap.put(key, this.toString(value, null));
            return;
        }

        Class<?> valueClass = value.getClass();
        DocumentComments comments = this.addComments(valueClass, key);

        if (! this.serialization.isSerializable(value))
        {
            if (this.serialization.isStringSerializable(value))
            {
                this.dataMap.put(key, this.serialization.serializeToString(value));
                return;
            }
            if (this.serialization.canBeSerialized(valueClass))
            {
                this.dataMap.put(key, value);
                this.joinComments(key, comments);
                return;
            }
            throw new SerializationException(this.type, "Given value must be serializable! key: " + key + ", value: " + value);
        }
        this.dataMap.put(key, this.serialize(value, comments));
        this.joinComments(key, comments);
    }

    public <T> void addRaw(String key, @Nullable T value, Class<T> type)
    {
        this.validateMap(key);
        if (value == null)
        {
            this.dataMap.put(key, null);
            return;
        }
        if (Serialization.isSimple(value))
        {
            this.dataMap.put(key, this.toString(value, type));
            return;
        }

        DocumentComments comments = this.addComments(value.getClass(), key);

        if (! this.serialization.isSerializable(type))
        {
            if (this.serialization.isStringSerializable(type))
            {
                this.dataMap.put(key, this.serialization.serializeToString(type, value));
                return;
            }
            if (this.serialization.canBeSerialized(value.getClass()))
            {
                this.dataMap.put(key, value);
                this.joinComments(key, comments);
                return;
            }
            throw new SerializationException(this.type, "Given value must be serializable! key: " + key + ", value: (" + type.getName() + ") -> " + value);
        }
        this.dataMap.put(key, this.serialize(type, value, comments));
        this.joinComments(key, comments);
    }

}
