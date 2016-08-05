package org.diorite.tileentity;

import org.diorite.inventory.item.meta.PotionMeta.PotionTypes;

public interface TileEntityBeacon extends TileEntity
{
    int getLevel();

    void setLevel(int level); // ?

    PotionTypes getPrimaryEffect();

    void setPrimaryEffect(PotionTypes effect);

    PotionTypes getSecondaryEffect();

    void setSecondaryEffect(PotionTypes effect);
}
