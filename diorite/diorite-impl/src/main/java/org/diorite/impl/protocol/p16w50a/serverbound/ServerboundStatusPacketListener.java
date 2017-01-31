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

package org.diorite.impl.protocol.p16w50a.serverbound;

import javax.annotation.Nullable;

import java.net.InetSocketAddress;

import org.diorite.impl.protocol.any.serverbound.H00Handshake;
import org.diorite.impl.protocol.p16w50a.clientbound.CS01Pong;
import org.diorite.chat.ChatMessage;
import org.diorite.core.protocol.connection.ActiveConnection;
import org.diorite.core.protocol.connection.internal.ServerboundPacketListener;
import org.diorite.core.protocol.packets.serverbound.RequestServerStatePacket;

public class ServerboundStatusPacketListener implements ServerboundPacketListener
{
    private final           ActiveConnection activeConnection;
    @Nullable private final H00Handshake     handshakePacket;

    public ServerboundStatusPacketListener(ActiveConnection activeConnection, @Nullable H00Handshake handshakePacket)
    {
        this.activeConnection = activeConnection;
        this.handshakePacket = handshakePacket;
    }

    @Override
    public void disconnect(@Nullable ChatMessage disconnectMessage)
    {
        this.activeConnection.close(disconnectMessage, true);
    }

    private volatile boolean handled = false;

    public void handle(SS00Request packet)
    {
        if (this.handled)
        {
            this.disconnect(null);
            return;
        }
        this.handled = true;
        RequestServerStatePacket requestServerStatePacket = new RequestServerStatePacket();
        requestServerStatePacket.setProtocolVersion(this.activeConnection.getProtocolVersion());
        InetSocketAddress serverAddress = this.activeConnection.getServerAddress();
        if (this.handshakePacket == null)
        {
            requestServerStatePacket.setServerAddress(serverAddress.getAddress().toString());
            requestServerStatePacket.setServerPort(serverAddress.getPort());
        }
        else
        {
            String handshakeAddress = this.handshakePacket.getAddress();
            assert handshakeAddress != null;
            requestServerStatePacket.setServerAddress(handshakeAddress);
            requestServerStatePacket.setServerPort(this.handshakePacket.getPort());
        }
        this.activeConnection.getProtocolVersion().handlePacket(requestServerStatePacket);
    }

    public void handle(SS01Ping packet)
    {
        CS01Pong cs01Pong = new CS01Pong();
        cs01Pong.setTime(packet.getTime());
        this.activeConnection.sendPacket(cs01Pong);
        this.disconnect(null);
    }
}
