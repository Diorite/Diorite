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

@PacketClass(id = 0x05, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND, size = 9)
public class PacketPlayClientLook extends PacketPlayClient
{
    private float   yaw; // 4 bytes
    private float   pitch; // 4 bytes
    private boolean onGround; // 1 byte

    public PacketPlayClientLook()
    {
    }

    public PacketPlayClientLook(final float yaw, final float pitch, final boolean onGround)
    {
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.yaw = data.readFloat();
        this.pitch = data.readFloat();
        this.onGround = data.readBoolean();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeFloat(this.yaw);
        data.writeFloat(this.pitch);
        data.writeBoolean(this.onGround);
    }

    @Override
    public void handle(final PacketPlayClientListener listener)
    {
        listener.handle(this);
    }

    public float getYaw()
    {
        return this.yaw;
    }

    public void setYaw(final float yaw)
    {
        this.yaw = yaw;
    }

    public float getPitch()
    {
        return this.pitch;
    }

    public void setPitch(final float pitch)
    {
        this.pitch = pitch;
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("yaw", this.yaw).append("pitch", this.pitch).append("onGround", this.onGround).toString();
    }
}
