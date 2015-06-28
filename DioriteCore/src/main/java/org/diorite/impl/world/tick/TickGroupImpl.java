package org.diorite.impl.world.tick;

import java.lang.ref.WeakReference;
import java.util.Random;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.ServerImpl;
import org.diorite.impl.Tickable;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutBlockChange;
import org.diorite.impl.world.WorldImpl;
import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.world.Block;
import org.diorite.world.TickGroup;
import org.diorite.world.World;
import org.diorite.world.chunk.Chunk;

import gnu.trove.TLongCollection;
import gnu.trove.set.hash.TLongHashSet;

public interface TickGroupImpl extends Tickable, TickGroup
{
    @Override
    default void tickChunk(final Chunk chunk, final int tps)
    {
        this.tickChunk((ChunkImpl) chunk, tps);
    }

    default void tickChunk(final ChunkImpl chunk, final int tps)
    {
        chunk.setLastTickThread(Thread.currentThread());
        chunk.getTileEntities().values().forEach(t -> t.doTick(tps));
        chunk.getEntities().forEach(e -> e.doTick(tps));
        // Test code, more gooold!
        final Random rand = chunk.getWorld().getRandom();
        for (int i = 0; i < Chunk.CHUNK_PART_HEIGHT; i++)
        {
            if (rand.nextBoolean() || rand.nextBoolean())
            {
                continue;
            }
            final Block b = chunk.getBlock(rand.nextInt(Chunk.CHUNK_SIZE), ((i % Chunk.CHUNK_PART_HEIGHT) * Chunk.CHUNK_PART_HEIGHT) + rand.nextInt(Chunk.CHUNK_PART_HEIGHT), rand.nextInt(Chunk.CHUNK_SIZE));

            if (rand.nextBoolean() && rand.nextBoolean())
            {
                if (! b.getType().simpleEquals(Material.STONE))
                {
                    continue;
                }
            }
            else if (rand.nextBoolean())
            {
                if (! b.getType().simpleEquals(Material.GRASS) && ! b.getType().simpleEquals(Material.DIRT))
                {
                    continue;
                }
            }
            else
            {
                continue;
            }
            final BlockMaterialData newMat = rand.nextBoolean() ? Material.GOLD_BLOCK : Material.GOLD_ORE;
            b.setType(newMat);
            final PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(b.getLocation(), newMat);
            ServerImpl.getInstance().getPlayersManager().forEach(p -> p.getWorld().equals(b.getWorld()) && p.isVisibleChunk(b.getChunk().getX(), b.getChunk().getZ()), packet);

        }
        // TODO
    }

    boolean removeWorld(World world);

    boolean isEmpty();

    @SuppressWarnings("MagicNumber")
    class ChunkGroup
    {
        private final WeakReference<WorldImpl> world;
        public static final int             CHUNKS_PER_FILE = 1024;
        private final       TLongCollection chunks          = new TLongHashSet(CHUNKS_PER_FILE / 2, .5f);

        public ChunkGroup(final WorldImpl world)
        {
            this.world = new WeakReference<>(world);
        }

        public TLongCollection getChunks()
        {
            return this.chunks;
        }

        public WorldImpl getWorld()
        {
            return this.world.get();
        }

        public boolean isLoaded()
        {
            return ! this.chunks.isEmpty() && (this.world.get() != null);
        }

        public void clear()
        {
            this.world.clear();
            this.chunks.clear();
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("world", this.world).append("chunks", this.chunks).toString();
        }
    }
}
