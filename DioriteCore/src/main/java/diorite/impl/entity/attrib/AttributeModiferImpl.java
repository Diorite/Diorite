package diorite.impl.entity.attrib;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.entity.attrib.AttributeModifer;

public class AttributeModiferImpl implements AttributeModifer
{
    protected final UUID   uuid;
    protected final double value;
    protected final byte   operation;

    public AttributeModiferImpl(final UUID uuid, final double value, final byte operation)
    {
        this.uuid = (uuid == null) ? UUID.randomUUID() : uuid;
        this.value = value;
        this.operation = operation;
    }

    public AttributeModiferImpl(final double value, final byte operation)
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
    public byte getOperation()
    {
        return this.operation;
    }

    @Override
    public int hashCode()
    {
        int result;
        final long temp;
        result = this.uuid.hashCode();
        temp = Double.doubleToLongBits(this.value);
        result = (31 * result) + (int) (temp ^ (temp >>> 32));
        result = (31 * result) + (int) this.operation;
        return result;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof AttributeModiferImpl))
        {
            return false;
        }

        final AttributeModiferImpl that = (AttributeModiferImpl) o;

        return (this.operation == that.operation) && (Double.compare(that.value, this.value) == 0) && this.uuid.equals(that.uuid);

    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("uuid", this.uuid).append("value", this.value).append("operation", this.operation).toString();
    }
}
