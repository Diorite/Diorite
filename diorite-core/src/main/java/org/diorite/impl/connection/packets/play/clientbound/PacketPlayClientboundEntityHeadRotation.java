package org.diorite.impl.connection.packets.play.clientbound;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayClientboundListener;

@PacketClass(id = 0x34, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 6)
public class PacketPlayClientboundEntityHeadRotation extends PacketPlayClientbound
{
    private int  entityId; // ~5 bytes
    private byte headYaw; // 1 byte

    public PacketPlayClientboundEntityHeadRotation()
    {
    }

    public PacketPlayClientboundEntityHeadRotation(final int entityId, final float headYaw)
    {
        this.entityId = entityId;
        this.headYaw = (byte) ((headYaw * 256.0F) / 360.0F);
    }

    public int getEntityId()
    {
        return entityId;
    }

    public void setEntityId(final int entityId)
    {
        this.entityId = entityId;
    }

    public byte getHeadYaw()
    {
        return headYaw;
    }

    public void setHeadYaw(final byte headYaw)
    {
        this.headYaw = headYaw;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.entityId = data.readVarInt();
        this.headYaw = data.readByte();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeVarInt(this.entityId);
        data.writeByte(this.headYaw);
    }

    @Override
    public void handle(final PacketPlayClientboundListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("entityId", this.entityId).append("headYaw", this.headYaw).toString();
    }
}
