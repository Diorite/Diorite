package org.diorite.impl.connection.packets.play.server;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayServerListener;
import org.diorite.entity.Entity;

@PacketClass(id = 0x14, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketPlayServerEntity extends PacketPlayServer
{
    private int entityId;

    public PacketPlayServerEntity()
    {
    }

    public PacketPlayServerEntity(final int entityId)
    {
        this.entityId = entityId;
    }

    public PacketPlayServerEntity(final Entity entity)
    {
        this.entityId = entity.getId();
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.entityId = data.readVarInt();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeVarInt(this.entityId);
    }

    @Override
    public void handle(final PacketPlayServerListener listener)
    {
        listener.handle(this);
    }

    public int getEntityId()
    {
        return this.entityId;
    }

    public void setEntityId(final int entityId)
    {
        this.entityId = entityId;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("entityId", this.entityId).toString();
    }
}
