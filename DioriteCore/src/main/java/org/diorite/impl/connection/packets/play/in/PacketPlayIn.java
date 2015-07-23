package org.diorite.impl.connection.packets.play.in;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.Packet;
import org.diorite.impl.connection.packets.play.PacketPlayInListener;

public abstract class PacketPlayIn extends Packet<PacketPlayInListener>
{
    public PacketPlayIn()
    {
    }

    public PacketPlayIn(final byte[] data)
    {
        super(data);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("TYPE", EnumProtocolDirection.SERVERBOUND + "|" + EnumProtocol.PLAY).toString();
    }
}
