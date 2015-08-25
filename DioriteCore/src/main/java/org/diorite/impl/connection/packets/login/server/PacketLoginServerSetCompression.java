package org.diorite.impl.connection.packets.login.server;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.login.PacketLoginServerListener;

@PacketClass(id = 0x03, protocol = EnumProtocol.LOGIN, direction = EnumProtocolDirection.CLIENTBOUND, size = 2)
public class PacketLoginServerSetCompression extends PacketLoginServer
{
    private int threshold; // ~1-2 bytes

    public PacketLoginServerSetCompression()
    {
    }

    public PacketLoginServerSetCompression(final int threshold)
    {
        this.threshold = threshold;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.threshold = data.readVarInt();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeVarInt(this.threshold);
    }

    @Override
    public void handle(final PacketLoginServerListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("threshold", this.threshold).toString();
    }
}
