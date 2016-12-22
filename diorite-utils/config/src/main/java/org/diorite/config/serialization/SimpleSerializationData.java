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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

class SimpleSerializationData implements SerializationData
{
    private final Serialization serialization;

    private final Map<String, Object> dataMap = new LinkedHashMap<>(20);

    private String trueValue = "true";
    private String falseValue = "false";

    SimpleSerializationData(Serialization serialization)
    {
        this.serialization = serialization;
    }

    @Override
    public void setTrueValue(String str)
    {
        this.trueValue = str;
    }

    @Override
    public void setFalseValue(String str)
    {
        this.falseValue = str;
    }

    @Override
    public Serialization getSerializationInstance()
    {
        return this.serialization;
    }

    @Override
    public <T> void add(String key, @Nullable T value)
    {

    }

    @Override
    public <T> void add(String key, Class<T> type, @Nullable T value)
    {

    }

    @Override
    public <T> void addMappedList(String key, Class<T> type, List<? extends T> value, Function<? extends T, String> mapper)
    {

    }

    @Override
    public <T> void addMapAsList(Map<String, ? extends T> value, Class<T> type)
    {

    }

    @Override
    public <T> void addMapAsListWithKeys(Map<String, ? extends T> value, Class<T> type, String keyPropertyName)
    {

    }

    @Override
    public <K, T> void addMap(Map<? extends K, ? extends T> value, Class<K> keyType, Class<T> type)
    {

    }

    @Override
    public <K, T> void addMap(Map<? extends K, ? extends T> value, Class<T> type)
    {

    }

    @Override
    public <K, T> void addMap(Map<? extends K, ? extends T> value, Class<T> type, Function<K, String> keyMapper)
    {

    }

    @Override
    public Map<String, Object> toMap()
    {
        return new LinkedHashMap<>(this.dataMap);
    }
}
