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

package org.diorite.cfg.simple;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.math.DioriteMathUtils;

public class MemorySection implements ConfigurationSection
{
    protected final Map<String, Object>  map;
    private final   ConfigurationSection parent;
    private final   ConfigurationSection root;
    private final   String               path;
    private final   String               fullPath;
    private final   char                 pathSeparator;

    public MemorySection(final Map<String, Object> map, final char pathSeparator)
    {
        this.map = map;
        this.pathSeparator = pathSeparator;
        this.root = this;
        this.path = "";
        this.fullPath = "";
        this.parent = null;
    }

    public MemorySection(final Map<String, Object> map)
    {
        this.pathSeparator = DEFAULT_SEPARATOR;
        this.root = this;
        this.path = "";
        this.fullPath = "";
        this.parent = null;

        this.map = map;
    }

    public MemorySection(final char pathSeparator)
    {
        this.pathSeparator = pathSeparator;
        this.root = this;
        this.path = "";
        this.fullPath = "";
        this.parent = null;

        this.map = new LinkedHashMap<>(10);
    }

    public MemorySection()
    {
        this.root = this;
        this.path = "";
        this.fullPath = "";
        this.parent = null;
        this.pathSeparator = DEFAULT_SEPARATOR;

        this.map = new LinkedHashMap<>(10);
    }

    protected MemorySection(final ConfigurationSection parent, final String path)
    {
        Validate.notNull(parent, "Parent cannot be null");
        Validate.notNull(path, "Path cannot be null");

        this.root = parent.getRoot();
        this.path = path;
        this.parent = parent;
        this.pathSeparator = parent.getPathSeparator();

        this.fullPath = createPath(parent, path);

        this.map = new LinkedHashMap<>(10);
    }

    @Override
    public Map<String, Object> getMap()
    {
        return this.map;
    }

    @Override
    public ConfigurationSection getRoot()
    {
        return this.root;
    }

    @Override
    public char getPathSeparator()
    {
        return this.pathSeparator;
    }

    @Override
    public Set<String> getKeys(final boolean deep)
    {
        final Set<String> result = new LinkedHashSet<>(20);
        this.mapChildrenKeys(result, this, deep);
        return result;
    }

    @Override
    public Map<String, Object> getValues(final boolean deep)
    {
        final Map<String, Object> result = new LinkedHashMap<>(20);
        this.mapChildrenValues(result, this, deep);

        return result;
    }

    @Override
    public boolean contains(final String path)
    {
        return this.get(path) != null;
    }

    @Override
    public boolean isSet(final String path)
    {
        return this.get(path, null) != null;
    }

    @Override
    public String getCurrentPath()
    {
        return this.fullPath;
    }

    @Override
    public String getName()
    {
        return this.path;
    }

    @Override
    public ConfigurationSection getParent()
    {
        return this.parent;
    }

    @Override
    public Object get(final String path)
    {
        return this.get(path, null);
    }

    @Override
    public Object get(final String path, final Object def)
    {
        Validate.notNull(path, "Path cannot be null");

        if (path.isEmpty())
        {
            return this;
        }
        // i1 is the leading (higher) index
        // i2 is the trailing (lower) index
        int i1 = - 1, i2;
        ConfigurationSection section = this;
        while ((i1 = path.indexOf(this.pathSeparator, i2 = i1 + 1)) != - 1)
        {
            section = section.getConfigurationSection(path.substring(i2, i1));
            if (section == null)
            {
                return def;
            }
        }

        final String key = path.substring(i2);
        if (section.equals(this))
        {
            final Object result = this.map.get(key);
            return (result == null) ? def : result;
        }
        return section.get(key, def);
    }

