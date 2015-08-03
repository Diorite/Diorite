package org.diorite.impl.connection;

import java.net.InetAddress;

import org.diorite.impl.DioriteCore;

import io.netty.util.AttributeKey;

public interface ConnectionHandler
{
    AttributeKey<EnumProtocol> getProtocolKey();

    DioriteCore getCore();

    void init(InetAddress address, int port, boolean useEpoll);

    void close();

    void start();

//    void remove(CoreNetworkManager networkManager);
}
