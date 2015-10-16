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

package org.diorite.impl.world.generator;

import java.util.Map;

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
    public FlatWorldGeneratorImpl(final World world, final String name, final Map<String, Object> options)
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
        return new WorldGeneratorInitializer<FlatWorldGeneratorImpl>("diorite:flat")
        {
            @Override
            public FlatWorldGeneratorImpl baseInit(final World world, final Map<String, Object> options)
            {
                return new FlatWorldGeneratorImpl(world, this.name, options);
            }
        };
    }
}
