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

package org.diorite.impl.connection;


import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.packets.Packet;
import org.diorite.impl.connection.packets.PacketClass;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.internal.TypeParameterMatcher;

/**
 * Diorite edited copy of {@link io.netty.handler.codec.ByteToMessageCodec}
 * <br>
 * A Codec for on-the-fly encoding/decoding of bytes to messages and vise-versa.
 * <br>
 * This can be thought of as a combination of {@link ByteToMessageDecoder} and {@link MessageToByteEncoder}.
 * <br>
 * Be aware that sub-classes of {@link ByteToMessageCodec} <strong>MUST NOT</strong>
 * annotated with {@link io.netty.channel.ChannelHandler.Sharable}.
 */
public abstract class ByteToMessageCodec<I> extends ChannelHandlerAdapter
{
    public abstract static class PacketByteToMessageCodec extends ByteToMessageCodec<Packet<?>>
    {
        @SuppressWarnings("MagicNumber")
        private static int varintSize(final int i)
        {
            if ((i < 0) || (i >= 268435456))
            {
                return 5;
            }
            if (i < 128)
            {
                return 1;
            }
            if (i < 16384)
            {
                return 2;
            }
            if (i < 2097152)
            {
                return 3;
            }
            if (i < 268435456)
            {
                return 4;
            }
            throw new AssertionError();
        }

