package org.diorite.impl.entity;

import org.diorite.entity.TippedArrow;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface ITippedArrow extends IProjectile, TippedArrow
{
    ImmutableEntityBoundingBox BASE_SIZE      = new ImmutableEntityBoundingBox(0.5F, 0.5F);
    /**
     * int, RGB
     */
    byte                       META_KEY_COLOR = 6;
}
