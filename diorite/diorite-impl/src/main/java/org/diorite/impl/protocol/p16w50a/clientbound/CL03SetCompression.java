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

import org.diorite.core.protocol.AbstractPacketDataSerializer;
import org.diorite.core.protocol.InvalidPacketException;
import org.diorite.core.protocol.PacketClass;
import org.diorite.core.protocol.connection.ProtocolDirection;
import org.diorite.core.protocol.connection.internal.ProtocolState;

@SuppressWarnings("MagicNumber")
@PacketClass(id = 0x03, direction = ProtocolDirection.CLIENTBOUND, state = ProtocolState.LOGIN, minSize = 1, maxSize = 5, preferredSize = 5)
public class CL03SetCompression extends ClientboundPacket
{
    private int threshold;

    public CL03SetCompression()
    {
    }

    public CL03SetCompression(int threshold)
    {
        this.threshold = threshold;
    }

    public int getThreshold()
    {
        return this.threshold;
    }

    public void setThreshold(int threshold)
    {
        this.threshold = threshold;
        this.setDirty();
    }

    @Override
    protected void write(AbstractPacketDataSerializer serializer) throws InvalidPacketException
    {
        serializer.writeVarInt(this.threshold);
    }
}
