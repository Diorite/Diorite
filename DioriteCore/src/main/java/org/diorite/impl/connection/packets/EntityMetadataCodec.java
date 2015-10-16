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

import java.util.ArrayList;
import java.util.List;

import org.diorite.impl.entity.meta.entry.EntityMetadataBlockLocationEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataByteEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataFloatEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataIntEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataItemStackEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataObjectEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataShortEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataStringEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataVector3FEntry;
import org.diorite.BlockLocation;
import org.diorite.inventory.item.ItemStack;
import org.diorite.utils.math.geometry.Vector3F;

@SuppressWarnings({"MagicNumber", "unchecked"})
public final class EntityMetadataCodec
{
    private EntityMetadataCodec()
    {
    }

    public static void encode(final PacketDataSerializer data, final EntityMetadataEntry<?> metadataObject)
    {
        final int i = ((metadataObject.getDataType().getId() << 5) | (metadataObject.getIndex() & 0x1F)) & 0xFF;

        data.writeByte(i);
        switch (metadataObject.getDataType())
        {
            case BYTE:
                data.writeByte(((EntityMetadataByteEntry) metadataObject).getValue());
                break;
            case SHORT:
                data.writeShort(((EntityMetadataShortEntry) metadataObject).getValue());
                break;
            case INT:
                data.writeInt(((EntityMetadataIntEntry) metadataObject).getValue());
                break;
            case FLOAT:
                data.writeFloat(((EntityMetadataFloatEntry) metadataObject).getValue());
                break;
            case STRING:
                data.writeText(((EntityMetadataObjectEntry<String>) metadataObject).getData());
                break;
            case ITEM_STACK:
                final ItemStack itemstack = ((EntityMetadataObjectEntry<ItemStack>) metadataObject).getData();
                data.writeItemStack(itemstack);
                break;
            case LOCATION:
                final BlockLocation blockposition = ((EntityMetadataObjectEntry<BlockLocation>) metadataObject).getData();

                data.writeInt(blockposition.getX());
                data.writeInt(blockposition.getY());
                data.writeInt(blockposition.getZ());
                break;
            case ROTATION:
                final Vector3F vector3f = ((EntityMetadataObjectEntry<Vector3F>) metadataObject).getData();

                data.writeFloat(vector3f.getX());
                data.writeFloat(vector3f.getY());
                data.writeFloat(vector3f.getZ());
            default:
                throw new UnsupportedOperationException("Unknown entity metadata type (" + metadataObject.getDataType() + "): " + metadataObject);
        }
    }

    public static List<EntityMetadataEntry<?>> decode(final PacketDataSerializer data)
    {
        final List<EntityMetadataEntry<?>> temp = new ArrayList<>(5);

        for (byte b = data.readByte(); b != 127; b = data.readByte())
        {
            final int type = (b & 0xE0) >> 5;
            final int index = b & 0x1F;

            final EntityMetadataEntry<?> object;
            switch (type)
            {
                case 0:
                    object = new EntityMetadataByteEntry(index, data.readByte());
                    break;
                case 1:
                    object = new EntityMetadataShortEntry(index, data.readShort());
                    break;
                case 2:
                    object = new EntityMetadataIntEntry(index, data.readInt());
                    break;
                case 3:
                    object = new EntityMetadataFloatEntry(index, data.readFloat());
                    break;
                case 4:
                    object = new EntityMetadataStringEntry(index, data.readText(Short.MAX_VALUE));
                    break;
                case 5:
                    object = new EntityMetadataItemStackEntry(index, data.readItemStack());
                    break;
                case 6:
                    final int x = data.readInt();
                    final int y = data.readInt();
                    final int z = data.readInt();

                    object = new EntityMetadataBlockLocationEntry(index, new BlockLocation(x, y, z));
                    break;
                case 7:
                    final float f = data.readFloat();
                    final float f1 = data.readFloat();
                    final float f2 = data.readFloat();

                    object = new EntityMetadataVector3FEntry(index, new Vector3F(f, f1, f2));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown entity metadata type: " + type);
            }
            temp.add(object);
        }

        return temp;
    }

}
