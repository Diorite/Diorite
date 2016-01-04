package org.diorite.impl.entity.diorite;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.packets.play.server.PacketPlayServer;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerSpawnEntity;
import org.diorite.impl.entity.IEnderCrystal;
import org.diorite.impl.entity.meta.EntityMetadata;
import org.diorite.impl.entity.meta.entry.EntityMetadataBlockLocationEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataBooleanEntry;
import org.diorite.BlockLocation;
import org.diorite.ImmutableLocation;
import org.diorite.entity.EntityType;

class EnderCrystalImpl extends EntityImpl implements IEnderCrystal
{
    EnderCrystalImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
        this.setBoundingBox(BASE_SIZE.create(this));
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
        final EntityMetadata meta = this.getMetadata();
        meta.add(new EntityMetadataBlockLocationEntry(META_KEY_TARGET, null, true));
        meta.add(new EntityMetadataBooleanEntry(META_KEY_SHOW_BOTTOM, true));
    }

    @Override
    public EntityType getType()
    {
        return EntityType.ENDER_CRYSTAL;
    }

    @Override
    public int getEntityObjectData()
    {
        return 0;
    }

    @Override
    public boolean isShowBottom()
    {
        return this.getMetadata().getBoolean(META_KEY_SHOW_BOTTOM);
    }

    @Override
    public void setShowBottom(final boolean showBottom)
    {
        this.getMetadata().setBoolean(META_KEY_SHOW_BOTTOM, showBottom);
    }

    @Override
    public BlockLocation getBeamTarget()
    {
        return this.getMetadata().getBlockLocation(META_KEY_TARGET);
    }

    @Override
    public void setBeamTarget(final BlockLocation location)
    {
        this.getMetadata().setBlockLocation(META_KEY_TARGET, location);
    }
}

