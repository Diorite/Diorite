package org.diorite.impl.world.generator;

import org.diorite.material.Material;
import org.diorite.utils.math.DioriteRandomUtils;
import org.diorite.world.World;
import org.diorite.world.chunk.Chunk;
import org.diorite.world.chunk.ChunkPos;
import org.diorite.world.generator.ChunkBuilder;
import org.diorite.world.generator.WorldGenerator;
import org.diorite.world.generator.WorldGeneratorInitializer;

public class FlatWorldGeneratorImpl extends WorldGenerator
{
    public FlatWorldGeneratorImpl(final World world, final String name, final String options)
    {
        super(world, name, options);  // TODO: implement options
    }

    @Override
    public ChunkBuilder generate(final ChunkBuilder builder, final ChunkPos pos)
    {
        for (int x = 0; x < Chunk.CHUNK_SIZE; x++)
        {
            for (int z = 0; z < Chunk.CHUNK_SIZE; z++)
            {
                for (int y = 0; y < 4; y++)
                {
                    if (y == 3)
                    {
                        builder.setBlock(x, y, z, Material.GRASS);
                    }
                    else if ((y == 2) || (y == 1))
                    {
                        builder.setBlock(x, y, z, Material.STONE.getType(DioriteRandomUtils.getRandInt(3, 4)));
                    }
                    else
                    {
                        builder.setBlock(x, y, z, Material.BEDROCK);
                    }
                }
            }
        }
        return builder;
    }

    public static WorldGeneratorInitializer<FlatWorldGeneratorImpl> createInitializer()
    {
        return new WorldGeneratorInitializer<FlatWorldGeneratorImpl>("flat")
        {
            @Override
            public FlatWorldGeneratorImpl baseInit(final World world, final String options)
            {
                return new FlatWorldGeneratorImpl(world, this.name, options);
            }
        };
    }
}
