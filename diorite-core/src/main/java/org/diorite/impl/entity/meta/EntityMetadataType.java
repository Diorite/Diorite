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

package org.diorite.impl.entity.meta;

import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.entity.meta.entry.EntityMetadataBlockLocationEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataByteEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataFloatEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataIntEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataItemStackEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataShortEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataStringEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataVector3FEntry;
import org.diorite.BlockLocation;

public enum EntityMetadataType
{
    /**
     * byte value type, {@link EntityMetadataByteEntry}
     */
    BYTE(0, EntityMetadataByteEntry.class)
            {
                @Override
                public EntityMetadataByteEntry read(final int index, final PacketDataSerializer data)
                {
                    return new EntityMetadataByteEntry(index, data.readByte());
                }
            },
    /**
     * short value type, {@link EntityMetadataShortEntry}
     */
    SHORT(1, EntityMetadataShortEntry.class)
            {
                @Override
                public EntityMetadataByteEntry read(final int index, final PacketDataSerializer data)
                {
                    return new EntityMetadataByteEntry(index, data.readShort());
                }
            },
    /**
     * integer value type, {@link EntityMetadataIntEntry}
     */
    INT(2, EntityMetadataIntEntry.class)
            {
                @Override
                public EntityMetadataIntEntry read(final int index, final PacketDataSerializer data)
                {
                    return new EntityMetadataIntEntry(index, data.readInt());
                }
            },
    /**
     * float value type, {@link EntityMetadataFloatEntry}
     */
    FLOAT(3, EntityMetadataFloatEntry.class)
            {
                @Override
                public EntityMetadataFloatEntry read(final int index, final PacketDataSerializer data)
                {
                    return new EntityMetadataFloatEntry(index, data.readFloat());
                }
            },
    /**
     * String value type, {@link EntityMetadataStringEntry}
     * UTF-8 String (VarInt prefixed)
     */
    STRING(4, EntityMetadataStringEntry.class)
            {
                @Override
                public EntityMetadataStringEntry read(final int index, final PacketDataSerializer data)
                {
                    return new EntityMetadataStringEntry(index, data.readText(Short.MAX_VALUE));
                }
            },
    /**
     * ItemStack value type, {@link EntityMetadataItemStackEntry}
     */
    ITEM_STACK(5, EntityMetadataItemStackEntry.class)
            {
                @Override
                public EntityMetadataItemStackEntry read(final int index, final PacketDataSerializer data)
                {
                    return new EntityMetadataItemStackEntry(index, data.readItemStack());
                }
            },
    /**
     * BlockLocation value type, {@link EntityMetadataBlockLocationEntry}
     * Int, Int, Int (x, y, z)
     */
    LOCATION(6, EntityMetadataBlockLocationEntry.class)
            {
                @Override
                public EntityMetadataBlockLocationEntry read(final int index, final PacketDataSerializer data)
                {
                    final int x = data.readInt();
                    final int y = data.readInt();
                    final int z = data.readInt();
                    return new EntityMetadataBlockLocationEntry(index, new BlockLocation(x, y, z));
                }
            },
    /**
     * string value type, {@link EntityMetadataVector3FEntry}
     * Float, Float, Float (pitch, yaw, roll)
     */
    VECTOR3F(7, EntityMetadataVector3FEntry.class)
            {
                @Override
                public EntityMetadataVector3FEntry read(final int index, final PacketDataSerializer data)
                {
                    return new EntityMetadataVector3FEntry(index, data.readVector3F());
                }
            };

    private final int                                     id;
    private final Class<? extends EntityMetadataEntry<?>> clazz;

    EntityMetadataType(final int id, final Class<? extends EntityMetadataEntry<?>> clazz)
    {
        this.id = id;
        this.clazz = clazz;
    }

    public int getId()
    {
        return this.id;
    }

    public Class<? extends EntityMetadataEntry<?>> getClazz()
    {
        return this.clazz;
    }

    public abstract EntityMetadataEntry<?> read(int index, PacketDataSerializer data);
}
