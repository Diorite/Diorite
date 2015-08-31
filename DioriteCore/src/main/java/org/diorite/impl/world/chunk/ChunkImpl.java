package org.diorite.impl.world.chunk;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.entity.EntityImpl;
import org.diorite.impl.pipelines.event.chunk.ChunkGeneratePipelineImpl;
import org.diorite.impl.world.TileEntityImpl;
import org.diorite.impl.world.WorldImpl;
import org.diorite.event.EventType;
import org.diorite.event.chunk.ChunkUnloadEvent;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.nbt.NbtTag;
import org.diorite.nbt.NbtTagCompound;
import org.diorite.utils.collections.arrays.NibbleArray;
import org.diorite.utils.collections.sets.ConcurrentSet;
import org.diorite.utils.concurrent.ParallelUtils;
import org.diorite.utils.concurrent.atomic.AtomicShortArray;
import org.diorite.world.Biome;
import org.diorite.world.Block;
import org.diorite.world.World;
import org.diorite.world.chunk.Chunk;
import org.diorite.world.chunk.ChunkPos;

import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TLongObjectHashMap;

public class ChunkImpl implements Chunk
{
    protected volatile Thread   lastTickThread;
    protected final    ChunkPos pos;
    protected final    int[]    heightMap;
    protected final AtomicBoolean populated = new AtomicBoolean(false);
    protected byte[]          biomes;
    protected ChunkPartImpl[] chunkParts; // size of 16, parts can be null

    @SuppressWarnings("MagicNumber")
    protected final TLongObjectMap<TileEntityImpl> tileEntities = new TLongObjectHashMap<>(10, .2f, Long.MAX_VALUE);
    protected final Set<EntityImpl>                entities     = new ConcurrentSet<>(4, .3f, 2);

    @Override
    public Thread getLastTickThread()
    {
        return this.lastTickThread;
    }

    @Override
    public boolean isValidSynchronizable()
    {
        return this.isLoaded();
    }

    public void setLastTickThread(final Thread lastTickThread)
    {
        this.lastTickThread = lastTickThread;
    }

    public ChunkImpl(final ChunkPos pos, final byte[] biomes, final ChunkPartImpl[] chunkParts, final int[] heightMap)
    {
        this.pos = pos;
        this.biomes = biomes;
        this.chunkParts = chunkParts;
        this.heightMap = heightMap;
    }

    public ChunkImpl(final ChunkPos pos, final byte[] biomes)
    {
        this.pos = pos;
        this.biomes = biomes;
        this.heightMap = new int[Chunk.CHUNK_SIZE * Chunk.CHUNK_SIZE];
    }

    public ChunkImpl(final ChunkPos pos, final ChunkPartImpl[] chunkParts)
    {
        this.pos = pos;
        this.chunkParts = chunkParts;
        this.heightMap = new int[Chunk.CHUNK_SIZE * Chunk.CHUNK_SIZE];
    }

    public ChunkImpl(final ChunkPos pos)
    {
        this.pos = pos;
        this.heightMap = new int[Chunk.CHUNK_SIZE * Chunk.CHUNK_SIZE];
    }

    @Override
    @SuppressWarnings("MagicNumber")
    public Biome getBiome(final int x, final int y, final int z) // y is ignored, added for future possible changes.
    {
        if ((this.biomes == null) && ! this.load())
        {
            return null;
        }
        return Biome.getByBiomeId(this.biomes[((z * Chunk.CHUNK_SIZE) + x)] & 0xFF);
    }

    public void setBiome(final int x, final int y, final int z, final Biome biome) // y is ignored, added for future possible changes.
    {
        if (this.biomes == null)
        {
            return;
        }
        this.biomes[((z * Chunk.CHUNK_SIZE) + x)] = (byte) biome.getBiomeId();
    }

    @Override
    public boolean isLoaded()
    {
        return this.chunkParts != null;
    }

    @Override
    public boolean load()
    {
        return this.load(true);
    }

    @Override
    public boolean load(final boolean generate)
    {
        return this.isLoaded() || this.pos.getWorld().getChunkManager().loadChunk(this.pos.getX(), this.pos.getZ(), generate);
    }

    @Override
    public boolean unload()
    {
        return this.unload(true, true);
    }

    @Override
    public boolean unload(final boolean save)
    {
        return this.unload(save, true);
    }

    @Override
    public boolean unload(final boolean save, final boolean safe)
    {
        if (! this.isLoaded())
        {
            return true;
        }
        final ChunkUnloadEvent unloadEvt = new ChunkUnloadEvent(this, safe);
        EventType.callEvent(unloadEvt);
        return ! unloadEvt.isCancelled();
    }

    public void setBiomes(final byte[] biomes)
    {
        this.biomes = biomes;
    }

