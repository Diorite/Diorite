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
import org.diorite.chat.component.BaseComponent;
import org.diorite.inventory.InventoryType;

@PacketClass(id = 0x2D, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 200)
public class PacketPlayServerOpenWindow extends PacketPlayServer
{
    private int           windowId; // 1 byte
    private InventoryType inventoryType; // ~65 bytes
    private BaseComponent name; // ~128 bytes
    private int           size; // 1 byte
    private int           entityId; // ~5 bytes, Only for EntityHorse

    public PacketPlayServerOpenWindow()
    {
    }

    public PacketPlayServerOpenWindow(final int windowId, final InventoryType inventoryType, final BaseComponent name, final int size)
    {
        this(windowId, inventoryType, name, size, 0);
    }

    public PacketPlayServerOpenWindow(final int windowId, final InventoryType inventoryType, final BaseComponent name, final int size, final int entityId)
    {
        this.windowId = windowId;
        this.inventoryType = inventoryType;
        this.name = name;
        this.size = size;
        this.entityId = entityId;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.windowId = data.readUnsignedByte();
        //noinspection MagicNumber
        this.inventoryType = InventoryType.getByMinecraftId(data.readText(64));
        this.name = data.readBaseComponent();
        this.size = data.readUnsignedByte();
        if (this.inventoryType.getMinecraftId().equals("EntityHorse"))
        {
            this.entityId = data.readInt();
        }
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeByte(this.windowId);
        data.writeText(this.inventoryType.getMinecraftId());
        data.writeBaseComponent(this.name);
        data.writeByte(this.size);
        if (this.inventoryType.getMinecraftId().equals("EntityHorse"))
        {
            data.writeInt(this.entityId);
        }
    }

    @Override
    public void handle(final PacketPlayServerListener listener)
    {
        listener.handle(this);
    }

    public InventoryType getInventoryType()
    {
        return this.inventoryType;
    }

    public void setInventoryType(final InventoryType inventoryType)
    {
        this.inventoryType = inventoryType;
    }

    public int getWindowId()
    {
        return this.windowId;
    }

    public void setWindowId(final int windowId)
    {
        this.windowId = windowId;
    }

    public BaseComponent getName()
    {
        return this.name;
    }

    public void setName(final BaseComponent name)
    {
        this.name = name;
    }

    public int getSize()
    {
        return this.size;
    }

    public void setSize(final int size)
    {
        this.size = size;
    }

    public int getEntityId()
    {
        return this.entityId;
    }

    public void setEntityId(final int entityId)
    {
        this.entityId = entityId;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("windowId", this.windowId).append("inventoryType", this.inventoryType).append("name", this.name).append("size", this.size).append("entityId", this.entityId).toString();
    }
}
