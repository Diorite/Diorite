package diorite.utils;

import java.util.Random;

import org.apache.commons.lang3.Validate;

@SuppressWarnings("MagicNumber")
public final class DioriteMathUtils
{
    private static final Random random = new Random();

    private DioriteMathUtils()
    {
    }

    public static byte getInRange(final byte value, final byte min, final byte max)
    {
        if (value > max)
        {
            return max;
        }
        if (value < min)
        {
            return min;
        }
        return value;
    }

    public static short getInRange(final short value, final short min, final short max)
    {
        if (value > max)
        {
            return max;
        }
        if (value < min)
        {
            return min;
        }
        return value;
    }

    public static int getInRange(final int value, final int min, final int max)
    {
        if (value > max)
        {
            return max;
        }
        if (value < min)
        {
            return min;
        }
        return value;
    }

    public static long getInRange(final long value, final long min, final long max)
    {
        if (value > max)
        {
            return max;
        }
        if (value < min)
        {
            return min;
        }
        return value;
    }

    public static float getInRange(final float value, final float min, final float max)
    {
        if (value > max)
        {
            return max;
        }
        if (value < min)
        {
            return min;
        }
        return value;
    }

    public static double getInRange(final double value, final double min, final double max)
    {
        if (value > max)
        {
            return max;
        }
        if (value < min)
        {
            return min;
        }
        return value;
    }

    public static byte getInRangeOrDefault(final byte value, final byte min, final byte max, final byte def)
    {
        if (value > max)
        {
            return def;
        }
        if (value < min)
        {
            return def;
        }
        return value;
    }

    public static short getInRangeOrDefault(final short value, final short min, final short max, final short def)
    {
        if (value > max)
        {
            return def;
        }
        if (value < min)
        {
            return def;
        }
        return value;
    }

    public static int getInRangeOrDefault(final int value, final int min, final int max, final int def)
    {
        if (value > max)
        {
            return def;
        }
        if (value < min)
        {
            return def;
        }
        return value;
    }

    public static long getInRangeOrDefault(final long value, final long min, final long max, final long def)
    {
        if (value > max)
        {
            return def;
        }
        if (value < min)
        {
            return def;
        }
        return value;
    }

    public static float getInRangeOrDefault(final float value, final float min, final float max, final float def)
    {
        if (value > max)
        {
            return def;
        }
        if (value < min)
        {
            return def;
        }
        return value;
    }

    public static double getInRangeOrDefault(final double value, final double min, final double max, final double def)
    {
        if (value > max)
        {
            return def;
        }
        if (value < min)
        {
            return def;
        }
        return value;
    }

    public static boolean canBeByte(final int i)
    {
        return (i >= Byte.MIN_VALUE) && (i <= Byte.MAX_VALUE);
    }

    public static boolean canBeShort(final int i)
    {
        return (i >= Short.MIN_VALUE) && (i <= Short.MAX_VALUE);
    }

    public static boolean canBeByte(final long i)
    {
        return (i >= Byte.MIN_VALUE) && (i <= Byte.MAX_VALUE);
    }

    public static boolean canBeShort(final long i)
    {
        return (i >= Short.MIN_VALUE) && (i <= Short.MAX_VALUE);
    }

    public static boolean canBeByte(final short i)
    {
        return (i >= Byte.MIN_VALUE) && (i <= Byte.MAX_VALUE);
    }

    public static boolean canBeInt(final long i)
    {
        return (i >= Integer.MIN_VALUE) && (i <= Integer.MAX_VALUE);
    }

    public static boolean canBeLong(final float i)
    {
        return (i >= Long.MIN_VALUE) && (i <= Long.MAX_VALUE);
    }

    public static boolean canBeInt(final float i)
    {
        return (i >= Integer.MIN_VALUE) && (i <= Integer.MAX_VALUE);
    }

    public static boolean canBeShort(final float i)
    {
        return (i >= Short.MIN_VALUE) && (i <= Short.MAX_VALUE);
    }

    public static boolean canBeByte(final float i)
    {
        return (i >= Byte.MIN_VALUE) && (i <= Byte.MAX_VALUE);
    }

    public static boolean canBeLong(final double i)
    {
        return (i >= Long.MIN_VALUE) && (i <= Long.MAX_VALUE);
    }

    public static boolean canBeInt(final double i)
    {
        return (i >= Integer.MIN_VALUE) && (i <= Integer.MAX_VALUE);
    }

    public static boolean canBeShort(final double i)
    {
        return (i >= Short.MIN_VALUE) && (i <= Short.MAX_VALUE);
    }

    public static boolean canBeByte(final double i)
    {
        return (i >= Byte.MIN_VALUE) && (i <= Byte.MAX_VALUE);
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
        Validate.isTrue(max > min, "Max can't be smaller than min!");
        return (Math.abs(random.nextLong()) % ((max - min) + 1)) + min;
    }

    public static int getRandInt(final int min, final int max) throws IllegalArgumentException
    {
        Validate.isTrue(max > min, "Max can't be smaller than min!");
        return (int) getRandLong(min, max);
    }

    public static double getRandDouble(final double min, final double max) throws IllegalArgumentException
    {
        Validate.isTrue(max > min, "Max can't be smaller than min!");
        return (random.nextDouble() * (max - min)) + min;
    }

    public static float getRandFloat(final float min, final float max) throws IllegalArgumentException
    {
        Validate.isTrue(max > min, "Max can't be smaller than min!");
        return (random.nextFloat() * (max - min)) + min;
    }

    public static boolean getChance(final double chance)
    {
        return (chance > 0) && ((chance >= 100) || (chance >= getRandDouble(0, 100)));
    }

    public static int floor(final double num)
    {
        final int floor = (int) num;
        return (floor == num) ? floor : (floor - (int) (Double.doubleToRawLongBits(num) >>> 63));
    }

    public static int ceil(final double num)
    {
        final int floor = (int) num;
        return (floor == num) ? floor : (floor + (int) (~ Double.doubleToRawLongBits(num) >>> 63));
    }

    public static int round(final double num)
    {
        return floor(num + 0.5d);
    }

    public static double square(final double num)
    {
        return num * num;
    }

    public static byte countBits(long num)
    {
        byte result;
        for (result = 0; num > 0; result++)
        {
            num &= num - 1;
        }
        return result;
    }

    public static byte countBits(int num)
    {
        byte result;
        for (result = 0; num > 0; result++)
        {
            num &= num - 1;
        }
        return result;
    }

    public static byte countBits(short num)
    {
        byte result;
        for (result = 0; num > 0; result++)
        {
            num &= num - 1;
        }
        return result;
    }

    public static byte countBits(char num)
    {
        byte result;
        for (result = 0; num > 0; result++)
        {
            num &= num - 1;
        }
        return result;
    }

    public static byte countBits(byte num)
    {
        byte result;
        for (result = 0; num > 0; result++)
        {
            num &= num - 1;
        }
        return result;
    }

    public static boolean isBetweenInclusive(final long min, final long i, final long max)
    {
        return (i >= min) && (i <= max);
    }

    public static boolean isBetweenExclusive(final long min, final long i, final long max)
    {
        return (i > min) && (i < max);
    }

    public static boolean isBetweenInclusive(final double min, final double i, final double max)
    {
        return (i >= min) && (i <= max);
    }

    public static boolean isBetweenExclusive(final double min, final double i, final double max)
    {
        return (i > min) && (i < max);
    }
}
