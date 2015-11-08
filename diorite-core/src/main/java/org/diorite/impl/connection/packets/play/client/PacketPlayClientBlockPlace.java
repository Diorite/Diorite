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
import org.diorite.BlockFace;
import org.diorite.BlockLocation;
import org.diorite.utils.CursorPos;

@PacketClass(id = 0x08, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND, size = 50)
public class PacketPlayClientBlockPlace extends PacketPlayClient
{
    private BlockLocation location; // 8 bytes
    // private ItemStackImpl itemStack; // 2 bytes if null, 7 if simple item. ~40 // ignored by server, always read as null to prevent memory leaks and client ability to crash server.
    private CursorPos     cursorPos; // 4 bytes

    public PacketPlayClientBlockPlace()
    {
    }

    public PacketPlayClientBlockPlace(final BlockLocation location, final BlockFace blockFace, final float cursorX, final float cursorY, final float cursorZ)
    {
        this.location = location;
        this.cursorPos = new CursorPos(blockFace, cursorX, cursorY, cursorZ);
    }

    public PacketPlayClientBlockPlace(final BlockLocation location, final CursorPos cursorPos)
    {
        this.location = location;
        this.cursorPos = cursorPos;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.location = data.readBlockLocation();
        final BlockFace blockFace = data.readBlockFace();

        // this.itemStack = data.readItemStack(); // don't read item stack, skip all bytes instead
        data.skipBytes(data.readableBytes() - 3); // skip rest of bytes, except last 3 (cursor pos)

        this.cursorPos = new CursorPos(blockFace, data.readUnsignedByte(), data.readUnsignedByte(), data.readUnsignedByte());
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeBlockLocation(this.location);
        data.writeBlockFace(this.cursorPos.getBlockFace());
        data.writeItemStack(null);
        data.writeByte(this.cursorPos.getPixelX());
        data.writeByte(this.cursorPos.getPixelY());
        data.writeByte(this.cursorPos.getPixelZ());
    }

    public BlockLocation getLocation()
    {
        return this.location;
    }

    public void setLocation(final BlockLocation location)
    {
        this.location = location;
    }

    public CursorPos getCursorPos()
    {
        return this.cursorPos;
    }

    public void setCursorPos(final CursorPos cursorPos)
    {
        this.cursorPos = cursorPos;
    }

    @Override
    public void handle(final PacketPlayClientListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("location", this.location).append("cursorPos", this.cursorPos).toString();
    }
}
