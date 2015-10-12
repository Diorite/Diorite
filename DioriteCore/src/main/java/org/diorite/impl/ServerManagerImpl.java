package org.diorite.impl;

import org.diorite.impl.permissions.DioritePermissionsManager;
import org.diorite.ServerManager;
import org.diorite.permissions.PermissionsManager;

/**
 * Class used to manage all event pipelines, other pipelines, plugins and more.
 */
public class ServerManagerImpl implements ServerManager
{
    private final DioriteCore core;
    private PermissionsManager permissionsManager = new DioritePermissionsManager();

    public ServerManagerImpl(final DioriteCore core)
    {
        this.core = core;
    }

    @Override
    public PermissionsManager getPermissionsManager()
    {
        return this.permissionsManager;
    }

    @Override
    public void setPermissionsManager(final PermissionsManager permissionsManager)
    {
        this.permissionsManager = permissionsManager;
    }
}
