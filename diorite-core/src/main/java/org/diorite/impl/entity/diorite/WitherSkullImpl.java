package org.diorite.impl.entity.diorite;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.entity.IWitherSkull;
import org.diorite.impl.entity.meta.EntityMetadata;
import org.diorite.impl.entity.meta.entry.EntityMetadataBooleanEntry;
import org.diorite.ImmutableLocation;
import org.diorite.entity.EntityType;

class WitherSkullImpl extends ProjectileImpl implements IWitherSkull
{
    WitherSkullImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
        this.setBoundingBox(BASE_SIZE.create(this));
    }

    @Override
    protected void createMetadata()
    {
        this.metadata = new EntityMetadata(IWitherSkull.META_KEYS);
    }

    @Override
    public void initMetadata()
    {
        super.initMetadata();
        this.getMetadata().add(new EntityMetadataBooleanEntry(META_KEY_INVULNERABLE, false));
    }

    @Override
    public EntityType getType()
    {
        return EntityType.WITHER_SKULL;
    }
}

