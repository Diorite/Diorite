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

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

class SimpleDeserializationData implements DeserializationData
{
    private final Serialization       serialization;
    private final Map<String, Object> dataMap;

    private final Set<String> trueValues  = new HashSet<>(1);
    private final Set<String> falseValues = new HashSet<>(1);

    SimpleDeserializationData(Serialization serialization, Map<String, Object> dataMap)
    {
        this.serialization = serialization;
        this.dataMap = dataMap;
    }

    @Override
    public Serialization getSerializationInstance()
    {
        return this.serialization;
    }

    @Override
    public void addTrueValues(String... strings)
    {
        Collections.addAll(this.trueValues, strings);
    }

    @Override
    public void addFalseValues(String... strings)
    {
        Collections.addAll(this.falseValues, strings);
    }

    @Nullable
    Boolean toBool(String str)
    {
        Boolean bool = this.serialization.toBool(str);
        if (bool != null)
        {
            return bool;
        }
        if (this.trueValues.contains(str))
        {
            return true;
        }
        if (this.falseValues.contains(str))
        {
            return false;
        }
        return null;
    }

    @Override
    public boolean containsKey(String key)
    {
        return this.dataMap.containsKey(key);
    }

    @Nullable
    @Override
    public <T> T get(String key, Class<T> type, @Nullable T def)
    {
        return null;
    }

    @Override
    public <T, C extends Collection<T>> void getAsCollection(String key, Class<T> type, C collection)
    {

    }

    @Override
    public <T, M extends Map<String, T>> void getAsMap(String key, Class<T> type, Function<? extends T, String> keyMapper, M map)
    {

    }

    @Override
    public <T, M extends Map<String, T>> void getAsMapWithKeys(String key, Class<T> type, String keyPropertyName, M map)
    {

    }

    @Override
    public <K, T, M extends Map<K, T>> void getMap(String key, Class<K> keyType, Class<T> type, M map)
    {

    }

    @Override
    public <K, T, M extends Map<K, T>> void getMap(String key, Function<String, K> keyMapper, Class<T> type, M map)
    {

    }

    @Override
    public Map<String, Object> asMap()
    {
        return new LinkedHashMap<>(this.dataMap);
    }
}
