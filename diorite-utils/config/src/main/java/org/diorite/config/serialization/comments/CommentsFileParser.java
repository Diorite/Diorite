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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;

class CommentsFileParser
{
    private final BufferedReader   reader;
    private final DocumentComments comments;

    CommentsFileParser(Reader reader)
    {
        this.reader = (reader instanceof BufferedReader) ? (BufferedReader) reader : new BufferedReader(reader);
        this.comments = new DocumentCommentsImpl();
    }

    @Nullable private StringBuilder currentComment;

    private final LinkedList<String> currentNodes = new LinkedList<>();
    @Nullable private String lastNode;
    private int indentLevel = 0;

    private boolean headerLock = false;
    private boolean footerLock = false;

    public DocumentComments parse()
    {
        if (this.indentLevel == Integer.MIN_VALUE)
        {
            throw new IllegalStateException("Parser was already used!");
        }
        try (this.reader)
        {
            this.parseFile();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        this.indentLevel = Integer.MIN_VALUE;
        return this.comments;
    }

    private void parseFile() throws IOException
    {
        while (true)
        {
            if (! this.parseLine())
            {
                break;
            }
        }
    }

    private int setIndent(String line)
    {
        int indent = 0;
        char[] chars = line.toCharArray();
        int index = 0;
        while (chars[index++] == ' ')
        {
            indent++;
        }
        if ((indent % 2) == 1)
        {
            throw new CommentsParserException("File should use indent of 2 spaces per level");
        }
        int newLevel = indent / 2;
        int result = newLevel - this.indentLevel;
        this.indentLevel = newLevel;
        return result;
    }

    private boolean parseLine() throws IOException
    {
        String line = this.reader.readLine();

        String rawLine = line;

        if (line == null)
        {
            if ((this.currentComment != null) && ! this.footerLock)
            {
                this.footerLock = true;
                this.comments.setFooter(this.currentComment.toString().trim());
            }
            return false;
        }

        line = line.trim();
        if (line.isEmpty())
        {
            this.addCurrentComment();
            return true;
        }

        if (line.charAt(0) == '#')
        {
            if (this.currentComment == null)
            {
                this.currentComment = new StringBuilder(256);
            }
            else
            {
                this.currentComment.append('\n');
            }
            if (line.length() == 1)
            {
                return true;
            }
            if (line.charAt(1) == ' ')
            {
                line = line.substring(2);
            }
            else
            {
                line = line.substring(1);
            }
            this.currentComment.append(line);
            return true;
        }
        int indentChange = this.setIndent(rawLine);
        if ((indentChange == 0) && (this.indentLevel != 0) && (this.currentNodes.pollLast() == null))
        {
            throw new CommentsParserException("Can't parse comments, unexpected indent level change found on line: " + rawLine);
        }
        if (indentChange < 0)
        {
            indentChange -= 1;
        }
        while (indentChange++ < 0)
        {
            if (this.currentNodes.pollLast() == null)
            {
                throw new CommentsParserException("Can't parse comments, unexpected indent level change found on line: " + rawLine);
            }
        }

        char[] chars = line.toCharArray();
        StringBuilder keyBuilder = new StringBuilder(32);
        for (char aChar : chars)
        {
            if (aChar == ':')
            {
                break;
            }
            keyBuilder.append(aChar);
        }

        String key = keyBuilder.toString();
        if (key.trim().equals("*"))
        {
            key = CommentsNode.ANY;
        }
        this.currentNodes.add(key);
        this.lastNode = key;
        this.addCurrentComment();
        return true;
    }

    private void addCurrentComment()
    {
        if (this.currentComment != null)
        {
            if (this.lastNode == null)
            {
                if (! this.headerLock && this.currentNodes.isEmpty())
                {
                    this.headerLock = true;
                    this.comments.setHeader(this.currentComment.toString().trim());
                }
            }
            else
            {
                String[] strings = this.currentNodes.toArray(new String[this.currentNodes.size()]);
                this.comments.setComment(strings, this.currentComment.toString().trim());
                this.lastNode = null;
            }
            this.currentComment = null;
        }
    }
}
