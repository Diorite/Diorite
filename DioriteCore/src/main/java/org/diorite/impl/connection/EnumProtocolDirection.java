package org.diorite.impl.connection;

public enum EnumProtocolDirection
{
    SERVERBOUND("SERVERBOUND", 0),
    CLIENTBOUND("CLIENTBOUND", 1);

    private final String name;
    private final int    value;

    EnumProtocolDirection(final String name, final int value)
    {
        this.name = name;
        this.value = value;
    }

    public String getName()
    {
        return this.name;
    }

    public int getValue()
    {
        return this.value;
    }
}