package org.diorite.impl.entity;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.GameObjectImpl;
import org.diorite.impl.ServerImpl;
import org.diorite.impl.Tickable;
import org.diorite.impl.connection.packets.play.out.PacketPlayOut;
import org.diorite.impl.entity.meta.EntityMetadata;
import org.diorite.impl.entity.meta.entry.EntityMetadataByteEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataStringEntry;
import org.diorite.impl.world.WorldImpl;
import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.ImmutableLocation;
import org.diorite.entity.Entity;
import org.diorite.utils.math.geometry.EntityBoundingBox;
import org.diorite.world.chunk.Chunk;

public abstract class EntityImpl extends GameObjectImpl implements Entity, Tickable
{
    public static final    AtomicInteger ENTITY_ID                     = new AtomicInteger();
    public static final    int           MAX_AIR_LEVEL                 = 300;
    protected static final byte          META_KEY_BASIC_FLAGS          = 0;
    protected static final byte          META_KEY_AIR                  = 1;
    protected static final byte          META_KEY_NAME_TAG             = 2;
    protected static final byte          META_KEY_ALWAYS_SHOW_NAME_TAG = 3;
    protected static final byte          META_KEY_SILENT               = 4;

    /**
     * Contains mask for basic flags used in matadata at index 0
     * http://wiki.vg/Entities#Entity
     */
    public static final class BasicFlags
    {
        public static final byte ON_FIRE   = 0x01;
        public static final byte CROUCHED  = 0x01;
        public static final byte SPRINTING = 0x01;
        public static final byte ACTION    = 0x01;
        public static final byte INVISIBLE = 0x01;

        private BasicFlags()
        {
        }
    }

    protected final    ServerImpl        server;
    protected final    WorldImpl         world;
    protected volatile Thread            lastTickThread;
    protected          EntityBoundingBox aabb;
    private            int               id;
    private            double            x;
    private            double            y;
    private            double            z;
    private            float             yaw;
    private            float             pitch;
    private            float             velX;
    private            float             velY;
    private            float             velZ;
    protected          EntityMetadata    metadata;


    protected boolean aiEnabled = true; // don't do any actions if AI is disabled

    protected EntityImpl(final UUID uuid, final ServerImpl server, final int id, final ImmutableLocation location)
    {
        super(uuid);
        this.server = server;
        this.id = id;
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
        this.world = (WorldImpl) location.getWorld();
        this.metadata = new EntityMetadata();
        this.initMetadata();

        this.updateChunk(null, this.world.getChunkAt(location.getChunkPos()));
    }

    public void initMetadata()
    {
        this.metadata.add(new EntityMetadataByteEntry(META_KEY_BASIC_FLAGS, 0));
        this.metadata.add(new EntityMetadataByteEntry(META_KEY_AIR, MAX_AIR_LEVEL));
        this.metadata.add(new EntityMetadataByteEntry(META_KEY_SILENT, 0));
        this.metadata.add(new EntityMetadataByteEntry(META_KEY_ALWAYS_SHOW_NAME_TAG, 0));
        this.metadata.add(new EntityMetadataStringEntry(META_KEY_NAME_TAG, ""));


        // test TODO: remove
//        this.metadata.add(new EntityMetadataByteEntry(META_KEY_ALWAYS_SHOW_NAME_TAG, 1));
//        this.metadata.add(new EntityMetadataStringEntry(META_KEY_NAME_TAG, ChatColor.translateAlternateColorCodesInString("&a#&3OnlyDiorite")));

    }

    public EntityMetadata getMetadata()
    {
        return this.metadata;
    }

    @Override
    public float getVelocityX()
    {
        return this.velX;
    }

    @Override
    public float getVelocityY()
    {
        return this.velY;
    }

    @Override
    public float getVelocityZ()
    {
        return this.velZ;
    }

    public float getHeadRotation()
    {
        return 0.0F;
    }

    public ChunkImpl getChunk()
    {
        return this.world.getChunkAt(((int) this.x) >> 4, ((int) this.z) >> 4);
    }

    public boolean isAiEnabled()
    {
        return this.aiEnabled;
    }

    public void setAiEnabled(final boolean aiEnabled)
    {
        this.aiEnabled = aiEnabled;
    }

    @Override
    public boolean exist()
    {
        return this.id != - 1;
    }

    public void remove()
    {
        this.id = - 1;
        this.getChunk().removeEntity(this);
        final Chunk c = this.getChunk();
        final int x = c.getX();
        final int z = c.getZ();

        this.server.getPlayersManager().forEach(p -> p.isVisibleChunk(x, z), p -> p.removeEntityFromView(this));
    }

    @Override
    public boolean isValidSynchronizable()
    {
        return this.getChunk().getEntities().contains(this) || this.world.getChunkAt(((int) this.x) >> 4, ((int) this.z) >> 4).getEntities().contains(this);
    }

    @Override
    public Thread getLastTickThread()
    {
        return this.lastTickThread;
    }

    @Override
    public void doTick(final int tps)
    {
        this.lastTickThread = Thread.currentThread();
        // TODO
    }

    public WorldImpl getWorld()
    {
        return this.world;
    }

    @Override
    public int getId()
    {
        return this.id;
    }

    @Override
    public double getX()
    {
        return this.x;
    }

    @Override
    public double getZ()
    {
        return this.z;
    }

    @Override
    public double getY()
    {
        return this.y;
    }

    public float getYaw()
    {
        return this.yaw;
    }

    public float getPitch()
    {
        return this.pitch;
    }

    @Override
    public ImmutableLocation getLocation()
    {
        return new ImmutableLocation(this.x, this.y, this.z, this.yaw, this.pitch, this.world);
    }

    @Override
    public ServerImpl getServer()
    {
        return this.server;
    }


    public void move(final double modX, final double modY, final double modZ, final float modYaw, final float modPitch)
    {
        final ChunkImpl chunk = this.getChunk();

        this.x += modX;
        this.y += modY;
        this.z += modZ;
        this.yaw += modYaw;
        this.pitch += modPitch;

        this.updateChunk(chunk, this.getChunk());
    }

    public void setPositionAndRotation(final double newX, final double newY, final double newZ, final float newYaw, final float newPitch)
    {
        this.setPosition(newX, newY, newZ);
        this.setRotation(newYaw, newPitch);
    }

    public void setPosition(final double newX, final double newY, final double newZ)
    {
        final ChunkImpl chunk = this.getChunk();

        this.x = newX;
        this.y = newY;
        this.z = newZ;

        this.updateChunk(chunk, this.getChunk());
    }

    @SuppressWarnings("ObjectEquality")
    public void updateChunk(final ChunkImpl chunk, final ChunkImpl newChunk)
    {
        if (chunk == null)
        {
            newChunk.addEntity(this);
        }
        else if (chunk != newChunk)
        {
            synchronized (this)
            {
                chunk.removeEntity(this);
                newChunk.addEntity(this);
            }
        }
    }

    public void setRotation(final float newYaw, final float newPitch)
    {
        this.yaw = newYaw;
        this.pitch = newPitch;
    }

    /**
     * @return Packet need to spawn entity
     */
    public abstract PacketPlayOut getSpawnPacket();

    /**
     * Need return array of packet in valid order needed to spawn entity with all data. <br>
     * Like base spawn packet, potion effects, metadata, passanger etc...
     *
     * @return array of packets.
     */
    public abstract PacketPlayOut[] getSpawnPackets();

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("id", this.id).append("world", this.world).append("x", this.x).append("y", this.y).append("z", this.z).toString();
    }
}
