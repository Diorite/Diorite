package org.diorite.impl.entity;

import org.diorite.entity.Guardian;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IGuardian extends IMonsterEntity, Guardian
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.85F, 0.85F);
}
