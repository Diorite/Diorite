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

package org.diorite.commons;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

/**
 * Simple parser context class for easier parsers implementation.
 */
public class ParserContext implements CharacterIterator, Cloneable
{
    public static char NULL = '\0';
    public static char END  = StringCharacterIterator.DONE;

    protected final StringCharacterIterator iterator;
    protected final String                  data;

    public ParserContext(String data)
    {
        this.data = data;
        this.iterator = new StringCharacterIterator('\0' + data);
    }

    /**
     * Returns true if parsers contains more chars.
     *
     * @return true if parsers contains more chars.
     */
    public boolean hasNext()
    {
        return (this.iterator.getIndex() + 1) < this.iterator.getEndIndex();
    }

    /**
     * Returns raw text of parser.
     *
     * @return raw text of parser.
     */
    public String getText()
    {
        return this.data;
    }

    /**
     * Skips all whitespaces from current index.
     *
     * @return amount of skipped chars.
     */
    public int skipWhitespaces()
    {
        int i = 0;
        while (this.hasNext())
        {
            char c = this.next();
            if (Character.isWhitespace(c))
            {
                i++;
                continue;
            }
            this.previous();
            break;
        }
        return i;
    }

    @Override
    public char first()
    {
        return this.iterator.first();
    }

    @Override
    public char last()
    {
        return this.iterator.last();
    }

    @Override
    public char setIndex(int p)
    {
        return this.iterator.setIndex(p + 1);
    }

    @Override
    public char current()
    {
        return this.iterator.current();
    }

    @Override
    public char next()
    {
        return this.iterator.next();
    }

    @Override
    public char previous()
    {
        return this.iterator.previous();
    }

    @Override
    public int getBeginIndex()
    {
        return this.iterator.getBeginIndex() + 1;
    }

    @Override
    public int getEndIndex()
    {
        return this.iterator.getEndIndex() - 1;
    }

    @Override
    public int getIndex()
    {
        return this.iterator.getIndex() - 1;
    }

    @Override
    public ParserContext clone()
    {
        try
        {
            return (ParserContext) super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            throw new InternalError(e);
        }
    }
}
