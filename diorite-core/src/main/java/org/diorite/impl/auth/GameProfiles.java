/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.impl.auth;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.auth.exceptions.AuthenticationException;
import org.diorite.auth.GameProfile;
import org.diorite.cfg.DioriteConfig.OnlineMode;

public final class GameProfiles
{
    private static final long MAX_TIME = TimeUnit.DAYS.toMillis(1);

    private static final Map<String, GameProfileEntry> nameCache  = new ConcurrentHashMap<>(100, .5f, 4);
    private static final Map<UUID, GameProfileEntry>   uuidCache  = new ConcurrentHashMap<>(100, .5f, 4);
    private static final Map<UUID, String>             uuidToName = new ConcurrentHashMap<>(100, .5f, 4);

    private GameProfiles()
    {
    }

    public static void removeFromCache(final GameProfile profile)
    {
        removeFromCache(profile.getName(), profile.getId());
    }

    public static void removeFromCache(final String name, final UUID uuid)
    {
        if (name != null)
        {
            nameCache.remove(name.toLowerCase());
        }
        if (uuid != null)
        {
            uuidCache.remove(uuid);
        }
        if ((name != null) && (uuid != null))
        {
            uuidToName.put(uuid, name);
        }
    }

    public static void addToCache(final GameProfile gameProfile)
    {
        final GameProfileEntry entry = new GameProfileEntry(gameProfile);
        nameCache.put(gameProfile.getName().toLowerCase(), entry);
        uuidCache.put(gameProfile.getId(), entry);
        uuidToName.put(gameProfile.getId(), gameProfile.getName());
    }

    public static void addName(final UUID uuid, final String name)
    {
        uuidToName.put(uuid, name);
    }

    public static String getNameByUUID(final UUID uuid)
    {
        return uuidToName.get(uuid);
    }

    public static void addEmptyEntry(String name, final UUID uuid)
    {
        final GameProfileEntry entry = new GameProfileEntry(null);
        if ((name == null) && (uuid != null))
        {
            name = getNameByUUID(uuid);
        }
        if (name != null)
        {
            nameCache.put(name.toLowerCase(), entry);
        }
        if (uuid != null)
        {
            uuidCache.put(uuid, entry);
        }
        if ((name != null) && (uuid != null))
        {
            uuidToName.put(uuid, name);
        }
    }

    public static GameProfile getGameProfile(final String name, final boolean onlyCache) throws AuthenticationException
    {
        final GameProfileEntry entry = nameCache.get(name.toLowerCase());
        if (entry != null)
        {
            if ((System.currentTimeMillis() - entry.updateTime) <= MAX_TIME)
            {
                return entry.profile;
            }
            nameCache.remove(name.toLowerCase());
            uuidCache.remove(entry.profile.getId());
        }
        else if (nameCache.containsKey(name))
        {
            return null;
        }
        if (onlyCache || (DioriteCore.getInstance().getOnlineMode() == OnlineMode.FALSE))
        {
            return null;
        }
        final GameProfile gp = DioriteCore.getInstance().getSessionService().getGameProfile(name);
        if (gp == null)
        {
            return null;
        }
        addToCache(gp);
        return gp;
    }

    public static GameProfile getGameProfile(final UUID uuid, final boolean onlyCache) throws AuthenticationException
    {
        final GameProfileEntry entry = uuidCache.get(uuid);
        if (entry != null)
        {
            if ((System.currentTimeMillis() - entry.updateTime) <= MAX_TIME)
            {
                return entry.profile;
            }
            uuidCache.remove(uuid);
            nameCache.remove(entry.profile.getName().toLowerCase());
        }
        if (onlyCache || (DioriteCore.getInstance().getOnlineMode() == OnlineMode.FALSE))
        {
            return null;
        }
        final GameProfile gp = DioriteCore.getInstance().getSessionService().getGameProfile(uuid);
        if (gp == null)
        {
            return null;
        }
        addToCache(gp);
        return gp;
    }

    private static class GameProfileEntry
    {
        private final GameProfile profile;
        private final long        updateTime;

        private GameProfileEntry(final GameProfile profile)
        {
            this.profile = profile;
            this.updateTime = System.currentTimeMillis();
        }
    }
}
