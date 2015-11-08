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

@PacketClass(id = 0x07, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND, size = 10)
public class PacketPlayClientBlockDig extends PacketPlayClient
{
    private BlockDigAction action; // 1 byte
    private BlockLocation  blockLocation; // 8 bytes
    private BlockFace      blockFace; // 1 byte

    public PacketPlayClientBlockDig()
    {
    }

    public PacketPlayClientBlockDig(final BlockDigAction action)
    {
        this.action = action;
        this.blockLocation = BlockLocation.ZERO;
    }

    public PacketPlayClientBlockDig(final BlockDigAction action, final BlockLocation blockLocation, final BlockFace blockFace)
    {
        this.action = action;
        this.blockLocation = blockLocation;
        this.blockFace = blockFace;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.action = BlockDigAction.values()[data.readByte()];
        this.blockLocation = data.readBlockLocation();
        this.blockFace = data.readBlockFace();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeByte(this.action.ordinal());
        data.writeBlockLocation(this.blockLocation);
        data.writeBlockFace(this.blockFace);
    }

    @Override
    public void handle(final PacketPlayClientListener listener)
    {
        listener.handle(this);
    }

    public BlockDigAction getAction()
    {
        return this.action;
    }

    public void setAction(final BlockDigAction action)
    {
        this.action = action;
    }

    public BlockLocation getBlockLocation()
    {
        return this.blockLocation;
    }

    public void setBlockLocation(final BlockLocation blockLocation)
    {
        this.blockLocation = blockLocation;
    }

    public BlockFace getBlockFace()
    {
        return this.blockFace;
    }

    public void setBlockFace(final BlockFace blockFace)
    {
        this.blockFace = blockFace;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("action", this.action).append("blockLocation", this.blockLocation).append("blockFace", this.blockFace).toString();
    }

    public enum BlockDigAction
    {
        START_DIG,
        CANCEL_DIG,
        FINISH_DIG,
        DROP_ITEM_STACK,
        DROP_ITEM,
        SHOT_ARROW_OR_EAT
    }
}
