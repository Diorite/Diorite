package org.diorite.material.blocks.redstone.piston;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.DirectionalMat;
import org.diorite.material.blocks.PowerableMat;

public abstract class PistonBaseMat extends BlockMaterialData implements DirectionalMat, PowerableMat
{
    /**
     * Bit flag defining if postion in extended.
     * If bit is set to 0, then it isn't extended..
     */
    public static final byte EXTENDED_FLAG = 0x08;

    protected final BlockFace facing;
    protected final boolean   extended;

    protected PistonBaseMat(final String enumName, final int id, final String minecraftId, final BlockFace facing, final boolean extended, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, facing.name() + (extended ? "_EXTENDED" : ""), combine(facing, extended), hardness, blastResistance);
        this.facing = facing;
        this.extended = extended;
    }

    protected PistonBaseMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace facing, final boolean extended, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.facing = facing;
        this.extended = extended;
    }

    @Override
    public boolean isPowered()
    {
        return this.extended;
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
    public PistonBaseMat getExtended(final boolean extended)
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
    public PistonBaseMat getType(final BlockFace face, final boolean extended)
    {
        return this.getType(combine(face, extended));
    }

    @Override
    public abstract PistonBaseMat getType(int id);

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
    public PistonBaseMat getBlockFacing(final BlockFace face)
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
