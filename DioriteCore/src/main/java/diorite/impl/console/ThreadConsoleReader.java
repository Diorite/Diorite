package diorite.impl.console;

import java.io.InputStream;
import java.util.Scanner;

import diorite.impl.Main;
import diorite.impl.ServerImpl;

public class ThreadConsoleReader extends Thread
{
    private final Scanner    scanner;
    private final ServerImpl server;

    public ThreadConsoleReader(final ServerImpl server, final InputStream inputStream)
    {
        super("ConsoleReader");
        this.scanner = new Scanner(inputStream);
        this.server = server;
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
            Main.debug("Reading...");
            final String line = this.scanner.nextLine();
            Main.debug("Line: " + line);
            if (line == null)
            {
                Main.debug("Null line...");
                continue;
            }
            this.server.getCommandMap().dispatch(this.server.getConsoleSender(), line);
        }
    }
}
