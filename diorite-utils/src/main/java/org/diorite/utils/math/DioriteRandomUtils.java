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

package org.diorite.utils.math;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.apache.commons.lang3.Validate;

public final class DioriteRandomUtils
{
    private static final ThreadLocal<DioriteRandom> random = ThreadLocal.withInitial(DioriteRandom::new);

    private DioriteRandomUtils()
    {
    }

    public static DioriteRandom getRandom()
    {
        return random.get();
    }

    public static <T> T getRandom(final T[] array)
    {
        return getRandom(getRandom(), array);
    }

    public static <T> T getRandom(final List<T> coll)
    {
        return getRandom(getRandom(), coll);
    }

    public static <T, E extends Collection<T>> E getRandom(final Collection<T> coll, final E target, final int amount)
    {
        return getRandom(getRandom(), coll, target, amount, true);
    }

    public static <T, E extends Collection<T>> E getRandom(final Collection<T> coll, final E target, final int amount, final boolean noRepeat)
    {
        return getRandom(getRandom(), coll, target, amount, noRepeat);
    }

    public static <T> T getRandom(final Collection<T> coll)
    {
        return getRandom(getRandom(), coll);
    }

    public static long getRandomLongSafe(final long a, final long b)
    {
        return getRandomLongSafe(getRandom(), a, b);
    }

    public static int getRandomIntSafe(final int a, final int b)
    {
        return getRandomIntSafe(getRandom(), a, b);
    }

    public static double getRandomDoubleSafe(final double a, final double b)
    {
        return getRandomDoubleSafe(getRandom(), a, b);
    }

    public static float getRandomFloatSafe(final float a, final float b)
    {
        return getRandomFloatSafe(getRandom(), a, b);
    }

    public static long getRandomLong(final long min, final long max) throws IllegalArgumentException
    {
        return getRandomLong(getRandom(), min, max);
    }

    public static int getRandomInt(final int min, final int max) throws IllegalArgumentException
    {
        return getRandomInt(getRandom(), min, max);
    }

    public static double getRandomDouble(final double min, final double max) throws IllegalArgumentException
    {
        return getRandomDouble(getRandom(), min, max);
    }

    public static float getRandomFloat(final float min, final float max) throws IllegalArgumentException
    {
        return getRandomFloat(getRandom(), min, max);
    }

    public static boolean getChance(final double chance)
    {
        return getChance(getRandom(), chance);
    }


    // custom random
    public static <T> T getRandom(final Random random, final T[] array)
    {
        if (array.length == 0)
        {
            return null;
        }
        return array[random.nextInt(array.length)];
    }

    public static <T> T getRandom(final Random random, final List<T> coll)
    {
        return coll.get(random.nextInt(coll.size()));
    }

    public static <T, E extends Collection<T>> E getRandom(final Random random, final Collection<T> coll, final E target, final int amount)
    {
        return getRandom(random, coll, target, amount, true);
    }

    public static <T, E extends Collection<T>> E getRandom(final Random random, final Collection<T> coll, final E target, int amount, final boolean noRepeat)
    {
        if (coll.isEmpty())
        {
            return target;
        }
        final List<T> list = new ArrayList<>(coll);
        if (noRepeat)
        {
            while (! list.isEmpty() && (amount-- > 0))
            {
                target.add(list.remove(random.nextInt(list.size())));
            }
        }
        else
        {
            while (! list.isEmpty() && (amount-- > 0))
            {
                target.add(list.get(random.nextInt(list.size())));
            }
        }
        return target;
    }

    public static <T> T getRandom(final Random random, final Collection<T> coll)
    {
        if (coll.isEmpty())
        {
            return null;
        }

        final int index = random.nextInt(coll.size());
        if (coll instanceof List)
        {
            return ((List<? extends T>) coll).get(index);
        }
        else
        {
            final Iterator<? extends T> iter = coll.iterator();
            for (int i = 0; i < index; i++)
            {
                iter.next();
            }
            return iter.next();
        }
    }

    public static long getRandomLongSafe(final Random random, final long a, final long b)
    {
        if (a > b)
        {
            return getRandomLong(random, b, a);
        }
        return getRandomLong(random, a, b);
    }

