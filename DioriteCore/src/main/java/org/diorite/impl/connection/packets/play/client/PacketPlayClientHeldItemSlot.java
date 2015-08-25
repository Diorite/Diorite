package org.diorite.impl.connection.packets.play.client;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayClientListener;

@PacketClass(id = 0x09, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND, size = 2)
public class PacketPlayClientHeldItemSlot extends PacketPlayClient
{
    private int slot; // 2 bytes

    public PacketPlayClientHeldItemSlot()
    {
    }

    public PacketPlayClientHeldItemSlot(final int slot)
    {
        this.slot = slot;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.slot = data.readShort();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeShort(this.slot);
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

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("slot", this.slot).toString();
    }
}
