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

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
import org.diorite.inventory.item.ItemStack;
import org.diorite.utils.SimpleEnum;
import org.diorite.utils.math.geometry.Vector3F;

import gnu.trove.map.hash.TByteObjectHashMap;

public class EntityMetadata
{
    private final TByteObjectHashMap<EntityMetadataEntry<?>> data;

    public EntityMetadata()
    {
        this.data = new TByteObjectHashMap<>(9, SimpleEnum.SMALL_LOAD_FACTOR, (byte) - 1);
    }

    public void add(final EntityMetadataEntry<?> entry)
    {
        final EntityMetadataEntry<?> old = this.data.put(entry.getIndex(), entry);
        if (old != null)
        {
            entry.setDirty();
        }
    }

    public Collection<EntityMetadataEntry<?>> getOutdatedEntries()
    {
        return this.data.valueCollection().stream().filter(EntityMetadataEntry::isDirty).collect(Collectors.toSet());
    }

    /**
     * WARN: this method will mark all entires as updated again
     *
     * @return outdated entires of metadata.
     */
    public Collection<EntityMetadataEntry<?>> popOutdatedEntries()
    {
        final Collection<EntityMetadataEntry<?>> result = new HashSet<>(3);
        this.data.valueCollection().stream().filter(EntityMetadataEntry::isDirty).forEach(e -> {
            e.setClean();
            result.add(e);
        });
        return result;
    }

    public Collection<EntityMetadataEntry<?>> getEntries()
    {
        return this.data.valueCollection().stream().collect(Collectors.toSet());
    }

    /*
     * Setters
     */

    public void setByte(final byte index, final byte value)
    {
        final EntityMetadataEntry<?> entry = this.data.get(index);
        if (! (entry instanceof EntityMetadataByteEntry))
        {
            throw new IllegalArgumentException("Metadata type mismatch excepted byte but found: " + entry);
        }
        ((EntityMetadataByteEntry) entry).setValue(value);
        entry.setDirty();
    }

    public void setShort(final byte index, final short value)
    {
        final EntityMetadataEntry<?> entry = this.data.get(index);
        if (! (entry instanceof EntityMetadataShortEntry))
        {
            throw new IllegalArgumentException("Metadata type mismatch excepted short but found: " + entry);
        }
        ((EntityMetadataShortEntry) entry).setValue(value);
        entry.setDirty();
    }

    public void setInt(final byte index, final int value)
    {
        final EntityMetadataEntry<?> entry = this.data.get(index);
        if (! (entry instanceof EntityMetadataIntEntry))
        {
            throw new IllegalArgumentException("Metadata type mismatch excepted int but found: " + entry);
        }
        ((EntityMetadataIntEntry) entry).setValue(value);
        entry.setDirty();
    }

    public void setFloat(final byte index, final float value)
    {
        final EntityMetadataEntry<?> entry = this.data.get(index);
        if (! (entry instanceof EntityMetadataFloatEntry))
        {
            throw new IllegalArgumentException("Metadata type mismatch excepted float but found: " + entry);
        }
        ((EntityMetadataFloatEntry) entry).setValue(value);
        entry.setDirty();
    }

    public void setBoolean(final byte index, final int flagIndex, final boolean bool)
    {
        final EntityMetadataEntry<?> entry = this.data.get(index);
        if (entry instanceof EntityMetadataByteEntry)
        {
            final EntityMetadataByteEntry e = (EntityMetadataByteEntry) entry;
            if (bool)
            {
                e.setValue((byte) (e.getValue() | (1 << flagIndex)));
            }
            else
            {
                e.setValue((byte) (e.getValue() & ~ (1 << flagIndex)));
            }
            e.setDirty();
            return;
        }
        if (entry instanceof EntityMetadataIntEntry)
        {
            final EntityMetadataIntEntry e = (EntityMetadataIntEntry) entry;
            if (bool)
            {
                e.setValue((byte) (e.getValue() | (1 << flagIndex)));
            }
            else
            {
                e.setValue((byte) (e.getValue() & ~ (1 << flagIndex)));
            }
            e.setDirty();
            return;
        }
        if (entry instanceof EntityMetadataShortEntry)
        {
            final EntityMetadataShortEntry e = (EntityMetadataShortEntry) entry;
            if (bool)
            {
                e.setValue((byte) (e.getValue() | (1 << flagIndex)));
            }
            else
            {
                e.setValue((byte) (e.getValue() & ~ (1 << flagIndex)));
            }
            e.setDirty();
            return;
        }
        throw new IllegalArgumentException("Metadata type mismatch excepted byte, short or int but found: " + entry);
    }

