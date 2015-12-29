package org.diorite.impl.entity;

import org.diorite.entity.ThrownEnderpearl;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IThrownEnderpearl extends IProjectile, ThrownEnderpearl
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.25F, 0.25F);
}
