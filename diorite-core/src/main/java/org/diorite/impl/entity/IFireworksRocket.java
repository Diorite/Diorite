package org.diorite.impl.entity;

import org.diorite.entity.FireworksRocket;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IFireworksRocket extends IProjectile, FireworksRocket
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.25F, 0.25F);
}
