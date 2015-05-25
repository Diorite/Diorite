package org.diorite.world.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.world.World;
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
     * @param world {@link World} to generate
     * @param name Name of world generator, must be unique
     * @param options Options for world generator.
     */
    public WorldGenerator(final World world, final String name, final Map<String, Object> options)
    {
        this.world = world;
        this.name = name;
        this.options = options;
    }

    /**
     *
     * @return Name of world generator
     */
    public String getName()
    {
        return this.name;
    }

    /**
     *
     * @return raw options for world generator
     */
    public Map<String, Object> getOptions()
    {
        return this.options;
    }

    /**
     *
     * @return {@link World} to generate
     */
    public World getWorld()
    {
        return this.world;
    }

    /**
     * Add new {@link ChunkPopulator} to generator
     * @param chunkPopulator {@link ChunkPopulator} to add.
     */
    public void addPopulator(final ChunkPopulator chunkPopulator)
    {
        this.populators.add(chunkPopulator);
    }

    /**
     * Remove {@link ChunkPopulator} from generator
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
     * @param pos x, z and world of chunk
     * @return in most cases, this same {@link ChunkBuilder} as provided, returned value will be used to create {@link org.diorite.world.chunk.Chunk}
     */
    public abstract ChunkBuilder generate(ChunkBuilder builder, ChunkPos pos);

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
