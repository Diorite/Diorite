package diorite.impl.connection.packets.status.in;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.impl.connection.packets.PacketDataSerializer;
import diorite.impl.connection.packets.status.PacketStatusInListener;

public class PacketStatusInPing implements PacketStatusIn
{
    private long ping;

    public PacketStatusInPing()
    {
    }

    public PacketStatusInPing(final long ping)
    {
        this.ping = ping;
    }

    @Override
    public void readPacket(final PacketDataSerializer data)
    {
        this.ping = data.readLong();
    }

    @Override
    public void writePacket(final PacketDataSerializer data)
    {
        data.writeLong(this.ping);
    }

    public void handle(final PacketStatusInListener listener)
    {
        listener.handle(this);
    }

    public void setPing(final long ping)
    {
        this.ping = ping;
    }

    public long getPing()
    {
        return this.ping;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("ping", this.ping).toString();
    }
}