package org.diorite.impl.entity;

import org.diorite.entity.CaveSpider;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface ICaveSpider extends ISpider, CaveSpider
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.7F, 0.5F);
}
