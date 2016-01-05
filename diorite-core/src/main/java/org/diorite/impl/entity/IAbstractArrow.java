package org.diorite.impl.entity;

import org.diorite.entity.AbstractArrow;

public interface IAbstractArrow extends IEntity, AbstractArrow, EntityObject
{
    /**
     * Size of metadata.
     */
    byte META_KEYS                  = 6;
    /**
     * Byte as boolean?
     */
    byte META_KEY_ARROW_IS_CRITICAL = 5;
}
