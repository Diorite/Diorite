package org.diorite.utils.math;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.Validate;

public final class DioriteRandomUtils
{
    private static final Random random = new Random();

    private DioriteRandomUtils()
    {
    }

    public static <T> T getRand(final T[] array)
    {
        return getRand(random, array);
    }

    public static <T> T getRand(final List<T> coll)
    {
        return getRand(random, coll);
    }

    public static <T, E extends Collection<T>> E getRand(final Collection<T> coll, final E target, final int amount)
    {
        return getRand(random, coll, target, amount, true);
    }

    public static <T, E extends Collection<T>> E getRand(final Collection<T> coll, final E target, final int amount, final boolean noRepeat)
    {
        return getRand(random, coll, target, amount, noRepeat);
    }

    public static <T> T getRand(final Collection<T> coll)
    {
        return getRand(random, coll);
    }

    public static long getRandLongSafe(final long a, final long b)
    {
        return getRandLongSafe(random, a, b);
    }

    public static int getRandIntSafe(final int a, final int b)
    {
        return getRandIntSafe(random, a, b);
    }

    public static double getRandDoubleSafe(final double a, final double b)
    {
        return getRandDoubleSafe(random, a, b);
    }

    public static float getRandFloatSafe(final float a, final float b)
    {
        return getRandFloatSafe(random, a, b);
    }

    public static long getRandLong(final long min, final long max) throws IllegalArgumentException
    {
        return getRandLong(random, min, max);
    }

    public static int getRandInt(final int min, final int max) throws IllegalArgumentException
    {
        return getRandInt(random, min, max);
    }

    public static double getRandDouble(final double min, final double max) throws IllegalArgumentException
    {
        return getRandDouble(random, min, max);
    }

    public static float getRandFloat(final float min, final float max) throws IllegalArgumentException
    {
        return getRandFloat(random, min, max);
    }

    public static boolean getChance(final double chance)
    {
        return getChance(random, chance);
    }


    // custom random
    public static <T> T getRand(final Random random, final T[] array)
    {
        if (array.length == 0)
        {
            return null;
        }
        return array[random.nextInt(array.length)];
    }

    public static <T> T getRand(final Random random, final List<T> coll)
    {
        return coll.get(random.nextInt(coll.size()));
    }

    public static <T, E extends Collection<T>> E getRand(final Random random, final Collection<T> coll, final E target, final int amount)
    {
        return getRand(random, coll, target, amount, true);
    }

    public static <T, E extends Collection<T>> E getRand(final Random random, final Collection<T> coll, final E target, int amount, final boolean noRepeat)
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

    public static <T> T getRand(final Random random, final Collection<T> coll)
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

    public static long getRandLongSafe(final Random random, final long a, final long b)
    {
        if (a > b)
        {
            return getRandLong(random, b, a);
        }
        return getRandLong(random, a, b);
    }

    public static int getRandIntSafe(final Random random, final int a, final int b)
    {
        return (int) getRandLongSafe(random, a, b);
    }

    public static double getRandDoubleSafe(final Random random, final double a, final double b)
    {
        if (a > b)
        {
            return getRandDouble(random, b, a);
        }
        return getRandDouble(random, a, b);
    }

    public static float getRandFloatSafe(final Random random, final float a, final float b)
    {
        if (a > b)
        {
            return getRandFloat(random, b, a);
        }
        return getRandFloat(random, a, b);
    }

    public static long getRandLong(final Random random, final long min, final long max) throws IllegalArgumentException
    {
        if (min == max)
        {
            return max;
        }
        Validate.isTrue(max > min, "Max can't be smaller than min!");
        return (Math.abs(random.nextLong()) % ((max - min) + 1)) + min;
    }

    public static int getRandInt(final Random random, final int min, final int max) throws IllegalArgumentException
    {
        if (min == max)
        {
            return max;
        }
        Validate.isTrue(max > min, "Max can't be smaller than min!");
        return (int) getRandLong(random, min, max);
    }

    public static double getRandDouble(final Random random, final double min, final double max) throws IllegalArgumentException
    {
        if (Double.compare(min, max) == 0)
        {
            return max;
        }
        Validate.isTrue(max > min, "Max can't be smaller than min!");
        return (random.nextDouble() * (max - min)) + min;
    }

    public static float getRandFloat(final Random random, final float min, final float max) throws IllegalArgumentException
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
        return (chance > 0) && ((chance >= 100) || (chance >= getRandDouble(random, 0, 100)));
    }
}
