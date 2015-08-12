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