    public static int getRandomIntSafe(final Random random, final int a, final int b)
    {
        return (int) getRandomLongSafe(random, a, b);
    }

    public static double getRandomDoubleSafe(final Random random, final double a, final double b)
    {
        if (a > b)
        {
            return getRandomDouble(random, b, a);
        }
        return getRandomDouble(random, a, b);
    }

    public static float getRandomFloatSafe(final Random random, final float a, final float b)
    {
        if (a > b)
        {
            return getRandomFloat(random, b, a);
        }
        return getRandomFloat(random, a, b);
    }

    public static long getRandomLong(final Random random, final long min, final long max) throws IllegalArgumentException
    {
        if (min == max)
        {
            return max;
        }
        Validate.isTrue(max > min, "Max can't be smaller than min!");
        return (Math.abs(random.nextLong()) % ((max - min) + 1)) + min;
    }

    public static int getRandomInt(final Random random, final int min, final int max) throws IllegalArgumentException
    {
        if (min == max)
        {
            return max;
        }
        Validate.isTrue(max > min, "Max can't be smaller than min!");
        return (int) getRandomLong(random, min, max);
    }

    public static double getRandomDouble(final Random random, final double min, final double max) throws IllegalArgumentException
    {
        if (Double.compare(min, max) == 0)
        {
            return max;
        }
        Validate.isTrue(max > min, "Max can't be smaller than min!");
        return (random.nextDouble() * (max - min)) + min;
    }

    public static float getRandomFloat(final Random random, final float min, final float max) throws IllegalArgumentException
    {
        if (Float.compare(min, max) == 0)
        {
            return max;
        }
        Validate.isTrue(max > min, "Max can't be smaller than min!");
        return (random.nextFloat() * (max - min)) + min;
    }

    public static boolean getChance(final Random random, final double chance)
    {
        return (chance > 0) && ((chance >= 100) || (chance >= getRandomDouble(random, 0, 100)));
    }

    /**
     * Delegated {@link Random} methods.
     */

    /**
     * Generates random bytes and places them into a user-supplied
     * byte array.  The number of random bytes produced is equal to
     * the length of the byte array.
     * <br>
     * <p>The method {@code nextBytes} is implemented by class {@code Random}
     * as if by:
     * <pre> {@code
     * public void nextBytes(byte[] bytes) {
     *   for (int i = 0; i < bytes.length; )
     *     for (int rnd = nextInt(), n = Math.min(bytes.length - i, 4);
     *          n-- > 0; rnd >>= 8)
     *       bytes[i++] = (byte)rnd;
     * }}</pre>
     *
     * @param bytes the byte array to fill with random bytes
     *
     * @throws NullPointerException if the byte array is null
     */
    public static void nextBytes(final byte[] bytes)
    {
        getRandom().nextBytes(bytes);
    }

    /**
     * Returns the next pseudorandom, uniformly distributed {@code int}
     * value from this random number generator's sequence. The general
     * contract of {@code nextInt} is that one {@code int} value is
     * pseudorandomly generated and returned. All 2<sup>32</sup> possible
     * {@code int} values are produced with (approximately) equal probability.
     * <br>
     * <p>The method {@code nextInt} is implemented by class {@code Random}
     * as if by:
     * <pre> {@code
     * public int nextInt() {
     *   return next(32);
     * }}</pre>
     *
     * @return the next pseudorandom, uniformly distributed {@code int}
     * value from this random number generator's sequence
     */
    public static int nextInt()
    {
        return getRandom().nextInt();
    }

