package org.diorite.material.items;

import org.diorite.material.BlockMaterialData;

/**
 * Represents an item that can be placed as block.
 */
public interface PlaceableItemMat
{
    /**
     * Returns block material that will be placed instead of that item.
     * @return block material representation of this item.
     */
    BlockMaterialData getBlockMaterial();
}
