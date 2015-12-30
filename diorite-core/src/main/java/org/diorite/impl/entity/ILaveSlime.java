package org.diorite.impl.entity;

import org.diorite.entity.LaveSlime;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface ILaveSlime extends IMonsterEntity, LaveSlime
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.51000005F, 0.51000005F); // * size
}
