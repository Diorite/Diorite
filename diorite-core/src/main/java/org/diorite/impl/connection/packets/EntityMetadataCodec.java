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

import org.diorite.impl.entity.meta.EntityMetadataType;
import org.diorite.impl.entity.meta.entry.EntityMetadataEntry;

@SuppressWarnings({"MagicNumber", "unchecked"})
public final class EntityMetadataCodec
{
    private EntityMetadataCodec()
    {
    }

    public static void encode(final PacketDataSerializer data, final EntityMetadataEntry<?> metadataObject)
    {
        data.writeByte(metadataObject.getIndex());
        data.writeByte(metadataObject.getDataType().getId());
        metadataObject.write(data);
    }

    public static List<EntityMetadataEntry<?>> decode(final PacketDataSerializer data)
    {
        final List<EntityMetadataEntry<?>> temp = new ArrayList<>(5);

        while (true)
        {
            final int index = data.readUnsignedByte();
            if (index == 0xff)
            {
                break;
            }
            final byte byteType = data.readByte();
            final EntityMetadataType[] values = EntityMetadataType.values();
            if (byteType >= values.length)
            {
                throw new IllegalArgumentException("Unknown metadata type (index: " + index + ", type: " + byteType + ")");
            }
            final EntityMetadataType type = values[byteType];
            final EntityMetadataEntry<?> object = type.read(index, data);
            temp.add(object);
        }

        return temp;
    }

}
