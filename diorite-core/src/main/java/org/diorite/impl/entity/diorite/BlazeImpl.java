package org.diorite.impl.entity.diorite;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.entity.IBlaze;
import org.diorite.impl.entity.meta.EntityMetadata;
import org.diorite.impl.entity.meta.entry.EntityMetadataByteEntry;
import org.diorite.ImmutableLocation;
import org.diorite.entity.EntityType;

class BlazeImpl extends MonsterEntityImpl implements IBlaze
{
    BlazeImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
        this.setBoundingBox(BASE_SIZE.create(this));
    }

    @Override
    protected void createMetadata()
    {
        this.metadata = new EntityMetadata(IBlaze.META_KEYS);
    }

    @Override
    public void initMetadata()
    {
        super.initMetadata();
        this.metadata.add(new EntityMetadataByteEntry(META_KEY_BLAZE_IS_ON_FIRE, 1));
    }

    @Override
    public boolean canShotFireballs()
    {
        return this.metadata.getByte(META_KEY_BLAZE_IS_ON_FIRE) == 1;
    }

    @Override
    public void setShotFireballs(final boolean fire)
    {
        this.metadata.setByte(META_KEY_BLAZE_IS_ON_FIRE, fire ? 1 : 0);
    }

    @Override
    public EntityType getType()
    {
        return EntityType.BLAZE;
    }
}

