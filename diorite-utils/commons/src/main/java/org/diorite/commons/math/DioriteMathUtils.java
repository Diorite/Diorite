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

package org.diorite.commons.math;

import javax.annotation.Nullable;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;

/**
 * Class with some simple math utils/methods.
 */
@SuppressWarnings("MagicNumber")
public final class DioriteMathUtils
{
    public static final float  F_PI                = (float) Math.PI;
    public static final int    CIRCLE_DEGREES      = 360;
    public static final int    HALF_CIRCLE_DEGREES = CIRCLE_DEGREES / 2;
    public static final double ONE_OF_10           = 1D / 10D;
    public static final double ONE_OF_100          = 1D / 100D;
    public static final float  F_ONE_OF_10         = 1F / 10F;
    public static final float  F_ONE_OF_100        = 1F / 100F;

    private static final int     SIN_CACHE_SIZE = 0xFFFF;
    private static final float   UNKNOWN_CONST  = 10430.378F;
    private static final float[] sinCache       = new float[SIN_CACHE_SIZE];

    @SuppressWarnings("MagicNumber")
    public static int varintSize(int i)
    {
        if ((i < 0) || (i >= 268435456))
        {
            return 5;
        }
        if (i < 128)
        {
            return 1;
        }
        if (i < 16384)
        {
            return 2;
        }
        if (i < 2097152)
        {
            return 3;
        }
        if (i < 268435456)
        {
            return 4;
        }
        throw new AssertionError();
    }

    static
    {
        for (int i = 0; i < SIN_CACHE_SIZE; i++)
        {
            sinCache[i] = ((float) Math.sin((i * Math.PI * 2.0D) / ((double) (SIN_CACHE_SIZE + 1))));
        }
    }

    private DioriteMathUtils()
    {
    }

    private static final NumberFormat simpleFormat          = new DecimalFormat("##0.##");
    private static final NumberFormat simpleFormatForceZero = new DecimalFormat("##0.00");

    /**
     * Faster sin method using special cache, based on Mojang code.
     *
     * @param angle
     *         angle.
     *
     * @return sin of given angle.
     */
    public static float sin(float angle)
    {
        return sinCache[((int) (angle * UNKNOWN_CONST) & SIN_CACHE_SIZE)];
    }

    /**
     * Faster cos method using special cache, based on Mojang code.
     *
     * @param angle
     *         angle.
     *
     * @return cos of given angle.
     */
    public static float cos(float angle)
    {
        return sinCache[((int) ((angle * UNKNOWN_CONST) + ((SIN_CACHE_SIZE + 1) / 4)) & SIN_CACHE_SIZE)];
    }

    /**
     * Change tps to number of milliseconds per tick, 1000/tps.
     *
     * @param tps
     *         tick per second.
     *
     * @return number of milliseconds per tick.
     */
    public static int millisecondsPerTick(int tps)
    {
        return 1000 / tps;
    }

    /**
     * Change tps to number of centiseconds per tick, 100/tps.
     *
     * @param tps
     *         tick per second.
     *
     * @return number of centiseconds per tick.
     */
    public static int centisecondsPerTick(int tps)
    {
        return 100 / tps;
    }

    /**
     * returns string from double formatted to DecimalFormat("###.##")
     *
     * @param d
     *         value to format.
     *
     * @return formatted string.
     */
    public static String formatSimpleDecimal(double d)
    {
        return simpleFormat.format(d);
    }

    /**
     * returns string from double formatted to DecimalFormat("###.00")
     *
     * @param d
     *         value to format.
     *
     * @return formatted string.
     */
    public static String formatSimpleDecimalWithZeros(double d)
    {
        return simpleFormatForceZero.format(d);
    }

    /**
     * Convert given int to String with roman number.
     * Examples:<br>
     * {@code 10 -> X}<br>
     * {@code 99 -> IC}<br>
     * {@code 30 -> XXX}<br>
     * {@code 80 -> LXXX}<br>
     * {@code 1910 -> MCMX}<br>
     * {@code 1903 -> MCMIII}<br>
     *
     * @param i
     *         int to convert.
     *
     * @return roman number in string
     */
    public static String toRoman(int i)
    {
        return RomanNumeral.toString(i);
    }

