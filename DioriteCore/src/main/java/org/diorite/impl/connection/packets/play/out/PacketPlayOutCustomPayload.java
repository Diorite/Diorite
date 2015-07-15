package org.diorite.impl.connection.packets.play.out;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayOutListener;

@PacketClass(id = 0x3F, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketPlayOutCustomPayload extends PacketPlayOut
{
    public static final int TAG_SIZE = 20;
    public static final int MAX_SIZE = 1048576;
    private String               tag;
    private PacketDataSerializer dataSerializer;

    public PacketPlayOutCustomPayload()
    {
    }

    public PacketPlayOutCustomPayload(final String tag, final PacketDataSerializer dataSerializer)
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
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeText(this.tag);
        data.writeBytes(this.dataSerializer);
    }

    @Override
    public void handle(final PacketPlayOutListener listener)
    {
        listener.handle(this);
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
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("tag", this.tag).toString();
    }
}
