package org.diorite.impl.world.chunk;

import org.diorite.impl.world.BlockImpl;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.nbt.NbtTagCompound;
import org.diorite.world.Block;
import org.diorite.world.chunk.Chunk;
import org.diorite.world.chunk.ChunkPos;

/**
 * Used as null chunk, ignores all actions.
 */
public class DumbChunk extends ChunkImpl
{
    public DumbChunk(final ChunkPos pos)
    {
        super(pos, new byte[CHUNK_SIZE * CHUNK_SIZE], null, null);
    }

    @Override
    public boolean isPopulated()
    {
        return true;
    }

    @Override
    public void setPopulated(final boolean populated)
    {
    }

    @Override
    public void populate()
    {
    }

    @Override
    public void initHeightMap()
    {
    }

    @Override
    public void setBlock(final int x, final int y, final int z, final BlockMaterialData materialData)
    {
    }

    @Override
    public void setBlock(final int x, final int y, final int z, final int id, final int meta)
    {
    }

    @Override
    public BlockMaterialData getBlockType(final int x, final int y, final int z)
    {
        return Material.AIR;
    }

    @Override
    public BlockMaterialData getHighestBlockType(final int x, final int z)
    {
        return Material.AIR;
    }

    @Override
    public Block getBlock(final int x, final int y, final int z)
    {
        return new DumbBlock(x, y, z, this);
    }

    @Override
    public Block getHighestBlock(final int x, final int z)
    {
        return new DumbBlock(x, 0, z, this);
    }

    @Override
    public int getHighestBlockY(final int x, final int z)
    {
        return 0;
    }

    @Override
    public int getUsages()
    {
        return 1;
    }

    @Override
    public void recalculateBlockCounts()
    {
    }

    @Override
    public int addUsage()
    {
        return 1;
    }

    @Override
    public int removeUsage()
    {
        return 1;
    }

    @Override
    public void loadFrom(final NbtTagCompound tag)
    {
    }

    @Override
    public NbtTagCompound writeTo(final NbtTagCompound tag)
    {
        return tag;
    }

    @Override
    public byte[] getBiomes()
    {
        return super.getBiomes();
    }

    @Override
    public int getMask()
    {
        return 0;
    }

    private static class DumbBlock extends BlockImpl
    {
        private DumbBlock(final int x, final int y, final int z, final Chunk chunk)
        {
            super(x, y, z, chunk, null);
        }

        @Override
        public BlockMaterialData getType()
        {
            return Material.AIR;
        }

        @Override
        public void setType(final BlockMaterialData type)
        {

        }

        @Override
        public void update()
        {

        }
    }
}
