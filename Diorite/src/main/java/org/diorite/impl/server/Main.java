package org.diorite.impl.server;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.server.connection.ServerConnection;

public class Main
{
    public static void main(final String[] args)
    {
        DioriteCore.getInitPipeline().addLast("Diorite|initConnection", (s, p, data) -> {
            s.setHostname(data.options.has("hostname") ? data.options.valueOf("hostname").toString() : s.getConfig().getHostname());
            s.setPort(data.options.has("port") ? (int) data.options.valueOf("port") : s.getConfig().getPort());
            s.setConnectionHandler(new ServerConnection(s));
            s.getConnectionHandler().start();
        });
        org.diorite.impl.Main.main(args);
    }
}
