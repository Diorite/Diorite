package org.diorite.impl.connection.packets.status.in;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.Packet;
import org.diorite.impl.connection.packets.status.PacketStatusInListener;

public abstract class PacketStatusIn extends Packet<PacketStatusInListener>
{
    public PacketStatusIn()
    {
    }

    public PacketStatusIn(final byte[] data)
    {
        super(data);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("TYPE", EnumProtocolDirection.SERVERBOUND + "|" + EnumProtocol.STATUS).toString();
    }
}
