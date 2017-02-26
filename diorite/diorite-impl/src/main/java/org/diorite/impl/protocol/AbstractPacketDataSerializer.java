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

package org.diorite.impl.protocol;

import javax.vecmath.Vector3f;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import io.netty.util.ByteProcessor;

public class AbstractPacketDataSerializer extends ByteBuf
{
    protected final ByteBuf byteBuf;

    public AbstractPacketDataSerializer(ByteBuf bytebuf) {this.byteBuf = bytebuf;}

    public void writeByteWord(byte[] abyte)
    {
        this.writeVarInt(abyte.length);
        this.writeBytes(abyte);
    }

    public byte[] readByteWord()
    {
        byte[] abyte = new byte[this.readVarInt()];

        this.readBytes(abyte);
        return abyte;
    }

    private static final ConcurrentHashMap<Class<?>, Object[]> enumValues = new ConcurrentHashMap<>(20);

    @SuppressWarnings("unchecked")
    public <T extends Enum<T>> T readEnum(Class<T> c)
    {
        return (T) enumValues.computeIfAbsent(c, k -> c.getEnumConstants())[this.readVarInt()];
    }

    public void writeEnum(Enum<?> oenum)
    {
        this.writeVarInt(oenum.ordinal());
    }

    public int readVarInt()
    {
        return readVarInt(this.byteBuf);
    }

    @SuppressWarnings("MagicNumber")
    public static int readVarInt(ByteBuf byteBuf)
    {
        int i = 0;
        int j = 0;
        byte b0;
        do
        {
            b0 = byteBuf.readByte();
            i |= (b0 & 0x7F) << (j++ * 7);
            if (j > 5)
            {
                throw new RuntimeException("VarInt too big");
            }
        } while ((b0 & 0x80) == 128);
        return i;
    }

    @SuppressWarnings("MagicNumber")
    public long readVarLong()
    {
        long i = 0L;
        int j = 0;
        byte b0;
        do
        {
            b0 = this.readByte();
            i |= (b0 & 0x7F) << (j++ * 7);
            if (j > 10)
            {
                throw new RuntimeException("VarLong too big");
            }
        } while ((b0 & 0x80) == 128);
        return i;
    }

    public Vector3f readVector3F()
    {
        return new Vector3f(this.readFloat(), this.readFloat(), this.readFloat());
    }

    public void writeVector3F(Vector3f vec)
    {
        this.writeFloat(vec.x);
        this.writeFloat(vec.y);
        this.writeFloat(vec.z);
    }

    public void writeUUID(UUID uuid)
    {
        this.writeLong(uuid.getMostSignificantBits());
        this.writeLong(uuid.getLeastSignificantBits());
    }

    public UUID readUUID()
    {
        return new UUID(this.readLong(), this.readLong());
    }

    public void writeVarInt(int i)
    {
        writeVarInt(this.byteBuf, i);
    }

    @SuppressWarnings("MagicNumber")
    public static void writeVarInt(ByteBuf byteBuf, int i)
    {
        while ((i & 0xFFFFFF80) != 0)
        {
            byteBuf.writeByte((i & 0x7F) | 0x80);
            i >>>= 7;
        }
        byteBuf.writeByte(i);
    }

    @SuppressWarnings("MagicNumber")
    public void writeVarLong(long i)
    {
        while ((i & 0xFFFFFF80) != 0L)
        {
            this.writeByte((int) (i & 0x7F) | 0x80);
            i >>>= 7;
        }
        this.writeByte((int) i);
    }

    public String readText(int maxChars)
    {
        return this.readText(maxChars, maxChars * 4);
    }

