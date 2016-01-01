package org.diorite.impl.entity.diorite;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.packets.play.server.PacketPlayServer;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerSpawnEntity;
import org.diorite.impl.entity.IAreaEffectCloud;
import org.diorite.impl.entity.meta.EntityMetadata;
import org.diorite.impl.entity.meta.entry.EntityMetadataBooleanEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataFloatEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataIntEntry;
import org.diorite.ImmutableLocation;
import org.diorite.Particle;
import org.diorite.entity.EntityType;
import org.diorite.utils.math.geometry.EntityBoundingBox;

class AreaEffectCloudImpl extends EntityImpl implements IAreaEffectCloud
{
    AreaEffectCloudImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
        final EntityBoundingBox box = new EntityBoundingBox(5, 5);
        box.setCenter(this);
        this.setBoundingBox(box);// TODO
    }

    @Override
    public PacketPlayServer getSpawnPacket()
    {
        return new PacketPlayServerSpawnEntity(this);
    }

    @Override
    public void initMetadata()
    {
        super.initMetadata();
        final EntityMetadata met = this.getMetadata();
        met.add(new EntityMetadataFloatEntry(META_KEY_RADIUS, 0));
        met.add(new EntityMetadataIntEntry(META_KEY_COLOR, 0));
        met.add(new EntityMetadataBooleanEntry(META_KEY_UNKNOWN, false));
        met.add(new EntityMetadataIntEntry(META_KEY_PARTICLE_ID, Particle.SPELL_MOB.getParticleId()));
    }

    @Override
    public EntityType getType()
    {
        return EntityType.AREA_EFFECT_CLOUD;
    }

    @Override
    public int getEntityObjectData()
    {
        return 0;
    }
}

