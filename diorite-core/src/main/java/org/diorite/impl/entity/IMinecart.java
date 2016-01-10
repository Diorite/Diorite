package org.diorite.impl.entity;

import org.diorite.entity.Minecart;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IMinecart extends IAbstractMinecart, Minecart, EntityObject
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.98F, 0.7F);
}