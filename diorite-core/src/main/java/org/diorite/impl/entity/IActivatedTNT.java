package org.diorite.impl.entity;

import org.diorite.entity.ActivatedTNT;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IActivatedTNT extends IEntity, IFallingBlock, ActivatedTNT // ?
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.98F, 0.98F);
}
