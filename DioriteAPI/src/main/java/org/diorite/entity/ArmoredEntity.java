package org.diorite.entity;

import org.diorite.inventory.EntityEquipment;

/**
 * Entity that can hold armor/weapon.
 */
public interface ArmoredEntity extends LivingEntity
{
    /**
     * Return instance of equipment, for player it just return {@link org.diorite.inventory.PlayerArmorInventory}
     *
     * @return instance of equipment.
     */
    EntityEquipment getEquipment();
}
