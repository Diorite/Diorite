package org.diorite.material.blocks.redstone;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.material.blocks.AttachableMat;
import org.diorite.material.blocks.PowerableMat;

/**
 * Abstract class for all RedstoneTorch-based blocks
 */
public abstract class RedstoneTorchMat extends BlockMaterialData implements PowerableMat, AttachableMat
{
    protected final BlockFace face;

    protected RedstoneTorchMat(final String enumName, final int id, final String minecraftId, final BlockFace face)
    {
        super(enumName, id, minecraftId, face.name(), combine(face));
        this.face = face;
    }

    protected RedstoneTorchMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
        this.face = face;
    }

    @Override
    public PowerableMat getPowered(final boolean powered)
    {
        return getRedstoneTorch(powered);
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).toString();
    }

    protected static byte combine(final BlockFace face)
    {
        switch (face)
        {
            case EAST:
                return 0x2;
            case SOUTH:
                return 0x3;
            case NORTH:
                return 0x4;
            case UP:
                return 0x5;
            default:
                return 0x1;
        }
    }

    /**
     * Returns one of RedstoneTorch sub-type based on powered state.
     * It will never return null.
     *
     * @param powered if RedstoneTorch should be powered.
     *
     * @return sub-type of RedstoneTorch
     */
    public static RedstoneTorchMat getRedstoneTorch(final boolean powered)
    {
        if (powered)
        {
            return Material.REDSTONE_TORCH_ON;
        }
        else
        {
            return Material.REDSTONE_TORCH_OFF;
        }
    }

    /**
     * Returns one of RedstoneTorch sub-type based on powered state and {@link BlockFace}.
     * It will never return null.
     *
     * @param powered if RedstoneTorch should be powered.
     * @param face    facing direction of torch.
     *
     * @return sub-type of RedstoneTorch
     */
    public static RedstoneTorchMat getRedstoneTorch(final boolean powered, final BlockFace face)
    {
        if (powered)
        {
            return RedstoneTorchOnMat.getRedstoneTorchOn(face);
        }
        else
        {
            return RedstoneTorchOffMat.getRedstoneTorchOff(face);
        }
    }
}
