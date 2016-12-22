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

package org.diorite.config.impl.proxy;

import javax.annotation.Nullable;

import java.util.List;
import java.util.Map;

import org.diorite.config.Config;

class ConfigNode
{
    private final Map<String, ConfigNode> parent;

    private final String key;
    private final Class<?> type;
    @Nullable
    private Object value;

    ConfigNode(Map<String, ConfigNode> parent, String key, Class<?> type, @Nullable Object value)
    {
        this.parent = parent;
        this.key = key;
        this.type = type;
        this.value = value;
    }

    public String getKey()
    {
        return this.key;
    }

    public Class<?> getType()
    {
        return this.type;
    }

    @Nullable
    public Object getValue()
    {
        return this.value;
    }

    public void setValue(@Nullable Object value)
    {
        this.value = value;
    }

    @SuppressWarnings("PointlessNullCheck")
    boolean canStoreNodes()
    {
        return (this.value != null) && ((this.value instanceof Config) || (this.value instanceof Map) || (this.value instanceof List));
    }

    Object getNode(String key)
    {
        return null;
        // TODO
    }

}
