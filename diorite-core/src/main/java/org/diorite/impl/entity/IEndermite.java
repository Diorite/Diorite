package org.diorite.impl.entity;

import org.diorite.entity.Endermite;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IEndermite extends IMonsterEntity, Endermite
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.4F, 0.3F);
}
