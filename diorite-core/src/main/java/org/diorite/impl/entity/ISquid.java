package org.diorite.impl.entity;

import org.diorite.entity.Squid;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface ISquid extends IAnimalEntity, Squid
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.95F, 0.95F);
}
