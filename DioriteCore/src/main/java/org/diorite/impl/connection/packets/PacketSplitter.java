package org.diorite.impl.connection.packets;


import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.CorruptedFrameException;

public class PacketSplitter extends ByteToMessageCodec<ByteBuf>
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

    @Override
    protected void decode(final ChannelHandlerContext context, final ByteBuf byteBuf, final List<Object> objects)
    {
        byteBuf.markReaderIndex();

        final byte[] arrayOfByte = new byte[3];
        for (int i = 0; i < arrayOfByte.length; i++)
        {
            if (! byteBuf.isReadable())
            {
                byteBuf.resetReaderIndex();
                return;
            }
            arrayOfByte[i] = byteBuf.readByte();
            if (arrayOfByte[i] >= 0)
            {
                final PacketDataSerializer dataSerializer = new PacketDataSerializer(Unpooled.wrappedBuffer(arrayOfByte));
                try
                {
                    final int size = dataSerializer.readVarInt();
                    if (byteBuf.readableBytes() < size)
                    {
                        byteBuf.resetReaderIndex();
                        return;
                    }
                    objects.add(byteBuf.readBytes(size));
                    return;
                } finally
                {
                    dataSerializer.release();
                }
            }
        }
        throw new CorruptedFrameException("length wider than 21-bit");
    }
}