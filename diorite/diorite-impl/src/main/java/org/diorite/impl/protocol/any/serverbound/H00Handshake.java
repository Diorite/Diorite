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

package org.diorite.impl.protocol.any.serverbound;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.diorite.impl.protocol.AbstractPacketDataSerializer;
import org.diorite.impl.protocol.PCPacket;
import org.diorite.core.protocol.InvalidPacketException;
import org.diorite.core.protocol.PacketClass;
import org.diorite.core.protocol.connection.ProtocolDirection;
import org.diorite.core.protocol.connection.internal.ProtocolState;

import io.netty.buffer.ByteBuf;

@SuppressWarnings("MagicNumber")
@PacketClass(id = 0x00, direction = ProtocolDirection.SERVERBOUND, state = ProtocolState.HANDSHAKE, minSize = 1 + 1 + 2 + 1, maxSize = 5 + 100 + 2 + 1)
public class H00Handshake extends PCPacket<ServerboundHandshakeListener>
{
    private           int           protocolVersion; // varInt 1-5 bytes, currently: 2 bytes
    private @Nullable String        address; // max length of 50 to prevent spamming with huge packets, max 100 bytes
    private           int           port; // unsigned short
    private @Nullable ProtocolState state; // varint, 1 byte

    @Override
    protected AbstractPacketDataSerializer createSerializer(ByteBuf byteBuf)
    {
        return new PacketDataSerializer(byteBuf);
    }

    @Override
    protected void read(@Nonnull AbstractPacketDataSerializer serializer) throws InvalidPacketException
    {
        this.protocolVersion = serializer.readVarInt();
        this.address = serializer.readText(50, 100);
        this.port = serializer.readUnsignedShort();
        this.state = serializer.readEnum(ProtocolState.class);
    }

    @Override
    public void handle(@Nonnull ServerboundHandshakeListener packetListener)
    {
        packetListener.handle(this);
    }

    public int getProtocolVersion()
    {
        return this.protocolVersion;
    }

    public void setProtocolVersion(int protocolVersion)
    {
        this.protocolVersion = protocolVersion;
    }

    @Nullable
    public String getAddress()
    {
        return this.address;
    }

    public void setAddress(@Nullable String address)
    {
        this.address = address;
    }

    public int getPort()
    {
        return this.port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    @Nullable
    public ProtocolState getState()
    {
        return this.state;
    }

    public void setState(@Nullable ProtocolState state)
    {
        this.state = state;
    }
}
