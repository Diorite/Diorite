package org.diorite.impl.entity;

import org.diorite.entity.AreaEffectCloud;

public interface IAreaEffectCloud extends IEntity, AreaEffectCloud, EntityObject
{
    /**
     * Size of metadata.
     */
    byte META_KEYS                              = 9;
    /**
     * Float
     */
    byte META_KEY_AREA_EFFECT_CLOUD_RADIUS      = 5;
    /**
     * Int  (only for mob spell particle)
     */
    byte META_KEY_AREA_EFFECT_CLOUD_COLOR       = 6;
    /**
     * Boolean, make effect visible only as point.
     */
    byte META_KEY_AREA_EFFECT_CLOUD_IS_POINT    = 7; // TODO
    /**
     * Int
     */
    byte META_KEY_AREA_EFFECT_CLOUD_PARTICLE_ID = 8;
}
