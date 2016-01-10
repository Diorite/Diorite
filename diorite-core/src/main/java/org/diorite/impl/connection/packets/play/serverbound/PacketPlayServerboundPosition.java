/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.impl.connection.packets.play.serverbound;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayServerboundListener;

@PacketClass(id = 0x0C, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND, size = 25)
public class PacketPlayServerboundPosition extends PacketPlayServerbound
{
    private double  x; // 8 bytes
    private double  y; // 8 bytes
    private double  z; // 8 bytes
    private boolean onGround; // 1 byte

    public PacketPlayServerboundPosition()
    {
    }

    public PacketPlayServerboundPosition(final double x, final boolean onGround, final double z, final double y)
    {
        this.x = x;
        this.onGround = onGround;
        this.z = z;
        this.y = y;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.x = data.readDouble();
        this.y = data.readDouble();
        this.z = data.readDouble();
        this.onGround = data.readBoolean();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeDouble(this.x);
        data.writeDouble(this.y);
        data.writeDouble(this.z);
        data.writeBoolean(this.onGround);
    }

    @Override
    public void handle(final PacketPlayServerboundListener listener)
    {
        listener.handle(this);
    }

    public double getX()
    {
        return this.x;
    }

    public void setX(final double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return this.y;
    }

    public void setY(final double y)
    {
        this.y = y;
    }

    public double getZ()
    {
        return this.z;
    }

    public void setZ(final double z)
    {
        this.z = z;
    }

    public boolean isOnGround()
    {
        return this.onGround;
    }

    public void setOnGround(final boolean onGround)
    {
        this.onGround = onGround;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("x", this.x).append("y", this.y).append("z", this.z).append("onGround", this.onGround).toString();
    }
}
