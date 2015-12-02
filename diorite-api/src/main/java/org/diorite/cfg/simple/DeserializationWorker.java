package org.diorite.cfg.simple;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.Diorite;
import org.diorite.ILocation;
import org.diorite.ImmutableLocation;
import org.diorite.enchantments.EnchantmentType;

@SuppressWarnings({"rawtypes", "unchecked"})
public class DeserializationWorker
{
    private final Map data;

    private DeserializationWorker(final Map data)
    {
        this.data = data;
    }

    @SuppressWarnings("unchecked")
    public <T> T getTypedObject(final String name)
    {
        if (! this.data.containsKey(name))
        {
            return null;
        }
        return (T) this.data.get(name);
    }

    public <T> T getTypedObject(final String name, final Class<T> clazz)
    {
        return this.getTypedObject(name);
    }

    @SuppressWarnings("unchecked")
    public <T> T getTypedObject(final String name, final T def)
    {
        if (! this.data.containsKey(name))
        {
            return def;
        }
        return (T) this.data.get(name);
    }

    public Object getObject(final String name)
    {
        return this.getObject(name, null);
    }

    public Object getObject(final String name, final Object def)
    {
        if (! this.data.containsKey(name))
        {
            return def;
        }
        return this.data.get(name);
    }

    public String getString(final String name)
    {
        return this.getString(name, null);
    }

    public String getString(final String name, final String def)
    {
        if (! this.data.containsKey(name))
        {
            return def;
        }
        final Object obj = this.data.get(name);
        return (obj == null) ? null : obj.toString();
    }

    public UUID getUUID(final String name)
    {
        return this.getUUID(name, null);
    }

    public UUID getUUID(final String name, final UUID def)
    {
        if (! this.data.containsKey(name))
        {
            return def;
        }
        return UUID.fromString(this.data.get(name).toString());
    }

    public float getFloat(final String name)
    {
        return this.getFloat(name, 0f);
    }

    public float getFloat(final String name, final float def)
    {
        if (! this.data.containsKey(name))
        {
            return def;
        }
        return ((Number) this.data.get(name)).floatValue();
    }

    public int getInt(final String name)
    {
        return this.getInt(name, 0);
    }

    public int getInt(final String name, final int def)
    {
        if (! this.data.containsKey(name))
        {
            return def;
        }
        return ((Number) this.data.get(name)).intValue();
    }

    public long getLong(final String name)
    {
        return this.getLong(name, 0);
    }

    public long getLong(final String name, final long def)
    {
        if (! this.data.containsKey(name))
        {
            return def;
        }
        return ((Number) this.data.get(name)).longValue();
    }

    public boolean getBoolean(final String name)
    {
        return this.getBoolean(name, false);
    }

    public boolean getBoolean(final String name, final boolean def)
    {
        if (! this.data.containsKey(name))
        {
            return def;
        }
        final Object obj = this.data.get(name);
        return (obj instanceof Boolean) ? (Boolean) obj : Boolean.parseBoolean(obj.toString());
    }

    public byte getByte(final String name)
    {
        return this.getByte(name, (byte) 0);
    }

    public byte getByte(final String name, final byte def)
    {
        if (! this.data.containsKey(name))
        {
            return def;
        }
        return ((Number) this.data.get(name)).byteValue();
    }

    public short getShort(final String name)
    {
        return this.getShort(name, (short) 0);
    }

    public short getShort(final String name, final short def)
    {
        if (! this.data.containsKey(name))
        {
            return def;
        }
        return ((Number) this.data.get(name)).shortValue();
    }

    public <T extends Enum<T>> T getEnum(final String name, final T def)
    {
        return this.getEnum(name, def.getDeclaringClass(), def);
    }

    public <T extends Enum<T>> T getEnum(final String name, final Class<T> enumClass)
    {
        return this.getEnum(name, enumClass, null);
    }

    public <T extends Enum<T>> T getEnum(final String name, final Class<T> enumClass, final T def)
    {
        final String constName = this.getString(name, (def == null) ? null : def.name());
        if (constName == null)
        {
            return def;
        }
        final T[] consts = enumClass.getEnumConstants();
        for (final T enumConst : consts)
        {
            if (enumConst.name().equals(constName))
            {
                return enumConst;
            }
        }
        return def;
    }

    public EnchantmentType getEnchantment(final String name)
    {
        return this.getEnchantment(name, null);
    }

    public EnchantmentType getEnchantment(final String name, final EnchantmentType def)
    {
        final String enchName = this.getString(name, null);
        if (enchName == null)
        {
            return null;
        }
        return EnchantmentType.matchEnchantment(enchName);
    }

    public ILocation getLoc(final String name)
    {
        return this.getLoc(name, null);
    }

    public ILocation getLoc(final String name, final ILocation def)
    {
        if (! this.data.containsKey(name))
        {
            return def;
        }
        @SuppressWarnings("unchecked")
        final DeserializationWorker dw = DeserializationWorker.start((Map<String, Object>) this.data.get(name));
        final String worldName = dw.getString("world");
        return new ImmutableLocation(dw.getInt("x"), dw.getInt("y"), dw.getInt("z"), dw.getFloat("yaw"), dw.getFloat("pitch"), (worldName == null) ? null : Diorite.getWorldsManager().getWorld(worldName));
    }

    public List<String> getStringListSafe(final String name)
    {
        return this.getStringList(name, new ArrayList<String>(1));
    }

    public List<String> getStringList(final String name)
    {
        return this.getStringList(name, null);
    }

    public List<String> getStringList(final String name, final List<String> def)
    {
        if (! this.data.containsKey(name))
        {
            return def;
        }
        return new ArrayList<>((Collection<String>) this.data.get(name));
    }

    public <T> Set<T> getHashSet(final String name)
    {
        return this.getHashSet(name, null);
    }

    @SuppressWarnings("unchecked")
    public <T> Set<T> getHashSet(final String name, final Set<T> def)
    {
        if (! this.data.containsKey(name))
        {
            return def;
        }
        return new HashSet<>((Collection<T>) this.data.get(name));
    }

    public <T> List<T> getList(final String name)
    {
        return this.getList(name, null);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getList(final String name, final List<T> def)
    {
        if (! this.data.containsKey(name))
        {
            return def;
        }
        return new ArrayList<>((Collection<T>) this.data.get(name));
    }

    public Map<String, Object> getSection(final String name)
    {
        return this.getSection(name, null);
    }

    public Map<String, Object> getSection(final String name, final Map<String, Object> def)
    {
        final Object obj = this.data.get(name);
        if (obj == null)
        {
            return def;
        }
        if (obj instanceof ConfigurationSection)
        {
            return ((ConfigurationSection) obj).getValues(true);
        }
        return (Map<String, Object>) obj;
    }

    public Map<String, Object> getMap()
    {
        return this.data;
    }

    public static DeserializationWorker startUnsafe(final Map data)
    {
        try
        {
            data.remove("==");
        } catch (final Exception ignored)
        {
        }
        return new DeserializationWorker(data);
    }

    public static DeserializationWorker start(final Map<String, Object> data)
    {
        try
        {
            data.remove("==");
        } catch (final Exception ignored)
        {
        }
        return new DeserializationWorker(data);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("data", this.data).toString();
    }
}
