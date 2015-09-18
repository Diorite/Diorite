package org.diorite.impl.permissions;

import java.io.PrintStream;
import java.util.Random;

public abstract class PermissionContainerImpl
{
    private PermissionContainerImpl parent;

    PrintStream out = System.out;

    public PermissionContainerImpl()
    {
        final Random rand = new Random();
    }

    public PermissionContainerImpl(final PermissionContainerImpl parent)
    {
        this.parent = parent;
    }

    public void setParent(final PermissionContainerImpl parent)
    {
        this.parent = parent;
    }

    public boolean hasPermission(final String permission)
    {
        // TODO;
        return false;
    }

    public boolean hasPermission(final String pattern, final String permission)
    {
        // TODO;
        return false;
    }
}
