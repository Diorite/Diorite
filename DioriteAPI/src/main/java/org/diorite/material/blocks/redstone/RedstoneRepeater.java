package org.diorite.material.blocks.redstone;

import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.material.blocks.Activatable;

public abstract class RedstoneRepeater extends BlockMaterialData implements Activatable
{
    public RedstoneRepeater(final String enumName, final int id, final String minecraftId, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    public RedstoneRepeater(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public RedstoneRepeater getActivated(final boolean activate)
    {
        return getType(activate);
    }

    public static RedstoneRepeater getType(final boolean activate)
    {
        if (activate)
        {
            return Material.REPEATER_ON;
        }
        else
        {
            return Material.REPEATER_OFF;
        }
    }
}