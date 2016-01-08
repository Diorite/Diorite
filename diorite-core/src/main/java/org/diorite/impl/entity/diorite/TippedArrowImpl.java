package org.diorite.impl.entity.diorite;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.entity.ITippedArrow;
import org.diorite.impl.entity.meta.EntityMetadata;
import org.diorite.impl.entity.meta.entry.EntityMetadataIntEntry;
import org.diorite.ImmutableLocation;
import org.diorite.entity.EntityType;

class TippedArrowImpl extends AbstractArrowImpl implements ITippedArrow
{
    TippedArrowImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
        this.setBoundingBox(BASE_SIZE.create(this));
    }

    @Override
    protected void createMetadata()
    {
        this.metadata = new EntityMetadata(ITippedArrow.META_KEYS);
    }

    @Override
    public void initMetadata()
    {
        super.initMetadata();
        this.getMetadata().add(new EntityMetadataIntEntry(META_KEY_COLOR, 0));
    }

    @Override
    public EntityType getType()
    {
        return EntityType.TIPPED_ARROW;
    }

    @Override
    public int getEntityObjectData()
    {
        return 0;
    }
}

