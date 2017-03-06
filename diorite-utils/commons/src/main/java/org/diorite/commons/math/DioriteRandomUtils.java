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

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.apache.commons.lang3.Validate;

import it.unimi.dsi.fastutil.doubles.Double2ObjectMap;
import it.unimi.dsi.fastutil.doubles.DoubleCollection;
import it.unimi.dsi.fastutil.doubles.DoubleIterator;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

public final class DioriteRandomUtils
{
    private static final ThreadLocal<DioriteRandom>       random       = ThreadLocal.withInitial(DioriteRandomImpl::new);
    private static final ThreadLocal<DioriteSecureRandom> secureRandom = ThreadLocal.withInitial(DioriteSecureRandomImpl::new);

    private DioriteRandomUtils()
    {
    }

    // non-public to prevent performance loss

    @SuppressWarnings("unchecked")
    static <T extends Random & DioriteRandom> T getRandom()
    {
        return (T) random.get();
    }

    @SuppressWarnings("unchecked")
    static <T extends SecureRandom & DioriteSecureRandom> T getSecureRandom()
    {
        return (T) secureRandom.get();
    }

    /**
     * Returns random element from given array.
     *
     * @param array
     *         array of elements.
     * @param <T>
     *         type of array.
     *
     * @return random element from given array.
     */
    @Nullable
    public static <T> T getRandom(T[] array)
    {
        return getRandom(getRandom(), array);
    }

    /**
     * Returns random element from given list.
     *
     * @param list
     *         list of elements.
     * @param <T>
     *         type of array.
     *
     * @return random element from given list.
     */
    @Nullable
    public static <T> T getRandom(List<T> list)
    {
        return getRandom(getRandom(), list);
    }

    /**
     * Pick some amount of random non-repeating elements from given collection, and adds it to another given collection. <br>
     * Same as {@link #getRandom(Collection, Collection, int, boolean)} with noRepeat = true.
     *
     * @param coll
     *         source collection with elements.
     * @param target
     *         target collection where random elements will be added.
     * @param amount
     *         amount of element to randomly pick from collection.
     * @param <T>
     *         type of collection elements.
     * @param <E>
     *         type of target collection.
     *
     * @return this same target collection as given.
     */
    public static <T, E extends Collection<? super T>> E getRandom(Collection<? extends T> coll, E target, int amount)
    {
        return getRandom(getRandom(), coll, target, amount, true);
    }

    /**
     * Pick some amount of random elements from given collection, and adds it to another given collection.
     *
     * @param coll
     *         source collection with elements.
     * @param target
     *         target collection where random elements will be added.
     * @param amount
     *         amount of element to randomly pick from collection.
     * @param noRepeat
     *         if false no duplicates will be selected.
     * @param <T>
     *         type of collection elements.
     * @param <E>
     *         type of target collection.
     *
     * @return this same target collection as given.
     */
    public static <T, E extends Collection<? super T>> E getRandom(Collection<? extends T> coll, E target, int amount, boolean noRepeat)
    {
        return getRandom(getRandom(), coll, target, amount, noRepeat);
    }

    /**
     * Returns random element from given collection.
     *
     * @param coll
     *         collection of elements.
     * @param <T>
     *         type of array.
     *
     * @return random element from given collection.
     */
    @Nullable
    public static <T> T getRandom(Collection<? extends T> coll)
    {
        return getRandom(getRandom(), coll);
    }

    /**
     * Returns random numeric value from inclusive range from a to b, method will still return valid random number if a > b.
     *
     * @param a
     *         range number.
     * @param b
     *         range number.
     *
     * @return random numeric value from inclusive range from a to b.
     */
    public static long getRandomLongSafe(long a, long b)
    {
        return getRandomLongSafe(getRandom(), a, b);
    }

    /**
     * Returns random numeric value from inclusive range from a to b, method will still return valid random number if a > b.
     *
     * @param a
     *         range number.
     * @param b
     *         range number.
     *
     * @return random numeric value from inclusive range from a to b.
     */
    public static int getRandomIntSafe(int a, int b)
    {
        return getRandomIntSafe(getRandom(), a, b);
    }

    /**
     * Returns random numeric value from inclusive range from a to b, method will still return valid random number if a > b.
     *
     * @param a
     *         range number.
     * @param b
     *         range number.
     *
     * @return random numeric value from inclusive range from a to b.
     */
    public static double getRandomDoubleSafe(double a, double b)
    {
        return getRandomDoubleSafe(getRandom(), a, b);
    }

