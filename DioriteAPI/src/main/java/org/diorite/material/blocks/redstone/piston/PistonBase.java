package org.diorite.material.blocks.redstone.piston;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.Directional;

public abstract class PistonBase extends BlockMaterialData implements Directional
{
    /**
     * Bit flag defining if postion in extended.
     * If bit is set to 0, then it isn't extended..
     */
    public static final byte EXTENDED_FLAG = 0x08;

    protected final BlockFace facing;
    protected final boolean   extended;

    public PistonBase(final String enumName, final int id, final String minecraftId, final String typeName, final BlockFace facing, final boolean extended)
    {
        super(enumName, id, minecraftId, typeName, combine(facing, extended));
        this.facing = facing;
        this.extended = extended;
    }

    /**
     * @return true if this is extended sub-type.
     */
    public boolean isExtended()
    {
        return this.extended;
    }

    /**
     * Returns one of Piston sub-type based on extended state.
     *
     * @param extended if piston should be extended.
     *
     * @return sub-type of Piston
     */
    public PistonBase getExtended(final boolean extended)
    {
        return this.getType(combine(this.facing, extended));
    }

    /**
     * Returns one of Piston sub-type based on {@link BlockFace} and extended state.
     * It will never return null.
     *
     * @param face     facing direction of piston.
     * @param extended if piston should be extended.
     *
     * @return sub-type of Piston
     */
    public PistonBase getType(final BlockFace face, final boolean extended)
    {
        return this.getType(combine(face, extended));
    }

    @Override
    public abstract PistonBase getType(int id);

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("facing", this.facing).toString();
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.facing;
    }

    @Override
    public PistonBase getBlockFacing(final BlockFace face)
    {
        return this.getType(combine(face, this.extended));
    }

    protected static byte combine(final BlockFace facing, final boolean extended)
    {
        byte result = extended ? EXTENDED_FLAG : 0x00;
        switch (facing)
        {
            case UP:
                result |= 0x01;
                break;
            case NORTH:
                result |= 0x02;
                break;
            case SOUTH:
                result |= 0x03;
                break;
            case WEST:
                result |= 0x04;
                break;
            case EAST:
                result |= 0x05;
                break;
            default:
                return result;
        }
        return result;
    }
}
