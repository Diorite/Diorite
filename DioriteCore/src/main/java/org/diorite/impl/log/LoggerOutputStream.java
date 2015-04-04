package org.diorite.impl.log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

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
                this.logger.log(this.level, record);
            }
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("logger", this.logger).append("separator", this.separator).append("level", this.level).toString();
    }
}
