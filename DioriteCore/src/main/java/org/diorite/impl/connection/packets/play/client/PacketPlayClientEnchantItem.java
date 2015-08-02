package org.diorite.impl.connection.packets.play.client;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayClientListener;

@PacketClass(id = 0x11, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND)
public class PacketPlayClientEnchantItem extends PacketPlayClient
{
    private int windowId;
    private int enchantmentId; // Number of enchantment in window, begin from 0

    public PacketPlayClientEnchantItem()
    {
    }

    public PacketPlayClientEnchantItem(final int windowId, final int enchantmentId)
    {
        this.windowId = windowId;
        this.enchantmentId = enchantmentId;
    }

    public int getWindowId()
    {
        return this.windowId;
    }

    public void setWindowId(final int windowId)
    {
        this.windowId = windowId;
    }

    public int getEnchantmentId()
    {
        return this.enchantmentId;
    }

    public void setEnchantmentId(final int enchantmentId)
    {
        this.enchantmentId = enchantmentId;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.windowId = data.readByte();
        this.enchantmentId = data.readByte();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeByte(this.windowId);
        data.writeByte(this.enchantmentId);
    }

    @Override
    public void handle(final PacketPlayClientListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("windowId", this.windowId).append("enchantmentId", this.enchantmentId).toString();
    }
}
