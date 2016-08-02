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

package org.diorite.impl.connection.packets.play.serverbound;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayServerboundListener;
import org.diorite.block.BlockLocation;

@PacketClass(id = 0x01, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND, size = 141)
public class PacketPlayServerboundTabComplete extends PacketPlayServerbound
{
    private String        content; // ~128 bytes
    private boolean       assumeCommand; // 1 byte
    private BlockLocation blockLocation; // 8 bytes + bool

    public PacketPlayServerboundTabComplete()
    {
    }

    public PacketPlayServerboundTabComplete(final String content)
    {
        this.content = content;
    }

    public PacketPlayServerboundTabComplete(final String content, final boolean assumeCommand, final BlockLocation blockLocation)
    {
        this.content = content;
        this.assumeCommand = assumeCommand;
        this.blockLocation = blockLocation;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.content = data.readText(Short.MAX_VALUE);
        this.assumeCommand = data.readBoolean();
        this.blockLocation = data.readBoolean() ? data.readBlockLocationFromLong() : null;
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeText(this.content);
        data.writeBoolean(this.assumeCommand);
        data.writeBoolean(this.blockLocation != null);
        if (this.blockLocation != null)
        {
            data.writeBlockLocationAsLong(this.blockLocation);
        }
    }

    public String getContent()
    {
        return this.content;
    }

    public void setContent(final String content)
    {
        this.content = content;
    }

    public BlockLocation getBlockLocation()
    {
        return this.blockLocation;
    }

    public void setBlockLocation(final BlockLocation blockLocation)
    {
        this.blockLocation = blockLocation;
    }

    public boolean isAssumeCommand()
    {
        return this.assumeCommand;
    }

    public void setAssumeCommand(final boolean assumeCommand)
    {
        this.assumeCommand = assumeCommand;
    }

    @Override
    public void handle(final PacketPlayServerboundListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("content", this.content).append("blockLocation", this.blockLocation).toString();
    }
}
