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
import org.diorite.BlockFace;
import org.diorite.BlockLocation;
import org.diorite.entity.data.HandType;
import org.diorite.utils.CursorPos;

@PacketClass(id = 0x1C, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND, size = 15)
public class PacketPlayServerboundBlockPlace extends PacketPlayServerbound
{
    private BlockLocation location; // 8 bytes
    private CursorPos     cursorPos; // 5 bytes

    public PacketPlayServerboundBlockPlace()
    {
    }

    public PacketPlayServerboundBlockPlace(final BlockLocation location, final BlockFace blockFace, final HandType handType, final float cursorX, final float cursorY, final float cursorZ)
    {
        this.location = location;
        this.cursorPos = new CursorPos(blockFace, handType, cursorX, cursorY, cursorZ);
    }

    public PacketPlayServerboundBlockPlace(final BlockLocation location, final CursorPos cursorPos)
    {
        this.location = location;
        this.cursorPos = cursorPos;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.location = data.readBlockLocationFromLong();
        this.cursorPos = new CursorPos(readBlockFace(data), data.readEnum(HandType.class), data.readUnsignedByte(), data.readUnsignedByte(), data.readUnsignedByte());
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeBlockLocationAsLong(this.location);
        writeBlockFace(this.cursorPos.getBlockFace(), data);
        data.writeEnum(this.cursorPos.getHandType());
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
    public void handle(final PacketPlayServerboundListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("location", this.location).append("cursorPos", this.cursorPos).toString();
    }

    protected static BlockFace readBlockFace(final PacketDataSerializer data)
    {
        return PacketPlayServerboundBlockDig.readBlockFace(data.readVarInt());
    }

    protected static void writeBlockFace(final BlockFace face, final PacketDataSerializer data)
    {
        if (face == null)
        {
            data.writeVarInt(- 1);
            return;
        }
        switch (face)
        {
            case NORTH:
                data.writeVarInt(2);
                break;
            case EAST:
                data.writeVarInt(5);
                break;
            case SOUTH:
                data.writeVarInt(3);
                break;
            case WEST:
                data.writeVarInt(4);
                break;
            case UP:
                data.writeVarInt(1);
                break;
            case DOWN:
                data.writeVarInt(0);
                break;
            default:
                data.writeVarInt(- 1);
                break;
        }
    }

}