    /**
     * Returns a pseudorandom, uniformly distributed {@code int} value
     * between 0 (inclusive) and the specified value (exclusive), drawn from
     * this random number generator's sequence.  The general contract of
     * {@code nextInt} is that one {@code int} value in the specified range
     * is pseudorandomly generated and returned.  All {@code bound} possible
     * {@code int} values are produced with (approximately) equal
     * probability.  The method {@code nextInt(int bound)} is implemented by
     * class {@code Random} as if by:
     * <pre> {@code
     * public int nextInt(int bound) {
     *   if (bound <= 0)
     *     throw new IllegalArgumentException("bound must be positive");
     *   if ((bound & -bound) == bound)  // i.e., bound is a power of 2
     *     return (int)((bound * (long)next(31)) >> 31);
     *   int bits, val;
     *   do {
     *       bits = next(31);
     *       val = bits % bound;
     *   } while (bits - val + (bound-1) < 0);
     *   return val;
     * }}</pre>
     * <br>
     * The hedge "approximately" is used in the foregoing description only
     * because the next method is only approximately an unbiased source of
     * independently chosen bits.  If it were a perfect source of randomly
     * chosen bits, then the algorithm shown would choose {@code int}
     * values from the stated range with perfect uniformity.
     * <br>
     * The algorithm is slightly tricky.  It rejects values that would result
     * in an uneven distribution (due to the fact that 2^31 is not divisible
     * by n). The probability of a value being rejected depends on n.  The
     * worst case is n=2^30+1, for which the probability of a reject is 1/2,
     * and the expected number of iterations before the loop terminates is 2.
     * <br>
     * The algorithm treats the case where n is a power of two specially: it
     * returns the correct number of high-order bits from the underlying
     * pseudo-random number generator.  In the absence of special treatment,
     * the correct number of <i>low-order</i> bits would be returned.  Linear
     * congruential pseudo-random number generators such as the one
     * implemented by this class are known to have short periods in the
     * sequence of values of their low-order bits.  Thus, this special case
     * greatly increases the length of the sequence of values returned by
     * successive calls to this method if n is a small power of two.
     *
     * @param bound the upper bound (exclusive).  Must be positive.
     *
     * @return the next pseudorandom, uniformly distributed {@code int}
     * value between zero (inclusive) and {@code bound} (exclusive)
     * from this random number generator's sequence
     *
     * @throws IllegalArgumentException if bound is not positive
     */
    public static int nextInt(final int bound)
    {
        return getRandom().nextInt(bound);
    }

    /**
     * Returns the next pseudorandom, uniformly distributed {@code long}
     * value from this random number generator's sequence. The general
     * contract of {@code nextLong} is that one {@code long} value is
     * pseudorandomly generated and returned.
     * <br>
     * <p>The method {@code nextLong} is implemented by class {@code Random}
     * as if by:
     * <pre> {@code
     * public long nextLong() {
     *   return ((long)next(32) << 32) + next(32);
     * }}</pre>
     * <br>
     * Because class {@code Random} uses a seed with only 48 bits,
     * this algorithm will not return all possible {@code long} values.
     *
     * @return the next pseudorandom, uniformly distributed {@code long}
     * value from this random number generator's sequence
     */
    public static long nextLong()
    {
        return getRandom().nextLong();
    }

    /**
     * Returns the next pseudorandom, uniformly distributed
     * {@code boolean} value from this random number generator's
     * sequence. The general contract of {@code nextBoolean} is that one
     * {@code boolean} value is pseudorandomly generated and returned.  The
     * values {@code true} and {@code false} are produced with
     * (approximately) equal probability.
     * <br>
     * <p>The method {@code nextBoolean} is implemented by class {@code Random}
     * as if by:
     * <pre> {@code
     * public boolean nextBoolean() {
     *   return next(1) != 0;
     * }}</pre>
     *
     * @return the next pseudorandom, uniformly distributed
     * {@code boolean} value from this random number generator's
     * sequence
     */
    public static boolean nextBoolean()
    {
        return getRandom().nextBoolean();
    }

