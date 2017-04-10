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

package org.diorite.command.parser;

import java.util.Collections;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import org.diorite.commons.maps.CaseInsensitiveMap;

@SuppressWarnings({"rawtypes", "unchecked"})
class SimpleArgumentParseResultType implements ArgumentParseResultType
{
    static Map<String, SimpleArgumentParseResultType> results = Collections.synchronizedMap(new CaseInsensitiveMap<>(20));

    private final String id;

    SimpleArgumentParseResultType(String id)
    {
        ParsersManager manager = null;
        this.id = id.intern();
    }

    @Override
    public String getId()
    {
        return this.id;
    }

    @Override
    public <T> ArgumentParseResult<T> of(T object)
    {
        return new ArgumentParseResult<>(object, this, null);
    }

    @Override
    public <T> ArgumentParseResult<T> empty()
    {
        return new ArgumentParseResult<>(null, this, null);
    }

    @Override
    public <T> ArgumentParseResult<T> empty(ArgumentParseResult<?> last)
    {
        return new ArgumentParseResult<>(null, this, null, last);
    }

    @Override
    public <T> ArgumentParseResult<T> empty(Exception exception)
    {
        return new ArgumentParseResult<>(null, this, exception);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("id", this.id).toString();
    }
}
