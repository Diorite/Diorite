package org.diorite.impl.entity;

import org.diorite.entity.EyeOfEnderSignal;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IEyeOfEnderSignal extends IProjectile, EyeOfEnderSignal
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.25F, 0.25F);
}
