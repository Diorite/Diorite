package org.diorite.impl.entity;

import java.util.UUID;

import org.diorite.impl.ServerImpl;
import org.diorite.impl.connection.packets.play.out.PacketPlayOut;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutEntityMetadata;
import org.diorite.impl.entity.meta.entry.EntityMetadataByteEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataFloatEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataIntEntry;
import org.diorite.ImmutableLocation;
import org.diorite.entity.LivingEntity;

public abstract class LivingEntityImpl extends AttributableEntityImpl implements LivingEntity
{
   protected static final byte META_KEY_HEALTH               = 6;
   protected static final byte META_KEY_POTION_EFFECT_COLOR  = 7;
   protected static final byte META_KEY_POTION_IS_AMBIENT    = 8;
   protected static final byte META_KEY_ARROWS_IN_BODY       = 9;
   protected static final byte META_KEY_NO_AI                = 15;

    public LivingEntityImpl(final UUID uuid, final ServerImpl server, final int id, final ImmutableLocation location)
    {
        super(uuid, server, id, location);
    }

    @Override
    public void initMetadata()
    {
        super.initMetadata();
        this.metadata.add(new EntityMetadataFloatEntry(META_KEY_HEALTH, 1F));
        this.metadata.add(new EntityMetadataIntEntry(META_KEY_POTION_EFFECT_COLOR, 0));
        this.metadata.add(new EntityMetadataByteEntry(META_KEY_POTION_IS_AMBIENT, 0));
        this.metadata.add(new EntityMetadataByteEntry(META_KEY_ARROWS_IN_BODY, 0));

        // test TODO: remove
//        this.metadata.add(new EntityMetadataByteEntry(META_KEY_ARROWS_IN_BODY, 127));
    }

    @Override
    public PacketPlayOut[] getSpawnPackets()
    {
        // TODO: add other stuff, like potions
        return new PacketPlayOut[]{this.getSpawnPacket(), new PacketPlayOutEntityMetadata(this, true)};
    }
}
