package diorite.impl.connection.packets.play.in;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.impl.connection.EnumProtocol;
import diorite.impl.connection.EnumProtocolDirection;
import diorite.impl.connection.packets.PacketClass;
import diorite.impl.connection.packets.PacketDataSerializer;
import diorite.impl.connection.packets.play.PacketPlayInListener;

@PacketClass(id = 0x17, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND)
public class PacketPlayInCustomPayload implements PacketPlayIn
{
    public static final int TAG_SIZE = 20;
    public static final int MAX_SIZE = 32767;
    private String               tag;
    private PacketDataSerializer dataSerializer;

    public PacketPlayInCustomPayload()
    {
    }

    public PacketPlayInCustomPayload(final String tag, final PacketDataSerializer dataSerializer)
    {
        this.tag = tag;
        this.dataSerializer = dataSerializer;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.tag = data.readText(TAG_SIZE);
        final int size = data.readableBytes();
        if ((size < 0) || (size > MAX_SIZE))
        {
            throw new IOException("Payload may not be larger than " + MAX_SIZE + " bytes");
        }
        this.dataSerializer = new PacketDataSerializer(data.readBytes(size));
    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeText(this.tag);
        data.writeBytes(this.dataSerializer);
    }

    public String getTag()
    {
        return this.tag;
    }

    public void setTag(final String tag)
    {
        this.tag = tag;
    }

    public PacketDataSerializer getDataSerializer()
    {
        return this.dataSerializer;
    }

    public void setDataSerializer(final PacketDataSerializer dataSerializer)
    {
        this.dataSerializer = dataSerializer;
    }

    @Override
    public void handle(final PacketPlayInListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("tag", this.tag).toString();
    }
}
