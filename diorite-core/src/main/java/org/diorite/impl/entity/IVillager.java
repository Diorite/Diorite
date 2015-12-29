package org.diorite.impl.entity;

import org.diorite.entity.Villager;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IVillager extends IAnimalEntity, Villager
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.6F, 1.8F);
}
