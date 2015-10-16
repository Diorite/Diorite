/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.impl.world;

import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.lazy.LazyValue;
import org.diorite.utils.math.geometry.BoundingBox;
import org.diorite.utils.math.geometry.Vector;
import org.diorite.world.Biome;
import org.diorite.world.Block;
import org.diorite.world.TileEntity;
import org.diorite.world.World;

public class BlockImpl implements Block
{
    private final byte                   x; // x pos on chunk, not map
    private final int                    y;
    private final byte                   z; // z pos on chunk, not map
    private final ChunkImpl              chunk;
    private       BlockMaterialData      type;
    private final LazyValue<BoundingBox> lazyBox;

    public BlockImpl(final int x, final int y, final int z, final ChunkImpl chunk, final BlockMaterialData type)
    {
        this.x = (byte) x;
        this.y = y;
        this.z = (byte) z;
        this.chunk = chunk;
        this.type = type;
        this.lazyBox = new LazyValue<>(() -> {
            int x1 = this.x + (this.chunk.getX() << 4);
            int x2 = (x1 >= 0) ? (x1 + 1) : (x1 - 1);

            int y1 = this.z + (this.chunk.getZ() << 4);
            int y2 = y1 + 1;

            int z1 = this.z + (this.chunk.getZ() << 4);
            int z2 = (z1 >= 0) ? (z1 + 1) : (z1 - 1);

            return BoundingBox.fromCorners(new Vector(x1, y1, z1), new Vector(x2, y2, z2));
        });
    }

    public BlockImpl(final int x, final int y, final int z, final ChunkImpl chunk)
    {
        this.x = (byte) x;
        this.y = y;
        this.z = (byte) z;
        this.chunk = chunk;
        if (! chunk.isLoaded())
        {
            chunk.load();
        }
        this.type = chunk.getBlockType(x, y, z);
        this.lazyBox = new LazyValue<>(() -> {
            int x1 = this.x + (this.chunk.getX() << 4);
            int x2 = (x1 >= 0) ? (x1 + 1) : (x1 - 1);

            int z1 = this.z + (this.chunk.getZ() << 4);
            int z2 = (z1 >= 0) ? (z1 + 1) : (z1 - 1);

            return BoundingBox.fromCorners(new Vector(x1, this.y, z1), new Vector(x2, this.y + 1, z2));
        });
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
    public Optional<TileEntity> getTileEntity()
    {
        return Optional.ofNullable(this.chunk.getTileEntities().get(this.getLocation().asLong()));
    }

    @Override
    public void setType(final BlockMaterialData type)
    {
        this.type = type;
        this.chunk.setBlock(this.x, this.y, this.z, this.type);

//        final PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(new BlockLocation(this.getX(), this.y, this.getZ(), this.getWorld()), type);
//        ServerImpl.getInstance().getPlayersManager().forEach(p -> p.getWorld().equals(this.getWorld()) && p.isVisibleChunk(this.x, this.z), packet);
    }

    @Override
    public Biome getBiome()
    {
        return this.chunk.getBiome(this.x, this.y, this.z);
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
    public BoundingBox getBoundingBox()
    {
        return this.lazyBox.get();
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof BlockImpl))
        {
            return false;
        }

        final BlockImpl block = (BlockImpl) o;

        return (this.x == block.x) && (this.y == block.y) && (this.z == block.z) && ! ((this.chunk != null) ? ! this.chunk.equals(block.chunk) : (block.chunk != null)) && ! ((this.type != null) ? ! this.type.equals(block.type) : (block.type != null));
    }

    @Override
    public int hashCode()
    {
        int result = (int) this.x;
        result = (31 * result) + this.y;
        result = (31 * result) + (int) this.z;
        result = (31 * result) + ((this.chunk != null) ? this.chunk.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("x", this.getX()).append("y", this.y).append("z", this.getZ()).append("type", this.type.name() + ":" + this.type.getTypeName()).toString();
    }
}
