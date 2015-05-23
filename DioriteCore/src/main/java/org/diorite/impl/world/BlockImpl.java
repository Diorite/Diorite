package org.diorite.impl.world;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.BlockMaterialData;
import org.diorite.world.Block;
import org.diorite.world.World;
import org.diorite.world.chunk.Chunk;

public class BlockImpl implements Block
{
    private final byte              x; // x pos on chunk, not map
    private final int               y;
    private final byte              z; // z pos on chunk, not map
    private final Chunk             chunk;
    private       BlockMaterialData type;

    public BlockImpl(final int x, final int y, final int z, final Chunk chunk, final BlockMaterialData type)
    {
        this.x = (byte) x;
        this.y = y;
        this.z = (byte) z;
        this.chunk = chunk;
        this.type = type;
    }

    public BlockImpl(final int x, final int y, final int z, final Chunk chunk)
    {
        this.x = (byte) x;
        this.y = y;
        this.z = (byte) z;
        this.chunk = chunk;
        this.type = chunk.getBlockType(x, y, z);
    }

    @Override
    public int getX()
    {
        return this.x + (this.chunk.getX() << 4);
    }

    @Override
    public int getY()
    {
        return this.y;
    }

    @Override
    public int getZ()
    {
        return this.z + (this.chunk.getZ() << 4);
    }

    @Override
    public World getWorld()
    {
        return this.chunk.getWorld();
    }

    @Override
    public BlockMaterialData getType()
    {
        return this.type;
    }

    @Override
    public void setType(final BlockMaterialData type)
    {
        this.type = type;
        this.chunk.setBlock(this.x, this.y, this.z, this.type);
    }

    @Override
    public void update()
    {
        this.type = this.chunk.getBlockType(this.x, this.y, this.z);
    }

    @Override
    public Block getRelative(final int x, final int y, final int z)
    {
        return this.chunk.getWorld().getBlock(this.getX() + x, this.y + y, this.getZ() + z);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("x", this.x).append("y", this.y).append("z", this.z).append("chunk", this.chunk).append("type", this.type).toString();
    }
}
