package org.diorite.material.blocks.redstone;

import org.diorite.BlockFace;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.Activatable;
import org.diorite.material.blocks.Directional;
import org.diorite.utils.math.ByteRange;

/**
 * Abstract class for all RedstoneRepeater-based blocks.
 */
public abstract class RedstoneRepeater extends BlockMaterialData implements Activatable, Directional
{
    /**
     * Delay range of repeater, from 0 o 3
     */
    public static final ByteRange DELAY_RANGE = new ByteRange(0, 3);

    protected final BlockFace face;
    protected final int       delay;

    public RedstoneRepeater(final String enumName, final int id, final String minecraftId, final BlockFace face, final int delay)
    {
        super(enumName, id, minecraftId, face.name() + "_" + delay, combine(face, delay));
        this.face = face;
        this.delay = delay;
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
    public abstract RedstoneRepeater getDelay(int delay);


    /**
     * Returns sub-type of RedstoneRepeater based on {@link BlockFace} and redstone-tick delay
     *
     * @param face     facing direction of RedstoneRepeater.
     * @param delay    redstone-ticks delay of RedstoneRepeater (from 1 to 4).
     *
     * @return sub-type of RedstoneRepeater
     */
    public abstract RedstoneRepeater getType(BlockFace face, int delay);

    @Override
    public RedstoneRepeater getActivated(final boolean activate)
    {
        return getRedstoneRepeater(activate, this.face, this.delay);
    }

    /**
     * Returns sub-type of RedstoneRepeater based on active status, {@link BlockFace} and redstone-tick delay
     *
     * @param activate if RedstoneRepeater should be activated.
     * @param face     facing direction of RedstoneRepeater.
     * @param delay    redstone-ticks delay of RedstoneRepeater (from 1 to 4).
     *
     * @return sub-type of RedstoneRepeater
     */
    public static RedstoneRepeater getRedstoneRepeater(final boolean activate, final BlockFace face, final int delay)
    {
        if (activate)
        {
            return RedstoneRepeaterOn.getByID(combine(face, delay));
        }
        else
        {
            return RedstoneRepeaterOff.getByID(combine(face, delay));
        }
    }
}
