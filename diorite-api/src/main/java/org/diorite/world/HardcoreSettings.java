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

package org.diorite.world;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class HardcoreSettings
{
    private boolean        enabled;
    private HardcoreAction onDeath;

    public HardcoreSettings(final boolean enabled)
    {
        this.enabled = enabled;
        this.onDeath = HardcoreAction.BAN_PLAYER;
    }

    public HardcoreSettings(final boolean enabled, final HardcoreAction onDeath)
    {
        this.enabled = enabled;
        this.onDeath = (onDeath == null) ? HardcoreAction.BAN_PLAYER : onDeath;
    }

    public boolean isEnabled()
    {
        return this.enabled;
    }

    public void setEnabled(final boolean enabled)
    {
        this.enabled = enabled;
    }

    public HardcoreAction getOnDeath()
    {
        return this.onDeath;
    }

    public void setOnDeath(final HardcoreAction onDeath)
    {
        this.onDeath = onDeath;
    }

    public enum HardcoreAction
    {
        BAN_PLAYER,
        DELETE_PLAYER,
        BAN_ALL_PLAYERS,
        DELETE_ALL_PLAYERS,
        DELETE_WORLD
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("enabled", this.enabled).append("onDeath", this.onDeath).toString();
    }
}
