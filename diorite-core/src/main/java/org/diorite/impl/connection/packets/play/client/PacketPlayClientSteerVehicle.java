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

package org.diorite.impl.connection.packets.play.client;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayClientListener;

@PacketClass(id = 0x0C, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND, size = 9)
public class PacketPlayClientSteerVehicle extends PacketPlayClient
{
    private float sideways; // 4 bytes
    private float forward; // 4 bytes
    private boolean jump; // 1/4 byte
    private boolean unMount; // 1/4 byte

    public PacketPlayClientSteerVehicle()
    {
    }

    public PacketPlayClientSteerVehicle(final float sideways, final float forward, final boolean jump, final boolean unMount)
    {
        this.sideways = sideways;
        this.forward = forward;
        this.jump = jump;
        this.unMount = unMount;
    }

    public float getSideways()
    {
        return this.sideways;
    }

    public void setSideways(final float sideways)
    {
        this.sideways = sideways;
    }

    public float getForward()
    {
        return this.forward;
    }

    public void setForward(final float forward)
    {
        this.forward = forward;
    }

    public boolean isJump()
    {
        return this.jump;
    }

    public void setJump(final boolean jump)
    {
        this.jump = jump;
    }

    public boolean isUnMount()
    {
        return this.unMount;
    }

    public void setUnMount(final boolean unMount)
    {
        this.unMount = unMount;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.sideways = data.readFloat();
        this.forward = data.readFloat();

        final byte temp = data.readByte();
        this.jump = (temp & 0x01) > 0;
        this.unMount = (temp & 0x02) > 0;
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeFloat(this.sideways);
        data.writeFloat(this.forward);

        byte temp = 0;
        if (this.jump)
        {
            temp |= 0x01;
        }
        if (this.unMount)
        {
            temp |= 0x02;
        }
        data.writeByte(temp);
    }

    @Override
    public void handle(final PacketPlayClientListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("sideways", this.sideways).append("forward", this.forward).append("jump", this.jump).append("unMount", this.unMount).toString();
    }
}
