package org.diorite.impl.entity;

import org.diorite.entity.MinecartCommandBlock;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IMinecartCommandBlock extends IAbstractMinecart, MinecartCommandBlock, EntityObject
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.98F, 0.7F);
}
