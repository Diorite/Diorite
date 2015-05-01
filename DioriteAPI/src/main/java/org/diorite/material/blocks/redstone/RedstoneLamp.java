package org.diorite.material.blocks.redstone;

import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.material.blocks.Activatable;

public abstract class RedstoneLamp extends BlockMaterialData implements Activatable
{
    public RedstoneLamp(final String enumName, final int id, final String minecraftId, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    public RedstoneLamp(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public RedstoneLamp getActivated(final boolean activate)
    {
        return getRedstoneLamp(activate);
    }

    public static RedstoneLamp getRedstoneLamp(final boolean activate)
    {
        if (activate)
        {
            return Material.REDSTONE_LAMP_ON;
        }
        else
        {
            return Material.REDSTONE_LAMP_OFF;
        }
    }
}
