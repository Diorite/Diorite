package org.diorite.material.items.mob.tool;

import org.diorite.material.ItemMaterialData;

/**
 * Abstract class for all horse armor-based items.
 */
@SuppressWarnings("JavaDoc")
public abstract class AbstractHorseArmor extends ItemMaterialData
{
    protected AbstractHorseArmor(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, 1, typeName, type);
    }

    protected AbstractHorseArmor(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public abstract AbstractHorseArmor getType(final int type);

    @Override
    public abstract AbstractHorseArmor getType(final String type);

    @Override
    public abstract AbstractHorseArmor[] types();
}
