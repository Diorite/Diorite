/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.commons.math;

import javax.annotation.Nullable;

import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Class defining range in ints, may be used to validate numbers.
 */
public class IntRange
{
    /**
     * Range from 1 to 1.
     */
    public static final IntRange ONE   = new IntRange(1, 1);
    /**
     * Range from 0 to 0.
     */
    public static final IntRange EMPTY = new IntRange(0, 0);
    /**
     * Range from {@link Integer#MIN_VALUE} to {@link Integer#MAX_VALUE}
     */
    public static final IntRange FULL  = new IntRange(Integer.MIN_VALUE, Integer.MAX_VALUE);

    private final int min;
    private final int max;

    /**
     * Construct new range.
     *
     * @param min min value of range.
     * @param max max value of range.
     */
    public IntRange(int min, int max)
    {
        this.min = min;
        this.max = max;
    }

    /**
     * @return min value in range.
     */
    public int getMin()
    {
        return this.min;
    }

    /**
     * @return max value in range.
     */
    public int getMax()
    {
        return this.max;
    }

    /**
     * @return random value in range.
     */
    public int getRandom()
    {
        return ((this.max - this.min) == 0) ? this.max : DioriteRandomUtils.getRandomInt(this.min, this.max);
    }

    /**
     * Returns random value in range.
     *
     * @param random random instance to use.
     *
     * @return random value in range.
     */
    public int getRandom(Random random)
    {
        return ((this.max - this.min) == 0) ? this.max : DioriteRandomUtils.getRandomInt(random, this.min, this.max);
    }

    /**
     * @return size of range. (max - min + 1)
     */
    public long size()
    {
        return (((long) this.max - (long) this.min) + 1L);
    }

    /**
     * Check if given number is in range.
     *
     * @param i number to check.
     *
     * @return true if it is in range
     */
    public boolean isIn(int i)
    {
        return (i >= this.min) && (i <= this.max);
    }

    /**
     * Return given number if it is in range, or closest value in range.
     * {@code i > max -> max}
     * {@code i < min -> min}
     * {@code else -> i}
     *
     * @param i number to validate.
     *
     * @return closest number in range.
     */
    public int getIn(int i)
    {
        if (i > this.max)
        {
            return this.max;
        }
        if (i < this.min)
        {
            return this.min;
        }
        return i;
    }

    /**
     * Return given number if it is in range, or default value.
     * {@code i > max -> def}
     * {@code i < min -> def}
     * {@code else -> i}
     *
     * @param i   number to validate.
     * @param def default value.
     *
     * @return given number or default value.
     */
    public int getIn(int i, int def)
    {
        if (! this.isIn(i))
        {
            return def;
        }
        return i;
    }

    @Override
    public int hashCode()
    {
        int result = this.min;
        result = (31 * result) + this.max;
        return result;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof IntRange))
        {
            return false;
        }

        IntRange byteRange = (IntRange) o;

        return (this.max == byteRange.max) && (this.min == byteRange.min);

    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("min", this.min).append("max", this.max).toString();
    }

    /**
     * Create range with only gived value in range.
     *
     * @param num min and max of range.
     *
     * @return range with only one value in range.
     */
    public static IntRange fixed(int num)
    {
        return new IntRange(num, num);
    }

    /**
     * Parses given string to range, string is valid range when contains 2 numbers (second greater than first) and splt char: <br>
     * " - ", " : ", " ; ", ", ", " ", ",", ";", ":", "-"
     *
     * @param string string to parse.
     *
     * @return parsed range or null.
     */
    @Nullable
    public static IntRange valueOf(String string)
    {
        if (string.isEmpty())
        {
            return null;
        }
        String[] nums = null;
        int i = 0;
        boolean firstMinus = string.charAt(0) == '-';
        if (firstMinus)
        {
            string = string.substring(1);
        }
        while ((i < ByteRange.SPLITS.length) && ((nums == null) || (nums.length != 2)))
        {
            nums = StringUtils.splitByWholeSeparator(string, ByteRange.SPLITS[i++], 2);
        }
        if ((nums == null) || (nums.length != 2))
        {
            return null;
        }
        Integer min = DioriteMathUtils.asInt(firstMinus ? ("-" + nums[0]) : nums[0]);
        if (min == null)
        {
            return null;
        }
        Integer max = DioriteMathUtils.asInt(nums[1]);
        if ((max == null) || (min > max))
        {
            return null;
        }
        return new IntRange(min, max);
    }
}
