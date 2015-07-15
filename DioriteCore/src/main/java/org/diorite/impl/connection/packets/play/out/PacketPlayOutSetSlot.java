package org.diorite.impl.connection.packets.play.out;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayOutListener;
import org.diorite.inventory.item.ItemStack;

@PacketClass(id = 0x2F, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketPlayOutSetSlot extends PacketPlayOut
{
    private int       windowId;
    private short     slotId;
    private ItemStack item;

    public PacketPlayOutSetSlot()
    {
    }

    public PacketPlayOutSetSlot(final int windowId, final short slotId, final ItemStack item)
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
    public void handle(final PacketPlayOutListener listener)
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
