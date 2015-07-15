package org.diorite.impl.connection.packets.login.in;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.Packet;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.login.PacketLoginInListener;

public abstract class PacketLoginIn extends Packet<PacketLoginInListener>
{
    public PacketLoginIn()
    {
    }

    public PacketLoginIn(final PacketDataSerializer data)
    {
        super(data);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("TYPE", EnumProtocolDirection.SERVERBOUND + "|" + EnumProtocol.LOGIN).toString();
    }
}
