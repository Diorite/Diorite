package org.diorite.utils.math.geometry;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Entity;

public class EntityBoundingBox extends BoundingBox
{
    protected final float hSize;
    protected final float vSize;

    public EntityBoundingBox(final float hSize, final float vSize)
    {
        this.hSize = hSize;
        this.vSize = vSize;
    }

    @Override
    public Vector getSize()
    {
        return new Vector(this.hSize, this.vSize, this.hSize);
    }

    public void setCenter(final double x, final double y, final double z)
    {
        this.min.setX(x - (this.hSize / 2));
        this.min.setY(y);
        this.min.setZ(z - (this.hSize / 2));
        this.max.setX(x + (this.hSize / 2));
        this.max.setY(y + this.vSize);
        this.max.setZ(z + (this.hSize / 2));
    }

    public void setCenter(final Entity entity)
    {
        this.setCenter(entity.getX(), entity.getY(), entity.getZ());
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("hSize", this.hSize).append("vSize", this.vSize).toString();
    }
}