    @Override
    public void set(final String path, final Object value)
    {
        Validate.notEmpty(path, "Cannot set to an empty path");

        // i1 is the leading (higher) index
        // i2 is the trailing (lower) index
        int i1 = - 1, i2;
        ConfigurationSection section = this;
        while ((i1 = path.indexOf(this.pathSeparator, i2 = i1 + 1)) != - 1)
        {
            final String node = path.substring(i2, i1);
            final ConfigurationSection subSection = section.getConfigurationSection(node);
            if (subSection == null)
            {
                section = section.createSection(node);
            }
            else
            {
                section = subSection;
            }
        }

        final String key = path.substring(i2);
        if (section.equals(this))
        {
            if (value == null)
            {
                this.map.remove(key);
            }
            else
            {
                this.map.put(key, value);
            }
        }
        else
        {
            section.set(key, value);
        }
    }

    @Override
    public ConfigurationSection createSection(final String path)
    {
        Validate.notEmpty(path, "Cannot create section at empty path");

        // i1 is the leading (higher) index
        // i2 is the trailing (lower) index
        int i1 = - 1, i2;
        ConfigurationSection section = this;
        while ((i1 = path.indexOf(this.pathSeparator, i2 = i1 + 1)) != - 1)
        {
            final String node = path.substring(i2, i1);
            final ConfigurationSection subSection = section.getConfigurationSection(node);
            if (subSection == null)
            {
                section = section.createSection(node);
            }
            else
            {
                section = subSection;
            }
        }

        final String key = path.substring(i2);
        if (section.equals(this))
        {
            final ConfigurationSection result = new MemorySection(this, key);
            this.map.put(key, result);
            return result;
        }
        return section.createSection(key);
    }

    @Override
    public ConfigurationSection createSection(final String path, final Map<?, ?> map)
    {
        final ConfigurationSection section = this.createSection(path);

        for (final Map.Entry<?, ?> entry : map.entrySet())
        {
            if (entry.getValue() instanceof Map)
            {
                section.createSection(entry.getKey().toString(), (Map<?, ?>) entry.getValue());
            }
            else
            {
                section.set(entry.getKey().toString(), entry.getValue());
            }
        }

        return section;
    }

    // Primitives
    @Override
    public String getString(final String path)
    {
        return this.getString(path, null);
    }

    @Override
    public String getString(final String path, final String def)
    {
        final Object val = this.get(path, def);
        return (val != null) ? val.toString() : def;
    }

    @Override
    public boolean isString(final String path)
    {
        final Object val = this.get(path);
        return val instanceof String;
    }

    @Override
    public int getInt(final String path)
    {
        return this.getInt(path, 0);
    }

    @Override
    public int getInt(final String path, final int def)
    {
        final Object val = this.get(path, def);
        return (val instanceof Number) ? toInt(val) : def;
    }

    @Override
    public boolean isInt(final String path)
    {
        final Object val = this.get(path);
        return val instanceof Integer;
    }

    @Override
    public boolean getBoolean(final String path)
    {
        return this.getBoolean(path, false);
    }

    @Override
    public boolean getBoolean(final String path, final boolean def)
    {
        final Object val = this.get(path, def);
        return (val instanceof Boolean) ? (Boolean) val : def;
    }

    @Override
    public boolean isBoolean(final String path)
    {
        final Object val = this.get(path);
        return val instanceof Boolean;
    }

    @Override
    public double getDouble(final String path)
    {
        return this.getDouble(path, 0);
    }

    @Override
    public double getDouble(final String path, final double def)
    {
        final Object val = this.get(path, def);
        return (val instanceof Number) ? toDouble(val) : def;
    }

    @Override
    public boolean isDouble(final String path)
    {
        final Object val = this.get(path);
        return val instanceof Double;
    }

    @Override
    public long getLong(final String path)
    {
        return this.getLong(path, 0);
    }

    @Override
    public long getLong(final String path, final long def)
    {
        final Object val = this.get(path, def);
        return (val instanceof Number) ? toLong(val) : def;
    }

    @Override
    public boolean isLong(final String path)
    {
        final Object val = this.get(path);
        return val instanceof Long;
    }

