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

package org.diorite;

import org.diorite.permissions.PermissionsManager;
import org.diorite.plugin.PluginManager;
import org.diorite.sender.PlayerCommandSender;
import org.diorite.service.ServiceManager;

/**
 * Shared part of diorite API.
 */
public interface SharedAPI
{
    /**
     * Returns plugin manager.
     *
     * @return plugin manager.
     */
    PluginManager getPluginManager();

    /**
     * Returns permissions manager.
     *
     * @return permissions manager.
     */
    PermissionsManager getPermissionsManager();

    /**
     * Returns service manager.
     *
     * @return service manager.
     */
    ServiceManager getServiceManager();

    /**
     * Returns manager of online players. <br>
     * Online player changes will be visible in returned object.
     *
     * @return manager of online players.
     */
    PlayersManager<? extends PlayerCommandSender> getOnlinePlayers();

    /**
     * Get diorite server instance from static content.
     *
     * @return diorite instance.
     */
    static SharedAPI getSharedAPI()
    {
        SharedAPI diorite = DioriteInstanceHolder.instance;
        if (diorite == null)
        {
            throw new IllegalStateException("Instance not ready yet.");
        }
        return diorite;
    }

    /**
     * Used by diorite to set static instance throws exception if used after init.
     *
     * @param apiInstance
     *         instance to set.
     */
    static void setDioriteInstance(SharedAPI apiInstance)
    {
        if (DioriteInstanceHolder.instance != null)
        {
            throw new IllegalStateException("Instance already set!");
        }
        DioriteInstanceHolder.instance = apiInstance;
    }
}
