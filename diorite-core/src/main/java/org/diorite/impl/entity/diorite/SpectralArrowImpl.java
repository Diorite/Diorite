package org.diorite.impl.entity.diorite;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.entity.ISpectralArrow;
import org.diorite.ImmutableLocation;
import org.diorite.entity.EntityType;

class SpectralArrowImpl extends AbstractArrowImpl implements ISpectralArrow
{
    SpectralArrowImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
        this.setBoundingBox(BASE_SIZE.create(this));
    }

    @Override
    public void initMetadata()
    {
        super.initMetadata();
    }

    @Override
    public EntityType getType()
    {
        return EntityType.SPECTRAL_ARROW;
    }

    @Override
    public int getEntityObjectData()
    {
        return 0;
    }
}

