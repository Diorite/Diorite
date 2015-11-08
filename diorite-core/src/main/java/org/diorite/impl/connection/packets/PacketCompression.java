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
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.ByteToMessageCodec.PacketByteBufByteToMessageCodec;

import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;

@SuppressWarnings("MagicNumber")
public class PacketCompression extends PacketByteBufByteToMessageCodec
{
    public static final int    MAX_PACKET_SIZE = 2097152; // 2 MB
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


    private static final TIntIntMap sizes;

    static
    {
        sizes = new TIntIntHashMap(641, .1f, - 1, - 1);
        for (int k = 100; k <= 1_000_000_000; k *= 10)
        {
            for (int i = k, to = (k * 10), add = (k / 10); i < to; i += add)
            {
                sizes.put(((i / add) * add), 1);
            }
        }
        for (int i = 1_500_000_000, to = 2_000_000_000, add = (100_000_000); i <= to; i += add)
        {
            sizes.put(((i / add) * add), 1);
        }
    }

    private static int getSize(final int key)
    {
        if (key < 128)
        {
            return 0;
        }
        return sizes.get(key);
    }

    private static void update(final int key, final int newValue)
    {
        sizes.put(key, newValue);
//        final TIntIntIterator it = sizes.iterator();
//        while (it.hasNext())
//        {
//            it.advance();
//            if ((it.key() >= key) && (it.value() < newValue))
//            {
//                it.setValue(newValue);
//            }
//        }
    }

    private static int getKey(final int i)
    {
        if (i > 1_000_000_000)
        {
            return (i / 100_000_000) * 100_000_000;
        }
        if (i > 100_000_000)
        {
            return (i / 10_000_000) * 10_000_000;
        }
        if (i > 10_000_000)
        {
            return (i / 1_000_000) * 1_000_000;
        }
        if (i > 1_000_000)
        {
            return (i / 100_000) * 100_000;
        }
        if (i > 100_000)
        {
            return (i / 10_000) * 10_000;
        }
        if (i > 10_000)
        {
            return (i / 1_000) * 1_000;
        }
        if (i > 1_000)
        {
            return (i / 100) * 100;
        }
        if (i > 127)
        {
            return (i / 10) * 10;
        }
        return 0;
    }

    @Override
    protected ByteBuf allocateBuffer(final ChannelHandlerContext ctx, final ByteBuf msg, final boolean preferDirect) throws Exception
    {
        // TODO: keep this in valid values, to keep byte buffers not extends when writing, but also don't crate 2 000 000 byte buffers for 50 000 bytes data.
        final int i = msg.readableBytes();
        final int sizesKey = getKey(i);
        int size = getSize(sizesKey);
        if (size == - 1)
        {
            throw new AssertionError();
        }
        if (size == 0)
        {
            size = msg.readableBytes() + 10;
        }
        else if (size > msg.readableBytes())
        {
            size = msg.readableBytes();
        }
        if (preferDirect)
        {
            return ctx.alloc().ioBuffer(size);
        }
        else
        {
            return ctx.alloc().heapBuffer(size);
        }
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

            if (i > 127)
            {
                final int sizesKey = getKey(i);
                final int maxSize = getSize(sizesKey);
                if (byteBuf.readableBytes() > maxSize)
                {
                    int newSize = byteBuf.readableBytes();
                    if (maxSize == 1)
                    {
                        newSize += newSize / 100;
                    }
                    else
                    {
                        newSize += (byteBuf.readableBytes() - maxSize) * 5;
                    }
                    update(sizesKey, newSize);
//                    CoreMain.debug("Updated " + sizesKey + ", to: " + newSize + ", bytes: " + byteBuf.readableBytes());
                }
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