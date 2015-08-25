package org.diorite.impl.connection.packets.play.client;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayClientListener;
import org.diorite.entity.EntityAction;

@PacketClass(id = 0x0B, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND, size = 11)
public class PacketPlayClientEntityAction extends PacketPlayClient
{
    private int          entityID; // ~5 bytes
    private EntityAction entityAction; // ~1 byte
    private int          jumpBoost; // ~5 bytes

    public PacketPlayClientEntityAction()
    {
    }

    public PacketPlayClientEntityAction(final int entityID, final EntityAction entityAction, final int jumpBoost)
    {
        this.entityID = entityID;
        this.entityAction = entityAction;
        this.jumpBoost = jumpBoost;
    }

    public PacketPlayClientEntityAction(final int entityID, final EntityAction entityAction)
    {
        this.entityID = entityID;
        this.entityAction = entityAction;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.entityID = data.readVarInt();
        this.entityAction = EntityAction.getByEnumOrdinal(data.readVarInt());
        this.jumpBoost = data.readVarInt();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeVarInt(this.entityID);
        data.writeVarInt(this.entityAction.ordinal());
        data.writeVarInt(this.jumpBoost);
    }

    @Override
    public void handle(final PacketPlayClientListener listener)
    {
        listener.handle(this);
    }

    public int getEntityID()
    {
        return this.entityID;
    }

    public void setEntityID(final int entityID)
    {
        this.entityID = entityID;
    }

    public EntityAction getEntityAction()
    {
        return this.entityAction;
    }

    public void setEntityAction(final EntityAction entityAction)
    {
        this.entityAction = entityAction;
    }

    public int getJumpBoost()
    {
        return this.jumpBoost;
    }

    public void setJumpBoost(final int jumpBoost)
    {
        this.jumpBoost = jumpBoost;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("entityID", this.entityID).append("entityAction", this.entityAction).append("jumpBoost", this.jumpBoost).toString();
    }
}
