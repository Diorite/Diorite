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

package org.diorite.world.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.ImmutableLocation;
import org.diorite.world.World;
import org.diorite.world.chunk.Chunk;
import org.diorite.world.chunk.ChunkPos;

public abstract class WorldGenerator
{
    /**
     * Queue of all populators that will be used by generator in valid order
     */
    protected final Queue<ChunkPopulator> populators = new ConcurrentLinkedQueue<>();
    /**
     * {@link World} to generate
     */
    protected final World               world;
    /**
     * Name of world generator, must be unique
     */
    protected final String              name;
    /**
     * Options for world generator.
     */
    protected final Map<String, Object> options;

    /**
     * Create new WorldGenerator for selected world
     *
     * @param world   {@link World} to generate
     * @param name    Name of world generator, must be unique
     * @param options Options for world generator.
     */
    public WorldGenerator(final World world, final String name, final Map<String, Object> options)
    {
        this.world = world;
        this.name = name;
        this.options = options;
    }

    /**
     * @return Name of world generator
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * @return raw options for world generator
     */
    public Map<String, Object> getOptions()
    {
        return this.options;
    }

    /**
     * @return {@link World} to generate
     */
    public World getWorld()
    {
        return this.world;
    }

    /**
     * Add new {@link ChunkPopulator} to generator
     *
     * @param chunkPopulator {@link ChunkPopulator} to add.
     */
    public void addPopulator(final ChunkPopulator chunkPopulator)
    {
        this.populators.add(chunkPopulator);
    }

    /**
     * Remove {@link ChunkPopulator} from generator
     *
     * @param chunkPopulator {@link ChunkPopulator} to remove.
     */
    public void removePopulator(final ChunkPopulator chunkPopulator)
    {
        this.populators.remove(chunkPopulator);
    }

    /**
     * Adding/removing elements to returned list will not affect generator, but called methods on {@link ChunkPopulator} can affect world generation.
     *
     * @return copy of populator Queue.
     */
    public List<ChunkPopulator> getPopulators()
    {
        return new ArrayList<>(this.populators);
    }

    /**
     * Main method to generate chunk, implementing class can return other {@link ChunkBuilder} than provided.
     *
     * @param builder default {@link ChunkBuilder} for {@link org.diorite.world.chunk.Chunk}.
     * @param pos     x, z and world of chunk
     *
     * @return in most cases, this same {@link ChunkBuilder} as provided, returned value will be used to create {@link org.diorite.world.chunk.Chunk}
     */
    public abstract ChunkBuilder generate(ChunkBuilder builder, ChunkPos pos);

    /**
     * Method to generate biome grid, if generator want override biomes, it should override this method.
     *
     * @param builder default {@link ChunkBuilder} for {@link org.diorite.world.chunk.Chunk}.
     * @param pos     x, z and world of chunk
     *
     * @return in most cases, this same {@link ChunkBuilder} as provided, returned value will be used to create {@link org.diorite.world.chunk.Chunk}
     */
    public ChunkBuilder generateBiomes(final ChunkBuilder builder, final ChunkPos pos)
    {
        final BiomeGrid biomes = new BiomeGrid();
        final int[] biomeValues = builder.getBiomesInput()[0].generateValues(pos.getX() * Chunk.CHUNK_SIZE, pos.getZ() * Chunk.CHUNK_SIZE, Chunk.CHUNK_SIZE, Chunk.CHUNK_SIZE);
        for (int i = 0; i < biomeValues.length; i++)
        {
            biomes.rawData()[i] = (byte) biomeValues[i];
        }
        builder.setBiomeGrid(biomes);
        return builder;
    }

    /**
     * May be used to suggest default spawn location. <br>
     * Diorite will choose some location if this return null.
     *
     * @return spawn location to use, or null
     */
    public ImmutableLocation getSpawnLocation()
    {
        return null;
    }

    /**
     * Check if spawn can be set at this point.
     *
     * @param x x coordinate of spawn location to check. <br>
     * @param y y coordinate of spawn location to check. <br>
     * @param z z coordinate of spawn location to check.
     *
     * @return true if this is valid spawn location.
     */
    public boolean canSpawn(final double x, final double y, final double z)
    {
        return true;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof WorldGenerator))
        {
            return false;
        }

        final WorldGenerator that = (WorldGenerator) o;

        return this.name.equals(that.name) && ! ((this.options != null) ? ! this.options.equals(that.options) : (that.options != null)) && this.world.equals(that.world);

    }

    @Override
    public int hashCode()
    {
        int result = this.world.hashCode();
        result = (31 * result) + this.name.hashCode();
        result = (31 * result) + ((this.options != null) ? this.options.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("world", this.world.getName()).append("name", this.name).append("options", this.options).toString();
    }

}
