package org.diorite.material.blocks.plants;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.material.blocks.DirectionalMat;

/**
 * Abstract class for all pumpkin-based items.
 */
@SuppressWarnings("JavaDoc")
public abstract class AbstractPumpkinMat extends PlantMat implements DirectionalMat
{
    /**
     * facing direction of pumpkin.
     */
    protected final BlockFace face;

    protected AbstractPumpkinMat(final String enumName, final int id, final String minecraftId, final BlockFace face, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, face.name(), combine(face), hardness, blastResistance);
        this.face = face;
    }

    protected AbstractPumpkinMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.face = face;
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public abstract AbstractPumpkinMat getType(final int type);

    @Override
    public abstract AbstractPumpkinMat getType(final String type);

    @Override
    public abstract AbstractPumpkinMat[] types();

    @Override
    public abstract AbstractPumpkinMat getBlockFacing(final BlockFace face);

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).toString();
    }

    protected static byte combine(final BlockFace face)
    {
        switch (face)
        {
            case WEST:
                return 0x1;
            case NORTH:
                return 0x2;
            case EAST:
                return 0x3;
            case SELF:
                return 0x4;
            default:
                return 0x0;
        }
    }
}
