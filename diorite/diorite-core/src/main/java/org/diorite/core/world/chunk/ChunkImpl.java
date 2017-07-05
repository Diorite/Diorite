/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.core.world.chunk;

import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockLocation;
import org.diorite.block.Block;
import org.diorite.commons.arrays.NibbleArray;
import org.diorite.core.DioriteCore;
import org.diorite.core.world.WorldImpl;
import org.diorite.core.world.chunk.palette.PaletteImpl;
import org.diorite.nbt.NbtTag;
import org.diorite.nbt.NbtTagCompound;
import org.diorite.world.Biome;
import org.diorite.world.World;
import org.diorite.world.chunk.Chunk;
import org.diorite.world.chunk.ChunkAnchor;
import org.diorite.world.chunk.ChunkPosition;

public class ChunkImpl implements Chunk
{
    private final Object lock = new Object();

    protected volatile @Nullable Thread        lastTickThread;
    protected final              ChunkPosition position;
    protected final              short[]       heightMap;
    protected final AtomicBoolean populated = new AtomicBoolean(false);
    protected byte[]          biomes;
    protected ChunkPartImpl[] chunkParts; // size of 16, parts can be null

//    protected final Long2ObjectMap<TileEntityImpl> tileEntities = new Long2ObjectOpenHashMap<>(1, .2f);
//    protected final Set<IEntity>                   entities     = new ConcurrentSet<>(4, .3f, 2);

    @Override @Nullable
    public Thread getLastTickThread()
    {
        return this.lastTickThread;
    }

    @Override
    public boolean isValidSynchronizable()
    {
        return this.isLoaded();
    }

    public void setLastTickThread(Thread lastTickThread)
    {
        this.lastTickThread = lastTickThread;
    }

    public ChunkImpl(ChunkPosition position, byte[] biomes, ChunkPartImpl[] chunkParts, short[] heightMap)
    {
        this.position = position;
        this.biomes = biomes;
        this.chunkParts = chunkParts;
        this.heightMap = heightMap;
    }

    public ChunkImpl(ChunkPosition position, byte[] biomes)
    {
        this.position = position;
        this.biomes = biomes;
        this.heightMap = new short[Chunk.CHUNK_SIZE * Chunk.CHUNK_SIZE];
    }

    public ChunkImpl(ChunkPosition position, ChunkPartImpl[] chunkParts)
    {
        this.position = position;
        this.chunkParts = chunkParts;
        this.heightMap = new short[Chunk.CHUNK_SIZE * Chunk.CHUNK_SIZE];
    }

    public ChunkImpl(ChunkPosition position)
    {
        this.position = position;
        this.heightMap = new short[Chunk.CHUNK_SIZE * Chunk.CHUNK_SIZE];
    }

    @Override
    @SuppressWarnings("MagicNumber")
    public Biome getBiome(int x, int y, int z) // y is ignored, added for future possible changes.
    {
        if ((this.biomes == null) && ! this.load())
        {
            return null;
        }
        return Biome.getByBiomeId(this.biomes[((z * Chunk.CHUNK_SIZE) + x)] & 0xFF);
    }

    public void setBiome(int x, int y, int z, Biome biome) // y is ignored, added for future possible changes.
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
    public boolean canBeUnloaded()
    {
        return false;
    }

    @Override
    public boolean load()
    {
        return this.load(true);
    }

    @Override
    public boolean load(boolean generate)
    {
        return this.isLoaded() || this.position.getWorld().getChunkManager().loadChunk(this.position.getX(), this.position.getZ(), generate);
    }

    @Override
    public boolean addAnchor(ChunkAnchor anchor)
    {
        return false;
    }

    @Override
    public boolean removeAnchor(ChunkAnchor anchor)
    {
        return false;
    }

    @Override
    public Collection<? extends ChunkAnchor> getAnchors()
    {
        return null;
    }

    @Override
    public boolean isEmpty()
    {
        return false;
    }

    @Override
    public BlockLocation getMaximalPoint()
    {
        return null;
    }

    @Override
    public boolean unload()
    {
        return this.unload(true, true);
    }

