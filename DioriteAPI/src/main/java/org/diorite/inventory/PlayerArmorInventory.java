package org.diorite.inventory;

import org.diorite.entity.Player;
import org.diorite.inventory.item.ItemStack;
import org.diorite.material.ArmorType;

public interface PlayerArmorInventory extends Inventory, EntityEquipment, PlayerInventoryPart
{
    @Override
    default InventoryType getType()
    {
        return InventoryType.PLAYER_ARMOR;
    }

    @Override
    default Player getEquipmentHolder()
    {
        return this.getHolder();
    }

}
