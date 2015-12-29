package org.diorite.impl.entity;

import org.diorite.entity.FallingBlock;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IFallingBlock extends IEntity, FallingBlock
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.98F, 0.98F);
}
