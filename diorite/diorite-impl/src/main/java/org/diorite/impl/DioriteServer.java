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

import java.util.concurrent.TimeUnit;

import org.diorite.impl.protocol.PCProtocol;
import org.diorite.impl.protocol.connection.ServerConnectionImpl;
import org.diorite.Diorite;
import org.diorite.DioriteConfig;
import org.diorite.commons.SpammyError;
import org.diorite.commons.function.supplier.Supplier;
import org.diorite.core.DioriteCore;
import org.diorite.core.event.EventManagerImpl;
import org.diorite.core.protocol.Protocol;
import org.diorite.core.protocol.connection.ServerConnection;

public class DioriteServer implements DioriteCore
{
    private final PCProtocol       pcProtocol;
    private final DioriteConfig    dioriteConfig;
    private final ServerConnection serverConnection;
    private final EventManagerImpl eventManager = new EventManagerImpl();

    @Nullable private final String serverVersion;

    private boolean isRunning;

    public DioriteServer(DioriteConfig dioriteConfig)
    {
        this.dioriteConfig = dioriteConfig;
        this.serverVersion = DioriteCore.class.getPackage().getImplementationVersion();
        this.pcProtocol = new PCProtocol(dioriteConfig);
        this.serverConnection = new ServerConnectionImpl(this);
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

    {
        Diorite.setDioriteInstance(this);
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
