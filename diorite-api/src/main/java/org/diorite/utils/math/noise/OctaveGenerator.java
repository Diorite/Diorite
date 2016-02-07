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
 * Creates noise using unbiased octaves
 * From Bukkit project https://github.com/Bukkit/Bukkit
 */
public abstract class OctaveGenerator
{
    protected final NoiseGenerator[] octaves;
    protected double xScale = 1;
    protected double yScale = 1;
    protected double zScale = 1;

    protected OctaveGenerator(final NoiseGenerator[] octaves)
    {
        this.octaves = octaves;
    }

    /**
     * Sets the scale used for all coordinates passed to this generator.
     * <br>
     * This is the equivalent to setting each coordinate to the specified
     * value.
     *
     * @param scale New value to scale each coordinate by
     */
    public void setScale(final double scale)
    {
        this.xScale = scale;
        this.yScale = scale;
        this.zScale = scale;
    }

    /**
     * Gets the scale used for each X-coordinates passed
     *
     * @return X scale
     */
    public double getXScale()
    {
        return this.xScale;
    }

    /**
     * Sets the scale used for each X-coordinates passed
     *
     * @param scale New X scale
     */
    public void setXScale(final double scale)
    {
        this.xScale = scale;
    }

    /**
     * Gets the scale used for each Y-coordinates passed
     *
     * @return Y scale
     */
    public double getYScale()
    {
        return this.yScale;
    }

    /**
     * Sets the scale used for each Y-coordinates passed
     *
     * @param scale New Y scale
     */
    public void setYScale(final double scale)
    {
        this.yScale = scale;
    }

    /**
     * Gets the scale used for each Z-coordinates passed
     *
     * @return Z scale
     */
    public double getZScale()
    {
        return this.zScale;
    }

    /**
     * Sets the scale used for each Z-coordinates passed
     *
     * @param scale New Z scale
     */
    public void setZScale(final double scale)
    {
        this.zScale = scale;
    }

    /**
     * Gets a clone of the individual octaves used within this generator
     *
     * @return Clone of the individual octaves
     */
    public NoiseGenerator[] getOctaves()
    {
        return this.octaves.clone();
    }

    /**
     * Generates noise for the 1D coordinates using the specified number of
     * octaves and parameters
     *
     * @param x         X-coordinate
     * @param frequency How much to alter the frequency by each octave
     * @param amplitude How much to alter the amplitude by each octave
     *
     * @return Resulting noise
     */
    public double noise(final double x, final double frequency, final double amplitude)
    {
        return this.noise(x, 0, 0, frequency, amplitude);
    }

    /**
     * Generates noise for the 1D coordinates using the specified number of
     * octaves and parameters
     *
     * @param x          X-coordinate
     * @param frequency  How much to alter the frequency by each octave
     * @param amplitude  How much to alter the amplitude by each octave
     * @param normalized If true, normalize the value to [-1, 1]
     *
     * @return Resulting noise
     */
    public double noise(final double x, final double frequency, final double amplitude, final boolean normalized)
    {
        return this.noise(x, 0, 0, frequency, amplitude, normalized);
    }

    /**
     * Generates noise for the 2D coordinates using the specified number of
     * octaves and parameters
     *
     * @param x         X-coordinate
     * @param y         Y-coordinate
     * @param frequency How much to alter the frequency by each octave
     * @param amplitude How much to alter the amplitude by each octave
     *
     * @return Resulting noise
     */
    public double noise(final double x, final double y, final double frequency, final double amplitude)
    {
        return this.noise(x, y, 0, frequency, amplitude);
    }

    /**
     * Generates noise for the 2D coordinates using the specified number of
     * octaves and parameters
     *
     * @param x          X-coordinate
     * @param y          Y-coordinate
     * @param frequency  How much to alter the frequency by each octave
     * @param amplitude  How much to alter the amplitude by each octave
     * @param normalized If true, normalize the value to [-1, 1]
     *
     * @return Resulting noise
     */
    public double noise(final double x, final double y, final double frequency, final double amplitude, final boolean normalized)
    {
        return this.noise(x, y, 0, frequency, amplitude, normalized);
    }

    /**
     * Generates noise for the 3D coordinates using the specified number of
     * octaves and parameters
     *
     * @param x         X-coordinate
     * @param y         Y-coordinate
     * @param z         Z-coordinate
     * @param frequency How much to alter the frequency by each octave
     * @param amplitude How much to alter the amplitude by each octave
     *
     * @return Resulting noise
     */
    public double noise(final double x, final double y, final double z, final double frequency, final double amplitude)
    {
        return this.noise(x, y, z, frequency, amplitude, false);
    }

    /**
     * Generates noise for the 3D coordinates using the specified number of
     * octaves and parameters
     *
     * @param x          X-coordinate
     * @param y          Y-coordinate
     * @param z          Z-coordinate
     * @param frequency  How much to alter the frequency by each octave
     * @param amplitude  How much to alter the amplitude by each octave
     * @param normalized If true, normalize the value to [-1, 1]
     *
     * @return Resulting noise
     */
    public double noise(double x, double y, double z, final double frequency, final double amplitude, final boolean normalized)
    {
        double result = 0;
        double amp = 1;
        double freq = 1;
        double max = 0;

        x *= this.xScale;
        y *= this.yScale;
        z *= this.zScale;

        for (final NoiseGenerator octave : this.octaves)
        {
            result += octave.noise(x * freq, y * freq, z * freq) * amp;
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("octaves", this.octaves).append("xScale", this.xScale).append("yScale", this.yScale).append("zScale", this.zScale).toString();
    }
}
