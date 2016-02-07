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

package org.diorite.impl.connection.packets.play.clientbound;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayClientboundListener;

@PacketClass(id = 0x35, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 50)
public class PacketPlayClientboundWorldBorder extends PacketPlayClientbound
{
    private Action action;
    private double x;
    private double z;
    private double oldSize;
    private double newSize;
    private long speed; // Time in ticks
    private int portalTeleportBoundary;
    private int warningTime;
    private int warningBlocks;

    public PacketPlayClientboundWorldBorder()
    {
    }

    /**
     * Constructor for action SET_SIZE
     *
     * @see Action#SET_SIZE
     */
    public PacketPlayClientboundWorldBorder(final double newSize)
    {
        this.action = Action.SET_SIZE;
        this.newSize = newSize;
    }

    /**
     * Constructor for action LERP_SIZE
     *
     * @see Action#LERP_SIZE
     */
    public PacketPlayClientboundWorldBorder(final double oldSize, final double newSize, final long speed)
    {
        this.action = Action.LERP_SIZE;
        this.oldSize = oldSize;
        this.newSize = newSize;
        this.speed = speed;
    }

    /**
     * Constructor for action SET_CENTER
     *
     * @see Action#SET_CENTER
     */
    public PacketPlayClientboundWorldBorder(final double x, final double z)
    {
        this.action = Action.SET_CENTER;
        this.x = x;
        this.z = z;
    }

    /**
     * Constructor for action INITIALIZE
     *
     * @see Action#INITIALIZE
     */
    public PacketPlayClientboundWorldBorder(final double x, final double z, final double oldSize, final double newSize, final long speed, final int portalTeleportBoundary, final int warningTime, final int warningBlocks)
    {
        this.action = Action.INITIALIZE;
        this.x = x;
        this.z = z;
        this.oldSize = oldSize;
        this.newSize = newSize;
        this.speed = speed;
        this.portalTeleportBoundary = portalTeleportBoundary;
        this.warningTime = warningTime;
        this.warningBlocks = warningBlocks;
    }

    /**
     * Constructor for actions SET_WARNING_BLOCKS and SET_WARNING_TIME
     *
     * @see Action#SET_WARNING_BLOCKS
     * @see Action#SET_WARNING_TIME
     */
    public PacketPlayClientboundWorldBorder(final Action action, final int warning)
    {
        this.action = action;
        if (action == Action.SET_WARNING_BLOCKS)
        {
            this.warningBlocks = warning;
        }
        else if (action == Action.SET_WARNING_TIME)
        {
            this.warningTime = warning;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    public Action getAction()
    {
        return this.action;
    }

    public void setAction(final Action action)
    {
        this.action = action;
    }

    public double getX()
    {
        return this.x;
    }

    public void setX(final double x)
    {
        this.x = x;
    }

    public double getZ()
    {
        return this.z;
    }

    public void setZ(final double z)
    {
        this.z = z;
    }

    public double getOldSize()
    {
        return this.oldSize;
    }

    public void setOldSize(final double oldSize)
    {
        this.oldSize = oldSize;
    }

    public double getNewSize()
    {
        return this.newSize;
    }

    public void setNewSize(final double newSize)
    {
        this.newSize = newSize;
    }

    public long getSpeed()
    {
        return this.speed;
    }

    public void setSpeed(final long speed)
    {
        this.speed = speed;
    }

    public int getPortalTeleportBoundary()
    {
        return this.portalTeleportBoundary;
    }

    public void setPortalTeleportBoundary(final int portalTeleportBoundary)
    {
        this.portalTeleportBoundary = portalTeleportBoundary;
    }

    public int getWarningTime()
    {
        return this.warningTime;
    }

    public void setWarningTime(final int warningTime)
    {
        this.warningTime = warningTime;
    }

    public int getWarningBlocks()
    {
        return this.warningBlocks;
    }

    public void setWarningBlocks(final int warningBlocks)
    {
        this.warningBlocks = warningBlocks;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.action = Action.values()[data.readVarInt()];
        switch(this.action)
        {
            case SET_SIZE:
                this.newSize = data.readDouble();
                break;

            case LERP_SIZE:
            {
                this.oldSize = data.readDouble();
                this.newSize = data.readDouble();
                this.speed = data.readVarLong();
                break;
            }

            case SET_CENTER:
            {
                this.x = data.readDouble();
                this.z = data.readDouble();
                break;
            }

            case INITIALIZE:
            {
                this.x = data.readDouble();
                this.z = data.readDouble();
                this.oldSize = data.readDouble();
                this.newSize = data.readDouble();
                this.speed = data.readVarLong();
                this.portalTeleportBoundary = data.readVarInt();
                this.warningTime = data.readVarInt();
                this.warningBlocks = data.readVarInt();
                break;
            }

            case SET_WARNING_TIME:
                this.warningTime = data.readVarInt();
                break;

            case SET_WARNING_BLOCKS:
                this.warningBlocks = data.readVarInt();
                break;
        }
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeVarInt(this.action.ordinal());
        switch(this.action)
        {
            case SET_SIZE:
                data.writeDouble(this.newSize);
                break;

            case LERP_SIZE:
            {
                data.writeDouble(this.oldSize);
                data.writeDouble(this.newSize);
                data.writeVarLong(this.speed);
                break;
            }

            case SET_CENTER:
            {
                data.writeDouble(this.x);
                data.writeDouble(this.z);
                break;
            }

            case INITIALIZE:
            {
                data.writeDouble(this.x);
                data.writeDouble(this.z);
                data.writeDouble(this.oldSize);
                data.writeDouble(this.newSize);
                data.writeVarLong(this.speed);
                data.writeVarInt(this.portalTeleportBoundary);
                data.writeVarInt(this.warningTime);
                data.writeVarInt(this.warningBlocks);
                break;
            }

            case SET_WARNING_TIME:
                data.writeVarInt(this.warningTime);
                break;

            case SET_WARNING_BLOCKS:
                data.writeVarInt(this.warningBlocks);
                break;
        }
    }

    @Override
    public void handle(final PacketPlayClientboundListener listener)
    {
        listener.handle(this);
    }

    public enum Action
    {
        SET_SIZE, LERP_SIZE, SET_CENTER, INITIALIZE, SET_WARNING_TIME, SET_WARNING_BLOCKS
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("action", this.action).append("x", this.x).append("z", this.z).append("oldSize", this.oldSize).append("newSize", this.newSize).append("speed", this.speed).append("portalTeleportBoundary", this.portalTeleportBoundary).append("warningTime", this.warningTime).append("warningBlocks", this.warningBlocks).toString();
    }
}
