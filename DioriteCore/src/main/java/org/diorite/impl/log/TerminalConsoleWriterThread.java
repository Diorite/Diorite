package org.diorite.impl.log;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TerminalConsoleWriterThread implements Runnable
{
    private final OutputStream output;

    public TerminalConsoleWriterThread(final OutputStream output)
    {
        this.output = output;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run()
    {
        while (true)
        {
            final String message = QueueLogAppender.getNextLogEvent("TerminalConsole");
            if (message != null)
            {
                try
                {
                    this.output.write(message.getBytes());
                    this.output.flush();
                } catch (final IOException ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("output", this.output).toString();
    }
}
