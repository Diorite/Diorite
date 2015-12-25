package org.diorite.impl.entity;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.packets.play.server.PacketPlayServer;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerSpawnEntityLiving;
import org.diorite.impl.entity.meta.entry.EntityMetadataBooleanEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataIntEntry;
import org.diorite.ImmutableLocation;
import org.diorite.entity.EntityType;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

class CreeperImpl extends MonsterEntityImpl implements ICreeper
{
    protected static final byte                       META_KEY_STATE   = 11;
    protected static final byte                       META_KEY_POWERED = 12;
    protected static final byte                       META_KEY_IGNITED = 13;
    public static final    ImmutableEntityBoundingBox BASE_SIZE        = new ImmutableEntityBoundingBox(0.6F, 1.8F);

    public CreeperImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
        this.aabb = BASE_SIZE.create(this);
    }

    @Override
    public void initMetadata()
    {
        super.initMetadata();
        this.metadata.add(new EntityMetadataIntEntry(META_KEY_STATE, 0));
        this.metadata.add(new EntityMetadataBooleanEntry(META_KEY_POWERED, false));
        this.metadata.add(new EntityMetadataBooleanEntry(META_KEY_IGNITED, false));
    }

    @Override
    public PacketPlayServer getSpawnPacket()
    {
        return new PacketPlayServerSpawnEntityLiving(this);
    }

    @Override
    public EntityType getType()
    {
        return EntityType.CREEPER;
    }
}
