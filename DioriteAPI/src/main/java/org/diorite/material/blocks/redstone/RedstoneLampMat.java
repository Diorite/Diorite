package org.diorite.material.blocks.redstone;

import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.material.blocks.PowerableMat;

/**
 * Abstract class for all RedstoneLamp-based classes
 */
public abstract class RedstoneLampMat extends BlockMaterialData implements PowerableMat
{
    protected RedstoneLampMat(final String enumName, final int id, final String minecraftId, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, typeName, type, hardness, blastResistance);
    }

    protected RedstoneLampMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public RedstoneLampMat getPowered(final boolean powered)
    {
        return getRedstoneLamp(powered);
    }

    @Override
    public abstract RedstoneLampMat getType(final int type);

    @Override
    public abstract RedstoneLampMat getType(final String type);

    @Override
    public abstract RedstoneLampMat[] types();

    public static RedstoneLampMat getRedstoneLamp(final boolean powered)
    {
        if (powered)
        {
            return Material.REDSTONE_LAMP_ON;
        }
        else
        {
            return Material.REDSTONE_LAMP_OFF;
        }
    }
}
