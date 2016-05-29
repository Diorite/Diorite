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

package org.diorite.impl.world.chunk;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.entity.IEntity;
import org.diorite.impl.world.TileEntityImpl;
import org.diorite.impl.world.WorldImpl;
import org.diorite.impl.world.chunk.palette.PaletteImpl;
import org.diorite.event.EventType;
import org.diorite.event.chunk.ChunkUnloadEvent;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.nbt.NbtTag;
import org.diorite.nbt.NbtTagCompound;
import org.diorite.utils.collections.arrays.NibbleArray;
import org.diorite.utils.collections.sets.ConcurrentSet;
import org.diorite.world.Biome;
import org.diorite.world.Block;
import org.diorite.world.World;
import org.diorite.world.chunk.Chunk;
import org.diorite.world.chunk.ChunkPos;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;

public class ChunkImpl implements Chunk
{
    private final Object lock = new Object();

    protected volatile Thread   lastTickThread;
    protected final    ChunkPos pos;
    protected final    short[]  heightMap;
    protected final AtomicBoolean populated = new AtomicBoolean(false);
    protected byte[]          biomes;
    protected ChunkPartImpl[] chunkParts; // size of 16, parts can be null

    protected final Long2ObjectMap<TileEntityImpl> tileEntities = new Long2ObjectOpenHashMap<>(1, .2f);
    protected final Set<IEntity>                   entities     = new ConcurrentSet<>(4, .3f, 2);

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