    /**
     * Convert given string with roman number to int.
     * Examples:<br>
     * {@code X -> 10}<br>
     * {@code LC -> 99}<br>
     * {@code XXX -> 30}<br>
     * {@code LXXX -> 80}<br>
     * {@code MCMX -> 1910}<br>
     * {@code MCMIII -> 1903}<br>
     * <br>
     * It can also convert not really valid numbers, like:
     * {@code IIIIIX -> 5}<br>
     * {@code MDCCCCX -> 1910}<br>
     * {@code MDCDIII -> 1903}<br>
     *
     * @param roman
     *         roman numver
     *
     * @return int value of roman number.
     *
     * @throws NumberFormatException
     *         if string isn't valid roman number.
     * @see DioriteMathUtils#toRoman(int)
     */
    public static int fromRoman(String roman) throws NumberFormatException
    {
        return RomanNumeral.toInt(roman);
    }

    /**
     * Return given number if it is in range, or closest value in range.
     * {@code value > max -> max}<br>
     * {@code value < min -> min}<br>
     * {@code else -> value}<br>
     *
     * @param value
     *         number to validate.
     * @param min
     *         min value in range. (inclusive)
     * @param max
     *         max value in range. (inclusive)
     *
     * @return closest number in range.
     */
    public static byte getInRange(byte value, byte min, byte max)
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

    /**
     * Return given number if it is in range, or closest value in range.
     * {@code value > max -> max}<br>
     * {@code value < min -> min}<br>
     * {@code else -> value}<br>
     *
     * @param value
     *         number to validate.
     * @param min
     *         min value in range. (inclusive)
     * @param max
     *         max value in range. (inclusive)
     *
     * @return closest number in range.
     */
    public static short getInRange(short value, short min, short max)
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

    /**
     * Return given number if it is in range, or closest value in range.
     * {@code value > max -> max}<br>
     * {@code value < min -> min}<br>
     * {@code else -> value}<br>
     *
     * @param value
     *         number to validate.
     * @param min
     *         min value in range. (inclusive)
     * @param max
     *         max value in range. (inclusive)
     *
     * @return closest number in range.
     */
    public static int getInRange(int value, int min, int max)
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

    /**
     * Return given number if it is in range, or closest value in range.
     * {@code value > max -> max}<br>
     * {@code value < min -> min}<br>
     * {@code else -> value}<br>
     *
     * @param value
     *         number to validate.
     * @param min
     *         min value in range. (inclusive)
     * @param max
     *         max value in range. (inclusive)
     *
     * @return closest number in range.
     */
    public static long getInRange(long value, long min, long max)
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

    /**
     * Return given number if it is in range, or closest value in range.
     * {@code value > max -> max}<br>
     * {@code value < min -> min}<br>
     * {@code else -> value}<br>
     *
     * @param value
     *         number to validate.
     * @param min
     *         min value in range. (inclusive)
     * @param max
     *         max value in range. (inclusive)
     *
     * @return closest number in range.
     */
    public static float getInRange(float value, float min, float max)
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

    /**
     * Return given number if it is in range, or closest value in range.
     * {@code value > max -> max}<br>
     * {@code value < min -> min}<br>
     * {@code else -> value}<br>
     *
     * @param value
     *         number to validate.
     * @param min
     *         min value in range. (inclusive)
     * @param max
     *         max value in range. (inclusive)
     *
     * @return closest number in range.
     */
    public static double getInRange(double value, double min, double max)
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

    /**
     * Return given number if it is in range, or default value.
     * {@code value > max -> def}<br>
     * {@code value < min -> def}<br>
     * {@code else -> value}<br>
     *
     * @param value
     *         number to validate.
     * @param min
     *         min value in range. (inclusive)
     * @param max
     *         max value in range. (inclusive)
     * @param def
     *         default value.
     *
     * @return given number or default value.
     */
    public static byte getInRangeOrDefault(byte value, byte min, byte max, byte def)
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

