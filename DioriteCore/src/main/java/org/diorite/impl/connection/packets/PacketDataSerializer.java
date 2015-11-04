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

package org.diorite.impl.connection.packets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.diorite.impl.auth.GameProfileImpl;
import org.diorite.impl.entity.attrib.AttributePropertyImpl;
import org.diorite.impl.entity.attrib.SimpleAttributeModifier;
import org.diorite.impl.entity.meta.entry.EntityMetadataEntry;
import org.diorite.impl.inventory.item.meta.ItemMetaImpl;
import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.impl.world.chunk.ChunkPartImpl;
import org.diorite.BlockFace;
import org.diorite.BlockLocation;
import org.diorite.Core;
import org.diorite.chat.component.BaseComponent;
import org.diorite.chat.component.serialize.ComponentSerializer;
import org.diorite.entity.attrib.AttributeModifier;
import org.diorite.entity.attrib.AttributeProperty;
import org.diorite.entity.attrib.AttributeType;
import org.diorite.entity.attrib.ModifierOperation;
import org.diorite.inventory.item.BaseItemStack;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.item.meta.ItemMeta;
import org.diorite.material.Material;
import org.diorite.nbt.NbtInputStream;
import org.diorite.nbt.NbtLimiter;
import org.diorite.nbt.NbtOutputStream;
import org.diorite.nbt.NbtTag;
import org.diorite.nbt.NbtTagCompound;
import org.diorite.nbt.NbtTagType;
import org.diorite.utils.math.DioriteMathUtils;
import org.diorite.utils.math.geometry.Vector3F;
import org.diorite.world.chunk.Chunk;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import io.netty.util.ByteProcessor;

public class PacketDataSerializer extends ByteBuf
{
    private final ByteBuf byteBuf;

    public PacketDataSerializer(final ByteBuf bytebuf)
    {
        this.byteBuf = bytebuf;
    }

    public BlockFace readBlockFace()
    {
        switch (this.readUnsignedByte())
        {
            case 0:
                return BlockFace.DOWN;
            case 1:
                return BlockFace.UP;
            case 2:
                return BlockFace.NORTH;
            case 3:
                return BlockFace.SOUTH;
            case 4:
                return BlockFace.WEST;
            case 5:
                return BlockFace.EAST;
            default:
                return null;
        }
    }

    public void writeBlockFace(final BlockFace face)
    {
        if (face == null)
        {
            this.writeByte(- 1);
            return;
        }
        switch (face)
        {
            case NORTH:
                this.writeByte(2);
                break;
            case EAST:
                this.writeByte(5);
                break;
            case SOUTH:
                this.writeByte(3);
                break;
            case WEST:
                this.writeByte(4);
                break;
            case UP:
                this.writeByte(1);
                break;
            case DOWN:
                this.writeByte(0);
                break;
            default:
                this.writeByte(- 1);
                break;
        }
    }

    public void writeEntityMetadata(final EntityMetadataEntry<?> data)
    {
        EntityMetadataCodec.encode(this, data);
    }

    public void writeEntityMetadata(final Iterable<EntityMetadataEntry<?>> data)
    {
        data.forEach(this::writeEntityMetadata);
        this.writeByte(Byte.MAX_VALUE); // mark end of metadata
    }

    public List<EntityMetadataEntry<?>> readEntityMetadata()
    {
        return EntityMetadataCodec.decode(this);
    }

    public ItemStack readItemStack()
    {
        ItemStack itemstack = null;
        final short id = this.readShort();
        if (id >= 0)
        {
            final byte amount = this.readByte();
            final short damage = this.readShort();
            final Material mat = Material.getByID(id, damage);
            itemstack = new BaseItemStack((mat == null) ? Material.AIR : mat, amount);
            itemstack.getItemMeta().setNbtData(this.readNbtTagCompound());
        }
        return itemstack;
    }

    public NbtTagCompound readNbtTagCompound()
    {
        final int currIndex = this.readerIndex();
        final byte firstTag = this.readByte();
        if (firstTag == NbtTagType.END.getTypeID())
        {
            return null;
        }
        this.readerIndex(currIndex);
        try
        {
            return (NbtTagCompound) NbtInputStream.readTag(new ByteBufInputStream(this), NbtLimiter.getDefault());
        } catch (final IOException e)
        {
            throw new RuntimeException("Can't decode nbt.", e);
        }
    }

