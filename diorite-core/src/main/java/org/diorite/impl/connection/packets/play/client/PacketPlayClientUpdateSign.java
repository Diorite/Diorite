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
import org.diorite.BlockLocation;
import org.diorite.chat.component.BaseComponent;

@PacketClass(id = 0x12, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND, size = 350)
public class PacketPlayClientUpdateSign extends PacketPlayClient
{
    private BlockLocation location; // 8 bytes
    private BaseComponent[] lines; // ~256 bytes

    public PacketPlayClientUpdateSign()
    {
    }

    public PacketPlayClientUpdateSign(final BlockLocation location, final BaseComponent... lines)
    {
        this.location = location;
        this.lines = lines;
    }

    public BlockLocation getLocation()
    {
        return this.location;
    }

    public void setLocation(final BlockLocation location)
    {
        this.location = location;
    }

    public BaseComponent getLine(final int id)
    {
        return this.lines[id];
    }

    public void setLine(final int id, final BaseComponent text)
    {
        this.lines[id] = text;
    }

    public BaseComponent[] getLines()
    {
        return this.lines;
    }

    public void setLines(final BaseComponent[] lines)
    {
        this.lines = lines;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.location = data.readBlockLocation();

        this.lines = new BaseComponent[4];
        for (int i = 0; i < 4; i++)
        {
            this.lines[i] = data.readBaseComponent();
        }
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeBlockLocation(this.location);
        for (int i = 0; i < 4; i++)
        {
            data.writeBaseComponent(this.lines[i]);
        }
    }

    @Override
    public void handle(final PacketPlayClientListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("location", this.location).append("lines", this.lines).toString();
    }
}
