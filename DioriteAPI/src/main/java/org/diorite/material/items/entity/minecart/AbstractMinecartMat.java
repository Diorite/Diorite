package org.diorite.material.items.entity.minecart;

import org.diorite.material.ItemMaterialData;
import org.diorite.material.items.PlaceableEntityMat;

public abstract class AbstractMinecartMat extends ItemMaterialData implements PlaceableEntityMat
{
    protected AbstractMinecartMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected AbstractMinecartMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }
}