    public void swichBoolean(final byte index, final int flagIndex)
    {
        final EntityMetadataEntry<?> entry = this.data.get(index);
        if (entry instanceof EntityMetadataByteEntry)
        {
            final EntityMetadataByteEntry e = (EntityMetadataByteEntry) entry;
            e.setValue((byte) (e.getValue() ^ (1 << flagIndex)));
            e.setDirty();
            return;
        }
        if (entry instanceof EntityMetadataIntEntry)
        {
            final EntityMetadataIntEntry e = (EntityMetadataIntEntry) entry;
            e.setValue((byte) (e.getValue() ^ (1 << flagIndex)));
            e.setDirty();
            return;
        }
        if (entry instanceof EntityMetadataShortEntry)
        {
            final EntityMetadataShortEntry e = (EntityMetadataShortEntry) entry;
            e.setValue((byte) (e.getValue() ^ (1 << flagIndex)));
            e.setDirty();
            return;
        }
        throw new IllegalArgumentException("Metadata type mismatch excepted byte, short or int but found: " + entry);
    }

    public void setString(final byte index, final String value)
    {
        final EntityMetadataEntry<?> entry = this.data.get(index);
        if (! (entry instanceof EntityMetadataStringEntry))
        {
            throw new IllegalArgumentException("Metadata type mismatch excepted String but found: " + entry);
        }
        //noinspection OverlyStrongTypeCast instanceof will block it anyway.
        ((EntityMetadataStringEntry) entry).setData(value);
        entry.setDirty();
    }

    public void setItemStack(final byte index, final ItemStack value)
    {
        final EntityMetadataEntry<?> entry = this.data.get(index);
        if (! (entry instanceof EntityMetadataItemStackEntry))
        {
            throw new IllegalArgumentException("Metadata type mismatch excepted ItemStack but found: " + entry);
        }
        //noinspection OverlyStrongTypeCast instanceof will block it anyway.
        ((EntityMetadataItemStackEntry) entry).setData(value);
        entry.setDirty();
    }

    public void setBlockLocation(final byte index, final BlockLocation value)
    {
        final EntityMetadataEntry<?> entry = this.data.get(index);
        if (! (entry instanceof EntityMetadataBlockLocationEntry))
        {
            throw new IllegalArgumentException("Metadata type mismatch excepted BlockLocation but found: " + entry);
        }
        //noinspection OverlyStrongTypeCast instanceof will block it anyway.
        ((EntityMetadataBlockLocationEntry) entry).setData(value);
        entry.setDirty();
    }

    public void setVector3F(final byte index, final Vector3F value)
    {
        final EntityMetadataEntry<?> entry = this.data.get(index);
        if (! (entry instanceof EntityMetadataVector3FEntry))
        {
            throw new IllegalArgumentException("Metadata type mismatch excepted Vector3F but found: " + entry);
        }
        //noinspection OverlyStrongTypeCast instanceof will block it anyway.
        ((EntityMetadataVector3FEntry) entry).setData(value);
        entry.setDirty();
    }

    /*
     * Input friendly versions
     */

    public void setByte(final int index, final int value)
    {
        this.setByte((byte) index, (byte) value);
    }

