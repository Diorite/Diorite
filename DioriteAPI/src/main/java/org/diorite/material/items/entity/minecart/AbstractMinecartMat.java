package org.diorite.material.items.entity.minecart;

import org.diorite.material.ItemMaterialData;
import org.diorite.material.PlaceableEntityMat;

/**
 * Abstract class for all minecart-based items.
 */
public abstract class AbstractMinecartMat extends ItemMaterialData implements PlaceableEntityMat
{
    protected AbstractMinecartMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, 1, typeName, type);
    }

    protected AbstractMinecartMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public abstract AbstractMinecartMat getType(final int type);

    @Override
    public abstract AbstractMinecartMat getType(final String type);

    @Override
    public abstract AbstractMinecartMat[] types();
}
