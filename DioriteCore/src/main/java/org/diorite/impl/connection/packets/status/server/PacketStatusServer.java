package org.diorite.impl.connection.packets.status.server;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.Packet;
import org.diorite.impl.connection.packets.status.PacketStatusServerListener;

public abstract class PacketStatusServer extends Packet<PacketStatusServerListener>
{
    public PacketStatusServer()
    {
    }

    public PacketStatusServer(final byte[] data)
    {
        super(data);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("TYPE", EnumProtocolDirection.CLIENTBOUND + "|" + EnumProtocol.STATUS).toString();
    }
}
