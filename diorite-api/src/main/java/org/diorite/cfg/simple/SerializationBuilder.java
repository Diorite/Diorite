package org.diorite.cfg.simple;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.ILocation;

public class SerializationBuilder
{
    private final Map<String, Object> data;

    private SerializationBuilder(final int size)
    {
        this.data = new LinkedHashMap<>(size);
    }

    public SerializationBuilder append(final Object str, final Enum<?> object)
    {
        this.data.put(str.toString(), object.name());
        return this;
    }

    public SerializationBuilder append(final String str, final Enum<?> object)
    {
        this.data.put(str, object.name());
        return this;
    }

    public SerializationBuilder append(final Object str, final Object object)
    {
        if (object instanceof Enum)
        {
            return this.append(str, (Enum<?>) object);
        }
        this.data.put(str.toString(), object);
        return this;
    }

    public SerializationBuilder append(final String str, final Object object)
    {
        if (object instanceof Enum)
        {
            return this.append(str, (Enum<?>) object);
        }
        this.data.put(str, object);
        return this;
    }

    public SerializationBuilder append(final String str, final SerializationBuilder object)
    {
        this.data.put(str, object.data);
        return this;
    }

    public SerializationBuilder append(final Object str, final SerializationBuilder object)
    {
        this.data.put(str.toString(), object.data);
        return this;
    }

    public SerializationBuilder appendLoc(final String str, final ILocation location)
    {
        return this.append(str, start(6).append("x", location.getX()).append("y", location.getY()).append("z", location.getZ()).append("world", (location.getWorld() == null) ? null : location.getWorld().getName()).append("pitch", location.getPitch()).append("yaw", location.getYaw()).build());
    }

    public SerializationBuilder append(final Map<String, Object> object)
    {
        this.data.putAll(object);
        return this;
    }

    public Map<String, Object> build()
    {
        return this.data;
    }

    public static SerializationBuilder start(final int size)
    {
        return new SerializationBuilder(size);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("data", this.data).toString();
    }
}
