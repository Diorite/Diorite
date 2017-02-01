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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.diorite.impl.protocol.AbstractPacketDataSerializer;
import org.diorite.core.protocol.InvalidPacketException;
import org.diorite.core.protocol.PacketClass;
import org.diorite.core.protocol.connection.ProtocolDirection;
import org.diorite.core.protocol.connection.internal.ProtocolState;

@SuppressWarnings("MagicNumber")
@PacketClass(id = 0x00, direction = ProtocolDirection.SERVERBOUND, state = ProtocolState.LOGIN, minSize = 4, maxSize = 17)
public class SL00LoginStart extends ServerboundLoginPacket
{
    private  @Nullable String username; // 16 simple chars = bytes + length 1 byte, min size: 4

    @Override
    protected void read(@Nonnull AbstractPacketDataSerializer serializer) throws InvalidPacketException
    {
        this.username = serializer.readText(16, 20);
    }

    @Nullable
    public String getUsername()
    {
        return this.username;
    }

    public void setUsername(@Nullable String username)
    {
        this.username = username;
        this.setDirty();
    }
}
