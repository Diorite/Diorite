package org.diorite.inventory;

import org.diorite.entity.ArmoredEntity;
import org.diorite.inventory.item.IItemStack;

public interface EntityEquipment
{
    /**
     * @return An array of ItemStacks from the eq.
     */
    IItemStack[] getContents();

    /**
     * @return The ItemStack in the helmet slot
     */
    IItemStack getHelmet();

    /**
     * @return The ItemStack in the chestplate slot
     */
    IItemStack getChestplate();

    /**
     * @return The ItemStack in the leg slot
     */
    IItemStack getLeggings();

    /**
     * @return The ItemStack in the boots slot
     */
    IItemStack getBoots();

    /**
     * Put the given ItemStack into the helmet slot. This does not check if
     * the ItemStack is a helmet
     *
     * @param helmet The ItemStack to use as helmet
     *
     * @return previous itemstack used as helmet.
     */
    IItemStack setHelmet(final IItemStack helmet);

    /**
     * Put the given ItemStack into the chestplate slot. This does not check
     * if the ItemStack is a chestplate
     *
     * @param chestplate The ItemStack to use as chestplate
     *
     * @return previous itemstack used as chestplate.
     */
    IItemStack setChestplate(final IItemStack chestplate);

    /**
     * Put the given ItemStack into the leg slot. This does not check if the
     * ItemStack is a pair of leggings
     *
     * @param leggings The ItemStack to use as leggings
     *
     * @return previous itemstack used as leggings.
     */
    IItemStack setLeggings(final IItemStack leggings);

    /**
     * Put the given ItemStack into the boots slot. This does not check if the
     * ItemStack is a boots
     *
     * @param boots The ItemStack to use as boots
     *
     * @return previous itemstack used as boots.
     */
    IItemStack setBoots(final IItemStack boots);

    /**
     * Put the given ItemStack into the helmet slot if it matches a excepted one.
     * NOTE: this is atomic operation.
     *
     * @param excepted excepted item to replace.
     * @param helmet   The ItemStack to use as helmet
     *
     * @return true if item was replaced.
     *
     * @throws IllegalArgumentException if excepted item isn't impl version of ItemStack, so it can't be == to any item from inventory.
     */
    boolean replaceHelmet(final IItemStack excepted, final IItemStack helmet) throws IllegalArgumentException;

    /**
     * Put the given ItemStack into the chestplate slot if it matches a excepted one.
     * NOTE: this is atomic operation.
     *
     * @param excepted   excepted item to replace.
     * @param chestplate The ItemStack to use as chestplate
     *
     * @return true if item was replaced.
     *
     * @throws IllegalArgumentException if excepted item isn't impl version of ItemStack, so it can't be == to any item from inventory.
     */
    boolean replaceChestplate(final IItemStack excepted, final IItemStack chestplate) throws IllegalArgumentException;

    /**
     * Put the given ItemStack into the leggings slot if it matches a excepted one.
     * NOTE: this is atomic operation.
     *
     * @param excepted excepted item to replace.
     * @param leggings The ItemStack to use as leggings
     *
     * @return true if item was replaced.
     *
     * @throws IllegalArgumentException if excepted item isn't impl version of ItemStack, so it can't be == to any item from inventory.
     */
    boolean replaceLeggings(final IItemStack excepted, final IItemStack leggings) throws IllegalArgumentException;

    /**
     * Put the given ItemStack into the boots slot if it matches a excepted one.
     * NOTE: this is atomic operation.
     *
     * @param excepted excepted item to replace.
     * @param boots    The ItemStack to use as boots
     *
     * @return true if item was replaced.
     *
     * @throws IllegalArgumentException if excepted item isn't impl version of ItemStack, so it can't be == to any item from inventory.
     */
    boolean replaceBoots(final IItemStack excepted, final IItemStack boots) throws IllegalArgumentException;

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
    void setContent(final IItemStack[] items);

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