    /**
     * Returns random numeric value from inclusive range from a to b, method will still return valid random number if a > b.
     *
     * @param a
     *         range number.
     * @param b
     *         range number.
     *
     * @return random numeric value from inclusive range from a to b.
     */
    public static float getRandomFloatSafe(float a, float b)
    {
        return getRandomFloatSafe(getRandom(), a, b);
    }

    /**
     * Returns random numeric value from inclusive range from min to max.
     *
     * @param min
     *         minimal number to pick.
     * @param max
     *         maximal number to pick.
     *
     * @return random numeric value from inclusive range from min to max.
     *
     * @throws IllegalArgumentException
     *         if min > max
     */
    public static long getRandomLong(long min, long max) throws IllegalArgumentException
    {
        return getRandomLong(getRandom(), min, max);
    }

    /**
     * Returns random numeric value from inclusive range from min to max.
     *
     * @param min
     *         minimal number to pick.
     * @param max
     *         maximal number to pick.
     *
     * @return random numeric value from inclusive range from min to max.
     *
     * @throws IllegalArgumentException
     *         if min > max
     */
    public static int getRandomInt(int min, int max) throws IllegalArgumentException
    {
        return getRandomInt(getRandom(), min, max);
    }

    /**
     * Returns random numeric value from inclusive range from min to max.
     *
     * @param min
     *         minimal number to pick.
     * @param max
     *         maximal number to pick.
     *
     * @return random numeric value from inclusive range from min to max.
     *
     * @throws IllegalArgumentException
     *         if min > max
     */
    public static double getRandomDouble(double min, double max) throws IllegalArgumentException
    {
        return getRandomDouble(getRandom(), min, max);
    }

    /**
     * Returns random numeric value from inclusive range from min to max.
     *
     * @param min
     *         minimal number to pick.
     * @param max
     *         maximal number to pick.
     *
     * @return random numeric value from inclusive range from min to max.
     *
     * @throws IllegalArgumentException
     *         if min > max
     */
    public static float getRandomFloat(float min, float max) throws IllegalArgumentException
    {
        return getRandomFloat(getRandom(), min, max);
    }

    /**
     * Get random boolean with given chance for true, chance is in %, 100.0 = 100%
     *
     * @param chance
     *         chance in %.
     *
     * @return true or false.
     */
    public static boolean getChance(double chance)
    {
        return getChance(getRandom(), chance);
    }

    /**
     * Returns random elements from iterable of choices based on weight of each random element.
     *
     * @param choices
     *         random choices.
     * @param <T>
     *         type of elements.
     *
     * @return one of elements.
     */
    @Nullable
    public static <T extends IWeightedRandomChoice> T getWeightedRandom(Iterable<? extends T> choices)
    {
        return getWeightedRandom(getRandom(), choices, sumWeight(choices));
    }

    /**
     * Returns random elements from map of choices based on weight of each random element, where key is weight of element.
     *
     * @param choices
     *         random choices.
     * @param <T>
     *         type of elements.
     *
     * @return one of elements.
     */
    @Nullable
    public static <T> T getWeightedRandomReversed(Map<? extends Number, ? extends T> choices)
    {
        return getWeightedRandomReversed(getRandom(), choices);
    }

    /**
     * Returns random elements from map of choices based on weight of each random element, where key is weight of element.
     *
     * @param choices
     *         random choices.
     * @param <T>
     *         type of elements.
     *
     * @return one of elements.
     */
    @Nullable
    public static <T> T getWeightedRandomReversedDouble(Map<Double, T> choices)
    {
        return getWeightedRandomReversed(getRandom(), choices);
    }

    /**
     * Returns random elements from map of choices based on weight of each random element, where key is weight of element.
     *
     * @param choices
     *         random choices.
     * @param <T>
     *         type of elements.
     *
     * @return one of elements.
     */
    @Nullable
    public static <T> T getWeightedRandomReversed(Double2ObjectMap<T> choices)
    {
        return getWeightedRandomReversed(getRandom(), choices);
    }

    /**
     * Returns random elements from map of choices based on weight of each random element, where key is weight of element.
     *
     * @param choices
     *         random choices.
     * @param <T>
     *         type of elements.
     *
     * @return one of elements.
     */
    @Nullable
    public static <T> T getWeightedRandomReversedInt(Map<Integer, T> choices)
    {
        return getWeightedRandomReversed(getRandom(), choices);
    }

