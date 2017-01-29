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

package org.diorite.impl.protocol;

import javax.annotation.Nonnull;

import org.diorite.commons.lazy.IntLazyValue;
import org.diorite.core.protocol.InvalidPacketException;
import org.diorite.core.protocol.connection.internal.Packet;
import org.diorite.core.protocol.connection.internal.ServerboundPacketListener;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public abstract class PCPacket<T extends ServerboundPacketListener> implements Packet<T>
{
    @Nonnull
    protected abstract AbstractPacketDataSerializer createSerializer(ByteBuf byteBuf);

    private final IntLazyValue id = new IntLazyValue(() -> this.packetType().getId());

    @Override
    public void read(ByteBuf byteBuf) throws InvalidPacketException
    {
        this.read(this.createSerializer(byteBuf));
    }

    @Override
    public void write(ByteBuf byteBuf) throws InvalidPacketException
    {
        this.write(this.createSerializer(byteBuf));
    }

    @Override
    public int id()
    {
        return this.id.get();
    }

    @Override
    public void encode(ChannelHandlerContext context, ByteBuf byteBuf) throws InvalidPacketException
    {
        int id = this.id();
        AbstractPacketDataSerializer dataSerializer = this.createSerializer(byteBuf);
        dataSerializer.writeVarInt(id);
        try
        {
            this.write(dataSerializer);
        }
        catch (Throwable throwable)
        {
            throw new InvalidPacketException("Can't encode packet: " + this.packetType(), throwable);
        }
    }

    @Override
    public void decode(ChannelHandlerContext context, ByteBuf byteBuf) throws InvalidPacketException
    {
        this.read(byteBuf);
    }

    protected void read(AbstractPacketDataSerializer serializer) throws InvalidPacketException
    {
        throw new UnsupportedOperationException("Read operation isn't supported by this packet!");
    }

    protected void write(AbstractPacketDataSerializer serializer) throws InvalidPacketException
    {
        throw new UnsupportedOperationException("Write operation isn't supported by this packet!");
    }
}
