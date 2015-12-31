package org.diorite.impl.entity;

import org.diorite.entity.ArmorStand;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IArmorStand extends ILivingEntity, ArmorStand, EntityObject
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.5F, 2.0F);
}
