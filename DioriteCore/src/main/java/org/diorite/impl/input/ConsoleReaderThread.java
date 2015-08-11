package org.diorite.impl.input;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.CoreMain;
import org.diorite.impl.DioriteCore;
import org.diorite.event.EventType;
import org.diorite.event.input.SenderCommandEvent;

import jline.console.ConsoleReader;

public class ConsoleReaderThread extends Thread
{
    private final DioriteCore core;

    public ConsoleReaderThread(final DioriteCore core)
    {
        super("{Diorite|Console}");
        this.core = core;
        this.setDaemon(true);
    }

    @Override
    public void run()
    {
        if (! CoreMain.isConsoleEnabled())
        {
            return;
        }
        final ConsoleReader reader = this.core.getReader();
        try
        {
            while (this.core.isRunning())
            {
                final String line = CoreMain.isUseJline() ? reader.readLine(">", null) : reader.readLine();
                if ((line == null) || (line.trim().length() <= 0))
                {
                    continue;
                }
                EventType.callEvent(new SenderCommandEvent(this.core.getConsoleSender(), line));
            }
        } catch (final NoSuchElementException ignored)
        {
        } catch (final IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("server", this.core).toString();
    }

    public static void start(final DioriteCore core)
    {
        new ConsoleReaderThread(core).start();
    }
}
