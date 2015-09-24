package org.diorite.impl.connection.packets.play.server;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayServerListener;
import org.diorite.impl.inventory.item.ItemStackImpl;
import org.diorite.impl.inventory.item.ItemStackImplArray;

@PacketClass(id = 0x30, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 512)
public class PacketPlayServerWindowItems extends PacketPlayServer
{
    private int                windowId; // 1 byte
    private ItemStackImplArray items;

    public PacketPlayServerWindowItems()
    {
    }

    public PacketPlayServerWindowItems(final int windowId, final ItemStackImplArray items)
    {
        this.windowId = windowId;
        this.items = items;
    }

    public PacketPlayServerWindowItems(final int windowId, final ItemStackImpl... items)
    {
        this.items = ItemStackImplArray.create(items);
        if (this.items.length() == 0)
        {
            throw new IllegalArgumentException();
        }
        this.windowId = windowId;
    }

    public int getWindowId()
    {
        return this.windowId;
    }

    public void setWindowId(final int windowId)
    {
        this.windowId = windowId;
    }

    public ItemStackImplArray getItems()
    {
        return this.items;
    }

    public void setItems(final ItemStackImplArray items)
    {
        this.items = items;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.windowId = data.readUnsignedByte();
//        final short count = data.readShort();
        //for (int i = 0; count < i; i++)
        //{
        //    this.items.add(data.readItemStack());
        //} // TODO
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeByte(this.windowId);
        data.writeShort(this.items.length());
        this.items.forEach(data::writeItemStack);
    }

    @Override
    public void handle(final PacketPlayServerListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("windowId", this.windowId).append("items", this.items).toString();
    }
}