    /**
     * Returns random elements from map of choices based on weight of each random element, where key is weight of element.
     *
     * @param choices
     *         random choices.
     * @param <T>
     *         type of elements.
     *
     * @return one of elements.
     */
    @Nullable
    public static <T> T getWeightedRandomReversed(Int2ObjectMap<T> choices)
    {
        return getWeightedRandomReversed(getRandom(), choices);
    }

    /**
     * Returns random elements from map of choices based on weight of each random element, where value is weight of element.
     *
     * @param choices
     *         random choices.
     * @param <T>
     *         type of elements.
     *
     * @return one of elements.
     */
    @Nullable
    public static <T> T getWeightedRandom(Map<? extends T, ? extends Number> choices)
    {
        return getWeightedRandom(getRandom(), choices);
    }

    /**
     * Returns random elements from map of choices based on weight of each random element, where value is weight of element.
     *
     * @param choices
     *         random choices.
     * @param <T>
     *         type of elements.
     *
     * @return one of elements.
     */
    @Nullable
    public static <T> T getWeightedRandomDouble(Map<T, Double> choices)
    {
        return getWeightedRandom(getRandom(), choices);
    }

    /**
     * Returns random elements from map of choices based on weight of each random element, where value is weight of element.
     *
     * @param choices
     *         random choices.
     * @param <T>
     *         type of elements.
     *
     * @return one of elements.
     */
    @Nullable
    public static <T> T getWeightedRandom(Object2DoubleMap<T> choices)
    {
        return getWeightedRandom(getRandom(), choices);
    }

    /**
     * Returns random elements from map of choices based on weight of each random element, where value is weight of element.
     *
     * @param choices
     *         random choices.
     * @param <T>
     *         type of elements.
     *
     * @return one of elements.
     */
    @Nullable
    public static <T> T getWeightedRandomInt(Map<T, Integer> choices)
    {
        return getWeightedRandom(getRandom(), choices);
    }

    /**
     * Returns random elements from map of choices based on weight of each random element, where value is weight of element.
     *
     * @param choices
     *         random choices.
     * @param <T>
     *         type of elements.
     *
     * @return one of elements.
     */
    @Nullable
    public static <T> T getWeightedRandom(Object2IntMap<T> choices)
    {
        return getWeightedRandom(getRandom(), choices);
    }

    /*
     * with custom random
     */

    /**
     * Returns random element from given array.
     *
     * @param random
     *         random instance to use.
     * @param array
     *         array of elements.
     * @param <T>
     *         type of array.
     *
     * @return random element from given array.
     */
    @Nullable
    public static <T> T getRandom(Random random, T[] array)
    {
        if (array.length == 0)
        {
            return null;
        }
        return array[random.nextInt(array.length)];
    }

    /**
     * Returns random element from given list.
     *
     * @param random
     *         random instance to use.
     * @param list
     *         list of elements.
     * @param <T>
     *         type of array.
     *
     * @return random element from given list.
     */
    @Nullable
    public static <T> T getRandom(Random random, List<T> list)
    {
        if (list.isEmpty())
        {
            return null;
        }
        return list.get(random.nextInt(list.size()));
    }

    /**
     * Pick some amount of random non-repeating elements from given collection, and adds it to another given collection. <br>
     * Same as {@link #getRandom(Collection, Collection, int, boolean)} with noRepeat = true.
     *
     * @param random
     *         random instance to use.
     * @param coll
     *         source collection with elements.
     * @param target
     *         target collection where random elements will be added.
     * @param amount
     *         amount of element to randomly pick from collection.
     * @param <T>
     *         type of collection elements.
     * @param <E>
     *         type of target collection.
     *
     * @return this same target collection as given.
     */
    public static <T, E extends Collection<? super T>> E getRandom(Random random, Collection<? extends T> coll, E target, int amount)
    {
        return getRandom(random, coll, target, amount, true);
    }

