package org.diorite.impl.entity.meta.entry;

import org.diorite.impl.entity.meta.EntityMetadataType;
import org.diorite.inventory.item.IItemStack;

public class EntityMetadataItemStackEntry extends EntityMetadataObjectEntry<IItemStack>
{
    public EntityMetadataItemStackEntry(final int index, final IItemStack data)
    {
        super(index, data);
    }

    @Override
    public EntityMetadataType getDataType()
    {
        return EntityMetadataType.ITEM_STACK;
    }
}