/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.impl.connection.packets.play.clientbound;

import java.io.IOException;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayClientboundListener;
import org.diorite.world.World;

@PacketClass(id = 0x44, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 10)
public class PacketPlayClientboundUpdateTime extends PacketPlayClientbound
{
    private long worldAge;
    private long time;

    public PacketPlayClientboundUpdateTime()
    {
    }

    public PacketPlayClientboundUpdateTime(final long worldAge, final long time)
    {
        this.worldAge = worldAge;
        this.time = time;
    }

    public PacketPlayClientboundUpdateTime(final World world)
    {
        this.worldAge = 0; // TODO
        this.time = world.getTime();
    }

    public long getWorldAge()
    {
        return worldAge;
    }

    public void setWorldAge(final long worldAge)
    {
        this.worldAge = worldAge;
    }

    public long getTime()
    {
        return time;
    }

    public void setTime(final long time)
    {
        this.time = time;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.worldAge = data.readLong();
        this.time = data.readLong();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeLong(this.worldAge);
        data.writeLong(this.time);
    }

    @Override
    public void handle(final PacketPlayClientboundListener listener)
    {
        listener.handle(this);
    }
}
