package org.diorite.impl.entity;

import org.diorite.entity.VillagerGolem;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IVillagerGolem extends IAnimalEntity, VillagerGolem
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(1.4F, 2.9F);
}
