package org.diorite.material.blocks.plants;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.material.Material;
import org.diorite.material.blocks.Directional;
import org.diorite.material.blocks.Powerable;

public abstract class AbstractPumpkin extends Plant implements Directional, Powerable
{
    protected final BlockFace face;

    public AbstractPumpkin(final String enumName, final int id, final String minecraftId, final BlockFace face)
    {
        super(enumName, id, minecraftId, face.name(), combine(face));
        this.face = face;
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public AbstractPumpkin getPowered(final boolean powered)
    {
        return AbstractPumpkin.getAbstractPumpkin(powered);
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

    /**
     * Return pumping or pumpkin lantren based on powered state.
     *
     * @param powered if it should return powered version of pumpkin.
     *
     * @return one of pumpkins.
     */
    public static AbstractPumpkin getAbstractPumpkin(final boolean powered)
    {
        if (powered)
        {
            return Material.PUMPKIN_LANTERN;
        }
        else
        {
            return Material.PUMPKIN;
        }
    }

    /**
     * Return pumping or pumpkin lantren based on powered state and {@link BlockFace}.
     *
     * @param powered if it should return powered version of pumpkin.
     *
     * @return one of pumpkins.
     */
    public static AbstractPumpkin getAbstractPumpkin(final boolean powered, final BlockFace face)
    {
        if (powered)
        {
            return PumpkinLantern.getPumpkinLantern(face);
        }
        else
        {
            return Pumpkin.getPumpkin(face);
        }
    }
}
