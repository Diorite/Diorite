package org.diorite.inventory;

import org.diorite.inventory.item.ItemStack;

public interface PlayerArmorInventory extends Inventory, PlayerInventoryPart
{
    /**
     * @return The ItemStack in the helmet slot
     */
    default ItemStack getHelmet()
    {
        return this.getContents().get(0);
    }

    /**
     * @return The ItemStack in the chestplate slot
     */
    default ItemStack getChestplate()
    {
        return this.getContents().get(1);
    }

    /**
     * @return The ItemStack in the leg slot
     */
    default ItemStack getLeggings()
    {
        return this.getContents().get(2);
    }

    /**
     * @return The ItemStack in the boots slot
     */
    default ItemStack getBoots()
    {
        return this.getContents().get(3);
    }

    /**
     * Put the given ItemStack into the helmet slot. This does not check if
     * the ItemStack is a helmet
     *
     * @param helmet The ItemStack to use as helmet
     *
     * @return previous itemstack used as helmet.
     */
    default ItemStack setHelmet(final ItemStack helmet)
    {
        return this.getContents().getAndSet(0, helmet);
    }

    /**
     * Put the given ItemStack into the chestplate slot. This does not check
     * if the ItemStack is a chestplate
     *
     * @param chestplate The ItemStack to use as chestplate
     *
     * @return previous itemstack used as chestplate.
     */
    default ItemStack setChestplate(final ItemStack chestplate)
    {
        return this.getContents().getAndSet(1, chestplate);
    }

    /**
     * Put the given ItemStack into the leg slot. This does not check if the
     * ItemStack is a pair of leggings
     *
     * @param leggings The ItemStack to use as leggings
     *
     * @return previous itemstack used as leggings.
     */
    default ItemStack setLeggings(final ItemStack leggings)
    {
        return this.getContents().getAndSet(2, leggings);
    }

    /**
     * Put the given ItemStack into the boots slot. This does not check if the
     * ItemStack is a boots
     *
     * @param boots The ItemStack to use as boots
     *
     * @return previous itemstack used as boots.
     */
    default ItemStack setBoots(final ItemStack boots)
    {
        return this.getContents().getAndSet(3, boots);
    }

    /**
     * Put the given ItemStack into the helmet slot if it matches a excepted one.
     * NOTE: this is atomic operation.
     *
     * @param excepted excepted item to replace.
     * @param helmet   The ItemStack to use as helmet
     *
     * @return true if item was replaced.
     */
    default boolean replaceHelmet(final ItemStack excepted, final ItemStack helmet)
    {
        return this.getContents().compareAndSet(0, excepted, helmet);
    }

    /**
     * Put the given ItemStack into the chestplate slot if it matches a excepted one.
     * NOTE: this is atomic operation.
     *
     * @param excepted   excepted item to replace.
     * @param chestplate The ItemStack to use as chestplate
     *
     * @return true if item was replaced.
     */
    default boolean replaceChestplate(final ItemStack excepted, final ItemStack chestplate)
    {
        return this.getContents().compareAndSet(0, excepted, chestplate);
    }

    /**
     * Put the given ItemStack into the leggings slot if it matches a excepted one.
     * NOTE: this is atomic operation.
     *
     * @param excepted excepted item to replace.
     * @param leggings The ItemStack to use as leggings
     *
     * @return true if item was replaced.
     */
    default boolean replaceLeggings(final ItemStack excepted, final ItemStack leggings)
    {
        return this.getContents().compareAndSet(0, excepted, leggings);
    }

    /**
     * Put the given ItemStack into the boots slot if it matches a excepted one.
     * NOTE: this is atomic operation.
     *
     * @param excepted excepted item to replace.
     * @param boots    The ItemStack to use as boots
     *
     * @return true if item was replaced.
     */
    default boolean replaceBoots(final ItemStack excepted, final ItemStack boots)
    {
        return this.getContents().compareAndSet(0, excepted, boots);
    }

    @Override
    default InventoryType getType()
    {
        return InventoryType.PLAYER_ARMOR;
    }
}
