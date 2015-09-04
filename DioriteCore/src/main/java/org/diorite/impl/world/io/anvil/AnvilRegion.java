package org.diorite.impl.world.io.anvil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.impl.world.io.ChunkRegion;
import org.diorite.nbt.NbtInputStream;
import org.diorite.nbt.NbtLimiter;
import org.diorite.nbt.NbtTagCompound;

/**
 * Every region contains up to 32x32 chunks, up to 1024 chunks per fire.<br>
 * Every file is divided into sectors, each sector have 4096 bytes (4KiB)<br>
 * Every file have at least two sectors:<br>
 * <ol>
 * <li>Contains chunk location, 4 bytes per chunk, indexed like (x + (z * 32)). <br>
 * First 3 bytes are for offset (in sectors), and last byte is for size. (also in sectors)<br>
 * Chunks will be always less than 1 MiB in size.</li>
 * <li>Contains chunk last modyfication timestamps, 4 bytes per chunk, indexed like ((x + (z * 32)) * 4).</li>
 * </ol>
 * Data of every chunks is stored in comppressed (mode=1 for GZip, mode=2 for Zlib, minecraft use only mode=2) NBT. <br>
 * Every chunk data starts with 5 byte header, first 4 bytes for length (in bytes + 1 byte for compression mode), and last byte for compression mode. <br>
 */
class AnvilRegion extends ChunkRegion
{
    private static final int    SECTOR_BYTES    = 4096;
    private static final int    SECTOR_INTS     = SECTOR_BYTES / 4;
    private static final int    VERSION_GZIP    = 1;
    private static final int    VERSION_DEFLATE = 2;
    private static final byte[] emptySector     = new byte[SECTOR_BYTES];

    private final int[] locations  = new int[SECTOR_INTS];
    private final int[] timestamps = new int[SECTOR_INTS];

    protected final RandomAccessFile raf;
    private         int              sizeDelta;
    private final   SectorsBitSet    freeSectors;

    AnvilRegion(final File file, final int x, final int z)
    {
        super(file, x, z);
        try
        {
            if (! file.exists())
            {
                file.getAbsoluteFile().getParentFile().mkdirs();
                file.createNewFile();
            }
            this.raf = new RandomAccessFile(file, "rw");
            if (this.raf.length() < (SECTOR_BYTES << 1))
            {
                this.raf.seek(0);
                this.raf.write(emptySector);
                this.raf.write(emptySector);
                this.sizeDelta += (SECTOR_BYTES << 1);
            }
            final int nSectors = (int) (this.file.length() / SECTOR_BYTES);
            this.freeSectors = new SectorsBitSet(nSectors);
            for (int i = 2; i < nSectors; ++ i)
            {
                this.freeSectors.set(i, true);
            }
            this.freeSectors.set(0, false);
            this.freeSectors.set(1, false);


            // read locations from offset table
            this.raf.seek(0);
            for (int i = 0; i < SECTOR_INTS; ++ i)
            {
                final int location = this.raf.readInt();
                this.locations[i] = location;

                final int offset = (location >> 8);
                final int size = (location & 0xff);

                if ((location != 0) && (offset >= 0) && ((offset + size) <= this.freeSectors.getLargestIndex()))
                {
                    for (int sectorNum = 0; sectorNum < size; ++ sectorNum)
                    {
                        this.freeSectors.set(offset + sectorNum, false);
                    }
                }
                else if (location != 0)
                {
                    System.err.println("[ChunkIO] Region \"" + file.getPath() + "\": locations[" + i + "] = " + location + " -> " + offset + ", " + size + " does not fit");
                }
            }
            // read timestamps from timestamp table
            for (int i = 0; i < SECTOR_INTS; ++ i)
            {
                this.timestamps[i] = this.raf.readInt();
            }
        } catch (final IOException e)
        {
            throw new RuntimeException("Can't create AnvilRegion(" + x + ", " + z + ") file: " + file.getPath(), e);
        }
    }

    @Override
    public void close()
    {
        try
        {
            this.raf.close();
        } catch (final IOException e)
        {
            System.err.println("[ChunkIO] Region \"" + this.file.getPath() + "\": can't be closed.");
            e.printStackTrace();
        }
    }