    /**
     * Return given number if it is in range, or default value.
     * {@code value > max -> def}<br>
     * {@code value < min -> def}<br>
     * {@code else -> value}<br>
     *
     * @param value
     *         number to validate.
     * @param min
     *         min value in range. (inclusive)
     * @param max
     *         max value in range. (inclusive)
     * @param def
     *         default value.
     *
     * @return given number or default value.
     */
    public static short getInRangeOrDefault(short value, short min, short max, short def)
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

    /**
     * Return given number if it is in range, or default value.
     * {@code value > max -> def}<br>
     * {@code value < min -> def}<br>
     * {@code else -> value}<br>
     *
     * @param value
     *         number to validate.
     * @param min
     *         min value in range. (inclusive)
     * @param max
     *         max value in range. (inclusive)
     * @param def
     *         default value.
     *
     * @return given number or default value.
     */
    public static int getInRangeOrDefault(int value, int min, int max, int def)
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

    /**
     * Return given number if it is in range, or default value.
     * {@code value > max -> def}<br>
     * {@code value < min -> def}<br>
     * {@code else -> value}<br>
     *
     * @param value
     *         number to validate.
     * @param min
     *         min value in range. (inclusive)
     * @param max
     *         max value in range. (inclusive)
     * @param def
     *         default value.
     *
     * @return given number or default value.
     */
    public static long getInRangeOrDefault(long value, long min, long max, long def)
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

    /**
     * Return given number if it is in range, or default value.
     * {@code value > max -> def}<br>
     * {@code value < min -> def}<br>
     * {@code else -> value}<br>
     *
     * @param value
     *         number to validate.
     * @param min
     *         min value in range. (inclusive)
     * @param max
     *         max value in range. (inclusive)
     * @param def
     *         default value.
     *
     * @return given number or default value.
     */
    public static float getInRangeOrDefault(float value, float min, float max, float def)
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

    /**
     * Return given number if it is in range, or default value.
     * {@code value > max -> def}<br>
     * {@code value < min -> def}<br>
     * {@code else -> value}<br>
     *
     * @param value
     *         number to validate.
     * @param min
     *         min value in range. (inclusive)
     * @param max
     *         max value in range. (inclusive)
     * @param def
     *         default value.
     *
     * @return given number or default value.
     */
    public static double getInRangeOrDefault(double value, double min, double max, double def)
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

    /**
     * Check in number is in between {@link Byte#MIN_VALUE} and {@link Byte#MAX_VALUE}
     *
     * @param i
     *         number to validate.
     *
     * @return true if it is in range.
     */
    public static boolean canBeByte(int i)
    {
        return (i >= Byte.MIN_VALUE) && (i <= Byte.MAX_VALUE);
    }

    /**
     * Check in number is in between {@link Short#MIN_VALUE} and {@link Short#MAX_VALUE}
     *
     * @param i
     *         number to validate.
     *
     * @return true if it is in range.
     */
    public static boolean canBeShort(int i)
    {
        return (i >= Short.MIN_VALUE) && (i <= Short.MAX_VALUE);
    }

    /**
     * Check in number is in between {@link Byte#MIN_VALUE} and {@link Byte#MAX_VALUE}
     *
     * @param i
     *         number to validate.
     *
     * @return true if it is in range.
     */
    public static boolean canBeByte(long i)
    {
        return (i >= Byte.MIN_VALUE) && (i <= Byte.MAX_VALUE);
    }

    /**
     * Check in number is in between {@link Short#MIN_VALUE} and {@link Short#MAX_VALUE}
     *
     * @param i
     *         number to validate.
     *
     * @return true if it is in range.
     */
    public static boolean canBeShort(long i)
    {
        return (i >= Short.MIN_VALUE) && (i <= Short.MAX_VALUE);
    }

    /**
     * Check in number is in between {@link Byte#MIN_VALUE} and {@link Byte#MAX_VALUE}
     *
     * @param i
     *         number to validate.
     *
     * @return true if it is in range.
     */
    public static boolean canBeByte(short i)
    {
        return (i >= Byte.MIN_VALUE) && (i <= Byte.MAX_VALUE);
    }

