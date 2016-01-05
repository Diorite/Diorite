package org.diorite.impl.entity;

import org.diorite.entity.Boat;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IBoat extends IEntity, Boat, EntityObject
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(1.5F, 0.6F);

    /**
     * Size of metadata.
     */
    byte META_KEYS                         = 9;
    /**
     * int
     */
    byte META_KEY_BOAT_TIME_SINCE_LAST_HIT = 5;
    /**
     * int
     */
    byte META_KEY_BOAT_FORWARD_DIRECTION   = 6;
    /**
     * float
     */
    byte META_KEY_BOAT_DAMAGE_TAKEN        = 7;
    /**
     * int, enum? wood type of boat.
     */
    byte META_KEY_BOAT_TYPE                = 8;
}
