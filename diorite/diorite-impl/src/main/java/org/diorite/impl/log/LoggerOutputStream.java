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

package org.diorite.impl.log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

@SuppressWarnings({"ClassHasNoToStringMethod", "ObjectEquality"})
public class LoggerOutputStream extends ByteArrayOutputStream
{
    private static final Marker UNKNOWN = MarkerFactory.getMarker("unknown");

    private final String separator = System.getProperty("line.separator");
    private final Logger logger;
    private final Level  level;

    public LoggerOutputStream(Logger logger, Level level)
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
            String record = this.toString();
            super.reset();
            if ((! record.isEmpty()) && (! record.equals(this.separator)))
            {
                String msg = record.endsWith(this.separator) ? record.substring(0, record.length() - this.separator.length()) : record;

                if (this.level == Level.SEVERE)
                {
                    this.logger.error(msg);
                }
                else if (this.level == Level.WARNING)
                {
                    this.logger.warn(msg);
                }
                else if (this.level == Level.INFO)
                {
                    this.logger.info(msg);
                }
                else if (this.level == Level.CONFIG)
                {
                    this.logger.debug(msg);
                }
                else if ((this.level == Level.FINE) || (this.level == Level.FINER) || (this.level == Level.FINEST))
                {
                    this.logger.trace(msg);
                }
                else
                {
                    this.logger.debug(UNKNOWN, msg);
                }
            }
        }
    }

//    @Override
//    public String toString()
//    {
//        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("logger", this.logger).append("separator",
// this.separator).append("level", this.level).toString();
//    }
}
