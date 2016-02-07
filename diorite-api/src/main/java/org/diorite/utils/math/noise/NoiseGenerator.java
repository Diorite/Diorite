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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Base class for all noise generators
 * From Bukkit project https://github.com/Bukkit/Bukkit
 */
@SuppressWarnings("MagicNumber")
public abstract class NoiseGenerator
{
    protected final int[] perm = new int[512];
    protected double offsetX;
    protected double offsetY;
    protected double offsetZ;

    /**
     * Computes and returns the 1D noise for the given coordinate in 1D space
     *
     * @param x X coordinate
     *
     * @return Noise at given location, from range -1 to 1
     */
    public double noise(final double x)
    {
        return this.noise(x, 0, 0);
    }

    /**
     * Computes and returns the 2D noise for the given coordinates in 2D space
     *
     * @param x X coordinate
     * @param y Y coordinate
     *
     * @return Noise at given location, from range -1 to 1
     */
    public double noise(final double x, final double y)
    {
        return this.noise(x, y, 0);
    }

    /**
     * Computes and returns the 3D noise for the given coordinates in 3D space
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     *
     * @return Noise at given location, from range -1 to 1
     */
    public abstract double noise(double x, double y, double z);

    /**
     * Generates noise for the 1D coordinates using the specified number of
     * octaves and parameters
     *
     * @param x         X-coordinate
     * @param octaves   Number of octaves to use
     * @param frequency How much to alter the frequency by each octave
     * @param amplitude How much to alter the amplitude by each octave
     *
     * @return Resulting noise
     */
    public double noise(final double x, final int octaves, final double frequency, final double amplitude)
    {
        return this.noise(x, 0, 0, octaves, frequency, amplitude);
    }

    /**
     * Generates noise for the 1D coordinates using the specified number of
     * octaves and parameters
     *
     * @param x          X-coordinate
     * @param octaves    Number of octaves to use
     * @param frequency  How much to alter the frequency by each octave
     * @param amplitude  How much to alter the amplitude by each octave
     * @param normalized If true, normalize the value to [-1, 1]
     *
     * @return Resulting noise
     */
    public double noise(final double x, final int octaves, final double frequency, final double amplitude, final boolean normalized)
    {
        return this.noise(x, 0, 0, octaves, frequency, amplitude, normalized);
    }

    /**
     * Generates noise for the 2D coordinates using the specified number of
     * octaves and parameters
     *
     * @param x         X-coordinate
     * @param y         Y-coordinate
     * @param octaves   Number of octaves to use
     * @param frequency How much to alter the frequency by each octave
     * @param amplitude How much to alter the amplitude by each octave
     *
     * @return Resulting noise
     */
    public double noise(final double x, final double y, final int octaves, final double frequency, final double amplitude)
    {
        return this.noise(x, y, 0, octaves, frequency, amplitude);
    }

    /**
     * Generates noise for the 2D coordinates using the specified number of
     * octaves and parameters
     *
     * @param x          X-coordinate
     * @param y          Y-coordinate
     * @param octaves    Number of octaves to use
     * @param frequency  How much to alter the frequency by each octave
     * @param amplitude  How much to alter the amplitude by each octave
     * @param normalized If true, normalize the value to [-1, 1]
     *
     * @return Resulting noise
     */
    public double noise(final double x, final double y, final int octaves, final double frequency, final double amplitude, final boolean normalized)
    {
        return this.noise(x, y, 0, octaves, frequency, amplitude, normalized);
    }

    /**
     * Generates noise for the 3D coordinates using the specified number of
     * octaves and parameters
     *
     * @param x         X-coordinate
     * @param y         Y-coordinate
     * @param z         Z-coordinate
     * @param octaves   Number of octaves to use
     * @param frequency How much to alter the frequency by each octave
     * @param amplitude How much to alter the amplitude by each octave
     *
     * @return Resulting noise
     */
    public double noise(final double x, final double y, final double z, final int octaves, final double frequency, final double amplitude)
    {
        return this.noise(x, y, z, octaves, frequency, amplitude, false);
    }

    /**
     * Generates noise for the 3D coordinates using the specified number of
     * octaves and parameters
     *
     * @param x          X-coordinate
     * @param y          Y-coordinate
     * @param z          Z-coordinate
     * @param octaves    Number of octaves to use
     * @param frequency  How much to alter the frequency by each octave
     * @param amplitude  How much to alter the amplitude by each octave
     * @param normalized If true, normalize the value to [-1, 1]
     *
     * @return Resulting noise
     */
    public double noise(final double x, final double y, final double z, final int octaves, final double frequency, final double amplitude, final boolean normalized)
    {
        double result = 0;
        double amp = 1;
        double freq = 1;
        double max = 0;

        for (int i = 0; i < octaves; i++)
        {
            result += this.noise(x * freq, y * freq, z * freq) * amp;
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

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("perm", this.perm).append("offsetX", this.offsetX).append("offsetY", this.offsetY).append("offsetZ", this.offsetZ).toString();
    }

    /**
     * Speedy floor, faster than (int)Math.floor(x)
     *
     * @param x Value to floor
     *
     * @return Floored value
     */
    public static int floor(final double x)
    {
        return (x >= 0) ? (int) x : ((int) x - 1);
    }

    protected static double fade(final double x)
    {
        return x * x * x * ((x * ((x * 6) - 15)) + 10);
    }

    protected static double lerp(final double x, final double y, final double z)
    {
        return y + (x * (z - y));
    }

    protected static double grad(int hash, final double x, final double y, final double z)
    {
        hash &= 15;
        final double u = (hash < 8) ? x : y;
        final double v = (hash < 4) ? y : (((hash == 12) || (hash == 14)) ? x : z);
        return (((hash & 1) == 0) ? u : - u) + (((hash & 2) == 0) ? v : - v);
    }
}
