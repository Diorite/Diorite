package org.diorite.impl.entity;

import org.diorite.entity.EnderDragon;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IEnderDragon extends IMonsterEntity, EnderDragon
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(16F, 8F);
}
