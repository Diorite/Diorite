package org.diorite.impl.block;

import org.diorite.impl.world.WorldImpl;
import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.block.Block;
import org.diorite.block.BlockLocation;
import org.diorite.block.BlockState;
import org.diorite.cfg.magic.MagicNumber;
import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.world.World;

public class BlockStateImpl implements BlockState
{
    private final int x;
    private final int y;
    private final int z;
    private final WorldImpl         world;
    private final ChunkImpl         chunk;
    private       BlockMaterialData type;

    public BlockStateImpl(final Block block)
    {
        this.x = block.getX();
        this.y = block.getY();
        this.z = block.getZ();
        this.world = (WorldImpl) block.getWorld();
        this.chunk = (ChunkImpl) block.getChunk();
        this.type = block.getType();
    }

    public static BlockStateImpl getBlockState(World world, BlockLocation location)
    {
        return new BlockStateImpl(world.getBlock(location));
    }

    @Override
    public int getX()
    {
        return x;
    }

    @Override
    public int getY()
    {
        return y;
    }

    @Override
    public int getZ()
    {
        return z;
    }

    @Override
    public Block getBlock()
    {
        return world.getBlock(x, y, z);
    }

    @Override
    public BlockMaterialData getType()
    {
        return type;
    }

    @Override
    public void setType(final BlockMaterialData type)
    {
        //TODO
    }

    @Override
    public World getWorld()
    {
        return world;
    }

    @Override
    public BlockLocation getLocation()
    {
        return new BlockLocation(x, y, z);
    }

    @Override
    public boolean update()
    {
        return update(false);
    }

    @Override
    public boolean update(final boolean force)
    {
        return update(force, true);
    }

    @Override
    public boolean update(final boolean force, final boolean applyPhysics)
    {
        requirePlaced();

        Block block = getBlock();

        if(block.getType() != type && !force)
        {
            return false;
        }

        //TODO

        return true;
    }

    @Override
    public boolean isPlaced()
    {
        return world != null;
    }

    protected void requirePlaced()
    {
        if(!isPlaced())
        {
            throw new IllegalStateException("Block must be placed to call this method");
        }
    }
}
