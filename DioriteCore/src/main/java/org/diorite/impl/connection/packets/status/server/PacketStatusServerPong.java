package org.diorite.impl.connection.packets.status.server;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.status.PacketStatusServerListener;

@PacketClass(id = 0x01, protocol = EnumProtocol.STATUS, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketStatusServerPong extends PacketStatusServer
{
    private long ping;

    public PacketStatusServerPong()
    {
    }

    public PacketStatusServerPong(final long ping)
    {
        this.ping = ping;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.ping = data.readLong();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeLong(this.ping);
    }

    @Override
    public void handle(final PacketStatusServerListener listener)
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