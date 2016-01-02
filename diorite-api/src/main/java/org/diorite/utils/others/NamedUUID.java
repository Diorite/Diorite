package org.diorite.utils.others;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represent UUID with name.
 */
public class NamedUUID
{
    private final String name;
    private final UUID   uuid;

    /**
     * Construct new named UUID with given name and uuid.
     *
     * @param name name of uuid.
     * @param uuid named uuid.
     */
    public NamedUUID(final String name, final UUID uuid)
    {
        this.name = name;
        this.uuid = uuid;
    }

    /**
     * Returns name of this uuid.
     *
     * @return name of this uuid.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Returns uuid of this named uuid.
     *
     * @return uuid of this named uuid.
     */
    public UUID getUuid()
    {
        return this.uuid;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof NamedUUID))
        {
            return false;
        }

        final NamedUUID namedUUID = (NamedUUID) o;

        return (this.name != null) ? this.name.equals(namedUUID.name) : ((namedUUID.name == null) && ((this.uuid != null) ? this.uuid.equals(namedUUID.uuid) : (namedUUID.uuid == null)));
    }

    @Override
    public int hashCode()
    {
        int result = (this.name != null) ? this.name.hashCode() : 0;
        result = (31 * result) + ((this.uuid != null) ? this.uuid.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).append("uuid", this.uuid).toString();
    }
}
