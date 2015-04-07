package org.diorite.impl.world.generator;

import org.diorite.world.World;
import org.diorite.world.chunk.ChunkPos;
import org.diorite.world.generator.ChunkBuilder;
import org.diorite.world.generator.WorldGenerator;
import org.diorite.world.generator.WorldGeneratorInitializer;

public class VoidWorldGeneratorImpl extends WorldGenerator
{
    public VoidWorldGeneratorImpl(final World world, final String name, final String options)
    {
        super(world, name, options);
    }

    @Override
    public ChunkBuilder generate(final ChunkBuilder builder, final ChunkPos pos)
    {
        return builder;
    }

    public static WorldGeneratorInitializer<VoidWorldGeneratorImpl> createInitializer()
    {
        return new WorldGeneratorInitializer<VoidWorldGeneratorImpl>("void")
        {
            @Override
            public VoidWorldGeneratorImpl baseInit(final World world, final String options)
            {
                return new VoidWorldGeneratorImpl(world, this.name, options);
            }
        };
    }
}
