package org.diorite.world.bag;

import org.diorite.material.BlockMaterialData;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2ShortMap;
import it.unimi.dsi.fastutil.shorts.Short2ShortOpenHashMap;

public class SimpleBlockBag implements BlockBag
{
    private final Long2ObjectMap<ChunkBag> changes; // TODO, ugh

    public SimpleBlockBag(final int size)
    {
        this.changes = new Long2ObjectOpenHashMap<>(size);
    }

    @Override
    public BlockMaterialData getBlock(final int x, final int y, final int z)
    {
        return null;
    }

    @Override
    public void setBlock(final int x, final int y, final int z, final BlockMaterialData material)
    {

    }

    private final class ChunkBag
    {
        private final Short2ShortMap changes;

        ChunkBag(final int size)
        {
            this.changes = new Short2ShortOpenHashMap(size);
        }

        BlockMaterialData getBlock(int x, int y, int z)
        {
//            return changes.get()
            return null;
        }
    }
}
