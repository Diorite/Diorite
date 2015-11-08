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
import org.diorite.inventory.ClickType;
import org.diorite.inventory.item.ItemStack;

@PacketClass(id = 0x0E, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND, size = 50)
public class PacketPlayClientWindowClick extends PacketPlayClient
{
    public static final int SLOT_NOT_NEEDED = - 999;
    public static final int INVALID_SLOT    = - 1;
    private int       id; // 1 byte, inventory id
    private short     clickedSlot; // 2 bytes
    private short     actionNumber; // 2 bytes
    private ClickType clickType; // 2 bytes
    private ItemStack clicked; // 1 byte if null, 5 for simple item. should be ignored?

    public PacketPlayClientWindowClick()
    {
    }

    public PacketPlayClientWindowClick(final int id, final short actionNumber, final ClickType clickType)
    {
        this.id = id;
        this.actionNumber = actionNumber;
        this.clickType = clickType;
    }

    public PacketPlayClientWindowClick(final int id, final short clickedSlot, final short actionNumber, final ClickType clickType)
    {
        this.id = id;
        this.clickedSlot = clickedSlot;
        this.actionNumber = actionNumber;
        this.clickType = clickType;
    }

    public PacketPlayClientWindowClick(final int id, final short clickedSlot, final short actionNumber, final ClickType clickType, final ItemStack clicked)
    {
        this.id = id;
        this.clickedSlot = clickedSlot;
        this.actionNumber = actionNumber;
        this.clickType = clickType;
        this.clicked = clicked;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.id = data.readByte();
        this.clickedSlot = data.readShort();
        final byte button = data.readByte();
        this.actionNumber = data.readShort();
        final byte mode = data.readByte();
        this.clickType = ClickType.get(mode, button, this.clickedSlot != SLOT_NOT_NEEDED);
        data.skipBytes(data.readableBytes());
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeByte(this.id);
        data.writeShort(this.clickedSlot);
        data.writeByte(this.clickType.getButton());
        data.writeShort(this.actionNumber);
        data.writeByte(this.clickType.getMode());
        data.writeItemStack(this.clicked);
    }

    public int getId()
    {
        return this.id;
    }

    public void setId(final int id)
    {
        this.id = id;
    }

    public short getClickedSlot()
    {
        return this.clickedSlot;
    }

    public void setClickedSlot(final short clickedSlot)
    {
        this.clickedSlot = clickedSlot;
    }

    public short getActionNumber()
    {
        return this.actionNumber;
    }

    public void setActionNumber(final short actionNumber)
    {
        this.actionNumber = actionNumber;
    }

    public ClickType getClickType()
    {
        return this.clickType;
    }

    public void setClickType(final ClickType clickType)
    {
        this.clickType = clickType;
    }

    public ItemStack getClicked()
    {
        return this.clicked;
    }

    public void setClicked(final ItemStack clicked)
    {
        this.clicked = clicked;
    }

    @Override
    public void handle(final PacketPlayClientListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("id", this.id).append("clickedSlot", this.clickedSlot).append("actionNumber", this.actionNumber).append("clickType", this.clickType).append("clicked", this.clicked).toString();
    }
}
