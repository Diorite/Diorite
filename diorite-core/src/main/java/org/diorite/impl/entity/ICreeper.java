package org.diorite.impl.entity;

import org.diorite.entity.Creeper;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface ICreeper extends IMonsterEntity, Creeper
{
    byte                       META_KEY_STATE   = 11;
    byte                       META_KEY_POWERED = 12;
    byte                       META_KEY_IGNITED = 13;
    ImmutableEntityBoundingBox BASE_SIZE        = new ImmutableEntityBoundingBox(0.6F, 1.8F);
}
