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

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represent nbt map-like tag container.
 */
public class NbtTagCompound extends NbtAbstractTag implements NbtNamedTagContainer
{
    private static final long serialVersionUID = 0;

    /**
     * Value of nbt tag.
     */
    protected Map<String, NbtTag> tags;

    /**
     * Raw constructor, allow any values, doesn't copy map etc...
     *
     * @param unused use null here.
     * @param name   name of this tag.
     * @param map    map of tags.
     */
    protected NbtTagCompound(Void unused, final String name, Map<String, NbtTag> map)
    {
        super(name);
        this.tags = map;
    }

    /**
     * Construct new NbtTagCompound with empty string name and empty map as value.
     */
    public NbtTagCompound()
    {
        super("");
        this.tags = new HashMap<>(8);
    }

    /**
     * Construct new NbtTagDouble with given name and empty map as value.
     *
     * @param name name to be used.
     */
    public NbtTagCompound(final String name)
    {
        super(name);
        this.tags = new HashMap<>(8);
    }

    /**
     * Construct new NbtTagDouble with given name and map.
     *
     * @param name name to be used.
     * @param tags map of tags to be used.
     */
    public NbtTagCompound(final String name, final Map<String, NbtTag> tags)
    {
        super(name);
        this.tags = new HashMap<>(tags);
    }

