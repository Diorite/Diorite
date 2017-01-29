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

import javax.crypto.SecretKey;

import java.net.InetSocketAddress;

import org.diorite.impl.protocol.MinecraftEncryption;
import org.diorite.impl.protocol.PacketEncryptionCodec;
import org.diorite.core.DioriteCore;
import org.diorite.core.protocol.ProtocolVersion;
import org.diorite.core.protocol.connection.ActiveConnection;
import org.diorite.core.protocol.connection.internal.Packet;
import org.diorite.core.protocol.connection.internal.ServerboundPacketListener;

public class DioriteActiveConnection<T extends Packet<? super ServerboundPacketListener>> extends ActiveConnection
{
    public DioriteActiveConnection(DioriteCore dioriteCore, InetSocketAddress serverAddress, ServerboundPacketListener packetListener,
                                   ProtocolVersion<?> protocolVersion)
    {
        super(dioriteCore, serverAddress, packetListener, protocolVersion);
    }

    @Override
    public void enableEncryption(SecretKey secretkey)
    {
        if (this.closed)
        {
            this.handleClosed();
            return;
        }
        assert this.channel != null;
        this.channel.pipeline().addBefore("sizer", "encryption",
                                          new PacketEncryptionCodec(MinecraftEncryption.getCipher(1, secretkey), MinecraftEncryption.getCipher(2, secretkey)));
    }

    @Override
    public void setCompression(int i)
    {
        if (this.closed)
        {
            this.handleClosed();
            return;
        }
        assert this.channel != null;
        if (i >= 0)
        {
            if ((this.channel.pipeline().get("compression") instanceof PacketCompressionCodec))
            {
                ((PacketCompressionCodec) this.channel.pipeline().get("compression")).setThreshold(i);
            }
            else
            {
                this.channel.pipeline().addBefore("codec", "compression", new PacketCompressionCodec(i));
            }
        }
        else
        {
            if ((this.channel.pipeline().get("compression") instanceof PacketCompressionCodec))
            {
                this.channel.pipeline().remove("compression");
            }
        }
    }
}
