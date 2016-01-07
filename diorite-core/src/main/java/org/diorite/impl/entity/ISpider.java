package org.diorite.impl.entity;

import org.diorite.entity.Spider;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface ISpider extends IMonsterEntity, Spider
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(1.4F, 0.9F);

    /**
     * Size of metadata.
     */
    byte META_KEYS                   = 12;
    /**
     * byte as bool
     */
    byte META_KEY_SPIDER_IS_CLIMBING = 11;
}