    /**
     * Returns the next pseudorandom, uniformly distributed {@code float}
     * value between {@code 0.0} and {@code 1.0} from this random
     * number generator's sequence.
     * <br>
     * <p>The general contract of {@code nextFloat} is that one
     * {@code float} value, chosen (approximately) uniformly from the
     * range {@code 0.0f} (inclusive) to {@code 1.0f} (exclusive), is
     * pseudorandomly generated and returned. All 2<sup>24</sup> possible
     * {@code float} values of the form <i>m&nbsp;x&nbsp;</i>2<sup>-24</sup>,
     * where <i>m</i> is a positive integer less than 2<sup>24</sup>, are
     * produced with (approximately) equal probability.
     * <br>
     * <p>The method {@code nextFloat} is implemented by class {@code Random}
     * as if by:
     * <pre> {@code
     * public float nextFloat() {
     *   return next(24) / ((float)(1 << 24));
     * }}</pre>
     * <br>
     * <p>The hedge "approximately" is used in the foregoing description only
     * because the next method is only approximately an unbiased source of
     * independently chosen bits. If it were a perfect source of randomly
     * chosen bits, then the algorithm shown would choose {@code float}
     * values from the stated range with perfect uniformity.<p>
     * [In early versions of Java, the result was incorrectly calculated as:
     * <pre> {@code
     *   return next(30) / ((float)(1 << 30));}</pre>
     * This might seem to be equivalent, if not better, but in fact it
     * introduced a slight nonuniformity because of the bias in the rounding
     * of floating-point numbers: it was slightly more likely that the
     * low-order bit of the significand would be 0 than that it would be 1.]
     *
     * @return the next pseudorandom, uniformly distributed {@code float}
     * value between {@code 0.0} and {@code 1.0} from this
     * random number generator's sequence
     */
    public static float nextFloat()
    {
        return getRandom().nextFloat();
    }

    /**
     * Returns the next pseudorandom, uniformly distributed
     * {@code double} value between {@code 0.0} and
     * {@code 1.0} from this random number generator's sequence.
     * <br>
     * <p>The general contract of {@code nextDouble} is that one
     * {@code double} value, chosen (approximately) uniformly from the
     * range {@code 0.0d} (inclusive) to {@code 1.0d} (exclusive), is
     * pseudorandomly generated and returned.
     * <br>
     * <p>The method {@code nextDouble} is implemented by class {@code Random}
     * as if by:
     * <pre> {@code
     * public double nextDouble() {
     *   return (((long)next(26) << 27) + next(27))
     *     / (double)(1L << 53);
     * }}</pre>
     * <br>
     * <p>The hedge "approximately" is used in the foregoing description only
     * because the {@code next} method is only approximately an unbiased
     * source of independently chosen bits. If it were a perfect source of
     * randomly chosen bits, then the algorithm shown would choose
     * {@code double} values from the stated range with perfect uniformity.
     * <p>[In early versions of Java, the result was incorrectly calculated as:
     * <pre> {@code
     *   return (((long)next(27) << 27) + next(27))
     *     / (double)(1L << 54);}</pre>
     * This might seem to be equivalent, if not better, but in fact it
     * introduced a large nonuniformity because of the bias in the rounding
     * of floating-point numbers: it was three times as likely that the
     * low-order bit of the significand would be 0 than that it would be 1!
     * This nonuniformity probably doesn't matter much in practice, but we
     * strive for perfection.]
     *
     * @return the next pseudorandom, uniformly distributed {@code double}
     * value between {@code 0.0} and {@code 1.0} from this
     * random number generator's sequence
     *
     * @see Math#random
     */
    public static double nextDouble()
    {
        return getRandom().nextDouble();
    }

    // no nextGaussian method, it is synchronized, may cause blocks.

    /**
     * Returns a stream producing the given {@code streamSize} number of
     * pseudorandom {@code int} values.
     * <br>
     * <p>A pseudorandom {@code int} value is generated as if it's the result of
     * calling the method {@link #nextInt()}.
     *
     * @param streamSize the number of values to generate
     *
     * @return a stream of pseudorandom {@code int} values
     *
     * @throws IllegalArgumentException if {@code streamSize} is
     *                                  less than zero
     */
    public static IntStream ints(final long streamSize)
    {
        return getRandom().ints(streamSize);
    }

    /**
     * Returns an effectively unlimited stream of pseudorandom {@code int}
     * values.
     * <br>
     * <p>A pseudorandom {@code int} value is generated as if it's the result of
     * calling the method {@link #nextInt()}.
     *
     * @return a stream of pseudorandom {@code int} values
     * </p>
     * This method is implemented to be equivalent to {@code
     * ints(Long.MAX_VALUE)}.
     */
    public static IntStream ints()
    {
        return getRandom().ints();
    }

