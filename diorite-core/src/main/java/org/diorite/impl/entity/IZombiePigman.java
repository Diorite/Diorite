package org.diorite.impl.entity;

import org.diorite.entity.ZombiePigman;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IZombiePigman extends IMonsterEntity, ZombiePigman
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.6F, 1.8F);
}
