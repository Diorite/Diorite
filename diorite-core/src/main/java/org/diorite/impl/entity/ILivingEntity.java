package org.diorite.impl.entity;

import org.diorite.entity.LivingEntity;

public interface ILivingEntity extends IEntity, LivingEntity
{
    /**
     * float entry, hp of entity
     */
    byte META_KEY_HEALTH              = 6;
    /**
     * byte entry, Potion effect color over entity
     */
    byte META_KEY_POTION_EFFECT_COLOR = 7;
    /**
     * byte/bool entry, if potion is ambiend (less visible)
     */
    byte META_KEY_POTION_IS_AMBIENT   = 8;
    /**
     * byte entry, number of arrows in player.
     */
    byte META_KEY_ARROWS_IN_BODY      = 9;
    /**
     * byte/bool entry, if entity have AI
     */
    byte META_KEY_NO_AI               = 15;
}
