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
import org.diorite.inventory.item.ItemStack;

@PacketClass(id = 0x10, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND, size = 128)
public class PacketPlayClientSetCreativeSlot extends PacketPlayClient
{
    private int       slot; // 2 bytes
    private ItemStack item; // ~5 bytes or more

    public PacketPlayClientSetCreativeSlot()
    {
    }

    public PacketPlayClientSetCreativeSlot(final int slot, final ItemStack item)
    {
        this.slot = slot;
        this.item = item;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.slot = data.readShort();
        this.item = data.readItemStack();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeShort(this.slot);
        data.writeItemStack(this.item);
    }

    @Override
    public void handle(final PacketPlayClientListener listener)
    {
        listener.handle(this);
    }

    public int getSlot()
    {
        return this.slot;
    }

    public void setSlot(final int slot)
    {
        this.slot = slot;
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("slot", this.slot).append("item", this.item).toString();
    }
}
