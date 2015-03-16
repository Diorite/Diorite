package org.diorite.impl.connection.packets.play.in;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayInListener;

@PacketClass(id = 0x09, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND)
public class PacketPlayInHeldItemSlot implements PacketPlayIn
{
    private int slot;

    public PacketPlayInHeldItemSlot()
    {
    }

    public PacketPlayInHeldItemSlot(final int slot)
    {
        this.slot = slot;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.slot = data.readShort();
    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeShort(this.slot);
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

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("slot", this.slot).toString();
    }
}
