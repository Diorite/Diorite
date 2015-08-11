package org.diorite.utils.math.geometry;

import org.diorite.entity.Entity;

public class ImmutableEntityBoundingBox extends EntityBoundingBox
{
    public ImmutableEntityBoundingBox(final float hSize, final float vSize)
    {
        super(hSize, vSize);
    }

    @Override
    public void setCenter(final double x, final double y, final double z)
    {
        throw new UnsupportedOperationException("Can't change center of immutable aabb");
    }

    @Override
    public void setCenter(final Entity entity)
    {
        throw new UnsupportedOperationException("Can't change center of immutable aabb");
    }

    public EntityBoundingBox create(final double x, final double y, final double z)
    {
        final EntityBoundingBox aabb = new EntityBoundingBox(this.hSize, this.vSize);
        aabb.setCenter(x, y, z);
        return aabb;
    }

    public EntityBoundingBox create(final Entity entity)
    {
        return this.create(entity.getX(), entity.getY(), entity.getZ());
    }
}
