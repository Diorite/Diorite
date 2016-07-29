package org.diorite.impl.connection.packets.play.clientbound;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayClientboundListener;

@PacketClass(id = 0x17, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 10)
public class PacketPlayClientboundSetCooldown extends PacketPlayClientbound
{
    private int itemId; // ~5 bytes
    private int cooldownTicks; // ~5 bytes

    public PacketPlayClientboundSetCooldown()
    {
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.itemId = data.readVarInt();
        this.cooldownTicks = data.readVarInt();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeVarInt(this.itemId);
        data.writeVarInt(this.cooldownTicks);
    }

    @Override
    public void handle(final PacketPlayClientboundListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("itemId", this.itemId).append("cooldownTicks", this.cooldownTicks).toString();
    }
}
