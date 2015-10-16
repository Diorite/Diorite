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

package org.diorite;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TeleportData
{
    public static final byte REL_X_FLAG     = 0x01;
    public static final byte REL_Y_FLAG     = 0x02;
    public static final byte REL_Z_FLAG     = 0x04;
    public static final byte REL_YAW_FLAG   = 0x08;
    public static final byte REL_PITCH_FLAG = 0x10;

    private double  x;
    private double  y;
    private double  z;
    private float   yaw;
    private float   pitch;
    private boolean isXRelatvie;
    private boolean isYRelatvie;
    private boolean isZRelatvie;
    private boolean isYawRelatvie;
    private boolean isPitchRelatvie;

    public TeleportData()
    {
    }

    public TeleportData(final Loc location)
    {
        this(location.getX(), location.getY(), location.getZ());
    }

    public TeleportData(final double x, final double y, final double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public TeleportData(final double x, final double y, final double z, final float yaw, final float pitch)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public TeleportData(final double x, final double y, final double z, final float yaw, final float pitch, final int flags)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.setRelativeFlags(flags);
    }

    public TeleportData(final double x, final double y, final double z, final float yaw, final float pitch, final boolean isXRelatvie, final boolean isYRelatvie, final boolean isZRelatvie, final boolean isYawRelatvie, final boolean isPitchRelatvie)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.isXRelatvie = isXRelatvie;
        this.isYRelatvie = isYRelatvie;
        this.isZRelatvie = isZRelatvie;
        this.isYawRelatvie = isYawRelatvie;
        this.isPitchRelatvie = isPitchRelatvie;
    }

    public TeleportData(final double x, final double y, final double z, final float yaw, final float pitch, final boolean isPosRelatvie, final boolean isLookRelatvie)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.isXRelatvie = isPosRelatvie;
        this.isYRelatvie = isPosRelatvie;
        this.isZRelatvie = isPosRelatvie;
        this.isYawRelatvie = isLookRelatvie;
        this.isPitchRelatvie = isLookRelatvie;
    }

    public TeleportData(final double x, final double y, final double z, final float yaw, final float pitch, final boolean isAllRelatvie)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.isXRelatvie = isAllRelatvie;
        this.isYRelatvie = isAllRelatvie;
        this.isZRelatvie = isAllRelatvie;
        this.isYawRelatvie = isAllRelatvie;
        this.isPitchRelatvie = isAllRelatvie;
    }

    public double getX()
    {
        return this.x;
    }

    public void setX(final double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return this.y;
    }

    public void setY(final double y)
    {
        this.y = y;
    }

    public double getZ()
    {
        return this.z;
    }

    public void setZ(final double z)
    {
        this.z = z;
    }

    public float getYaw()
    {
        return this.yaw;
    }

    public void setYaw(final float yaw)
    {
        this.yaw = yaw;
    }

    public float getPitch()
    {
        return this.pitch;
    }

    public void setPitch(final float pitch)
    {
        this.pitch = pitch;
    }

    public boolean isXRelatvie()
    {
        return this.isXRelatvie;
    }

    public void setXRelatvie(final boolean isXRelatvie)
    {
        this.isXRelatvie = isXRelatvie;
    }

    public boolean isYRelatvie()
    {
        return this.isYRelatvie;
    }

    public void setYRelatvie(final boolean isYRelatvie)
    {
        this.isYRelatvie = isYRelatvie;
    }

    public boolean isZRelatvie()
    {
        return this.isZRelatvie;
    }

    public void setZRelatvie(final boolean isZRelatvie)
    {
        this.isZRelatvie = isZRelatvie;
    }

    public boolean isYawRelatvie()
    {
        return this.isYawRelatvie;
    }

    public void setYawRelatvie(final boolean isYawRelatvie)
    {
        this.isYawRelatvie = isYawRelatvie;
    }

    public boolean isPitchRelatvie()
    {
        return this.isPitchRelatvie;
    }

    public void setPitchRelatvie(final boolean isPitchRelatvie)
    {
        this.isPitchRelatvie = isPitchRelatvie;
    }

    public byte getRelativeFlags()
    {
        byte flags = 0x00;
        if (this.isXRelatvie)
        {
            flags |= REL_X_FLAG;
        }
        if (this.isYRelatvie)
        {
            flags |= REL_Y_FLAG;
        }
        if (this.isZRelatvie)
        {
            flags |= REL_Z_FLAG;
        }
        if (this.isYawRelatvie)
        {
            flags |= REL_YAW_FLAG;
        }
        if (this.isPitchRelatvie)
        {
            flags |= REL_PITCH_FLAG;
        }
        return flags;
    }

    public void setRelativeFlags(final int flags)
    {
        this.isXRelatvie = (flags & REL_X_FLAG) != 0;
        this.isYRelatvie = (flags & REL_Y_FLAG) != 0;
        this.isZRelatvie = (flags & REL_Z_FLAG) != 0;
        this.isYawRelatvie = (flags & REL_YAW_FLAG) != 0;
        this.isPitchRelatvie = (flags & REL_PITCH_FLAG) != 0;
    }

    @Override
    public int hashCode()
    {
        int result;
        long temp;
        temp = Double.doubleToLongBits(this.x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.y);
        result = (31 * result) + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.z);
        result = (31 * result) + (int) (temp ^ (temp >>> 32));
        result = (31 * result) + ((this.yaw != + 0.0f) ? Float.floatToIntBits(this.yaw) : 0);
        result = (31 * result) + ((this.pitch != + 0.0f) ? Float.floatToIntBits(this.pitch) : 0);
        result = (31 * result) + (this.isXRelatvie ? 1 : 0);
        result = (31 * result) + (this.isYRelatvie ? 1 : 0);
        result = (31 * result) + (this.isZRelatvie ? 1 : 0);
        result = (31 * result) + (this.isYawRelatvie ? 1 : 0);
        result = (31 * result) + (this.isPitchRelatvie ? 1 : 0);
        return result;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof TeleportData))
        {
            return false;
        }

        final TeleportData that = (TeleportData) o;

        return (this.isPitchRelatvie == that.isPitchRelatvie) && (this.isXRelatvie == that.isXRelatvie) && (this.isYRelatvie == that.isYRelatvie) && (this.isYawRelatvie == that.isYawRelatvie) && (this.isZRelatvie == that.isZRelatvie) && (Float.compare(that.pitch, this.pitch) == 0) && (Double.compare(that.x, this.x) == 0) && (Double.compare(that.y, this.y) == 0) && (Float.compare(that.yaw, this.yaw) == 0) && (Double.compare(that.z, this.z) == 0);

    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("x", this.x).append("y", this.y).append("z", this.z).append("yaw", this.yaw).append("pitch", this.pitch).append("isXRelatvie", this.isXRelatvie).append("isYRelatvie", this.isYRelatvie).append("isZRelatvie", this.isZRelatvie).append("isYawRelatvie", this.isYawRelatvie).append("isPitchRelatvie", this.isPitchRelatvie).toString();
    }
}
