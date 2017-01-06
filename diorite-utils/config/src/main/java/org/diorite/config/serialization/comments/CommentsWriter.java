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

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;

class CommentsWriter
{
    private final BufferedWriter                      writer;
    private final DocumentComments                    comments;
    private final Map<String, MutablePair<String, ?>> dataMap;

    private int    indent       = - 1;
    private String cachedIndent = "";

    private boolean lineLock = false;

    CommentsWriter(Writer writer, DocumentCommentsImpl comments)
    {
        this.writer = (writer instanceof BufferedWriter) ? ((BufferedWriter) writer) : new BufferedWriter(writer);
        this.comments = comments;
        this.dataMap = comments.buildMap();
    }

    private void writeNewLine(boolean force) throws IOException
    {
        if (this.lineLock && ! force)
        {
            return;
        }
        this.lineLock = true;
        this.writer.newLine();
    }

    private void writeComment(String comment) throws IOException
    {
        if (comment.isEmpty())
        {
            return;
        }
        String[] strings = StringUtils.splitPreserveAllTokens(comment, '\n');
        int i = 1;
        int size = strings.length;
        for (String string : strings)
        {
            this.writer.write(this.cachedIndent);
            this.writer.write("# ");
            this.writer.write(string);
            this.writeNewLine(true);
        }
        this.lineLock = false;
    }

    private void writeKey(String key) throws IOException
    {
        this.writer.write(this.cachedIndent);
        this.writer.write(key);
        this.writer.write(':');
        this.writeNewLine(true);
        this.lineLock = false;
    }

    public void writeAll() throws IOException
    {
        this.write(this.dataMap);
        this.writer.flush();
    }

    private void updateIndent(boolean up)
    {
        if (up)
        {
            this.cachedIndent = StringUtils.repeat(' ', (++ this.indent) * 2);
        }
        else
        {
            this.cachedIndent = StringUtils.repeat(' ', (-- this.indent) * 2);
        }
    }

    @SuppressWarnings("unchecked")
    private void write(Map<String, MutablePair<String, ?>> map) throws IOException
    {
        this.updateIndent(true);
        int keys = map.entrySet().size();
        int k = 0;
        for (Entry<String, MutablePair<String, ?>> entry : map.entrySet())
        {
            k += 1;
            MutablePair<String, ?> pair = entry.getValue();
            String comment = pair.getLeft();
            Map<String, MutablePair<String, ?>> rightMap = (Map<String, MutablePair<String, ?>>) pair.getRight();

            int rightKeys = (rightMap == null) ? 0 : rightMap.size();
            boolean newLine = keys > 3;
            if (comment != null)
            {
                this.writeComment(comment);
            }

            String key = entry.getKey();
            this.writeKey(key);
            if (rightMap != null)
            {
                this.write(rightMap);
            }
            if (newLine)
            {
                this.writeNewLine(false);
            }
        }
        this.writeNewLine(false);
        this.updateIndent(false);
    }
}