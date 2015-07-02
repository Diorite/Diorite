package org.diorite.inventory;

import org.diorite.entity.ArmoredEntity;
import org.diorite.inventory.item.ItemStack;

public interface EntityEquipment
{
    /**
     * @return An array of ItemStacks from the eq.
     */
    ItemStack[] getContents();

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
     ItemStack setHelmet(final ItemStack helmet);

    /**
     * Put the given ItemStack into the chestplate slot. This does not check
     * if the ItemStack is a chestplate
     *
     * @param chestplate The ItemStack to use as chestplate
     *
     * @return previous itemstack used as chestplate.
     */
     ItemStack setChestplate(final ItemStack chestplate);

    /**
     * Put the given ItemStack into the leg slot. This does not check if the
     * ItemStack is a pair of leggings
     *
     * @param leggings The ItemStack to use as leggings
     *
     * @return previous itemstack used as leggings.
     */
     ItemStack setLeggings(final ItemStack leggings);

    /**
     * Put the given ItemStack into the boots slot. This does not check if the
     * ItemStack is a boots
     *
     * @param boots The ItemStack to use as boots
     *
     * @return previous itemstack used as boots.
     */
     ItemStack setBoots(final ItemStack boots);

    /**
     * Put the given ItemStack into the helmet slot if it matches a excepted one.
     * NOTE: this is atomic operation.
     *
     * @param excepted excepted item to replace.
     * @param helmet   The ItemStack to use as helmet
     *
     * @return true if item was replaced.
     * @throws IllegalArgumentException if excepted item isn't impl version of ItemStack, so it can't be == to any item from inventory.
     */
     boolean replaceHelmet(final ItemStack excepted, final ItemStack helmet)throws IllegalArgumentException;

    /**
     * Put the given ItemStack into the chestplate slot if it matches a excepted one.
     * NOTE: this is atomic operation.
     *
     * @param excepted   excepted item to replace.
     * @param chestplate The ItemStack to use as chestplate
     *
     * @return true if item was replaced.
     * @throws IllegalArgumentException if excepted item isn't impl version of ItemStack, so it can't be == to any item from inventory.
     */
     boolean replaceChestplate(final ItemStack excepted, final ItemStack chestplate)throws IllegalArgumentException;

    /**
     * Put the given ItemStack into the leggings slot if it matches a excepted one.
     * NOTE: this is atomic operation.
     *
     * @param excepted excepted item to replace.
     * @param leggings The ItemStack to use as leggings
     *
     * @return true if item was replaced.
     * @throws IllegalArgumentException if excepted item isn't impl version of ItemStack, so it can't be == to any item from inventory.
     */
     boolean replaceLeggings(final ItemStack excepted, final ItemStack leggings)throws IllegalArgumentException;

    /**
     * Put the given ItemStack into the boots slot if it matches a excepted one.
     * NOTE: this is atomic operation.
     *
     * @param excepted excepted item to replace.
     * @param boots    The ItemStack to use as boots
     *
     * @return true if item was replaced.
     * @throws IllegalArgumentException if excepted item isn't impl version of ItemStack, so it can't be == to any item from inventory.
     */
     boolean replaceBoots(final ItemStack excepted, final ItemStack boots)throws IllegalArgumentException;

    /**
     * Completely replaces the inventory's contents. Removes all existing
     * contents and replaces it with the ItemStacks given in the array.
     *
     * @param items A complete replacement for the contents; the length must
     *              be less than or equal to {@link #size()}.
     *
     * @throws IllegalArgumentException If the array has more items than the
     *                                  inventory.
     */
    void setContent(final ItemStack[] items);

    /**
     * Gets the entity belonging to this equipment.
     *
     * @return The holder of the inventory; null if it has no holder.
     */
    ArmoredEntity getEquipmentHolder();

    /**
     * @return The size of the inventory
     */
    int size();

}
