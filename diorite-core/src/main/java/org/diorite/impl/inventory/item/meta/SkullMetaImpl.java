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

package org.diorite.impl.inventory.item.meta;

import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;

import org.diorite.impl.CoreMain;
import org.diorite.impl.DioriteCore;
import org.diorite.impl.auth.GameProfileImpl;
import org.diorite.impl.auth.GameProfiles;
import org.diorite.impl.auth.exceptions.AuthenticationException;
import org.diorite.impl.auth.exceptions.TooManyRequestsException;
import org.diorite.Diorite;
import org.diorite.auth.GameProfile;
import org.diorite.cfg.DioriteConfig.OnlineMode;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.item.meta.SkullMeta;
import org.diorite.nbt.NbtSerialization;
import org.diorite.nbt.NbtTagCompound;
import org.diorite.utils.DioriteUtils;

public class SkullMetaImpl extends SimpleItemMetaImpl implements SkullMeta
{
    protected static final String OWNER         = "SkullOwner";
    private static final   int    TIMEOUT_DELAY = 90;

    public SkullMetaImpl(final NbtTagCompound tag)
    {
        super(tag);
    }

    public SkullMetaImpl(final NbtTagCompound tag, final ItemStack itemStack)
    {
        super(tag, itemStack);
    }

    @Override
    public GameProfile getOwner()
    {
        if (this.tag == null)
        {
            return null;
        }
        final NbtTagCompound nbt = this.tag.getCompound(OWNER);
        if (nbt == null)
        {
            return null;
        }
        return NbtSerialization.deserialize(GameProfile.class, nbt);
    }

    @Override
    public boolean hasOwner()
    {
        return (this.tag != null) && this.tag.containsTag(OWNER);
    }

    @Override
    public void setOwner(final String nickname)
    {
        if (nickname == null)
        {
            this.setOwner((GameProfile) null);
            return;
        }
        this.setOwner0(nickname);
    }

    @Override
    public void setOwner(final UUID uuid)
    {
        if (uuid == null)
        {
            this.setOwner((GameProfile) null);
            return;
        }
        this.setOwner0(uuid);
    }

    private void setOwner0(final Object uuidOrName)
    {
        final boolean isUUID = uuidOrName instanceof UUID;
        final UUID uuid = isUUID ? (UUID) uuidOrName : null;
        final String nickname = isUUID ? null : (String) uuidOrName;
        try
        {
            final GameProfile fastGP = isUUID ? GameProfiles.getGameProfile(uuid, true) : GameProfiles.getGameProfile(nickname, true);
            if (fastGP != null)
            {
                this.checkTag(true);
                this.getRawData().put(OWNER, fastGP.serializeToNBT());
                this.setDirty();
                return;
            }
        } catch (final AuthenticationException ignored) // never thrown if fast check.
        {
        }
        this.setOwner(new GameProfileImpl(isUUID ? uuid : DioriteUtils.getCrackedUuid(nickname), nickname));
        if (DioriteCore.getInstance().getOnlineMode() == OnlineMode.FALSE)
        {
            return;
        }
        final String temp = RandomStringUtils.randomAlphanumeric(32);
        this.getRawData().setByte(temp, 0);
        SkullThread.addTask(() -> {
            try
            {
                final GameProfile gp = isUUID ? GameProfiles.getGameProfile(uuid, false) : GameProfiles.getGameProfile(nickname, false);
                if (gp == null)
                {
                    return;
                }
                synchronized (this)
                {
                    this.checkTag(true);
                    this.getRawData().put(OWNER, gp.serializeToNBT());
                }
            } catch (final TooManyRequestsException e)
            {
                if (CoreMain.isEnabledDebug())
                {
                    e.printStackTrace();
                }
                SkullThread.addTask(() -> this.setOwner0(uuidOrName), System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(TIMEOUT_DELAY));
            } catch (final AuthenticationException e)
            {
                e.printStackTrace();
            } finally
            {
                synchronized (this)
                {
                    this.getRawData().removeTag(temp);
                    this.setDirty();
                }
            }
        });
    }

    @Override
    public void setOwner(final GameProfile gameProfile)
    {
        if (this.removeIfNeeded(OWNER, gameProfile))
        {
            return;
        }
        this.checkTag(true);
        this.tag.put(OWNER, gameProfile.serializeToNBT());
        this.setDirty();
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public SkullMetaImpl clone()
    {
        return new SkullMetaImpl((this.tag == null) ? null : this.tag.clone(), this.itemStack);
    }

    public static class SkullThread extends Thread
    {
        private static SkullThread thread;

        private final Queue<Runnable>                   actions      = new ConcurrentLinkedQueue<>();
        private final Collection<Entry<Runnable, Long>> delayActions = new ConcurrentLinkedQueue<>();

        public static synchronized void addTask(final Runnable runnable, final long startTime)
        {
            final boolean s = thread == null;
            if (s)
            {
                thread = new SkullThread();
                thread.setDaemon(true);
                thread.setName("Diorite|Skull");
            }
            thread.delayActions.add(new SimpleEntry<>(runnable, startTime));
            if (s)
            {
                thread.start();
            }
            else
            {
                synchronized (thread.actions)
                {
                    thread.actions.notifyAll();
                }
            }
        }

        public static synchronized void addTask(final Runnable runnable)
        {
            final boolean s = thread == null;
            if (s)
            {
                thread = new SkullThread();
                thread.setDaemon(true);
                thread.setName("Diorite|Skull");
            }
            thread.actions.add(runnable);
            if (s)
            {
                thread.start();
            }
            else
            {
                synchronized (thread.actions)
                {
                    thread.actions.notifyAll();
                }
            }
        }

        @Override
        public void run()
        {
            while (Diorite.isRunning())
            {
                Runnable runnable;
                while ((((runnable = this.actions.poll())) == null) && this.delayActions.isEmpty())
                {
                    if (! Diorite.isRunning())
                    {
                        return;
                    }
                    try
                    {
                        synchronized (this.actions)
                        {
                            this.actions.wait();
                        }
                    } catch (final InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                if (runnable != null)
                {
                    runnable.run();
                }
                if (! this.delayActions.isEmpty())
                {
                    long toAny = Long.MAX_VALUE;
                    for (final Iterator<Entry<Runnable, Long>> it = this.delayActions.iterator(); it.hasNext(); )
                    {
                        final Entry<Runnable, Long> delayAction = it.next();
                        final long to = delayAction.getValue() - System.currentTimeMillis();
                        if (to <= 0)
                        {
                            delayAction.getKey().run();
                            it.remove();
                        }
                        else if (to < toAny)
                        {
                            toAny = to;
                        }
                    }
                    try
                    {
                        synchronized (this.actions)
                        {
                            if (! this.actions.isEmpty())
                            {
                                continue;
                            }
                            this.actions.wait(toAny);
                        }
                    } catch (final InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
