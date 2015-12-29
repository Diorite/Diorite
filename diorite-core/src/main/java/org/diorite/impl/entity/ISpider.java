package org.diorite.impl.entity;

import org.diorite.entity.Spider;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface ISpider extends IMonsterEntity, Spider
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(1.4F, 0.9F);
}
