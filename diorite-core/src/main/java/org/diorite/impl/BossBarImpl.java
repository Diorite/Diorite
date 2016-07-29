/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

import java.util.Collection;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundBoss;
import org.diorite.impl.entity.IPlayer;
import org.diorite.BossBar;
import org.diorite.chat.component.BaseComponent;
import org.diorite.utils.collections.WeakCollection;

public class BossBarImpl implements BossBar
{
    private final Collection<IPlayer> holders = new WeakCollection<>(5);
    private final UUID          uuid;
    private       int           health;
    private       BaseComponent title;
    private       Color         color;
    private       Style         style;
    private       boolean       isDarkSky;
    private       boolean       isDragonBar;

    public BossBarImpl(final UUID uuid, final int health, final BaseComponent title, final Style style, final Color color)
    {
        if (this.health < 0 || this.health > 100)
        {
            throw new IllegalArgumentException();
        }
        this.uuid = uuid;
        this.health = health;
        this.title = title;
        this.style = style;
        this.color = color;
    }

    public void addHolder(final IPlayer iPlayer)
    {
        this.holders.add(iPlayer);
        iPlayer.getNetworkManager().sendPacket(new PacketPlayClientboundBoss(this.uuid, this.title, this.healthInPacketFormat(), this.color, this.style, this.isDarkSky, this.isDragonBar));
    }

    public void removeHolder(final IPlayer iPlayer)
    {
        this.holders.remove(iPlayer);
        iPlayer.getNetworkManager().sendPacket(new PacketPlayClientboundBoss(this.uuid));
    }

    public Collection<IPlayer> getHolders()
    {
        return this.holders;
    }

    /**
     * Allows to set health (progress) value greather that allowed
     * May cause unexpected problems in client
     * The standard supported values are 0-100
     *
     * @param health new progress bar value
     */
    public void setUnsafeHealth(final int health)
    {
        this.health = health;
        this.broadcastPacketToHolders(new PacketPlayClientboundBoss(this.uuid, this.healthInPacketFormat()));
    }

    private void broadcastPacketToHolders(final PacketPlayClientboundBoss bossPacket)
    {
        this.holders.forEach(iPlayer -> iPlayer.getNetworkManager().sendPacket(bossPacket));
    }

    private float healthInPacketFormat()
    {
        return this.health / 100F;
    }

    @Override
    public UUID getUUID()
    {
        return this.uuid;
    }

    @Override
    public BaseComponent getTitle()
    {
        return this.title;
    }

    @Override
    public void setTitle(final BaseComponent component)
    {
        this.title = component;
        this.broadcastPacketToHolders(new PacketPlayClientboundBoss(this.uuid, component));
    }

    @Override
    public int getHealth()
    {
        return this.health;
    }

    @Override
    public void setHealth(final int health)
    {
        if (this.health < 0 || this.health > 100)
        {
            throw new IllegalArgumentException();
        }
        this.setUnsafeHealth(health);
    }

    @Override
    public Color getColor()
    {
        return this.color;
    }

    @Override
    public void setColor(final Color color)
    {
        this.color = color;
        this.broadcastPacketToHolders(new PacketPlayClientboundBoss(this.uuid, color, this.style));
    }

    @Override
    public Style getStyle()
    {
        return this.style;
    }

    @Override
    public void setStyle(final Style style)
    {
        this.style = style;
        this.broadcastPacketToHolders(new PacketPlayClientboundBoss(this.uuid, this.color, style));
    }

    @Override
    public void setColorAndStyle(final Color color, final Style style)
    {
        this.color = color;
        this.style = style;
        this.broadcastPacketToHolders(new PacketPlayClientboundBoss(this.uuid, this.color, this.style));
    }

    @Override
    public boolean isDarkSky()
    {
        return isDarkSky;
    }

    @Override
    public void setIsDarkSky(final boolean darkSky)
    {
        isDarkSky = darkSky;
        this.broadcastPacketToHolders(new PacketPlayClientboundBoss(this.uuid, this.isDarkSky, this.isDragonBar));
    }

    @Override
    public boolean isDragonBar()
    {
        return isDragonBar;
    }

    @Override
    public void setIsDragonBar(final boolean dragonBar)
    {
        isDragonBar = dragonBar;
        this.broadcastPacketToHolders(new PacketPlayClientboundBoss(this.uuid, this.isDarkSky, this.isDragonBar));
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("holders", this.holders).append("uuid", this.uuid).append("health", this.health).append("title", this.title).append("color", this.color).append("style", this.style).append("isDarkSky", this.isDarkSky).append("isDragonBar", this.isDragonBar).toString();
    }
}
