package org.diorite.impl.connection.packets.play.out;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayOutListener;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutMapChunk.ChunkPacketData;
import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.world.World;


@SuppressWarnings("MagicNumber")
@PacketClass(id = 0x26, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketPlayOutMapChunkBulk implements PacketPlayOut
{
    private int[]             xCords;
    private int[]             zCords;
    private ChunkPacketData[] datas;
    private boolean           hasSkyLight;
    private World             world;

    public PacketPlayOutMapChunkBulk()
    {
    }

    public PacketPlayOutMapChunkBulk(ChunkImpl[] chunks)
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
            final ChunkPacketData chunkData = PacketPlayOutMapChunk.createChunkPacketData(chunk, true, this.hasSkyLight, PacketPlayOutMapChunk.MASK);
            this.xCords[j] = chunk.getX();
            this.zCords[j] = chunk.getZ();
            this.datas[j] = chunkData;
        }
    }

    public PacketPlayOutMapChunkBulk(final Collection<ChunkImpl> chunks)
    {
        this(chunks.toArray(new ChunkImpl[chunks.size()]));
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
            this.datas[i].mask = (data.readShort() & PacketPlayOutMapChunk.MASK);
            this.datas[i].rawData = new byte[PacketPlayOutMapChunk.calcArraySize(Integer.bitCount(this.datas[i].mask), this.hasSkyLight, true)];
        }
        for (int j = 0; j < size; ++ j)
        {
            data.readBytes(this.datas[j].rawData);
        }
    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeBoolean(this.hasSkyLight);
        data.writeVarInt(this.datas.length);
        for (int i = 0; i < this.datas.length; ++ i)
        {
            data.writeInt(this.xCords[i]);
            data.writeInt(this.zCords[i]);
            data.writeShort(this.datas[i].mask & PacketPlayOutMapChunk.MASK);
        }
        for (final ChunkPacketData chunkData : this.datas)
        {
            // TODO: add x-ray protection
            data.writeBytes(chunkData.rawData);
        }
    }

    @Override
    public void handle(final PacketPlayOutListener listener)
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

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("xCords", this.xCords).append("zCords", this.zCords).append("datas", this.datas).append("hasSkyLight", this.hasSkyLight).append("world", this.world).toString();
    }
}
