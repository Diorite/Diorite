package org.diorite.impl.entity;

import org.diorite.entity.LivingEntity;

public interface ILivingEntity extends IEntity, LivingEntity
{
    /**
     * byte, possible used hand.
     */
    byte META_KEY_UNKNOWN             = 5;
    /**
     * float entry, hp of entity
     */
    byte META_KEY_HEALTH              = 6;
    /**
     * int entry, Potion effect color over entity
     */
    byte META_KEY_POTION_EFFECT_COLOR = 7;
    /**
     * boolean entry, if potion is ambiend (less visible)
     */
    byte META_KEY_POTION_IS_AMBIENT   = 8;
    /**
     * int entry, number of arrows in player.
     */
    byte META_KEY_ARROWS_IN_BODY      = 9;
//    /**
//     * byte/bool entry, if entity have AI
//     */
//    byte META_KEY_NO_AI               = 15;
}
