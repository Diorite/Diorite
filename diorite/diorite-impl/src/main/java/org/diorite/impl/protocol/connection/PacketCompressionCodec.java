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
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.core.protocol.AbstractPacketDataSerializer;
import org.diorite.impl.protocol.connection.ByteToMessageCodec.PacketByteBufByteToMessageCodec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;

@SuppressWarnings("MagicNumber")
public class PacketCompressionCodec extends PacketByteBufByteToMessageCodec
{
    public static final int    MAX_PACKET_SIZE = 2097152; // 2 MB
    private final       byte[] bytes           = new byte[8192];
    private final Inflater inflater;
    private final Deflater deflater;
    private       int      threshold;

    public PacketCompressionCodec(int threshold)
    {
        this.threshold = threshold;
        this.deflater = new Deflater();
        this.inflater = new Inflater();
    }

    private static final Int2IntMap sizes;

    static
    {
        sizes = new Int2IntOpenHashMap(641, .1f);
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

    private static int getSize(int key)
    {
        if (key < 128)
        {
            return 0;
        }
        return sizes.get(key);
    }

    private static void update(int key, int newValue)
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

    private static int getKey(int i)
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
    protected ByteBuf allocateBuffer(ChannelHandlerContext ctx, ByteBuf msg, boolean preferDirect) throws Exception
    {
        // TODO: keep this in sane values, to keep byte buffers not extends when writing, but also don't crate 2 000 000 byte buffers for 50 000 bytes data.
        int i = msg.readableBytes();
        int sizesKey = getKey(i);
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
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf srcByteBuf, ByteBuf byteBuf)
    {
        int i = srcByteBuf.readableBytes();
        if (i < this.threshold)
        {
            AbstractPacketDataSerializer.writeVarInt(byteBuf, 0);
            byteBuf.writeBytes(srcByteBuf);
        }
        else
        {
            byte[] arrayOfByte = new byte[i];
            srcByteBuf.readBytes(arrayOfByte);


            AbstractPacketDataSerializer.writeVarInt(byteBuf, arrayOfByte.length);

            this.deflater.setInput(arrayOfByte, 0, i);
            this.deflater.finish();
            while (! this.deflater.finished())
            {
                int j = this.deflater.deflate(this.bytes);
                byteBuf.writeBytes(this.bytes, 0, j);
            }

            if (i > 127)
            {
                int sizesKey = getKey(i);
                int maxSize = getSize(sizesKey);
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
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> paramList) throws DataFormatException
    {
        if (byteBuf.readableBytes() == 0)
        {
            return;
        }

        int i = AbstractPacketDataSerializer.readVarInt(byteBuf);
        if (i == 0)
        {
            paramList.add(byteBuf.slice());
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
            byte[] arrayOfByte1 = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(arrayOfByte1);
            this.inflater.setInput(arrayOfByte1);

            byte[] arrayOfByte2 = new byte[i];
            this.inflater.inflate(arrayOfByte2);
            paramList.add(Unpooled.wrappedBuffer(arrayOfByte2));

            this.inflater.reset();
        }
    }

    public void setThreshold(int threshold)
    {
        this.threshold = threshold;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("deflater", this.deflater).append("threshold", this.threshold).toString();
    }
}