package org.diorite;

public final class Diorite
{
    private static Server server;

    //TODO: add static methods
    private Diorite()
    {
    }

    public static void setServer(final Server server)
    {
        if (Diorite.server != null)
        {
            throw new RuntimeException("Server instance can't be changed at runtime");
        }
        Diorite.server = server;
    }

    public static Server getServer()
    {
        return server;
    }
}
