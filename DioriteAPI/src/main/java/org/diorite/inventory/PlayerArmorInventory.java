package org.diorite.inventory;

import org.diorite.entity.Player;

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
