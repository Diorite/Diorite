package org.diorite.impl.connection.listeners;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.chat.BaseComponent;
import org.diorite.impl.Main;
import org.diorite.impl.ServerImpl;
import org.diorite.impl.connection.NetworkManager;
import org.diorite.impl.connection.packets.play.PacketPlayInListener;
import org.diorite.impl.connection.packets.play.in.PacketPlayInArmAnimation;
import org.diorite.impl.connection.packets.play.in.PacketPlayInBlockDig;
import org.diorite.impl.connection.packets.play.in.PacketPlayInBlockPlace;
import org.diorite.impl.connection.packets.play.in.PacketPlayInChat;
import org.diorite.impl.connection.packets.play.in.PacketPlayInCustomPayload;
import org.diorite.impl.connection.packets.play.in.PacketPlayInEntityAction;
import org.diorite.impl.connection.packets.play.in.PacketPlayInFlying;
import org.diorite.impl.connection.packets.play.in.PacketPlayInHeldItemSlot;
import org.diorite.impl.connection.packets.play.in.PacketPlayInKeepAlive;
import org.diorite.impl.connection.packets.play.in.PacketPlayInLook;
import org.diorite.impl.connection.packets.play.in.PacketPlayInPosition;
import org.diorite.impl.connection.packets.play.in.PacketPlayInPositionLook;
import org.diorite.impl.connection.packets.play.in.PacketPlayInSettings;
import org.diorite.impl.entity.PlayerImpl;
import org.diorite.impl.multithreading.ChatAction;

public class PlayListener implements PacketPlayInListener
{
    private final ServerImpl     server;
    private final NetworkManager networkManager;
    private final PlayerImpl     player;

    public PlayListener(final ServerImpl server, final NetworkManager networkManager, final PlayerImpl player)
    {
        this.server = server;
        this.networkManager = networkManager;
        this.player = player;
    }

    @Override
    public void handle(final PacketPlayInKeepAlive packet)
    {
        // TODO kick if inactive
    }

    @Override
    public void handle(final PacketPlayInSettings packet)
    {
        final byte oldViewDistance = this.player.getViewDistance();
        this.player.setViewDistance(packet.getViewDistance());
        if (oldViewDistance != this.player.getViewDistance())
        {
            this.player.getPlayerChunks().update();
        }
        // TODO: implement
    }

    @Override
    public void handle(final PacketPlayInCustomPayload packet)
    {
        Main.debug("Got plugin message: " + packet.getTag());
        // TODO: implement
    }

    @Override
    public void handle(final PacketPlayInHeldItemSlot packet)
    {
        // TODO: implement
    }

    @Override
    public void handle(final PacketPlayInPositionLook packet)
    {
        this.player.setPositionAndRotation(packet.getX(), packet.getY(), packet.getZ(), packet.getYaw(), packet.getPitch());
    }

    @Override
    public void handle(final PacketPlayInFlying packet)
    {
        // TODO: implement
    }

    @Override
    public void handle(final PacketPlayInPosition packet)
    {
        this.player.setPosition(packet.getX(), packet.getY(), packet.getZ());
    }

    @Override
    public void handle(final PacketPlayInLook packet)
    {
        this.player.setRotation(packet.getYaw(), packet.getPitch());
    }

    @Override
    public void handle(final PacketPlayInChat packet)
    {
        final String str = packet.getContent();
        //noinspection HardcodedFileSeparator
        if (str.startsWith("/"))
        {
            this.server.getCommandsThread().add(new ChatAction(str.substring(1), this.player));
        }
        else
        {
            this.server.getChatThread().add(new ChatAction(str, this.player));
        }
    }

    @Override
    public void handle(final PacketPlayInEntityAction packet)
    {
        packet.getEntityAction().doAction(this.player, packet.getJumpBoost());
    }

    @Override
    public void handle(final PacketPlayInArmAnimation packet)
    {
        // TODO: implement
    }

    @Override
    public void handle(final PacketPlayInBlockDig packet)
    {
        // TODO: implement
    }

    @Override
    public void handle(final PacketPlayInBlockPlace packet)
    {
        // TODO: implement
    }

    public NetworkManager getNetworkManager()
    {
        return this.networkManager;
    }

    public ServerImpl getServer()
    {
        return this.server;
    }

    @Override
    public void disconnect(final BaseComponent message)
    {
        // TODO: implement
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("server", this.server).append("networkManager", this.networkManager).toString();
    }

    public PlayerImpl getPlayer()
    {
        return this.player;
    }
}
