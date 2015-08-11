package org.diorite.inventory.item;

import org.diorite.material.Material;

public interface ItemStack extends Cloneable
{
    /**
     * @return material of itemstack.
     */
    Material getMaterial();

    /**
     * Change material of itemstack.
     *
     * @param material new material.
     */
    void setMaterial(Material material);

    /**
     * ItemMeta contains data like name, lore, enchantments of item.
     *
     * @return ItemMeta of itemstack, may be null.
     */
    ItemMeta getItemMeta();

    /**
     * Change itemmeta of itemstack.
     *
     * @param itemMeta new itemmeta.
     */
    void setItemMeta(ItemMeta itemMeta);

    /**
     * @return amout of material in itemstack.
     */
    int getAmount();

    /**
     * Change amout of itemstack in material.
     *
     * @param amount new amount.
     */
    void setAmount(int amount);

    void update();

    /**
     * @return true if this is air itemstack.
     */
    boolean isAir();

    /**
     * Check if this itemstack have valid amout of items in it.
     *
     * @return true if amount is smaller or equal to max stack size of material.
     */
    boolean isValid();

    /**
     * Check if items are similar, items are similar if they are made from this
     * same material and have this same item meta, but they can have different size.
     * (amount of items in itemstack)
     *
     * @param b item to check.
     *
     * @return true if items are similar.
     */
    boolean isSimilar(ItemStack b);

    /**
     * Subtract the specified number of items and creates a new ItemStack with given amount of items
     *
     * @param size Number of items which should be removed from this itemstack and moved to new
     *
     * @return ItemStack with specified amount of items
     * null when number of items in this ItemStack is 1
     *
     * @throws IllegalArgumentException when size is greater than amount of items in this ItemStack
     */
    ItemStack split(int size);

    /**
     * Adds one ItemStack to another and returns the remainder
     *
     * @param other ItemStack to add
     *
     * @return All of which failed to add
     */
    ItemStack combine(ItemStack other);

    /**
     * Adds part of one ItemStack to another and returns the remainder
     *
     * @param other  ItemStack to add
     * @param amount amount of ItemStack to add
     *
     * @return All of which failed to add
     */
    ItemStack addFrom(ItemStack other, int amount);

    /**
     * Clone this itemstack (deep clone).
     *
     * @return cloned itemstack.
     */
    ItemStack clone();

    static boolean isSimilar(final ItemStack a, final ItemStack b)
    {
        //noinspection ObjectEquality
        if (a == b)
        {
            return true;
        }
        if (a != null)
        {
            return a.isSimilar(b);
        }
        return b.isSimilar(null);
    }
}
