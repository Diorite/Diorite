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

import org.diorite.world.World;
import org.diorite.world.chunk.ChunkPos;
import org.diorite.world.generator.ChunkBuilder;
import org.diorite.world.generator.WorldGenerator;
import org.diorite.world.generator.WorldGeneratorInitializer;

public class VoidWorldGeneratorImpl extends WorldGenerator
{
    public VoidWorldGeneratorImpl(final World world, final String name, final Map<String, Object> options)
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
        return new WorldGeneratorInitializer<VoidWorldGeneratorImpl>("diorite:void")
        {
            @Override
            public VoidWorldGeneratorImpl baseInit(final World world, final Map<String, Object> options)
            {
                return new VoidWorldGeneratorImpl(world, this.name, options);
            }
        };
    }
}
