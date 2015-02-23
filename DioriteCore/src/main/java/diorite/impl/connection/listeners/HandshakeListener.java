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
    public void handle(PacketHandshakingInSetProtocol packet)
    {

        switch (packet.getRequestType())
        {
            case LOGIN:
                this.networkManager.setProtocol(EnumProtocol.LOGIN);
                final BaseComponent chatcomponenttext = new TextComponent("Spierdalaj");
                chatcomponenttext.setColor(ChatColor.AQUA);
                final TextComponent tx = new TextComponent(" <3");
                tx.setBold(true);
                tx.setColor(ChatColor.RED);
                chatcomponenttext.addExtra(tx);
                this.networkManager.handle(new PacketLoginOutDisconnect(chatcomponenttext));
                this.networkManager.close(chatcomponenttext);
//                try
//                {
//                    long currentTime = System.currentTimeMillis();
//                    long connectionThrottle = this.server.getConnectionThrottle();
//                    InetAddress address = ((InetSocketAddress)this.networkManager.getSocketAddress()).getAddress();
//                    synchronized (throttleTracker)
//                    {
//                        if ((throttleTracker.containsKey(address)) && (! "127.0.0.1".equals(address.getHostAddress())) && ((currentTime - ((Long) throttleTracker.get(address)).longValue()) < connectionThrottle))
//                        {
//                            throttleTracker.put(address, Long.valueOf(currentTime));
//                            BaseComponent chatcomponenttext = new TextComponent("Connection throttled! Please wait before reconnecting.");
//                            this.networkManager.handle(new PacketLoginOutDisconnect(chatcomponenttext));
//                            this.networkManager.close(chatcomponenttext);
//                            return;
//                        }
//                        throttleTracker.put(address, Long.valueOf(currentTime));
//                        throttleCounter += 1;
//                        if (throttleCounter > 200)
//                        {
//                            throttleCounter = 0;
//
//
//                            Iterator iter = throttleTracker.entrySet().iterator();
//                            while (iter.hasNext())
//                            {
//                                Map.Entry<InetAddress, Long> entry = (Map.Entry)iter.next();
//                                if (((Long)entry.getValue()).longValue() > connectionThrottle) {
//                                    iter.remove();
//                                }
//                            }
//                        }
//                    }
//                }
//                catch (Throwable t)
//                {
//                    LogManager.getLogger().debug("Failed to check connection throttle", t);
//                }
//                if (packet.b() > 47)
//                {
//                    ChatComponentText chatcomponenttext = new ChatComponentText(MessageFormat.format(SpigotConfig.outdatedServerMessage, new Object[]{"1.8"}));
//                    this.networkManager.handle(new PacketLoginOutDisconnect(chatcomponenttext));
//                    this.networkManager.close(chatcomponenttext);
//                }
//                else if (packet.b() < 47)
//                {
//                    ChatComponentText chatcomponenttext = new ChatComponentText(MessageFormat.format(SpigotConfig.outdatedClientMessage, new Object[] { "1.8" }));
//                    this.networkManager.handle(new PacketLoginOutDisconnect(chatcomponenttext));
//                    this.networkManager.close(chatcomponenttext);
//                }
//                else
//                {
//                    this.networkManager.a(new LoginListener(this.server, this.networkManager));
//                    if (SpigotConfig.bungee)
//                    {
//                        String[] split = packet.b.split("");
//                        if ((split.length == 3) || (split.length == 4))
//                        {
//                            packet.b = split[0];
//                            this.networkManager.j = new InetSocketAddress(split[1], ((InetSocketAddress)this.networkManager.getSocketAddress()).getPort());
//                            this.networkManager.spoofedUUID = UUIDTypeAdapter.fromString(split[2]);
//                        }
//                        else
//                        {
//                            ChatComponentText chatcomponenttext = new ChatComponentText("If you wish to use IP forwarding, please enable it in your BungeeCord config as well!");
//                            this.networkManager.handle(new PacketLoginOutDisconnect(chatcomponenttext));
//                            this.networkManager.close(chatcomponenttext);
//                            return;
//                        }
//                        if (split.length == 4) {
//                            this.networkManager.spoofedProfile = ((Property[])gson.fromJson(split[3], [Lcom.mojang.authlib.properties.Property.class));
//                        }
//                    }
//                    ((LoginListener)this.networkManager.getPacketListener()).hostname = (packet.b + ":" + packet.c);
//                }
                break;
            case STATUS:
                this.networkManager.setProtocol(EnumProtocol.STATUS);
                this.networkManager.setPacketListener(new PacketStatusListener(this.server, this.networkManager));
                break;
            default:
                throw new UnsupportedOperationException("Invalid intention " + packet.getRequestType());
        }
    }

    public void disconnect(BaseComponent baseComponent) {}
}