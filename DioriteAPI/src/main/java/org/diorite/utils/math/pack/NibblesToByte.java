package org.diorite.utils.math.pack;

/**
 * Class with methods to create byte from two nibbles (half of byte), and get nibbles from byte.
 */
public final class NibblesToByte
{
    /**
     * Bits of nibble
     */
    public static final int BITS  = 0xf;
    /**
     * Size of nibble
     */
    public static final int SHIFT = 4;

    private NibblesToByte()
    {
    }

    /**
     * Creates a byte value from 2 nibble-bytes.
     *
     * @param a first value.
     * @param b second value.
     *
     * @return a byte which is the concatenation of a and b
     */
    public static byte pack(final byte a, final byte b)
    {
        return (byte) ((a << SHIFT) | (b & BITS));
    }

    /**
     * Gets the first 4-bit byte value from an byte key
     *
     * @param key to get from
     *
     * @return the first 4-bit byte value in the key
     */
    public static byte getA(final byte key)
    {
        return (byte) ((key >> SHIFT) & BITS);
    }

    /**
     * Gets the second 4-bit byte value from an byte key
     *
     * @param key to get from
     *
     * @return the second 4-bit byte value in the key
     */
    public static byte getB(final byte key)
    {
        return (byte) (key & BITS);
    }

    /**
     * Get both nibbles from an byte key.
     *
     * @param key to get from
     *
     * @return bytes array with both (2) values.
     */
    public static byte[] get(final byte key)
    {
        return new byte[]{getA(key), getB(key)};
    }
}
