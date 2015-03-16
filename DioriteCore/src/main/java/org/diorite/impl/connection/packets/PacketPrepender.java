package org.diorite.impl.connection.packets;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketPrepender extends MessageToByteEncoder<ByteBuf>
{
    @Override
    protected void encode(final ChannelHandlerContext context, final ByteBuf srcByteBuf, final ByteBuf byteBuf)
    {
        final int readableBytes = srcByteBuf.readableBytes();
        final int neededBytes = PacketDataSerializer.neededBytes(readableBytes);
        if (neededBytes > 3)
        {
            throw new IllegalArgumentException("unable to fit " + readableBytes + " into " + 3);
        }
        final PacketDataSerializer dataSerializer = new PacketDataSerializer(byteBuf);

        dataSerializer.ensureWritable(neededBytes + readableBytes);

        dataSerializer.writeVarInt(readableBytes);
        dataSerializer.writeBytes(srcByteBuf, srcByteBuf.readerIndex(), readableBytes);
    }
}
