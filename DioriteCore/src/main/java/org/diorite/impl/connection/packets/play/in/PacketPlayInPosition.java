package org.diorite.impl.connection.packets.play.in;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayInListener;

@PacketClass(id = 0x04, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND)
public class PacketPlayInPosition implements PacketPlayIn
{
    private double  x;
    private double  y;
    private double  z;
    private boolean onGround;

    public PacketPlayInPosition()
    {
    }

    public PacketPlayInPosition(final double x, final boolean onGround, final double z, final double y)
    {
        this.x = x;
        this.onGround = onGround;
        this.z = z;
        this.y = y;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.x = data.readDouble();
        this.y = data.readDouble();
        this.z = data.readDouble();
        this.onGround = data.readBoolean();
    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeDouble(this.x);
        data.writeDouble(this.y);
        data.writeDouble(this.z);
        data.writeBoolean(this.onGround);
    }

    @Override
    public void handle(final PacketPlayInListener listener)
    {
        listener.handle(this);
    }

    public double getX()
    {
        return this.x;
    }

    public void setX(final double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return this.y;
    }

    public void setY(final double y)
    {
        this.y = y;
    }

    public double getZ()
    {
        return this.z;
    }

    public void setZ(final double z)
    {
        this.z = z;
    }

    public boolean isOnGround()
    {
        return this.onGround;
    }

    public void setOnGround(final boolean onGround)
    {
        this.onGround = onGround;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("x", this.x).append("y", this.y).append("z", this.z).append("onGround", this.onGround).toString();
    }
}