    @Override
    public boolean unload(boolean save)
    {
        return this.unload(save, true);
    }

    @Override
    public boolean unload(boolean save, boolean safe)
    {
        synchronized (this.lock)
        {
            if (! this.isLoaded())
            {
                return true;
            }
            ChunkUnloadEvent unloadEvt = new ChunkUnloadEvent(this, safe);
            EventType.callEvent(unloadEvt);
            return ! unloadEvt.isCancelled();
        }
    }

    public void setBiomes(byte[] biomes)
    {
        synchronized (this.lock)
        {
            this.biomes = biomes;
        }
    }

    public void setChunkParts(ChunkPartImpl[] chunkParts)
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

    public boolean removeEntity(IEntity entity)
    {
        return this.entities.remove(entity);
    }

    public boolean addEntity(IEntity entity)
    {
        return this.entities.add(entity);
    }

    @Override
    public boolean isPopulated()
    {
        return this.populated.get();
    }

    public void setPopulated(boolean populated)
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

    public BlockMaterialData setBlock(int x, int y, int z, BlockMaterialData materialData)
    {
        short sy = (short) y;

        ChunkPartImpl chunkPart = this.getPart(sy);
        BlockMaterialData prev = chunkPart.setBlock(x, sy % Chunk.CHUNK_PART_HEIGHT, z, materialData);

        short hy = this.heightMap[((z << 4) | x)];
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
        this.checkTileEntity(x, y, z);
        return prev;
    }

    public BlockMaterialData setBlock(int x, int y, int z, int id, int meta)
    {
        return this.setBlock(x, y, z, (BlockMaterialData) Material.getByID(id, meta));
    }

    @Override
    public BlockMaterialData getBlockType(int x, int y, int z)
    {
        ChunkPartImpl chunkPart = this.chunkParts[(y >> 4)];
        if (chunkPart == null)
        {
            return Material.AIR;
        }
        return chunkPart.getBlockType(x, y % Chunk.CHUNK_PART_HEIGHT, z);
    }

    @Override
    public BlockMaterialData getHighestBlockType(int x, int z)
    {
        return this.getBlockType(x, ((z << 4) | x), z);
    }

    @Override
    public Block getBlock(int x, int y, int z)
    {
        // TODO change when meta-data of block will be added
        return new BlockImpl(x, y, z, this);
    }

    @Override
    public Block getHighestBlock(int x, int z)
    {
        // TODO change when meta-data of block will be added
        int y = this.heightMap[((z << 4) | x)];
        if (y == - 1)
        {
            return null;
        }
        return this.getBlock(x, y, z);
    }

    @Override
    public int getHighestBlockY(int x, int z)
    {
        return this.heightMap[((z << 4) | x)];
    }

    @Override
    public WorldImpl getWorld()
    {
        return (WorldImpl) this.position.getWorld();
    }

    @Override
    public ChunkPosition getPosition()
    {
        return this.position;
    }

    @Override
    public boolean isValid()
    {
        return true;
    }

    @Override
    public int getX()
    {
        return this.position.getX();
    }

    @Override
    public int getZ()
    {
        return this.position.getZ();
    }

    public void recalculateBlockCounts()
    {
        for (ChunkPartImpl chunkPart : this.chunkParts)
        {
            if (chunkPart == null)
            {
                continue;
            }
            chunkPart.recalculateBlockCount();
        }
    }

    private void checkPart(ChunkPartImpl chunkPart)
    {
        if (chunkPart.getBlocksCount() <= 0)
        {
            this.chunkParts[chunkPart.getYPos()] = null;
        }
    }

    private ChunkPartImpl getPart(int worldY)
    {
        byte chunkPosY = (byte) (worldY >> 4);
        ChunkPartImpl chunkPart = this.chunkParts[chunkPosY];
        if (chunkPart == null)
        {
            chunkPart = new ChunkPartImpl(chunkPosY, this.getWorld().getDimension().hasSkyLight());
            this.chunkParts[chunkPosY] = chunkPart;
        }
        return chunkPart;
    }

