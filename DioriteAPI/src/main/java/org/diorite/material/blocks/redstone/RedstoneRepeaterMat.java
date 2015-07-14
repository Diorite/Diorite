package org.diorite.material.blocks.redstone;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.DirectionalMat;
import org.diorite.material.blocks.PowerableMat;
import org.diorite.utils.math.ByteRange;

/**
 * Abstract class for all RedstoneRepeater-based blocks.
 */
public abstract class RedstoneRepeaterMat extends BlockMaterialData implements PowerableMat, DirectionalMat
{
    /**
     * Delay range of repeater, from 0 o 3
     */
    public static final ByteRange DELAY_RANGE = new ByteRange(0, 3);

    protected final BlockFace face;
    protected final int       delay;

    protected RedstoneRepeaterMat(final String enumName, final int id, final String minecraftId, final BlockFace face, final int delay, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, face.name() + "_" + delay, combine(face, delay), hardness, blastResistance);
        this.face = face;
        this.delay = delay;
    }

    protected RedstoneRepeaterMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final int delay, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.face = face;
        this.delay = delay;
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return null;
    }

    /**
     * @return tick delay of repeater (in redstone-ticks) (from 1 to 4)
     */
    public int getDelay()
    {
        return this.delay;
    }

    /**
     * Returns sub-type of RedstoneRepeater based on redstone-tick delay
     *
     * @param delay redstone-ticks delay (from 1 to 4).
     *
     * @return sub-type of RedstoneRepeater
     */
    public abstract RedstoneRepeaterMat getDelay(int delay);

    /**
     * Returns sub-type of RedstoneRepeater based on {@link BlockFace} and redstone-tick delay
     *
     * @param face  facing direction of RedstoneRepeater.
     * @param delay redstone-ticks delay of RedstoneRepeater (from 1 to 4).
     *
     * @return sub-type of RedstoneRepeater
     */
    public abstract RedstoneRepeaterMat getType(BlockFace face, int delay);

    @Override
    public RedstoneRepeaterMat getPowered(final boolean powered)
    {
        return getRedstoneRepeater(powered, this.face, this.delay);
    }

    protected static byte combine(final BlockFace face, final int delay)
    {
        byte result;
        switch (face)
        {
            case EAST:
                result = 0x1;
                break;
            case SOUTH:
                result = 0x2;
                break;
            case WEST:
                result = 0x3;
                break;
            default:
                result = 0x0;
                break;
        }
        result |= (DELAY_RANGE.getIn(delay - 1) << 2);
        return result;
    }

    /**
     * Returns sub-type of RedstoneRepeater based on powered status, {@link BlockFace} and redstone-tick delay
     *
     * @param powered if RedstoneRepeater should be powered.
     * @param face    facing direction of RedstoneRepeater.
     * @param delay   redstone-ticks delay of RedstoneRepeater (from 1 to 4).
     *
     * @return sub-type of RedstoneRepeater
     */
    public static RedstoneRepeaterMat getRedstoneRepeater(final boolean powered, final BlockFace face, final int delay)
    {
        if (powered)
        {
            return RedstoneRepeaterOnMat.getByID(combine(face, delay));
        }
        else
        {
            return RedstoneRepeaterOffMat.getByID(combine(face, delay));
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).append("delay", this.delay).toString();
    }
}
