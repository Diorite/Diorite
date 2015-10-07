package org.diorite.material.items.entity.boat;

import org.diorite.material.ItemMaterialData;
import org.diorite.material.PlaceableEntityMat;

public abstract class AbstractBoatMat extends ItemMaterialData implements PlaceableEntityMat
{
    protected AbstractBoatMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, 1, typeName, type);
    }

    protected AbstractBoatMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    @Override
    public abstract AbstractBoatMat getType(final int type);

    @Override
    public abstract AbstractBoatMat getType(final String type);

    @Override
    public abstract AbstractBoatMat[] types();
}
