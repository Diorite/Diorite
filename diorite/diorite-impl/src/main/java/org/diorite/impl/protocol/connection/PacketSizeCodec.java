/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.impl.protocol.connection;

import java.util.List;

import org.diorite.impl.protocol.AbstractPacketDataSerializer;
import org.diorite.impl.protocol.connection.ByteToMessageCodec.PacketByteBufByteToMessageCodec;
import org.diorite.commons.math.DioriteMathUtils;
import org.diorite.core.protocol.connection.ActiveConnection;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.CorruptedFrameException;

public class PacketSizeCodec extends PacketByteBufByteToMessageCodec
{
    private final ServerConnectionImpl serverConnection;
    private final ActiveConnection     activeConnection;

    public PacketSizeCodec(ServerConnectionImpl serverConnection, ActiveConnection activeConnection)
    {
        this.serverConnection = serverConnection;
        this.activeConnection = activeConnection;
    }

    @Override
    protected void encode(ChannelHandlerContext context, ByteBuf srcByteBuf, ByteBuf byteBuf)
    {
        int readableBytes = srcByteBuf.readableBytes();
        int neededBytes = DioriteMathUtils.varintSize(readableBytes);
        if (neededBytes > 3)
        {
            throw new IllegalArgumentException("unable to fit " + readableBytes + " into buffer");
        }
        byteBuf.ensureWritable(neededBytes + readableBytes);
        AbstractPacketDataSerializer.writeVarInt(byteBuf, readableBytes);
        byteBuf.writeBytes(srcByteBuf, srcByteBuf.readerIndex(), readableBytes);
    }

    @Override
    protected void decode(ChannelHandlerContext context, ByteBuf byteBuf, List<Object> objects)
    {
        byte[] arrayOfByte = new byte[3];
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
                ByteBuf buffer = Unpooled.wrappedBuffer(arrayOfByte);
                try
                {
                    int size = AbstractPacketDataSerializer.readVarInt(buffer);
                    if (byteBuf.readableBytes() < size)
                    {
                        byteBuf.resetReaderIndex();
                        return;
                    }
                    objects.add(byteBuf.readBytes(size));
                    return;
                }
                finally
                {
                    buffer.release();
                }
            }
        }
        throw new CorruptedFrameException("length wider than 21-bit");
    }
}