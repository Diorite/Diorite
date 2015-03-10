package diorite.impl.entity.attrib;

import java.util.Collection;
import java.util.HashSet;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.entity.attrib.AttributeModifer;
import diorite.entity.attrib.AttributeProperty;
import diorite.entity.attrib.AttributeType;
import diorite.utils.DioriteMathUtils;

public class AttributePropertyImpl implements AttributeProperty
{
    private final AttributeType                    type;
    private final double                           value;
    private final Collection<AttributeModifer> modifers;

    public AttributePropertyImpl(final AttributeType type)
    {
        this.type = type;
        this.value = type.getDefaultValue();
        this.modifers = new HashSet<>(5);
    }

    public AttributePropertyImpl(final AttributeType type, final double value)
    {
        this.type = type;
        this.value = DioriteMathUtils.getInRange(value, type.getMinValue(), type.getMaxValue());
        this.modifers = new HashSet<>(5);
    }

    public AttributePropertyImpl(final AttributeType type, final Collection<AttributeModifer> modifers, final double value)
    {
        this.type = type;
        this.value = value;
        this.modifers = (modifers == null) ? new HashSet<>(1) : new HashSet<>(modifers);
    }

    @Override
    public AttributeType getType()
    {
        return this.type;
    }

    @Override
    public double getValue()
    {
        return this.value;
    }

    @Override
    public Collection<AttributeModifer> getModifers()
    {
        return this.modifers;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof AttributePropertyImpl))
        {
            return false;
        }

        final AttributePropertyImpl that = (AttributePropertyImpl) o;

        return this.modifers.equals(that.modifers);

    }

    @Override
    public int hashCode()
    {
        return this.modifers.hashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("modifers", this.modifers).toString();
    }
}
