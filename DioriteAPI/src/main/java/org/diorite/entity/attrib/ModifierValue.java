package org.diorite.entity.attrib;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ModifierValue
{
    double x;
    double y = 0.0D;

    public ModifierValue(final double x)
    {
        this.x = x;
    }

    public double getX()
    {
        return this.x;
    }

    public ModifierValue setX(final double x)
    {
        this.x = x;
        return this;
    }

    public ModifierValue addX(final double x)
    {
        this.x += x;
        return this;
    }

    public ModifierValue multipleX(final double x)
    {
        this.x *= x;
        return this;
    }

    public double getY()
    {
        return this.y;
    }

    public ModifierValue setY(final double y)
    {
        this.y = y;
        return this;
    }

    public ModifierValue addY(final double y)
    {
        this.y += y;
        return this;
    }

    public ModifierValue multipleY(final double y)
    {
        this.y *= y;
        return this;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("x", this.x).append("y", this.y).toString();
    }
}
