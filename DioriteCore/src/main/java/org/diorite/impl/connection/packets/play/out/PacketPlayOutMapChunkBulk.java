package org.diorite.impl.connection.packets.play.out;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayOutListener;
import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.impl.world.chunk.ChunkPartImpl;
import org.diorite.world.chunk.Chunk;
import org.diorite.world.chunk.ChunkPos;


@PacketClass(id = 0x26, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketPlayOutMapChunkBulk implements PacketPlayOut
{
    private boolean     skyLight;
    private ChunkMeta[] metas;
    private Chunk[]     chunks;

    public PacketPlayOutMapChunkBulk()
    {
    }

    public PacketPlayOutMapChunkBulk(final boolean skyLight, Chunk[] chunks)
    {
        chunks = fix(chunks);
        this.skyLight = skyLight;
        this.chunks = chunks;

        this.metas = new ChunkMeta[chunks.length];
        for (int k = 0, chunksLength = chunks.length; k < chunksLength; k++)
        {
            final ChunkImpl chunk = (ChunkImpl) chunks[k];
            int mask = chunk.getMask();

            final ChunkPartImpl[] chunkParts = chunk.getChunkParts();
            for (int i = 0, chunkPartsLength = chunkParts.length; i < chunkPartsLength; i++)
            {
                final ChunkPartImpl part = chunkParts[i];
                if (part == null)
                {
                    mask &= ~ (1 << i);
                }
            }

            this.metas[k] = new ChunkMeta(chunk.getPos(), mask);
        }
    }

    public PacketPlayOutMapChunkBulk(final boolean skyLight, Chunk[] chunks, final ChunkMeta[] metas)
    {
        chunks = fix(chunks);
        this.skyLight = skyLight;
        this.chunks = chunks;
        this.metas = metas;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        // TODO: implement
    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeBoolean(this.skyLight);
        data.writeVarInt(this.chunks.length);
        for (final ChunkMeta meta : this.metas)
        {
            data.writeInt(meta.getPos().getX());
            data.writeInt(meta.getPos().getZ());
            data.writeShort(meta.getMask());
        }
        for (int i = 0, chunks1Length = this.chunks.length; i < chunks1Length; i++)
        {
            final ChunkImpl chunk = (ChunkImpl) this.chunks[i];
            data.writeChunkSimple(chunk, this.metas[i].getMask(), this.skyLight, true, false);
        }
    }

    @Override
    public void handle(final PacketPlayOutListener listener)
    {
        listener.handle(this);
    }

    public boolean isSkyLight()
    {
        return this.skyLight;
    }

    public void setSkyLight(final boolean skyLight)
    {
        this.skyLight = skyLight;
    }

    public ChunkMeta[] getMetas()
    {
        return this.metas;
    }

    public void setMetas(final ChunkMeta[] metas)
    {
        this.metas = metas;
    }

    public Chunk[] getChunks()
    {
        return this.chunks;
    }

    public void setChunks(final ChunkImpl[] chunks)
    {
        this.chunks = chunks;
    }

    private static Chunk[] fix(final Chunk[] chunks)
    {
        final List<Chunk> list = new ArrayList<>(chunks.length);
        for (final Chunk chunk : chunks)
        {
            if (chunk != null)
            {
                list.add(chunk);
            }
        }
        return list.toArray(new Chunk[list.size()]);
    }

    public static class ChunkMeta
    {
        private ChunkPos pos;
        private int      mask;

        public ChunkMeta(final ChunkPos pos, final int mask)
        {
            this.pos = pos;
            this.mask = mask;
        }

        public ChunkPos getPos()
        {
            return this.pos;
        }

        public void setPos(final ChunkPos pos)
        {
            this.pos = pos;
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
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("pos", this.pos).append("mask", this.mask).toString();
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("skyLight", this.skyLight).append("metas", this.metas).append("chunks", this.chunks).toString();
    }
}
