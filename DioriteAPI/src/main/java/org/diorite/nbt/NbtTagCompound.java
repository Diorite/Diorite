package org.diorite.nbt;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.collect.ImmutableMap;

public class NbtTagCompound extends NbtAbstractTag<NbtTagCompound>
{
    protected Map<String, NbtAbstractTag<?>> tags;

    public NbtTagCompound()
    {
        this.tags = new ConcurrentHashMap<>(5, .75f, 8);
    }

    public NbtTagCompound(final Map<String, NbtAbstractTag<?>> tags)
    {
        this.tags = new ConcurrentHashMap<>(tags);
    }

    public NbtTagCompound(final String name)
    {
        super(name);
        this.tags = new ConcurrentHashMap<>(5, .75f, 8);
    }

    public NbtTagCompound(final String name, final Map<String, NbtAbstractTag<?>> tags)
    {
        super(name);
        this.tags = new ConcurrentHashMap<>(tags);
    }

    public NbtTagCompound(final String name, final NbtTagCompound parent, final Map<String, NbtAbstractTag<?>> tags)
    {
        super(name, parent);
        this.tags = new ConcurrentHashMap<>(tags);
    }

    @Override
    public NbtAbstractTag<NbtTagCompound> read(final NbtInputStream inputStream, final boolean hasName) throws IOException
    {
        super.read(inputStream, hasName);
        final Map<String, NbtAbstractTag<?>> temp = new HashMap<>(10);
        do
        {
            final byte type = inputStream.readByte();
            final NbtTagType tagType = NbtTagType.valueOf(type);
            if (tagType == null)
            {
                throw new TagNotFoundException("Could not find a tag for type ID " + type + ".");
            }
            if (tagType == NbtTagType.END)
            {
                break;
            }
            final NbtAbstractTag<?> tag = inputStream.readTag(tagType, false);
            temp.put(tag.getName(), tag);
        } while (true);
        this.tags = new ConcurrentHashMap<>(temp);
        return this;
    }

    @Override
    public NbtTagCompound write(final NbtOutputStream outputStream, final boolean hasName) throws IOException
    {
        super.write(outputStream, hasName);
        for (final Map.Entry<String, NbtAbstractTag<?>> tagEntry : this.tags.entrySet())
        {
            tagEntry.getValue().write(outputStream, true);
        }
        outputStream.writeByte(NbtTagType.END.getTypeID());
        return this;
    }

    @Override
    public NbtTagType getType()
    {
        return NbtTagType.COMPOUND;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("tags", this.tags.keySet()).toString();
    }

    @SuppressWarnings("unchecked")
    public <T extends NbtAbstractTag<?>> T getTag(final String name)
    {
        Validate.notNull(name, "name can't be null");
        return (T) this.tags.get(name);
    }

    @SuppressWarnings({"unchecked", "UnusedParameters"})
    public <T extends NbtAbstractTag<?>> T getTag(final String name, final Class<T> tagClass)
    {
        return this.<T>getTag(name);
    }

    @SuppressWarnings("unchecked")
    public <T extends NbtAbstractTag<?>> Optional<T> getTagOpt(final String name)
    {
        Validate.notNull(name, "name can't be null");
        return Optional.ofNullable((T) this.tags.get(name));
    }

    @SuppressWarnings({"unchecked", "UnusedParameters"})
    public <T extends NbtAbstractTag<?>> Optional<T> getTagOpt(final String name, final Class<T> tagClass)
    {
        return this.<T>getTagOpt(name);
    }

    public NbtTagCompound getCompound(final String name)
    {
        return this.getTag(name, NbtTagCompound.class);
    }

    public int getInt(final String name)
    {
        return this.getTag(name, NbtTagInt.class).getValue();
    }

    public short getShort(final String name)
    {
        return this.getTag(name, NbtTagShort.class).getValue();
    }

    public byte getByte(final String name)
    {
        return this.getTag(name, NbtTagByte.class).getValue();
    }

    public long getLong(final String name)
    {
        return this.getTag(name, NbtTagLong.class).getValue();
    }

    public double getDouble(final String name)
    {
        return this.getTag(name, NbtTagDouble.class).getValue();
    }

    public float getFloat(final String name)
    {
        return this.getTag(name, NbtTagFloat.class).getValue();
    }

    public String getString(final String name)
    {
        return this.getTag(name, NbtTagString.class).getValue();
    }

    @SuppressWarnings("unchecked")
    public <T extends NbtAbstractTag<?>> List<T> getList(final String name, final Class<T> itemClass)
    {
        return this.getTag(name, NbtTagList.class).getTags(itemClass);
    }

    public int[] getIntArray(final String name)
    {
        return this.getTag(name, NbtTagIntArray.class).getValue();
    }

    public byte[] getByteArray(final String name)
    {
        return this.getTag(name, NbtTagByteArray.class).getValue();
    }