        @Override
        protected ByteBuf allocateBuffer(final ChannelHandlerContext ctx, final Packet<?> msg, final boolean preferDirect) throws Exception
        {
            final PacketClass pc = msg.getPacketData();
            final int size = varintSize(pc.id()) + pc.size();
            if (size < 0)
            {
                throw new IllegalArgumentException("Size can't be lower than 0!");
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
    }

    public abstract static class PacketByteBufByteToMessageCodec extends ByteToMessageCodec<ByteBuf>
    {
        @SuppressWarnings("MagicNumber")
        private static int varintSize(final int i)
        {
            if ((i < 0) || (i >= 268435456))
            {
                return 5;
            }
            if (i < 128)
            {
                return 1;
            }
            if (i < 16384)
            {
                return 2;
            }
            if (i < 2097152)
            {
                return 3;
            }
            if (i < 268435456)
            {
                return 4;
            }
            throw new AssertionError();
        }

        @Override
        protected ByteBuf allocateBuffer(final ChannelHandlerContext ctx, final ByteBuf msg, final boolean preferDirect) throws Exception
        {
            final int dataSize = msg.readableBytes();
            final int size = varintSize(dataSize) + dataSize;
            if (size < 0)
            {
                throw new IllegalArgumentException("Size can't be lower than 0!");
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
    }

    private final TypeParameterMatcher    outboundMsgMatcher;
    private final MessageToByteEncoder<I> encoder;

    private final ByteToMessageDecoder decoder = new ByteToMessageDecoder()
    {
        @Override
        public void decode(final ChannelHandlerContext ctx, final ByteBuf in, final List<Object> out) throws Exception
        {
            ByteToMessageCodec.this.decode(ctx, in, out);
        }

        @Override
        protected void decodeLast(final ChannelHandlerContext ctx, final ByteBuf in, final List<Object> out) throws Exception
        {
            ByteToMessageCodec.this.decodeLast(ctx, in, out);
        }
    };

    /**
     * @see #ByteToMessageCodec(boolean) with {@code true} as boolean parameter.
     */
    protected ByteToMessageCodec()
    {
        this(true);
    }

    /**
     * @param outboundMessageType type of message.
     *
     * @see #ByteToMessageCodec(Class, boolean) with {@code true} as boolean value.
     */
    protected ByteToMessageCodec(final Class<? extends I> outboundMessageType)
    {
        this(outboundMessageType, true);
    }

    /**
     * Create a new instance which will try to detect the types to match out of the type parameter of the class.
     *
     * @param preferDirect {@code true} if a direct {@link ByteBuf} should be tried to be used as target for
     *                     the encoded messages. If {@code false} is used it will allocate a heap
     *                     {@link ByteBuf}, which is backed by an byte array.
     */
    protected ByteToMessageCodec(final boolean preferDirect)
    {
        this.outboundMsgMatcher = TypeParameterMatcher.find(this, ByteToMessageCodec.class, "I");
        this.encoder = new Encoder(preferDirect);
    }

    /**
     * Create a new instance
     *
     * @param outboundMessageType The type of messages to match
     * @param preferDirect        {@code true} if a direct {@link ByteBuf} should be tried to be used as target for
     *                            the encoded messages. If {@code false} is used it will allocate a heap
     *                            {@link ByteBuf}, which is backed by an byte array.
     */
    protected ByteToMessageCodec(final Class<? extends I> outboundMessageType, final boolean preferDirect)
    {
        this.outboundMsgMatcher = TypeParameterMatcher.get(outboundMessageType);
        this.encoder = new Encoder(preferDirect);
    }

    /**
     * Returns {@code true} if and only if the specified message can be encoded by this codec.
     *
     * @param msg the message
     *
     * @return {@code true} if and only if the specified message can be encoded by this codec.
     *
     * @throws Exception if any element of codec throw exception.
     */
    public boolean acceptOutboundMessage(final Object msg) throws Exception
    {
        return this.outboundMsgMatcher.match(msg);
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception
    {
        this.decoder.channelRead(ctx, msg);
    }

    @Override
    public void write(final ChannelHandlerContext ctx, final Object msg, final ChannelPromise promise) throws Exception
    {
        this.encoder.write(ctx, msg, promise);
    }

    /**
     * Encode a message into a {@link ByteBuf}. This method will be called for each written message that can be handled
     * by this encoder.
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link MessageToByteEncoder} belongs to
     * @param msg the message to encode
     * @param out the {@link ByteBuf} into which the encoded message will be written
     *
     * @throws Exception is thrown if an error accour
     */
    protected abstract void encode(ChannelHandlerContext ctx, I msg, ByteBuf out) throws Exception;

    /**
     * Decode the from one {@link ByteBuf} to an other. This method will be called till either the input
     * {@link ByteBuf} has nothing to read when return from this method or till nothing was read from the input
     * {@link ByteBuf}.
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link ByteToMessageDecoder} belongs to
     * @param in  the {@link ByteBuf} from which to read data
     * @param out the {@link List} to which decoded messages should be added
     *
     * @throws Exception is thrown if an error accour
     */
    protected abstract void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception;

    /**
     * Allocate a {@link ByteBuf} which will be used as argument of {@link #encode(ChannelHandlerContext, Object, ByteBuf)}.
     * Sub-classes may override this method to returna {@link ByteBuf} with a perfect matching {@code initialCapacity}.
     *
     * @param ctx          the {@link ChannelHandlerContext} which this {@link ByteToMessageDecoder} belongs to
     * @param msg          the message to encode
     * @param preferDirect if it should prefer direct buffer.
     *
     * @return created ByteBuf.
     *
     * @throws Exception if allocating buffer fail.
     */
    protected ByteBuf allocateBuffer(final ChannelHandlerContext ctx, final I msg, final boolean preferDirect) throws Exception
    {
        if (preferDirect)
        {
            return ctx.alloc().ioBuffer();
        }
        else
        {
            return ctx.alloc().heapBuffer();
        }
    }

    /**
     * Is called one last time when the {@link ChannelHandlerContext} goes in-active. Which means the
     * {@link #channelInactive(ChannelHandlerContext)} was triggered.
     * <br>
     * By default this will just call {@link #decode(ChannelHandlerContext, ByteBuf, List)} but sub-classes may
     * override this for some special cleanup operation.
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link ByteToMessageDecoder} belongs to
     * @param in  the {@link ByteBuf} from which to read data
     * @param out the {@link List} to which decoded messages should be added
     *
     * @throws Exception if decode fail.
     */
    protected void decodeLast(final ChannelHandlerContext ctx, final ByteBuf in, final List<Object> out) throws Exception
    {
        this.decode(ctx, in, out);
    }

    private final class Encoder extends MessageToByteEncoder<I>
    {
        Encoder(final boolean preferDirect)
        {
            super(preferDirect);
        }

        @Override
        public boolean acceptOutboundMessage(final Object msg) throws Exception
        {
            return ByteToMessageCodec.this.acceptOutboundMessage(msg);
        }

        @Override
        protected void encode(final ChannelHandlerContext ctx, final I msg, final ByteBuf out) throws Exception
        {
            ByteToMessageCodec.this.encode(ctx, msg, out);
        }

        @Override
        protected ByteBuf allocateBuffer(final ChannelHandlerContext ctx, final I msg, final boolean preferDirect) throws Exception
        {
            return ByteToMessageCodec.this.allocateBuffer(ctx, msg, preferDirect);
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("outboundMsgMatcher", this.outboundMsgMatcher).append("encoder", this.encoder).append("decoder", this.decoder).toString();
    }
}