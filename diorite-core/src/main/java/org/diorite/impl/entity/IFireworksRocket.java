package org.diorite.impl.entity;

import org.diorite.entity.FireworksRocket;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IFireworksRocket extends IProjectile, FireworksRocket
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.25F, 0.25F);

    /**
     * Size of metadata.
     */
    byte META_KEYS              = 6;
    /**
     * ItemStack
     */
    byte META_KEY_FIREWORK_ITEM = 5;
}