    public void writeItemStack(final ItemStack itemStack)
    {
        if (itemStack == null)
        {
            this.writeShort(- 1);
            return;
        }
        final Material mat = itemStack.getMaterial();
        this.writeShort(mat.ordinal());
        this.writeByte(itemStack.getAmount());
        this.writeShort(mat.getType());
        final ItemMeta meta = itemStack.getItemMeta();
        this.writeNbtTagCompound((meta == null) ? null : ((meta instanceof ItemMetaImpl) ? ((ItemMetaImpl) meta).getRawData() : meta.getNbtData()));
    }

    public void writeNbtTagCompound(final NbtTag nbt)
    {
        if (nbt == null)
        {
            this.writeByte(NbtTagType.END.getTypeID());
            return;
        }
        try (NbtOutputStream os = new NbtOutputStream(new ByteBufOutputStream(this)))
        {
            os.write(nbt);
        } catch (final IOException e)
        {
            e.printStackTrace();
        }
    }

    public AttributeModifier readAttributeModifer()
    {
        final UUID uuid = this.readUUID();
        final double value = this.readDouble();
        final byte operation = this.readByte();
        return new SimpleAttributeModifier(uuid, null, value, ModifierOperation.getByEnumOrdinal(operation), null, null);
    }

    public void writeAttributeModifer(final AttributeModifier attribute)
    {
        this.writeUUID(attribute.getUuid());
        this.writeDouble(attribute.getValue());
        this.writeByte(attribute.getOperation().ordinal());
    }

    public AttributePropertyImpl readAttributeProperty()
    {
        final AttributeType type = AttributeType.getByKey(this.readText(Short.MAX_VALUE));
        final double value = this.readDouble();
        final int size = this.readVarInt();
        final Collection<AttributeModifier> mods = new HashSet<>(size);
        for (int i = 0; i < size; i++)
        {
            mods.add(this.readAttributeModifer());
        }
        return new AttributePropertyImpl(type, mods, value);
    }

    public void writeAttributeProperty(final AttributeProperty attribute)
    {
        this.writeText(attribute.getType().getKey());
        this.writeDouble(attribute.getBaseValue());
        this.writeVarInt(attribute.getModifiersCollection().size());
        attribute.getModifiersCollection().forEach(this::writeAttributeModifer);
    }

    public BaseComponent readBaseComponent()
    {
        return ComponentSerializer.parseOne(this.readText(Short.MAX_VALUE));
    }

    public void writeBaseComponent(final BaseComponent baseComponent)
    {
        this.writeText(ComponentSerializer.toString(baseComponent));
    }

    public void writeChunkSimple(final ChunkImpl chunk, final int mask, final boolean skyLight, final boolean groundUpContinuous, final boolean writeSize) // groundUpContinuous, with biomes
    {
        final ChunkPartImpl[] chunkParts = chunk.getChunkParts(); // get all chunk parts

        final byte chunkPartsCount = DioriteMathUtils.countBits(chunk.getMask()); // number of chunks to sent
        final ChunkPartImpl[] chunkPartsToSent = new ChunkPartImpl[chunkPartsCount];

        for (int i = 0, j = 0, localMask = 1; i < chunkParts.length; ++ i, localMask <<= 1)
        {
            if ((mask & localMask) != 0)
            {
                chunkPartsToSent[j++] = chunkParts[i];
            }
        }

        // skyLight ? 2 bytes per block, one byte per light : 2 bytes per block, half per blockLight
        final int sectionSize = skyLight ? (ChunkPartImpl.CHUNK_DATA_SIZE * 3) : ((ChunkPartImpl.CHUNK_DATA_SIZE * 5) / 2);

        final byte[] data = new byte[(chunkPartsCount * sectionSize) + (groundUpContinuous ? Chunk.CHUNK_BIOMES_SIZE : 0)]; // size of all chunk parts and biomes if enabled
        int index = 0;


        // write all blocks
        for (final ChunkPartImpl chunkPart : chunkPartsToSent)
        {
            for (final short blockData : chunkPart.getBlocks().getArray())
            {
                //noinspection MagicNumber
                data[index++] = (byte) (blockData & 255);
                //noinspection MagicNumber
                data[index++] = (byte) ((blockData >> 8) & 255);
            }
        }

        // add all block light
        for (final ChunkPartImpl chunkPart : chunkPartsToSent)
        {
            final byte[] blockLightData = chunkPart.getBlockLight().getRawData();
            System.arraycopy(blockLightData, 0, data, index, blockLightData.length);
            index += blockLightData.length;
        }

        // add skyLight if needed
        if (skyLight)
        {
            for (final ChunkPartImpl chunkPart : chunkPartsToSent)
            {
                final byte[] skyLightData = chunkPart.getSkyLight().getRawData();
                System.arraycopy(skyLightData, 0, data, index, skyLightData.length);
                index += skyLightData.length;
            }
        }

        // and biomes if needed
        if (groundUpContinuous)
        {
            for (int i = 0; i < chunk.getBiomes().length; ++ i)
            {
                data[index++] = chunk.getBiomes()[i];
            }
        }

        // write all data with size of it
        if (writeSize)
        {
            this.writeByteWord(data);
        }
        else
        {
            this.writeBytes(data);
        }
    }

