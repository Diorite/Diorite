package org.diorite.impl.connection.packets.play.in;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayInListener;
import org.diorite.inventory.item.ItemStack;

@PacketClass(id = 0x10, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND)
public class PacketPlayInSetCreativeSlot implements PacketPlayIn
{
    private int       slot;
    private ItemStack item;

    public PacketPlayInSetCreativeSlot()
    {
    }

    public PacketPlayInSetCreativeSlot(final int slot, final ItemStack item)
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
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeShort(this.slot);
        data.writeItemStack(this.item);
    }

    @Override
    public void handle(final PacketPlayInListener listener)
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
