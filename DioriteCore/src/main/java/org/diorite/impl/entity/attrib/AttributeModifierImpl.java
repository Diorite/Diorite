package org.diorite.impl.entity.attrib;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.attrib.AttributeModifier;
import org.diorite.entity.attrib.ModifierOperation;

public class AttributeModifierImpl implements AttributeModifier
{
    protected final UUID              uuid;
    protected final double            value;
    protected final ModifierOperation operation;
    protected boolean serialize = true;

    public AttributeModifierImpl(final UUID uuid, final double value, final ModifierOperation operation)
    {
        this.uuid = (uuid == null) ? UUID.randomUUID() : uuid;
        this.value = value;
        this.operation = operation;
    }

    public AttributeModifierImpl(final double value, final ModifierOperation operation)
    {
        this.uuid = UUID.randomUUID();
        this.value = value;
        this.operation = operation;
    }

    @Override
    public UUID getUuid()
    {
        return this.uuid;
    }

    @Override
    public double getValue()
    {
        return this.value;
    }

    @Override
    public ModifierOperation getOperation()
    {
        return this.operation;
    }

    @Override
    public boolean isSerialize()
    {
        return this.serialize;
    }

    @Override
    public AttributeModifierImpl setSerialize(final boolean serialize)
    {
        this.serialize = serialize;
        return this;
    }

    @Override
    public int hashCode()
    {
        int result;
        final long temp;
        result = this.uuid.hashCode();
        temp = Double.doubleToLongBits(this.value);
        result = (31 * result) + (int) (temp ^ (temp >>> 32));
        result = (31 * result) + this.operation.hashCode();
        return result;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof AttributeModifierImpl))
        {
            return false;
        }

        final AttributeModifierImpl that = (AttributeModifierImpl) o;

        return (Double.compare(that.value, this.value) == 0) && this.operation.equals(that.operation) && this.uuid.equals(that.uuid);

    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("uuid", this.uuid).append("value", this.value).append("operation", this.operation).toString();
    }
}
