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

package org.diorite.core.protocol.packets.serverbound;

import javax.annotation.Nullable;

import org.diorite.core.protocol.ProtocolVersion;

public class LoginStartPacket implements ServerboundDioritePacket
{
    @Nullable private ProtocolVersion<?> protocolVersion;
    @Nullable private String             serverAddress;
    private           int                serverPort;
    @Nullable private String             username;

    @Nullable
    public ProtocolVersion<?> getProtocolVersion()
    {
        return this.protocolVersion;
    }

    public void setProtocolVersion(ProtocolVersion<?> protocolVersion)
    {
        this.protocolVersion = protocolVersion;
    }

    @Nullable
    public String getServerAddress()
    {
        return this.serverAddress;
    }

    public void setServerAddress(String serverAddress)
    {
        this.serverAddress = serverAddress;
    }

    public int getServerPort()
    {
        return this.serverPort;
    }

    public void setServerPort(int serverPort)
    {
        this.serverPort = serverPort;
    }

    @Nullable
    public String getUsername()
    {
        return this.username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }
}
