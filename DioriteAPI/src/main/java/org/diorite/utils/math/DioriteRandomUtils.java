package org.diorite.utils.math;

import java.util.Random;

import org.apache.commons.lang3.Validate;

public final class DioriteRandomUtils
{
    private static final Random random = new Random();

    private DioriteRandomUtils()
    {
    }

    public static long getRandLongSafe(final long a, final long b)
    {
        if (a > b)
        {
            return getRandLong(b, a);
        }
        return getRandLong(a, b);
    }

    public static int getRandIntSafe(final int a, final int b)
    {
        return (int) getRandLongSafe(a, b);
    }

    public static double getRandDoubleSafe(final double a, final double b)
    {
        if (a > b)
        {
            return getRandDouble(b, a);
        }
        return getRandDouble(a, b);
    }

    public static float getRandFloatSafe(final float a, final float b)
    {
        if (a > b)
        {
            return getRandFloat(b, a);
        }
        return getRandFloat(a, b);
    }

    public static long getRandLong(final long min, final long max) throws IllegalArgumentException
    {
        if (min == max)
        {
            return max;
        }
        Validate.isTrue(max > min, "Max can't be smaller than min!");
        return (Math.abs(random.nextLong()) % ((max - min) + 1)) + min;
    }

    public static int getRandInt(final int min, final int max) throws IllegalArgumentException
    {
        if (min == max)
        {
            return max;
        }
        Validate.isTrue(max > min, "Max can't be smaller than min!");
        return (int) getRandLong(min, max);
    }

    public static double getRandDouble(final double min, final double max) throws IllegalArgumentException
    {
        if (Double.compare(min, max) == 0)
        {
            return max;
        }
        Validate.isTrue(max > min, "Max can't be smaller than min!");
        return (random.nextDouble() * (max - min)) + min;
    }

    public static float getRandFloat(final float min, final float max) throws IllegalArgumentException
    {
        if (Float.compare(min, max) == 0)
        {
            return max;
        }
        Validate.isTrue(max > min, "Max can't be smaller than min!");
        return (random.nextFloat() * (max - min)) + min;
    }

    public static boolean getChance(final double chance)
    {
        return (chance > 0) && ((chance >= 100) || (chance >= getRandDouble(0, 100)));
    }
}