    @SuppressWarnings("MagicNumber")
    public void loadFrom(NbtTagCompound tag)
    {
        boolean vc = this.getWorld().isVanillaCompatible();

        List<NbtTagCompound> sectionList = tag.getList("Sections", NbtTagCompound.class);
        ChunkPartImpl[] sections = new ChunkPartImpl[16];
        for (NbtTagCompound sectionTag : sectionList)
        {
            byte y = sectionTag.getByte("Y");
            byte[] rawTypes = sectionTag.getByteArray("Blocks");
            NibbleArray extTypes = sectionTag.containsTag("Add") ? new NibbleArray(sectionTag.getByteArray("Add")) : null;
            NibbleArray data = new NibbleArray(sectionTag.getByteArray("Data"));
            NibbleArray blockLight = new NibbleArray(sectionTag.getByteArray("BlockLight"));
            NibbleArray skyLight = new NibbleArray(sectionTag.getByteArray("SkyLight"));

            PaletteImpl palette = new PaletteImpl();
            int[] loading = new int[rawTypes.length];
            for (int i = 0; i < rawTypes.length; i++)
            {
                int k = ((((extTypes == null) ? 0 : extTypes.get(i)) << 12) | ((rawTypes[i] & 0xff) << 4) | data.get(i));
                if (Material.getByID(k >> 4, k & 15) == null)
                {
                    Material material = Material.getByID(k >> 4);
                    k = (material == null) ? 0 : material.getIdAndMeta();
//                    throw new IllegalArgumentException("Unknown material: " + k + " (" + (k >> 4) + ":" + (k & 15) + ")");
                }
                loading[i] = k;
            }
            ChunkBlockData cd = new ChunkBlockData(palette.bitsPerBlock(), ChunkPartImpl.CHUNK_DATA_SIZE);
            int k = 0;
            for (int i : loading)
            {
                cd.set(k++, palette.put(i));
            }
            ChunkPartImpl part = new ChunkPartImpl(cd, palette, skyLight, blockLight, y);
            sections[y] = part;
        }
        this.chunkParts = sections;
        this.recalculateBlockCounts();

        this.populated.set(tag.getBoolean("TerrainPopulated"));

        List<NbtTagCompound> entities = tag.getList("Entities", NbtTagCompound.class);
        for (NbtTagCompound entity : entities)
        {
            IEntity dioriteEntity;
            try
            {
                dioriteEntity = DioriteCore.getInstance().getServerManager().getEntityFactory().createEntity(entity, this.getWorld());
            } catch (Exception e)
            {
                System.err.println("Failed to load entity (" + entity + ")");
                continue;
            }
            this.getWorld().addEntity(dioriteEntity, false);
        }

        List<NbtTagCompound> nbtTileEntities = tag.getList("TileEntities", NbtTagCompound.class);
        for(NbtTagCompound nbtTileEntity : nbtTileEntities)
        {
            TileEntityImpl tileEntity;
            try
            {
                tileEntity = DioriteCore.getInstance().getServerManager().getTileEntityFactory().createTileEntity(nbtTileEntity, this.getWorld());
            }
            catch (Exception e)
            {
                System.err.println("Failed to load tile entity (" + nbtTileEntity + ")");
                continue;
            }
            this.tileEntities.put(tileEntity.getBlock().getLocation().asLong(), tileEntity);
        }

        byte[] biomes = tag.getByteArray("Biomes");
        if (biomes != null)
        {
            this.biomes = biomes;
        }
        else
        {
            this.biomes = new byte[CHUNK_BIOMES_SIZE];
        }
        short[] heightMap = tag.getShortArray("Diorite.HeightMap");
        if (vc || (heightMap == null))
        {
            int[] array = tag.getIntArray("HeightMap");
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
    @Nullable
    public NbtTagCompound writeTo(NbtTagCompound tag)
    {
        synchronized (this.lock)
        {
            if (! this.isLoaded())
            {
                return null;
            }
            boolean vc = this.getWorld().isVanillaCompatible();

            tag.setByte("V", 1);
            tag.setInt("xPos", this.getX());
            tag.setInt("zPos", this.getZ());
            tag.setLong("LastUpdate", this.getWorld().getTime());

            // TODO: add converter command/util, to convert worlds to diorite/vanilla type.
            if (vc)
            {
                int[] array = new int[this.heightMap.length];
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
            List<NbtTag> sections = new ArrayList<>(16);
            boolean hasSkyLight = this.getWorld().getDimension().hasSkyLight();
            for (ChunkPartImpl chunkPart : this.chunkParts)
            {
                if (chunkPart == null)
                {
                    continue;
                }
                NbtTagCompound sectionNBT = new NbtTagCompound();
                sectionNBT.setByte("Y", chunkPart.getYPos());
                ChunkBlockData data = chunkPart.getBlockData();
                PaletteImpl palette = chunkPart.getPalette();
                byte[] blocksIDs = new byte[ChunkPartImpl.CHUNK_DATA_SIZE];
                org.diorite.impl.world.chunk.ChunkNibbleArray blocksMetaData = new org.diorite.impl.world.chunk.ChunkNibbleArray();
                org.diorite.impl.world.chunk.ChunkNibbleArray additionalData = null;
                for (int i = 0; i < ChunkPartImpl.CHUNK_DATA_SIZE; ++ i)
                {
                    int block = data.getAsInt(i, palette);
                    int blockMeta = i & 15;
                    int blockData = (i >> 8) & 15;
                    int blockID = (i >> 4) & 15;
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

            List<NbtTag> entities = new ArrayList<>(this.getEntities().size());
            for (IEntity dioriteEntity : this.getEntities())
            {
                if (dioriteEntity instanceof IPlayer)
                {
                    continue;
                }
                NbtTagCompound entity = new NbtTagCompound();
                dioriteEntity.saveToNbt(entity);

                entities.add(entity);
            }

            tag.setList("Entities", entities);

            List<NbtTag> nbtTileEntities = new ArrayList<>(this.tileEntities.size());

            Collection<TileEntityImpl> tileEntitiesCollection = this.tileEntities.values();
            for (TileEntityImpl aTileEntitiesCollection : tileEntitiesCollection)
            {
                NbtTagCompound nbtTileEntity = new NbtTagCompound();
                aTileEntitiesCollection.saveToNbt(nbtTileEntity);

                nbtTileEntities.add(nbtTileEntity);
            }

            tag.setList("TileEntities", nbtTileEntities);
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("pos", this.position).toString();
    }

    public void init()
    {
        // TODO
    }

    @Override
    public BlockLocation getRelativeOrigin() throws IllegalStateException
    {
        return null;
    }

    @Override
    public int getSizeX()
    {
        return 0;
    }

    @Override
    public int getSizeY()
    {
        return 0;
    }

    @Override
    public int getSizeZ()
    {
        return 0;
    }

    @Override
    public Block getBlockAt(int x, int y, int z) throws IllegalArgumentException
    {
        return null;
    }

    public static ChunkImpl loadFromNBT(World world, NbtTagCompound tag)
    {
        if (tag == null)
        {
            return null;
        }
        ChunkPos chunkPos = new ChunkPos(tag.getInt("xPos"), tag.getInt("zPos"), world);
        ChunkImpl chunk = new ChunkImpl(chunkPos);
        chunk.loadFrom(tag);
        return chunk;
    }

    public void checkTileEntity(int x, int y, int z)
    {
        Block block = this.getBlock(x, y, z);
        long blockLocation = block.getLocation().asLong();
        if (this.tileEntities.containsKey(blockLocation))
        {
            if (block.getType().equals(BlockMaterialData.AIR))
            {
                this.tileEntities.remove(blockLocation);
            }
            //else if () TODO check if tile entity type match block type to prevent ClassCastException and other issues.
            {
                TileEntityImpl tileEntity = this.tileEntities.get(blockLocation);

            }
            return;
        }

        TileEntityImpl tileEntity = DioriteCore.getInstance().getServerManager().getTileEntityFactory().createTileEntity(block.getState());
        if (tileEntity == null)
        {
            return;
        }

        this.tileEntities.put(blockLocation, tileEntity);
    }

    @Override
    public TileEntity getTileEntity(Block block)
    {
        return this.tileEntities.get(block.getLocation().asLong());
    }
}
