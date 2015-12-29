package org.diorite.impl.entity;

import org.diorite.entity.Slime;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface ISlime extends IMonsterEntity, Slime
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.51000005F, 0.51000005F); // * size
}
