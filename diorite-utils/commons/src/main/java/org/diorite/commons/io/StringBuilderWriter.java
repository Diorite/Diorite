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

package org.diorite.commons.io;

import java.io.IOException;
import java.io.Writer;

/**
 * A character stream that collects its output in a string buffer, which can
 * then be used to construct a string.
 * <p>
 * Closing a {@code StringWriter} has no effect. The methods in this class
 * can be called after the stream has been closed without generating an
 * {@code IOException}.
 */
public class StringBuilderWriter extends Writer
{
    private StringBuilder buf;

    /**
     * Create a new string writer using the default initial string-buffer
     * size.
     */
    public StringBuilderWriter() {
        this.buf = new StringBuilder();
        this.lock = this.buf;
    }

    /**
     * Create a new string writer using the specified initial string-buffer
     * size.
     *
     * @param initialSize
     *        The number of {@code char} values that will fit into this buffer
     *        before it is automatically expanded
     *
     * @throws IllegalArgumentException
     *         If {@code initialSize} is negative
     */
    public StringBuilderWriter(int initialSize) {
        if (initialSize < 0) {
            throw new IllegalArgumentException("Negative buffer size");
        }
        this.buf = new StringBuilder(initialSize);
        this.lock = this.buf;
    }

    /**
     * Write a single character.
     */
    @Override
    public void write(int c) {
        this.buf.append((char) c);
    }

    /**
     * Write a portion of an array of characters.
     *
     * @param  cbuf  Array of characters
     * @param  off   Offset from which to start writing characters
     * @param  len   Number of characters to write
     *
     * @throws  IndexOutOfBoundsException
     *          If {@code off} is negative, or {@code len} is negative,
     *          or {@code off + len} is negative or greater than the length
     *          of the given array
     */
    @Override
    public void write(char[] cbuf, int off, int len) {
        if ((off < 0) || (off > cbuf.length) || (len < 0) ||
            ((off + len) > cbuf.length) || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        }
        if (len == 0) {
            return;
        }
        this.buf.append(cbuf, off, len);
    }

    /**
     * Write a string.
     */
    @Override
    public void write(String str) {
        this.buf.append(str);
    }

    /**
     * Write a portion of a string.
     *
     * @param  str  String to be written
     * @param  off  Offset from which to start writing characters
     * @param  len  Number of characters to write
     *
     * @throws  IndexOutOfBoundsException
     *          If {@code off} is negative, or {@code len} is negative,
     *          or {@code off + len} is negative or greater than the length
     *          of the given string
     */
    @Override
    public void write(String str, int off, int len)  {
        this.buf.append(str, off, off + len);
    }

    /**
     * Appends the specified character sequence to this writer.
     *
     * <p> An invocation of this method of the form {@code out.append(csq)}
     * behaves in exactly the same way as the invocation
     *
     * <pre>
     *     out.write(csq.toString()) </pre>
     *
     * <p> Depending on the specification of {@code toString} for the
     * character sequence {@code csq}, the entire sequence may not be
     * appended. For instance, invoking the {@code toString} method of a
     * character buffer will return a subsequence whose content depends upon
     * the buffer's position and limit.
     *
     * @param  csq
     *         The character sequence to append.  If {@code csq} is
     *         {@code null}, then the four characters {@code "null"} are
     *         appended to this writer.
     *
     * @return  This writer
     *
     * @since  1.5
     */
    @Override
    public StringBuilderWriter append(CharSequence csq) {
        this.write(String.valueOf(csq));
        return this;
    }

    /**
     * Appends a subsequence of the specified character sequence to this writer.
     *
     * <p> An invocation of this method of the form
     * {@code out.append(csq, start, end)} when {@code csq}
     * is not {@code null}, behaves in
     * exactly the same way as the invocation
     *
     * <pre>{@code
     *     out.write(csq.subSequence(start, end).toString())
     * }</pre>
     *
     * @param  csq
     *         The character sequence from which a subsequence will be
     *         appended.  If {@code csq} is {@code null}, then characters
     *         will be appended as if {@code csq} contained the four
     *         characters {@code "null"}.
     *
     * @param  start
     *         The index of the first character in the subsequence
     *
     * @param  end
     *         The index of the character following the last character in the
     *         subsequence
     *
     * @return  This writer
     *
     * @throws  IndexOutOfBoundsException
     *          If {@code start} or {@code end} are negative, {@code start}
     *          is greater than {@code end}, or {@code end} is greater than
     *          {@code csq.length()}
     *
     * @since  1.5
     */
    @Override
    public StringBuilderWriter append(CharSequence csq, int start, int end) {
        if (csq == null)
        {
            csq = "null";
        }
        return this.append(csq.subSequence(start, end));
    }

    /**
     * Appends the specified character to this writer.
     *
     * <p> An invocation of this method of the form {@code out.append(c)}
     * behaves in exactly the same way as the invocation
     *
     * <pre>
     *     out.write(c) </pre>
     *
     * @param  c
     *         The 16-bit character to append
     *
     * @return  This writer
     *
     * @since 1.5
     */
    @Override
    public StringBuilderWriter append(char c) {
        this.write(c);
        return this;
    }

    /**
     * Return the buffer's current value as a string.
     */
    public String toString() {
        return this.buf.toString();
    }

    /**
     * Return the string builder itself.
     *
     * @return StringBuilder holding the current buffer value.
     */
    public StringBuilder getBuffer() {
        return this.buf;
    }

    /**
     * Flush the stream.
     */
    @Override
    public void flush() {
    }

    /**
     * Closing a {@code StringWriter} has no effect. The methods in this
     * class can be called after the stream has been closed without generating
     * an {@code IOException}.
     */
    @Override
    public void close() throws IOException
    {
    }

}