    /**
     * Pick some amount of random elements from given collection, and adds it to another given collection.
     *
     * @param random
     *         random instance to use.
     * @param coll
     *         source collection with elements.
     * @param target
     *         target collection where random elements will be added.
     * @param amount
     *         amount of element to randomly pick from collection.
     * @param noRepeat
     *         if false no duplicates will be selected.
     * @param <T>
     *         type of collection elements.
     * @param <E>
     *         type of target collection.
     *
     * @return this same target collection as given.
     */
    public static <T, E extends Collection<? super T>> E getRandom(Random random, Collection<? extends T> coll, E target, int amount, boolean noRepeat)
    {
        if (coll.isEmpty())
        {
            return target;
        }
        List<T> list = new ArrayList<>(coll);
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

    /**
     * Returns random element from given collection.
     *
     * @param random
     *         random instance to use.
     * @param coll
     *         collection of elements.
     * @param <T>
     *         type of array.
     *
     * @return random element from given collection.
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <T> T getRandom(Random random, Collection<? extends T> coll)
    {
        if (coll.isEmpty())
        {
            return null;
        }

        int index = random.nextInt(coll.size());
        if (coll instanceof List)
        {
            return ((List<? extends T>) coll).get(index);
        }
        else
        {
            Iterator<? extends T> iter = coll.iterator();
            for (int i = 0; i < index; i++)
            {
                iter.next();
            }
            return iter.next();
        }
    }

    /**
     * Returns random numeric value from inclusive range from a to b, method will still return valid random number if a > b.
     *
     * @param random
     *         random instance to use.
     * @param a
     *         range number.
     * @param b
     *         range number.
     *
     * @return random numeric value from inclusive range from a to b.
     */
    public static long getRandomLongSafe(Random random, long a, long b)
    {
        if (a > b)
        {
            return getRandomLong(random, b, a);
        }
        return getRandomLong(random, a, b);
    }

    /**
     * Returns random numeric value from inclusive range from a to b, method will still return valid random number if a > b.
     *
     * @param random
     *         random instance to use.
     * @param a
     *         range number.
     * @param b
     *         range number.
     *
     * @return random numeric value from inclusive range from a to b.
     */
    public static int getRandomIntSafe(Random random, int a, int b)
    {
        return (int) getRandomLongSafe(random, a, b);
    }

    /**
     * Returns random numeric value from inclusive range from a to b, method will still return valid random number if a > b.
     *
     * @param random
     *         random instance to use.
     * @param a
     *         range number.
     * @param b
     *         range number.
     *
     * @return random numeric value from inclusive range from a to b.
     */
    public static double getRandomDoubleSafe(Random random, double a, double b)
    {
        if (a > b)
        {
            return getRandomDouble(random, b, a);
        }
        return getRandomDouble(random, a, b);
    }

    /**
     * Returns random numeric value from inclusive range from a to b, method will still return valid random number if a > b.
     *
     * @param random
     *         random instance to use.
     * @param a
     *         range number.
     * @param b
     *         range number.
     *
     * @return random numeric value from inclusive range from a to b.
     */
    public static float getRandomFloatSafe(Random random, float a, float b)
    {
        if (a > b)
        {
            return getRandomFloat(random, b, a);
        }
        return getRandomFloat(random, a, b);
    }

    /**
     * Returns random numeric value from inclusive range from min to max.
     *
     * @param random
     *         random instance to use.
     * @param min
     *         minimal number to pick.
     * @param max
     *         maximal number to pick.
     *
     * @return random numeric value from inclusive range from min to max.
     *
     * @throws IllegalArgumentException
     *         if min > max
     */
    public static long getRandomLong(Random random, long min, long max) throws IllegalArgumentException
    {
        if (min == max)
        {
            return max;
        }
        Validate.isTrue(max > min, "Max can't be smaller than min!");
        return (Math.abs(random.nextLong()) % ((max - min) + 1)) + min;
    }

    /**
     * Returns random numeric value from inclusive range from min to max.
     *
     * @param random
     *         random instance to use.
     * @param min
     *         minimal number to pick.
     * @param max
     *         maximal number to pick.
     *
     * @return random numeric value from inclusive range from min to max.
     *
     * @throws IllegalArgumentException
     *         if min > max
     */
    public static int getRandomInt(Random random, int min, int max) throws IllegalArgumentException
    {
        if (min == max)
        {
            return max;
        }
        Validate.isTrue(max > min, "Max can't be smaller than min!");
        return (int) getRandomLong(random, min, max);
    }

    /**
     * Returns random numeric value from inclusive range from min to max.
     *
     * @param random
     *         random instance to use.
     * @param min
     *         minimal number to pick.
     * @param max
     *         maximal number to pick.
     *
     * @return random numeric value from inclusive range from min to max.
     *
     * @throws IllegalArgumentException
     *         if min > max
     */
    public static double getRandomDouble(Random random, double min, double max) throws IllegalArgumentException
    {
        if (Double.compare(min, max) == 0)
        {
            return max;
        }
        Validate.isTrue(max > min, "Max can't be smaller than min!");
        return (random.nextDouble() * (max - min)) + min;
    }

    /**
     * Returns random numeric value from inclusive range from min to max.
     *
     * @param random
     *         random instance to use.
     * @param min
     *         minimal number to pick.
     * @param max
     *         maximal number to pick.
     *
     * @return random numeric value from inclusive range from min to max.
     *
     * @throws IllegalArgumentException
     *         if min > max
     */
    public static float getRandomFloat(Random random, float min, float max) throws IllegalArgumentException
    {
        if (Float.compare(min, max) == 0)
        {
            return max;
        }
        Validate.isTrue(max > min, "Max can't be smaller than min!");
        return (random.nextFloat() * (max - min)) + min;
    }

    /**
     * Get random boolean with given chance for true, chance is in %, 100.0 = 100%
     *
     * @param random
     *         random instance to use.
     * @param random
     *         random instance to use.
     * @param chance
     *         chance in %.
     *
     * @return true or false.
     */
    public static boolean getChance(Random random, double chance)
    {
        return (chance > 0) && ((chance >= 100) || (chance >= getRandomDouble(random, 0, 100)));
    }

    /**
     * Returns random elements from iterable of choices based on weight of each random element.
     *
     * @param random
     *         random instance to use.
     * @param choices
     *         random choices.
     * @param <T>
     *         type of elements.
     *
     * @return one of elements.
     */
    @Nullable
    public static <T extends IWeightedRandomChoice> T getWeightedRandom(Random random, Iterable<? extends T> choices)
    {
        return getWeightedRandom(random, choices, sumWeight(choices));
    }

    /**
     * Returns random elements from map of choices based on weight of each random element, where key is weight of element.
     *
     * @param random
     *         random instance to use.
     * @param choices
     *         random choices.
     * @param <T>
     *         type of elements.
     *
     * @return one of elements.
     */
    @Nullable
    public static <T> T getWeightedRandomReversed(Random random, Map<? extends Number, ? extends T> choices)
    {
        double i = 0;
        for (Number choice : choices.keySet())
        {
            i += choice.doubleValue();
        }
        i = getRandomDouble(random, 0, i);
        for (Entry<? extends Number, ? extends T> entry : choices.entrySet())
        {
            i -= entry.getKey().doubleValue();
            if (i < 0)
            {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * Returns random elements from map of choices based on weight of each random element, where key is weight of element.
     *
     * @param random
     *         random instance to use.
     * @param choices
     *         random choices.
     * @param <T>
     *         type of elements.
     *
     * @return one of elements.
     */
    @Nullable
    public static <T> T getWeightedRandomReversedDouble(Random random, Map<Double, T> choices)
    {
        if (choices instanceof Double2ObjectMap)
        {
            return getWeightedRandomReversed(random, (Double2ObjectMap<T>) choices);
        }
        double i = 0;
        Set<Double> doubles = choices.keySet();
        for (Double x : doubles)
        {
            i += x;
        }
        i = getRandomDouble(random, 0, i);
        for (Entry<Double, T> entry : choices.entrySet())
        {
            i -= entry.getKey();
            if (i < 0)
            {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * Returns random elements from map of choices based on weight of each random element, where key is weight of element.
     *
     * @param random
     *         random instance to use.
     * @param choices
     *         random choices.
     * @param <T>
     *         type of elements.
     *
     * @return one of elements.
     */
    @Nullable
    public static <T> T getWeightedRandomReversed(Random random, Double2ObjectMap<T> choices)
    {
        double i = 0;
        DoubleSet doubles = choices.keySet();
        for (DoubleIterator iterator = doubles.iterator(); iterator.hasNext(); )
        {
            double x = iterator.nextDouble();
            i += x;
        }
        i = getRandomDouble(random, 0, i);
        for (Double2ObjectMap.Entry<T> entry : choices.double2ObjectEntrySet())
        {
            i -= entry.getDoubleKey();
            if (i < 0)
            {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * Returns random elements from map of choices based on weight of each random element, where key is weight of element.
     *
     * @param random
     *         random instance to use.
     * @param choices
     *         random choices.
     * @param <T>
     *         type of elements.
     *
     * @return one of elements.
     */
    @Nullable
    public static <T> T getWeightedRandomReversedInt(Random random, Map<Integer, T> choices)
    {
        if (choices instanceof Int2ObjectMap)
        {
            return getWeightedRandomReversed(random, (Int2ObjectMap<T>) choices);
        }
        long i = 0;
        Set<Integer> ints = choices.keySet();
        for (Integer x : ints)
        {
            i += x;
        }
        i = getRandomLong(random, 0, i);
        for (Entry<Integer, T> entry : choices.entrySet())
        {
            i -= entry.getKey();
            if (i < 0)
            {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * Returns random elements from map of choices based on weight of each random element, where key is weight of element.
     *
     * @param random
     *         random instance to use.
     * @param choices
     *         random choices.
     * @param <T>
     *         type of elements.
     *
     * @return one of elements.
     */
    @Nullable
    public static <T> T getWeightedRandomReversed(Random random, Int2ObjectMap<T> choices)
    {
        long i = 0;
        IntSet ints = choices.keySet();
        for (IntIterator iterator = ints.iterator(); iterator.hasNext(); )
        {
            int x = iterator.nextInt();
            i += x;
        }
        i = getRandomLong(random, 0, i);
        for (Int2ObjectMap.Entry<T> entry : choices.int2ObjectEntrySet())
        {
            i -= entry.getIntKey();
            if (i < 0)
            {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * Returns random elements from map of choices based on weight of each random element, where value is weight of element.
     *
     * @param random
     *         random instance to use.
     * @param choices
     *         random choices.
     * @param <T>
     *         type of elements.
     *
     * @return one of elements.
     */
    @Nullable
    public static <T> T getWeightedRandom(Random random, Map<? extends T, ? extends Number> choices)
    {
        double i = 0;
        for (Number choice : choices.values())
        {
            i += choice.doubleValue();
        }
        i = getRandomDouble(random, 0, i);
        for (Entry<? extends T, ? extends Number> entry : choices.entrySet())
        {
            i -= entry.getValue().doubleValue();
            if (i < 0)
            {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Returns random elements from map of choices based on weight of each random element, where value is weight of element.
     *
     * @param random
     *         random instance to use.
     * @param choices
     *         random choices.
     * @param <T>
     *         type of elements.
     *
     * @return one of elements.
     */
    @Nullable
    public static <T> T getWeightedRandomDouble(Random random, Map<T, Double> choices)
    {
        if (choices instanceof Object2DoubleMap)
        {
            return getWeightedRandom(random, (Object2DoubleMap<T>) choices);
        }
        double i = 0;
        Collection<Double> doubles = choices.values();
        for (Double x : doubles)
        {
            i += x;
        }
        i = getRandomDouble(random, 0, i);
        for (Entry<T, Double> entry : choices.entrySet())
        {
            i -= entry.getValue();
            if (i < 0)
            {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Returns random elements from map of choices based on weight of each random element, where value is weight of element.
     *
     * @param random
     *         random instance to use.
     * @param choices
     *         random choices.
     * @param <T>
     *         type of elements.
     *
     * @return one of elements.
     */
    @Nullable
    public static <T> T getWeightedRandom(Random random, Object2DoubleMap<T> choices)
    {
        double i = 0;
        DoubleCollection doubles = choices.values();
        for (DoubleIterator iterator = doubles.iterator(); iterator.hasNext(); )
        {
            double x = iterator.nextDouble();
            i += x;
        }
        i = getRandomDouble(random, 0, i);
        for (Object2DoubleMap.Entry<T> entry : choices.object2DoubleEntrySet())
        {
            i -= entry.getDoubleValue();
            if (i < 0)
            {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Returns random elements from map of choices based on weight of each random element, where value is weight of element.
     *
     * @param random
     *         random instance to use.
     * @param choices
     *         random choices.
     * @param <T>
     *         type of elements.
     *
     * @return one of elements.
     */
    @Nullable
    public static <T> T getWeightedRandomInt(Random random, Map<T, Integer> choices)
    {
        if (choices instanceof Object2IntMap)
        {
            return getWeightedRandom(random, (Object2IntMap<T>) choices);
        }
        long i = 0;
        Collection<Integer> ints = choices.values();
        for (Integer x : ints)
        {
            i += x;
        }
        i = getRandomLong(random, 0, i);
        for (Entry<T, Integer> entry : choices.entrySet())
        {
            i -= entry.getValue();
            if (i < 0)
            {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Returns random elements from map of choices based on weight of each random element, where value is weight of element.
     *
     * @param random
     *         random instance to use.
     * @param choices
     *         random choices.
     * @param <T>
     *         type of elements.
     *
     * @return one of elements.
     */
    @Nullable
    public static <T> T getWeightedRandom(Random random, Object2IntMap<T> choices)
    {
        long i = 0;
        IntCollection ints = choices.values();
        for (IntIterator iterator = ints.iterator(); iterator.hasNext(); )
        {
            int x = iterator.nextInt();
            i += x;
        }
        i = getRandomLong(random, 0, i);
        for (Object2IntMap.Entry<T> entry : choices.object2IntEntrySet())
        {
            i -= entry.getIntValue();
            if (i < 0)
            {
                return entry.getKey();
            }
        }
        return null;
    }

    private static double sumWeight(Iterable<? extends IWeightedRandomChoice> choices)
    {
        double i = 0;
        for (IWeightedRandomChoice choice : choices)
        {
            i += choice.getWeight();
        }
        return i;
    }

    @Nullable
    private static <T extends IWeightedRandomChoice> T getWeightedRandom(Random random, Iterable<? extends T> choices, double weightSum)
    {
        if (weightSum <= 0)
        {
            throw new IllegalArgumentException("Weight must be greater than 0.");
        }
        return getWeightedRandomElement(choices, getRandomDouble(random, 0, weightSum));
    }

    @Nullable
    private static <T extends IWeightedRandomChoice> T getWeightedRandom(Iterable<? extends T> choices, double weightSum)
    {
        return getWeightedRandom(getRandom(), choices, weightSum);
    }

    @Nullable
    private static <T extends IWeightedRandomChoice> T getWeightedRandomElement(Iterable<? extends T> choices, double randomWeight)
    {
        for (T choice : choices)
        {
            randomWeight -= choice.getWeight();
            if (randomWeight < 0)
            {
                return choice;
            }
        }
        return null;
    }
    /*
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
     * @param bytes
     *         the byte array to fill with random bytes
     *
     * @throws NullPointerException
     *         if the byte array is null
     */
    public static void nextBytes(byte[] bytes)
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
     * @return the next pseudorandom, uniformly distributed {@code int} value from this random number generator's sequence
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
     * @param bound
     *         the upper bound (exclusive).  Must be positive.
     *
     * @return the next pseudorandom, uniformly distributed {@code int} value between zero (inclusive) and {@code bound} (exclusive) from this random number
     * generator's sequence
     *
     * @throws IllegalArgumentException
     *         if bound is not positive
     */
    public static int nextInt(int bound)
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
     * @return the next pseudorandom, uniformly distributed {@code long} value from this random number generator's sequence
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
     * @return the next pseudorandom, uniformly distributed {@code boolean} value from this random number generator's sequence
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
     * @return the next pseudorandom, uniformly distributed {@code float} value between {@code 0.0} and {@code 1.0} from this random number generator's sequence
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
     * @return the next pseudorandom, uniformly distributed {@code double} value between {@code 0.0} and {@code 1.0} from this random number generator's
     * sequence
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
     * @param streamSize
     *         the number of values to generate
     *
     * @return a stream of pseudorandom {@code int} values
     *
     * @throws IllegalArgumentException
     *         if {@code streamSize} is less than zero
     */
    public static IntStream ints(long streamSize)
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
     * @return a stream of pseudorandom {@code int} values </p> This method is implemented to be equivalent to {@code ints(Long.MAX_VALUE)}.
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
     * @param streamSize
     *         the number of values to generate
     * @param randomNumberOrigin
     *         the origin (inclusive) of each random value
     * @param randomNumberBound
     *         the bound (exclusive) of each random value
     *
     * @return a stream of pseudorandom {@code int} values, each with the given origin (inclusive) and bound (exclusive)
     *
     * @throws IllegalArgumentException
     *         if {@code streamSize} is less than zero, or {@code randomNumberOrigin} is greater than or equal to {@code randomNumberBound}
     */
    public static IntStream ints(long streamSize, int randomNumberOrigin, int randomNumberBound)
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
     * @param randomNumberOrigin
     *         the origin (inclusive) of each random value
     * @param randomNumberBound
     *         the bound (exclusive) of each random value
     *
     * @return a stream of pseudorandom {@code int} values, each with the given origin (inclusive) and bound (exclusive)
     *
     * @throws IllegalArgumentException
     *         if {@code randomNumberOrigin} is greater than or equal to {@code randomNumberBound} This method is implemented to be equivalent to {@code
     *         ints(Long.MAX_VALUE, randomNumberOrigin, randomNumberBound)}.
     */
    public static IntStream ints(int randomNumberOrigin, int randomNumberBound)
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
     * @param streamSize
     *         the number of values to generate
     *
     * @return a stream of pseudorandom {@code long} values
     *
     * @throws IllegalArgumentException
     *         if {@code streamSize} is less than zero
     */
    public static LongStream longs(long streamSize)
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
     * @return a stream of pseudorandom {@code long} values </p> This method is implemented to be equivalent to {@code longs(Long.MAX_VALUE)}.
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
     * @param streamSize
     *         the number of values to generate
     * @param randomNumberOrigin
     *         the origin (inclusive) of each random value
     * @param randomNumberBound
     *         the bound (exclusive) of each random value
     *
     * @return a stream of pseudorandom {@code long} values, each with the given origin (inclusive) and bound (exclusive)
     *
     * @throws IllegalArgumentException
     *         if {@code streamSize} is less than zero, or {@code randomNumberOrigin} is greater than or equal to {@code randomNumberBound}
     */
    public static LongStream longs(long streamSize, long randomNumberOrigin, long randomNumberBound)
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
     * @param randomNumberOrigin
     *         the origin (inclusive) of each random value
     * @param randomNumberBound
     *         the bound (exclusive) of each random value
     *
     * @return a stream of pseudorandom {@code long} values, each with the given origin (inclusive) and bound (exclusive)
     *
     * @throws IllegalArgumentException
     *         if {@code randomNumberOrigin} is greater than or equal to {@code randomNumberBound} This method is implemented to be equivalent to {@code
     *         longs(Long.MAX_VALUE, randomNumberOrigin, randomNumberBound)}.
     */
    public static LongStream longs(long randomNumberOrigin, long randomNumberBound)
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
     * @param streamSize
     *         the number of values to generate
     *
     * @return a stream of {@code double} values
     *
     * @throws IllegalArgumentException
     *         if {@code streamSize} is less than zero
     */
    public static DoubleStream doubles(long streamSize)
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
     * @return a stream of pseudorandom {@code double} values </p> This method is implemented to be equivalent to {@code doubles(Long.MAX_VALUE)}.
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
     * @param streamSize
     *         the number of values to generate
     * @param randomNumberOrigin
     *         the origin (inclusive) of each random value
     * @param randomNumberBound
     *         the bound (exclusive) of each random value
     *
     * @return a stream of pseudorandom {@code double} values, each with the given origin (inclusive) and bound (exclusive)
     *
     * @throws IllegalArgumentException
     *         if {@code streamSize} is less than zero
     * @throws IllegalArgumentException
     *         if {@code randomNumberOrigin} is greater than or equal to {@code randomNumberBound}
     */
    public static DoubleStream doubles(long streamSize, double randomNumberOrigin, double randomNumberBound)
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
     * @param randomNumberOrigin
     *         the origin (inclusive) of each random value
     * @param randomNumberBound
     *         the bound (exclusive) of each random value
     *
     * @return a stream of pseudorandom {@code double} values, each with the given origin (inclusive) and bound (exclusive)
     *
     * @throws IllegalArgumentException
     *         if {@code randomNumberOrigin} is greater than or equal to {@code randomNumberBound} This method is implemented to be equivalent to {@code
     *         doubles(Long.MAX_VALUE, randomNumberOrigin, randomNumberBound)}.
     */
    public static DoubleStream doubles(double randomNumberOrigin, double randomNumberBound)
    {
        return getRandom().doubles(randomNumberOrigin, randomNumberBound);
    }

    /**
     * Construct new random instance.
     *
     * @return created random instance.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Random & DioriteRandom> T newRandom()
    {
        return (T) new DioriteRandomImpl();
    }

    /**
     * Construct new random instance with given seed.
     *
     * @param seed
     *         seed of random instance.
     *
     * @return created random instance.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Random & DioriteRandom> T newRandom(long seed)
    {
        return (T) new DioriteRandomImpl(seed);
    }

    /**
     * Construct new random instance.
     *
     * @return created random instance.
     */
    @SuppressWarnings("unchecked")
    public static <T extends SecureRandom & DioriteSecureRandom> T newSecureRandom()
    {
        return (T) new DioriteSecureRandomImpl();
    }

    /**
     * Construct new random instance with given seed.
     *
     * @param seed
     *         seed of random instance.
     *
     * @return created random instance.
     */
    @SuppressWarnings("unchecked")
    public static <T extends SecureRandom & DioriteSecureRandom> T newSecureRandom(byte[] seed)
    {
        return (T) new DioriteSecureRandomImpl(seed);
    }

}