    /**
     * Clone constructor.
     *
     * @param nbtTagCompound tag to be cloned.
     */
    protected NbtTagCompound(final NbtTagCompound nbtTagCompound)
    {
        super(nbtTagCompound);
        this.tags = new HashMap<>(nbtTagCompound.tags.size());
        for (final Entry<String, NbtTag> entry : nbtTagCompound.tags.entrySet())
        {
            this.setTag(entry.getKey(), entry.getValue().clone());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends NbtTag> T getTag(final String name)
    {
        final int index = name.indexOf('.');
        if (index == - 1)
        {
            return (T) this.tags.get(name);
        }
        return Optional.ofNullable(this.getCompound(name.substring(0, index))).map(tag -> tag.<T>getTag(name.substring(index + 1))).orElse(null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends NbtTag> T getTag(final String name, final Class<T> tagClass)
    {
        return (T) this.getTag(name);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends NbtTag> T getTag(final String name, final T def)
    {
        final int index = name.indexOf('.');
        if (index == - 1)
        {
            return (T) this.tags.get(name);
        }
        return Optional.ofNullable(this.getCompound(name.substring(0, index))).map(tag -> tag.<T>getTag(name.substring(index + 1))).orElse(null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends NbtTag> T getTag(final String name, final Class<T> tagClass, final T def)
    {
        return (T) Optional.ofNullable(this.getTag(name)).orElse(def);
    }

    @Override
    public Map<String, NbtTag> getTags()
    {
        return new HashMap<>(this.tags);
    }

    @Override
    public NbtTag removeTag(final String name)
    {
        final int index = name.indexOf('.');
        if (index == - 1)
        {
            return this.tags.remove(name);
        }
        final NbtTagCompound tag = this.getCompound(name.substring(0, index));
        if (tag == null)
        {
            return null;
        }
        final NbtTag removed = tag.removeTag(name.substring(index + 1));
        if (tag.isEmpty())
        {
            this.removeTag(tag.name);
        }
        return removed;
    }

    @Override
    public int size()
    {
        return this.tags.size();
    }

    @Override
    public boolean isEmpty()
    {
        return this.tags.isEmpty();
    }

    @Override
    public boolean containsKey(final Object key)
    {
        return this.tags.containsKey(key);
    }

    @Override
    public boolean containsValue(final Object value)
    {
        return this.tags.containsValue(value);
    }

    @Override
    public NbtTag get(final Object key)
    {
        return this.tags.get(key);
    }

    @Override
    public NbtTag put(final String key, final NbtTag value)
    {
        value.setName(key);
        return this.tags.put(key, value);
    }

    @Override
    public NbtTag remove(final Object key)
    {
        return this.tags.remove(key);
    }

    @Override
    public void putAll(final Map<? extends String, ? extends NbtTag> m)
    {
        this.tags.putAll(m);
    }

    @Override
    public void clear()
    {
        this.tags.clear();
    }

    @Override
    public Set<String> keySet()
    {
        return this.tags.keySet();
    }

    @Override
    public Collection<NbtTag> values()
    {
        return this.tags.values();
    }

    @Override
    public Set<Entry<String, NbtTag>> entrySet()
    {
        return this.tags.entrySet();
    }

    @Override
    public void addTag(final NbtTag tag)
    {
        if (this.tags.containsKey(tag.getName()))
        {
            this.tags.get(tag.getName()).setParent(null);
        }
        this.tags.put(tag.getName(), tag);
        tag.setParent(this);
    }

    @Override
    public void setTag(final String key, final NbtTag tag)
    {
        if (this.tags.containsKey(key))
        {
            this.tags.get(key).setParent(null);
        }
        this.tags.put(key, tag);
        tag.setParent(this);
    }

    @Override
    public void addTag(final String path, final NbtTag tag)
    {
        final NbtTagCompound compound = this.getCompound(path);
        compound.addTag(tag);
    }

    @Override
    public boolean containsTag(final String name)
    {
        final int index = name.indexOf('.');
        if (index == - 1)
        {
            return this.tags.containsKey(name);
        }
        final NbtTagCompound tag = this.getCompound(name.substring(0, index));
        return (tag != null) && tag.containsTag(name.substring(index + 1));
    }

    @Override
    public void removeTag(final NbtTag tag)
    {
        this.tags.remove(tag.getName());
    }

    /*
     * Simple get methods.
     */

    /**
     * Returns NbtTagCompound on given path.
     *
     * @param name path of nbt tag.
     *
     * @return NbtTagCompound on given path or null.
     */
    public NbtTagCompound getCompound(final String name)
    {
        return this.getTag(name, NbtTagCompound.class);
    }

    /**
     * Returns NbtTagCompound on given path or create new if it don't exist.
     *
     * @param name path of nbt tag.
     *
     * @return NbtTagCompound on given path.
     */
    public NbtTagCompound getOrCreateCompound(final String name)
    {
        NbtTagCompound nbt = this.getTag(name, NbtTagCompound.class);
        if (nbt != null)
        {
            return nbt;
        }
        final int index = name.indexOf('.');
        if (index == - 1)
        {
            this.addTag(nbt = new NbtTagCompound(name));
            return nbt;
        }
        return this.getOrCreateCompound(name.substring(0, index)).getOrCreateCompound(name.substring(index + 1));
    }

    /**
     * Returns int on given path.
     *
     * @param name path of nbt tag.
     *
     * @return int on given path or 0.
     */
    public int getInt(final String name)
    {
        return this.getTag(name, NbtTagInt.class, new NbtTagInt(name, 0)).getValue();
    }

    /**
     * Returns int on given path.
     *
     * @param name path of nbt tag.
     *
     * @return int on given path or null.
     */
    public Integer getInteger(final String name)
    {
        return Optional.ofNullable(this.getTag(name, NbtTagInt.class)).map(NbtTagInt::getValue).orElse(null);
    }

    /**
     * Returns short on given path.
     *
     * @param name path of nbt tag.
     *
     * @return short on given path or 0.
     */
    public short getShort(final String name)
    {
        return this.getTag(name, NbtTagShort.class, new NbtTagShort(name, (short) 0)).getValue();
    }

    /**
     * Returns short on given path.
     *
     * @param name path of nbt tag.
     *
     * @return short on given path or null.
     */
    public Short getShortObj(final String name)
    {
        return Optional.ofNullable(this.getTag(name, NbtTagShort.class)).map(NbtTagShort::getValue).orElse(null);
    }

    /**
     * Returns byte on given path.
     *
     * @param name path of nbt tag.
     *
     * @return byte on given path or 0.
     */
    public byte getByte(final String name)
    {

        return this.getTag(name, NbtTagByte.class, new NbtTagByte(name, (byte) 0)).getValue();
    }

    /**
     * Returns byte on given path.
     *
     * @param name path of nbt tag.
     *
     * @return byte on given path or null.
     */
    public Byte getByteObj(final String name)
    {
        return Optional.ofNullable(this.getTag(name, NbtTagByte.class)).map(NbtTagByte::getValue).orElse(null);
    }

    /**
     * Returns long on given path.
     *
     * @param name path of nbt tag.
     *
     * @return long on given path or 0.
     */
    public long getLong(final String name)
    {
        return this.getTag(name, NbtTagLong.class, new NbtTagLong(name, 0)).getValue();
    }

    /**
     * Returns long on given path.
     *
     * @param name path of nbt tag.
     *
     * @return long on given path or null.
     */
    public Long getLongObj(final String name)
    {
        return Optional.ofNullable(this.getTag(name, NbtTagLong.class)).map(NbtTagLong::getValue).orElse(null);
    }

    /**
     * Returns double on given path.
     *
     * @param name path of nbt tag.
     *
     * @return double on given path or 0.
     */
    public double getDouble(final String name)
    {
        return this.getTag(name, NbtTagDouble.class, new NbtTagDouble(name, 0)).getValue();
    }

    /**
     * Returns double on given path.
     *
     * @param name path of nbt tag.
     *
     * @return double on given path or null.
     */
    public Double getDoubleObj(final String name)
    {
        return Optional.ofNullable(this.getTag(name, NbtTagDouble.class)).map(NbtTagDouble::getValue).orElse(null);
    }

    /**
     * Returns float on given path.
     *
     * @param name path of nbt tag.
     *
     * @return float on given path or 0.
     */
    public float getFloat(final String name)
    {
        return this.getTag(name, NbtTagFloat.class, new NbtTagFloat(name, 0)).getValue();
    }

    /**
     * Returns float on given path.
     *
     * @param name path of nbt tag.
     *
     * @return float on given path or null.
     */
    public Float getFloatObj(final String name)
    {
        return Optional.ofNullable(this.getTag(name, NbtTagFloat.class)).map(NbtTagFloat::getValue).orElse(null);
    }

    /**
     * Returns string on given path.
     *
     * @param name path of nbt tag.
     *
     * @return string on given path or null.
     */
    public String getString(final String name)
    {
        return Optional.ofNullable(this.getTag(name, NbtTagString.class)).map(NbtTagString::getValue).orElse(null);
    }

    /**
     * Returns list on given path.
     *
     * @param name      path of nbt tag.
     * @param itemClass type of list.
     * @param <T>       type of list.
     *
     * @return list on given path or null.
     */
    public <T extends NbtTag> List<T> getList(final String name, final Class<T> itemClass)
    {
        return Optional.ofNullable(this.getTag(name, NbtTagList.class)).map(t -> t.getTags(itemClass)).orElse(null);
    }

    /**
     * Returns long array on given path.
     *
     * @param name path of nbt tag.
     *
     * @return long array on given path or null.
     */
    public long[] getLongArray(final String name)
    {
        return Optional.ofNullable(this.getTag(name, NbtTagLongArray.class)).map(NbtTagLongArray::getValue).orElse(null);
    }

    /**
     * Returns long array on given path.
     *
     * @param name path of nbt tag.
     *
     * @return long array on given path or null.
     */
    public int[] getIntArray(final String name)
    {
        return Optional.ofNullable(this.getTag(name, NbtTagIntArray.class)).map(NbtTagIntArray::getValue).orElse(null);
    }

    /**
     * Returns short array on given path.
     *
     * @param name path of nbt tag.
     *
     * @return short array on given path or null.
     */
    public short[] getShortArray(final String name)
    {
        return Optional.ofNullable(this.getTag(name, NbtTagShortArray.class)).map(NbtTagShortArray::getValue).orElse(null);
    }

    /**
     * Returns byte array on given path.
     *
     * @param name path of nbt tag.
     *
     * @return byte array on given path or null.
     */
    public byte[] getByteArray(final String name)
    {
        return Optional.ofNullable(this.getTag(name, NbtTagByteArray.class)).map(NbtTagByteArray::getValue).orElse(null);
    }

    /**
     * Returns float array on given path.
     *
     * @param name path of nbt tag.
     *
     * @return float array on given path or null.
     */
    public float[] getFloatArray(final String name)
    {
        return Optional.ofNullable(this.getTag(name, NbtTagFloatArray.class)).map(NbtTagFloatArray::getValue).orElse(null);
    }

    /**
     * Returns double array on given path.
     *
     * @param name path of nbt tag.
     *
     * @return double array on given path or null.
     */
    public double[] getDoubleArray(final String name)
    {
        return Optional.ofNullable(this.getTag(name, NbtTagDoubleArray.class)).map(NbtTagDoubleArray::getValue).orElse(null);
    }

    /**
     * Returns string array on given path.
     *
     * @param name path of nbt tag.
     *
     * @return string array on given path or null.
     */
    public String[] getStringArray(final String name)
    {
        return Optional.ofNullable(this.getTag(name, NbtTagStringArray.class)).map(NbtTagStringArray::getValue).orElse(null);
    }

    /*
     * Get methods with default
     */

    /**
     * Returns NbtTagCompound on given path.
     *
     * @param name path of nbt tag.
     * @param def  default value.
     *
     * @return NbtTagCompound on given path or default value.
     */
    public NbtTagCompound getCompound(final String name, final NbtTagCompound def)
    {
        return this.getTag(name, NbtTagCompound.class, def);
    }

    /**
     * Returns int on given path.
     *
     * @param name path of nbt tag.
     * @param def  default value.
     *
     * @return int on given path or default value.
     */
    public int getInt(final String name, final NbtTagInt def)
    {
        return this.getTag(name, NbtTagInt.class, def).getValue();
    }

    /**
     * Returns short on given path.
     *
     * @param name path of nbt tag.
     * @param def  default value.
     *
     * @return short on given path or default value.
     */
    public short getShort(final String name, final NbtTagShort def)
    {
        return this.getTag(name, NbtTagShort.class, def).getValue();
    }

    /**
     * Returns byte on given path.
     *
     * @param name path of nbt tag.
     * @param def  default value.
     *
     * @return byte on given path or default value.
     */
    public byte getByte(final String name, final NbtTagByte def)
    {
        return this.getTag(name, NbtTagByte.class, def).getValue();
    }

    /**
     * Returns long on given path.
     *
     * @param name path of nbt tag.
     * @param def  default value.
     *
     * @return long on given path or default value.
     */
    public long getLong(final String name, final NbtTagLong def)
    {
        return this.getTag(name, NbtTagLong.class, def).getValue();
    }

    /**
     * Returns double on given path.
     *
     * @param name path of nbt tag.
     * @param def  default value.
     *
     * @return double on given path or default value.
     */
    public double getDouble(final String name, final NbtTagDouble def)
    {
        return this.getTag(name, NbtTagDouble.class, def).getValue();
    }

    /**
     * Returns float on given path.
     *
     * @param name path of nbt tag.
     * @param def  default value.
     *
     * @return float on given path or default value.
     */
    public float getFloat(final String name, final NbtTagFloat def)
    {
        return this.getTag(name, NbtTagFloat.class, def).getValue();
    }

    /**
     * Returns string on given path.
     *
     * @param name path of nbt tag.
     * @param def  default value.
     *
     * @return string on given path or default value.
     */
    public String getString(final String name, final NbtTagString def)
    {
        return this.getTag(name, NbtTagString.class, def).getValue();
    }

    /**
     * Returns nbt list on given path.
     *
     * @param name      path of nbt tag.
     * @param itemClass type of list.
     * @param def       default value.
     * @param <T>       type of list.
     *
     * @return nbt list on given path or default value.
     */
    public <T extends NbtTag> List<T> getList(final String name, final Class<T> itemClass, final NbtTagList def)
    {
        return this.getTag(name, NbtTagList.class, def).getTags(itemClass);
    }

    /**
     * Returns long array on given path.
     *
     * @param name path of nbt tag.
     * @param def  default value.
     *
     * @return long array on given path or default value.
     */
    public long[] getLongArray(final String name, final NbtTagLongArray def)
    {
        return this.getTag(name, NbtTagLongArray.class, def).getValue();
    }

    /**
     * Returns int array on given path.
     *
     * @param name path of nbt tag.
     * @param def  default value.
     *
     * @return int array on given path or default value.
     */
    public int[] getIntArray(final String name, final NbtTagIntArray def)
    {
        return this.getTag(name, NbtTagIntArray.class, def).getValue();
    }

    /**
     * Returns short array on given path.
     *
     * @param name path of nbt tag.
     * @param def  default value.
     *
     * @return short array on given path or default value.
     */
    public short[] getShortArray(final String name, final NbtTagShortArray def)
    {
        return this.getTag(name, NbtTagShortArray.class, def).getValue();
    }

    /**
     * Returns byte array on given path.
     *
     * @param name path of nbt tag.
     * @param def  default value.
     *
     * @return byte array on given path or default value.
     */
    public byte[] getByteArray(final String name, final NbtTagByteArray def)
    {
        return this.getTag(name, NbtTagByteArray.class, def).getValue();
    }

    /**
     * Returns float array on given path.
     *
     * @param name path of nbt tag.
     * @param def  default value.
     *
     * @return float array on given path or default value.
     */
    public float[] getFloatArray(final String name, final NbtTagFloatArray def)
    {
        return this.getTag(name, NbtTagFloatArray.class, def).getValue();
    }

    /**
     * Returns double array on given path.
     *
     * @param name path of nbt tag.
     * @param def  default value.
     *
     * @return double array on given path or default value.
     */
    public double[] getDoubleArray(final String name, final NbtTagDoubleArray def)
    {
        return this.getTag(name, NbtTagDoubleArray.class, def).getValue();
    }

    /**
     * Returns string array on given path.
     *
     * @param name path of nbt tag.
     * @param def  default value.
     *
     * @return string array on given path or default value.
     */
    public String[] getStringArray(final String name, final NbtTagStringArray def)
    {
        return this.getTag(name, NbtTagStringArray.class, def).getValue();
    }

    /*
     * Get methods with simple default
     */

    /**
     * Returns int on given path.
     *
     * @param name path of nbt tag.
     * @param def  default value.
     *
     * @return int on given path or default value.
     */
    public int getInt(final String name, final int def)
    {
        return this.getTag(name, NbtTagInt.class, new NbtTagInt(name, def)).getValue();
    }

    /**
     * Returns short on given path.
     *
     * @param name path of nbt tag.
     * @param def  default value.
     *
     * @return short on given path or default value.
     */
    public short getShort(final String name, final int def)
    {
        return this.getTag(name, NbtTagShort.class, new NbtTagShort(name, (short) def)).getValue();
    }

    /**
     * Returns byte on given path.
     *
     * @param name path of nbt tag.
     * @param def  default value.
     *
     * @return byte on given path or default value.
     */
    public byte getByte(final String name, final int def)
    {
        return this.getTag(name, NbtTagByte.class, new NbtTagByte(name, (byte) def)).getValue();
    }

    /**
     * Returns long on given path.
     *
     * @param name path of nbt tag.
     * @param def  default value.
     *
     * @return long on given path or default value.
     */
    public long getLong(final String name, final long def)
    {
        return this.getTag(name, NbtTagLong.class, new NbtTagLong(name, def)).getValue();
    }

    /**
     * Returns double on given path.
     *
     * @param name path of nbt tag.
     * @param def  default value.
     *
     * @return double on given path or default value.
     */
    public double getDouble(final String name, final double def)
    {
        return this.getTag(name, NbtTagDouble.class, new NbtTagDouble(name, def)).getValue();
    }

    /**
     * Returns float on given path.
     *
     * @param name path of nbt tag.
     * @param def  default value.
     *
     * @return float on given path or default value.
     */
    public float getFloat(final String name, final double def)
    {
        return this.getTag(name, NbtTagFloat.class, new NbtTagFloat(name, (float) def)).getValue();
    }

    /**
     * Returns string on given path.
     *
     * @param name path of nbt tag.
     * @param def  default value.
     *
     * @return string on given path or default value.
     */
    public String getString(final String name, final String def)
    {
        return this.getTag(name, NbtTagString.class, new NbtTagString(name, def)).getValue();
    }

    /**
     * Returns nbt list on given path.
     *
     * @param name      path of nbt tag.
     * @param itemClass type of list.
     * @param def       default value.
     * @param <T>       type of list.
     *
     * @return nbt list on given path or default value.
     */
    public <T extends NbtTag> List<T> getList(final String name, final Class<T> itemClass, final List<T> def)
    {
        return this.getTag(name, NbtTagList.class, new NbtTagList(name, def)).getTags(itemClass);
    }

    /**
     * Returns long array on given path.
     *
     * @param name path of nbt tag.
     * @param def  default value.
     *
     * @return long array on given path or default value.
     */
    public long[] getLongArray(final String name, final long[] def)
    {
        return this.getTag(name, NbtTagLongArray.class, new NbtTagLongArray(name, def)).getValue();
    }

    /**
     * Returns int array on given path.
     *
     * @param name path of nbt tag.
     * @param def  default value.
     *
     * @return int array on given path or default value.
     */
    public int[] getIntArray(final String name, final int[] def)
    {
        return this.getTag(name, NbtTagIntArray.class, new NbtTagIntArray(name, def)).getValue();
    }

    /**
     * Returns short array on given path.
     *
     * @param name path of nbt tag.
     * @param def  default value.
     *
     * @return short array on given path or default value.
     */
    public short[] getShortArray(final String name, final short[] def)
    {
        return this.getTag(name, NbtTagShortArray.class, new NbtTagShortArray(name, def)).getValue();
    }

    /**
     * Returns byte array on given path.
     *
     * @param name path of nbt tag.
     * @param def  default value.
     *
     * @return byte array on given path or default value.
     */
    public byte[] getByteArray(final String name, final byte[] def)
    {
        return this.getTag(name, NbtTagByteArray.class, new NbtTagByteArray(name, def)).getValue();
    }

    /**
     * Returns float array on given path.
     *
     * @param name path of nbt tag.
     * @param def  default value.
     *
     * @return float array on given path or default value.
     */
    public float[] getFloatArray(final String name, final float[] def)
    {
        return this.getTag(name, NbtTagFloatArray.class, new NbtTagFloatArray(name, def)).getValue();
    }

    /**
     * Returns double array on given path.
     *
     * @param name path of nbt tag.
     * @param def  default value.
     *
     * @return double array on given path or default value.
     */
    public double[] getDoubleArray(final String name, final double[] def)
    {
        return this.getTag(name, NbtTagDoubleArray.class, new NbtTagDoubleArray(name, def)).getValue();
    }

    /**
     * Returns string array on given path.
     *
     * @param name path of nbt tag.
     * @param def  default value.
     *
     * @return string array on given path or default value.
     */
    public String[] getStringArray(final String name, final String[] def)
    {
        return this.getTag(name, NbtTagStringArray.class, new NbtTagStringArray(name, def)).getValue();
    }


    /*
     * Boolean methods.
     */

    /**
     * Set boolean value on given path, this method will set byte tag to 1 or 0.
     *
     * @param path  path of value.
     * @param value value to set.
     */
    public void setBoolean(final String path, final boolean value)
    {
        this.setByte(path, value ? 1 : 0);
    }

    /**
     * Returns boolean on given path, this method will check if byte on given path is equals to 1.
     *
     * @param name path of nbt tag.
     *
     * @return boolean on given path or false.
     */
    public boolean getBoolean(final String name)
    {
        return this.getByte(name) == 1;
    }

    /**
     * Returns boolean on given path, this method will check if byte on given path is equals to 1.
     *
     * @param name path of nbt tag.
     * @param def  default value.
     *
     * @return boolean on given path or default value.
     */
    public boolean getBoolean(final String name, final boolean def)
    {
        return this.getByte(name, def ? 1 : 0) == 1;
    }

    /*
     * Set methods.
     */

    /**
     * Set nbt list value on given path.
     *
     * @param path  path of value.
     * @param value value to set.
     */
    public void setList(final String path, final List<NbtTag> value)
    {
        final int index = path.indexOf('.');
        if (index == - 1)
        {
            this.addTag(new NbtTagList(path, value));
            return;
        }
        this.getOrCreateCompound(path.substring(0, index)).setList(path.substring(index + 1), value);
    }

    /**
     * Set byte array value on given path.
     *
     * @param path  path of value.
     * @param value value to set.
     */
    public void setByteArray(final String path, final byte[] value)
    {
        final int index = path.indexOf('.');
        if (index == - 1)
        {
            this.addTag(new NbtTagByteArray(path, value));
            return;
        }
        this.getOrCreateCompound(path.substring(0, index)).setByteArray(path.substring(index + 1), value);
    }

    /**
     * Set long array value on given path.
     *
     * @param path  path of value.
     * @param value value to set.
     */
    public void setLongArray(final String path, final long[] value)
    {
        final int index = path.indexOf('.');
        if (index == - 1)
        {
            this.addTag(new NbtTagLongArray(path, value));
            return;
        }
        this.getOrCreateCompound(path.substring(0, index)).setLongArray(path.substring(index + 1), value);
    }

    /**
     * Set int array value on given path.
     *
     * @param path  path of value.
     * @param value value to set.
     */
    public void setIntArray(final String path, final int[] value)
    {
        final int index = path.indexOf('.');
        if (index == - 1)
        {
            this.addTag(new NbtTagIntArray(path, value));
            return;
        }
        this.getOrCreateCompound(path.substring(0, index)).setIntArray(path.substring(index + 1), value);
    }

    /**
     * Set short array value on given path.
     *
     * @param path  path of value.
     * @param value value to set.
     */
    public void setShortArray(final String path, final short[] value)
    {
        final int index = path.indexOf('.');
        if (index == - 1)
        {
            this.addTag(new NbtTagShortArray(path, value));
            return;
        }
        this.getOrCreateCompound(path.substring(0, index)).setShortArray(path.substring(index + 1), value);
    }

    /**
     * Set float array value on given path.
     *
     * @param path  path of value.
     * @param value value to set.
     */
    public void setFloatArray(final String path, final float[] value)
    {
        final int index = path.indexOf('.');
        if (index == - 1)
        {
            this.addTag(new NbtTagFloatArray(path, value));
            return;
        }
        this.getOrCreateCompound(path.substring(0, index)).setFloatArray(path.substring(index + 1), value);
    }

    /**
     * Set double array value on given path.
     *
     * @param path  path of value.
     * @param value value to set.
     */
    public void setDoubleArray(final String path, final double[] value)
    {
        final int index = path.indexOf('.');
        if (index == - 1)
        {
            this.addTag(new NbtTagDoubleArray(path, value));
            return;
        }
        this.getOrCreateCompound(path.substring(0, index)).setDoubleArray(path.substring(index + 1), value);
    }

    /**
     * Set string array value on given path.
     *
     * @param path  path of value.
     * @param value value to set.
     */
    public void setStringArray(final String path, final String[] value)
    {
        final int index = path.indexOf('.');
        if (index == - 1)
        {
            this.addTag(new NbtTagStringArray(path, value));
            return;
        }
        this.getOrCreateCompound(path.substring(0, index)).setStringArray(path.substring(index + 1), value);
    }

    /**
     * Set string value on given path.
     *
     * @param path  path of value.
     * @param value value to set.
     */
    public void setString(final String path, final String value)
    {
        final int index = path.indexOf('.');
        if (index == - 1)
        {
            this.addTag(new NbtTagString(path, value));
            return;
        }
        this.getOrCreateCompound(path.substring(0, index)).setString(path.substring(index + 1), value);
    }

    /**
     * Set short value on given path.
     *
     * @param path  path of value.
     * @param value value to set.
     */
    public void setShort(final String path, final int value)
    {
        final int index = path.indexOf('.');
        if (index == - 1)
        {
            this.addTag(new NbtTagShort(path, (short) value));
            return;
        }
        this.getOrCreateCompound(path.substring(0, index)).setShort(path.substring(index + 1), value);
    }

    /**
     * Set short value on given path.
     *
     * @param path  path of value.
     * @param value value to set.
     */
    public void setShort(final String path, final short value)
    {
        final int index = path.indexOf('.');
        if (index == - 1)
        {
            this.addTag(new NbtTagShort(path, value));
            return;
        }
        this.getOrCreateCompound(path.substring(0, index)).setShort(path.substring(index + 1), value);
    }

    /**
     * Set double value on given path.
     *
     * @param path  path of value.
     * @param value value to set.
     */
    public void setDouble(final String path, final double value)
    {
        final int index = path.indexOf('.');
        if (index == - 1)
        {
            this.addTag(new NbtTagDouble(path, value));
            return;
        }
        this.getOrCreateCompound(path.substring(0, index)).setDouble(path.substring(index + 1), value);
    }

    /**
     * Set float value on given path.
     *
     * @param path  path of value.
     * @param value value to set.
     */
    public void setFloat(final String path, final double value)
    {
        final int index = path.indexOf('.');
        if (index == - 1)
        {
            this.addTag(new NbtTagFloat(path, (float) value));
            return;
        }
        this.getOrCreateCompound(path.substring(0, index)).setFloat(path.substring(index + 1), value);
    }

    /**
     * Set float value on given path.
     *
     * @param path  path of value.
     * @param value value to set.
     */
    public void setFloat(final String path, final float value)
    {
        final int index = path.indexOf('.');
        if (index == - 1)
        {
            this.addTag(new NbtTagFloat(path, value));
            return;
        }
        this.getOrCreateCompound(path.substring(0, index)).setFloat(path.substring(index + 1), value);
    }

    /**
     * Set long value on given path.
     *
     * @param path  path of value.
     * @param value value to set.
     */
    public void setLong(final String path, final long value)
    {
        final int index = path.indexOf('.');
        if (index == - 1)
        {
            this.addTag(new NbtTagLong(path, value));
            return;
        }
        this.getOrCreateCompound(path.substring(0, index)).setLong(path.substring(index + 1), value);
    }

    /**
     * Set byte value on given path.
     *
     * @param path  path of value.
     * @param value value to set.
     */
    public void setByte(final String path, final int value)
    {
        final int index = path.indexOf('.');
        if (index == - 1)
        {
            this.addTag(new NbtTagByte(path, (byte) value));
            return;
        }
        this.getOrCreateCompound(path.substring(0, index)).setByte(path.substring(index + 1), value);
    }

    /**
     * Set byte value on given path.
     *
     * @param path  path of value.
     * @param value value to set.
     */
    public void setByte(final String path, final byte value)
    {
        final int index = path.indexOf('.');
        if (index == - 1)
        {
            this.addTag(new NbtTagByte(path, value));
            return;
        }
        this.getOrCreateCompound(path.substring(0, index)).setByte(path.substring(index + 1), value);
    }

    /**
     * Set int value on given path.
     *
     * @param path  path of value.
     * @param value value to set.
     */
    public void setInt(final String path, final int value)
    {
        final int index = path.indexOf('.');
        if (index == - 1)
        {
            this.addTag(new NbtTagInt(path, value));
            return;
        }
        this.getOrCreateCompound(path.substring(0, index)).setInt(path.substring(index + 1), value);
    }

    @Override
    public Map<String, Object> getNBTValue()
    {
        final Map<String, Object> result = new HashMap<>(this.tags.size());
        for (final Entry<String, NbtTag> entry : this.tags.entrySet())
        {
            result.put(entry.getKey(), entry.getValue().getNBTValue());
        }
        return result;
    }

    @Override
    public NbtTagType getTagType()
    {
        return NbtTagType.COMPOUND;
    }

    @Override
    public void write(final NbtOutputStream outputStream, final boolean anonymous) throws IOException
    {
        super.write(outputStream, anonymous);
        for (final Map.Entry<String, NbtTag> tagEntry : this.tags.entrySet())
        {
            outputStream.writeByte(tagEntry.getValue().getTagID());
            tagEntry.getValue().write(outputStream, false);
        }
        outputStream.writeByte(NbtTagType.END.getTypeID());
    }

    @Override
    public void read(final NbtInputStream inputStream, final boolean anonymous, final NbtLimiter limiter) throws IOException
    {
        super.read(inputStream, anonymous, limiter);
        limiter.incrementElementsCount(1);
        limiter.incrementComplexity();

        this.tags = new HashMap<>(8);
        do
        {
            final byte type = inputStream.readByte();
            final NbtTagType tagType = NbtTagType.valueOf(type);
            if (tagType == null)
            {
                throw new IOException("Could not find a tag for type ID " + type + ".");
            }
            if (tagType == NbtTagType.END)
            {
                break;
            }
            this.addTag(inputStream.readTag(tagType, false, limiter));
        } while (true);
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public NbtTagCompound clone()
    {
        return new NbtTagCompound(this);
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof NbtTagCompound))
        {
            return false;
        }
        if (! super.equals(o))
        {
            return false;
        }

        final NbtTagCompound that = (NbtTagCompound) o;
        return this.tags.equals(that.tags);
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = (31 * result) + this.tags.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("tags", this.tags).toString();
    }
}