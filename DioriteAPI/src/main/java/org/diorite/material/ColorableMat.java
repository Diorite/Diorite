package org.diorite.material;

import org.diorite.DyeColor;

/**
 * Represent block/item that can be colored to one of simple colors.
 */
public interface ColorableMat
{
    /**
     * @return {@link DyeColor} of current block'item
     */
    DyeColor getColor();

    /**
     * Returns sub-type of block/item, based on {@link DyeColor}.
     *
     * @param color color of block/item.
     *
     * @return sub-type of block/item.
     */
    Material getColor(DyeColor color);
}
