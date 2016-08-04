package org.diorite.material.items;

import org.diorite.material.Material;

/**
 * Represents item which can be smelted
 */
public interface SmeltableMat
{
    /**
     * Item's material after smelting
     *
     * @return material after smelting
     */
    Material getSmeltResult();
}
