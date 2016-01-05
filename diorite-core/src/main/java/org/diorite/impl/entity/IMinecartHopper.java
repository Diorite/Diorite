package org.diorite.impl.entity;

import org.diorite.entity.MinecartHopper;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IMinecartHopper extends IAbstractMinecart, MinecartHopper, EntityObject
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.98F, 0.7F);
}
