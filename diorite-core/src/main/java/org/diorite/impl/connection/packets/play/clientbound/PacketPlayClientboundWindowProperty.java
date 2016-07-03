package org.diorite.impl.connection.packets.play.clientbound;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayClientboundListener;

@PacketClass(id = 0x15, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 5)
public class PacketPlayClientboundWindowProperty extends PacketPlayClientbound
{
    private int windowId; // 1 byte
    private int property; // 2 bytes
    private int value; // 2 bytes

    public PacketPlayClientboundWindowProperty()
    {
    }

    public PacketPlayClientboundWindowProperty(final int windowId, final int property, final int value)
    {
        this.windowId = windowId;
        this.property = property;
        this.value = value;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.windowId = data.readUnsignedByte();
        this.property = data.readShort();
        this.value = data.readShort();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeByte(this.windowId);
        data.writeShort(this.property);
        data.writeShort(this.value);
    }

    @Override
    public void handle(final PacketPlayClientboundListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("windowId", this.windowId).append("property", this.property).append("value", this.value).toString();
    }
}
