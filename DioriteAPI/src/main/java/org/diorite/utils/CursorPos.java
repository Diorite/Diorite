package org.diorite.utils;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.utils.math.ByteRange;
import org.diorite.utils.math.FloatRange;

/**
 * Sent by client when block is placed,
 * It can be used to check clicked block face and clicked pixel (part) of block.
 * (with textures 16x16)
 */
@SuppressWarnings("MagicNumber")
public class CursorPos
{
    private static final ByteRange  validByteRange  = new ByteRange(0, 16);
    private static final FloatRange validFloatRange = new FloatRange(0, 1);

    private final BlockFace blockFace;
    private final byte      x;
    private final byte      y;
    private final byte      z;

    /**
     * Construct new CursorPos using pixel coordinates, from 0 to 16
     *
     * @param blockFace clicked blockface
     * @param x x pixel coordinate, from 0 to 16.
     * @param y y pixel coordinate, from 0 to 16.
     * @param z z pixel coordinate, from 0 to 16.
     */
    public CursorPos(final BlockFace blockFace, final int x, final int y, final int z)
    {
        Validate.isTrue(blockFace.isBasic(), "BlockFace must be simple.");
        Validate.isTrue(validByteRange.isIn(x), "x pos must be in 0..16 range.");
        Validate.isTrue(validByteRange.isIn(y), "y pos must be in 0..16 range.");
        Validate.isTrue(validByteRange.isIn(z), "z pos must be in 0..16 range.");
        this.blockFace = blockFace;
        this.x = (byte) x;
        this.y = (byte) y;
        this.z = (byte) z;
    }

    /**
     * Construct new CursorPos using block coordinates, from 0 to 1
     *
     * @param blockFace clicked blockface
     * @param x x block coordinate, from 0 to 1.
     * @param y y block coordinate, from 0 to 1.
     * @param z z block coordinate, from 0 to 1.
     */
    public CursorPos(final BlockFace blockFace, final float x, final float y, final float z)
    {
        Validate.isTrue(blockFace.isBasic(), "BlockFace must be simple.");
        Validate.isTrue(validFloatRange.isIn(x), "x (float) pos must be in 0..1 range.");
        Validate.isTrue(validFloatRange.isIn(y), "x (float) pos must be in 0..1 range.");
        Validate.isTrue(validFloatRange.isIn(z), "x (float) pos must be in 0..1 range.");
        this.blockFace = blockFace;
        this.x = (byte) (x * 16);
        this.y = (byte) (x * 16);
        this.z = (byte) (x * 16);
    }

    /**
     * @return clicked block face
     */
    public BlockFace getBlockFace()
    {
        return this.blockFace;
    }

    /**
     * returns x pixel coordinate on block, from 0 to 16.
     * If returned value is 16, then clicked side is facing east. (Towards positive x)
     *
     * @return clicked pixel x coordinate on block.
     */
    public byte getPixelX()
    {
        return this.x;
    }

    /**
     * returns y pixel coordinate on block, from 0 to 16.
     * If returned value is 16, then clicked side is facing up. (Towards positive y)
     *
     * @return clicked pixel y coordinate on block.
     */
    public byte getPixelY()
    {
        return this.y;
    }

    /**
     * returns z pixel coordinate on block, from 0 to 16.
     * If returned value is 16, then clicked side is facing south. (Towards positive z)
     *
     * @return clicked pixel z coordinate on block.
     */
    public byte getPixelZ()
    {
        return this.z;
    }

    /**
     * returns x coordinate on block, from 0 to 1.
     * If returned value is 1, then clicked side is facing east. (Towards positive x)
     *
     * @return x coordinate on block
     */
    public float getBlockX()
    {
        return this.x / 16F;
    }

    /**
     * returns y coordinate on block, from 0 to 1.
     * If returned value is 1, then clicked side is facing up. (Towards positive y)
     *
     * @return y coordinate on block
     */
    public float getBlockY()
    {
        return this.y / 16F;
    }

    /**
     * returns z coordinate on block, from 0 to 1.
     * If returned value is 1, then clicked side is facing south. (Towards positive z)
     *
     * @return z coordinate on block
     */
    public float getBlockZ()
    {
        return this.z / 16F;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof CursorPos))
        {
            return false;
        }

        final CursorPos cursorPos = (CursorPos) o;
        return (this.x == cursorPos.x) && (this.y == cursorPos.y) && (this.z == cursorPos.z);
    }

    @Override
    public int hashCode()
    {
        int result = (int) this.x;
        result = (31 * result) + (int) this.y;
        result = (31 * result) + (int) this.z;
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("blockFace", this.blockFace).append("x", this.x).append("y", this.y).append("z", this.z).toString();
    }
}
