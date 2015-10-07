package org.diorite.material.blocks.redstone;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.AttachableMat;
import org.diorite.material.blocks.PowerableMat;

/**
 * Abstract class for all button-type blocks.
 */
public abstract class ButtonMat extends BlockMaterialData implements PowerableMat, AttachableMat
{
    /**
     * Bit flag defining if rail is powered.
     * If bit is set to 0, then it isn't powered
     */
    public static final byte POWERED_FLAG = 0x08;

    protected final BlockFace face;
    protected final boolean   powered;

    protected ButtonMat(final String enumName, final int id, final String minecraftId, final BlockFace face, final boolean powered, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, face.name() + (powered ? "_POWERED" : ""), combine(face, powered), hardness, blastResistance);
        this.face = face;
        this.powered = powered;
    }

    protected ButtonMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final boolean powered, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.face = face;
        this.powered = powered;
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public boolean isPowered()
    {
        return this.powered;
    }

    /**
     * Returns one of Button sub-type based on {@link BlockFace} and powered state.
     * It will never return null.
     *
     * @param face    facing direction of piston.
     * @param powered if button should be powered.
     *
     * @return sub-type of Button
     */
    public abstract ButtonMat getType(BlockFace face, boolean powered);

    @Override
    public abstract ButtonMat getType(final int type);

    @Override
    public abstract ButtonMat getType(final String type);

    @Override
    public abstract ButtonMat[] types();

    @Override
    public abstract ButtonMat getBlockFacing(final BlockFace face);

    @Override
    public abstract ButtonMat getPowered(final boolean powered);

    @Override
    public abstract ButtonMat getAttachedFace(final BlockFace face);

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).append("powered", this.powered).toString();
    }

    protected static byte combine(final BlockFace face, final boolean powered)
    {
        byte result = powered ? POWERED_FLAG : 0x0;
        switch (face)
        {
            case EAST:
                result |= 0x1;
                break;
            case WEST:
                result |= 0x2;
                break;
            case SOUTH:
                result |= 0x3;
                break;
            case NORTH:
                result |= 0x4;
                break;
            case UP:
                result |= 0x5;
                break;
            default:
                break;
        }
        return result;
    }
}