    public void writeChunk(final ChunkImpl chunk, int mask, final boolean skyLight, final boolean groundUpContinuous) // groundUpContinuous, with biomes
    {
        final ChunkPartImpl[] chunkParts = chunk.getChunkParts(); // get all chunk parts

        // remove empty chunks from mask
        for (int i = 0, chunkPartsLength = chunkParts.length; i < chunkPartsLength; i++)
        {
            final ChunkPartImpl part = chunkParts[i];
            if ((part == null) || part.isEmpty())
            {
                mask &= ~ (1 << i);
            }
        }

        // mask
        this.writeShort(mask);
        // chunk data
        this.writeChunkSimple(chunk, mask, skyLight, groundUpContinuous, true);
    }

    public void writeBlockLocation(final BlockLocation loc)
    {
        this.writeLong(loc.asLong());
    }

    public BlockLocation readBlockLocation()
    {
        return BlockLocation.fromLong(this.readLong());
    }

    public void writeByteWord(final byte[] abyte)
    {
        this.writeVarInt(abyte.length);
        this.writeBytes(abyte);
    }

    public byte[] readByteWord()
    {
        final byte[] abyte = new byte[this.readVarInt()];

        this.readBytes(abyte);
        return abyte;
    }

    public <T extends Enum<T>> Enum<T> readEnum(final Class<T> oclass)
    {
        return oclass.getEnumConstants()[this.readVarInt()];
    }

    public void writeEnum(final Enum<?> oenum)
    {
        this.writeVarInt(oenum.ordinal());
        this.readInt();
    }

    public void writeGameProfile(final GameProfileImpl gameProfile)
    {
        this.writeText((gameProfile.getId() == null) ? "" : gameProfile.getId().toString());
        this.writeText(gameProfile.getName());
    }

    public GameProfileImpl readGameProfile()
    {
        //noinspection MagicNumber
        final String uuidStr = this.readText(36);
        final String name = this.readText(Core.MAX_NICKNAME_SIZE);
        final UUID uuid = uuidStr.isEmpty() ? null : UUID.fromString(uuidStr);
        return new GameProfileImpl(uuid, name);
    }