    public void setByte(final int index, final byte value)
    {
        this.setByte((byte) index, value);
    }

    public void setShort(final int index, final int value)
    {
        this.setShort((byte) index, (short) value);
    }

    public void setShort(final int index, final short value)
    {
        this.setShort((byte) index, value);
    }

    public void setInt(final int index, final int value)
    {
        this.setInt((byte) index, value);
    }

    public void setFloat(final int index, final double value)
    {
        this.setFloat((byte) index, (float) value);
    }

    public void setFloat(final int index, final float value)
    {
        this.setFloat((byte) index, value);
    }

    public void setBoolean(final int index, final int flagIndex, final boolean bool)
    {
        this.setBoolean((byte) index, flagIndex, bool);
    }

    public void swichBoolean(final int index, final int flagIndex)
    {
        this.swichBoolean((byte) index, flagIndex);
    }

    public void setString(final int index, final String value)
    {
        this.setString((byte) index, value);
    }

    public void setItemStack(final int index, final ItemStack value)
    {
        this.setItemStack((byte) index, value);
    }

    public void setBlockLocation(final int index, final BlockLocation value)
    {
        this.setBlockLocation((byte) index, value);
    }

    public void setVector3F(final int index, final Vector3F value)
    {
        this.setVector3F((byte) index, value);
    }

    /*
     * Getters
     */

    private EntityMetadataEntry<?> get(final byte index)
    {
        return this.data.get(index);
    }

    public byte getByte(final byte index)
    {
        final EntityMetadataEntry<?> entry = this.data.get(index);
        if (entry == null)
        {
            return 0;
        }
        if (! (entry instanceof EntityMetadataByteEntry))
        {
            throw new IllegalArgumentException("Metadata type mismatch excepted byte but found: " + entry);
        }
        return ((EntityMetadataByteEntry) entry).getValue();
    }

    public short getShort(final byte index)
    {
        final EntityMetadataEntry<?> entry = this.data.get(index);
        if (entry == null)
        {
            return 0;
        }
        if (! (entry instanceof EntityMetadataShortEntry))
        {
            throw new IllegalArgumentException("Metadata type mismatch excepted short but found: " + entry);
        }
        return ((EntityMetadataShortEntry) entry).getValue();
    }

    public int getInt(final byte index)
    {
        final EntityMetadataEntry<?> entry = this.data.get(index);
        if (entry == null)
        {
            return 0;
        }
        if (! (entry instanceof EntityMetadataIntEntry))
        {
            throw new IllegalArgumentException("Metadata type mismatch excepted int but found: " + entry);
        }
        return ((EntityMetadataIntEntry) entry).getValue();
    }

    public float getFloat(final byte index)
    {
        final EntityMetadataEntry<?> entry = this.data.get(index);
        if (entry == null)
        {
            return 0;
        }
        if (! (entry instanceof EntityMetadataFloatEntry))
        {
            throw new IllegalArgumentException("Metadata type mismatch excepted float but found: " + entry);
        }
        return ((EntityMetadataFloatEntry) entry).getValue();
    }

    public boolean getBoolean(final byte index)
    {
        return this.getByte(index) != 0;
    }

    public boolean getBoolean(final byte index, final int flagIndex)
    {
        final EntityMetadataEntry<?> entry = this.data.get(index);
        if (entry == null)
        {
            return false;
        }
        if (entry instanceof EntityMetadataByteEntry)
        {
            return (((EntityMetadataByteEntry) entry).getValue() & (1 << flagIndex)) != 0;
        }
        if (entry instanceof EntityMetadataIntEntry)
        {
            return (((EntityMetadataIntEntry) entry).getValue() & (1 << flagIndex)) != 0;
        }
        if (entry instanceof EntityMetadataShortEntry)
        {
            return (((EntityMetadataShortEntry) entry).getValue() & (1 << flagIndex)) != 0;
        }
        throw new IllegalArgumentException("Metadata type mismatch excepted byte, short or int but found: " + entry);
    }

