package org.diorite.impl.connection.packets.play.out;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.Packet;
import org.diorite.impl.connection.packets.play.PacketPlayOutListener;

public abstract class PacketPlayOut extends Packet<PacketPlayOutListener>
{
    public PacketPlayOut()
    {
    }

    public PacketPlayOut(final byte[] data)
    {
        super(data);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("TYPE", EnumProtocolDirection.CLIENTBOUND + "|" + EnumProtocol.PLAY).toString();
    }
}
