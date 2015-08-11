package org.diorite.utils.math.pack;

/**
 * Class with methods to create long from two ints, and get ints from long.
 */
public final class IntsToLong
{
    /**
     * Bits of integer
     */
    public static final long BITS  = 0xFFFFFFFFL;
    /**
     * Size of integer
     */
    public static final int  SHIFT = 32;

    private IntsToLong()
    {
    }

    /**
     * Creates a long value from 2 ints.
     *
     * @param a first value.
     * @param b second value.
     *
     * @return a long which is the concatenation of a and b
     */
    public static long pack(final int a, final int b)
    {
        return ((long) a << SHIFT) | (b & BITS);
    }

    /**
     * Gets the first 32-bit integer value from an long key
     *
     * @param key to get from
     *
     * @return the first 32-bit integer value in the key
     */
    public static int getA(final long key)
    {
        return (int) ((key >> SHIFT) & BITS);
    }

    /**
     * Gets the second 32-bit integer value from an long key
     *
     * @param key to get from
     *
     * @return the second 32-bit integer value in the key
     */
    public static int getB(final long key)
    {
        return (int) (key & BITS);
    }

    /**
     * Get both integers from an long key.
     *
     * @param key to get from
     *
     * @return int array with both (2) values.
     */
    public static int[] get(final long key)
    {
        return new int[]{getA(key), getB(key)};
    }
}
