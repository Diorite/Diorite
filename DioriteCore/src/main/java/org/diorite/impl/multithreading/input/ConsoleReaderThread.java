package org.diorite.impl.multithreading.input;

import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.Main;
import org.diorite.impl.ServerImpl;

public class ConsoleReaderThread extends Thread
{
    private final Scanner    scanner;
    private final ServerImpl server;

    public ConsoleReaderThread(final ServerImpl server, final InputStream inputStream)
    {
        super("{Diorite-" + server.getServerName() + "|Console}");
        this.scanner = new Scanner(inputStream);
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
        while (this.server.isRunning())
        {
            final String line;
            try
            {
                line = this.scanner.nextLine();
            } catch (final NoSuchElementException ignored)
            {
                break;
            }
            if (line == null)
            {
                continue;
            }
            this.server.getCommandMap().dispatch(this.server.getConsoleSender(), line);
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("scanner", this.scanner).append("server", this.server).toString();
    }

    public static void start(final ServerImpl server, final InputStream inputStream)
    {
        new ConsoleReaderThread(server, inputStream).start();
    }
}
