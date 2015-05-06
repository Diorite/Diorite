package org.diorite.inventory;

import org.diorite.inventory.item.ItemStack;

public interface PlayerArmorInventory extends Inventory, PlayerInventoryPart
{
    /**
     * @return The ItemStack in the helmet slot
     */
    ItemStack getHelmet();

    /**
     * @return The ItemStack in the chestplate slot
     */
    ItemStack getChestplate();

    /**
     * @return The ItemStack in the leg slot
     */
    ItemStack getLeggings();

    /**
     * @return The ItemStack in the boots slot
     */
    ItemStack getBoots();

    /**
     * Put the given ItemStack into the helmet slot. This does not check if
     * the ItemStack is a helmet
     *
     * @param helmet The ItemStack to use as helmet
     *
     * @return previous itemstack used as helmet.
     */
    ItemStack setHelmet(ItemStack helmet);

    /**
     * Put the given ItemStack into the chestplate slot. This does not check
     * if the ItemStack is a chestplate
     *
     * @param chestplate The ItemStack to use as chestplate
     *
     * @return previous itemstack used as chestplate.
     */
    ItemStack setChestplate(ItemStack chestplate);

    /**
     * Put the given ItemStack into the leg slot. This does not check if the
     * ItemStack is a pair of leggings
     *
     * @param leggings The ItemStack to use as leggings
     *
     * @return previous itemstack used as leggings.
     */
    ItemStack setLeggings(ItemStack leggings);

    /**
     * Put the given ItemStack into the boots slot. This does not check if the
     * ItemStack is a boots
     *
     * @param boots The ItemStack to use as boots
     *
     * @return previous itemstack used as boots.
     */
    ItemStack setBoots(ItemStack boots);

    /**
     * Put the given ItemStack into the helmet slot if it matches a excepted one.
     * NOTE: this is atomic operation.
     *
     * @param excepted excepted item to replace.
     * @param helmet   The ItemStack to use as helmet
     *
     * @return true if item was replaced.
     */
    boolean replaceHelmet(ItemStack excepted, ItemStack helmet);

    /**
     * Put the given ItemStack into the chestplate slot if it matches a excepted one.
     * NOTE: this is atomic operation.
     *
     * @param excepted   excepted item to replace.
     * @param chestplate The ItemStack to use as chestplate
     *
     * @return true if item was replaced.
     */
    boolean replaceChestplate(ItemStack excepted, ItemStack chestplate);

    /**
     * Put the given ItemStack into the leggings slot if it matches a excepted one.
     * NOTE: this is atomic operation.
     *
     * @param excepted excepted item to replace.
     * @param leggings The ItemStack to use as leggings
     *
     * @return true if item was replaced.
     */
    boolean replaceLeggings(ItemStack excepted, ItemStack leggings);

    /**
     * Put the given ItemStack into the boots slot if it matches a excepted one.
     * NOTE: this is atomic operation.
     *
     * @param excepted excepted item to replace.
     * @param boots    The ItemStack to use as boots
     *
     * @return true if item was replaced.
     */
    boolean replaceBoots(ItemStack excepted, ItemStack boots);

    @Override
    default InventoryType getType()
    {
        return InventoryType.PLAYER_ARMOR;
    }
}
