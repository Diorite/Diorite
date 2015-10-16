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

import org.diorite.impl.entity.meta.entry.EntityMetadataBlockLocationEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataByteEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataFloatEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataIntEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataItemStackEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataShortEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataStringEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataVector3FEntry;

public enum EntityMetadataType
{
    /**
     * byte value type, {@link EntityMetadataByteEntry}
     */
    BYTE(0, EntityMetadataByteEntry.class),
    /**
     * short value type, {@link EntityMetadataShortEntry}
     */
    SHORT(1, EntityMetadataShortEntry.class),
    /**
     * integer value type, {@link EntityMetadataIntEntry}
     */
    INT(2, EntityMetadataIntEntry.class),
    /**
     * float value type, {@link EntityMetadataFloatEntry}
     */
    FLOAT(3, EntityMetadataFloatEntry.class),
    /**
     * String value type, {@link EntityMetadataStringEntry}
     * UTF-8 String (VarInt prefixed)
     */
    STRING(4, EntityMetadataStringEntry.class),
    /**
     * ItemStack value type, {@link EntityMetadataItemStackEntry}
     */
    ITEM_STACK(5, EntityMetadataItemStackEntry.class),
    /**
     * BlockLocation value type, {@link EntityMetadataBlockLocationEntry}
     * Int, Int, Int (x, y, z)
     */
    LOCATION(6, EntityMetadataBlockLocationEntry.class),
    /**
     * string value type, {@link EntityMetadataVector3FEntry}
     * Float, Float, Float (pitch, yaw, roll)
     */
    ROTATION(7, EntityMetadataVector3FEntry.class);

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
}
