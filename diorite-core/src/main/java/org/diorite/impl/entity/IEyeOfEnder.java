package org.diorite.impl.entity;

import org.diorite.entity.EyeOfEnder;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IEyeOfEnder extends IProjectile, EyeOfEnder
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.25F, 0.25F);
}