    public String[] getStringArray(final String name)
    {
        final List<NbtTagString> tags = this.getList(name, NbtTagString.class);
        final String[] array = new String[tags.size()];
        for (int i = 0; i < tags.size(); i++)
        {
            array[i] = tags.get(i).getValue();
        }
        return array;
    }

    public double[] getDoubleArray(final String name)
    {
        final List<NbtTagDouble> tags = this.getList(name, NbtTagDouble.class);
        final double[] array = new double[tags.size()];
        for (int i = 0; i < tags.size(); i++)
        {
            array[i] = tags.get(i).getValue();
        }
        return array;
    }

    public float[] getFloatArray(final String name)
    {
        final List<NbtTagFloat> tags = this.getList(name, NbtTagFloat.class);
        final float[] array = new float[tags.size()];
        for (int i = 0; i < tags.size(); i++)
        {
            array[i] = tags.get(i).getValue();
        }
        return array;
    }

    public NbtTagCompound getCompound(final String name, final NbtTagCompound def)
    {
        return this.getTagOpt(name, NbtTagCompound.class).orElse(def);
    }

    public int getInt(final String name, final int def)
    {
        return this.getTagOpt(name, NbtTagInt.class).map(NbtTagInt::getValue).orElse(def);
    }

    public short getShort(final String name, final short def)
    {
        return this.getTagOpt(name, NbtTagShort.class).map(NbtTagShort::getValue).orElse(def);
    }

    public byte getByte(final String name, final byte def)
    {
        return this.getTagOpt(name, NbtTagByte.class).map(NbtTagByte::getValue).orElse(def);
    }

    public long getLong(final String name, final long def)
    {
        return this.getTagOpt(name, NbtTagLong.class).map(NbtTagLong::getValue).orElse(def);
    }

    public double getDouble(final String name, final double def)
    {
        return this.getTagOpt(name, NbtTagDouble.class).map(NbtTagDouble::getValue).orElse(def);
    }

    public float getFloat(final String name, final float def)
    {
        return this.getTagOpt(name, NbtTagFloat.class).map(NbtTagFloat::getValue).orElse(def);
    }

    public String getString(final String name, final String def)
    {
        return this.getTagOpt(name, NbtTagString.class).map(NbtTagString::getValue).orElse(def);
    }

    @SuppressWarnings("unchecked")
    public <T extends NbtAbstractTag<?>> List<T> getList(final String name, final Class<T> itemClass, final List<T> def)
    {
        return this.getTagOpt(name, NbtTagList.class).map(list -> list.getTags(itemClass)).orElse(def);
    }

    public int[] getIntArray(final String name, final int[] def)
    {
        return this.getTagOpt(name, NbtTagIntArray.class).map(NbtTagIntArray::getValue).orElse(def);
    }

    public byte[] getByteArray(final String name, final byte[] def)
    {
        return this.getTagOpt(name, NbtTagByteArray.class).map(NbtTagByteArray::getValue).orElse(def);
    }

    public String[] getStringArray(final String name, final String[] def)
    {
        final List<NbtTagString> tags = this.getList(name, NbtTagString.class, null);
        if (tags == null)
        {
            return def;
        }
        final String[] array = new String[tags.size()];
        for (int i = 0; i < tags.size(); i++)
        {
            array[i] = tags.get(i).getValue();
        }
        return array;
    }

    public double[] getDoubleArray(final String name, final double[] def)
    {
        final List<NbtTagDouble> tags = this.getList(name, NbtTagDouble.class, null);
        if (tags == null)
        {
            return def;
        }
        final double[] array = new double[tags.size()];
        for (int i = 0; i < tags.size(); i++)
        {
            array[i] = tags.get(i).getValue();
        }
        return array;
    }

    public float[] getFloatArray(final String name, final float[] def)
    {
        final List<NbtTagFloat> tags = this.getList(name, NbtTagFloat.class, null);
        if (tags == null)
        {
            return def;
        }
        final float[] array = new float[tags.size()];
        for (int i = 0; i < tags.size(); i++)
        {
            array[i] = tags.get(i).getValue();
        }
        return array;
    }

    public Map<String, NbtAbstractTag<?>> getTags()
    {
        return (new ImmutableMap.Builder<String, NbtAbstractTag<?>>().putAll(this.tags)).build();
    }

    public void setTags(final Map<String, NbtAbstractTag<?>> tags)
    {
        this.tags = new ConcurrentHashMap<>(tags);
    }

    public void removeTag(final NbtAbstractTag<?> tag)
    {
        Validate.notNull(tag, "tag can't be null");
        this.tags.remove(tag.getName());
    }

    public void removeTag(final String tag)
    {
        Validate.notNull(tag, "tag name can't be null");
        this.tags.remove(tag);
    }

    public void setTag(final NbtAbstractTag<?> tag)
    {
        Validate.notNull(tag, "tag can't be null");

        if (this.tags.containsKey(tag))
        {
            this.tags.get(tag.getName()).setParent(null);
        }

        this.tags.put(tag.getName(), tag);
        tag.setParent(this);
    }
}