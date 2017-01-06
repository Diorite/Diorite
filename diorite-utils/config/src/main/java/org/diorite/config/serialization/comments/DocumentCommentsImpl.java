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

package org.diorite.config.serialization.comments;

import javax.annotation.Nullable;

import java.io.IOException;
import java.io.Writer;

import org.apache.commons.lang3.StringUtils;

import org.diorite.config.annotations.SerializableAs;

/**
 * Root comments node.
 */
@SerializableAs(DocumentComments.class)
class DocumentCommentsImpl extends CommentsNodeImpl implements DocumentComments
{
    private String header = StringUtils.EMPTY;
    private String footer = StringUtils.EMPTY;

    @Override
    public String getFooter()
    {
        return this.footer;
    }

    @Override
    public void setFooter(@Nullable String footer)
    {
        if ((footer == null) || footer.trim().isEmpty())
        {
            footer = StringUtils.EMPTY;
        }
        this.footer = footer;
    }

    @Override
    public String getHeader()
    {
        return this.header;
    }

    @Override
    public void setHeader(@Nullable String header)
    {
        if ((header == null) || header.trim().isEmpty())
        {
            header = StringUtils.EMPTY;
        }
        this.header = header;
    }

    @Override
    public DocumentComments getRoot()
    {
        return this;
    }

    @Override
    public void writeTo(Writer writer) throws IOException
    {
        CommentsWriter commentsWriter = new CommentsWriter(writer, this);
        commentsWriter.writeAll();
    }

    @Override
    public DocumentComments copy()
    {
        DocumentCommentsImpl copy = new DocumentCommentsImpl();
        copy.dataMap.putAll(this.copyMap(copy));
        return copy;
    }
}
