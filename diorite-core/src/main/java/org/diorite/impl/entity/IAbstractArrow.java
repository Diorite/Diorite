package org.diorite.impl.entity;

import org.diorite.entity.AbstractArrow;

public interface IAbstractArrow extends IEntity, AbstractArrow, EntityObject
{
    /**
     * Byte as boolean?
     */
    byte META_KEY_IS_CRITICAL = 5;
}
