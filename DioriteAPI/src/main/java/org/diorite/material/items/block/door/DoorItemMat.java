package org.diorite.material.items.block.door;

import org.diorite.material.ItemMaterialData;
import org.diorite.material.items.PlaceableItemMat;

public abstract class DoorItemMat extends ItemMaterialData implements PlaceableItemMat
{
    protected DoorItemMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected DoorItemMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }
}
