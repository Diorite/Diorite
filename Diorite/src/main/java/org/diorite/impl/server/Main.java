package org.diorite.impl.server;

import java.net.InetAddress;
import java.net.Proxy;
import java.net.UnknownHostException;

import org.diorite.impl.CoreMain;
import org.diorite.impl.DioriteCore;
import org.diorite.impl.server.connection.ServerConnection;

import joptsimple.OptionSet;

public final class Main
{
    private Main()
    {
    }

    public static void main(final String[] args)
    {
        DioriteCore.getInitPipeline().addLast("Diorite|initConnection", (s, p, data) -> {
            s.setHostname(data.options.has("hostname") ? data.options.valueOf("hostname").toString() : s.getConfig().getHostname());
            s.setPort(data.options.has("port") ? (int) data.options.valueOf("port") : s.getConfig().getPort());
            s.setConnectionHandler(new ServerConnection(s));
            s.getConnectionHandler().start();
        });
        DioriteCore.getStartPipeline().addBefore("DioriteCore|Run", "Diorite|bindConnection", (s, p, options) -> {
            try
            {
                System.setProperty("io.netty.eventLoopThreads", options.has("netty") ? options.valueOf("netty").toString() : Integer.toString(s.getConfig().getNettyThreads()));
                System.out.println("Starting listening on " + s.getHostname() + ":" + s.getPort());
                s.getConnectionHandler().init(InetAddress.getByName(s.getHostname()), s.getPort(), s.getConfig().isUseNativeTransport());

                System.out.println("Binded to " + s.getHostname() + ":" + s.getPort());
            } catch (final UnknownHostException e)
            {
                e.printStackTrace();
            }
        });

        final OptionSet options = CoreMain.main(args, false);
        if (options != null)
        {
            new DioriteServer(Proxy.NO_PROXY, options).start(options);
        }
    }
}
