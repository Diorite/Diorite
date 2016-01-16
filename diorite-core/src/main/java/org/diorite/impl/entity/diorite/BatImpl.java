package org.diorite.impl.entity.diorite;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.entity.IBat;
import org.diorite.impl.entity.meta.EntityMetadata;
import org.diorite.impl.entity.meta.entry.EntityMetadataByteEntry;
import org.diorite.ImmutableLocation;
import org.diorite.entity.EntityType;

class BatImpl extends AmbientEntityImpl implements IBat
{
    BatImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
        this.setBoundingBox(BASE_SIZE.create(this));
    }

    @Override
    protected void createMetadata()
    {
        this.metadata = new EntityMetadata(IBat.META_KEYS);
    }

    @Override
    public void initMetadata()
    {
        super.initMetadata();
        this.metadata.add(new EntityMetadataByteEntry(META_KEY_BAT_IS_HANGING, 0));
    }

    @Override
    public boolean isHanging()
    {
        return this.metadata.getByte(META_KEY_BAT_IS_HANGING) == 1;
    }

    @Override
    public void setHanging(final boolean hanging)
    {
        this.metadata.setByte(META_KEY_BAT_IS_HANGING, hanging ? 1 : 0);
    }

    @Override
    public EntityType getType()
    {
        return EntityType.BAT;
    }
}