    public String readText(int maxChars, int maxBytes)
    {
        int byteSize = this.readVarInt();
        if (byteSize > maxBytes) // max 4 bytes per char
        {
            throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + byteSize + " > " + maxBytes + ")");
        }
        if (byteSize < 0)
        {
            throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
        }
        byte[] bytes = new byte[byteSize];
        this.readBytes(bytes);
        String s = new String(bytes, StandardCharsets.UTF_8);
        if (s.length() > maxChars)
        {
            throw new DecoderException("The received string length is longer than maximum allowed (" + s.length() + " > " + maxChars + ")");
        }
        return s;
    }

    public void writeText(String s)
    {
        byte[] abyte = s.getBytes(StandardCharsets.UTF_8);
        if (abyte.length > Short.MAX_VALUE)
        {
            throw new EncoderException("String too big (was " + s.length() + " bytes encoded, max " + Short.MAX_VALUE + ")");
        }
        this.writeVarInt(abyte.length);
        this.writeBytes(abyte);
    }

    @Override
    public int capacity()
    {
        return this.byteBuf.capacity();
    }

    @Override
    public ByteBuf capacity(int newCapacity)
    {
        return this.byteBuf.capacity(newCapacity);
    }

    @Override
    public int maxCapacity()
    {
        return this.byteBuf.maxCapacity();
    }

    @Override
    public ByteBufAllocator alloc()
    {
        return this.byteBuf.alloc();
    }

    @Override
    @Deprecated
    public ByteOrder order()
    {
        return this.byteBuf.order();
    }

    @Override
    @Deprecated
    public ByteBuf order(ByteOrder endianness)
    {
        return this.byteBuf.order(endianness);
    }

    @Override
    public ByteBuf unwrap()
    {
        return this.byteBuf.unwrap();
    }

    @Override
    public boolean isDirect()
    {
        return this.byteBuf.isDirect();
    }

    @Override
    public boolean isReadOnly()
    {
        return this.byteBuf.isReadOnly();
    }

    @Override
    public ByteBuf asReadOnly()
    {
        return this.byteBuf.asReadOnly();
    }

    @Override
    public int readerIndex()
    {
        return this.byteBuf.readerIndex();
    }

    @Override
    public ByteBuf readerIndex(int readerIndex)
    {
        return this.byteBuf.readerIndex(readerIndex);
    }

    @Override
    public int writerIndex()
    {
        return this.byteBuf.writerIndex();
    }

    @Override
    public ByteBuf writerIndex(int writerIndex)
    {
        return this.byteBuf.writerIndex(writerIndex);
    }

    @Override
    public ByteBuf setIndex(int readerIndex, int writerIndex)
    {
        return this.byteBuf.setIndex(readerIndex, writerIndex);
    }

    @Override
    public int readableBytes()
    {
        return this.byteBuf.readableBytes();
    }

    @Override
    public int writableBytes()
    {
        return this.byteBuf.writableBytes();
    }

    @Override
    public int maxWritableBytes()
    {
        return this.byteBuf.maxWritableBytes();
    }

    @Override
    public boolean isReadable()
    {
        return this.byteBuf.isReadable();
    }

    @Override
    public boolean isReadable(int size)
    {
        return this.byteBuf.isReadable(size);
    }

    @Override
    public boolean isWritable()
    {
        return this.byteBuf.isWritable();
    }

    @Override
    public boolean isWritable(int size)
    {
        return this.byteBuf.isWritable(size);
    }

    @Override
    public ByteBuf clear()
    {
        return this.byteBuf.clear();
    }

    @Override
    public ByteBuf markReaderIndex()
    {
        return this.byteBuf.markReaderIndex();
    }

    @Override
    public ByteBuf resetReaderIndex()
    {
        return this.byteBuf.resetReaderIndex();
    }

    @Override
    public ByteBuf markWriterIndex()
    {
        return this.byteBuf.markWriterIndex();
    }

    @Override
    public ByteBuf resetWriterIndex()
    {
        return this.byteBuf.resetWriterIndex();
    }

    @Override
    public ByteBuf discardReadBytes()
    {
        return this.byteBuf.discardReadBytes();
    }

    @Override
    public ByteBuf discardSomeReadBytes()
    {
        return this.byteBuf.discardSomeReadBytes();
    }

    @Override
    public ByteBuf ensureWritable(int minWritableBytes)
    {
        return this.byteBuf.ensureWritable(minWritableBytes);
    }

    @Override
    public int ensureWritable(int minWritableBytes, boolean force)
    {
        return this.byteBuf.ensureWritable(minWritableBytes, force);
    }

    @Override
    public boolean getBoolean(int index)
    {
        return this.byteBuf.getBoolean(index);
    }

    @Override
    public byte getByte(int index)
    {
        return this.byteBuf.getByte(index);
    }

    @Override
    public short getUnsignedByte(int index)
    {
        return this.byteBuf.getUnsignedByte(index);
    }

    @Override
    public short getShort(int index)
    {
        return this.byteBuf.getShort(index);
    }

    @Override
    public short getShortLE(int index)
    {
        return this.byteBuf.getShortLE(index);
    }

    @Override
    public int getUnsignedShort(int index)
    {
        return this.byteBuf.getUnsignedShort(index);
    }

    @Override
    public int getUnsignedShortLE(int index)
    {
        return this.byteBuf.getUnsignedShortLE(index);
    }

    @Override
    public int getMedium(int index)
    {
        return this.byteBuf.getMedium(index);
    }

    @Override
    public int getMediumLE(int index)
    {
        return this.byteBuf.getMediumLE(index);
    }

    @Override
    public int getUnsignedMedium(int index)
    {
        return this.byteBuf.getUnsignedMedium(index);
    }

    @Override
    public int getUnsignedMediumLE(int index)
    {
        return this.byteBuf.getUnsignedMediumLE(index);
    }

    @Override
    public int getInt(int index)
    {
        return this.byteBuf.getInt(index);
    }

    @Override
    public int getIntLE(int index)
    {
        return this.byteBuf.getIntLE(index);
    }

    @Override
    public long getUnsignedInt(int index)
    {
        return this.byteBuf.getUnsignedInt(index);
    }

    @Override
    public long getUnsignedIntLE(int index)
    {
        return this.byteBuf.getUnsignedIntLE(index);
    }

    @Override
    public long getLong(int index)
    {
        return this.byteBuf.getLong(index);
    }

    @Override
    public long getLongLE(int index)
    {
        return this.byteBuf.getLongLE(index);
    }

    @Override
    public char getChar(int index)
    {
        return this.byteBuf.getChar(index);
    }

    @Override
    public float getFloat(int index)
    {
        return this.byteBuf.getFloat(index);
    }

    @Override
    public double getDouble(int index)
    {
        return this.byteBuf.getDouble(index);
    }

    @Override
    public ByteBuf getBytes(int index, ByteBuf dst)
    {
        return this.byteBuf.getBytes(index, dst);
    }

    @Override
    public ByteBuf getBytes(int index, ByteBuf dst, int length)
    {
        return this.byteBuf.getBytes(index, dst, length);
    }

    @Override
    public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length)
    {
        return this.byteBuf.getBytes(index, dst, dstIndex, length);
    }

    @Override
    public ByteBuf getBytes(int index, byte[] dst)
    {
        return this.byteBuf.getBytes(index, dst);
    }

    @Override
    public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length)
    {
        return this.byteBuf.getBytes(index, dst, dstIndex, length);
    }

    @Override
    public ByteBuf getBytes(int index, ByteBuffer dst)
    {
        return this.byteBuf.getBytes(index, dst);
    }

    @Override
    public ByteBuf getBytes(int index, OutputStream out, int length) throws IOException
    {
        return this.byteBuf.getBytes(index, out, length);
    }

    @Override
    public int getBytes(int index, GatheringByteChannel out, int length) throws IOException
    {
        return this.byteBuf.getBytes(index, out, length);
    }

    @Override
    public int getBytes(int index, FileChannel out, long position, int length) throws IOException
    {
        return this.byteBuf.getBytes(index, out, position, length);
    }

    @Override
    public CharSequence getCharSequence(int index, int length, Charset charset)
    {
        return this.byteBuf.getCharSequence(index, length, charset);
    }

    @Override
    public ByteBuf setBoolean(int index, boolean value)
    {
        return this.byteBuf.setBoolean(index, value);
    }

    @Override
    public ByteBuf setByte(int index, int value)
    {
        return this.byteBuf.setByte(index, value);
    }

    @Override
    public ByteBuf setShort(int index, int value)
    {
        return this.byteBuf.setShort(index, value);
    }

    @Override
    public ByteBuf setShortLE(int index, int value)
    {
        return this.byteBuf.setShortLE(index, value);
    }

    @Override
    public ByteBuf setMedium(int index, int value)
    {
        return this.byteBuf.setMedium(index, value);
    }

    @Override
    public ByteBuf setMediumLE(int index, int value)
    {
        return this.byteBuf.setMediumLE(index, value);
    }

    @Override
    public ByteBuf setInt(int index, int value)
    {
        return this.byteBuf.setInt(index, value);
    }

    @Override
    public ByteBuf setIntLE(int index, int value)
    {
        return this.byteBuf.setIntLE(index, value);
    }

    @Override
    public ByteBuf setLong(int index, long value)
    {
        return this.byteBuf.setLong(index, value);
    }

    @Override
    public ByteBuf setLongLE(int index, long value)
    {
        return this.byteBuf.setLongLE(index, value);
    }

    @Override
    public ByteBuf setChar(int index, int value)
    {
        return this.byteBuf.setChar(index, value);
    }

    @Override
    public ByteBuf setFloat(int index, float value)
    {
        return this.byteBuf.setFloat(index, value);
    }

    @Override
    public ByteBuf setDouble(int index, double value)
    {
        return this.byteBuf.setDouble(index, value);
    }

    @Override
    public ByteBuf setBytes(int index, ByteBuf src)
    {
        return this.byteBuf.setBytes(index, src);
    }

    @Override
    public ByteBuf setBytes(int index, ByteBuf src, int length)
    {
        return this.byteBuf.setBytes(index, src, length);
    }

    @Override
    public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length)
    {
        return this.byteBuf.setBytes(index, src, srcIndex, length);
    }

    @Override
    public ByteBuf setBytes(int index, byte[] src)
    {
        return this.byteBuf.setBytes(index, src);
    }

    @Override
    public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length)
    {
        return this.byteBuf.setBytes(index, src, srcIndex, length);
    }

    @Override
    public ByteBuf setBytes(int index, ByteBuffer src)
    {
        return this.byteBuf.setBytes(index, src);
    }

    @Override
    public int setBytes(int index, InputStream in, int length) throws IOException
    {
        return this.byteBuf.setBytes(index, in, length);
    }

    @Override
    public int setBytes(int index, ScatteringByteChannel in, int length) throws IOException
    {
        return this.byteBuf.setBytes(index, in, length);
    }

    @Override
    public int setBytes(int index, FileChannel in, long position, int length) throws IOException
    {
        return this.byteBuf.setBytes(index, in, position, length);
    }

    @Override
    public ByteBuf setZero(int index, int length)
    {
        return this.byteBuf.setZero(index, length);
    }

    @Override
    public int setCharSequence(int index, CharSequence sequence, Charset charset)
    {
        return this.byteBuf.setCharSequence(index, sequence, charset);
    }

    @Override
    public boolean readBoolean()
    {
        return this.byteBuf.readBoolean();
    }

    @Override
    public byte readByte()
    {
        return this.byteBuf.readByte();
    }

    @Override
    public short readUnsignedByte()
    {
        return this.byteBuf.readUnsignedByte();
    }

    @Override
    public short readShort()
    {
        return this.byteBuf.readShort();
    }

    @Override
    public short readShortLE()
    {
        return this.byteBuf.readShortLE();
    }

    @Override
    public int readUnsignedShort()
    {
        return this.byteBuf.readUnsignedShort();
    }

    @Override
    public int readUnsignedShortLE()
    {
        return this.byteBuf.readUnsignedShortLE();
    }

    @Override
    public int readMedium()
    {
        return this.byteBuf.readMedium();
    }

    @Override
    public int readMediumLE()
    {
        return this.byteBuf.readMediumLE();
    }

    @Override
    public int readUnsignedMedium()
    {
        return this.byteBuf.readUnsignedMedium();
    }

    @Override
    public int readUnsignedMediumLE()
    {
        return this.byteBuf.readUnsignedMediumLE();
    }

    @Override
    public int readInt()
    {
        return this.byteBuf.readInt();
    }

    @Override
    public int readIntLE()
    {
        return this.byteBuf.readIntLE();
    }

    @Override
    public long readUnsignedInt()
    {
        return this.byteBuf.readUnsignedInt();
    }

    @Override
    public long readUnsignedIntLE()
    {
        return this.byteBuf.readUnsignedIntLE();
    }

    @Override
    public long readLong()
    {
        return this.byteBuf.readLong();
    }

    @Override
    public long readLongLE()
    {
        return this.byteBuf.readLongLE();
    }

    @Override
    public char readChar()
    {
        return this.byteBuf.readChar();
    }

    @Override
    public float readFloat()
    {
        return this.byteBuf.readFloat();
    }

    @Override
    public double readDouble()
    {
        return this.byteBuf.readDouble();
    }

    @Override
    public ByteBuf readBytes(int length)
    {
        return this.byteBuf.readBytes(length);
    }

    @Override
    public ByteBuf readSlice(int length)
    {
        return this.byteBuf.readSlice(length);
    }

    @Override
    public ByteBuf readRetainedSlice(int length)
    {
        return this.byteBuf.readRetainedSlice(length);
    }

    @Override
    public ByteBuf readBytes(ByteBuf dst)
    {
        return this.byteBuf.readBytes(dst);
    }

    @Override
    public ByteBuf readBytes(ByteBuf dst, int length)
    {
        return this.byteBuf.readBytes(dst, length);
    }

    @Override
    public ByteBuf readBytes(ByteBuf dst, int dstIndex, int length)
    {
        return this.byteBuf.readBytes(dst, dstIndex, length);
    }

    @Override
    public ByteBuf readBytes(byte[] dst)
    {
        return this.byteBuf.readBytes(dst);
    }

    @Override
    public ByteBuf readBytes(byte[] dst, int dstIndex, int length)
    {
        return this.byteBuf.readBytes(dst, dstIndex, length);
    }

    @Override
    public ByteBuf readBytes(ByteBuffer dst)
    {
        return this.byteBuf.readBytes(dst);
    }

    @Override
    public ByteBuf readBytes(OutputStream out, int length) throws IOException
    {
        return this.byteBuf.readBytes(out, length);
    }

    @Override
    public int readBytes(GatheringByteChannel out, int length) throws IOException
    {
        return this.byteBuf.readBytes(out, length);
    }

    @Override
    public CharSequence readCharSequence(int length, Charset charset)
    {
        return this.byteBuf.readCharSequence(length, charset);
    }

    @Override
    public int readBytes(FileChannel out, long position, int length) throws IOException
    {
        return this.byteBuf.readBytes(out, position, length);
    }

    @Override
    public ByteBuf skipBytes(int length)
    {
        return this.byteBuf.skipBytes(length);
    }

    @Override
    public ByteBuf writeBoolean(boolean value)
    {
        return this.byteBuf.writeBoolean(value);
    }

    @Override
    public ByteBuf writeByte(int value)
    {
        return this.byteBuf.writeByte(value);
    }

    @Override
    public ByteBuf writeShort(int value)
    {
        return this.byteBuf.writeShort(value);
    }

    @Override
    public ByteBuf writeShortLE(int value)
    {
        return this.byteBuf.writeShortLE(value);
    }

    @Override
    public ByteBuf writeMedium(int value)
    {
        return this.byteBuf.writeMedium(value);
    }

    @Override
    public ByteBuf writeMediumLE(int value)
    {
        return this.byteBuf.writeMediumLE(value);
    }

    @Override
    public ByteBuf writeInt(int value)
    {
        return this.byteBuf.writeInt(value);
    }

    @Override
    public ByteBuf writeIntLE(int value)
    {
        return this.byteBuf.writeIntLE(value);
    }

    @Override
    public ByteBuf writeLong(long value)
    {
        return this.byteBuf.writeLong(value);
    }

    @Override
    public ByteBuf writeLongLE(long value)
    {
        return this.byteBuf.writeLongLE(value);
    }

    @Override
    public ByteBuf writeChar(int value)
    {
        return this.byteBuf.writeChar(value);
    }

    @Override
    public ByteBuf writeFloat(float value)
    {
        return this.byteBuf.writeFloat(value);
    }

    @Override
    public ByteBuf writeDouble(double value)
    {
        return this.byteBuf.writeDouble(value);
    }

    @Override
    public ByteBuf writeBytes(ByteBuf src)
    {
        return this.byteBuf.writeBytes(src);
    }

    @Override
    public ByteBuf writeBytes(ByteBuf src, int length)
    {
        return this.byteBuf.writeBytes(src, length);
    }

    @Override
    public ByteBuf writeBytes(ByteBuf src, int srcIndex, int length)
    {
        return this.byteBuf.writeBytes(src, srcIndex, length);
    }

    @Override
    public ByteBuf writeBytes(byte[] src)
    {
        return this.byteBuf.writeBytes(src);
    }

    @Override
    public ByteBuf writeBytes(byte[] src, int srcIndex, int length)
    {
        return this.byteBuf.writeBytes(src, srcIndex, length);
    }

    @Override
    public ByteBuf writeBytes(ByteBuffer src)
    {
        return this.byteBuf.writeBytes(src);
    }

    @Override
    public int writeBytes(InputStream in, int length) throws IOException
    {
        return this.byteBuf.writeBytes(in, length);
    }

    @Override
    public int writeBytes(ScatteringByteChannel in, int length) throws IOException
    {
        return this.byteBuf.writeBytes(in, length);
    }

    @Override
    public int writeBytes(FileChannel in, long position, int length) throws IOException
    {
        return this.byteBuf.writeBytes(in, position, length);
    }

    @Override
    public ByteBuf writeZero(int length)
    {
        return this.byteBuf.writeZero(length);
    }

    @Override
    public int writeCharSequence(CharSequence sequence, Charset charset)
    {
        return this.byteBuf.writeCharSequence(sequence, charset);
    }

    @Override
    public int indexOf(int fromIndex, int toIndex, byte value)
    {
        return this.byteBuf.indexOf(fromIndex, toIndex, value);
    }

    @Override
    public int bytesBefore(byte value)
    {
        return this.byteBuf.bytesBefore(value);
    }

    @Override
    public int bytesBefore(int length, byte value)
    {
        return this.byteBuf.bytesBefore(length, value);
    }

    @Override
    public int bytesBefore(int index, int length, byte value)
    {
        return this.byteBuf.bytesBefore(index, length, value);
    }

    @Override
    public int forEachByte(ByteProcessor processor)
    {
        return this.byteBuf.forEachByte(processor);
    }

    @Override
    public int forEachByte(int index, int length, ByteProcessor processor)
    {
        return this.byteBuf.forEachByte(index, length, processor);
    }

    @Override
    public int forEachByteDesc(ByteProcessor processor)
    {
        return this.byteBuf.forEachByteDesc(processor);
    }

    @Override
    public int forEachByteDesc(int index, int length, ByteProcessor processor)
    {
        return this.byteBuf.forEachByteDesc(index, length, processor);
    }

    @Override
    public ByteBuf copy()
    {
        return this.byteBuf.copy();
    }

    @Override
    public ByteBuf copy(int index, int length)
    {
        return this.byteBuf.copy(index, length);
    }

    @Override
    public ByteBuf slice()
    {
        return this.byteBuf.slice();
    }

    @Override
    public ByteBuf retainedSlice()
    {
        return this.byteBuf.retainedSlice();
    }

    @Override
    public ByteBuf slice(int index, int length)
    {
        return this.byteBuf.slice(index, length);
    }

    @Override
    public ByteBuf retainedSlice(int index, int length)
    {
        return this.byteBuf.retainedSlice(index, length);
    }

    @Override
    public ByteBuf duplicate()
    {
        return this.byteBuf.duplicate();
    }

    @Override
    public ByteBuf retainedDuplicate()
    {
        return this.byteBuf.retainedDuplicate();
    }

    @Override
    public int nioBufferCount()
    {
        return this.byteBuf.nioBufferCount();
    }

    @Override
    public ByteBuffer nioBuffer()
    {
        return this.byteBuf.nioBuffer();
    }

    @Override
    public ByteBuffer nioBuffer(int index, int length)
    {
        return this.byteBuf.nioBuffer(index, length);
    }

    @Override
    public ByteBuffer internalNioBuffer(int index, int length)
    {
        return this.byteBuf.internalNioBuffer(index, length);
    }

    @Override
    public ByteBuffer[] nioBuffers()
    {
        return this.byteBuf.nioBuffers();
    }

    @Override
    public ByteBuffer[] nioBuffers(int index, int length)
    {
        return this.byteBuf.nioBuffers(index, length);
    }

    @Override
    public boolean hasArray()
    {
        return this.byteBuf.hasArray();
    }

    @Override
    public byte[] array()
    {
        return this.byteBuf.array();
    }

    @Override
    public int arrayOffset()
    {
        return this.byteBuf.arrayOffset();
    }

    @Override
    public boolean hasMemoryAddress()
    {
        return this.byteBuf.hasMemoryAddress();
    }

    @Override
    public long memoryAddress()
    {
        return this.byteBuf.memoryAddress();
    }

    @Override
    public String toString(Charset charset)
    {
        return this.byteBuf.toString(charset);
    }

    @Override
    public String toString(int index, int length, Charset charset)
    {
        return this.byteBuf.toString(index, length, charset);
    }

    @Override
    public int hashCode()
    {
        return this.byteBuf.hashCode();
    }

    @Override
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (! (object instanceof AbstractPacketDataSerializer))
        {
            return false;
        }
        AbstractPacketDataSerializer that = (AbstractPacketDataSerializer) object;
        return Objects.equals(this.byteBuf, that.byteBuf);
    }

    @Override
    public int compareTo(ByteBuf bytebuf)
    {
        return this.byteBuf.compareTo(bytebuf);
    }

    public String toString()
    {
        return this.byteBuf.toString();
    }

    @Override
    public ByteBuf retain(int i)
    {
        return this.byteBuf.retain(i);
    }

    @Override
    public ByteBuf retain()
    {
        return this.byteBuf.retain();
    }

    @Override
    public ByteBuf touch()
    {
        return this.byteBuf.touch();
    }

    @Override
    public ByteBuf touch(Object o)
    {
        return this.byteBuf.touch(o);
    }

    @Override
    public int refCnt()
    {
        return this.byteBuf.refCnt();
    }

    @Override
    public boolean release()
    {
        return this.byteBuf.release();
    }

    @Override
    public boolean release(int i)
    {
        return this.byteBuf.release(i);
    }

    public static int neededBytes(int i)
    {
        for (int j = 1; j < 5; j++)
        {
            if ((i & (- 1 << (j * 7))) == 0)
            {
                return j;
            }
        }
        return 5;
    }
}