    @SuppressWarnings("MagicNumber")
    public int readVarInt()
    {
        int i = 0;
        int j = 0;
        byte b0;
        do
        {
            b0 = this.readByte();
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

    public Vector3F readVector3F()
    {
        return new Vector3F(this.readFloat(), this.readFloat(), this.readFloat());
    }

    public void writeVector3F(final Vector3F vec)
    {
        this.writeFloat(vec.getX());
        this.writeFloat(vec.getY());
        this.writeFloat(vec.getZ());
    }

    public void writeUUID(final UUID uuid)
    {
        this.writeLong(uuid.getMostSignificantBits());
        this.writeLong(uuid.getLeastSignificantBits());
    }

    public UUID readUUID()
    {
        return new UUID(this.readLong(), this.readLong());
    }

    @SuppressWarnings("MagicNumber")
    public void writeVarInt(int i)
    {
        while ((i & 0xFFFFFF80) != 0)
        {
            this.writeByte((i & 0x7F) | 0x80);
            i >>>= 7;
        }
        this.writeByte(i);
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

    public String readText(final int i)
    {
        final int j = this.readVarInt();
        if (j > (i << 2))
        {
            throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + j + " > " + (i << 2) + ")");
        }
        if (j < 0)
        {
            throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
        }
        final String s = new String(this.readBytes(j).array(), StandardCharsets.UTF_8);
        if (s.length() > i)
        {
            throw new DecoderException("The received string length is longer than maximum allowed (" + j + " > " + i + ")");
        }
        return s;
    }

    public PacketDataSerializer writeText(final String s)
    {
        final byte[] abyte = s.getBytes(StandardCharsets.UTF_8);
        if (abyte.length > Short.MAX_VALUE)
        {
            throw new EncoderException("String too big (was " + s.length() + " bytes encoded, max " + Short.MAX_VALUE + ")");
        }
        this.writeVarInt(abyte.length);
        this.writeBytes(abyte);
        return this;
    }

    @Override
    public int capacity()
    {
        return this.byteBuf.capacity();
    }

    @Override
    public ByteBuf capacity(final int i)
    {
        return this.byteBuf.capacity(i);
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
    public ByteOrder order()
    {
        return this.byteBuf.order();
    }

    @Override
    public ByteBuf order(final ByteOrder byteorder)
    {
        return this.byteBuf.order(byteorder);
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
    public int readerIndex()
    {
        return this.byteBuf.readerIndex();
    }

    @Override
    public ByteBuf readerIndex(final int i)
    {
        return this.byteBuf.readerIndex(i);
    }

    @Override
    public int writerIndex()
    {
        return this.byteBuf.writerIndex();
    }

    @Override
    public ByteBuf writerIndex(final int i)
    {
        return this.byteBuf.writerIndex(i);
    }

    @Override
    public ByteBuf setIndex(final int i, final int j)
    {
        return this.byteBuf.setIndex(i, j);
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
    public boolean isReadable(final int i)
    {
        return this.byteBuf.isReadable(i);
    }

    @Override
    public boolean isWritable()
    {
        return this.byteBuf.isWritable();
    }

    @Override
    public boolean isWritable(final int i)
    {
        return this.byteBuf.isWritable(i);
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
    public ByteBuf ensureWritable(final int i)
    {
        return this.byteBuf.ensureWritable(i);
    }

    @Override
    public int ensureWritable(final int i, final boolean flag)
    {
        return this.byteBuf.ensureWritable(i, flag);
    }

    @Override
    public boolean getBoolean(final int i)
    {
        return this.byteBuf.getBoolean(i);
    }

    @Override
    public byte getByte(final int i)
    {
        return this.byteBuf.getByte(i);
    }

    @Override
    public short getUnsignedByte(final int i)
    {
        return this.byteBuf.getUnsignedByte(i);
    }

    @Override
    public short getShort(final int i)
    {
        return this.byteBuf.getShort(i);
    }

    @Override
    public int getUnsignedShort(final int i)
    {
        return this.byteBuf.getUnsignedShort(i);
    }

    @Override
    public int getMedium(final int i)
    {
        return this.byteBuf.getMedium(i);
    }

    @Override
    public int getUnsignedMedium(final int i)
    {
        return this.byteBuf.getUnsignedMedium(i);
    }

    @Override
    public int getInt(final int i)
    {
        return this.byteBuf.getInt(i);
    }

    @Override
    public long getUnsignedInt(final int i)
    {
        return this.byteBuf.getUnsignedInt(i);
    }

    @Override
    public long getLong(final int i)
    {
        return this.byteBuf.getLong(i);
    }

    @Override
    public char getChar(final int i)
    {
        return this.byteBuf.getChar(i);
    }

    @Override
    public float getFloat(final int i)
    {
        return this.byteBuf.getFloat(i);
    }

    @Override
    public double getDouble(final int i)
    {
        return this.byteBuf.getDouble(i);
    }

    @Override
    public ByteBuf getBytes(final int i, final ByteBuf bytebuf)
    {
        return this.byteBuf.getBytes(i, bytebuf);
    }

    @Override
    public ByteBuf getBytes(final int i, final ByteBuf bytebuf, final int j)
    {
        return this.byteBuf.getBytes(i, bytebuf, j);
    }

    @Override
    public ByteBuf getBytes(final int i, final ByteBuf bytebuf, final int j, final int k)
    {
        return this.byteBuf.getBytes(i, bytebuf, j, k);
    }

    @Override
    public ByteBuf getBytes(final int i, final byte[] abyte)
    {
        return this.byteBuf.getBytes(i, abyte);
    }

    @Override
    public ByteBuf getBytes(final int i, final byte[] abyte, final int j, final int k)
    {
        return this.byteBuf.getBytes(i, abyte, j, k);
    }

    @Override
    public ByteBuf getBytes(final int i, final ByteBuffer bytebuffer)
    {
        return this.byteBuf.getBytes(i, bytebuffer);
    }

    @Override
    public ByteBuf getBytes(final int i, final OutputStream outputstream, final int j) throws IOException
    {
        return this.byteBuf.getBytes(i, outputstream, j);
    }

    @Override
    public int getBytes(final int i, final GatheringByteChannel gatheringbytechannel, final int j) throws IOException
    {
        return this.byteBuf.getBytes(i, gatheringbytechannel, j);
    }

    @Override
    public ByteBuf setBoolean(final int i, final boolean flag)
    {
        return this.byteBuf.setBoolean(i, flag);
    }

    @Override
    public ByteBuf setByte(final int i, final int j)
    {
        return this.byteBuf.setByte(i, j);
    }

    @Override
    public ByteBuf setShort(final int i, final int j)
    {
        return this.byteBuf.setShort(i, j);
    }

    @Override
    public ByteBuf setMedium(final int i, final int j)
    {
        return this.byteBuf.setMedium(i, j);
    }

    @Override
    public ByteBuf setInt(final int i, final int j)
    {
        return this.byteBuf.setInt(i, j);
    }

    @Override
    public ByteBuf setLong(final int i, final long j)
    {
        return this.byteBuf.setLong(i, j);
    }

    @Override
    public ByteBuf setChar(final int i, final int j)
    {
        return this.byteBuf.setChar(i, j);
    }

    @Override
    public ByteBuf setFloat(final int i, final float f)
    {
        return this.byteBuf.setFloat(i, f);
    }

    @Override
    public ByteBuf setDouble(final int i, final double d0)
    {
        return this.byteBuf.setDouble(i, d0);
    }

    @Override
    public ByteBuf setBytes(final int i, final ByteBuf bytebuf)
    {
        return this.byteBuf.setBytes(i, bytebuf);
    }

    @Override
    public ByteBuf setBytes(final int i, final ByteBuf bytebuf, final int j)
    {
        return this.byteBuf.setBytes(i, bytebuf, j);
    }

    @Override
    public ByteBuf setBytes(final int i, final ByteBuf bytebuf, final int j, final int k)
    {
        return this.byteBuf.setBytes(i, bytebuf, j, k);
    }

    @Override
    public ByteBuf setBytes(final int i, final byte[] abyte)
    {
        return this.byteBuf.setBytes(i, abyte);
    }

    @Override
    public ByteBuf setBytes(final int i, final byte[] abyte, final int j, final int k)
    {
        return this.byteBuf.setBytes(i, abyte, j, k);
    }

    @Override
    public ByteBuf setBytes(final int i, final ByteBuffer bytebuffer)
    {
        return this.byteBuf.setBytes(i, bytebuffer);
    }

    @Override
    public int setBytes(final int i, final InputStream inputstream, final int j) throws IOException
    {
        return this.byteBuf.setBytes(i, inputstream, j);
    }

    @Override
    public int setBytes(final int i, final ScatteringByteChannel scatteringbytechannel, final int j) throws IOException
    {
        return this.byteBuf.setBytes(i, scatteringbytechannel, j);
    }

    @Override
    public ByteBuf setZero(final int i, final int j)
    {
        return this.byteBuf.setZero(i, j);
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
    public int readUnsignedShort()
    {
        return this.byteBuf.readUnsignedShort();
    }

    @Override
    public int readMedium()
    {
        return this.byteBuf.readMedium();
    }

    @Override
    public int readUnsignedMedium()
    {
        return this.byteBuf.readUnsignedMedium();
    }

    @Override
    public int readInt()
    {
        return this.byteBuf.readInt();
    }

    @Override
    public long readUnsignedInt()
    {
        return this.byteBuf.readUnsignedInt();
    }

    @Override
    public long readLong()
    {
        return this.byteBuf.readLong();
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
    public ByteBuf readBytes(final int i)
    {
        return this.byteBuf.readBytes(i);
    }

    @Override
    public ByteBuf readSlice(final int i)
    {
        return this.byteBuf.readSlice(i);
    }

    @Override
    public ByteBuf readBytes(final ByteBuf bytebuf)
    {
        return this.byteBuf.readBytes(bytebuf);
    }

    @Override
    public ByteBuf readBytes(final ByteBuf bytebuf, final int i)
    {
        return this.byteBuf.readBytes(bytebuf, i);
    }

    @Override
    public ByteBuf readBytes(final ByteBuf bytebuf, final int i, final int j)
    {
        return this.byteBuf.readBytes(bytebuf, i, j);
    }

    @Override
    public ByteBuf readBytes(final byte[] abyte)
    {
        return this.byteBuf.readBytes(abyte);
    }

    @Override
    public ByteBuf readBytes(final byte[] abyte, final int i, final int j)
    {
        return this.byteBuf.readBytes(abyte, i, j);
    }

    @Override
    public ByteBuf readBytes(final ByteBuffer bytebuffer)
    {
        return this.byteBuf.readBytes(bytebuffer);
    }

    @Override
    public ByteBuf readBytes(final OutputStream outputstream, final int i) throws IOException
    {
        return this.byteBuf.readBytes(outputstream, i);
    }

    @Override
    public int readBytes(final GatheringByteChannel gatheringbytechannel, final int i) throws IOException
    {
        return this.byteBuf.readBytes(gatheringbytechannel, i);
    }

    @Override
    public ByteBuf skipBytes(final int i)
    {
        return this.byteBuf.skipBytes(i);
    }

    @Override
    public ByteBuf writeBoolean(final boolean flag)
    {
        return this.byteBuf.writeBoolean(flag);
    }

    @Override
    public ByteBuf writeByte(final int i)
    {
        return this.byteBuf.writeByte(i);
    }

    @Override
    public ByteBuf writeShort(final int i)
    {
        return this.byteBuf.writeShort(i);
    }

    @Override
    public ByteBuf writeMedium(final int i)
    {
        return this.byteBuf.writeMedium(i);
    }

    @Override
    public ByteBuf writeInt(final int i)
    {
        return this.byteBuf.writeInt(i);
    }

    @Override
    public ByteBuf writeLong(final long i)
    {
        return this.byteBuf.writeLong(i);
    }

    @Override
    public ByteBuf writeChar(final int i)
    {
        return this.byteBuf.writeChar(i);
    }

    @Override
    public ByteBuf writeFloat(final float f)
    {
        return this.byteBuf.writeFloat(f);
    }

    @Override
    public ByteBuf writeDouble(final double d0)
    {
        return this.byteBuf.writeDouble(d0);
    }

    @Override
    public ByteBuf writeBytes(final ByteBuf bytebuf)
    {
        return this.byteBuf.writeBytes(bytebuf);
    }

    @Override
    public ByteBuf writeBytes(final ByteBuf bytebuf, final int i)
    {
        return this.byteBuf.writeBytes(bytebuf, i);
    }

    @Override
    public ByteBuf writeBytes(final ByteBuf bytebuf, final int i, final int j)
    {
        return this.byteBuf.writeBytes(bytebuf, i, j);
    }

    @Override
    public ByteBuf writeBytes(final byte[] abyte)
    {
        return this.byteBuf.writeBytes(abyte);
    }

    @Override
    public ByteBuf writeBytes(final byte[] abyte, final int i, final int j)
    {
        return this.byteBuf.writeBytes(abyte, i, j);
    }

    @Override
    public ByteBuf writeBytes(final ByteBuffer bytebuffer)
    {
        return this.byteBuf.writeBytes(bytebuffer);
    }

    @Override
    public int writeBytes(final InputStream inputstream, final int i) throws IOException
    {
        return this.byteBuf.writeBytes(inputstream, i);
    }

    @Override
    public int writeBytes(final ScatteringByteChannel scatteringbytechannel, final int i) throws IOException
    {
        return this.byteBuf.writeBytes(scatteringbytechannel, i);
    }

    @Override
    public ByteBuf writeZero(final int i)
    {
        return this.byteBuf.writeZero(i);
    }

    @Override
    public int indexOf(final int i, final int j, final byte b0)
    {
        return this.byteBuf.indexOf(i, j, b0);
    }

    @Override
    public int bytesBefore(final byte b0)
    {
        return this.byteBuf.bytesBefore(b0);
    }

    @Override
    public int bytesBefore(final int i, final byte b0)
    {
        return this.byteBuf.bytesBefore(i, b0);
    }

    @Override
    public int bytesBefore(final int i, final int j, final byte b0)
    {
        return this.byteBuf.bytesBefore(i, j, b0);
    }

    @Override
    public int forEachByte(final ByteProcessor bytebufprocessor)
    {
        return this.byteBuf.forEachByte(bytebufprocessor);
    }

    @Override
    public int forEachByte(final int i, final int j, final ByteProcessor bytebufprocessor)
    {
        return this.byteBuf.forEachByte(i, j, bytebufprocessor);
    }

    @Override
    public int forEachByteDesc(final ByteProcessor bytebufprocessor)
    {
        return this.byteBuf.forEachByteDesc(bytebufprocessor);
    }

    @Override
    public int forEachByteDesc(final int i, final int j, final ByteProcessor bytebufprocessor)
    {
        return this.byteBuf.forEachByteDesc(i, j, bytebufprocessor);
    }

    @Override
    public ByteBuf copy()
    {
        return this.byteBuf.copy();
    }

    @Override
    public ByteBuf copy(final int i, final int j)
    {
        return this.byteBuf.copy(i, j);
    }

    @Override
    public ByteBuf slice()
    {
        return this.byteBuf.slice();
    }

    @Override
    public ByteBuf slice(final int i, final int j)
    {
        return this.byteBuf.slice(i, j);
    }

    @Override
    public ByteBuf duplicate()
    {
        return this.byteBuf.duplicate();
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
    public ByteBuffer nioBuffer(final int i, final int j)
    {
        return this.byteBuf.nioBuffer(i, j);
    }

    @Override
    public ByteBuffer internalNioBuffer(final int i, final int j)
    {
        return this.byteBuf.internalNioBuffer(i, j);
    }

    @Override
    public ByteBuffer[] nioBuffers()
    {
        return this.byteBuf.nioBuffers();
    }

    @Override
    public ByteBuffer[] nioBuffers(final int i, final int j)
    {
        return this.byteBuf.nioBuffers(i, j);
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
    public String toString(final Charset charset)
    {
        return this.byteBuf.toString(charset);
    }

    @Override
    public String toString(final int i, final int j, final Charset charset)
    {
        return this.byteBuf.toString(i, j, charset);
    }

    @Override
    public int hashCode()
    {
        return this.byteBuf.hashCode();
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof PacketDataSerializer))
        {
            return false;
        }

        final PacketDataSerializer that = (PacketDataSerializer) o;

        return this.byteBuf.equals(that.byteBuf);

    }

    @Override
    public int compareTo(final ByteBuf bytebuf)
    {
        return this.byteBuf.compareTo(bytebuf);
    }

    public String toString()
    {
        return this.byteBuf.toString();
    }

    @Override
    public ByteBuf retain(final int i)
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
    public ByteBuf touch(final Object o)
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
    public boolean release(final int i)
    {
        return this.byteBuf.release(i);
    }

    public static int neededBytes(final int i)
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
