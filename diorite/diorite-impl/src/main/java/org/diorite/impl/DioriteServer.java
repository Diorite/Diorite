/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.impl;

import javax.annotation.Nullable;

import java.net.Proxy;
import java.security.KeyPair;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.diorite.impl.permissions.DioritePermissionsManager;
import org.diorite.impl.plugin.DioriteServerPlugin;
import org.diorite.impl.protocol.MinecraftEncryption;
import org.diorite.impl.protocol.PCProtocol;
import org.diorite.impl.protocol.connection.ServerConnectionImpl;
import org.diorite.impl.service.ServiceManagerImpl;
import org.diorite.Diorite;
import org.diorite.DioriteConfig;
import org.diorite.SharedAPI;
import org.diorite.commons.SpammyError;
import org.diorite.commons.function.supplier.Supplier;
import org.diorite.core.DioriteCore;
import org.diorite.core.event.EventManagerImpl;
import org.diorite.core.protocol.Protocol;
import org.diorite.core.protocol.connection.ServerConnection;
import org.diorite.gameprofile.SessionService;
import org.diorite.gameprofile.internal.yggdrasil.YggdrasilSessionService;
import org.diorite.permissions.PermissionsManager;
import org.diorite.ping.Favicon;
import org.diorite.player.Player;
import org.diorite.plugin.PluginManager;
import org.diorite.serializers.SerializersInit;
import org.diorite.service.ServiceManager;
import org.diorite.services.AutoAuthMode;

public class DioriteServer implements DioriteCore
{
    private final Logger logger = LoggerFactory.getLogger("");

    private final PCProtocol       pcProtocol;
    private final DioriteConfig    dioriteConfig;
    private final ServerConnection serverConnection;
    private final EventManagerImpl    eventManager   = new EventManagerImpl();
    private final ServiceManager      serviceManager = new ServiceManagerImpl();
    private final DioriteServerPlugin plugin         = new DioriteServerPlugin();
    private final KeyPair             keyPair        = MinecraftEncryption.generateKeyPair();
    private @Nullable Favicon favicon;

    @Nullable private final String serverVersion;

    private boolean isRunning;

    public DioriteServer(DioriteConfig dioriteConfig)
    {
        SerializersInit.init();

        this.dioriteConfig = dioriteConfig;
        this.serverVersion = DioriteCore.class.getPackage().getImplementationVersion();
        this.pcProtocol = new PCProtocol(this);
        this.serverConnection = new ServerConnectionImpl(this);

        this.initServices();
    }

    private void initServices()
    {
        this.serviceManager.registerServiceType(this.plugin, PluginManager.class);
        this.serviceManager.registerServiceType(this.plugin, PermissionsManager.class);
        this.serviceManager.registerServiceType(this.plugin, AutoAuthMode.class);
        this.serviceManager.registerServiceType(this.plugin, SessionService.class);

        this.serviceManager.provideService(this.plugin, PermissionsManager.class, new DioritePermissionsManager(this.plugin), false);
        this.serviceManager.provideService(this.plugin, SessionService.class, new YggdrasilSessionService(Proxy.NO_PROXY, UUID.randomUUID().toString()), false);
    }

    @Override
    public SessionService getSessionService()
    {
        return Objects.requireNonNull(this.serviceManager.get(SessionService.class));
    }

    @Override
    public KeyPair getKeyPair()
    {
        return this.keyPair;
    }

    @Override
    public Logger getLogger()
    {
        return this.logger;
    }

    @Override
    public DioriteConfig getConfig()
    {
        return this.dioriteConfig;
    }

    @Override
    public boolean isRunning()
    {
        return this.isRunning;
    }

    @Override
    public boolean isEnabledDebug()
    {
        return DioriteMain.isEnabledDebug();
    }

    @Override
    public String getVersion()
    {
        if (this.serverVersion == null)
        {
            SpammyError.err("Missing server version!", (int) TimeUnit.HOURS.toSeconds(1), "serverVersion");
            return "Unknown" + " (MC: " + Diorite.getMinecraftVersion() + ")";
        }
        return this.serverVersion + " (MC: " + Diorite.getMinecraftVersion() + ")";
    }

    void start()
    {
        this.serverConnection.init();
        while (true)
        {
        }
    }

    @Override
    public Protocol<?> getProtocol()
    {
        return this.pcProtocol;
    }

    @Override
    public ServerConnection getServerConnection()
    {
        return this.serverConnection;
    }

    @Override
    public PluginManager getPluginManager()
    {
        return Objects.requireNonNull(this.serviceManager.get(PluginManager.class));
    }

    @Override
    public PermissionsManager getPermissionsManager()
    {
        return Objects.requireNonNull(this.serviceManager.get(PermissionsManager.class));
    }

    @Override
    public ServiceManager getServiceManager()
    {
        return this.serviceManager;
    }

    {
        SharedAPI.setDioriteInstance(this);
    }

    static DioriteCore getDiorite()
    {
        return (DioriteCore) Diorite.getDiorite();
    }

    @Override
    public EventManagerImpl getEventManager()
    {
        return this.eventManager;
    }

    @Override
    public Collection<? extends Player> getOnlinePlayers()
    {
        return null;
    }

    @Override
    public void debug(Throwable throwable)
    {
        DioriteMain.debug(throwable);
    }

    @Override
    public void debug(Object obj)
    {
        DioriteMain.debug(obj);
    }

    @Override
    public void debugRun(Runnable runnable)
    {
        DioriteMain.debugRun(runnable);
    }

    @Override
    public void debugRun(Supplier<?> supplier)
    {
        DioriteMain.debugRun(supplier);
    }
}
