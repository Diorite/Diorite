package org.diorite.impl.connection.packets.play.clientbound;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayClientboundListener;
import org.diorite.BlockLocation;

@PacketClass(id = 0x2F, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 13)
public class PacketPlayClientboundUseBed extends PacketPlayClientbound
{
    private int           entityId; // ~5 bytes
    private BlockLocation location; //  8 bytes

    public PacketPlayClientboundUseBed()
    {
    }

    public PacketPlayClientboundUseBed(int entityId, BlockLocation location)
    {
        this.entityId = entityId;
        this.location = location;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.entityId = data.readVarInt();
        this.location = data.readBlockLocationFromLong();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeVarInt(this.entityId);
        data.writeBlockLocationAsLong(this.location);
    }

    @Override
    public void handle(final PacketPlayClientboundListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("entityId", this.entityId).append("location", this.location).toString();
    }
}