    /**
     * Check in number is in between {@link Integer#MIN_VALUE} and {@link Integer#MAX_VALUE}
     *
     * @param i
     *         number to validate.
     *
     * @return true if it is in range.
     */
    public static boolean canBeInt(long i)
    {
        return (i >= Integer.MIN_VALUE) && (i <= Integer.MAX_VALUE);
    }

    /**
     * Check in number is in between {@link Long#MIN_VALUE} and {@link Long#MAX_VALUE}
     *
     * @param i
     *         number to validate.
     *
     * @return true if it is in range.
     */
    public static boolean canBeLong(float i)
    {
        return (i >= Long.MIN_VALUE) && (i <= Long.MAX_VALUE);
    }

    /**
     * Check in number is in between {@link Integer#MIN_VALUE} and {@link Integer#MAX_VALUE}
     *
     * @param i
     *         number to validate.
     *
     * @return true if it is in range.
     */
    public static boolean canBeInt(float i)
    {
        return (i >= Integer.MIN_VALUE) && (i <= Integer.MAX_VALUE);
    }

    /**
     * Check in number is in between {@link Short#MIN_VALUE} and {@link Short#MAX_VALUE}
     *
     * @param i
     *         number to validate.
     *
     * @return true if it is in range.
     */
    public static boolean canBeShort(float i)
    {
        return (i >= Short.MIN_VALUE) && (i <= Short.MAX_VALUE);
    }

    /**
     * Check in number is in between {@link Byte#MIN_VALUE} and {@link Byte#MAX_VALUE}
     *
     * @param i
     *         number to validate.
     *
     * @return true if it is in range.
     */
    public static boolean canBeByte(float i)
    {
        return (i >= Byte.MIN_VALUE) && (i <= Byte.MAX_VALUE);
    }

    /**
     * Check in number is in between {@link Long#MIN_VALUE} and {@link Long#MAX_VALUE}
     *
     * @param i
     *         number to validate.
     *
     * @return true if it is in range.
     */
    public static boolean canBeLong(double i)
    {
        return (i >= Long.MIN_VALUE) && (i <= Long.MAX_VALUE);
    }

    /**
     * Check in number is in between {@link Integer#MIN_VALUE} and {@link Integer#MAX_VALUE}
     *
     * @param i
     *         number to validate.
     *
     * @return true if it is in range.
     */
    public static boolean canBeInt(double i)
    {
        return (i >= Integer.MIN_VALUE) && (i <= Integer.MAX_VALUE);
    }

    /**
     * Check in number is in between {@link Short#MIN_VALUE} and {@link Short#MAX_VALUE}
     *
     * @param i
     *         number to validate.
     *
     * @return true if it is in range.
     */
    public static boolean canBeShort(double i)
    {
        return (i >= Short.MIN_VALUE) && (i <= Short.MAX_VALUE);
    }

    /**
     * Check in number is in between {@link Byte#MIN_VALUE} and {@link Byte#MAX_VALUE}
     *
     * @param i
     *         number to validate.
     *
     * @return true if it is in range.
     */
    public static boolean canBeByte(double i)
    {
        return (i >= Byte.MIN_VALUE) && (i <= Byte.MAX_VALUE);
    }

    /**
     * Round down given number.
     *
     * @param num
     *         number to round down.
     *
     * @return rounded number.
     *
     * @see Math#round(double)
     */
    public static int floor(double num)
    {
        int floor = (int) num;
        return (floor == num) ? floor : ((num > 0) ? floor : (floor - 1));
    }

    /**
     * Round up given number.
     *
     * @param num
     *         number to round up.
     *
     * @return rounded number.
     *
     * @see Math#round(double)
     */
    public static int ceil(double num)
    {
        int ceil = (int) num;
        return (ceil == num) ? ceil : ((num > 0) ? (ceil + 1) : ceil);
    }

    /**
     * Round down given number.
     *
     * @param num
     *         number to round down.
     *
     * @return rounded number.
     *
     * @see Math#round(double)
     */
    public static int floor(float num)
    {
        int floor = (int) num;
        return (floor == num) ? floor : ((num > 0) ? floor : (floor - 1));
    }