    /**
     * Returns a stream producing the given {@code streamSize} number
     * of pseudorandom {@code int} values, each conforming to the given
     * origin (inclusive) and bound (exclusive).
     * <br>
     * <p>A pseudorandom {@code int} value is generated as if it's the result of
     * calling the following method with the origin and bound:
     * <pre> {@code
     * int nextInt(int origin, int bound) {
     *   int n = bound - origin;
     *   if (n > 0) {
     *     return nextInt(n) + origin;
     *   }
     *   else {  // range not representable as int
     *     int r;
     *     do {
     *       r = nextInt();
     *     } while (r < origin || r >= bound);
     *     return r;
     *   }
     * }}</pre>
     *
     * @param streamSize         the number of values to generate
     * @param randomNumberOrigin the origin (inclusive) of each random value
     * @param randomNumberBound  the bound (exclusive) of each random value
     *
     * @return a stream of pseudorandom {@code int} values,
     * each with the given origin (inclusive) and bound (exclusive)
     *
     * @throws IllegalArgumentException if {@code streamSize} is
     *                                  less than zero, or {@code randomNumberOrigin}
     *                                  is greater than or equal to {@code randomNumberBound}
     */
    public static IntStream ints(final long streamSize, final int randomNumberOrigin, final int randomNumberBound)
    {
        return getRandom().ints(streamSize, randomNumberOrigin, randomNumberBound);
    }

    /**
     * Returns an effectively unlimited stream of pseudorandom {@code
     * int} values, each conforming to the given origin (inclusive) and bound
     * (exclusive).
     * <br>
     * <p>A pseudorandom {@code int} value is generated as if it's the result of
     * calling the following method with the origin and bound:
     * <pre> {@code
     * int nextInt(int origin, int bound) {
     *   int n = bound - origin;
     *   if (n > 0) {
     *     return nextInt(n) + origin;
     *   }
     *   else {  // range not representable as int
     *     int r;
     *     do {
     *       r = nextInt();
     *     } while (r < origin || r >= bound);
     *     return r;
     *   }
     * }}</pre>
     *
     * @param randomNumberOrigin the origin (inclusive) of each random value
     * @param randomNumberBound  the bound (exclusive) of each random value
     *
     * @return a stream of pseudorandom {@code int} values,
     * each with the given origin (inclusive) and bound (exclusive)
     *
     * @throws IllegalArgumentException if {@code randomNumberOrigin}
     *                                  is greater than or equal to {@code randomNumberBound}
     *                                  This method is implemented to be equivalent to {@code
     *                                  ints(Long.MAX_VALUE, randomNumberOrigin, randomNumberBound)}.
     */
    public static IntStream ints(final int randomNumberOrigin, final int randomNumberBound)
    {
        return getRandom().ints(randomNumberOrigin, randomNumberBound);
    }

    /**
     * Returns a stream producing the given {@code streamSize} number of
     * pseudorandom {@code long} values.
     * <br>
     * <p>A pseudorandom {@code long} value is generated as if it's the result
     * of calling the method {@link #nextLong()}.
     *
     * @param streamSize the number of values to generate
     *
     * @return a stream of pseudorandom {@code long} values
     *
     * @throws IllegalArgumentException if {@code streamSize} is
     *                                  less than zero
     */
    public static LongStream longs(final long streamSize)
    {
        return getRandom().longs(streamSize);
    }

    /**
     * Returns an effectively unlimited stream of pseudorandom {@code long}
     * values.
     * <br>
     * <p>A pseudorandom {@code long} value is generated as if it's the result
     * of calling the method {@link #nextLong()}.
     *
     * @return a stream of pseudorandom {@code long} values
     * </p>
     * This method is implemented to be equivalent to {@code
     * longs(Long.MAX_VALUE)}.
     */
    public static LongStream longs()
    {
        return getRandom().longs();
    }

