package org.diorite.impl.input;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.Main;
import org.diorite.impl.DioriteCore;
import org.diorite.event.EventType;
import org.diorite.event.input.SenderCommandEvent;

import jline.console.ConsoleReader;

public class ConsoleReaderThread extends Thread
{
    private final DioriteCore server;

    public ConsoleReaderThread(final DioriteCore server)
    {
        super("{Diorite|Console}");
        this.server = server;
        this.setDaemon(true);
    }

    @Override
    public void run()
    {
        if (! Main.isConsoleEnabled())
        {
            return;
        }
        final ConsoleReader reader = this.server.getReader();
        try
        {
            while (this.server.isRunning())
            {
                final String line = Main.isUseJline() ? reader.readLine(">", null) : reader.readLine();
                if ((line == null) || (line.trim().length() <= 0))
                {
                    continue;
                }
                EventType.callEvent(new SenderCommandEvent(this.server.getConsoleSender(), line));
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("server", this.server).toString();
    }

    public static void start(final DioriteCore server)
    {
        new ConsoleReaderThread(server).start();
    }
}