    /**
     * Round up given number.
     *
     * @param num
     *         number to round up.
     *
     * @return rounded number.
     *
     * @see Math#round(double)
     */
    public static int ceil(float num)
    {
        int ceil = (int) num;
        return (ceil == num) ? ceil : ((num > 0) ? (ceil + 1) : ceil);
    }

    /**
     * Round given number.
     *
     * @param num
     *         number to round.
     *
     * @return rounded number.
     *
     * @see Math#round(double)
     */
    public static int round(double num)
    {
        return floor(num + 0.5d);
    }

    /**
     * Simple square number, just num * num.
     *
     * @param num
     *         number to square
     *
     * @return num * num
     */
    public static double square(double num)
    {
        return num * num;
    }

    /**
     * Simple square number, just num * num.
     *
     * @param num
     *         number to square
     *
     * @return num * num
     */
    public static float square(float num)
    {
        return num * num;
    }

    /**
     * Simple square number, just num * num.
     *
     * @param num
     *         number to square
     *
     * @return num * num
     */
    public static int square(int num)
    {
        return num * num;
    }

    /**
     * Simple square number, just num * num.
     *
     * @param num
     *         number to square
     *
     * @return num * num
     */
    public static long square(long num)
    {
        return num * num;
    }

    /**
     * count 1 bits in nubmer.
     * <br>
     * {@literal 10: 1010 -> 2}<br>
     * {@literal 2: 10 -> 1}<br>
     * {@literal 56: 111000 -> 3}<br>
     * {@literal 34: 100010 -> 2}<br>
     * {@literal 255: 11111111 -> 8}<br>
     * {@literal 256: 100000000 -> 1}<br>
     *
     * @param num
     *         number to count bits in it.
     *
     * @return number of 1 bits.
     */
    public static byte countBits(long num)
    {
        byte result;
        for (result = 0; Math.abs(num) > 0; result++)
        {
            num &= num - 1;
        }
        return result;
    }

    /**
     * count 1 bits in nubmer.
     * <br>
     * {@literal 10: 1010 -> 2}<br>
     * {@literal 2: 10 -> 1}<br>
     * {@literal 56: 111000 -> 3}<br>
     * {@literal 34: 100010 -> 2}<br>
     * {@literal 255: 11111111 -> 8}<br>
     * {@literal 256: 100000000 -> 1}<br>
     *
     * @param num
     *         number to count bits in it.
     *
     * @return number of 1 bits.
     */
    public static byte countBits(int num)
    {
        byte result;
        for (result = 0; Math.abs(num) > 0; result++)
        {
            num &= num - 1;
        }
        return result;
    }

    /**
     * count 1 bits in nubmer.
     * <br>
     * {@literal 10: 1010 -> 2}<br>
     * {@literal 2: 10 -> 1}<br>
     * {@literal 56: 111000 -> 3}<br>
     * {@literal 34: 100010 -> 2}<br>
     * {@literal 255: 11111111 -> 8}<br>
     * {@literal 256: 100000000 -> 1}<br>
     *
     * @param num
     *         number to count bits in it.
     *
     * @return number of 1 bits.
     */
    public static byte countBits(short num)
    {
        byte result;
        for (result = 0; num > 0; result++)
        {
            num &= num - 1;
        }
        return result;
    }

    /**
     * count 1 bits in nubmer.
     * <br>
     * {@literal 10: 1010 -> 2}<br>
     * {@literal 2: 10 -> 1}<br>
     * {@literal 56: 111000 -> 3}<br>
     * {@literal 34: 100010 -> 2}<br>
     * {@literal 255: 11111111 -> 8}<br>
     * {@literal 256: 100000000 -> 1}<br>
     *
     * @param num
     *         number to count bits in it.
     *
     * @return number of 1 bits.
     */
    public static byte countBits(char num)
    {
        byte result;
        for (result = 0; num > 0; result++)
        {
            num &= num - 1;
        }
        return result;
    }

