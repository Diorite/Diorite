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

import java.util.EnumMap;

import org.diorite.core.protocol.ProtocolVersion;
import org.diorite.core.protocol.connection.ProtocolDirection;
import org.diorite.core.protocol.connection.internal.Packet;
import org.diorite.core.protocol.connection.internal.PacketType;
import org.diorite.core.protocol.connection.internal.Packets;
import org.diorite.core.protocol.connection.internal.ProtocolState;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

public class PacketsImpl implements Packets
{
    private final ProtocolVersion<?> protocolVersion;
    private final Object2ObjectMap<Class<?>, PacketType>            packets                = new Object2ObjectOpenHashMap<>(100);
    private final EnumMap<ProtocolState, Int2ObjectMap<PacketType>> packetsByIdServerbound = new EnumMap<>(ProtocolState.class);
    private final EnumMap<ProtocolState, Int2ObjectMap<PacketType>> packetsByIdClientbound = new EnumMap<>(ProtocolState.class);

    {
        this.packetsByIdServerbound.put(ProtocolState.HANDSHAKE, new Int2ObjectOpenHashMap<>(1));
        this.packetsByIdClientbound.put(ProtocolState.HANDSHAKE, new Int2ObjectOpenHashMap<>(0));
        this.packetsByIdServerbound.put(ProtocolState.LOGIN, new Int2ObjectOpenHashMap<>(2));
        this.packetsByIdClientbound.put(ProtocolState.LOGIN, new Int2ObjectOpenHashMap<>(4));
        this.packetsByIdServerbound.put(ProtocolState.STATUS, new Int2ObjectOpenHashMap<>(2));
        this.packetsByIdClientbound.put(ProtocolState.STATUS, new Int2ObjectOpenHashMap<>(2));
        this.packetsByIdServerbound.put(ProtocolState.PLAY, new Int2ObjectOpenHashMap<>(50));
        this.packetsByIdClientbound.put(ProtocolState.PLAY, new Int2ObjectOpenHashMap<>(100));
    }

    private int maxServerboundPacketSize = 0;

    public PacketsImpl(ProtocolVersion<?> protocolVersion)
    {
        this.protocolVersion = protocolVersion;
    }

    @Override
    public boolean isRegistered(Class<? extends Packet> clazz)
    {
        return this.packets.containsKey(clazz);
    }

    @Override
    public PacketType getType(Class<? extends Packet> clazz)
    {
        PacketType packetType = this.packets.get(clazz);
        if (packetType == null)
        {
            throw new IllegalArgumentException("Invalid packet: " + clazz);
        }
        return packetType;
    }

    @Override
    public PacketType getServerboundType(ProtocolState protocolState, int id)
    {
        Int2ObjectMap<PacketType> int2ObjectMap = this.packetsByIdServerbound.get(protocolState);
        PacketType packetType = int2ObjectMap.get(id);
        if (packetType == null)
        {
            throw new IllegalArgumentException("Invalid packet: " + ProtocolDirection.SERVERBOUND.getName() + " " + protocolState.getName() + " 0x" +
                                               Integer.toHexString(id) + "/" + id);
        }
        return packetType;
    }

    @Override
    public PacketType getClientboundType(ProtocolState protocolState, int id)
    {
        Int2ObjectMap<PacketType> int2ObjectMap = this.packetsByIdClientbound.get(protocolState);
        PacketType packetType = int2ObjectMap.get(id);
        if (packetType == null)
        {
            throw new IllegalArgumentException("Invalid packet: " + ProtocolDirection.CLIENTBOUND.getName() + " " + protocolState.getName() + " 0x" +
                                               Integer.toHexString(id) + "/" + id);
        }
        return packetType;
    }

    @Override
    public void addType(Class<? extends Packet> clazz, PacketType type)
    {
        if (type.getType() != clazz)
        {
            throw new IllegalArgumentException("Invalid type: " + clazz + " != " + type);
        }
        if (this.packets.containsKey(clazz))
        {
            throw new IllegalStateException("Type already registred: " + type);
        }
        this.packets.put(clazz, type);
        switch (type.getDirection())
        {
            case SERVERBOUND:
                this.packetsByIdServerbound.get(type.getState()).put(type.getId(), type);
                break;
            case CLIENTBOUND:
                this.packetsByIdClientbound.get(type.getState()).put(type.getId(), type);
                break;
        }
        if (type.getMaxSize() > this.maxServerboundPacketSize)
        {
            this.maxServerboundPacketSize = type.getMaxSize();
        }
    }

    @Override
    public int getMaxServerboundPacketSize()
    {
        return this.maxServerboundPacketSize;
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public int getMaxServerboundCompressedPacketSize()
    {
        return (int) ((this.maxServerboundPacketSize / 10d) * 8d);
    }
}
