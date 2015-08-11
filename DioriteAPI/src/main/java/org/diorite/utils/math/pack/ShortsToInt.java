package org.diorite.utils.math.pack;

/**
 * Class with methods to create int from two shorts, and get shorts from int.
 */
public final class ShortsToInt
{
    /**
     * Bits of short
     */
    public static final int BITS  = 0xFFFF;
    /**
     * Size of short
     */
    public static final int SHIFT = 16;

    private ShortsToInt()
    {
    }

    /**
     * Creates a int value from 2 shorts.
     *
     * @param a first value.
     * @param b second value.
     *
     * @return a int which is the concatenation of a and b
     */
    public static int pack(final short a, final short b)
    {
        return ((int) a << SHIFT) | (b & BITS);
    }

    /**
     * Gets the first 16-bit short value from an int key
     *
     * @param key to get from
     *
     * @return the first 16-bit short value in the key
     */
    public static short getA(final int key)
    {
        return (short) ((key >> SHIFT) & BITS);
    }

    /**
     * Gets the second 16-bit short value from an int key
     *
     * @param key to get from
     *
     * @return the second 16-bit short value in the key
     */
    public static short getB(final int key)
    {
        return (short) (key & BITS);
    }

    /**
     * Get both shorts from an int key.
     *
     * @param key to get from
     *
     * @return short array with both (2) values.
     */
    public static short[] get(final int key)
    {
        return new short[]{getA(key), getB(key)};
    }
}
