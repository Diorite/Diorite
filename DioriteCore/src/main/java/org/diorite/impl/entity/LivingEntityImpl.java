package org.diorite.impl.entity;

import java.util.UUID;

import org.diorite.impl.ServerImpl;
import org.diorite.impl.connection.packets.play.server.PacketPlayServer;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerEntityMetadata;
import org.diorite.impl.entity.meta.entry.EntityMetadataByteEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataFloatEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataIntEntry;
import org.diorite.ImmutableLocation;
import org.diorite.entity.LivingEntity;

public abstract class LivingEntityImpl extends AttributableEntityImpl implements LivingEntity
{
    /**
     * float entry, hp of entity
     */
    protected static final byte META_KEY_HEALTH = 6;

    /**
     * byte entry, Potion effect color over entity
     */
    protected static final byte META_KEY_POTION_EFFECT_COLOR = 7;

    /**
     * byte/bool entry, if potion is ambiend (less visible)
     */
    protected static final byte META_KEY_POTION_IS_AMBIENT = 8;

    /**
     * byte entry, number of arrows in player.
     */
    protected static final byte META_KEY_ARROWS_IN_BODY = 9;

    /**
     * byte/bool entry, if entity have AI
     */
    protected static final byte META_KEY_NO_AI = 15;

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
    public PacketPlayServer[] getSpawnPackets()
    {
        // TODO: add other stuff, like potions
        return new PacketPlayServer[]{this.getSpawnPacket(), new PacketPlayServerEntityMetadata(this, true)};
    }
}
