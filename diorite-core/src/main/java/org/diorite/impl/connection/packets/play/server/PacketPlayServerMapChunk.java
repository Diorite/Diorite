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
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayServerListener;
import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.impl.world.chunk.ChunkPartImpl;
import org.diorite.world.chunk.ChunkPos;

@SuppressWarnings("MagicNumber")
@PacketClass(id = 0x21, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 90000)
public class PacketPlayServerMapChunk extends PacketPlayServer
{
    public static final int MASK = 0xffff;

    private int             x; // 4 bytes
    private int             z; // 4 bytes
    private ChunkPacketData data; // ~many bytes
    private boolean         fullChunk; // 1 byte
    private boolean         unload; // 1 byte

    public PacketPlayServerMapChunk()
    {
    }

    public PacketPlayServerMapChunk(final int x, final int z, final ChunkPacketData data, final boolean fullChunk)
    {
        this.x = x;
        this.z = z;
        this.data = data;
        this.fullChunk = fullChunk;
    }

    public PacketPlayServerMapChunk(final boolean fullChunk, final ChunkImpl chunk)
    {
        this.x = chunk.getPos().getX();
        this.z = chunk.getPos().getZ();
        this.fullChunk = fullChunk;
        this.data = createChunkPacketData(chunk, fullChunk, chunk.getWorld().hasSkyLight(), chunk.getMask());
    }

    public PacketPlayServerMapChunk(final boolean fullChunk, final ChunkImpl chunk, final boolean includeSkyLight)
    {
        this.x = chunk.getPos().getX();
        this.z = chunk.getPos().getZ();
        this.fullChunk = fullChunk;
        this.data = createChunkPacketData(chunk, fullChunk, includeSkyLight, chunk.getMask());
    }

    public PacketPlayServerMapChunk(final boolean fullChunk, final ChunkImpl chunk, final boolean includeSkyLight, final int mask)
    {
        this.x = chunk.getPos().getX();
        this.z = chunk.getPos().getZ();
        this.fullChunk = fullChunk;
        this.data = createChunkPacketData(chunk, fullChunk, includeSkyLight, mask);
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.x = data.readInt();
        this.z = data.readInt();
        this.fullChunk = data.readBoolean();
        this.data = new ChunkPacketData();
        this.data.mask = data.readShort();
        this.data.rawData = data.readByteWord();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeInt(this.x);
        data.writeInt(this.z);

        if (this.unload)
        {
            data.writeBoolean(true);
            data.writeShort(0);
            data.writeVarInt(0);
            return;
        }
        data.writeBoolean(this.fullChunk);
        data.writeShort(this.data.mask & MASK);
        data.writeBytes(this.data.rawData);
    }

    @Override
    public void handle(final PacketPlayServerListener listener)
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

    public boolean isUnload()
    {
        return this.unload;
    }

    public ChunkPacketData getData()
    {
        return this.data;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("x", this.x).append("z", this.z).append("data", this.data).append("fullChunk", this.fullChunk).append("unload", this.unload).toString();
    }

    public static PacketPlayServerMapChunk unload(final ChunkPos chunk)
    {
        final PacketPlayServerMapChunk packet = new PacketPlayServerMapChunk();
        packet.unload = true;
        packet.x = chunk.getX();
        packet.z = chunk.getZ();
        return packet;
    }

    protected static int calcArraySize(final int chunkParts, final boolean hasSkyLight, final boolean fullChunk)
    {
        final int blocksSize = chunkParts << 13; // 8192 bits per chunk part
        final int blockLight = chunkParts << 11; // 2048 bits per chunk part
        final int skyLightSize = hasSkyLight ? (chunkParts << 11) : 0; // 2048 bits per chunk part
        final int biomesSize = fullChunk ? 256 : 0;

        return blocksSize + blockLight + skyLightSize + biomesSize;
    }

    protected static ChunkPacketData createChunkPacketData(final ChunkImpl chunk, final boolean fullChunk, final boolean hasSkyLight, final int mask)
    {
        final ChunkPartImpl[] chunkParts = chunk.getChunkParts();
        final ChunkPacketData chunkPacketData = new ChunkPacketData();
        final Collection<ChunkPartImpl> chunkPartsList = new ArrayList<>(10);
        for (int j = 0; j < chunkParts.length; j++)
        {
            final ChunkPartImpl chunkPart = chunkParts[j];
            if ((chunkPart != null) && ((! fullChunk) || (! chunkPart.isEmpty())) && ((mask & (1 << j)) != 0))
            {
                chunkPacketData.mask |= 1 << j;
                chunkPartsList.add(chunkPart);
            }
        }
        chunkPacketData.rawData = new byte[calcArraySize(Integer.bitCount(chunkPacketData.mask), hasSkyLight, fullChunk)];
        int j = 0;
        for (final ChunkPartImpl chunkPart : chunkPartsList)
        {
            final short[] achar = chunkPart.getBlocks().getArray();
            for (int k = achar.length, l = 0; l < k; ++ l)
            {
                final short blockData = achar[l];
                chunkPacketData.rawData[j++] = (byte) (blockData & 0xff);
                chunkPacketData.rawData[j++] = (byte) ((blockData >> 8) & 0xff);
            }
        }
        for (final ChunkPartImpl chunkPart : chunkPartsList)
        {
            j = appendArray(chunkPart.getBlockLight().getRawData(), chunkPacketData.rawData, j);
        }
        if (hasSkyLight)
        {
            for (final ChunkPartImpl chunkPart : chunkPartsList)
            {
                j = appendArray(chunkPart.getSkyLight().getRawData(), chunkPacketData.rawData, j);
            }
        }
        if (fullChunk)
        {
            appendArray(chunk.getBiomes(), chunkPacketData.rawData, j);
        }
        return chunkPacketData;
    }

    protected static int appendArray(final byte[] sourceArray, final byte[] targetArray, final int currentLength)
    {
        System.arraycopy(sourceArray, 0, targetArray, currentLength, sourceArray.length);
        return currentLength + sourceArray.length;
    }

    public static class ChunkPacketData
    {
        protected byte[] rawData;
        protected int    mask;

        public byte[] getRawData()
        {
            return this.rawData;
        }

        public void setRawData(final byte[] rawData)
        {
            this.rawData = rawData;
        }

        public int getMask()
        {
            return this.mask;
        }

        public void setMask(final int mask)
        {
            this.mask = mask;
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("rawData", this.rawData).append("mask", this.mask).toString();
        }
    }
}