    public ChunkImpl(final ChunkPos pos, final byte[] biomes, final ChunkPartImpl[] chunkParts, final short[] heightMap)
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
        this.heightMap = new short[Chunk.CHUNK_SIZE * Chunk.CHUNK_SIZE];
    }

    public ChunkImpl(final ChunkPos pos, final ChunkPartImpl[] chunkParts)
    {
        this.pos = pos;
        this.chunkParts = chunkParts;
        this.heightMap = new short[Chunk.CHUNK_SIZE * Chunk.CHUNK_SIZE];
    }

    public ChunkImpl(final ChunkPos pos)
    {
        this.pos = pos;
        this.heightMap = new short[Chunk.CHUNK_SIZE * Chunk.CHUNK_SIZE];
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
        synchronized (this.lock)
        {
            if (! this.isLoaded())
            {
                return true;
            }
            final ChunkUnloadEvent unloadEvt = new ChunkUnloadEvent(this, safe);
            EventType.callEvent(unloadEvt);
            return ! unloadEvt.isCancelled();
        }
    }

    public void setBiomes(final byte[] biomes)
    {
        synchronized (this.lock)
        {
            this.biomes = biomes;
        }
    }

    public void setChunkParts(final ChunkPartImpl[] chunkParts)
    {
        synchronized (this.lock)
        {
            this.chunkParts = chunkParts;
        }
    }

    public Long2ObjectMap<TileEntityImpl> getTileEntities()
    {
        return this.tileEntities;
    }

    public Set<IEntity> getEntities()
    {
        return this.entities;
    }

    public boolean removeEntity(final IEntity entity)
    {
        return this.entities.remove(entity);
    }

    public boolean addEntity(final IEntity entity)
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
        synchronized (this.lock)
        {
            if (! this.populated.get())
            {
                if (this.populated.compareAndSet(false, true))
                {
                    this.getWorld().getGenerator().getPopulators().forEach(pop -> pop.populate(this));
                    return true;
                }
                return false;
            }
            return true;
        }
    }

    @Override
    public void initHeightMap()
    {
        synchronized (this.lock)
        {
            for (int x = 0; x < CHUNK_SIZE; x++)
            {
                for (int z = 0; z < CHUNK_SIZE; z++)
                {
                    this.heightMap[((z << 4) | x)] = - 1;
                    for (short y = Chunk.CHUNK_FULL_HEIGHT - 1; y >= 0; y--)
                    {
                        if (this.getBlockType(x, y, z).isSolid())
                        {
                            this.heightMap[((z << 4) | x)] = y;
                            break;
                        }
                    }
                }
            }
        }
    }

    public BlockMaterialData setBlock(final int x, final int y, final int z, final BlockMaterialData materialData)
    {
        final short sy = (short) y;

        final ChunkPartImpl chunkPart = this.getPart(sy);
        final BlockMaterialData prev = chunkPart.setBlock(x, sy % Chunk.CHUNK_PART_HEIGHT, z, materialData);

        final short hy = this.heightMap[((z << 4) | x)];
        if (sy >= hy)
        {
            if (materialData.isSolid())
            {
                this.heightMap[((z << 4) | x)] = sy;
            }
            else
            {
                for (short i = (short) (sy - 1); i >= 0; i--)
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
        return this.setBlock(x, y, z, (BlockMaterialData) Material.getByID(id, meta));
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
        final boolean vc = this.getWorld().isVanillaCompatible();

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

            final PaletteImpl palette = new PaletteImpl();
            final int[] loading = new int[rawTypes.length];
            for (int i = 0; i < rawTypes.length; i++)
            {
                int k = ((((extTypes == null) ? 0 : extTypes.get(i)) << 12) | ((rawTypes[i] & 0xff) << 4) | data.get(i));
                if (Material.getByID(k >> 4, k & 15) == null)
                {
                    final Material material = Material.getByID(k >> 4);
                    k = (material == null) ? 0 : material.getIdAndMeta();
//                    throw new IllegalArgumentException("Unknown material: " + k + " (" + (k >> 4) + ":" + (k & 15) + ")");
                }
                loading[i] = k;
            }
            final ChunkBlockData cd = new ChunkBlockData(palette.bitsPerBlock(), ChunkPartImpl.CHUNK_DATA_SIZE);
            int k = 0;
            for (final int i : loading)
            {
                cd.set(k++, palette.put(i));
            }
            final ChunkPartImpl part = new ChunkPartImpl(cd, palette, skyLight, blockLight, y);
            sections[y] = part;
        }
        this.chunkParts = sections;
        this.recalculateBlockCounts();

        this.populated.set(tag.getBoolean("TerrainPopulated"));

        final List<NbtTagCompound> entities = tag.getList("Entities", NbtTagCompound.class);
        for (final NbtTagCompound entity : entities)
        {
            final IEntity dioriteEntity;
            try
            {
                dioriteEntity = DioriteCore.getInstance().getServerManager().getEntityFactory().createEntity(entity, this.getWorld());
            }
            catch (final Exception e)
            {
                System.err.println("Failed to load entity (" + entity + ")");
                continue;
            }

            this.getWorld().addEntity(dioriteEntity, false);
            dioriteEntity.updateChunk(null, this);
        }

        // TODO tile entities

        final byte[] biomes = tag.getByteArray("Biomes");
        if (biomes != null)
        {
            this.biomes = biomes;
        }
        else
        {
            this.biomes = new byte[CHUNK_BIOMES_SIZE];
        }
        final short[] heightMap = tag.getShortArray("Diorite.HeightMap");
        if (vc || (heightMap == null))
        {
            final int[] array = tag.getIntArray("HeightMap");
            if (array != null)
            {
                for (int i = 0; i < this.heightMap.length; i++)
                {
                    this.heightMap[i] = (short) array[i];
                }
            }
            else
            {
                this.initHeightMap();
            }
        }
        else
        {
            System.arraycopy(heightMap, 0, this.heightMap, 0, this.heightMap.length);
        }

        this.init();
//        tag.getBoolean("LightPopulated"); // TODO
//        tag.getLong("InhabitedTime"); // TODO
    }

    @SuppressWarnings("MagicNumber")
    public NbtTagCompound writeTo(final NbtTagCompound tag)
    {
        synchronized (this.lock)
        {
            if (! this.isLoaded())
            {
                return null;
            }
            final boolean vc = this.getWorld().isVanillaCompatible();

            tag.setByte("V", 1);
            tag.setInt("xPos", this.getX());
            tag.setInt("zPos", this.getZ());
            tag.setLong("LastUpdate", this.getWorld().getTime());

            // TODO: add converter command/util, to convert worlds to diorite/vanilla type.
            if (vc)
            {
                final int[] array = new int[this.heightMap.length];
                for (int i = 0; i < this.heightMap.length; i++)
                {
                    array[i] = this.heightMap[i];
                }
                tag.setIntArray("HeightMap", array);
            }
            else
            {
                tag.setShortArray("Diorite.HeightMap", this.heightMap);
            }

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
                final ChunkBlockData data = chunkPart.getBlockData();
                final PaletteImpl palette = chunkPart.getPalette();
                final byte[] blocksIDs = new byte[ChunkPartImpl.CHUNK_DATA_SIZE];
                final org.diorite.impl.world.chunk.ChunkNibbleArray blocksMetaData = new org.diorite.impl.world.chunk.ChunkNibbleArray();
                org.diorite.impl.world.chunk.ChunkNibbleArray additionalData = null;
                for (int i = 0; i < ChunkPartImpl.CHUNK_DATA_SIZE; ++ i)
                {
                    final int block = data.getAsInt(i, palette);
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
            if (this.biomes != null)
            {
                tag.setByteArray("Biomes", this.biomes);
            }

            final List<NbtTag> entities = new ArrayList<>(this.getEntities().size());
            for (final IEntity dioriteEntity : this.getEntities())
            {
                final NbtTagCompound entity = new NbtTagCompound();
                dioriteEntity.saveToNbt(entity);

                entities.add(entity);
            }

            tag.setList("Entities", entities);
            tag.setList("TileEntities", new ArrayList<>(1)); // TODO
            return tag;
        }
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
        // TODO
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
