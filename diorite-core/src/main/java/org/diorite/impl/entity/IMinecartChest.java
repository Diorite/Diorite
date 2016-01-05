package org.diorite.impl.entity;

import org.diorite.entity.MinecartChest;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IMinecartChest extends IAbstractMinecart, MinecartChest, EntityObject
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.98F, 0.7F);
}
