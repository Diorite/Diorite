package diorite.impl;

import java.net.Proxy;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

import diorite.impl.connection.packets.RegisterPackets;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

public final class Main
{
    static boolean consoleEnabled = true; // TODO: implement

    private Main()
    {
    }

    public static boolean isConsoleEnabled()
    {
        return consoleEnabled;
    }

    public static void main(final String[] args)
    {
        final OptionParser parser = new OptionParser()
        {
            {
                this.acceptsAll(Arrays.asList("p", "port", "server-port"), "Port to listen on").withRequiredArg().ofType(Integer.class).describedAs("Port");
                this.acceptsAll(Arrays.asList("ip", "server-ip"), "IP to listen on").withRequiredArg().ofType(String.class).describedAs("IP");
            }
        };
        OptionSet options;
        try
        {
            options = parser.parse(args);
        } catch (final Exception e)
        {
            e.printStackTrace();
            options = parser.parse(ArrayUtils.EMPTY_STRING_ARRAY);
        }
        RegisterPackets.init();
        new ServerImpl(ServerImpl.DEFAULT_SERVER, Proxy.NO_PROXY, options).start(options);
    }

    public static void debug(final Object obj)
    {
        System.out.println(obj);
    }
}
