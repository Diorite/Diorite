package diorite.impl.log;

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
    public void flush()
    {
    }

    @Override
    public void close() throws SecurityException
    {
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("cachedLoggers", this.cachedLoggers.keySet()).toString();
    }
}
