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
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

import org.diorite.cfg.system.CfgEntryData;

/**
 * Template handler for all uri objects.
 *
 * @see URI
 */
public class URITemplateElement extends TemplateElement<URI>
{
    /**
     * Instance of template to direct-use.
     */
    public static final URITemplateElement INSTANCE = new URITemplateElement();

    /**
     * Construct new default template handler.
     */
    public URITemplateElement()
    {
        super(URI.class);
    }

    @Override
    protected boolean canBeConverted0(final Class<?> c)
    {
        return false;
    }

    @Override
    protected URI convertObject0(final Object obj) throws UnsupportedOperationException
    {
        throw this.getException(obj);
    }

    @Override
    protected URI convertDefault0(final Object obj, final Class<?> fieldType)
    {
        if (obj instanceof URI)
        {
            return ((URI) obj);
        }
        if (obj instanceof URL)
        {
            try
            {
                return ((URL) obj).toURI();
            } catch (final URISyntaxException e)
            {
                throw this.getException(obj, e);
            }
        }
        if (obj instanceof Path)
        {
            return ((Path) obj).toUri();
        }
        if (obj instanceof File)
        {
            return ((File) obj).toURI();
        }
        if (obj instanceof String)
        {
            try
            {
                return new URI(obj.toString());
            } catch (final URISyntaxException e)
            {
                throw this.getException(obj, e);
            }
        }
        throw this.getException(obj);
    }

    @Override
    public void appendValue(final Appendable writer, final CfgEntryData field, final Object source, final Object elementRaw, final int level, final ElementPlace elementPlace) throws IOException
    {
        final URI element = (elementRaw instanceof URI) ? ((URI) elementRaw) : this.validateType(elementRaw);
        StringTemplateElement.INSTANCE.appendValue(writer, field, source, StringTemplateElement.INSTANCE.validateType(element.toString()), level, elementPlace);
    }

}