    public void setChunkParts(final ChunkPartImpl[] chunkParts)
    {
        this.chunkParts = chunkParts;
    }

    public TLongObjectMap<TileEntityImpl> getTileEntities()
    {
        return this.tileEntities;
    }

    public Set<EntityImpl> getEntities()
    {
        return this.entities;
    }

    public boolean removeEntity(final EntityImpl entity)
    {
        return this.entities.remove(entity);
    }

    public boolean addEntity(final EntityImpl entity)
    {
        return this.entities.add(entity);
    }

    @Override
    public boolean isPopulated()
    {
        return this.populated.get();
    }

    public void setPopulated(final boolean populated)
    {
        this.populated.set(populated);
    }

    @Override
    public synchronized boolean populate()
    {
        if (! this.populated.get())
        {
            if (this.populated.compareAndSet(false, true))
            {
                ChunkGeneratePipelineImpl.addPops(this.pos);
                this.getWorld().getGenerator().getPopulators().forEach(pop -> pop.populate(this));
                return true;
            }
            return false;
        }
        return true;
    }

    @Override
    public void initHeightMap()
    {
        ParallelUtils.realParallelStream(() -> {
            IntStream.range(0, CHUNK_SIZE * CHUNK_SIZE).parallel().forEach(xz -> {
                final int x = xz / CHUNK_SIZE;
                final int z = xz % CHUNK_SIZE;
                this.heightMap[((z << 4) | x)] = - 1;
                for (int y = Chunk.CHUNK_FULL_HEIGHT - 1; y >= 0; y--)
                {
                    if (this.getBlockType(x, y, z).isSolid())
                    {
                        this.heightMap[((z << 4) | x)] = y;
                        return;
                    }
                }
            });
        }, "[" + this.pos.getX() + "," + this.pos.getZ() + "]initHeightMap");
    }

    public BlockMaterialData setBlock(final int x, final int y, final int z, final BlockMaterialData materialData)
    {
        final ChunkPartImpl chunkPart = this.getPart(y);
        final BlockMaterialData prev = chunkPart.setBlock(x, y % Chunk.CHUNK_PART_HEIGHT, z, materialData);

        final int hy = this.heightMap[((z << 4) | x)];
        if (y >= hy)
        {
            if (materialData.isSolid())
            {
                this.heightMap[((z << 4) | x)] = y;
            }
            else
            {
                for (int i = y - 1; i >= 0; i--)
                {
                    if (this.getBlockType(x, i, z).isSolid())
                    {
                        this.heightMap[((z << 4) | x)] = i;
                        break;
                    }
                    this.heightMap[((z << 4) | x)] = 0;
                }
            }
        }
//        ServerImpl.getInstance().getPlayersManager().forEach(p -> p.getPlayerChunks().getVisibleChunks().contains(this), new PacketPlayOutBlockChange(new BlockLocation(x + (this.pos.getX() << 4), y, z + (this.pos.getZ() << 4), this.getWorld()), materialData));
        this.checkPart(chunkPart);
        return prev;
    }

    public BlockMaterialData setBlock(final int x, final int y, final int z, final int id, final int meta)
    {
        return this.setBlock(x, y, z, BlockMaterialData.getByID(id, meta));
    }

    @Override
    public BlockMaterialData getBlockType(final int x, final int y, final int z)
    {
        final ChunkPartImpl chunkPart = this.chunkParts[(y >> 4)];
        if (chunkPart == null)
        {
            return Material.AIR;
        }
        return chunkPart.getBlockType(x, y % Chunk.CHUNK_PART_HEIGHT, z);
    }

    @Override
    public BlockMaterialData getHighestBlockType(final int x, final int z)
    {
        return this.getBlockType(x, ((z << 4) | x), z);
    }

    @Override
    public Block getBlock(final int x, final int y, final int z)
    {
        // TODO change when meta-data of block will be added
        return new org.diorite.impl.world.BlockImpl(x, y, z, this);
    }

    @Override
    public Block getHighestBlock(final int x, final int z)
    {
        // TODO change when meta-data of block will be added
        final int y = this.heightMap[((z << 4) | x)];
        if (y == - 1)
        {
            return null;
        }
        return this.getBlock(x, y, z);
    }

    @Override
    public int getHighestBlockY(final int x, final int z)
    {
        return this.heightMap[((z << 4) | x)];
    }

    @Override
    public WorldImpl getWorld()
    {
        return (WorldImpl) this.pos.getWorld();
    }

    @Override
    public ChunkPos getPos()
    {
        return this.pos;
    }

    @Override
    public int getX()
    {
        return this.pos.getX();
    }

    @Override
    public int getZ()
    {
        return this.pos.getZ();
    }

