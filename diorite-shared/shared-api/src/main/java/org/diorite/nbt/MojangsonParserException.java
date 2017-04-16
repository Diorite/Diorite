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

package org.diorite.nbt;

import org.apache.commons.lang3.StringUtils;

/**
 * Exception thrown if parsing of mojangson fail.
 */
public class MojangsonParserException extends RuntimeException
{
    private static final long serialVersionUID = 0;

    public MojangsonParserException()
    {
    }

    public MojangsonParserException(String message)
    {
        super(message);
    }

    public MojangsonParserException(String message, int index, String text)
    {
        super(prepareMessage(message, index, text));
    }

    public MojangsonParserException(String message, int index, String text, Throwable cause)
    {
        super(prepareMessage(message, index, text), cause);
    }

    public MojangsonParserException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public MojangsonParserException(Throwable cause)
    {
        super(cause);
    }

    public static String prepareMessage(String message, int index, String text)
    {
        int pos = index + 8;
        return message + '\n' + "input: `" + text + '`' + '\n' + StringUtils.repeat(" ", pos) + StringUtils.repeat("^", 3);
    }
}