    public int getNumberPart(final byte index, final int mask)
    {
        final EntityMetadataEntry<?> entry = this.data.get(index);
        if (entry == null)
        {
            return 0;
        }
        if (entry instanceof EntityMetadataIntEntry)
        {
            return ((EntityMetadataIntEntry) entry).getValue() & mask;
        }
        if (entry instanceof EntityMetadataByteEntry)
        {
            return ((EntityMetadataByteEntry) entry).getValue() & mask;
        }
        if (entry instanceof EntityMetadataShortEntry)
        {
            return ((EntityMetadataShortEntry) entry).getValue() & mask;
        }
        throw new IllegalArgumentException("Metadata type mismatch excepted byte, short or int but found: " + entry);
    }

    public String getString(final byte index)
    {
        final EntityMetadataEntry<?> entry = this.data.get(index);
        if (entry == null)
        {
            return null;
        }
        if (! (entry instanceof EntityMetadataStringEntry))
        {
            throw new IllegalArgumentException("Metadata type mismatch excepted String but found: " + entry);
        }
        //noinspection OverlyStrongTypeCast instanceof will block it anyway.
        return ((EntityMetadataStringEntry) entry).getData();
    }

    public ItemStack getItemStack(final byte index)
    {
        final EntityMetadataEntry<?> entry = this.data.get(index);
        if (entry == null)
        {
            return null;
        }
        if (! (entry instanceof EntityMetadataItemStackEntry))
        {
            throw new IllegalArgumentException("Metadata type mismatch excepted ItemStack but found: " + entry);
        }
        //noinspection OverlyStrongTypeCast instanceof will block it anyway.
        return ((EntityMetadataItemStackEntry) entry).getData();
    }

    public BlockLocation getBlockLocation(final byte index)
    {
        final EntityMetadataEntry<?> entry = this.data.get(index);
        if (entry == null)
        {
            return null;
        }
        if (! (entry instanceof EntityMetadataBlockLocationEntry))
        {
            throw new IllegalArgumentException("Metadata type mismatch excepted BlockLocation but found: " + entry);
        }
        //noinspection OverlyStrongTypeCast instanceof will block it anyway.
        return ((EntityMetadataBlockLocationEntry) entry).getData();
    }

    public Vector3F getVector3F(final byte index)
    {
        final EntityMetadataEntry<?> entry = this.data.get(index);
        if (entry == null)
        {
            return null;
        }
        if (! (entry instanceof EntityMetadataVector3FEntry))
        {
            throw new IllegalArgumentException("Metadata type mismatch excepted Vector3F but found: " + entry);
        }
        //noinspection OverlyStrongTypeCast instanceof will block it anyway.
        return ((EntityMetadataVector3FEntry) entry).getData();
    }

    /*
     * Input friendly versions
     */

    public byte getByte(final int index)
    {
        return this.getByte((byte) index);
    }

    public short getShort(final int index)
    {
        return this.getShort((byte) index);
    }

    public int getInt(final int index)
    {
        return this.getInt((byte) index);
    }

    public float getFloat(final int index)
    {
        return this.getFloat((byte) index);
    }

    public boolean getBoolean(final int index)
    {
        return this.getBoolean((byte) index);
    }

    public boolean getBoolean(final int index, final int flagIndex)
    {
        return this.getBoolean((byte) index, flagIndex);
    }

    public int getNumberPart(final int index, final int mask)
    {
        return this.getNumberPart((byte) index, mask);
    }

    public String getString(final int index)
    {
        return this.getString((byte) index);
    }

    public ItemStack getItemStack(final int index)
    {
        return this.getItemStack((byte) index);
    }

    public BlockLocation getBlockLocation(final int index)
    {
        return this.getBlockLocation((byte) index);
    }

    public Vector3F getVector3F(final int index)
    {
        return this.getVector3F((byte) index);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("data", this.data).toString();
    }
}