    // Java
    @Override
    public List<?> getList(final String path)
    {
        return this.getList(path, null);
    }

    @Override
    public List<?> getList(final String path, final List<?> def)
    {
        final Object val = this.get(path, def);
        return (List<?>) ((val instanceof List) ? val : def);
    }

    @Override
    public boolean isList(final String path)
    {
        final Object val = this.get(path);
        return val instanceof List;
    }

    @Override
    public List<String> getStringList(final String path)
    {
        final List<?> list = this.getList(path);

        if (list == null)
        {
            return new ArrayList<>(0);
        }

        final List<String> result = new ArrayList<>(10);

        result.addAll(list.stream().filter(object -> (object instanceof String) || (this.isPrimitiveWrapper(object))).map(String::valueOf).collect(Collectors.toList()));

        return result;
    }

    @Override
    public List<Integer> getIntegerList(final String path)
    {
        final List<?> list = this.getList(path);

        if (list == null)
        {
            return new ArrayList<>(0);
        }

        final List<Integer> result = new ArrayList<>(10);

        for (final Object object : list)
        {
            if (object instanceof Integer)
            {
                result.add((Integer) object);
            }
            else if (object instanceof String)
            {
                try
                {
                    result.add(Integer.valueOf((String) object));
                } catch (final Exception ignored)
                {
                }
            }
            else if (object instanceof Character)
            {
                result.add((int) object);
            }
            else if (object instanceof Number)
            {
                result.add(((Number) object).intValue());
            }
        }

        return result;
    }

    @Override
    public List<Boolean> getBooleanList(final String path)
    {
        final List<?> list = this.getList(path);

        if (list == null)
        {
            return new ArrayList<>(0);
        }

        final List<Boolean> result = new ArrayList<>(10);

        for (final Object object : list)
        {
            if (object instanceof Boolean)
            {
                result.add((Boolean) object);
            }
            else if (object instanceof String)
            {
                if (Boolean.TRUE.toString().equals(object))
                {
                    result.add(true);
                }
                else if (Boolean.FALSE.toString().equals(object))
                {
                    result.add(false);
                }
            }
        }

        return result;
    }

    @Override
    public List<Double> getDoubleList(final String path)
    {
        final List<?> list = this.getList(path);

        if (list == null)
        {
            return new ArrayList<>(0);
        }

        final List<Double> result = new ArrayList<>(10);

        for (final Object object : list)
        {
            if (object instanceof Double)
            {
                result.add((Double) object);
            }
            else if (object instanceof String)
            {
                try
                {
                    result.add(Double.valueOf((String) object));
                } catch (final Exception ignored)
                {
                }
            }
            else if (object instanceof Character)
            {
                result.add((double) object);
            }
            else if (object instanceof Number)
            {
                result.add(((Number) object).doubleValue());
            }
        }

        return result;
    }

    @Override
    public List<Float> getFloatList(final String path)
    {
        final List<?> list = this.getList(path);

        if (list == null)
        {
            return new ArrayList<>(0);
        }

        final List<Float> result = new ArrayList<>(10);

        for (final Object object : list)
        {
            if (object instanceof Float)
            {
                result.add((Float) object);
            }
            else if (object instanceof String)
            {
                try
                {
                    result.add(Float.valueOf((String) object));
                } catch (final Exception ignored)
                {
                }
            }
            else if (object instanceof Character)
            {
                result.add((float) object);
            }
            else if (object instanceof Number)
            {
                result.add(((Number) object).floatValue());
            }
        }

        return result;
    }

    @Override
    public List<Long> getLongList(final String path)
    {
        final List<?> list = this.getList(path);

        if (list == null)
        {
            return new ArrayList<>(0);
        }

        final List<Long> result = new ArrayList<>(10);

        for (final Object object : list)
        {
            if (object instanceof Long)
            {
                result.add((Long) object);
            }
            else if (object instanceof String)
            {
                try
                {
                    result.add(Long.valueOf((String) object));
                } catch (final Exception ignored)
                {
                }
            }
            else if (object instanceof Character)
            {
                result.add((long) object);
            }
            else if (object instanceof Number)
            {
                result.add(((Number) object).longValue());
            }
        }

        return result;
    }

