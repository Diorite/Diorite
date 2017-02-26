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

package org.diorite.event.events.connection;

import javax.annotation.Nullable;

import org.diorite.chat.ChatMessage;
import org.diorite.commons.objects.Cancellable;

/**
 * Called when player is trying to join the server, before any authorization. <br>
 * It's called right after first login packet. <br>
 * It is also called for invalid nicknames, use {@link #isValidNickname()} to check it. <br>
 * You can cancel event with custom kick message.
 */
public class PlayerConnectedEvent implements Cancellable
{
    private final     String      name;
    private           boolean     authWithMojang;
    private           boolean     validNickname;
    private           boolean     cancelled;
    private @Nullable ChatMessage kickMessage;

    public PlayerConnectedEvent(String name, boolean authWithMojang, boolean validNickname)
    {
        this.name = name;
        this.authWithMojang = authWithMojang;
        this.validNickname = validNickname;
    }

    public String getName()
    {
        return this.name;
    }

    public boolean isAuthWithMojang()
    {
        return this.authWithMojang;
    }

    public void setAuthWithMojang(boolean authWithMojang)
    {
        this.authWithMojang = authWithMojang;
    }

    public boolean isValidNickname()
    {
        return this.validNickname;
    }

    public void setValidNickname(boolean validNickname)
    {
        this.validNickname = validNickname;
    }

    @Nullable
    public ChatMessage getKickMessage()
    {
        return this.kickMessage;
    }

    public void setKickMessage(@Nullable ChatMessage kickMessage)
    {
        this.kickMessage = kickMessage;
    }

    @Override
    public boolean isCancelled()
    {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled)
    {
        this.cancelled = cancelled;
    }
}
