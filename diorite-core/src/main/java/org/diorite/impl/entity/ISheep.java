package org.diorite.impl.entity;

import org.diorite.entity.Sheep;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface ISheep extends IAnimalEntity, Sheep
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.9F, 1.3F);

    /**
     * Size of metadata.
     */
    byte META_KEYS            = 13;
    /**
     * int, enum
     */
    byte META_KEY_SHEEP_STATUS = 12;


    /**
     * Contains status flags used in matadata. <br>
     * 0x0F - color
     */
    interface SheepStatusFlag
    {
        byte SHEARED = 4;
    }
}
