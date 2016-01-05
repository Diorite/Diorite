package org.diorite.impl.entity;

import org.diorite.entity.InsentientEntity;

public interface IInsentientEntity extends ILivingEntity, InsentientEntity
{
    /**
     * Size of metadata.
     */
    byte META_KEYS                  = 11;
    /**
     * Status bit set. <br>
     * NoAI, Left handed.
     */
    byte META_KEY_INSENTIENT_STATUS = 10;

    /**
     * Contains status flags used in matadata.
     */
    interface InsentientStatusFlag
    {
        byte NO_AI       = 0;
        byte LEFT_HANDED = 1;
    }
}