    @Override
    public ChunkImpl loadChunk(final int x, final int z, final ChunkImpl chunk)
    {
        try
        {
            final int location = this.getLocation(x, z);
            if (location == 0)
            {
                return null;
            }
            final int offset = location >> 8;
            final int size = location & 0xFF;
            if ((offset + size) > this.freeSectors.getLargestIndex())
            {
                throw new RuntimeException("Invalid sector: " + offset + "+" + size + " > " + this.freeSectors.getLargestIndex());
            }
            this.raf.seek(offset * SECTOR_BYTES);
            final int length = this.raf.readInt();
            if (length > (SECTOR_BYTES * size))
            {
                throw new RuntimeException("Invalid length: " + length + " > " + (SECTOR_BYTES * size));
            }
            try (final NbtInputStream stream = this.getStream(length, this.raf.readByte()))
            {
                chunk.loadFrom(((NbtTagCompound) stream.readTag(NbtLimiter.getUnlimited())).getCompound("Level"));
            }
            return chunk;
        } catch (final IOException e)
        {
            System.err.println("[ChunkIO] Region \"" + this.file.getPath() + "\": can't be loaded. region(" + this.x + ", " + this.z + "), local chunk(" + x + ", " + z + "), map chunk(" + ((this.x << 5) + x) + ", " + ((this.z << 5) + z) + ")");
            return null;
        }
    }

    private NbtInputStream getStream(final int length, final byte version) throws IOException
    {
        if (version == VERSION_GZIP)
        {
            final byte[] data = new byte[length - 1];
            this.raf.read(data);
            return new NbtInputStream(new GZIPInputStream(new ByteArrayInputStream(data)));
        }
        if (version == VERSION_DEFLATE)
        {
            final byte[] data = new byte[length - 1];
            this.raf.read(data);
            return new NbtInputStream(new InflaterInputStream(new ByteArrayInputStream(data)));
        }
        throw new RuntimeException("Unknown version: " + version);
    }

    @Override
    public boolean deleteChunk(final int x, final int z)
    {
        try
        {
            return this.removeChunk(x, z);
        } catch (final IOException e)
        {
            throw new RuntimeException("Can't delete chunk, region(" + this.x + ", " + this.z + "), local chunk(" + x + ", " + z + "), map chunk(" + ((this.x << 5) + x) + ", " + ((this.z << 5) + z) + ")", e);
        }
    }

    @Override
    public void saveChunk(final int x, final int z, final NbtTagCompound data)
    {

    }

    private void checkBounds(final int x, final int z)
    {
        if ((x < 0) || (x >= AnvilIO.REGION_SIZE) || (z < 0) || (z >= AnvilIO.REGION_SIZE))
        {
            throw new IllegalArgumentException("Chunk out of bounds: region(" + this.x + ", " + this.z + "), local chunk(" + x + ", " + z + "), map chunk(" + ((this.x << 5) + x) + ", " + ((this.z << 5) + z) + ")");
        }
    }

    private int getLocation(final int x, final int z)
    {
        return this.locations[(x + (z << 5))];
    }

    public boolean hasChunk(final int x, final int z)
    {
        return this.getLocation(x, z) != 0;
    }

    public boolean removeChunk(final int x, final int z) throws IOException
    {
        final int key = (x + (z << 5));
        if (this.locations[key] == 0)
        {
            return false;
        }
        this.locations[(x + (z << 5))] = 0;
        this.raf.seek(key >> 1);
        this.raf.writeInt(0);
        return true;
    }

    private void setOffset(final int x, final int z, final int location) throws IOException
    {
        final int key = (x + (z << 5));
        this.locations[key] = location;
        this.raf.seek(key >> 1);
        this.raf.writeInt(location);
    }

    private void setTimestamp(final int x, final int z, final int value) throws IOException
    {
        final int key = (x + (z << 5));
        this.timestamps[key] = value;
        this.raf.seek(SECTOR_BYTES + (key << 2));
        this.raf.writeInt(value);
    }

}
