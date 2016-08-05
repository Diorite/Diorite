package org.diorite.block;

import org.diorite.inventory.InventoryHolder;
import org.diorite.inventory.item.meta.PotionMeta.PotionTypes;

/**
 * Represents a beacon block.
 */
public interface Beacon extends BlockState, InventoryHolder
{
    int getLevel();

    void setLevel(int level); // ?

    PotionTypes getPrimaryEffect();

    void setPrimaryEffect(PotionTypes effect);

    PotionTypes getSecondaryEffect();

    void setSecondaryEffect(PotionTypes effect);
}
