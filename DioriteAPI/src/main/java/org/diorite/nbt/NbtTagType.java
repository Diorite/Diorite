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

package org.diorite.nbt;

import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableMap;

@SuppressWarnings("MagicNumber")
public enum NbtTagType
{
    END(0, NbtTagEnd.class, NbtTagEnd::new),
    BYTE(1, NbtTagByte.class, NbtTagByte::new),
    SHORT(2, NbtTagShort.class, NbtTagShort::new),
    INTEGER(3, NbtTagInt.class, NbtTagInt::new),
    LONG(4, NbtTagLong.class, NbtTagLong::new),
    FLOAT(5, NbtTagFloat.class, NbtTagFloat::new),
    DOUBLE(6, NbtTagDouble.class, NbtTagDouble::new),
    BYTE_ARRAY(7, NbtTagByteArray.class, NbtTagByteArray::new),
    STRING(8, NbtTagString.class, NbtTagString::new),
    LIST(9, NbtTagList.class, NbtTagList::new),
    COMPOUND(10, NbtTagCompound.class, NbtTagCompound::new),
    INTEGER_ARRAY(11, NbtTagIntArray.class, NbtTagIntArray::new),
    // elements added by diorite, they break minecraft compatybility, use with caution.
    SHORT_ARRAY(127, NbtTagShortArray.class, NbtTagShortArray::new),
    LONG_ARRAY(126, NbtTagLongArray.class, NbtTagLongArray::new),
    FLOAT_ARRAY(125, NbtTagFloatArray.class, NbtTagFloatArray::new),
    DOUBLE_ARRAY(124, NbtTagDoubleArray.class, NbtTagDoubleArray::new),
    STRING_ARRAY(123, NbtTagStringArray.class, NbtTagStringArray::new);
    private static final Map<Byte, NbtTagType>           typeMap;
    private final        byte                            typeID;
    private final        Class<? extends NbtAbstractTag> typeClass;
    private final        Supplier<NbtAbstractTag>        getInstance;

    NbtTagType(final int typeID, final Class<? extends NbtAbstractTag> type, final Supplier<NbtAbstractTag> getInstance)
    {
        this.typeID = ((byte) typeID);
        this.typeClass = type;
        this.getInstance = getInstance;
    }

    public byte getTypeID()
    {
        return this.typeID;
    }

    public Class<? extends NbtAbstractTag> getTypeClass()
    {
        return this.typeClass;
    }

    @SuppressWarnings("unchecked")
    public <T extends NbtAbstractTag> T newInstance()
    {
        return (T) this.getInstance.get();
    }

    public static NbtTagType valueOf(final byte typeID)
    {
        return typeMap.get(typeID);
    }

    static
    {
        {
            final ImmutableMap.Builder<Byte, NbtTagType> mapBuilder = new ImmutableMap.Builder<>();
            for (final NbtTagType type : values())
            {
                mapBuilder.put(type.typeID, type);
            }
            typeMap = mapBuilder.build();
        }
    }
}