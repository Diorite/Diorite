package diorite.impl;

import java.net.Proxy;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

import diorite.impl.connection.packets.RegisterPackets;
import diorite.material.Material;
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
        debug(Material.PLANKS);
        final OptionParser parser = new OptionParser()
        {
            {
                this.acceptsAll(Arrays.asList("p", "port", "server-port"), "Port to listen on").withRequiredArg().ofType(Integer.class).describedAs("port");
                this.acceptsAll(Arrays.asList("hostname", "h"), "hostname to listen on").withRequiredArg().ofType(String.class).describedAs("hostname");
                this.acceptsAll(Arrays.asList("online-mode", "online", "o"), "hostname to listen on").withRequiredArg().ofType(Boolean.class).describedAs("online");
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
