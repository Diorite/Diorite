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

package org.diorite.config.impl;

import java.io.Reader;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

import org.diorite.config.Config;
import org.diorite.config.ConfigTemplate;

public class ConfigTemplateImpl<T extends Config> implements ConfigTemplate<T>
{
    private final Class<T>                     type;
    private       String                       name;
    private       CharsetEncoder               charsetEncoder;
    private       CharsetDecoder               charsetDecoder;
    private final ConfigImplementationProvider implementationProvider;

    public ConfigTemplateImpl(Class<T> type, ConfigImplementationProvider provider)
    {
        this.type = type;
        this.implementationProvider = provider;
        this.name = type.getSimpleName();
        this.setEncoding(StandardCharsets.UTF_8);
    }

    @Override
    public Class<T> getConfigType()
    {
        return this.type;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public CharsetEncoder getDefaultEncoder()
    {
        return this.charsetEncoder;
    }

    @Override
    public void setDefaultEncoder(CharsetEncoder encoder)
    {
        this.charsetEncoder = encoder;
    }

    @Override
    public CharsetDecoder getDefaultDecoder()
    {
        return this.charsetDecoder;
    }

    @Override
    public void setDefaultDecoder(CharsetDecoder decoder)
    {
        this.charsetDecoder = decoder;
    }

    @Override
    public T load(Reader reader)
    {
        T inst = this.implementationProvider.createImplementation(this.type, this);
        return inst;
    }

}