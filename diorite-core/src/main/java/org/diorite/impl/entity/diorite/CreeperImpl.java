package org.diorite.impl.entity.diorite;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.entity.ICreeper;
import org.diorite.impl.entity.meta.entry.EntityMetadataBooleanEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataIntEntry;
import org.diorite.ImmutableLocation;
import org.diorite.entity.EntityType;

class CreeperImpl extends MonsterEntityImpl implements ICreeper
{
    CreeperImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
        this.setBoundingBox(BASE_SIZE.create(this));
    }

    @Override
    public void initMetadata()
    {
        super.initMetadata();
        this.getMetadata().add(new EntityMetadataIntEntry(META_KEY_CREEPER_STATE, 0));
        this.getMetadata().add(new EntityMetadataBooleanEntry(META_KEY_CREEPER_POWERED, false));
        this.getMetadata().add(new EntityMetadataBooleanEntry(META_KEY_CREEPER_IGNITED, false));
    }

    @Override
    public EntityType getType()
    {
        return EntityType.CREEPER;
    }
}
