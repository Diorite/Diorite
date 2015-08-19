package org.diorite.impl.connection.packets;


import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.DecoderException;

public class PacketCompression extends ByteToMessageCodec<ByteBuf>
{
    public static final int    MAX_PACKET_SIZE = 2097152; // 256 KiB
    @SuppressWarnings("MagicNumber")
    private final       byte[] bytes           = new byte[8192];
    private final Inflater inflater;
    private final Deflater deflater;
    private       int      threshold;

    public PacketCompression(final int threshold)
    {
        this.threshold = threshold;
        this.deflater = new Deflater();
        this.inflater = new Inflater();
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

    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List<Object> paramList) throws DataFormatException
    {
        if (byteBuf.readableBytes() == 0)
        {
            return;
        }
        final PacketDataSerializer localPacketDataSerializer = new PacketDataSerializer(byteBuf);
        final int i = localPacketDataSerializer.readVarInt();
        if (i == 0)
        {
            paramList.add(localPacketDataSerializer.readBytes(localPacketDataSerializer.readableBytes()));
        }
        else
        {
            if (i < this.threshold)
            {
                throw new DecoderException("Badly compressed packet - size of " + i + " is below server threshold of " + this.threshold);
            }
            if (i > MAX_PACKET_SIZE)
            {
                throw new DecoderException("Badly compressed packet - size of " + i + " is larger than protocol maximum of " + MAX_PACKET_SIZE);
            }
            final byte[] arrayOfByte1 = new byte[localPacketDataSerializer.readableBytes()];
            localPacketDataSerializer.readBytes(arrayOfByte1);
            this.inflater.setInput(arrayOfByte1);

            final byte[] arrayOfByte2 = new byte[i];
            this.inflater.inflate(arrayOfByte2);
            paramList.add(Unpooled.wrappedBuffer(arrayOfByte2));

            this.inflater.reset();
        }
    }

    public void setThreshold(final int threshold)
    {
        this.threshold = threshold;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("deflater", this.deflater).append("threshold", this.threshold).toString();
    }
}