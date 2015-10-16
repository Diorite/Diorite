/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.impl.connection.packets;


import java.util.List;

import org.diorite.impl.connection.ByteToMessageCodec.PacketByteBufByteToMessageCodec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.CorruptedFrameException;

public class PacketSizer extends PacketByteBufByteToMessageCodec
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