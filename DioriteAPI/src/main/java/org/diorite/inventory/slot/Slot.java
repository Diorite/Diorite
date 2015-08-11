package org.diorite.inventory.slot;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.inventory.item.ItemStack;
import org.diorite.material.FuelMat;
import org.diorite.material.items.tool.ArmorMat;

/**
 * Represent slot properties, multiple inventory slots may use this same instance of slot object as it don't
 * represent some slot, but properties of it.
 */
public abstract class Slot
{
    public static final Slot BASE_CONTAINER_SLOT = new Slot(SlotType.CONTAINER)
    {
        @Override
        public ItemStack canHoldItem(final ItemStack item)
        {
            return item;
        }
    };
    public static final Slot BASE_HOTBAR_SLOT    = new Slot(SlotType.HOTBAR)
    {
        @Override
        public ItemStack canHoldItem(final ItemStack item)
        {
            return item;
        }
    };
    public static final Slot BASE_ARMOR_SLOT     = new Slot(SlotType.ARMOR)
    {
        @Override
        public ItemStack canHoldItem(final ItemStack item)
        {
            if (item == null)
            {
                return null;
            }
            return (item.getMaterial() instanceof ArmorMat) ? item : null;
        }
    };
    public static final Slot BASE_CRAFTING_SLOT  = new Slot(SlotType.CRAFTING)
    {
        @Override
        public ItemStack canHoldItem(final ItemStack item)
        {
            return item;
        }
    };
    public static final Slot BASE_FUEL_SLOT      = new Slot(SlotType.FUEL)
    {
        @Override
        public ItemStack canHoldItem(final ItemStack item)
        {
            if (item == null)
            {
                return null;
            } // TODO: add special handle for recipe-enabled fuel items
            return (item.getMaterial() instanceof FuelMat) ? item : null;
        }
    };
    public static final Slot BASE_RESULT_SLOT    = new Slot(SlotType.RESULT)
    {
        @Override
        public ItemStack canHoldItem(final ItemStack item)
        {
            return null;
        }
    };
    public static final Slot BASE_OUTSIDE_SLOT   = new Slot(SlotType.OUTSIDE)
    {
        @Override
        public ItemStack canHoldItem(final ItemStack item)
        {
            return item;
        }
    };

    protected final SlotType slotType;

    public Slot(final SlotType slotType)
    {
        this.slotType = slotType;
    }

    /**
     * Returns base slot type.
     *
     * @return base slot type.
     */
    public SlotType getSlotType()
    {
        return this.slotType;
    }

    /**
     * Method will check if this item can be inserted to this slot-type,
     * it will return this same item stack if it can be inserted or null
     * if it can't be inserted here. <br>
     * It may also return other itemstack with this same data but other
     * amount if only part of item can be inserted. (returned itemstack is
     * item stack that can be inserted). Like Beacon block, only one item
     * from item stack can be inserted. <br>
     * <p>
     * WARNING: it will not check if there is space in this slot, it only
     * check if this slot can hold this type of item.
     *
     * @param item item stack to check.
     *
     * @return item stack that can be inserted here or null.
     */
    public abstract ItemStack canHoldItem(ItemStack item);

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("slotType", this.slotType).toString();
    }
}
