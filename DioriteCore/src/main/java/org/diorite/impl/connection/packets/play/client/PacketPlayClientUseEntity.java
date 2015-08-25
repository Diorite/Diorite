package org.diorite.impl.connection.packets.play.client;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayClientListener;
import org.diorite.utils.math.geometry.Vector3F;

@PacketClass(id = 0x02, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND, size = 18)
public class PacketPlayClientUseEntity extends PacketPlayClient
{
    private int             targetEntity; // ~5 bytes
    private EntityUseAction action; // ~1 byte
    private Vector3F        interactAtLocation; // 12 bytes

    public PacketPlayClientUseEntity()
    {
    }

    public PacketPlayClientUseEntity(final int targetEntity, final EntityUseAction action)
    {
        this.targetEntity = targetEntity;
        this.action = action;
    }

    public PacketPlayClientUseEntity(final int targetEntity, final EntityUseAction action, final Vector3F interactAtLocation)
    {
        this.targetEntity = targetEntity;
        this.action = action;
        if (action != EntityUseAction.INTERACTAT)
        {
            throw new IllegalArgumentException();
        }
        this.interactAtLocation = interactAtLocation;
    }

    public int getTargetEntity()
    {
        return this.targetEntity;
    }

    public void setTargetEntity(final int targetEntity)
    {
        this.targetEntity = targetEntity;
    }

    public EntityUseAction getAction()
    {
        return this.action;
    }

    public void setAction(final EntityUseAction action)
    {
        this.action = action;
    }

    public Vector3F getInteractAtLocation()
    {
        return this.interactAtLocation;
    }

    public void setInteractAtLocation(final Vector3F interactAtLocation)
    {
        this.interactAtLocation = interactAtLocation;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.targetEntity = data.readVarInt();
        this.action = EntityUseAction.values()[data.readVarInt()];
        if (this.action == EntityUseAction.INTERACTAT)
        {
            this.interactAtLocation = data.readVector3F();
        }
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeVarInt(this.targetEntity);
        data.writeVarInt(this.action.ordinal());
        if (this.action == EntityUseAction.INTERACTAT)
        {
            data.writeVector3F(this.interactAtLocation);
        }
    }

    @Override
    public void handle(final PacketPlayClientListener listener)
    {
        listener.handle(this);
    }

    public enum EntityUseAction
    {
        INTERACT,
        ATTACK,
        INTERACTAT;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("targetEntity", this.targetEntity).append("action", this.action).append("interactAtLocation", this.interactAtLocation).toString();
    }
}
