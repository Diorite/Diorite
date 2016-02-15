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

package org.diorite.cfg;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.yaml.DioriteYaml;

public class SimpleConfigManager implements ConfigManager
{
    protected final DioriteYaml yaml;

    public SimpleConfigManager()
    {
        this.yaml = new DioriteYaml();
    }

    public SimpleConfigManager(final DioriteYaml yaml)
    {
        this.yaml = yaml;
    }

    @Override
    public DioriteYaml getYaml()
    {
        return this.yaml;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T load(final Reader reader) throws IOException
    {
        return (T) this.yaml.load(reader);
    }

    @Override
    public <T> T load(final Class<T> clazz, final Reader reader) throws IOException
    {
        return this.yaml.loadAs(reader, clazz);
    }

    @Override
    public void save(final Writer writer, final Object object) throws IOException
    {
        if (object instanceof Map)
        {
            writer.write(this.yaml.dumpAsMap(object));
            writer.flush();
        }
        else
        {
            this.yaml.dump(object, writer);
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("yaml", this.yaml).toString();
    }
}
