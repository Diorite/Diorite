package org.diorite.impl.connection.packets.login.out;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.Packet;
import org.diorite.impl.connection.packets.login.PacketLoginOutListener;

public abstract class PacketLoginOut extends Packet<PacketLoginOutListener>
{
    public PacketLoginOut()
    {
    }

    public PacketLoginOut(final byte[] data)
    {
        super(data);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("TYPE", EnumProtocolDirection.CLIENTBOUND + "|" + EnumProtocol.LOGIN).toString();
    }
}
