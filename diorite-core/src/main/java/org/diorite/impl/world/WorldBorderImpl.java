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

package org.diorite.impl.world;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.Tickable;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundWorldBorder;
import org.diorite.impl.entity.IPlayer;
import org.diorite.ILocation;
import org.diorite.ImmutableLocation;
import org.diorite.entity.Player;
import org.diorite.world.WorldBorder;

public class WorldBorderImpl implements WorldBorder, Tickable
{
    public static final double DEFAULT_BORDER_SIZE = 60000000;
    private final WorldImpl world;
    private double centerX, centerZ;
    private double size;
    private double targetSize;
    private double startSize; // Used where border is growing or decreasing
    private long targetReachTime;
    private double damageAmount;
    private double damageBuffer;
    private int warningDistance;
    private int warningTime;
    private int teleportBoundary;

    public WorldBorderImpl(final WorldImpl world)
    {
        this.world = world;
    }

    public WorldImpl getWorld()
    {
        return this.world;
    }

    @Override
    public State getWorldBorderState()
    {
        return (this.size == this.targetSize) ? State.STATIONARY : ((this.size > this.targetSize) ? State.DECREASING : State.GROWING);
    }

    @SuppressWarnings({"CallToSimpleSetterFromWithinClass", "MagicNumber"})
    @Override
    public void reset()
    {
        this.setSize(DEFAULT_BORDER_SIZE);
        this.setDamageAmount(0.2);
        this.setDamageBuffer(5.0);
        this.setWarningDistance(5);
        this.setWarningTime(15);
        this.setCenter(0.0, 0.0);
    }

    @Override
    public ILocation getCenter()
    {
        return new ImmutableLocation(this.centerX, this.centerZ, 0);
    }

    @Override
    public void setCenter(final double x, final double z)
    {
        this.centerX = x;
        this.centerZ = z;
        this.world.broadcastPacketInWorld(new PacketPlayClientboundWorldBorder(x, z));
    }

    @Override
    public double getSize()
    {
        return this.size;
    }

    @Override
    public void setSize(final double size)
    {
        this.targetSize = size;
        this.size = size;
        this.startSize = size;
        this.teleportBoundary = (int) (size / 2);
        this.world.broadcastPacketInWorld(new PacketPlayClientboundWorldBorder(size));
    }

    @Override
    public void setSize(final double size, final long ticks)
    {
        this.startSize = this.size;
        this.targetSize = size;
        this.targetReachTime = ticks;
        this.teleportBoundary = (int) (size / 2);
        this.world.broadcastPacketInWorld(new PacketPlayClientboundWorldBorder(this.size, size, ticks));
    }

    public double getTargetSize()
    {
        return this.targetSize;
    }

    public long getTargetSizeReachTime()
    {
        return this.targetReachTime;
    }

    @Override
    public int getWarningDistance()
    {
        return this.warningDistance;
    }

    @Override
    public void setWarningDistance(final int warningDistance)
    {
        this.warningDistance = warningDistance;
    }

    @Override
    public int getWarningTime()
    {
        return this.warningTime;
    }

    @Override
    public void setWarningTime(final int warningTime)
    {
        this.warningTime = warningTime;
    }

    @Override
    public double getDamageAmount()
    {
        return this.damageAmount;
    }

    @Override
    public void setDamageAmount(final double damageAmount)
    {
        this.damageAmount = damageAmount;
    }

    @Override
    public double getDamageBuffer()
    {
        return this.damageBuffer;
    }

    @Override
    public void setDamageBuffer(final double damageBuffer)
    {
        this.damageBuffer = damageBuffer;
    }

    @Override
    public int getPortalTeleportBoundary()
    {
        return this.teleportBoundary;
    }

    @Override
    public void setPortalTeleportBoundary(final int portalTeleportBoundary)
    {
        this.teleportBoundary = portalTeleportBoundary;
    }

    @Override
    public void doTick(final int tps)
    {
        // TODO This code may lose synchronization with client when Diorite TPS is changed
        final int clientTps = 20;
        final double multiply = 2.5;

        final State wbState = this.getWorldBorderState();
        if (wbState == State.GROWING)
        {
            final double delta = this.targetSize - this.startSize;
            this.size += ((delta * clientTps) / this.targetReachTime) * multiply;
            if (this.size > this.targetSize)
            {
                this.size = this.targetSize;
                return;
            }
        }
        else if (wbState == State.DECREASING)
        {
            final double delta = this.startSize - this.targetSize;
            this.size -= ((delta * clientTps) / this.targetReachTime) * multiply;
            if (this.size < this.targetSize)
            {
                this.size = this.targetSize;
                return;
            }
        }

        for (final Player p : this.world.getPlayersInWorld())
        {
            // TODO apply damage to players
        }
    }

    public void setTargetSize(final double targetSize)
    {
        this.targetSize = targetSize;
    }

    public void setTargetReachTime(final long targetReachTime)
    {
        this.targetReachTime = targetReachTime;
    }

    public void broadcastUpdate()
    {
        this.world.getPlayersInWorld().parallelStream().forEach(player -> ((IPlayer) player).sendWorldBorderUpdate());
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("world", this.world).append("centerX", this.centerX).append("centerZ", this.centerZ).append("size", this.size).append("targetSize", this.targetSize).append("startSize", this.startSize).append("targetReachTime", this.targetReachTime).append("damageAmount", this.damageAmount).append("damageBuffer", this.damageBuffer).append("warningDistance", this.warningDistance).append("warningTime", this.warningTime).append("teleportBoundary", this.teleportBoundary).toString();
    }
}
