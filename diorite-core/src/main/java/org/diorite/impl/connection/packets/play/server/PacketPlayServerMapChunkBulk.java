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
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayServerListener;
import org.diorite.impl.connection.packets.play.server.PacketPlayServerMapChunk.ChunkPacketData;
import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.world.World;


@PacketClass(id = 0x26, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 500000)
public class PacketPlayServerMapChunkBulk extends PacketPlayServer
{
    public static final int HEADER_SIZE = 10;
    public static final int MAX_SIZE    = 0x1fff00; // 2096896

    private int[]             xCords;
    private int[]             zCords;
    private ChunkPacketData[] datas;
    private boolean           hasSkyLight;
    private World             world;

    public PacketPlayServerMapChunkBulk()
    {
    }

    public PacketPlayServerMapChunkBulk(final int[] xCords, final int[] zCords, final ChunkPacketData[] datas, final boolean hasSkyLight, final World world)
    {
        this.xCords = xCords;
        this.zCords = zCords;
        this.datas = datas;
        this.hasSkyLight = hasSkyLight;
        this.world = world;
    }

    public PacketPlayServerMapChunkBulk(final List<PacketPlayServerMapChunk> chunks, final World world)
    {
        final int size = chunks.size();
        this.xCords = new int[size];
        this.zCords = new int[size];
        this.datas = new ChunkPacketData[size];
        this.world = world;
        this.hasSkyLight = this.world.getDimension().hasSkyLight();
        for (int j = 0; j < size; ++ j)
        {
            final PacketPlayServerMapChunk chunk = chunks.get(j);
            final ChunkPacketData chunkData = chunk.getData();
            this.xCords[j] = chunk.getX();
            this.zCords[j] = chunk.getZ();
            this.datas[j] = chunkData;
        }
    }

    public PacketPlayServerMapChunkBulk(ChunkImpl[] chunks)
    {
        chunks = fix(chunks);
        final int size = chunks.length;
        this.xCords = new int[size];
        this.zCords = new int[size];
        this.datas = new ChunkPacketData[size];
        this.world = chunks[0].getWorld();
        this.hasSkyLight = this.world.getDimension().hasSkyLight();
        for (int j = 0; j < size; ++ j)
        {
            final ChunkImpl chunk = chunks[j];
            final ChunkPacketData chunkData = PacketPlayServerMapChunk.createChunkPacketData(chunk, true, this.hasSkyLight, PacketPlayServerMapChunk.MASK);
            this.xCords[j] = chunk.getX();
            this.zCords[j] = chunk.getZ();
            this.datas[j] = chunkData;
        }
    }

    public PacketPlayServerMapChunkBulk(final List<ChunkImpl> chunks)
    {
        fix(chunks);
        final int size = chunks.size();
        this.xCords = new int[size];
        this.zCords = new int[size];
        this.datas = new ChunkPacketData[size];
        this.world = chunks.get(0).getWorld();
        this.hasSkyLight = this.world.getDimension().hasSkyLight();
        for (int j = 0; j < size; ++ j)
        {
            final ChunkImpl chunk = chunks.get(j);
            final ChunkPacketData chunkData = PacketPlayServerMapChunk.createChunkPacketData(chunk, true, this.hasSkyLight, PacketPlayServerMapChunk.MASK);
            this.xCords[j] = chunk.getX();
            this.zCords[j] = chunk.getZ();
            this.datas[j] = chunkData;
        }
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.hasSkyLight = data.readBoolean();
        final int size = data.readVarInt();
        this.xCords = new int[size];
        this.zCords = new int[size];
        this.datas = new ChunkPacketData[size];
        for (int i = 0; i < size; ++ i)
        {
            this.xCords[i] = data.readInt();
            this.zCords[i] = data.readInt();
            this.datas[i] = new ChunkPacketData();
            this.datas[i].mask = (data.readShort() & PacketPlayServerMapChunk.MASK);
            this.datas[i].rawData = new byte[PacketPlayServerMapChunk.calcArraySize(Integer.bitCount(this.datas[i].mask), this.hasSkyLight, true)];
        }
        for (int j = 0; j < size; ++ j)
        {
            data.readBytes(this.datas[j].rawData);
        }
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeBoolean(this.hasSkyLight);
        data.writeVarInt(this.datas.length);
        for (int i = 0; i < this.datas.length; ++ i)
        {
            data.writeInt(this.xCords[i]);
            data.writeInt(this.zCords[i]);
            data.writeShort(this.datas[i].mask & PacketPlayServerMapChunk.MASK);
        }
        for (final ChunkPacketData chunkData : this.datas)
        {
            // TODO: add x-ray protection
            data.writeBytes(chunkData.rawData);
        }
    }

    @Override
    public void handle(final PacketPlayServerListener listener)
    {
        listener.handle(this);
    }

    public int[] getxCords()
    {
        return this.xCords;
    }

    public void setxCords(final int[] xCords)
    {
        this.xCords = xCords;
    }

    public int[] getzCords()
    {
        return this.zCords;
    }

    public void setzCords(final int[] zCords)
    {
        this.zCords = zCords;
    }

    public ChunkPacketData[] getDatas()
    {
        return this.datas;
    }

    public void setDatas(final ChunkPacketData[] datas)
    {
        this.datas = datas;
    }

    public boolean isHasSkyLight()
    {
        return this.hasSkyLight;
    }

    public void setHasSkyLight(final boolean hasSkyLight)
    {
        this.hasSkyLight = hasSkyLight;
    }

    public World getWorld()
    {
        return this.world;
    }

    public void setWorld(final World world)
    {
        this.world = world;
    }

    private static ChunkImpl[] fix(final ChunkImpl[] chunks)
    {
        final List<ChunkImpl> list = new ArrayList<>(chunks.length);
        for (final ChunkImpl chunk : chunks)
        {
            if (chunk != null)
            {
                list.add(chunk);
            }
        }
        return list.toArray(new ChunkImpl[list.size()]);
    }

    private static List<ChunkImpl> fix(final List<ChunkImpl> chunks)
    {
        chunks.removeIf(c -> c == null);
        return chunks;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("xCords", this.xCords).append("zCords", this.zCords).append("datas", this.datas).append("hasSkyLight", this.hasSkyLight).append("world", this.world).toString();
    }
}
