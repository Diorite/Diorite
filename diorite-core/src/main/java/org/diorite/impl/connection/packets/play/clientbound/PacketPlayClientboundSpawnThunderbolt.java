package org.diorite.impl.connection.packets.play.clientbound;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayClientboundListener;

@PacketClass(id = 0x02, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 30)
public class PacketPlayClientboundSpawnThunderbolt extends PacketPlayClientbound
{
    private int entityId;  // ~5 bytes
    private byte type = 1; //  1 byte
    private double x;      //  8 bytes
    private double y;      //  8 bytes
    private double z;      //  8 bytes

    public PacketPlayClientboundSpawnThunderbolt()
    {
    }

    public PacketPlayClientboundSpawnThunderbolt(final int entityId, final double x, final double y, final double z)
    {
        this.entityId = entityId;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.entityId = data.readVarInt();
        data.readByte();
        this.x = data.readDouble();
        this.y = data.readDouble();
        this.z = data.readDouble();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeVarInt(this.entityId);
        data.writeByte(this.type);
        data.writeDouble(this.x);
        data.writeDouble(this.y);
        data.writeDouble(this.z);
    }

    @Override
    public void handle(final PacketPlayClientboundListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("entityId", this.entityId).append("type", this.type).append("x", this.x).append("y", this.y).append("z", this.z).toString();
    }
}
