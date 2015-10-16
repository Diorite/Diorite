/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.impl.log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import org.slf4j.Logger;

@SuppressWarnings({"ClassHasNoToStringMethod", "ObjectEquality"})
public class LoggerOutputStream extends ByteArrayOutputStream
{
    private final String separator = System.getProperty("line.separator");
    private final Logger logger;
    private final Level  level;

    public LoggerOutputStream(final Logger logger, final Level level)
    {
        this.logger = logger;
        this.level = level;
    }

    @Override
    public void flush() throws IOException
    {
        synchronized (this)
        {
            super.flush();
            final String record = this.toString();
            super.reset();
            if ((! record.isEmpty()) && (! record.equals(this.separator)))
            {
                if (this.level == Level.INFO)
                {
                    this.logger.info(record.endsWith(this.separator) ? record.substring(0, record.length() - this.separator.length()) : record);
                }
                else if (this.level == Level.WARNING)
                {
                    this.logger.warn(record.endsWith(this.separator) ? record.substring(0, record.length() - this.separator.length()) : record);
                }
            }
        }
    }

//    @Override
//    public String toString()
//    {
//        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("logger", this.logger).append("separator", this.separator).append("level", this.level).toString();
//    }
}
