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

package org.diorite.impl.protocol.p16w50a;

import java.util.Objects;
import java.util.Set;

import org.diorite.impl.protocol.PCProtocol;
import org.diorite.impl.protocol.PCProtocolVersion;
import org.diorite.impl.protocol.any.serverbound.ServerboundHandshakeListener;
import org.diorite.impl.protocol.p16w50a.clientbound.CS00Response;
import org.diorite.impl.protocol.p16w50a.clientbound.CS01Pong;
import org.diorite.impl.protocol.p16w50a.serverbound.SL00LoginStart;
import org.diorite.impl.protocol.p16w50a.serverbound.SS00Request;
import org.diorite.impl.protocol.p16w50a.serverbound.SS01Ping;
import org.diorite.impl.protocol.p16w50a.serverbound.ServerboundLoginPacketListener;
import org.diorite.impl.protocol.p16w50a.serverbound.ServerboundStatusPacketListener;
import org.diorite.DioriteConfig;
import org.diorite.core.protocol.connection.ActiveConnection;
import org.diorite.core.protocol.connection.ServerboundPacketHandler;
import org.diorite.core.protocol.connection.internal.ProtocolState;
import org.diorite.core.protocol.connection.internal.ServerboundPacketListener;
import org.diorite.core.protocol.packets.clientbound.ClientboundDioritePacket;
import org.diorite.core.protocol.packets.serverbound.ServerboundDioritePacket;

public class PCp16w50a extends PCProtocolVersion
{
    public static final int VERSION = 316;

    public PCp16w50a(PCProtocol protocol, DioriteConfig settings)
    {
        super(protocol, VERSION, "p16w50a", true, Objects.requireNonNull(settings.findProtocol(Set.of("p16w50a", "1.11.1", "1.11.2"))));
        this.aliases.add("1.11.1");
        this.aliases.add("1.11.2");
        this.packets.addType(CS00Response.class, CS00Response::new);
        this.packets.addType(CS01Pong.class, CS01Pong::new);

        this.packets.addType(SS00Request.class, SS00Request::new);
        this.packets.addType(SS01Ping.class, SS01Ping::new);
        this.packets.addType(SL00LoginStart.class, SL00LoginStart::new);
    }

    @Override
    public boolean isStable()
    {
        return true;
    }

    @Override
    public void setListener(ActiveConnection activeConnection, ProtocolState state)
    {
        switch (state)
        {
            case HANDSHAKE:
                activeConnection.setPacketListener(new ServerboundHandshakeListener(activeConnection));
                break;
            case STATUS:
                ServerboundPacketListener packetListener = activeConnection.getPacketListener();
                if (packetListener instanceof ServerboundHandshakeListener)
                {
                    ServerboundHandshakeListener handshakeListener = (ServerboundHandshakeListener) packetListener;
                    activeConnection.setPacketListener(new ServerboundStatusPacketListener(activeConnection, handshakeListener.getPacket()));
                }
                else
                {
                    activeConnection.setPacketListener(new ServerboundStatusPacketListener(activeConnection, null));
                }
                break;
            case LOGIN:
                activeConnection.setPacketListener(new ServerboundLoginPacketListener());
                break;
            case PLAY:
                break;
            default:
                throw new IllegalStateException("Unknown state:" + state);
        }
    }

    @Override
    public void handlePacket(ActiveConnection activeConnection, ServerboundDioritePacket packet)
    {
        activeConnection.handlePacket(packet);
    }

    @Override
    public void sendPacket(ActiveConnection activeConnection, ClientboundDioritePacket packet)
    {
        activeConnection.sendPacket(packet);
    }

    @Override
    public ServerboundPacketHandler createPacketHandler(ActiveConnection connection)
    {
        return new PCp16w50aServerboundPacketHandler(connection);
    }
}
