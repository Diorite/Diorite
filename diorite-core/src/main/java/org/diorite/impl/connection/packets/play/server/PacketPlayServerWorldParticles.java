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
import org.diorite.Particle;

@PacketClass(id = 0x2A, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 50)
public class PacketPlayServerWorldParticles extends PacketPlayServer
{
    private Particle particle;  // 4 bytes
    private boolean  longDistance; // 1 byte
    private float    x; // 4 bytes
    private float    y; // 4 bytes
    private float    z; // 4 bytes
    private float    offsetX; // 4 bytes
    private float    offsetY; // 4 bytes
    private float    offsetZ; // 4 bytes
    private float    particleData; // 4 bytes
    private int      particleCount; // 4 bytes
    private int[]    data; // ~5 bytes

    public PacketPlayServerWorldParticles()
    {
    }

    public PacketPlayServerWorldParticles(final Particle particle, final boolean longDistance, final float x, final float y, final float z, final float offsetX, final float offsetY, final float offsetZ, final float particleData, final int particleCount, final int... data)
    {
        this.particle = particle;
        this.longDistance = longDistance;
        this.x = x;
        this.y = y;
        this.z = z;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.particleData = particleData;
        this.particleCount = particleCount;
        this.data = data;
    }

    public Particle getParticle()
    {
        return this.particle;
    }

    public void setParticle(final Particle particle)
    {
        this.particle = particle;
    }

    public boolean isLongDistance()
    {
        return this.longDistance;
    }

    public void setLongDistance(final boolean longDistance)
    {
        this.longDistance = longDistance;
    }

    public float getX()
    {
        return this.x;
    }

    public void setX(final float x)
    {
        this.x = x;
    }

    public float getY()
    {
        return this.y;
    }

    public void setY(final float y)
    {
        this.y = y;
    }

    public float getZ()
    {
        return this.z;
    }

    public void setZ(final float z)
    {
        this.z = z;
    }

    public float getOffsetX()
    {
        return this.offsetX;
    }

    public void setOffsetX(final float offsetX)
    {
        this.offsetX = offsetX;
    }

    public float getOffsetY()
    {
        return this.offsetY;
    }

    public void setOffsetY(final float offsetY)
    {
        this.offsetY = offsetY;
    }

    public float getOffsetZ()
    {
        return this.offsetZ;
    }

    public void setOffsetZ(final float offsetZ)
    {
        this.offsetZ = offsetZ;
    }

    public float getParticleData()
    {
        return this.particleData;
    }

    public void setParticleData(final float particleData)
    {
        this.particleData = particleData;
    }

    public int getParticleCount()
    {
        return this.particleCount;
    }

    public void setParticleCount(final int particleCount)
    {
        this.particleCount = particleCount;
    }

    public int[] getData()
    {
        return this.data;
    }

    public void setData(final int[] data)
    {
        this.data = data;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.particle = Particle.getByParticleId(data.readInt());
        this.longDistance = data.readBoolean();
        this.x = data.readFloat();
        this.y = data.readFloat();
        this.z = data.readFloat();
        this.offsetX = data.readFloat();
        this.offsetY = data.readFloat();
        this.offsetZ = data.readFloat();
        this.particleData = data.readFloat();
        this.particleCount = data.readInt();

        final int dataCount = this.particle.getDataSize();
        for (int i = 0; i < dataCount; i++)
        {
            this.data[i] = data.readVarInt();
        }
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeInt(this.particle.ordinal());
        data.writeBoolean(this.longDistance);
        data.writeFloat(this.x);
        data.writeFloat(this.y);
        data.writeFloat(this.z);
        data.writeFloat(this.offsetX);
        data.writeFloat(this.offsetY);
        data.writeFloat(this.offsetZ);
        data.writeFloat(this.particleData);
        data.writeInt(this.particleCount);

        final int dataCount = this.particle.getDataSize();
        for (int i = 0; i < dataCount; i++)
        {
            data.writeVarInt(this.data[i]);
        }
    }

    @Override
    public void handle(final PacketPlayServerListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("particle", this.particle).append("longDistance", this.longDistance).append("x", this.x).append("y", this.y).append("z", this.z).append("offsetX", this.offsetX).append("offsetY", this.offsetY).append("offsetZ", this.offsetZ).append("particleData", this.particleData).append("particleCount", this.particleCount).append("data", this.data).toString();
    }
}
