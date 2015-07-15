package org.diorite.impl.connection.packets.play.out;


import java.io.IOException;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayOutListener;
import org.diorite.impl.entity.PlayerImpl;
import org.diorite.impl.entity.meta.entry.EntityMetadataEntry;

@PacketClass(id = 0x0C, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketPlayOutNamedEntitySpawn extends PacketPlayOut
{
    private int                              entityId;
    private UUID                             uuid;
    private int                              x; // WARNING! This is 'fixed-point' number
    private int                              y; // WARNING! This is 'fixed-point' number
    private int                              z; // WARNING! This is 'fixed-point' number
    private byte                             yaw;
    private byte                             pitch;
    private short                            currentItem;
    private Iterable<EntityMetadataEntry<?>> metadata;

    public PacketPlayOutNamedEntitySpawn()
    {
    }

    @SuppressWarnings("MagicNumber")
    public PacketPlayOutNamedEntitySpawn(final PlayerImpl entity)
    {
        this.entityId = entity.getId();
        this.uuid = entity.getUniqueID();
        this.x = (int) (entity.getX() * 32);
        this.y = (int) (entity.getY() * 32);
        this.z = (int) (entity.getZ() * 32);
        this.yaw = (byte) ((entity.getYaw() * 256.0F) / 360.0F);
        this.pitch = (byte) ((entity.getPitch() * 256.0F) / 360.0F);

        this.currentItem = (short) entity.getHeldItemSlot();
        // TODO pasre metadata from entity, or get it if possible
        this.metadata = entity.getMetadata().getEntries();
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.entityId = data.readVarInt();
        this.uuid = data.readUUID();
        this.x = data.readInt();
        this.y = data.readInt();
        this.z = data.readInt();
        this.yaw = data.readByte();
        this.pitch = data.readByte();
        this.currentItem = data.readShort();
        this.metadata = data.readEntityMetadata();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeVarInt(this.entityId);
        data.writeUUID(this.uuid);
        data.writeInt(this.x);
        data.writeInt(this.y);
        data.writeInt(this.z);
        data.writeByte(this.yaw);
        data.writeByte(this.pitch);
        data.writeShort(this.currentItem);
        data.writeEntityMetadata(this.metadata);
    }

    @Override
    public void handle(final PacketPlayOutListener listener)
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

    public UUID getUuid()
    {
        return this.uuid;
    }

    public void setUuid(final UUID uuid)
    {
        this.uuid = uuid;
    }

    public int getX()
    {
        return this.x;
    }

    public void setX(final int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return this.y;
    }

    public void setY(final int y)
    {
        this.y = y;
    }

    public int getZ()
    {
        return this.z;
    }

    public void setZ(final int z)
    {
        this.z = z;
    }

    public byte getYaw()
    {
        return this.yaw;
    }

    public void setYaw(final byte yaw)
    {
        this.yaw = yaw;
    }

    public byte getPitch()
    {
        return this.pitch;
    }

    public void setPitch(final byte pitch)
    {
        this.pitch = pitch;
    }

    public short getCurrentItem()
    {
        return this.currentItem;
    }

    public void setCurrentItem(final short currentItem)
    {
        this.currentItem = currentItem;
    }

    public Iterable<EntityMetadataEntry<?>> getMetadata()
    {
        return this.metadata;
    }

    public void setMetadata(final Iterable<EntityMetadataEntry<?>> metadata)
    {
        this.metadata = metadata;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("entityId", this.entityId).append("uuid", this.uuid).append("x", this.x).append("y", this.y).append("z", this.z).append("pitch", this.pitch).append("yaw", this.yaw).append("currentItem", this.currentItem).append("metadata", this.metadata).toString();
    }
}
