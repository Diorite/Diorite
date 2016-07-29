package org.diorite.impl.connection.packets.play.clientbound;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayClientboundListener;

@PacketClass(id = 0x1B, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 5)
public class PacketPlayClientboundEntityStatus extends PacketPlayClientbound
{
    private int entityId; // 4 bytes
    //TODO make enum?
    private int entityStatus; // 1 byte

    public PacketPlayClientboundEntityStatus()
    {
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.entityId = data.readInt();
        this.entityStatus = data.readByte();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeInt(this.entityId);
        data.writeByte(this.entityStatus);
    }

    @Override
    public void handle(final PacketPlayClientboundListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("entityId", this.entityId).append("entityStatus", this.entityStatus).toString();
    }
}
