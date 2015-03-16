package org.diorite.impl.connection.packets;


import java.util.zip.Deflater;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketCompressor extends MessageToByteEncoder<ByteBuf>
{
    @SuppressWarnings("MagicNumber")
    private final byte[] bytes = new byte[8192];
    private final Deflater deflater;
    private       int      threshold;

    public PacketCompressor(final int threshold)
    {
        this.threshold = threshold;
        this.deflater = new Deflater();
    }

    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final ByteBuf srcByteBuf, final ByteBuf byteBuf)
    {
        final int i = srcByteBuf.readableBytes();
        final PacketDataSerializer localPacketDataSerializer = new PacketDataSerializer(byteBuf);
        if (i < this.threshold)
        {
            localPacketDataSerializer.writeVarInt(0);
            localPacketDataSerializer.writeBytes(srcByteBuf);
        }
        else
        {
            final byte[] arrayOfByte = new byte[i];
            srcByteBuf.readBytes(arrayOfByte);

            localPacketDataSerializer.writeVarInt(arrayOfByte.length);

            this.deflater.setInput(arrayOfByte, 0, i);
            this.deflater.finish();
            while (! this.deflater.finished())
            {
                final int j = this.deflater.deflate(this.bytes);
                localPacketDataSerializer.writeBytes(this.bytes, 0, j);
            }
            this.deflater.reset();
        }
    }

    public void a(final int threshold)
    {
        this.threshold = threshold;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("deflater", this.deflater).append("threshold", this.threshold).toString();
    }
}