package org.diorite.impl.world;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.Main;
import org.diorite.nbt.NbtInputStream;
import org.diorite.nbt.NbtOutputStream;

@SuppressWarnings("MagicNumber")
public class RegionFile
{
    static final         int    CHUNK_HEADER_SIZE = 5;
    private static final int    VERSION_GZIP      = 1;
    private static final int    VERSION_DEFLATE   = 2;
    private static final int    SECTOR_BYTES      = 4096;
    private static final int    SECTOR_INTS       = SECTOR_BYTES / 4;
    private static final byte[] emptySector       = new byte[4096];

    private final File               fileName;
    private final int[]              offsets;
    private final int[]              chunkTimestamps;
    private       RandomAccessFile   file;
    private       ArrayList<Boolean> sectorFree;
    private       int                sizeDelta;
    private long lastModified = 0;

    public RegionFile(final File path)
    {
        this.offsets = new int[SECTOR_INTS];
        this.chunkTimestamps = new int[SECTOR_INTS];

        this.fileName = path;

        this.sizeDelta = 0;

        try
        {
            if (path.exists())
            {
                this.lastModified = path.lastModified();
            }

            this.file = new RandomAccessFile(path, "rw");

            if (this.file.length() < SECTOR_BYTES)
            {
                /* we need to write the chunk offset table */
                for (int i = 0; i < SECTOR_INTS; ++ i)
                {
                    this.file.writeInt(0);
                }
                // write another sector for the timestamp info
                for (int i = 0; i < SECTOR_INTS; ++ i)
                {
                    this.file.writeInt(0);
                }

                this.sizeDelta += SECTOR_BYTES << 1;
            }

            if ((this.file.length() & 0xfff) != 0)
            {
                /* the file size is not a multiple of 4KB, grow it */
                for (int i = 0; i < (this.file.length() & 0xfff); ++ i)
                {
                    this.file.write((byte) 0);
                }
            }

            /* set up the available sector map */
            final int nSectors = (int) this.file.length() / SECTOR_BYTES;
            this.sectorFree = new ArrayList<>(nSectors);

            for (int i = 0; i < nSectors; ++ i)
            {
                this.sectorFree.add(true);
            }

            this.sectorFree.set(0, false); // chunk offset table
            this.sectorFree.set(1, false); // for the last modified info

            this.file.seek(0);
            for (int i = 0; i < SECTOR_INTS; ++ i)
            {
                final int offset = this.file.readInt();
                this.offsets[i] = offset;
                if ((offset != 0) && (((offset >> 8) + (offset & 0xFF)) <= this.sectorFree.size()))
                {
                    for (int sectorNum = 0; sectorNum < (offset & 0xFF); ++ sectorNum)
                    {
                        this.sectorFree.set((offset >> 8) + sectorNum, false);
                    }
                }
            }
            for (int i = 0; i < SECTOR_INTS; ++ i)
            {
                final int lastModValue = this.file.readInt();
                this.chunkTimestamps[i] = lastModValue;
            }
        } catch (final IOException e)
        {
            e.printStackTrace();
        }
    }

    /* the modification date of the region file when it was first opened */
    public long lastModified()
    {
        return this.lastModified;
    }

    /* gets how much the region file has grown since it was last checked */
    public synchronized int getSizeDelta()
    {
        final int ret = this.sizeDelta;
        this.sizeDelta = 0;
        return ret;
    }

    /*
     * gets an (uncompressed) stream representing the chunk data returns null if
     * the chunk is not found or an error occurs
     */
    public synchronized NbtInputStream getChunkDataInputStream(final int x, final int z)
    {
        if (this.outOfBounds(x, z))
        {
            return null;
        }

        try
        {
            final int offset = this.getOffset(x, z);
            if (offset == 0)
            {
                // debugln("READ", x, z, "miss");
                return null;
            }

            final int sectorNumber = offset >> 8;
            final int numSectors = offset & 0xFF;

            if ((sectorNumber + numSectors) > this.sectorFree.size())
            {
                return null;
            }

            this.file.seek(sectorNumber * SECTOR_BYTES);
            final int length = this.file.readInt();

            if (length > (SECTOR_BYTES * numSectors))
            {
                return null;
            }

            final byte version = this.file.readByte();

            if (version == VERSION_GZIP)
            {
                final byte[] data = new byte[length - 1];
                this.file.read(data);
                // debug("READ", x, z, " = found");
                return NbtInputStream.fromCompressed(new ByteArrayInputStream(data));
            }
            if (version == VERSION_DEFLATE)
            {
                final byte[] data = new byte[length - 1];
                this.file.read(data);
                // debug("READ", x, z, " = found");
                return NbtInputStream.fromInflated(new ByteArrayInputStream(data));
            }

            return null;
        } catch (final IOException e)
        {
            return null;
        }
    }

    public NbtOutputStream getChunkDataOutputStream(final int x, final int z)
    {
        if (this.outOfBounds(x, z))
        {
            return null;
        }

        return new NbtOutputStream(new DataOutputStream(new DeflaterOutputStream(new ChunkBuffer(x, z))));
    }

