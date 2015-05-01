package org.diorite.material.blocks.redstone;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.material.blocks.Activatable;
import org.diorite.material.blocks.Directional;

public abstract class RedstoneTorch extends BlockMaterialData implements Activatable, Directional
{
    protected final BlockFace face;

    public RedstoneTorch(final String enumName, final int id, final String minecraftId, final BlockFace face)
    {
        super(enumName, id, minecraftId, face.name(), combine(face));
        this.face = face;
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

    @Override
    public Activatable getActivated(final boolean activate)
    {
        return getRedstoneTorch(activate);
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    /**
     * Returns one of RedstoneTorch sub-type based on activated state.
     * It will never return null.
     *
     * @param activated if RedstoneTorch should be activated.
     *
     * @return sub-type of RedstoneTorch
     */
    public static RedstoneTorch getRedstoneTorch(final boolean activate)
    {
        if (activate)
        {
            return Material.REDSTONE_TORCH_ON;
        }
        else
        {
            return Material.REDSTONE_TORCH_OFF;
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).toString();
    }
}