    @Override
    public List<Byte> getByteList(final String path)
    {
        final List<?> list = this.getList(path);

        if (list == null)
        {
            return new ArrayList<>(0);
        }

        final List<Byte> result = new ArrayList<>(10);

        for (final Object object : list)
        {
            if (object instanceof Byte)
            {
                result.add((Byte) object);
            }
            else if (object instanceof String)
            {
                try
                {
                    result.add(Byte.valueOf((String) object));
                } catch (final Exception ignored)
                {
                }
            }
            else if (object instanceof Character)
            {
                result.add((byte) ((Character) object).charValue());
            }
            else if (object instanceof Number)
            {
                result.add(((Number) object).byteValue());
            }
        }

        return result;
    }

    @Override
    public List<Character> getCharacterList(final String path)
    {
        final List<?> list = this.getList(path);

        if (list == null)
        {
            return new ArrayList<>(0);
        }

        final List<Character> result = new ArrayList<>(10);

        for (final Object object : list)
        {
            if (object instanceof Character)
            {
                result.add((Character) object);
            }
            else if (object instanceof String)
            {
                final String str = (String) object;

                if (str.length() == 1)
                {
                    result.add(str.charAt(0));
                }
            }
            else if (object instanceof Number)
            {
                result.add((char) ((Number) object).intValue());
            }
        }

        return result;
    }

    @Override
    public List<Short> getShortList(final String path)
    {
        final List<?> list = this.getList(path);

        if (list == null)
        {
            return new ArrayList<>(0);
        }

        final List<Short> result = new ArrayList<>(10);

        for (final Object object : list)
        {
            if (object instanceof Short)
            {
                result.add((Short) object);
            }
            else if (object instanceof String)
            {
                try
                {
                    result.add(Short.valueOf((String) object));
                } catch (final Exception ignored)
                {
                }
            }
            else if (object instanceof Character)
            {
                result.add((short) ((Character) object).charValue());
            }
            else if (object instanceof Number)
            {
                result.add(((Number) object).shortValue());
            }
        }

        return result;
    }

    @Override
    public List<Map<?, ?>> getMapList(final String path)
    {
        final List<?> list = this.getList(path);
        final List<Map<?, ?>> result = new ArrayList<>(10);

        if (list == null)
        {
            return result;
        }

        result.addAll(list.stream().filter(object -> object instanceof Map).map(object -> (Map<?, ?>) object).collect(Collectors.toList()));

        return result;
    }

    @Override
    public ConfigurationSection getConfigurationSection(final String path)
    {
        final Object val = this.get(path, null);
        return (val instanceof ConfigurationSection) ? (ConfigurationSection) val : null;
    }

    @Override
    public boolean isConfigurationSection(final String path)
    {
        final Object val = this.get(path);
        return val instanceof ConfigurationSection;
    }

    protected boolean isPrimitiveWrapper(final Object input)
    {
        return (input instanceof Integer) || (input instanceof Boolean) ||
                       (input instanceof Character) || (input instanceof Byte) ||
                       (input instanceof Short) || (input instanceof Double) ||
                       (input instanceof Long) || (input instanceof Float);
    }

    protected void mapChildrenKeys(final Set<String> output, final ConfigurationSection section, final boolean deep)
    {
        if (section instanceof MemorySection)
        {
            final MemorySection sec = (MemorySection) section;

            for (final Map.Entry<String, Object> entry : sec.map.entrySet())
            {
                output.add(createPath(section, entry.getKey(), this));

                if ((deep) && (entry.getValue() instanceof ConfigurationSection))
                {
                    final ConfigurationSection subsection = (ConfigurationSection) entry.getValue();
                    this.mapChildrenKeys(output, subsection, true);
                }
            }
        }
        else
        {
            final Set<String> keys = section.getKeys(deep);

            output.addAll(keys.stream().map(key -> createPath(section, key, this)).collect(Collectors.toList()));
        }
    }

