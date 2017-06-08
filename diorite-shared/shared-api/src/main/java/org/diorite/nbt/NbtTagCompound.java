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

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Represent nbt map-like tag container.
 */
public final class NbtTagCompound extends NbtAbstractTag implements NbtNamedTagContainer
{
    private static final long serialVersionUID = 0;

    /**
     * Value of nbt tag.
     */
    private Map<String, NbtTag> tags;

    /**
     * Raw constructor, allow any values, doesn't copy map etc...
     *
     * @param unused
     *         use null here.
     * @param name
     *         name of this tag.
     * @param map
     *         map of tags.
     */
    private NbtTagCompound(Void unused, String name, Map<String, NbtTag> map)
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
        this.tags = new LinkedHashMap<>(8);
    }

    /**
     * Construct new NbtTagDouble with given name and empty map as value.
     *
     * @param name
     *         name to be used.
     */
    public NbtTagCompound(@Nullable String name)
    {
        super(name);
        this.tags = new LinkedHashMap<>(8);
    }

    /**
     * Construct new NbtTagDouble with given name and map.
     *
     * @param name
     *         name to be used.
     * @param tags
     *         map of tags to be used.
     */
    public NbtTagCompound(@Nullable String name, Map<String, NbtTag> tags)
    {
        super(name);
        this.tags = new LinkedHashMap<>(tags);
    }

    /**
     * Clone constructor.
     *
     * @param nbtTagCompound
     *         tag to be cloned.
     */
    private NbtTagCompound(NbtTagCompound nbtTagCompound)
    {
        super(nbtTagCompound);
        this.tags = new LinkedHashMap<>(nbtTagCompound.tags.size());
        for (Entry<String, NbtTag> entry : nbtTagCompound.tags.entrySet())
        {
            this.setTag(entry.getKey(), entry.getValue().clone());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends NbtTag> T getTag(String name)
    {
        int index = name.indexOf('.');
        if (index == - 1)
        {
            return (T) this.tags.get(name);
        }
        return Optional.ofNullable(this.getCompound(name.substring(0, index))).map(tag -> tag.<T>getTag(name.substring(index + 1))).orElse(null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends NbtTag> T getTag(String name, Class<T> tagClass)
    {
        return (T) this.getTag(name);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends NbtTag> T getTag(String name, T def)
    {
        int index = name.indexOf('.');
        if (index == - 1)
        {
            return (T) this.tags.get(name);
        }
        return Optional.ofNullable(this.getCompound(name.substring(0, index))).map(tag -> tag.<T>getTag(name.substring(index + 1))).orElse(null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends NbtTag> T getTag(String name, Class<T> tagClass, T def)
    {
        return (T) Optional.ofNullable(this.getTag(name)).orElse(def);
    }

    @Override
    public Map<String, NbtTag> getTags()
    {
        return new LinkedHashMap<>(this.tags);
    }

    @Override
    public NbtTag removeTag(String name)
    {
        int index = name.indexOf('.');
        if (index == - 1)
        {
            return this.tags.remove(name);
        }
        NbtTagCompound tag = this.getCompound(name.substring(0, index));
        if (tag == null)
        {
            return null;
        }
        NbtTag removed = tag.removeTag(name.substring(index + 1));
        if (tag.isEmpty())
        {
            if (tag.name == null)
            {
                this.removeTag(tag);
            }
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
    public boolean containsKey(@Nullable Object key)
    {
        return this.tags.containsKey(key);
    }

    @Override
    public boolean containsValue(@Nullable Object value)
    {
        return this.tags.containsValue(value);
    }

    @Override
    @Nullable
    public NbtTag get(@Nullable Object key)
    {
        return this.tags.get(key);
    }

    @Override
    @Nullable
    public NbtTag put(@Nullable String key, @Nullable NbtTag value)
    {
        if (key == null)
        {
            return null;
        }
        if (value == null)
        {
            return this.removeTag(key);
        }
        value.setName(key);
        return this.tags.put(key, value);
    }

    @Override
    @Nullable
    public NbtTag remove(@Nullable Object key)
    {
        return this.tags.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends NbtTag> m)
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
    public boolean addTag(NbtTag tag)
    {
        if (this.tags.containsKey(tag.getName()))
        {
            this.tags.get(tag.getName()).setParent(null);
        }
        this.tags.put(tag.getName(), tag);
        tag.setParent(this);
        return true;
    }

    @Override
    public void setTag(String key, NbtTag tag)
    {
        if (this.tags.containsKey(key))
        {
            this.tags.get(key).setParent(null);
        }
        this.tags.put(key, tag);
        tag.setParent(this);
    }

    @Override
    public void addTag(String path, NbtTag tag)
    {
        NbtTagCompound compound = this.getCompound(path);
        if (compound == null)
        {
            throw new IllegalStateException("Can't find compound at: " + path);
        }
        compound.addTag(tag);
    }

    @Override
    public boolean containsTag(String name)
    {
        int index = name.indexOf('.');
        if (index == - 1)
        {
            return this.tags.containsKey(name);
        }
        NbtTagCompound tag = this.getCompound(name.substring(0, index));
        return (tag != null) && tag.containsTag(name.substring(index + 1));
    }

    @Override
    public void removeTag(NbtTag tag)
    {
        this.tags.remove(tag.getName());
    }

    /*
     * Simple get methods.
     */

    /**
     * Returns NbtTagCompound on given path.
     *
     * @param name
     *         path of nbt tag.
     *
     * @return NbtTagCompound on given path or null.
     */
    @Nullable
    public NbtTagCompound getCompound(String name)
    {
        return this.getTag(name, NbtTagCompound.class);
    }

    /**
     * Returns NbtTagCompound on given path or create new if it don't exist.
     *
     * @param name
     *         path of nbt tag.
     *
     * @return NbtTagCompound on given path.
     */
    public NbtTagCompound getOrCreateCompound(String name)
    {
        NbtTagCompound result = this;
        while (true)
        {
            NbtTagCompound nbt = result.getTag(name, NbtTagCompound.class);
            if (nbt != null)
            {
                return nbt;
            }
            int index = name.indexOf('.');
            if (index == - 1)
            {
                result.addTag(nbt = new NbtTagCompound(name));
                return nbt;
            }
            name = name.substring(index + 1);
            result = result.getOrCreateCompound(name.substring(0, index));
        }
    }

    /**
     * Returns int on given path.
     *
     * @param name
     *         path of nbt tag.
     *
     * @return int on given path or 0.
     */
    public int getInt(String name)
    {
        return this.getTag(name, NbtTagInt.class, new NbtTagInt(name, 0)).getValue();
    }

    /**
     * Returns int on given path.
     *
     * @param name
     *         path of nbt tag.
     *
     * @return int on given path or null.
     */
    public Integer getInteger(String name)
    {
        return Optional.ofNullable(this.getTag(name, NbtTagInt.class)).map(NbtTagInt::getValue).orElse(null);
    }

    /**
     * Returns short on given path.
     *
     * @param name
     *         path of nbt tag.
     *
     * @return short on given path or 0.
     */
    public short getShort(String name)
    {
        return this.getTag(name, NbtTagShort.class, new NbtTagShort(name, (short) 0)).getValue();
    }

    /**
     * Returns short on given path.
     *
     * @param name
     *         path of nbt tag.
     *
     * @return short on given path or null.
     */
    public Short getShortObj(String name)
    {
        return Optional.ofNullable(this.getTag(name, NbtTagShort.class)).map(NbtTagShort::getValue).orElse(null);
    }

    /**
     * Returns byte on given path.
     *
     * @param name
     *         path of nbt tag.
     *
     * @return byte on given path or 0.
     */
    public byte getByte(String name)
    {

        return this.getTag(name, NbtTagByte.class, new NbtTagByte(name, (byte) 0)).getValue();
    }

    /**
     * Returns byte on given path.
     *
     * @param name
     *         path of nbt tag.
     *
     * @return byte on given path or null.
     */
    public Byte getByteObj(String name)
    {
        return Optional.ofNullable(this.getTag(name, NbtTagByte.class)).map(NbtTagByte::getValue).orElse(null);
    }

    /**
     * Returns long on given path.
     *
     * @param name
     *         path of nbt tag.
     *
     * @return long on given path or 0.
     */
    public long getLong(String name)
    {
        return this.getTag(name, NbtTagLong.class, new NbtTagLong(name, 0)).getValue();
    }

    /**
     * Returns long on given path.
     *
     * @param name
     *         path of nbt tag.
     *
     * @return long on given path or null.
     */
    public Long getLongObj(String name)
    {
        return Optional.ofNullable(this.getTag(name, NbtTagLong.class)).map(NbtTagLong::getValue).orElse(null);
    }

    /**
     * Returns double on given path.
     *
     * @param name
     *         path of nbt tag.
     *
     * @return double on given path or 0.
     */
    public double getDouble(String name)
    {
        return this.getTag(name, NbtTagDouble.class, new NbtTagDouble(name, 0)).getValue();
    }

    /**
     * Returns double on given path.
     *
     * @param name
     *         path of nbt tag.
     *
     * @return double on given path or null.
     */
    public Double getDoubleObj(String name)
    {
        return Optional.ofNullable(this.getTag(name, NbtTagDouble.class)).map(NbtTagDouble::getValue).orElse(null);
    }

    /**
     * Returns float on given path.
     *
     * @param name
     *         path of nbt tag.
     *
     * @return float on given path or 0.
     */
    public float getFloat(String name)
    {
        return this.getTag(name, NbtTagFloat.class, new NbtTagFloat(name, 0)).getValue();
    }

    /**
     * Returns float on given path.
     *
     * @param name
     *         path of nbt tag.
     *
     * @return float on given path or null.
     */
    public Float getFloatObj(String name)
    {
        return Optional.ofNullable(this.getTag(name, NbtTagFloat.class)).map(NbtTagFloat::getValue).orElse(null);
    }

    /**
     * Returns string on given path.
     *
     * @param name
     *         path of nbt tag.
     *
     * @return string on given path or null.
     */
    public String getString(String name)
    {
        return Optional.ofNullable(this.getTag(name, NbtTagString.class)).map(NbtTagString::getValue).orElse(null);
    }

    /**
     * Returns list on given path.
     *
     * @param name
     *         path of nbt tag.
     * @param itemClass
     *         type of list.
     * @param <T>
     *         type of list.
     *
     * @return list on given path or null.
     */
    public <T extends NbtTag> List<T> getList(String name, Class<T> itemClass)
    {
        return Optional.ofNullable(this.getTag(name, NbtTagList.class)).map(t -> t.getTags(itemClass)).orElse(null);
    }

    /**
     * Returns long array on given path.
     *
     * @param name
     *         path of nbt tag.
     *
     * @return long array on given path or null.
     */
    public int[] getIntArray(String name)
    {
        return Optional.ofNullable(this.getTag(name, NbtTagIntArray.class)).map(NbtTagIntArray::getValue).orElse(null);
    }

    /**
     * Returns byte array on given path.
     *
     * @param name
     *         path of nbt tag.
     *
     * @return byte array on given path or null.
     */
    public byte[] getByteArray(String name)
    {
        return Optional.ofNullable(this.getTag(name, NbtTagByteArray.class)).map(NbtTagByteArray::getValue).orElse(null);
    }

    /*
     * Get methods with default
     */

    /**
     * Returns NbtTagCompound on given path.
     *
     * @param name
     *         path of nbt tag.
     * @param def
     *         default value.
     *
     * @return NbtTagCompound on given path or default value.
     */
    public NbtTagCompound getCompound(String name, NbtTagCompound def)
    {
        return this.getTag(name, NbtTagCompound.class, def);
    }

    /**
     * Returns int on given path.
     *
     * @param name
     *         path of nbt tag.
     * @param def
     *         default value.
     *
     * @return int on given path or default value.
     */
    public int getInt(String name, NbtTagInt def)
    {
        return this.getTag(name, NbtTagInt.class, def).getValue();
    }

    /**
     * Returns short on given path.
     *
     * @param name
     *         path of nbt tag.
     * @param def
     *         default value.
     *
     * @return short on given path or default value.
     */
    public short getShort(String name, NbtTagShort def)
    {
        return this.getTag(name, NbtTagShort.class, def).getValue();
    }

    /**
     * Returns byte on given path.
     *
     * @param name
     *         path of nbt tag.
     * @param def
     *         default value.
     *
     * @return byte on given path or default value.
     */
    public byte getByte(String name, NbtTagByte def)
    {
        return this.getTag(name, NbtTagByte.class, def).getValue();
    }

    /**
     * Returns long on given path.
     *
     * @param name
     *         path of nbt tag.
     * @param def
     *         default value.
     *
     * @return long on given path or default value.
     */
    public long getLong(String name, NbtTagLong def)
    {
        return this.getTag(name, NbtTagLong.class, def).getValue();
    }

    /**
     * Returns double on given path.
     *
     * @param name
     *         path of nbt tag.
     * @param def
     *         default value.
     *
     * @return double on given path or default value.
     */
    public double getDouble(String name, NbtTagDouble def)
    {
        return this.getTag(name, NbtTagDouble.class, def).getValue();
    }

    /**
     * Returns float on given path.
     *
     * @param name
     *         path of nbt tag.
     * @param def
     *         default value.
     *
     * @return float on given path or default value.
     */
    public float getFloat(String name, NbtTagFloat def)
    {
        return this.getTag(name, NbtTagFloat.class, def).getValue();
    }

    /**
     * Returns string on given path.
     *
     * @param name
     *         path of nbt tag.
     * @param def
     *         default value.
     *
     * @return string on given path or default value.
     */
    @Nullable
    public String getString(String name, NbtTagString def)
    {
        return this.getTag(name, NbtTagString.class, def).getValue();
    }

    /**
     * Returns nbt list on given path.
     *
     * @param name
     *         path of nbt tag.
     * @param itemClass
     *         type of list.
     * @param def
     *         default value.
     * @param <T>
     *         type of list.
     *
     * @return nbt list on given path or default value.
     */
    public <T extends NbtTag> List<T> getList(String name, Class<T> itemClass, NbtTagList def)
    {
        return this.getTag(name, NbtTagList.class, def).getTags(itemClass);
    }

    /**
     * Returns int array on given path.
     *
     * @param name
     *         path of nbt tag.
     * @param def
     *         default value.
     *
     * @return int array on given path or default value.
     */
    public int[] getIntArray(String name, NbtTagIntArray def)
    {
        return this.getTag(name, NbtTagIntArray.class, def).getValue();
    }

    /**
     * Returns byte array on given path.
     *
     * @param name
     *         path of nbt tag.
     * @param def
     *         default value.
     *
     * @return byte array on given path or default value.
     */
    public byte[] getByteArray(String name, NbtTagByteArray def)
    {
        return this.getTag(name, NbtTagByteArray.class, def).getValue();
    }

    /*
     * Get methods with simple default
     */

    /**
     * Returns int on given path.
     *
     * @param name
     *         path of nbt tag.
     * @param def
     *         default value.
     *
     * @return int on given path or default value.
     */
    public int getInt(String name, int def)
    {
        return this.getTag(name, NbtTagInt.class, new NbtTagInt(name, def)).getValue();
    }

    /**
     * Returns short on given path.
     *
     * @param name
     *         path of nbt tag.
     * @param def
     *         default value.
     *
     * @return short on given path or default value.
     */
    public short getShort(String name, int def)
    {
        return this.getTag(name, NbtTagShort.class, new NbtTagShort(name, (short) def)).getValue();
    }

    /**
     * Returns byte on given path.
     *
     * @param name
     *         path of nbt tag.
     * @param def
     *         default value.
     *
     * @return byte on given path or default value.
     */
    public byte getByte(String name, int def)
    {
        return this.getTag(name, NbtTagByte.class, new NbtTagByte(name, (byte) def)).getValue();
    }

    /**
     * Returns long on given path.
     *
     * @param name
     *         path of nbt tag.
     * @param def
     *         default value.
     *
     * @return long on given path or default value.
     */
    public long getLong(String name, long def)
    {
        return this.getTag(name, NbtTagLong.class, new NbtTagLong(name, def)).getValue();
    }

    /**
     * Returns double on given path.
     *
     * @param name
     *         path of nbt tag.
     * @param def
     *         default value.
     *
     * @return double on given path or default value.
     */
    public double getDouble(String name, double def)
    {
        return this.getTag(name, NbtTagDouble.class, new NbtTagDouble(name, def)).getValue();
    }

    /**
     * Returns float on given path.
     *
     * @param name
     *         path of nbt tag.
     * @param def
     *         default value.
     *
     * @return float on given path or default value.
     */
    public float getFloat(String name, double def)
    {
        return this.getTag(name, NbtTagFloat.class, new NbtTagFloat(name, (float) def)).getValue();
    }

    /**
     * Returns string on given path.
     *
     * @param name
     *         path of nbt tag.
     * @param def
     *         default value.
     *
     * @return string on given path or default value.
     */
    @Nullable
    public String getString(String name, String def)
    {
        return this.getTag(name, NbtTagString.class, new NbtTagString(name, def)).getValue();
    }

    /**
     * Returns nbt list on given path.
     *
     * @param name
     *         path of nbt tag.
     * @param itemClass
     *         type of list.
     * @param def
     *         default value.
     * @param <T>
     *         type of list.
     *
     * @return nbt list on given path or default value.
     */
    public <T extends NbtTag> List<T> getList(String name, Class<T> itemClass, List<T> def)
    {
        return this.getTag(name, NbtTagList.class, new NbtTagList(name, def)).getTags(itemClass);
    }

    /**
     * Returns int array on given path.
     *
     * @param name
     *         path of nbt tag.
     * @param def
     *         default value.
     *
     * @return int array on given path or default value.
     */
    public int[] getIntArray(String name, int[] def)
    {
        return this.getTag(name, NbtTagIntArray.class, new NbtTagIntArray(name, def)).getValue();
    }

    /**
     * Returns byte array on given path.
     *
     * @param name
     *         path of nbt tag.
     * @param def
     *         default value.
     *
     * @return byte array on given path or default value.
     */
    public byte[] getByteArray(String name, byte[] def)
    {
        return this.getTag(name, NbtTagByteArray.class, new NbtTagByteArray(name, def)).getValue();
    }


    /*
     * Boolean methods.
     */

    /**
     * Set boolean value on given path, this method will set byte tag to 1 or 0.
     *
     * @param path
     *         path of value.
     * @param value
     *         value to set.
     */
    public void setBoolean(String path, boolean value)
    {
        this.setByte(path, value ? 1 : 0);
    }

    /**
     * Returns boolean on given path, this method will check if byte on given path is equals to 1.
     *
     * @param name
     *         path of nbt tag.
     *
     * @return boolean on given path or false.
     */
    public boolean getBoolean(String name)
    {
        return this.getByte(name) == 1;
    }

    /**
     * Returns boolean on given path, this method will check if byte on given path is equals to 1.
     *
     * @param name
     *         path of nbt tag.
     * @param def
     *         default value.
     *
     * @return boolean on given path or default value.
     */
    public boolean getBoolean(String name, boolean def)
    {
        return this.getByte(name, def ? 1 : 0) == 1;
    }

    /*
     * Set methods.
     */

    /**
     * Set nbt list value on given path.
     *
     * @param path
     *         path of value.
     * @param value
     *         value to set.
     */
    public void setList(String path, List<NbtTag> value)
    {
        int index = path.indexOf('.');
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
     * @param path
     *         path of value.
     * @param value
     *         value to set.
     */
    public void setByteArray(String path, byte[] value)
    {
        int index = path.indexOf('.');
        if (index == - 1)
        {
            this.addTag(new NbtTagByteArray(path, value));
            return;
        }
        this.getOrCreateCompound(path.substring(0, index)).setByteArray(path.substring(index + 1), value);
    }

    /**
     * Set int array value on given path.
     *
     * @param path
     *         path of value.
     * @param value
     *         value to set.
     */
    public void setIntArray(String path, int[] value)
    {
        int index = path.indexOf('.');
        if (index == - 1)
        {
            this.addTag(new NbtTagIntArray(path, value));
            return;
        }
        this.getOrCreateCompound(path.substring(0, index)).setIntArray(path.substring(index + 1), value);
    }

    /**
     * Set string value on given path.
     *
     * @param path
     *         path of value.
     * @param value
     *         value to set.
     */
    public void setString(String path, String value)
    {
        int index = path.indexOf('.');
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
     * @param path
     *         path of value.
     * @param value
     *         value to set.
     */
    public void setShort(String path, int value)
    {
        int index = path.indexOf('.');
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
     * @param path
     *         path of value.
     * @param value
     *         value to set.
     */
    public void setShort(String path, short value)
    {
        int index = path.indexOf('.');
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
     * @param path
     *         path of value.
     * @param value
     *         value to set.
     */
    public void setDouble(String path, double value)
    {
        int index = path.indexOf('.');
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
     * @param path
     *         path of value.
     * @param value
     *         value to set.
     */
    public void setFloat(String path, double value)
    {
        int index = path.indexOf('.');
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
     * @param path
     *         path of value.
     * @param value
     *         value to set.
     */
    public void setFloat(String path, float value)
    {
        int index = path.indexOf('.');
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
     * @param path
     *         path of value.
     * @param value
     *         value to set.
     */
    public void setLong(String path, long value)
    {
        int index = path.indexOf('.');
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
     * @param path
     *         path of value.
     * @param value
     *         value to set.
     */
    public void setByte(String path, int value)
    {
        int index = path.indexOf('.');
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
     * @param path
     *         path of value.
     * @param value
     *         value to set.
     */
    public void setByte(String path, byte value)
    {
        int index = path.indexOf('.');
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
     * @param path
     *         path of value.
     * @param value
     *         value to set.
     */
    public void setInt(String path, int value)
    {
        int index = path.indexOf('.');
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
        Map<String, Object> result = new LinkedHashMap<>(this.tags.size());
        for (Entry<String, NbtTag> entry : this.tags.entrySet())
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
    public void write(NbtOutputStream outputStream, boolean anonymous) throws IOException
    {
        super.write(outputStream, anonymous);
        for (Entry<String, NbtTag> tagEntry : this.tags.entrySet())
        {
            outputStream.writeByte(tagEntry.getValue().getTagID());
            tagEntry.getValue().write(outputStream, false);
        }
        outputStream.writeByte(NbtTagType.END.getTypeID());
    }

    @Override
    public void read(NbtInputStream inputStream, boolean anonymous, NbtLimiter limiter) throws IOException
    {
        super.read(inputStream, anonymous, limiter);
        limiter.incrementElementsCount(1);
        limiter.incrementComplexity();

        this.tags = new LinkedHashMap<>(8);
        do
        {
            byte type = inputStream.readByte();
            NbtTagType tagType = NbtTagType.valueOf(type);
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
    public boolean equals(@Nullable Object o)
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

        NbtTagCompound that = (NbtTagCompound) o;
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
        StringBuilder sb = new StringBuilder(this.tags.size() * 100);
        sb.append('{');
        for (Entry<String, NbtTag> entry : this.tags.entrySet())
        {
            if (sb.length() != 1)
            {
                sb.append(',');
            }
            sb.append(entry.getKey()).append(':').append(entry.getValue());

        }
        return sb.append('}').toString();
    }
}