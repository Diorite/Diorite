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

package org.diorite.nbt;

import javax.annotation.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

import org.diorite.commons.reflections.DioriteReflectionUtils;

import it.unimi.dsi.fastutil.bytes.Byte2ObjectMap;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectMaps;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectOpenHashMap;

/**
 * Enum of nbt tag types.
 */
public enum NbtTagType
{
    /**
     * End nbt tag type.
     */
    END(0, NbtTagEnd.class, NbtTagEnd::new, void.class),
    /**
     * Byte nbt tag type.
     */
    BYTE(1, NbtTagByte.class, NbtTagByte::new, byte.class),
    /**
     * Short nbt tag type.
     */
    SHORT(2, NbtTagShort.class, NbtTagShort::new, short.class),
    /**
     * Integer nbt tag type.
     */
    INTEGER(3, NbtTagInt.class, NbtTagInt::new, int.class),
    /**
     * Long nbt tag type.
     */
    LONG(4, NbtTagLong.class, NbtTagLong::new, long.class),
    /**
     * Float nbt tag type.
     */
    FLOAT(5, NbtTagFloat.class, NbtTagFloat::new, float.class),
    /**
     * Double nbt tag type.
     */
    DOUBLE(6, NbtTagDouble.class, NbtTagDouble::new, double.class),
    /**
     * byte[] nbt tag type.
     */
    BYTE_ARRAY(7, NbtTagByteArray.class, NbtTagByteArray::new, byte[].class),
    /**
     * String nbt tag type.
     */
    STRING(8, NbtTagString.class, NbtTagString::new, String.class),
    /**
     * List nbt tag type.
     */
    LIST(9, NbtTagList.class, NbtTagList::new, List.class),
    /**
     * Compound/Map nbt tag type.
     */
    COMPOUND(10, NbtTagCompound.class, NbtTagCompound::new, Map.class),
    /**
     * int[] nbt tag type.
     */
    INTEGER_ARRAY(11, NbtTagIntArray.class, NbtTagIntArray::new, int[].class);
    private static final Byte2ObjectMap<NbtTagType> typeMap;
    private final        byte                       typeID;
    private final        Class<? extends NbtTag>    typeClass;
    private final        Supplier<NbtTag>           getInstance;
    private final        Class<?>                   valueClass;

    NbtTagType(int typeID, Class<? extends NbtTag> type, Supplier<NbtTag> getInstance, Class<?> valueClass)
    {
        this.valueClass = valueClass;
        this.typeID = ((byte) typeID);
        this.typeClass = type;
        this.getInstance = getInstance;
    }

    /**
     * Returns if of this nbt type.
     *
     * @return if of this nbt type.
     */
    public byte getTypeID()
    {
        return this.typeID;
    }

    /**
     * Returns type of value in this nbt type.
     *
     * @return type of value in this nbt type.
     */
    public Class<?> getValueClass()
    {
        return this.valueClass;
    }

    /**
     * Returns class implementing this nbt type.
     *
     * @return class implementing this nbt type.
     */
    public Class<? extends NbtTag> getTypeClass()
    {
        return this.typeClass;
    }

    /**
     * Returns new instance of given nbt type.
     *
     * @param <T>
     *         type of nbt.
     *
     * @return new instance of given nbt type.
     */
    @SuppressWarnings("unchecked")
    public <T extends NbtTag> T newInstance()
    {
        return (T) this.getInstance.get();
    }

    /**
     * Returns nbt type for given clazz, line {@link NbtTagInt} for int.class or Integer.class
     *
     * @param clazz
     *         class of type value.
     *
     * @return nbt type for given clazz, or null.
     */
    @Nullable
    public static NbtTagType getType(Class<?> clazz)
    {
        for (NbtTagType type : NbtTagType.values())
        {
            Class<?> valueClass = type.valueClass;
            if (valueClass.isAssignableFrom(clazz))
            {
                return type;
            }
            if (valueClass.isPrimitive() && clazz.equals(DioriteReflectionUtils.getWrapperClass(valueClass)))
            {
                return type;
            }
        }
        return null;
    }

    /**
     * Returns new instance of given nbt type with given value, this metod will not work for containers.
     *
     * @param value
     *         value to be used.
     * @param <T>
     *         type of nbt.
     *
     * @return new instance of given nbt type.
     *
     * @throws IllegalArgumentException
     *         if value isn't valid for this nbt tag type.
     */
    @SuppressWarnings("unchecked")
    public <T extends NbtTag> T newInstance(Object value) throws IllegalArgumentException
    {
        T tag = (T) this.getInstance.get();
        switch (this)
        {
            case END:
                break;
            case BYTE:
            case SHORT:
            case INTEGER:
            case LONG:
            case FLOAT:
            case DOUBLE:
                if (value instanceof Number)
                {
                    ((NbtAbstractTagNumber) tag).setNumberValue(((Number) value));
                }
                else
                {
                    throw new IllegalArgumentException("Invalid value type.");
                }
                break;
            case BYTE_ARRAY:
                if (value instanceof byte[])
                {
                    ((NbtTagByteArray) tag).setValue((byte[]) value);
                }
                else
                {
                    throw new IllegalArgumentException("Invalid value type.");
                }
                break;
            case STRING:
                if (value instanceof String)
                {
                    ((NbtTagString) tag).setValue((String) value);
                }
                else
                {
                    throw new IllegalArgumentException("Invalid value type.");
                }
                break;
            case LIST:
                if (value instanceof Iterable)
                {
                    NbtAnonymousTagContainer tagList = (NbtAnonymousTagContainer) tag;
                    Iterable<?> col = (Iterable<?>) value;
                    for (Object o : col)
                    {
                        NbtTagType tagType = (o == null) ? null : getType(o.getClass());
                        if (tagType == null)
                        {
                            throw new IllegalArgumentException("Invalid value type.");
                        }
                        tagList.addTag(tagType.newInstance(o));
                    }
                }
                else
                {
                    throw new IllegalArgumentException("Invalid value type.");
                }
                break;
            case COMPOUND:
                if (value instanceof Map)
                {
                    NbtNamedTagContainer tagMap = (NbtNamedTagContainer) tag;
                    Map<?, ?> map = (Map<?, ?>) value;
                    for (Entry<?, ?> entry : map.entrySet())
                    {
                        String name = entry.getKey().toString();
                        Object o = entry.getValue();
                        NbtTagType tagType = (o == null) ? null : getType(o.getClass());
                        if (tagType == null)
                        {
                            throw new IllegalArgumentException("Invalid value type.");
                        }
                        NbtTag newTag = tagType.newInstance(o);
                        newTag.setName(name);
                        tagMap.setTag(name, newTag);
                    }
                }
                else
                {
                    throw new IllegalArgumentException("Invalid value type.");
                }
                break;
            case INTEGER_ARRAY:
                if (value instanceof int[])
                {
                    ((NbtTagIntArray) tag).setValue((int[]) value);
                }
                else
                {
                    throw new IllegalArgumentException("Invalid value type.");
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid tag type.");
        }
        return tag;
    }

    /**
     * Get nbt type by id.
     *
     * @param typeID
     *         id of nbt type.
     *
     * @return nbt type for given id.
     */
    @Nullable
    public static NbtTagType valueOf(byte typeID)
    {
        return typeMap.get(typeID);
    }

    static
    {
        Byte2ObjectMap<NbtTagType> types = new Byte2ObjectOpenHashMap<>(20, 0.5f);
        for (NbtTagType type : values())
        {
            types.put(type.typeID, type);
        }
        typeMap = Byte2ObjectMaps.unmodifiable(types);
    }
}