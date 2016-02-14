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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Creates simplex noise through unbiased octaves
 * From Bukkit project https://github.com/Bukkit/Bukkit
 */
public class SimplexOctaveGenerator extends OctaveGenerator
{
    private double wScale = 1;

    /**
     * Creates a simplex octave generator for the given world
     *
     * @param seed    Seed to construct this generator for
     * @param octaves Amount of octaves to create
     */
    public SimplexOctaveGenerator(final long seed, final int octaves)
    {
        this(new Random(seed), octaves);
    }

    /**
     * Creates a simplex octave generator for the given {@link Random}
     *
     * @param rand    Random object to construct this generator for
     * @param octaves Amount of octaves to create
     */
    public SimplexOctaveGenerator(final Random rand, final int octaves)
    {
        super(createOctaves(rand, octaves));
    }

    @Override
    public void setScale(final double scale)
    {
        super.setScale(scale);
        this.wScale = scale;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("wScale", this.wScale).toString();
    }

    /**
     * Gets the scale used for each W-coordinates passed
     *
     * @return W scale
     */
    public double getWScale()
    {
        return this.wScale;
    }

    /**
     * Sets the scale used for each W-coordinates passed
     *
     * @param scale New W scale
     */
    public void setWScale(final double scale)
    {
        this.wScale = scale;
    }

    /**
     * Generates noise for the 3D coordinates using the specified number of
     * octaves and parameters
     *
     * @param x         X-coordinate
     * @param y         Y-coordinate
     * @param z         Z-coordinate
     * @param w         W-coordinate
     * @param frequency How much to alter the frequency by each octave
     * @param amplitude How much to alter the amplitude by each octave
     *
     * @return Resulting noise
     */
    public double noise(final double x, final double y, final double z, final double w, final double frequency, final double amplitude)
    {
        return this.noise(x, y, z, w, frequency, amplitude, false);
    }

    /**
     * Generates noise for the 3D coordinates using the specified number of
     * octaves and parameters
     *
     * @param x          X-coordinate
     * @param y          Y-coordinate
     * @param z          Z-coordinate
     * @param w          W-coordinate
     * @param frequency  How much to alter the frequency by each octave
     * @param amplitude  How much to alter the amplitude by each octave
     * @param normalized If true, normalize the value to [-1, 1]
     *
     * @return Resulting noise
     */
    public double noise(double x, double y, double z, double w, final double frequency, final double amplitude, final boolean normalized)
    {
        double result = 0;
        double amp = 1;
        double freq = 1;
        double max = 0;

        x *= this.xScale;
        y *= this.yScale;
        z *= this.zScale;
        w *= this.wScale;

        for (final NoiseGenerator octave : this.octaves)
        {
            result += ((SimplexNoiseGenerator) octave).noise(x * freq, y * freq, z * freq, w * freq) * amp;
            max += amp;
            freq *= frequency;
            amp *= amplitude;
        }

        if (normalized)
        {
            result /= max;
        }

        return result;
    }

    private static NoiseGenerator[] createOctaves(final Random rand, final int octaves)
    {
        final NoiseGenerator[] result = new NoiseGenerator[octaves];

        for (int i = 0; i < octaves; i++)
        {
            result[i] = new SimplexNoiseGenerator(rand);
        }

        return result;
    }
}
