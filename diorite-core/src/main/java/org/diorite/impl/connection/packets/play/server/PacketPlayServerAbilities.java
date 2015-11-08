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

package org.diorite.impl.connection.packets.play.server;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayServerListener;

@PacketClass(id = 0x39, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 9)
public class PacketPlayServerAbilities extends PacketPlayServer
{
    public static final byte INVULNERABLE_FLAG    = 0x01;
    public static final byte FLYING_FLAG          = 0x02;
    public static final byte FLY_FLAG             = 0x04;
    public static final byte INSTANTLY_BUILD_FLAG = 0x08;

    private boolean isInvulnerable; // 1/4 byte
    private boolean isFlying; // 1/4 byte
    private boolean canFly; // 1/4 byte
    private boolean canInstantlyBuild; // 1/4 byte
    private float   flyingSpeed; // 4 bytes
    private float   walkingSpeed; // 4 bytes

    public PacketPlayServerAbilities()
    {
    }

    public PacketPlayServerAbilities(final boolean isInvulnerable, final boolean isFlying, final boolean canFly, final boolean canInstantlyBuild, final float walkingSpeed, final float flyingSpeed)
    {
        this.isInvulnerable = isInvulnerable;
        this.isFlying = isFlying;
        this.canFly = canFly;
        this.canInstantlyBuild = canInstantlyBuild;
        this.walkingSpeed = walkingSpeed;
        this.flyingSpeed = flyingSpeed;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        final byte flags = data.readByte();
        this.isInvulnerable = ((flags & INVULNERABLE_FLAG) > 0);
        this.isFlying = ((flags & FLYING_FLAG) > 0);
        this.canFly = ((flags & FLY_FLAG) > 0);
        this.canInstantlyBuild = ((flags & INSTANTLY_BUILD_FLAG) > 0);
        this.flyingSpeed = data.readFloat();
        this.walkingSpeed = data.readFloat();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        byte flags = 0;
        if (this.isInvulnerable)
        {
            flags |= INVULNERABLE_FLAG;
        }
        if (this.isFlying)
        {
            flags |= FLYING_FLAG;
        }
        if (this.canFly)
        {
            flags |= FLY_FLAG;
        }
        if (this.canInstantlyBuild)
        {
            flags |= INSTANTLY_BUILD_FLAG;
        }
        data.writeByte(flags);
        data.writeFloat(this.flyingSpeed);
        data.writeFloat(this.walkingSpeed);
    }

    @Override
    public void handle(final PacketPlayServerListener listener)
    {
        listener.handle(this);
    }

    public boolean isInvulnerable()
    {
        return this.isInvulnerable;
    }

    public void setInvulnerable(final boolean isInvulnerable)
    {
        this.isInvulnerable = isInvulnerable;
    }

    public float getWalkingSpeed()
    {
        return this.walkingSpeed;
    }

    public void setWalkingSpeed(final float walkingSpeed)
    {
        this.walkingSpeed = walkingSpeed;
    }

    public float getFlyingSpeed()
    {
        return this.flyingSpeed;
    }

    public void setFlyingSpeed(final float flyingSpeed)
    {
        this.flyingSpeed = flyingSpeed;
    }

    public boolean isCanInstantlyBuild()
    {
        return this.canInstantlyBuild;
    }

    public void setCanInstantlyBuild(final boolean canInstantlyBuild)
    {
        this.canInstantlyBuild = canInstantlyBuild;
    }

    public boolean isCanFly()
    {
        return this.canFly;
    }

    public void setCanFly(final boolean canFly)
    {
        this.canFly = canFly;
    }

    public boolean isFlying()
    {
        return this.isFlying;
    }

    public void setFlying(final boolean isFlying)
    {
        this.isFlying = isFlying;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("isInvulnerable", this.isInvulnerable).append("isFlying", this.isFlying).append("canFly", this.canFly).append("canInstantlyBuild", this.canInstantlyBuild).append("flyingSpeed", this.flyingSpeed).append("walkingSpeed", this.walkingSpeed).toString();
    }
}
