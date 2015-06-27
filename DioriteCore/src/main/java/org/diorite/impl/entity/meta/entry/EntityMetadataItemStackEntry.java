package org.diorite.impl.entity.meta.entry;

import org.diorite.impl.entity.meta.EntityMetadataType;
import org.diorite.inventory.item.ItemStack;

public class EntityMetadataItemStackEntry extends EntityMetadataObjectEntry<ItemStack>
{
    public EntityMetadataItemStackEntry(final int index, final ItemStack data)
    {
        super(index, data);
    }

    @Override
    public EntityMetadataType getDataType()
    {
        return EntityMetadataType.ITEM_STACK;
    }
}