package org.diorite.impl.entity;

import org.diorite.entity.WitherSkull;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IWitherSkull extends IProjectile, WitherSkull
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.3125F, 0.3125F);

    /**
     * Size of metadata.
     */
    byte META_KEYS             = 6;
    /**
     * Boolean
     */
    byte META_KEY_INVULNERABLE = 5;
}