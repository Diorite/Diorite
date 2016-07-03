package org.diorite.impl.connection.packets.play.clientbound;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayClientboundListener;

@PacketClass(id = 0x06, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 6)
public class PacketPlayClientboundAnimation extends PacketPlayClientbound
{
    private int entityId; //~5 bytes
    private int animation; // 1 byte

    public PacketPlayClientboundAnimation()
    {
    }

    public PacketPlayClientboundAnimation(int entityId, int animation)
    {
        this.entityId = entityId;
        this.animation = animation;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.entityId = data.readVarInt();
        this.animation = data.readUnsignedByte();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeVarInt(entityId);
        data.writeByte(animation);
    }

    @Override
    public void handle(final PacketPlayClientboundListener listener)
    {
        listener.handle(this);
    }

    public int getEntityId()
    {
        return entityId;
    }

    public void setEntityId(int entityId)
    {
        this.entityId = entityId;
    }

    public int getAnimation()
    {
        return animation;
    }

    public void setAnimation(int animation)
    {
        this.animation = animation;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("entityId", this.entityId).append("animation", this.animation).toString();
    }
}
