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
import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.impl.world.chunk.ChunkPartImpl;

import org.diorite.utils.math.DioriteMathUtils;

import io.netty.buffer.Unpooled;

@SuppressWarnings("MagicNumber")
@PacketClass(id = 0x20, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 90000)
public class PacketPlayClientboundMapChunk extends PacketPlayClientbound
{
    public static final int MASK = 0xffff;

    private int     x; // 4 bytes
    private int     z; // 4 bytes
    private byte[]  data; // ~many bytes
    private boolean fullChunk; // 1 byte
    private boolean skyLight; // 1 byte
    private int     mask; // 4 byte

    public PacketPlayClientboundMapChunk()
    {
    }

    public PacketPlayClientboundMapChunk(final boolean fullChunk, final ChunkImpl chunk)
    {
        this.x = chunk.getPos().getX();
        this.z = chunk.getPos().getZ();
        this.fullChunk = fullChunk;
        this.mask = chunk.getMask(); // '\uFFFF';
        this.skyLight = chunk.getWorld().hasSkyLight();
        this.data = new byte[calcSize(chunk, fullChunk, this.skyLight, this.mask)];
        final PacketDataSerializer chunkSer = new PacketDataSerializer(Unpooled.wrappedBuffer(this.data));
        chunkSer.writerIndex(0);
        write(chunkSer, chunk, fullChunk, this.skyLight, this.mask);
    }

    public PacketPlayClientboundMapChunk(final boolean fullChunk, final ChunkImpl chunk, final boolean includeSkyLight)
    {
        this.x = chunk.getPos().getX();
        this.z = chunk.getPos().getZ();
        this.fullChunk = fullChunk;
        this.mask = chunk.getMask();
        this.skyLight = includeSkyLight;
        this.data = new byte[calcSize(chunk, fullChunk, this.skyLight, this.mask)];
        final PacketDataSerializer chunkSer = new PacketDataSerializer(Unpooled.wrappedBuffer(this.data));
        chunkSer.writerIndex(0);
        write(chunkSer, chunk, fullChunk, includeSkyLight, this.mask);
    }

    public PacketPlayClientboundMapChunk(final boolean fullChunk, final ChunkImpl chunk, final boolean includeSkyLight, final int mask)
    {
        this.x = chunk.getPos().getX();
        this.z = chunk.getPos().getZ();
        this.fullChunk = fullChunk;
        this.mask = mask;
        this.skyLight = includeSkyLight;
        this.data = new byte[calcSize(chunk, fullChunk, this.skyLight, this.mask)];
        final PacketDataSerializer chunkSer = new PacketDataSerializer(Unpooled.wrappedBuffer(this.data));
        chunkSer.writerIndex(0);
        write(chunkSer, chunk, fullChunk, includeSkyLight, mask);
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.x = data.readInt();
        this.z = data.readInt();
        this.fullChunk = data.readBoolean();
        this.mask = data.readVarInt();
        this.data = data.readByteWord();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeInt(this.x);
        data.writeInt(this.z);
        data.writeBoolean(this.fullChunk);
        data.writeVarInt(this.mask);
        data.writeByteWord(this.data);
    }

    @Override
    public void handle(final PacketPlayClientboundListener listener)
    {
        listener.handle(this);
    }

    public int getX()
    {
        return this.x;
    }

    public void setX(final int x)
    {
        this.x = x;
    }

    public int getZ()
    {
        return this.z;
    }

    public void setZ(final int z)
    {
        this.z = z;
    }

    public boolean isFullChunk()
    {
        return this.fullChunk;
    }

    public void setFullChunk(final boolean fullChunk)
    {
        this.fullChunk = fullChunk;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("x", this.x).append("z", this.z).append("fullChunk", this.fullChunk).append("skyLight", this.skyLight).append("mask", this.mask).toString();
    }

    protected static int write(final PacketDataSerializer data, final ChunkImpl chunk, final boolean full, final boolean skyLight, final int mask)
    {
        int size = 0;
        final ChunkPartImpl[] sections = chunk.getChunkParts();
        for (int i = 0, length = sections.length; i < length; ++ i)
        {
            final ChunkPartImpl section = sections[i];
            if ((section != null) && (! full || ! section.isEmpty()) && ((mask & (1 << i)) != 0))
            {
                size |= 1 << i;
                section.write(data);
                data.writeBytes(section.getBlockLight().getRawData());
                if (skyLight)
                {
                    data.writeBytes(section.getSkyLight().getRawData());
                }
            }
        }

        if (full)
        {
            data.writeBytes(chunk.getBiomes());
        }

        return size;
    }

    protected static int calcSize(final ChunkImpl chunk, final boolean full, final boolean skyLight, final int mask)
    {
        int bytes = 0;
        final ChunkPartImpl[] sections = chunk.getChunkParts();
        for (int length = sections.length, i = 0; i < length; ++ i)
        {
            final ChunkPartImpl section = sections[i];
            if ((section != null) && (! full || ! section.isEmpty()) && ((mask & (1 << i)) != 0))
            {
                bytes += 1 + section.getPalette().byteSize() + DioriteMathUtils.varintSize(i) + (section.getBlockData().size() * 8);
                bytes += section.getBlockLight().byteSize();
                if (skyLight)
                {
                    bytes += section.getSkyLight().byteSize();
                }
            }
        }
        if (full)
        {
            bytes += chunk.getBiomes().length;
        }
        return bytes;
    }
}
