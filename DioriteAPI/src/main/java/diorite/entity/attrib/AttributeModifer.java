package diorite.entity.attrib;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AttributeModifer
{
    protected final UUID   uuid;
    protected final double value;
    protected final byte   operation;

    public AttributeModifer(final UUID uuid, final double value, final byte operation)
    {
        this.uuid = (uuid == null) ? UUID.randomUUID() : uuid;
        this.value = value;
        this.operation = operation;
    }

    public AttributeModifer(final double value, final byte operation)
    {
        this.uuid = UUID.randomUUID();
        this.value = value;
        this.operation = operation;
    }

    public UUID getUuid()
    {
        return this.uuid;
    }

    public double getValue()
    {
        return this.value;
    }

    public byte getOperation()
    {
        return this.operation;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof AttributeModifer))
        {
            return false;
        }

        final AttributeModifer that = (AttributeModifer) o;

        return (this.operation == that.operation) && (Double.compare(that.value, this.value) == 0) && this.uuid.equals(that.uuid);

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
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("uuid", this.uuid).append("value", this.value).append("operation", this.operation).toString();
    }
}
