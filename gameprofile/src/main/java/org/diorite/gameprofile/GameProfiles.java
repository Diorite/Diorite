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

package org.diorite.gameprofile;

import javax.annotation.Nullable;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.diorite.gameprofile.internal.exceptions.AuthenticationException;

public final class GameProfiles
{
    private final long MAX_TIME = TimeUnit.DAYS.toMillis(1);

    private final Map<String, GameProfileEntry> nameCache  = new ConcurrentHashMap<>(100, .5f, 4);
    private final Map<UUID, GameProfileEntry>   uuidCache  = new ConcurrentHashMap<>(100, .5f, 4);
    private final Map<UUID, String>             uuidToName = new ConcurrentHashMap<>(100, .5f, 4);

    private final SessionService sessionService;

    public GameProfiles(SessionService sessionService)
    {
        this.sessionService = sessionService;
    }

    public void removeFromCache(GameProfile profile)
    {
        this.removeFromCache(profile.getName(), profile.getId());
    }

    public void removeFromCache(@Nullable String name, @Nullable UUID uuid)
    {
        if (name != null)
        {
            this.nameCache.remove(name.toLowerCase());
        }
        if (uuid != null)
        {
            this.uuidCache.remove(uuid);
        }
        if ((name != null) && (uuid != null))
        {
            this.uuidToName.put(uuid, name);
        }
    }

    public void addToCache(GameProfile gameProfile)
    {
        if (! gameProfile.isComplete())
        {
            throw new IllegalArgumentException("Can't cache incomplete profile");
        }
        GameProfileEntry entry = new GameProfileEntry(gameProfile);
        String name = gameProfile.getName();
        assert name != null;
        this.nameCache.put(name.toLowerCase(), entry);
        this.uuidCache.put(gameProfile.getId(), entry);
        this.uuidToName.put(gameProfile.getId(), name);
    }

    public void addName(UUID uuid, String name)
    {
        this.uuidToName.put(uuid, name);
    }

    public String getNameByUUID(UUID uuid)
    {
        return this.uuidToName.get(uuid);
    }

    public void addEmptyEntry(@Nullable String name, @Nullable UUID uuid)
    {
        GameProfileEntry entry = new GameProfileEntry(null);
        if ((name == null) && (uuid != null))
        {
            name = this.getNameByUUID(uuid);
        }
        if (name != null)
        {
            this.nameCache.put(name.toLowerCase(), entry);
        }
        if (uuid != null)
        {
            this.uuidCache.put(uuid, entry);
        }
        if ((name != null) && (uuid != null))
        {
            this.uuidToName.put(uuid, name);
        }
    }

    @Nullable
    public GameProfile getGameProfile(String name, boolean onlyCache, boolean allowSessionService) throws AuthenticationException
    {
        GameProfileEntry entry = this.nameCache.get(name.toLowerCase());
        if (entry != null)
        {
            if ((System.currentTimeMillis() - entry.updateTime) <= this.MAX_TIME)
            {
                return entry.profile;
            }
            if (entry.profile != null)
            {
                this.nameCache.remove(name.toLowerCase());
                this.uuidCache.remove(entry.profile.getId());
            }
        }
        else if (this.nameCache.containsKey(name))
        {
            return null;
        }
        if (onlyCache || ! allowSessionService)
        {
            return null;
        }
        GameProfile gp = this.sessionService.getGameProfile(name);
        if (gp == null)
        {
            return null;
        }
        this.addToCache(gp);
        return gp;
    }

    @Nullable
    public GameProfile getGameProfile(UUID uuid, boolean onlyCache, boolean allowSessionService) throws AuthenticationException
    {
        GameProfileEntry entry = this.uuidCache.get(uuid);
        if (entry != null)
        {
            if ((System.currentTimeMillis() - entry.updateTime) <= this.MAX_TIME)
            {
                return entry.profile;
            }
            if (entry.profile != null)
            {
                this.uuidCache.remove(uuid);
                String name = entry.profile.getName();
                assert name != null;
                this.nameCache.remove(name.toLowerCase());
            }
        }
        if (onlyCache || ! allowSessionService)
        {
            return null;
        }
        GameProfile gp = this.sessionService.getGameProfile(uuid);
        if (gp == null)
        {
            return null;
        }
        this.addToCache(gp);
        return gp;
    }

    private static final class GameProfileEntry
    {
        @Nullable private final GameProfile profile;
        private final           long        updateTime;

        private GameProfileEntry(@Nullable GameProfile profile)
        {
            this.profile = profile;
            this.updateTime = System.currentTimeMillis();
        }
    }
}