    public void recalculateBlockCounts()
    {
        for (final ChunkPartImpl chunkPart : this.chunkParts)
        {
            if (chunkPart == null)
            {
                continue;
            }
            chunkPart.recalculateBlockCount();
        }
    }

    private void checkPart(final ChunkPartImpl chunkPart)
    {
        if (chunkPart.getBlocksCount() <= 0)
        {
            this.chunkParts[chunkPart.getYPos()] = null;
        }
    }

    private ChunkPartImpl getPart(final int worldY)
    {
        final byte chunkPosY = (byte) (worldY >> 4);
        ChunkPartImpl chunkPart = this.chunkParts[chunkPosY];
        if (chunkPart == null)
        {
            chunkPart = new ChunkPartImpl(chunkPosY, this.getWorld().getDimension().hasSkyLight());
            this.chunkParts[chunkPosY] = chunkPart;
        }
        return chunkPart;
    }

    @SuppressWarnings("MagicNumber")
    public void loadFrom(final NbtTagCompound tag)
    {
        final List<NbtTagCompound> sectionList = tag.getList("Sections", NbtTagCompound.class);
        final ChunkPartImpl[] sections = new ChunkPartImpl[16];
        for (final NbtTagCompound sectionTag : sectionList)
        {
            final byte y = sectionTag.getByte("Y");
            final byte[] rawTypes = sectionTag.getByteArray("Blocks");
            final NibbleArray extTypes = sectionTag.containsTag("Add") ? new NibbleArray(sectionTag.getByteArray("Add")) : null;
            final NibbleArray data = new NibbleArray(sectionTag.getByteArray("Data"));
            final NibbleArray blockLight = new NibbleArray(sectionTag.getByteArray("BlockLight"));
            final NibbleArray skyLight = new NibbleArray(sectionTag.getByteArray("SkyLight"));

            final short[] types = new short[rawTypes.length];
            for (int i = 0; i < rawTypes.length; i++)
            {
                types[i] = (short) ((((extTypes == null) ? 0 : extTypes.get(i)) << 12) | ((rawTypes[i] & 0xff) << 4) | data.get(i));
            }
            sections[y] = new ChunkPartImpl(new AtomicShortArray(types), skyLight, blockLight, y);
        }
        this.chunkParts = new ChunkPartImpl[16];
        System.arraycopy(sections, 0, this.chunkParts, 0, sections.length);

        this.populated.set(tag.getBoolean("TerrainPopulated"));
        // TODO: load tile entites and other entities

        final byte[] biomes = tag.getByteArray("Biomes");
        if (biomes != null)
        {
            this.biomes = biomes;
        }
        else
        {
            this.biomes = new byte[CHUNK_BIOMES_SIZE];
        }
        final int[] heightMap = tag.getIntArray("HeightMap");
        if (heightMap != null)
        {
            System.arraycopy(tag.getIntArray("HeightMap"), 0, this.heightMap, 0, this.heightMap.length);
        }
        else
        {
            this.initHeightMap();
        }

        this.init();
//        tag.getBoolean("LightPopulated"); // TODO
//        tag.getLong("InhabitedTime"); // TODO
//
//        final List<NbtTagCompound> sectionsList = tag.getList("Sections", NbtTagCompound.class);
//        final boolean hasSkyLight = this.getWorld().getDimension().hasSkyLight();
//        for (final NbtTagCompound sectionNBT : sectionsList)
//        {
//            final byte posY = sectionNBT.getByte("Y");
//            final ChunkPartImpl chunkPart = new ChunkPartImpl(this, posY, hasSkyLight);
//            final byte[] blocksIDs = sectionNBT.getByteArray("Blocks");
//            final org.diorite.impl.world.chunk.ChunkNibbleArray blocksMetaData = new org.diorite.impl.world.chunk.ChunkNibbleArray(sectionNBT.getByteArray("Data"));
//            final org.diorite.impl.world.chunk.ChunkNibbleArray additionalData = Optional.ofNullable(sectionNBT.getByteArray("Add")).map(org.diorite.impl.world.chunk.ChunkNibbleArray::new).orElse(null);
//            final short[] blocks = new short[blocksIDs.length];
//            for (int i = 0; i < blocks.length; ++ i)
//            {
//                final int blockDataPos = (i >> 8) & 0xf;
//                final int blockIDPos = (i >> 4) & 0xf;
//                final int blockMetaPos = i & 0xf;
//                blocks[i] = (short) ((((additionalData != null) ? additionalData.get(blockMetaPos, blockDataPos, blockIDPos) : 0) << 12) | ((blocksIDs[i] & 0xff) << 4) | (blocksMetaData.get(blockMetaPos, (blockDataPos), (blockIDPos))));
//            }
//            chunkPart.setBlocks(new AtomicShortArray(blocks));
//            chunkPart.setBlockLight(new NibbleArray(sectionNBT.getByteArray("BlockLight")));
//            if (hasSkyLight)
//            {
//                chunkPart.setSkyLight(new NibbleArray(sectionNBT.getByteArray("SkyLight")));
//            }
//            chunkPart.recalculateBlockCount();
//            this.chunkParts[posY] = chunkPart;
//        }
    }

