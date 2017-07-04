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

package org.diorite.commons.arrays.fastutil;

import it.unimi.dsi.fastutil.booleans.BooleanBidirectionalIterator;
import it.unimi.dsi.fastutil.booleans.BooleanIterable;
import it.unimi.dsi.fastutil.bytes.ByteBidirectionalIterator;
import it.unimi.dsi.fastutil.bytes.ByteIterable;
import it.unimi.dsi.fastutil.chars.CharBidirectionalIterator;
import it.unimi.dsi.fastutil.chars.CharIterable;
import it.unimi.dsi.fastutil.doubles.DoubleBidirectionalIterator;
import it.unimi.dsi.fastutil.doubles.DoubleIterable;
import it.unimi.dsi.fastutil.floats.FloatBidirectionalIterator;
import it.unimi.dsi.fastutil.floats.FloatIterable;
import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.IntIterable;
import it.unimi.dsi.fastutil.longs.LongBidirectionalIterator;
import it.unimi.dsi.fastutil.longs.LongIterable;
import it.unimi.dsi.fastutil.shorts.ShortBidirectionalIterator;
import it.unimi.dsi.fastutil.shorts.ShortIterable;

public class FastutilArrayUtils
{
    /**
     * Creates primitive iterator of given array.
     *
     * @param array
     *         array to warp.
     *
     * @return primitive iterator for given array.
     */
    public static BooleanBidirectionalIterator iteratorOf(boolean[] array)
    {
        return new BooleanIterator(array);
    }

    /**
     * Creates primitive iterator of given array.
     *
     * @param array
     *         array to warp.
     *
     * @return primitive iterator for given array.
     */
    public static ByteBidirectionalIterator iteratorOf(byte[] array)
    {
        return new ByteIterator(array);
    }

    /**
     * Creates primitive iterator of given array.
     *
     * @param array
     *         array to warp.
     *
     * @return primitive iterator for given array.
     */
    public static ShortBidirectionalIterator iteratorOf(short[] array)
    {
        return new ShortIterator(array);
    }

    /**
     * Creates primitive iterator of given array.
     *
     * @param array
     *         array to warp.
     *
     * @return primitive iterator for given array.
     */
    public static CharBidirectionalIterator iteratorOf(char[] array)
    {
        return new CharIterator(array);
    }

    /**
     * Creates primitive iterator of given array.
     *
     * @param array
     *         array to warp.
     *
     * @return primitive iterator for given array.
     */
    public static IntBidirectionalIterator iteratorOf(int[] array)
    {
        return new IntIterator(array);
    }

    /**
     * Creates primitive iterator of given array.
     *
     * @param array
     *         array to warp.
     *
     * @return primitive iterator for given array.
     */
    public static LongBidirectionalIterator iteratorOf(long[] array)
    {
        return new LongIterator(array);
    }

    /**
     * Creates primitive iterator of given array.
     *
     * @param array
     *         array to warp.
     *
     * @return primitive iterator for given array.
     */
    public static FloatBidirectionalIterator iteratorOf(float[] array)
    {
        return new FloatIterator(array);
    }

    /**
     * Creates primitive iterator of given array.
     *
     * @param array
     *         array to warp.
     *
     * @return primitive iterator for given array.
     */
    public static DoubleBidirectionalIterator iteratorOf(double[] array)
    {
        return new DoubleIterator(array);
    }

    /**
     * Creates primitive iterator of given array.
     *
     * @param array
     *         array to warp.
     *
     * @return primitive iterator for given array.
     */
    public static BooleanIterable iterableOf(boolean[] array)
    {
        return () -> iteratorOf(array);
    }

    /**
     * Creates primitive iterator of given array.
     *
     * @param array
     *         array to warp.
     *
     * @return primitive iterator for given array.
     */
    public static ByteIterable iterableOf(byte[] array)
    {
        return () -> iteratorOf(array);
    }

    /**
     * Creates primitive iterator of given array.
     *
     * @param array
     *         array to warp.
     *
     * @return primitive iterator for given array.
     */
    public static ShortIterable iterableOf(short[] array)
    {
        return () -> iteratorOf(array);
    }

    /**
     * Creates primitive iterator of given array.
     *
     * @param array
     *         array to warp.
     *
     * @return primitive iterator for given array.
     */
    public static CharIterable iterableOf(char[] array)
    {
        return () -> iteratorOf(array);
    }

    /**
     * Creates primitive iterator of given array.
     *
     * @param array
     *         array to warp.
     *
     * @return primitive iterator for given array.
     */
    public static IntIterable iterableOf(int[] array)
    {
        return () -> iteratorOf(array);
    }

    /**
     * Creates primitive iterator of given array.
     *
     * @param array
     *         array to warp.
     *
     * @return primitive iterator for given array.
     */
    public static LongIterable iterableOf(long[] array)
    {
        return () -> iteratorOf(array);
    }

    /**
     * Creates primitive iterator of given array.
     *
     * @param array
     *         array to warp.
     *
     * @return primitive iterator for given array.
     */
    public static FloatIterable iterableOf(float[] array)
    {
        return () -> iteratorOf(array);
    }

    /**
     * Creates primitive iterator of given array.
     *
     * @param array
     *         array to warp.
     *
     * @return primitive iterator for given array.
     */
    public static DoubleIterable iterableOf(double[] array)
    {
        return () -> iteratorOf(array);
    }
}
