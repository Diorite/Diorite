package org.diorite.impl.entity;

import org.diorite.entity.Arrow;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IArrow extends IAbstractArrow, Arrow
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.5F, 0.5F);
}
