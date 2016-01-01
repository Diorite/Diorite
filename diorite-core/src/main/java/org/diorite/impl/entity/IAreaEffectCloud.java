package org.diorite.impl.entity;

import org.diorite.entity.AreaEffectCloud;

public interface IAreaEffectCloud extends IEntity, AreaEffectCloud, EntityObject
{
    /**
     * Float
     */
    byte META_KEY_RADIUS      = 5;
    /**
     * Int  (only for mob spell particle)
     */
    byte META_KEY_COLOR       = 6;
    /**
     * Boolean
     */
    byte META_KEY_UNKNOWN     = 7; // TODO
    /**
     * Int
     */
    byte META_KEY_PARTICLE_ID = 8;
}
