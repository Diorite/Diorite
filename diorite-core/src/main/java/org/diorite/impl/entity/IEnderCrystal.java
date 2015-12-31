package org.diorite.impl.entity;

import org.diorite.entity.EnderCrystal;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IEnderCrystal extends IEntity, EnderCrystal, EntityObject
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(2.0F, 2.0F);
}
