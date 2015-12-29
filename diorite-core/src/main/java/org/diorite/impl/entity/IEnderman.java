package org.diorite.impl.entity;

import org.diorite.entity.Enderman;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IEnderman extends IMonsterEntity, Enderman
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.6F, 2.9F);
}
