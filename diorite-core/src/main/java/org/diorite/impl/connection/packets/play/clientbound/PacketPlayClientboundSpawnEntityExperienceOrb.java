package org.diorite.impl.connection.packets.play.clientbound;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayClientboundListener;

@PacketClass(id = 0x01, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 31)
public class PacketPlayClientboundSpawnEntityExperienceOrb extends PacketPlayClientbound
{
    private int    entityId; // ~5 bytes
    private double x; // 8 bytes
    private double y; // 8 bytes
    private double z; // 8 bytes
    private short  count; // 2 bytes

    public PacketPlayClientboundSpawnEntityExperienceOrb()
    {
    }

    public PacketPlayClientboundSpawnEntityExperienceOrb(final int entityId, final double x, final double y, final double z, final short count)
    {
        this.entityId = entityId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.count = count;
    }

    public int getEntityId()
    {
        return entityId;
    }

    public void setEntityId(final int entityId)
    {
        this.entityId = entityId;
    }

    public double getX()
    {
        return x;
    }

    public void setX(final double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return y;
    }

    public void setY(final double y)
    {
        this.y = y;
    }

    public double getZ()
    {
        return z;
    }

    public void setZ(final double z)
    {
        this.z = z;
    }

    public short getCount()
    {
        return count;
    }

    public void setCount(final short count)
    {
        this.count = count;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.entityId = data.readVarInt();
        this.x = data.readDouble();
        this.y = data.readDouble();
        this.z = data.readDouble();
        this.count = data.readShort();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeVarInt(this.entityId);
        data.writeDouble(this.x);
        data.writeDouble(this.y);
        data.writeDouble(this.z);
        data.writeShort(this.count);
    }

    @Override
    public void handle(final PacketPlayClientboundListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("entityId", this.entityId).append("x", this.x).append("y", this.y).append("z", this.z).append("count", this.count).toString();
    }
}