    /**
     * Returns a stream producing the given {@code streamSize} number of
     * pseudorandom {@code long}, each conforming to the given origin
     * (inclusive) and bound (exclusive).
     * <br>
     * <p>A pseudorandom {@code long} value is generated as if it's the result
     * of calling the following method with the origin and bound:
     * <pre> {@code
     * long nextLong(long origin, long bound) {
     *   long r = nextLong();
     *   long n = bound - origin, m = n - 1;
     *   if ((n & m) == 0L)  // power of two
     *     r = (r & m) + origin;
     *   else if (n > 0L) {  // reject over-represented candidates
     *     for (long u = r >>> 1;            // ensure nonnegative
     *          u + m - (r = u % n) < 0L;    // rejection check
     *          u = nextLong() >>> 1) // retry
     *         ;
     *     r += origin;
     *   }
     *   else {              // range not representable as long
     *     while (r < origin || r >= bound)
     *       r = nextLong();
     *   }
     *   return r;
     * }}</pre>
     *
     * @param streamSize         the number of values to generate
     * @param randomNumberOrigin the origin (inclusive) of each random value
     * @param randomNumberBound  the bound (exclusive) of each random value
     *
     * @return a stream of pseudorandom {@code long} values,
     * each with the given origin (inclusive) and bound (exclusive)
     *
     * @throws IllegalArgumentException if {@code streamSize} is
     *                                  less than zero, or {@code randomNumberOrigin}
     *                                  is greater than or equal to {@code randomNumberBound}
     */
    public static LongStream longs(final long streamSize, final long randomNumberOrigin, final long randomNumberBound)
    {
        return getRandom().longs(streamSize, randomNumberOrigin, randomNumberBound);
    }

    /**
     * Returns an effectively unlimited stream of pseudorandom {@code
     * long} values, each conforming to the given origin (inclusive) and bound
     * (exclusive).
     * <br>
     * <p>A pseudorandom {@code long} value is generated as if it's the result
     * of calling the following method with the origin and bound:
     * <pre> {@code
     * long nextLong(long origin, long bound) {
     *   long r = nextLong();
     *   long n = bound - origin, m = n - 1;
     *   if ((n & m) == 0L)  // power of two
     *     r = (r & m) + origin;
     *   else if (n > 0L) {  // reject over-represented candidates
     *     for (long u = r >>> 1;            // ensure nonnegative
     *          u + m - (r = u % n) < 0L;    // rejection check
     *          u = nextLong() >>> 1) // retry
     *         ;
     *     r += origin;
     *   }
     *   else {              // range not representable as long
     *     while (r < origin || r >= bound)
     *       r = nextLong();
     *   }
     *   return r;
     * }}</pre>
     *
     * @param randomNumberOrigin the origin (inclusive) of each random value
     * @param randomNumberBound  the bound (exclusive) of each random value
     *
     * @return a stream of pseudorandom {@code long} values,
     * each with the given origin (inclusive) and bound (exclusive)
     *
     * @throws IllegalArgumentException if {@code randomNumberOrigin}
     *                                  is greater than or equal to {@code randomNumberBound}
     *                                  This method is implemented to be equivalent to {@code
     *                                  longs(Long.MAX_VALUE, randomNumberOrigin, randomNumberBound)}.
     */
    public static LongStream longs(final long randomNumberOrigin, final long randomNumberBound)
    {
        return getRandom().longs(randomNumberOrigin, randomNumberBound);
    }

    /**
     * Returns a stream producing the given {@code streamSize} number of
     * pseudorandom {@code double} values, each between zero
     * (inclusive) and one (exclusive).
     * <br>
     * <p>A pseudorandom {@code double} value is generated as if it's the result
     * of calling the method {@link #nextDouble()}.
     *
     * @param streamSize the number of values to generate
     *
     * @return a stream of {@code double} values
     *
     * @throws IllegalArgumentException if {@code streamSize} is
     *                                  less than zero
     */
    public static DoubleStream doubles(final long streamSize)
    {
        return getRandom().doubles(streamSize);
    }

    /**
     * Returns an effectively unlimited stream of pseudorandom {@code
     * double} values, each between zero (inclusive) and one
     * (exclusive).
     * <br>
     * <p>A pseudorandom {@code double} value is generated as if it's the result
     * of calling the method {@link #nextDouble()}.
     *
     * @return a stream of pseudorandom {@code double} values
     * </p>
     * This method is implemented to be equivalent to {@code
     * doubles(Long.MAX_VALUE)}.
     */
    public static DoubleStream doubles()
    {
        return getRandom().doubles();
    }

