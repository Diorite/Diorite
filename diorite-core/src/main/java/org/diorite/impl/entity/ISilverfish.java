package org.diorite.impl.entity;

import org.diorite.entity.Silverfish;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface ISilverfish extends IMonsterEntity, Silverfish
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.4F, 0.3F);
}
