package org.diorite.impl.connection.packets.status.client;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.status.PacketStatusClientListener;

@PacketClass(id = 0x01, protocol = EnumProtocol.STATUS, direction = EnumProtocolDirection.SERVERBOUND, size = 8)
public class PacketStatusClientPing extends PacketStatusClient
{
    private long ping; // 8 bytes

    public PacketStatusClientPing()
    {
    }

    public PacketStatusClientPing(final long ping)
    {
        this.ping = ping;
    }

    @Override
    public void readPacket(final PacketDataSerializer data)
    {
        this.ping = data.readLong();
    }

    @Override
    public void writeFields(final PacketDataSerializer data)
    {
        data.writeLong(this.ping);
    }

    @Override
    public void handle(final PacketStatusClientListener listener)
    {
        listener.handle(this);
    }

    public long getPing()
    {
        return this.ping;
    }

    public void setPing(final long ping)
    {
        this.ping = ping;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("ping", this.ping).toString();
    }
}