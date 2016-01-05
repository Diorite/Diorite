package org.diorite.impl.entity;

import org.diorite.entity.MinecartTNT;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IMinecartTNT extends IAbstractMinecart, MinecartTNT, EntityObject
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.98F, 0.7F);
}
