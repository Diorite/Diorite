package org.diorite.impl.entity;

import org.diorite.entity.Bat;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IBat extends IAmbientEntity, Bat
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.5F, 0.9F);

    /**
     * Size of metadata.
     */
    byte META_KEYS               = 12;
    /**
     * Byte, as boolean.
     */
    byte META_KEY_BAT_IS_HANGING = 11;
}
