package org.diorite.utils.math.pack;

/**
 * Class with methods to create short from two bytes, and get bytes from short.
 */
public final class BytesToShort
{
    /**
     * Bits of byte
     */
    public static final int BITS  = 0xff;
    /**
     * Size of byte
     */
    public static final int SHIFT = 8;

    private BytesToShort()
    {
    }

    /**
     * Creates a short value from 2 bytes.
     *
     * @param a first value.
     * @param b second value.
     *
     * @return a short which is the concatenation of a and b
     */
    public static short pack(final byte a, final byte b)
    {
        return (short) ((a << SHIFT) | (b & BITS));
    }

    /**
     * Gets the first 8-bit byte value from an short key
     *
     * @param key to get from
     *
     * @return the first 8-bit byte value in the key
     */
    public static byte getA(final short key)
    {
        return (byte) ((key >> SHIFT) & BITS);
    }

    /**
     * Gets the second 8-bit byte value from an short key
     *
     * @param key to get from
     *
     * @return the second 8-bit byte value in the key
     */
    public static byte getB(final short key)
    {
        return (byte) (key & BITS);
    }

    /**
     * Get both bytes from an short key.
     *
     * @param key to get from
     *
     * @return bytes array with both (2) values.
     */
    public static byte[] get(final short key)
    {
        return new byte[]{getA(key), getB(key)};
    }
}
