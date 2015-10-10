package org.diorite.material;

/**
 * Represent item that can be enchanted.
 */
public interface EnchantableMat
{
    /**
     * Returns enchantability level of tool, used to get possible enchantemnts using enchanting table.
     *
     * @return enchantability level of tool.
     */
    int getEnchantability();
}
