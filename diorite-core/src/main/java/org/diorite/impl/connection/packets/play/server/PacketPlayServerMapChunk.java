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
import java.util.Arrays;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayServerListener;
import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.impl.world.chunk.ChunkPartImpl;
import org.diorite.impl.world.chunk.ChunkPartImpl.ChunkSectionData;
import org.diorite.utils.math.DioriteMathUtils;

import io.netty.buffer.Unpooled;

@SuppressWarnings("MagicNumber")
@PacketClass(id = 0x20, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 90000)
public class PacketPlayServerMapChunk extends PacketPlayServer
{
    public static final int MASK = 0xffff;

    private int     x; // 4 bytes
    private int     z; // 4 bytes
    private byte[]  data; // ~many bytes
    private boolean fullChunk; // 1 byte
    private boolean skyLight; // 1 byte
    private int     mask; // 4 byte

    public PacketPlayServerMapChunk()
    {
    }

    public PacketPlayServerMapChunk(final boolean fullChunk, final ChunkImpl chunk)
    {
        this.x = chunk.getPos().getX();
        this.z = chunk.getPos().getZ();
        this.fullChunk = fullChunk;
        this.mask = chunk.getMask();
        this.skyLight = chunk.getWorld().hasSkyLight();
        this.data = writeChunkSimple(chunk, this.mask, chunk.getWorld().hasSkyLight(), fullChunk);
    }

    public PacketPlayServerMapChunk(final boolean fullChunk, final ChunkImpl chunk, final boolean includeSkyLight)
    {
        this.x = chunk.getPos().getX();
        this.z = chunk.getPos().getZ();
        this.fullChunk = fullChunk;
        this.mask = chunk.getMask();
        this.skyLight = includeSkyLight;
        this.data = writeChunkSimple(chunk, this.mask, includeSkyLight, fullChunk);
    }

    public PacketPlayServerMapChunk(final boolean fullChunk, final ChunkImpl chunk, final boolean includeSkyLight, final int mask)
    {
        this.x = chunk.getPos().getX();
        this.z = chunk.getPos().getZ();
        this.fullChunk = fullChunk;
        this.mask = mask;
        this.skyLight = includeSkyLight;
        this.data = writeChunkSimple(chunk, this.mask, includeSkyLight, fullChunk);
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
        System.out.println(Integer.toBinaryString(this.mask) + " ("+x+", "+this.z+"), " + this.data.length);
        data.writeByteWord(this.data);
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

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("x", this.x).append("z", this.z).append("fullChunk", this.fullChunk).append("skyLight", this.skyLight).append("mask", this.mask).toString();
    }

    public static byte[] writeChunkSimple(final ChunkImpl chunk, final int mask, final boolean skyLight, final boolean groundUpContinuous) // groundUpContinuous, with biomes
    {
        final ChunkPartImpl[] chunkParts = chunk.getChunkParts(); // get all chunk parts

        final byte chunkPartsCount = DioriteMathUtils.countBits(chunk.getMask()); // number of chunks to sent
        final ChunkSectionData[] chunkPartsToSent = new ChunkSectionData[chunkPartsCount];

        for (int i = 0, j = 0, localMask = 1; i < chunkParts.length; ++ i, localMask <<= 1)
        {
            if ((mask & localMask) != 0)
            {
                chunkPartsToSent[j++] = new ChunkSectionData(chunkParts[i], skyLight);
            }
        }
        final byte[] biomes = chunk.getBiomes();
        int fullSize = groundUpContinuous ? biomes.length : 0;
        for (final ChunkSectionData chunkPart : chunkPartsToSent)
        {
            fullSize += chunkPart.size + PacketDataSerializer.varintSize(chunkPart.size);
        }
        final PacketDataSerializer buf = new PacketDataSerializer(Unpooled.buffer(fullSize + 1000));
        buf.resetWriterIndex();
        for (final ChunkSectionData chunkPart : chunkPartsToSent)
        {
            chunkPart.pattern.write(buf);
            buf.writeVarInt(chunkPart.size);
            byte[] test = new byte[chunkPart.blocks.length];
            Arrays.fill(test, (byte) 1);
            buf.writeBytes(test);
//            buf.writeBytes(chunkPart.blocks);
            buf.writeBytes(chunkPart.blockLight);
            if (skyLight)
            {
                buf.writeBytes(chunkPart.skyLight);
            }
        }
        if (groundUpContinuous)
        {
            buf.writeBytes(biomes);
        }
        buf.resetReaderIndex();
        final byte[] result;
        buf.readBytes(result = new byte[buf.writerIndex()]);
        buf.release();
        return result;
    }

}
