package org.diorite.material.blocks.redstone;

import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.material.blocks.Activatable;

public abstract class RedstoneTorch extends BlockMaterialData implements Activatable
{
    public RedstoneTorch(final String enumName, final int id, final String minecraftId, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    public RedstoneTorch(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public RedstoneTorch getActivated(final boolean activate)
    {
        return getType(activate);
    }

    public static RedstoneTorch getType(final boolean activate)
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
}