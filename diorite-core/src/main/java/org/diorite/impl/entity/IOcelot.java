package org.diorite.impl.entity;

import org.diorite.entity.Ocelot;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface IOcelot extends IAnimalEntity, Ocelot
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.6F, 0.8F);

    /**
     * Size of metadata.
     */
    byte META_KEYS            = 15;
    /**
     * Int, enum
     */
    byte META_KEY_OCELOT_TYPE = 14;
}
