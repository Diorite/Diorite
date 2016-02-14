/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.utils.math.noise;

import java.util.Random;

/**
 * Creates perlin noise through unbiased octaves
 * From Bukkit project https://github.com/Bukkit/Bukkit
 */
public class PerlinOctaveGenerator extends OctaveGenerator
{
    /**
     * Creates a perlin octave generator for the given world
     *
     * @param seed    Seed to construct this generator for
     * @param octaves Amount of octaves to create
     */
    public PerlinOctaveGenerator(final long seed, final int octaves)
    {
        this(new Random(seed), octaves);
    }

    /**
     * Creates a perlin octave generator for the given {@link Random}
     *
     * @param rand    Random object to construct this generator for
     * @param octaves Amount of octaves to create
     */
    public PerlinOctaveGenerator(final Random rand, final int octaves)
    {
        super(createOctaves(rand, octaves));
    }

    private static NoiseGenerator[] createOctaves(final Random rand, final int octaves)
    {
        final NoiseGenerator[] result = new NoiseGenerator[octaves];

        for (int i = 0; i < octaves; i++)
        {
            result[i] = new PerlinNoiseGenerator(rand);
        }

        return result;
    }
}