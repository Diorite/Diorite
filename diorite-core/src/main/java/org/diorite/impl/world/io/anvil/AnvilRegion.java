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

package org.diorite.impl.world.io.anvil;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.impl.world.io.ChunkRegion;
import org.diorite.nbt.NbtInputStream;
import org.diorite.nbt.NbtLimiter;
import org.diorite.nbt.NbtOutputStream;
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
@SuppressWarnings({"ClassHasNoToStringMethod", "MagicNumber"})
class AnvilRegion extends ChunkRegion
{
    private static final int    SECTOR_BYTES    = 4096;
    private static final int    SECTOR_INTS     = SECTOR_BYTES / 4;
    private static final byte   VERSION_GZIP    = 1;
    private static final byte   VERSION_DEFLATE = 2;
    private static final byte[] emptySector     = new byte[SECTOR_BYTES];

    private final int[] locations = new int[SECTOR_INTS];

    protected final   RandomAccessFile raf;
    private final     SectorsBitSet    freeSectors;
    private transient FileChannel      channel;

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
            // time stamps are not used at all.
//            for (int i = 0; i < SECTOR_INTS; ++ i)
//            {
//                this.timestamps[i] = this.raf.readInt();
//            }
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
            try (final NbtInputStream stream = this.getInputStream(length, this.raf.readByte()))
            {
                chunk.loadFrom(((NbtTagCompound) stream.readTag(NbtLimiter.getUnlimited())).getCompound("Level"));
            }
            return chunk;
        } catch (final IOException e)
        {
            System.err.println("[ChunkIO] Region \"" + this.file.getPath() + "\": can't be loaded. region(" + this.x + ", " + this.z + "), local chunk(" + x + ", " + z + "), map chunk(" + ((this.x << 5) + x) + ", " + ((this.z << 5) + z) + ")");
            e.printStackTrace();
            return null;
        }
    }

    private NbtInputStream getInputStream(final int length, final byte version) throws IOException
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

    private NbtOutputStream getOutputStream(final int x, final int z, final byte version) throws IOException
    {
        this.checkBounds(x, z);
        if (version == VERSION_GZIP)
        {
            return new NbtOutputStream(new BufferedOutputStream(new GZIPOutputStream(new ChunkBuffer(x, z))));
        }
        if (version == VERSION_DEFLATE)
        {
            return new NbtOutputStream(new BufferedOutputStream(new DeflaterOutputStream(new ChunkBuffer(x, z), new Deflater(Deflater.BEST_SPEED))));
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
        try
        {
            try (final NbtOutputStream stream = this.getOutputStream(x, z, VERSION_DEFLATE))
            {
                stream.write(data);
            }
        } catch (final IOException e)
        {
            System.err.println("[ChunkIO] Region \"" + this.file.getPath() + "\": can't be loaded. region(" + this.x + ", " + this.z + "), local chunk(" + x + ", " + z + "), map chunk(" + ((this.x << 5) + x) + ", " + ((this.z << 5) + z) + ")");
        }
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

    private void setLocation(final int x, final int z, final int location) throws IOException
    {
        final int key = (x + (z << 5));
        this.locations[key] = location;
        this.raf.seek(key << 2);
        this.raf.writeInt(location);
    }

    private void setTimestamp(final int x, final int z, final int value) throws IOException
    {
        final int key = (x + (z << 5));
//        this.timestamps[key] = value;
        this.raf.seek(SECTOR_BYTES + (key << 2));
        this.raf.writeInt(value);
    }

    class ChunkBuffer extends ByteArrayOutputStream
    {
        private static final int SIZE = 8096;
        private final int x, z;

        ChunkBuffer(final int x, final int z)
        {
            super(SIZE); // initialize to 8KB
            this.x = x;
            this.z = z;
        }

        @Override
        public void close() throws IOException
        {
            try
            {
                AnvilRegion.this.write(this.x, this.z, this.buf, this.count);
            } finally
            {
                super.close();
            }
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("x", this.x).append("z", this.z).toString();
        }
    }

    protected void write(final int x, final int z, final byte[] data, final int length) throws IOException
    {
        final int location = this.getLocation(x, z);
        int offset = location >> 8;
        final int oldSize = location & 0xFF;
        final int newSize = ((length + 5) / SECTOR_BYTES) + 1;

        // chunk can't be bigger than 1MiB
        if (newSize >= 256)
        {
            throw new RuntimeException("Chunk is bigger than 1MiB! (" + (1024 - (newSize << 2)) + " KiB bigger) region(" + this.x + ", " + this.z + "), local chunk(" + x + ", " + z + "), map chunk(" + ((this.x << 5) + x) + ", " + ((this.z << 5) + z) + ")");
        }

        if ((offset != 0) && (oldSize == newSize)) // match old chunk sectors
        {
            this.write(offset, data, length);
        }
        else
        {
            for (int i = 0; i < oldSize; ++ i) // old sectors are now free
            {
                this.freeSectors.set(offset + i, true);
            }

            int runStart = this.freeSectors.nextSetBit(0);
            int runLength = 0;
            if (runStart != - 1)
            {
                for (int i = runStart; i < this.freeSectors.getLargestIndex(); ++ i)
                {
                    if (runLength != 0)
                    {
                        if (this.freeSectors.get(i))
                        {
                            runLength++;
                        }
                        else
                        {
                            runLength = 0;
                        }
                    }
                    else if (this.freeSectors.get(i))
                    {
                        runStart = i;
                        runLength = 1;
                    }
                    if (runLength >= newSize)
                    {
                        break;
                    }
                }
            }

            if (runLength >= newSize) // we found space to save this chunk
            {
                offset = runStart;
                this.setLocation(x, z, (offset << 8) | newSize);
                for (int i = 0; i < newSize; ++ i) // mark sectors as used
                {
                    this.freeSectors.set(offset + i, false);
                }
                this.write(offset, data, length);
            }
            else // file is too small, we need make it bigger
            {
                this.raf.seek(this.raf.length());
                offset = this.freeSectors.getLargestIndex();
                for (int i = 0; i < newSize; ++ i)
                {
                    this.raf.write(emptySector);
                    this.freeSectors.clear(offset + i);
                }

                this.write(offset, data, length);
                this.setLocation(x, z, (offset << 8) | newSize);
            }
        }
        this.setTimestamp(x, z, (int) (System.currentTimeMillis() / 1000));
        if (this.channel == null)
        {
            this.channel = this.raf.getChannel();
        }
        this.channel.force(true);
    }

    private void write(final int offset, final byte[] data, final int length) throws IOException
    {
        this.raf.seek(offset * SECTOR_BYTES);
        this.raf.writeInt(length + 1);
        this.raf.writeByte(VERSION_DEFLATE);
        this.raf.write(data, 0, length);
    }
}