    @SuppressWarnings("MagicNumber")
    public NbtTagCompound writeTo(final NbtTagCompound tag)
    {
        tag.setByte("V", 1);
        tag.setInt("xPos", this.getX());
        tag.setInt("zPos", this.getZ());
        tag.setLong("LastUpdate", this.getWorld().getTime());
        tag.setIntArray("HeightMap", this.heightMap);
        tag.setBoolean("TerrainPopulated", this.populated.get());
        tag.setBoolean("LightPopulated", false); // TODO
        tag.setLong("InhabitedTime", 0); // TODO: value used to set local difficulty based on play time
        final List<NbtTag> sections = new ArrayList<>(16);
        final boolean hasSkyLight = this.getWorld().getDimension().hasSkyLight();
        for (final ChunkPartImpl chunkPart : this.chunkParts)
        {
            if (chunkPart == null)
            {
                continue;
            }
            final NbtTagCompound sectionNBT = new NbtTagCompound();
            sectionNBT.setByte("Y", chunkPart.getYPos());
            final byte[] blocksIDs = new byte[chunkPart.getBlocks().length()];
            final org.diorite.impl.world.chunk.ChunkNibbleArray blocksMetaData = new org.diorite.impl.world.chunk.ChunkNibbleArray();
            org.diorite.impl.world.chunk.ChunkNibbleArray additionalData = null;
            for (int i = 0; i < chunkPart.getBlocks().length(); ++ i)
            {
                final short block = chunkPart.getBlocks().get(i);
                final int blockMeta = i & 15;
                final int blockData = (i >> 8) & 15;
                final int blockID = (i >> 4) & 15;
                if ((block >> 12) != 0)
                {
                    if (additionalData == null)
                    {
                        additionalData = new ChunkNibbleArray();
                    }
                    additionalData.set(blockMeta, blockData, blockID, block >> 12);
                }
                blocksIDs[i] = (byte) ((block >> 4) & 255);
                blocksMetaData.set(blockMeta, blockData, blockID, block & 15);
            }
            sectionNBT.setByteArray("Blocks", blocksIDs);
            sectionNBT.setByteArray("Data", blocksMetaData.getRawData());
            if (additionalData != null)
            {
                sectionNBT.setByteArray("Add", additionalData.getRawData());
            }
            sectionNBT.setByteArray("BlockLight", chunkPart.getBlockLight().getRawData());
            if (hasSkyLight)
            {
                sectionNBT.setByteArray("SkyLight", chunkPart.getSkyLight().getRawData());
            }
            else
            {
                sectionNBT.setByteArray("SkyLight", new byte[chunkPart.getBlockLight().getRawData().length]);
            }
            sections.add(sectionNBT);
        }
        tag.setList("Sections", sections);
        tag.setByteArray("Biomes", this.biomes);
        tag.setList("Entities", new ArrayList<>(1));
        tag.setList("TileEntities", new ArrayList<>(1));
        return tag;
    }

    public byte[] getBiomes()
    {
        return this.biomes;
    }

//    public void setBlock(final int x, final int y, final int z, final int id, final int meta)
//    {
//        this.setBlock(x, y, z, id, meta);
//    }

    // set bit to 1: variable |= (1 << bit)
    // switch bit  : variable ^= (1 << bit)
    // set bit to 0: variable &= ~(1 << bit)
    public int getMask()
    {
        if (! this.isLoaded())
        {
            this.load();
        }
        int mask = 0x0;
        for (int i = 0, chunkPartsLength = this.chunkParts.length; i < chunkPartsLength; i++)
        {
            if ((this.chunkParts[i] != null) && ! this.chunkParts[i].isEmpty())
            {
                mask |= (1 << i);
            }
        }
        return mask;
    }

    public ChunkPartImpl[] getChunkParts()
    {
        return this.chunkParts;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("pos", this.pos).toString();
    }

    public void init()
    {
        // TODO: init tile entities and other stuff
    }

    public static ChunkImpl loadFromNBT(final World world, final NbtTagCompound tag)
    {
        if (tag == null)
        {
            return null;
        }
        final ChunkPos chunkPos = new ChunkPos(tag.getInt("xPos"), tag.getInt("zPos"), world);
        final ChunkImpl chunk = new ChunkImpl(chunkPos);
        chunk.loadFrom(tag);
        return chunk;
    }
}