    /**
     * count 1 bits in nubmer.
     * <br>
     * {@literal 10: 1010 -> 2}<br>
     * {@literal 2: 10 -> 1}<br>
     * {@literal 56: 111000 -> 3}<br>
     * {@literal 34: 100010 -> 2}<br>
     * {@literal 255: 11111111 -> 8}<br>
     * {@literal 256: 100000000 -> 1}<br>
     *
     * @param num
     *         number to count bits in it.
     *
     * @return number of 1 bits.
     */
    public static byte countBits(byte num)
    {
        byte result;
        for (result = 0; num > 0; result++)
        {
            num &= num - 1;
        }
        return result;
    }

    /**
     * Check if given numver is in given range.
     *
     * @param min
     *         min value of range (inclusive).
     * @param i
     *         number to validate.
     * @param max
     *         max value of range (inclusive).
     *
     * @return true if it is in range.
     */
    public static boolean isBetweenInclusive(long min, long i, long max)
    {
        return (i >= min) && (i <= max);
    }

    /**
     * Check if given numver is in given range.
     *
     * @param min
     *         min value of range (exclusive).
     * @param i
     *         number to validate.
     * @param max
     *         max value of range (exclusive).
     *
     * @return true if it is in range.
     */
    public static boolean isBetweenExclusive(long min, long i, long max)
    {
        return (i > min) && (i < max);
    }

    /**
     * Check if given numver is in given range.
     *
     * @param min
     *         min value of range (inclusive).
     * @param i
     *         number to validate.
     * @param max
     *         max value of range (inclusive).
     *
     * @return true if it is in range.
     */
    public static boolean isBetweenInclusive(double min, double i, double max)
    {
        return (i >= min) && (i <= max);
    }

    /**
     * Check if given numver is in given range.
     *
     * @param min
     *         min value of range (exclusive).
     * @param i
     *         number to validate.
     * @param max
     *         max value of range (exclusive).
     *
     * @return true if it is in range.
     */
    public static boolean isBetweenExclusive(double min, double i, double max)
    {
        return (i > min) && (i < max);
    }

    /**
     * Parse string to int, if string can't be parsed to int, then it will return null. <br>
     * Based on {@link Integer#parseInt(String)}
     *
     * @param str
     *         string to parse
     *
     * @return parsed value or null.
     */
    @SuppressWarnings("Duplicates")
    @Nullable
    public static Integer asInt(String str)
    {
        int result = 0;
        boolean negative = false;
        int i = 0;
        int len = str.length();
        int limit = - Integer.MAX_VALUE;
        int multmin;
        int digit;

        if (len > 11) // integer number can't have more than 11 chars -> -2 147 483 648
        {
            return null;
        }
        if (len > 0)
        {
            char firstChar = str.charAt(0);
            if (firstChar < '0')
            { // Possible leading "+" or "-"
                if (firstChar == '-')
                {
                    negative = true;
                    limit = Integer.MIN_VALUE;
                }
                else if (firstChar != '+')
                {
                    return null;
                }

                if (len == 1) // Cannot have lone "+" or "-"
                {
                    return null;
                }
                i++;
            }
            multmin = limit / 10;
            while (i < len)
            {
                // Accumulating negatively avoids surprises near MAX_VALUE
                char digitChar = str.charAt(i++);
                if ((digitChar > '9') || (digitChar < '0'))
                {
                    return null;
                }
                digit = digitChar - '0';
                if (result < multmin)
                {
                    return null;
                }
                result *= 10;
                if (result < (limit + digit))
                {
                    return null;
                }
                result -= digit;
            }
        }
        else
        {
            return null;
        }
        return negative ? result : - result;
    }

    /**
     * Parse string to long, if string can't be parsed to long, then it will return null. <br>
     * Based on {@link Long#parseLong(String)}
     *
     * @param str
     *         string to parse
     *
     * @return parsed value or null.
     */
    @SuppressWarnings("Duplicates")
    @Nullable
    public static Long asLong(String str)
    {
        long result = 0;
        boolean negative = false;
        int i = 0;
        int len = str.length();
        long limit = - Long.MAX_VALUE;
        long multmin;
        int digit;

        if (len > 20) // long number can't have more than 11 chars -> -9 223 372 036 854 775 808
        {
            return null;
        }
        if (len > 0)
        {
            char firstChar = str.charAt(0);
            if (firstChar < '0')
            { // Possible leading "+" or "-"
                if (firstChar == '-')
                {
                    negative = true;
                    limit = Long.MIN_VALUE;
                }
                else if (firstChar != '+')
                {
                    return null;
                }

                if (len == 1) // Cannot have lone "+" or "-"
                {
                    return null;
                }
                i++;
            }
            multmin = limit / 10;
            while (i < len)
            {
                // Accumulating negatively avoids surprises near MAX_VALUE
                char digitChar = str.charAt(i++);
                if ((digitChar > '9') || (digitChar < '0'))
                {
                    return null;
                }
                digit = digitChar - '0';
                if (result < multmin)
                {
                    return null;
                }
                result *= 10;
                if (result < (limit + digit))
                {
                    return null;
                }
                result -= digit;
            }
        }
        else
        {
            return null;
        }
        return negative ? result : - result;
    }

