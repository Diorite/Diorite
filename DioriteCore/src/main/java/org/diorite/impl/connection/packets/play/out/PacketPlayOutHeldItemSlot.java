package org.diorite.impl.connection.packets.play.out;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayOutListener;

@PacketClass(id = 0x09, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketPlayOutHeldItemSlot extends PacketPlayOut
{
    private int slot;

    public PacketPlayOutHeldItemSlot()
    {
    }

    public PacketPlayOutHeldItemSlot(final int slot)
    {
        this.slot = slot;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.slot = data.readByte();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeByte(this.slot);
    }

    @Override
    public void handle(final PacketPlayOutListener listener)
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
