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

package org.diorite.impl.protocol.p16w50a.clientbound;

import javax.annotation.Nullable;

import org.diorite.impl.protocol.AbstractPacketDataSerializer;
import org.diorite.core.protocol.InvalidPacketException;
import org.diorite.core.protocol.PacketClass;
import org.diorite.core.protocol.connection.ProtocolDirection;
import org.diorite.core.protocol.connection.internal.ProtocolState;
import org.diorite.gameprofile.GameProfile;

@PacketClass(id = 0x02, direction = ProtocolDirection.CLIENTBOUND, state = ProtocolState.STATUS, minSize = 54, maxSize = 54, preferredSize = 54)
public class CL02LoginSuccess extends ClientboundPacket
{
    private @Nullable GameProfile gameProfile; // 54 bytes, 1 + 36 + 1 + 16
    public CL02LoginSuccess()
    {
    }

    public CL02LoginSuccess(GameProfile gameProfile)
    {
        this.gameProfile = gameProfile;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void write(AbstractPacketDataSerializer serializer) throws InvalidPacketException
    {
        assert this.gameProfile != null;
        assert this.gameProfile.isComplete();
        serializer.writeText(this.gameProfile.getId().toString());
        serializer.writeText(this.gameProfile.getName());
    }
}