    /**
     * Returns a stream producing the given {@code streamSize} number of
     * pseudorandom {@code double} values, each conforming to the given origin
     * (inclusive) and bound (exclusive).
     * <br>
     * <p>A pseudorandom {@code double} value is generated as if it's the result
     * of calling the following method with the origin and bound:
     * <pre> {@code
     * double nextDouble(double origin, double bound) {
     *   double r = nextDouble();
     *   r = r * (bound - origin) + origin;
     *   if (r >= bound) // correct for rounding
     *     r = Math.nextDown(bound);
     *   return r;
     * }}</pre>
     *
     * @param streamSize         the number of values to generate
     * @param randomNumberOrigin the origin (inclusive) of each random value
     * @param randomNumberBound  the bound (exclusive) of each random value
     *
     * @return a stream of pseudorandom {@code double} values,
     * each with the given origin (inclusive) and bound (exclusive)
     *
     * @throws IllegalArgumentException if {@code streamSize} is
     *                                  less than zero
     * @throws IllegalArgumentException if {@code randomNumberOrigin}
     *                                  is greater than or equal to {@code randomNumberBound}
     */
    public static DoubleStream doubles(final long streamSize, final double randomNumberOrigin, final double randomNumberBound)
    {
        return getRandom().doubles(streamSize, randomNumberOrigin, randomNumberBound);
    }

    /**
     * Returns an effectively unlimited stream of pseudorandom {@code
     * double} values, each conforming to the given origin (inclusive) and bound
     * (exclusive).
     * <br>
     * <p>A pseudorandom {@code double} value is generated as if it's the result
     * of calling the following method with the origin and bound:
     * <pre> {@code
     * double nextDouble(double origin, double bound) {
     *   double r = nextDouble();
     *   r = r * (bound - origin) + origin;
     *   if (r >= bound) // correct for rounding
     *     r = Math.nextDown(bound);
     *   return r;
     * }}</pre>
     *
     * @param randomNumberOrigin the origin (inclusive) of each random value
     * @param randomNumberBound  the bound (exclusive) of each random value
     *
     * @return a stream of pseudorandom {@code double} values,
     * each with the given origin (inclusive) and bound (exclusive)
     *
     * @throws IllegalArgumentException if {@code randomNumberOrigin}
     *                                  is greater than or equal to {@code randomNumberBound}
     *                                  This method is implemented to be equivalent to {@code
     *                                  doubles(Long.MAX_VALUE, randomNumberOrigin, randomNumberBound)}.
     */
    public static DoubleStream doubles(final double randomNumberOrigin, final double randomNumberBound)
    {
        return getRandom().doubles(randomNumberOrigin, randomNumberBound);
    }

    /**
     * Construct new diorite random instance.
     *
     * @return created random instance.
     */
    public static DioriteRandom newRandom()
    {
        return new DioriteRandom();
    }

    /**
     * Construct new diorite random instance with given seed.
     *
     * @param seed seed of random instance.
     *
     * @return created random instance.
     */
    public static DioriteRandom newRandom(final long seed)
    {
        return new DioriteRandom(seed);
    }

    public static int sumWeight(final Iterable<? extends IWeightedRandomChoice> choices)
    {
        int i = 0;
        for (final IWeightedRandomChoice choice : choices)
        {
            i += choice.getWeight();
        }
        return i;
    }

    public static <T extends IWeightedRandomChoice> T getWeightedRandom(final Random random, final Iterable<? extends T> choices, final int weight)
    {
        if (weight <= 0)
        {
            throw new IllegalArgumentException("Weight must be greater than 0.");
        }
        return getWeightedRandomElement(choices, random.nextInt(weight));
    }

    public static <T extends IWeightedRandomChoice> T getWeightedRandom(final Iterable<? extends T> choices, final int weight)
    {
        return getWeightedRandom(getRandom(), choices, weight);
    }

    public static <T extends IWeightedRandomChoice> T getWeightedRandomElement(final Iterable<? extends T> choices, int weight)
    {
        for (final T choice : choices)
        {
            weight -= choice.getWeight();
            if (weight < 0)
            {
                return choice;
            }
        }
        return null;
    }

    public static <T extends IWeightedRandomChoice> T getWeightedRandom(final Random random, final Iterable<? extends T> choices)
    {
        return getWeightedRandom(random, choices, sumWeight(choices));
    }

    public static <T extends IWeightedRandomChoice> T getWeightedRandom(final Iterable<? extends T> choices)
    {
        return getWeightedRandom(getRandom(), choices);
    }
}
