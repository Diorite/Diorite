package org.diorite.impl.entity;

import org.diorite.entity.MagmaCube;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IMagmaCube extends IMonsterEntity, MagmaCube
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.51000005F, 0.51000005F); // * size
}
