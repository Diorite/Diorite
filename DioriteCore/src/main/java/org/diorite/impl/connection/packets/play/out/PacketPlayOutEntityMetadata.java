package org.diorite.impl.connection.packets.play.out;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.EntityMetadataCodec;
import org.diorite.impl.connection.packets.EntityMetadataObject;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayOutListener;

@PacketClass(id = 0x1C, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketPlayOutEntityMetadata implements PacketPlayOut
{
    private int entityId;
    private List<EntityMetadataObject> metadata;

    public PacketPlayOutEntityMetadata()
    {
    }

    public PacketPlayOutEntityMetadata(final int entityId, List<EntityMetadataObject> metadata)
    {
        this.entityId = entityId;
        this.metadata = metadata;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.entityId = data.readVarInt();
        this.metadata = EntityMetadataCodec.decode(data);
    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeVarInt(entityId);
        for (EntityMetadataObject mo : metadata)
        {
            EntityMetadataCodec.encode(data, mo);
        }
        data.writeByte(127);
    }

    @Override
    public void handle(final PacketPlayOutListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("entityId", this.entityId).append("metadata", this.metadata).toString();
    }
}
