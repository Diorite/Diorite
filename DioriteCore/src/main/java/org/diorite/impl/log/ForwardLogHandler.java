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

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ForwardLogHandler extends ConsoleHandler
{
    private final Map<String, Logger> cachedLoggers = new ConcurrentHashMap<>(10);

    private Logger getLogger(final String name)
    {
        Logger logger = this.cachedLoggers.get(name);
        if (logger == null)
        {
            logger = LogManager.getLogger(name);
            this.cachedLoggers.put(name, logger);
        }
        return logger;
    }

    @Override
    public void publish(final LogRecord record)
    {
        final Logger logger = this.getLogger(record.getLoggerName());
        final Throwable exception = record.getThrown();
        final Level level = record.getLevel();
        final String message = this.getFormatter().formatMessage(record);
        if (Objects.equals(level, Level.SEVERE))
        {
            logger.error(message, exception);
        }
        else if (Objects.equals(level, Level.WARNING))
        {
            logger.warn(message, exception);
        }
        else if (Objects.equals(level, Level.INFO))
        {
            logger.info(message, exception);
        }
        else if (Objects.equals(level, Level.CONFIG))
        {
            logger.debug(message, exception);
        }
        else
        {
            logger.trace(message, exception);
        }
    }

    @Override
    public void close() throws SecurityException
    {
    }

    @Override
    public void flush()
    {
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("cachedLoggers", this.cachedLoggers.keySet()).toString();
    }
}
