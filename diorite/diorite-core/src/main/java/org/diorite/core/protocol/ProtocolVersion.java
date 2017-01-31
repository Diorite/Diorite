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

package org.diorite.core.protocol;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.diorite.DioriteConfig.ProtocolSettings;
import org.diorite.core.protocol.connection.ActiveConnection;
import org.diorite.core.protocol.connection.ServerboundPacketHandler;
import org.diorite.core.protocol.connection.internal.Packet;
import org.diorite.core.protocol.connection.internal.Packets;
import org.diorite.core.protocol.connection.internal.ProtocolState;
import org.diorite.core.protocol.packets.clientbound.ClientboundDioritePacket;
import org.diorite.core.protocol.packets.serverbound.ServerboundDioritePacket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public abstract class ProtocolVersion<T extends Protocol<?>>
{
    protected final T                protocol;
    protected final int              version;
    protected final String           versionName;
    protected final ProtocolSettings settings;

    public ProtocolVersion(T protocol, int version, String versionName, ProtocolSettings settings)
    {
        this.protocol = protocol;
        this.version = version;
        this.versionName = versionName;
        this.settings = settings;
    }

    public int getMaxServerboundPacketSize()
    {
        int maxServerboundPacketSize = this.settings.getMaxServerboundPacketSize();
        if (maxServerboundPacketSize == - 1)
        {
            return Integer.MAX_VALUE;
        }
        if (maxServerboundPacketSize == 0)
        {
            return this.getPackets().getMaxServerboundPacketSize();
        }
        return maxServerboundPacketSize;
    }

    public int getMaxServerboundCompressedPacketSize()
    {
        int maxServerboundCompressedPacketSize = this.settings.getMaxServerboundCompressedPacketSize();
        if (maxServerboundCompressedPacketSize == - 1)
        {
            return Integer.MAX_VALUE;
        }
        if (maxServerboundCompressedPacketSize == 0)
        {
            return this.getPackets().getMaxServerboundCompressedPacketSize();
        }
        return maxServerboundCompressedPacketSize;
    }

    public T getProtocol()
    {
        return this.protocol;
    }

    public abstract Packets getPackets();

    public abstract boolean isPacket(Class<? extends Packet<?>> packet);

    public int getVersion()
    {
        return this.version;
    }

    public String getVersionName()
    {
        return this.versionName;
    }

    public Set<String> getAliases()
    {
        return Collections.singleton(this.versionName);
    }

    public abstract boolean isStable();

    public abstract void decode(ChannelHandlerContext context, ByteBuf byteBuf, List<Object> packets) throws Exception;

    public abstract void setListener(ActiveConnection activeConnection, ProtocolState state);

    public abstract void handlePacket(ActiveConnection activeConnection, ServerboundDioritePacket packet);

    public abstract void sendPacket(ActiveConnection activeConnection, ClientboundDioritePacket packet);

    public abstract ServerboundPacketHandler createPacketHandler(ActiveConnection connection);
}
