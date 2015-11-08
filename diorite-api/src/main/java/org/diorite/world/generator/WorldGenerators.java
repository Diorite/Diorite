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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.diorite.world.World;

public final class WorldGenerators
{
    private static final Collection<WorldGeneratorInitializer<?>> generators = new HashSet<>(5);

    private WorldGenerators()
    {
    }

    public static WorldGenerator getGenerator(final String name, final World world, Map<String, Object> options)
    {
        if (options == null)
        {
            options = new HashMap<>(1);
        }
        for (final WorldGeneratorInitializer<?> gen : generators)
        {
            if (gen.getName().equalsIgnoreCase(name))
            {
                return gen.init(world, options);
            }
        }
        return null;
    }

    public static void registerGenerator(final WorldGeneratorInitializer<?> generator)
    {
        generators.add(generator);
    }

    public static Collection<WorldGeneratorInitializer<?>> getGeneratorInitializers()
    {
        return new HashSet<>(generators);
    }
}