    protected void mapChildrenValues(final Map<String, Object> output, final ConfigurationSection section, final boolean deep)
    {
        if (section instanceof MemorySection)
        {
            final MemorySection sec = (MemorySection) section;

            for (final Map.Entry<String, Object> entry : sec.map.entrySet())
            {
                output.put(createPath(section, entry.getKey(), this), entry.getValue());

                if ((entry.getValue() instanceof ConfigurationSection) && deep)
                {
                    this.mapChildrenValues(output, (ConfigurationSection) entry.getValue(), true);
                }
            }
        }
        else
        {
            final Map<String, Object> values = section.getValues(deep);

            for (final Map.Entry<String, Object> entry : values.entrySet())
            {
                output.put(createPath(section, entry.getKey(), this), entry.getValue());
            }
        }
    }

    public static String createPath(final ConfigurationSection section, final String key)
    {
        return createPath(section, key, (section == null) ? null : section.getRoot());
    }

    public static String createPath(final ConfigurationSection section, final String key, final ConfigurationSection relativeTo)
    {
        Validate.notNull(section, "Cannot create path without a section");

        final StringBuilder builder = new StringBuilder();
        final char separator = section.getPathSeparator();
        for (ConfigurationSection parent = section; (parent != null) && (! parent.equals(relativeTo)); parent = parent.getParent())
        {
            if (builder.length() > 0)
            {
                builder.insert(0, separator);
            }

            builder.insert(0, parent.getName());
        }

        if ((key != null) && (! key.isEmpty()))
        {
            if (builder.length() > 0)
            {
                builder.append(separator);
            }

            builder.append(key);
        }

        return builder.toString();
    }

    static int toInt(final Object object)
    {
        if ((object instanceof Number))
        {
            return ((Number) object).intValue();
        }
        final Integer i = DioriteMathUtils.asInt(object.toString());
        return (i == null) ? 0 : i;
    }

//    static float toFloat(final Object object)
//    {
//        if ((object instanceof Number))
//        {
//            return ((Number) object).floatValue();
//        }
//        try
//        {
//            return Float.valueOf(object.toString());
//        } catch (final NumberFormatException | NullPointerException ignored)
//        {
//        }
//        return 0.0F;
//    }

    static double toDouble(final Object object)
    {
        if ((object instanceof Number))
        {
            return ((Number) object).doubleValue();
        }
        try
        {
            return Double.valueOf(object.toString());
        } catch (final NumberFormatException | NullPointerException ignored)
        {
        }
        return 0.0D;
    }

    static long toLong(final Object object)
    {
        if ((object instanceof Number))
        {
            return ((Number) object).longValue();
        }
        try
        {
            return Long.valueOf(object.toString());
        } catch (final NumberFormatException | NullPointerException ignored)
        {
        }
        return 0L;
    }

//    static short toShort(final Object object)
//    {
//        if ((object instanceof Number))
//        {
//            return ((Number) object).shortValue();
//        }
//        try
//        {
//            return Short.valueOf(object.toString());
//        } catch (final NumberFormatException | NullPointerException ignored)
//        {
//        }
//        return 0;
//    }

//    static byte toByte(final Object object)
//    {
//        if ((object instanceof Number))
//        {
//            return ((Number) object).byteValue();
//        }
//        try
//        {
//            return Byte.valueOf(object.toString());
//        } catch (final NumberFormatException | NullPointerException ignored)
//        {
//        }
//        return 0;
//    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("map", this.map).append("path", this.path).append("fullPath", this.fullPath).toString();
    }
}
