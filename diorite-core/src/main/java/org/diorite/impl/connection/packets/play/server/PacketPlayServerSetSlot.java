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

package org.diorite.impl.connection.packets.play.server;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayServerListener;
import org.diorite.inventory.item.ItemStack;

@PacketClass(id = 0x2F, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 100)
public class PacketPlayServerSetSlot extends PacketPlayServer
{
    private int       windowId; // 1 byte
    private short     slotId; // 2 bytes
    private ItemStack item; // ~1-5+ bytes

    public PacketPlayServerSetSlot()
    {
    }

    public PacketPlayServerSetSlot(final int windowId, final short slotId, final ItemStack item)
    {
        this.windowId = windowId;
        this.slotId = slotId;
        this.item = item;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.windowId = data.readUnsignedByte();
        this.slotId = data.readShort();
        this.item = data.readItemStack();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeByte(this.windowId);
        data.writeShort(this.slotId);
        data.writeItemStack(this.item); // TODO Test this, i am not sure if this is good
    }

    @Override
    public void handle(final PacketPlayServerListener listener)
    {
        listener.handle(this);
    }

    public int getWindowId()
    {
        return this.windowId;
    }

    public void setWindowId(final int windowId)
    {
        this.windowId = windowId;
    }

    public short getSlotId()
    {
        return this.slotId;
    }

    public void setSlotId(final short slotId)
    {
        this.slotId = slotId;
    }

    public ItemStack getItem()
    {
        return this.item;
    }

    public void setItem(final ItemStack item)
    {
        this.item = item;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("windowId", this.windowId).append("slotId", this.slotId).append("item", this.item).toString();
    }
}
