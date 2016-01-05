package org.diorite.impl.entity;

import org.diorite.entity.LivingEntity;

public interface ILivingEntity extends IEntity, LivingEntity
{
    /**
     * Size of metadata.
     */
    byte META_KEYS                           = 10;
    /**
     * byte, possible used hand.
     */
    byte META_KEY_LIVING_UNKNOWN             = 5;
    /**
     * float entry, hp of entity
     */
    byte META_KEY_LIVING_HEALTH              = 6;
    /**
     * int entry, Potion effect color over entity
     */
    byte META_KEY_LIVING_POTION_EFFECT_COLOR = 7;
    /**
     * boolean entry, if potion is ambiend (less visible)
     */
    byte META_KEY_LIVING_POTION_IS_AMBIENT   = 8;
    /**
     * int entry, number of arrows in player.
     */
    byte META_KEY_LIVING_ARROWS_IN_BODY      = 9;
}
