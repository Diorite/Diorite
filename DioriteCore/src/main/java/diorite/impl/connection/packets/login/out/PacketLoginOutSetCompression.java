package diorite.impl.connection.packets.login.out;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.impl.connection.EnumProtocol;
import diorite.impl.connection.EnumProtocolDirection;
import diorite.impl.connection.packets.PacketClass;
import diorite.impl.connection.packets.login.PacketLoginOutListener;
import diorite.impl.connection.packets.PacketDataSerializer;

@PacketClass(id = 0x03, protocol = EnumProtocol.LOGIN, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketLoginOutSetCompression implements PacketLoginOut
{
    private int threshold;

    public PacketLoginOutSetCompression()
    {
    }

    public PacketLoginOutSetCompression(final int threshold)
    {
        this.threshold = threshold;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.threshold = data.readVarInt();
    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeVarInt(this.threshold);
    }

    @Override
    public void handle(final PacketLoginOutListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("threshold", this.threshold).toString();
    }
}