    /**
     * Parse string to double, if string can't be parsed to double, then it will return null.
     *
     * @param str
     *         string to parse
     *
     * @return parsed value or null.
     */
    @Nullable
    public static Double asDouble(String str)
    {
        try
        {
            return Double.valueOf(str);
        }
        catch (NumberFormatException e)
        {
            return null;
        }
    }

    /**
     * Parse string to float, if string can't be parsed to float, then it will return null.
     *
     * @param str
     *         string to parse
     *
     * @return parsed value or null.
     */
    @Nullable
    public static Float asFloat(String str)
    {
        try
        {
            return Float.valueOf(str);
        }
        catch (NumberFormatException e)
        {
            return null;
        }
    }

    /**
     * Parse string to int, if string can't be parsed to int, then it will return given default value.
     *
     * @param str
     *         string to parse
     * @param def
     *         default value.
     *
     * @return parsed value or default value.
     */
    public static int asInt(String str, int def)
    {
        Integer i = asInt(str);
        return (i == null) ? def : i;
    }

    /**
     * Parse string to long, if string can't be parsed to long, then it will return given default value.
     *
     * @param str
     *         string to parse
     * @param def
     *         default value.
     *
     * @return parsed value or default value.
     */
    public static long asLong(String str, long def)
    {
        Long l = asLong(str);
        return (l == null) ? def : l;
    }

    /**
     * Parse string to double, if string can't be parsed to double, then it will return given default value.
     *
     * @param str
     *         string to parse
     * @param def
     *         default value.
     *
     * @return parsed value or default value.
     */
    public static double asDouble(String str, double def)
    {
        try
        {
            return Double.parseDouble(str);
        }
        catch (NumberFormatException e)
        {
            return def;
        }
    }

    /**
     * Parse string to float, if string can't be parsed to float, then it will return given default value.
     *
     * @param str
     *         string to parse
     * @param def
     *         default value.
     *
     * @return parsed value or default value.
     */
    public static float asFloat(String str, float def)
    {
        try
        {
            return Float.parseFloat(str);
        }
        catch (NumberFormatException e)
        {
            return def;
        }
    }

    /**
     * Simple parse boolean.
     *
     * @param str
     *         string to parse
     *
     * @return parsed value
     *
     * @see Boolean#parseBoolean(String)
     */
    public static boolean asBoolean(String str)
    {
        return Boolean.parseBoolean(str);
    }

    /**
     * Parse string to boolean using two collections of words, for true and false values.
     * If any of trueWords is equals (equalsIgnoreCase) to given string, then method returns ture.<br>
     * If any of falseWords is equals (equalsIgnoreCase) to given string, then method returns false.<br>
     * If given word don't match any words from collections, then method returns null<br>
     * <br>
     *
     * @param str
     *         string to parse.
     * @param trueWords
     *         words that mean "true"
     * @param falseWords
     *         words that mean "false"
     *
     * @return true/false or null.
     */
    @Nullable
    public static Boolean asBoolean(String str, Collection<String> trueWords, Collection<String> falseWords)
    {
        if (trueWords.stream().anyMatch(s -> s.equalsIgnoreCase(str)))
        {
            return Boolean.TRUE;
        }
        if (falseWords.stream().anyMatch(s -> s.equalsIgnoreCase(str)))
        {
            return Boolean.FALSE;
        }
        return null;
    }
}