    /* write a chunk at (x,z) with length bytes of data to disk */
    protected synchronized void write(final int x, final int z, final byte[] data, final int length)
    {
        try
        {
            final int offset = this.getOffset(x, z);
            int sectorNumber = offset >> 8;
            final int sectorsAllocated = offset & 0xFF;
            final int sectorsNeeded = ((length + CHUNK_HEADER_SIZE) / SECTOR_BYTES) + 1;

            // maximum chunk size is 1MB
            if (sectorsNeeded >= 256)
            {
                return;
            }

            if ((sectorNumber != 0) && (sectorsAllocated == sectorsNeeded))
            {
                /* we can simply overwrite the old sectors */
                this.write(sectorNumber, data, length);
            }
            else
            {
                /* we need to allocate new sectors */

                /* mark the sectors previously used for this chunk as free */
                for (int i = 0; i < sectorsAllocated; ++ i)
                {
                    this.sectorFree.set(sectorNumber + i, true);
                }

                /* scan for a free space large enough to store this chunk */
                int runStart = this.sectorFree.indexOf(true);
                int runLength = 0;
                if (runStart != - 1)
                {
                    for (int i = runStart; i < this.sectorFree.size(); ++ i)
                    {
                        if (runLength != 0)
                        {
                            if (this.sectorFree.get(i))
                            {
                                runLength++;
                            }
                            else
                            {
                                runLength = 0;
                            }
                        }
                        else if (this.sectorFree.get(i))
                        {
                            runStart = i;
                            runLength = 1;
                        }
                        if (runLength >= sectorsNeeded)
                        {
                            break;
                        }
                    }
                }

                if (runLength >= sectorsNeeded)
                {
                    /* we found a free space large enough */
                    sectorNumber = runStart;
                    this.setOffset(x, z, (sectorNumber << 8) | sectorsNeeded);
                    for (int i = 0; i < sectorsNeeded; ++ i)
                    {
                        this.sectorFree.set(sectorNumber + i, false);
                    }
                    this.write(sectorNumber, data, length);
                }
                else
                {
                    /*
                     * no free space large enough found -- we need to grow the
                     * file
                     */
                    this.file.seek(this.file.length());
                    sectorNumber = this.sectorFree.size();
                    for (int i = 0; i < sectorsNeeded; ++ i)
                    {
                        this.file.write(emptySector);
                        this.sectorFree.add(false);
                    }
                    this.sizeDelta += SECTOR_BYTES * sectorsNeeded;

                    this.write(sectorNumber, data, length);
                    this.setOffset(x, z, (sectorNumber << 8) | sectorsNeeded);
                }
            }
            this.setTimestamp(x, z, (int) (System.currentTimeMillis() / 1000L));
        } catch (final IOException e)
        {
            e.printStackTrace();
        }
    }

    /* write a chunk data to the region file at specified sector number */
    private void write(final int sectorNumber, final byte[] data, final int length) throws IOException
    {
        this.file.seek(sectorNumber * SECTOR_BYTES);
        this.file.writeInt(length + 1); // chunk length
        this.file.writeByte(VERSION_DEFLATE); // chunk version number
        this.file.write(data, 0, length); // chunk data
    }

    /* is this an invalid chunk coordinate? */
    private boolean outOfBounds(final int x, final int z)
    {
        return (x < 0) || (x >= 32) || (z < 0) || (z >= 32);
    }

    private int getOffset(final int x, final int z)
    {
        return this.offsets[(x + (z << 5))];
    }

    public boolean hasChunk(final int x, final int z)
    {
        return this.getOffset(x, z) != 0;
    }

    private void setOffset(final int x, final int z, final int offset) throws IOException
    {
        this.offsets[(x + (z << 5))] = offset;
        this.file.seek((x + (z << 5)) << 2);
        this.file.writeInt(offset);
    }

    private void setTimestamp(final int x, final int z, final int value) throws IOException
    {
        this.chunkTimestamps[(x + (z << 5))] = value;
        this.file.seek(SECTOR_BYTES + ((x + (z << 5)) << 2));
        this.file.writeInt(value);
    }

    public void close() throws IOException
    {
        this.file.close();
    }

    /*
     * lets chunk writing be multithreaded by not locking the whole file as a
     * chunk is serializing -- only writes when serialization is over
     */
    class ChunkBuffer extends ByteArrayOutputStream
    {
        private final int x;
        private final int z;

        ChunkBuffer(final int x, final int z)
        {
            super(8096); // initialize to 8KB
            this.x = x;
            this.z = z;
        }

        @Override
        public void close()
        {
            RegionFile.this.write(this.x, this.z, this.buf, this.count);
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("x", this.x).append("z", this.z).toString();
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("fileName", this.fileName).append("file", this.file).append("sizeDelta", this.sizeDelta).append("lastModified", this.lastModified).toString();
    }
}
