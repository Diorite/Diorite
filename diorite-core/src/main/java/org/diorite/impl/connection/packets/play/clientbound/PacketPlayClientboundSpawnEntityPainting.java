package org.diorite.impl.connection.packets.play.clientbound;

import java.io.IOException;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayClientboundListener;
import org.diorite.Art;
import org.diorite.BlockFace;
import org.diorite.BlockLocation;

@PacketClass(id = 0x04, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 40)
public class PacketPlayClientboundSpawnEntityPainting extends PacketPlayClientbound
{
    private int           entityId; // ~5 bytes
    private UUID          entityUuid; // 16 bytes
    private Art           painting; // ~10 bytes?
    private BlockLocation blockLocation; // 8 bytes?
    private byte          direction; // 1 byte

    public PacketPlayClientboundSpawnEntityPainting()
    {
    }

    public PacketPlayClientboundSpawnEntityPainting(final int entityId, final UUID entityUuid, final Art painting, final BlockLocation blockLocation, final byte direction)
    {
        this.entityId = entityId;
        this.entityUuid = entityUuid;
        this.painting = painting;
        this.blockLocation = blockLocation;
        this.direction = direction;
    }

    public PacketPlayClientboundSpawnEntityPainting(final int entityId, final UUID entityUuid, final Art painting, final BlockLocation blockLocation, final BlockFace direction)
    {
        this(entityId, entityUuid, painting, blockLocation, (byte) direction.getOppositeFace().ordinal());
        if (this.direction > 3)
        {
            throw new IllegalArgumentException();
        }
    }

    public int getEntityId()
    {
        return entityId;
    }

    public void setEntityId(final int entityId)
    {
        this.entityId = entityId;
    }

    public UUID getEntityUuid()
    {
        return entityUuid;
    }

    public void setEntityUuid(final UUID entityUuid)
    {
        this.entityUuid = entityUuid;
    }

    public Art getPainting()
    {
        return painting;
    }

    public void setPainting(final Art painting)
    {
        this.painting = painting;
    }

    public BlockLocation getBlockLocation()
    {
        return blockLocation;
    }

    public void setBlockLocation(final BlockLocation blockLocation)
    {
        this.blockLocation = blockLocation;
    }

    public byte getDirection()
    {
        return direction;
    }

    public void setDirection(final byte direction)
    {
        this.direction = direction;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.entityId = data.readVarInt();
        this.entityUuid = data.readUUID();
        this.painting = Art.getByArtName(data.readText(Art.getMaxArtNameLength()));
        this.blockLocation = data.readBlockLocationFromLong();
        this.direction = data.readByte();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeVarInt(this.entityId);
        data.writeUUID(this.entityUuid);
        data.writeText(this.painting.getArtName());
        data.writeBlockLocationAsLong(this.blockLocation);
        data.writeByte(this.direction);
    }

    @Override
    public void handle(final PacketPlayClientboundListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("entityId", this.entityId).append("entityUuid", this.entityUuid).append("painting", this.painting).append("blockLocation", this.blockLocation).append("direction", this.direction).toString();
    }
}
