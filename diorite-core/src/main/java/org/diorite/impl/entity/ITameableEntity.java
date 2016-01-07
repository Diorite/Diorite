package org.diorite.impl.entity;

import org.diorite.entity.TameableEntity;

public interface ITameableEntity extends IAnimalEntity, TameableEntity
{
    /**
     * Size of metadata.
     */
    byte META_KEYS                = 14;
    /**
     * String
     */
    byte META_KEY_TAMEABLE_STATUS = 12;
    /**
     * UUID
     */
    byte META_KEY_TAMEABLE_OWNER  = 13;


    /**
     * Contains status flags used in matadata.
     */
    interface TameableStatusFlag
    {
        byte SITTING = 0;
        byte TAMED   = 2;
    }
}
