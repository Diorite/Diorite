package org.diorite.impl.entity;

import org.diorite.entity.SpectralArrow;
import org.diorite.utils.math.geometry.ImmutableEntityBoundingBox;

public interface ISpectralArrow extends IAbstractArrow, SpectralArrow
{
    ImmutableEntityBoundingBox BASE_SIZE = new ImmutableEntityBoundingBox(0.5F, 0.5F);
}
