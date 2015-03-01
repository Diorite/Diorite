package diorite.impl.connection.listeners;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.chat.BaseComponent;
import diorite.entity.Player;
import diorite.impl.Main;
import diorite.impl.ServerImpl;
import diorite.impl.connection.NetworkManager;
import diorite.impl.connection.packets.play.PacketPlayInListener;
import diorite.impl.connection.packets.play.in.PacketPlayInArmAnimation;
import diorite.impl.connection.packets.play.in.PacketPlayInBlockDig;
import diorite.impl.connection.packets.play.in.PacketPlayInChat;
import diorite.impl.connection.packets.play.in.PacketPlayInCustomPayload;
import diorite.impl.connection.packets.play.in.PacketPlayInEntityAction;
import diorite.impl.connection.packets.play.in.PacketPlayInFlying;
import diorite.impl.connection.packets.play.in.PacketPlayInHeldItemSlot;
import diorite.impl.connection.packets.play.in.PacketPlayInKeepAlive;
import diorite.impl.connection.packets.play.in.PacketPlayInLook;
import diorite.impl.connection.packets.play.in.PacketPlayInPosition;
import diorite.impl.connection.packets.play.in.PacketPlayInPositionLook;
import diorite.impl.connection.packets.play.in.PacketPlayInSettings;
import diorite.impl.entity.EntityImpl;
import diorite.impl.entity.PlayerImpl;

public class PlayListener implements PacketPlayInListener
{
    private final ServerImpl     server;
    private final NetworkManager networkManager;

    public PlayListener(final ServerImpl server, final NetworkManager networkManager)
    {
        this.server = server;
        this.networkManager = networkManager;
    }

    @Override
    public void handle(final PacketPlayInKeepAlive packet)
    {
        // TODO kick if inactive
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
    public void handle(final PacketPlayInSettings packet)
    {
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
        Main.debug("Player want slot: " + packet.getSlot());
        // TODO: implement
    }

    @Override
    public void handle(final PacketPlayInPositionLook packet)
    {
        // TODO: implement
    }

    @Override
    public void handle(final PacketPlayInFlying packet)
    {
        // TODO: implement
    }

    @Override
    public void handle(final PacketPlayInPosition packet)
    {
        // TODO: implement
    }

    @Override
    public void handle(final PacketPlayInLook packet)
    {
        // TODO: implement
    }

    @Override
    public void handle(final PacketPlayInChat packet)
    {
        Main.debug("Chat packet: " + packet.getContent());
    }

    @Override
    public void handle(final PacketPlayInEntityAction packet)
    {
        final EntityImpl entity = this.server.getEntityManager().getEntity(packet.getEntityID());
        if (entity instanceof PlayerImpl)
        {
            packet.getEntityAction().doAction(((Player) entity), packet.getJumpBoost());
        }
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
    public void disconnect(final BaseComponent message)
    {
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("server", this.server).append("networkManager", this.networkManager).toString();
    }
}
