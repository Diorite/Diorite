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

package org.diorite.cfg.system.elements;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;

import org.diorite.cfg.system.CfgEntryData;

/**
 * Template handler for all path/file objects.
 *
 * @see Path
 * @see File
 */
public class PathTemplateElement extends TemplateElement<Path>
{
    /**
     * Instance of template to direct-use.
     */
    public static final PathTemplateElement INSTANCE = new PathTemplateElement();

    /**
     * Construct new default template handler.
     */
    public PathTemplateElement()
    {
        super(Path.class, obj -> {
            if (obj instanceof File)
            {
                return ((File) obj).toPath();
            }
            throw new UnsupportedOperationException("Can't convert object (" + obj.getClass().getName() + ") to Path: " + obj);
        }, c -> Path.class.isAssignableFrom(c) || File.class.isAssignableFrom(c));
    }

    @Override
    protected Path convertDefault0(final Object def, final Class<?> fieldType)
    {
        try
        {
            if (def instanceof Path)
            {
                return (Path) def;
            }
            if (def instanceof File)
            {
                return ((File) def).toPath();
            }
            if (def instanceof String)
            {
                return new File(def.toString()).toPath();
            }
            if (def instanceof URI)
            {
                return new File(((URI) def).toURL().getFile()).toPath();
            }
            if (def instanceof URL)
            {
                return new File(((URL) def).getFile()).toPath();
            }
        } catch (final MalformedURLException e)
        {
            throw new RuntimeException("Can't convert default value (" + def.getClass().getName() + "): " + def, e);
        }
        throw new UnsupportedOperationException("Can't convert default value (" + def.getClass().getName() + "): " + def);
    }

    @Override
    protected void appendValue(final Appendable writer, final CfgEntryData field, final Object source, final Object elementRaw, final int level, final ElementPlace elementPlace) throws IOException
    {
        final Path element = (elementRaw instanceof Path) ? ((Path) elementRaw) : this.validateType(elementRaw);
        StringTemplateElement.INSTANCE.appendValue(writer, field, source, StringTemplateElement.INSTANCE.validateType(element.toString()), level, elementPlace);
    }

}
