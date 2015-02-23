package diorite.impl.connection.listeners;


import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import diorite.ChatColor;
import diorite.chat.BaseComponent;
import diorite.chat.TextComponent;
import diorite.impl.connection.EnumProtocol;
import diorite.impl.connection.packets.login.out.PacketLoginOutDisconnect;
import diorite.impl.ServerImpl;
import diorite.impl.connection.NetworkManager;
import diorite.impl.connection.packets.handshake.in.PacketHandshakingInSetProtocol;

public class HandshakeListener implements PacketHandshakingInListener
{
    private static final Gson                   gson            = new Gson();
    private static final Map<InetAddress, Long> throttleTracker = new HashMap<>(100);
    private static       int                    throttleCounter = 0;
    private final ServerImpl     server;
    private final NetworkManager networkManager;

    public HandshakeListener(final ServerImpl server, final NetworkManager networkManager)
    {
        this.server = server;
        this.networkManager = networkManager;
    }

    @Override
    public void handle(final PacketHandshakingInSetProtocol packet)
    {

        switch (packet.getRequestType())
        {
            case LOGIN:
                this.networkManager.setProtocol(EnumProtocol.LOGIN);
                final BaseComponent msg = new TextComponent("Diorite server, Not implemented yet :<");
                msg.setColor(ChatColor.AQUA);
                this.networkManager.handle(new PacketLoginOutDisconnect(msg));
                this.networkManager.close(msg);

                break;
            case STATUS:
                this.networkManager.setProtocol(EnumProtocol.STATUS);
                this.networkManager.setPacketListener(new PacketStatusListener(this.server, this.networkManager));
                break;
            default:
                throw new UnsupportedOperationException("Invalid intention " + packet.getRequestType());
        }
    }

    @Override
    public void disconnect(final BaseComponent baseComponent) {}
}