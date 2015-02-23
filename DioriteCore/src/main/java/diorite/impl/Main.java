package diorite.impl;

import java.net.Proxy;

import diorite.impl.connection.packets.RegisterPackets;

public final class Main
{
    static boolean consoleEnabled = true; // TODO: implement

    public static boolean isConsoleEnabled()
    {
        return consoleEnabled;
    }

    public static void main(final String[] sdfsd)
    {
        RegisterPackets.init();
        new ServerImpl(ServerImpl.DEFAULT_SERVER, Proxy.NO_PROXY).start();
    }

    public static void debug(final Object obj)
    {
        System.out.println(obj);
    }
}
