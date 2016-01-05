package org.diorite.impl.entity;

import org.diorite.entity.EnderDragon;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IEnderDragon extends IMonsterEntity, EnderDragon
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(16F, 8F);

    /**
     * Size of metadata.
     */
    byte META_KEYS                   = 12;
    /**
     * int, enum, dragon phase.
     */
    byte META_KEY_ENDER_DRAGON_PHASE = 11;
}
