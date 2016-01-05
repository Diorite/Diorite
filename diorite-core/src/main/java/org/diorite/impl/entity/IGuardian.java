package org.diorite.impl.entity;

import org.diorite.entity.Guardian;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IGuardian extends IMonsterEntity, Guardian
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.85F, 0.85F);

    /**
     * Size of metadata.
     */
    byte META_KEYS                = 13;
    /**
     * byte, bit flags. <br>
     * Eldery, RetractingSpikes
     */
    byte META_KEY_GUARDIAN_STATUS = 11;
    /**
     * int, entity id
     */
    byte META_KEY_GUARDIAN_TARGET = 12;

    /**
     * Contains status flags used in matadata.
     */
    interface GuardianStatusFlag
    {
        byte ELDERY            = 0;
        byte RETRACTING_SPIKES = 1;
    }
}
