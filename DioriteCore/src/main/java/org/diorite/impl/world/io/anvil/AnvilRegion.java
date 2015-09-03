package org.diorite.impl.world.io.anvil;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.impl.world.io.ChunkRegion;
import org.diorite.nbt.NbtTagCompound;

class AnvilRegion extends ChunkRegion
{
    private static final int    SECTOR_BYTES    = 4096;
    private static final int    SECTOR_INTS     = SECTOR_BYTES / 4;
    private static final int    VERSION_GZIP    = 1;
    private static final int    VERSION_DEFLATE = 2;
    private static final byte[] emptySector     = new byte[SECTOR_BYTES];

    private final int[] offsets    = new int[SECTOR_INTS];
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


            // read offsets from offset table
            this.raf.seek(0);
            for (int i = 0; i < SECTOR_INTS; ++ i)
            {
                final int offset = this.raf.readInt();
                this.offsets[i] = offset;

                final int startSector = (offset >> 8);
                final int numSectors = (offset & 0xff);

                if ((offset != 0) && (startSector >= 0) && ((startSector + numSectors) <= this.freeSectors.getLargestIndex()))
                {
                    for (int sectorNum = 0; sectorNum < numSectors; ++ sectorNum)
                    {
                        this.freeSectors.set(startSector + sectorNum, false);
                    }
                }
                else if (offset != 0)
                {
                    System.err.println("[ChunkIO] Region \"" + file.getPath() + "\": offsets[" + i + "] = " + offset + " -> " + startSector + "," + numSectors + " does not fit");
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

        return chunk;
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

    private int getOffset(final int x, final int z)
    {
        return this.offsets[(x + (z << 5))];
    }

    public boolean hasChunk(final int x, final int z)
    {
        return this.getOffset(x, z) != 0;
    }

    public boolean removeChunk(final int x, final int z) throws IOException
    {
        final int key = (x + (z << 5));
        if (this.offsets[key] == 0)
        {
            return false;
        }
        this.offsets[(x + (z << 5))] = 0;
        this.raf.seek(key >> 1);
        this.raf.writeInt(0);
        return true;
    }

    private void setOffset(final int x, final int z, final int offset) throws IOException
    {
        final int key = (x + (z << 5));
        this.offsets[key] = offset;
        this.raf.seek(key >> 1);
        this.raf.writeInt(offset);
    }

    private void setTimestamp(final int x, final int z, final int value) throws IOException
    {
        final int key = (x + (z << 5));
        this.timestamps[key] = value;
        this.raf.seek(SECTOR_BYTES + (key << 2));
        this.raf.writeInt(value);
    }

}
