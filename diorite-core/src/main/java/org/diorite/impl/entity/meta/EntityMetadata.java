/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

import javax.vecmath.Vector3f;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.entity.meta.entry.EntityMetadataBlockLocationEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataBooleanEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataByteEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataChatEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataFloatEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataIntEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataItemStackEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataMaterialEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataStringEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataUUIDEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataVector3FEntry;
import org.diorite.BlockLocation;
import org.diorite.chat.component.BaseComponent;
import org.diorite.inventory.item.ItemStack;
import org.diorite.material.Material;

public class EntityMetadata
{
    public static final int END_MARKER = 0xff;

    private final EntityMetadataEntry<?>[] data;

    public EntityMetadata(final int size)
    {
        this.data = new EntityMetadataEntry<?>[size];
    }

    public void add(final EntityMetadataEntry<?> entry)
    {
        this.data[entry.getIndex()] = entry;
        entry.setDirty();
    }

    public Collection<EntityMetadataEntry<?>> getOutdatedEntries()
    {
        final Collection<EntityMetadataEntry<?>> result = new HashSet<>(this.data.length);
        for (final EntityMetadataEntry<?> entry : this.data)
        {
            if ((entry != null) && entry.isDirty())
            {
                result.add(entry);
            }
        }
        return result;
    }

    /**
     * WARN: this method will mark all entires as updated again
     *
     * @return outdated entires of metadata.
     */
    public Collection<EntityMetadataEntry<?>> popOutdatedEntries()
    {
        final Collection<EntityMetadataEntry<?>> result = new HashSet<>(this.data.length);
        for (final EntityMetadataEntry<?> entry : this.data)
        {
            if ((entry != null) && entry.setClean())
            {
                result.add(entry);
            }
        }
        return result;
    }

    public Collection<EntityMetadataEntry<?>> getEntries()
    {
        final Collection<EntityMetadataEntry<?>> result = new HashSet<>(this.data.length);
        for (final EntityMetadataEntry<?> entry : this.data)
        {
            if (entry != null)
            {
                result.add(entry);
            }
        }
        return result;
    }

    /*
     * Setters
     */

    public void setByte(final byte index, final byte value)
    {
        final EntityMetadataEntry<?> entry = this.data[index];
        if (! (entry instanceof EntityMetadataByteEntry))
        {
            throw new IllegalArgumentException("Metadata type mismatch excepted byte but found: " + entry);
        }
        ((EntityMetadataByteEntry) entry).setValue(value);
        entry.setDirty();
    }

    public void setInt(final byte index, final int value)
    {
        final EntityMetadataEntry<?> entry = this.data[index];
        if (! (entry instanceof EntityMetadataIntEntry))
        {
            throw new IllegalArgumentException("Metadata type mismatch excepted int but found: " + entry);
        }
        ((EntityMetadataIntEntry) entry).setValue(value);
        entry.setDirty();
    }

    public void setFloat(final byte index, final float value)
    {
        final EntityMetadataEntry<?> entry = this.data[index];
        if (! (entry instanceof EntityMetadataFloatEntry))
        {
            throw new IllegalArgumentException("Metadata type mismatch excepted float but found: " + entry);
        }
        ((EntityMetadataFloatEntry) entry).setValue(value);
        entry.setDirty();
    }

    public void setBoolean(final byte index, final int flagIndex, final boolean bool)
    {
        final EntityMetadataEntry<?> entry = this.data[index];
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
        throw new IllegalArgumentException("Metadata type mismatch excepted byte or int but found: " + entry);
    }

    public void switchBoolean(final byte index, final int flagIndex)
    {
        final EntityMetadataEntry<?> entry = this.data[index];
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
        throw new IllegalArgumentException("Metadata type mismatch excepted byte or int but found: " + entry);
    }

    public void setBoolean(final byte index, final boolean value)
    {
        final EntityMetadataEntry<?> entry = this.data[index];
        if (! (entry instanceof EntityMetadataBooleanEntry) && ! (entry instanceof EntityMetadataByteEntry))
        {
            throw new IllegalArgumentException("Metadata type mismatch excepted boolean but found: " + entry);
        }

        if (entry instanceof EntityMetadataByteEntry)
        {
            ((EntityMetadataByteEntry) entry).setValue(value ? 1 : 0);
            entry.setDirty();
        }
        else
        {
            ((EntityMetadataBooleanEntry) entry).setValue(value);
            entry.setDirty();
        }
    }

    public void switchBoolean(final byte index)
    {
        final EntityMetadataEntry<?> entry = this.data[index];
        if (! (entry instanceof EntityMetadataBooleanEntry))
        {
            throw new IllegalArgumentException("Metadata type mismatch excepted boolean but found: " + entry);
        }
        final EntityMetadataBooleanEntry boolEntry = (EntityMetadataBooleanEntry) entry;
        boolEntry.setValue(! boolEntry.getValue());
        entry.setDirty();
    }

    public void setString(final byte index, final String value)
    {
        final EntityMetadataEntry<?> entry = this.data[index];
        if (! (entry instanceof EntityMetadataStringEntry))
        {
            throw new IllegalArgumentException("Metadata type mismatch excepted String but found: " + entry);
        }
        //noinspection OverlyStrongTypeCast instanceof will block it anyway.
        ((EntityMetadataStringEntry) entry).setData(value);
        entry.setDirty();
    }

    public void setChatComponent(final byte index, final BaseComponent value)
    {
        final EntityMetadataEntry<?> entry = this.data[index];
        if (! (entry instanceof EntityMetadataChatEntry))
        {
            throw new IllegalArgumentException("Metadata type mismatch excepted Chat but found: " + entry);
        }
        //noinspection OverlyStrongTypeCast instanceof will block it anyway.
        ((EntityMetadataChatEntry) entry).setData(value);
        entry.setDirty();
    }

    public void setItemStack(final byte index, final ItemStack value)
    {
        final EntityMetadataEntry<?> entry = this.data[index];
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
        final EntityMetadataEntry<?> entry = this.data[index];
        if (! (entry instanceof EntityMetadataBlockLocationEntry))
        {
            throw new IllegalArgumentException("Metadata type mismatch excepted BlockLocation but found: " + entry);
        }
        //noinspection OverlyStrongTypeCast instanceof will block it anyway.
        ((EntityMetadataBlockLocationEntry) entry).setData(value);
        entry.setDirty();
    }

    public void setMaterial(final byte index, final Material value)
    {
        final EntityMetadataEntry<?> entry = this.data[index];
        if (! (entry instanceof EntityMetadataMaterialEntry))
        {
            throw new IllegalArgumentException("Metadata type mismatch excepted Material but found: " + entry);
        }
        //noinspection OverlyStrongTypeCast instanceof will block it anyway.
        ((EntityMetadataMaterialEntry) entry).setData(value);
        entry.setDirty();
    }

    public void setVector3F(final byte index, final Vector3f value)
    {
        final EntityMetadataEntry<?> entry = this.data[index];
        if (! (entry instanceof EntityMetadataVector3FEntry))
        {
            throw new IllegalArgumentException("Metadata type mismatch excepted Vector3F but found: " + entry);
        }
        //noinspection OverlyStrongTypeCast instanceof will block it anyway.
        ((EntityMetadataVector3FEntry) entry).setData(value);
        entry.setDirty();
    }

    public void setUUID(final byte index, final UUID value)
    {
        final EntityMetadataEntry<?> entry = this.data[index];
        if (! (entry instanceof EntityMetadataUUIDEntry))
        {
            throw new IllegalArgumentException("Metadata type mismatch excepted UUID but found: " + entry);
        }
        //noinspection OverlyStrongTypeCast instanceof will block it anyway.
        ((EntityMetadataUUIDEntry) entry).setData(value);
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

    public void setByte(final int index, final boolean value) { this.setByte((byte) index, value ? 1 : 0);}

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

    public void setBoolean(final int index, final boolean bool)
    {
        this.setBoolean((byte) index, bool);
    }

    public void switchBoolean(final int index)
    {
        this.switchBoolean((byte) index);
    }

    public void setBoolean(final int index, final int flagIndex, final boolean bool)
    {
        this.setBoolean((byte) index, flagIndex, bool);
    }

    public void switchBoolean(final int index, final int flagIndex)
    {
        this.switchBoolean((byte) index, flagIndex);
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

    public void setUUID(final int index, final UUID value)
    {
        this.setUUID((byte) index, value);
    }

    public void setChatComponent(final int index, final BaseComponent value)
    {
        this.setChatComponent((byte) index, value);
    }

    public void setMaterial(final int index, final Material value)
    {
        this.setMaterial((byte) index, value);
    }

    public void setVector3F(final int index, final Vector3f value)
    {
        this.setVector3F((byte) index, value);
    }

    /*
     * Getters
     */

    private EntityMetadataEntry<?> get(final byte index)
    {
        return this.data[index];
    }

    public byte getByte(final byte index)
    {
        final EntityMetadataEntry<?> entry = this.data[index];
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

    public int getInt(final byte index)
    {
        final EntityMetadataEntry<?> entry = this.data[index];
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
        final EntityMetadataEntry<?> entry = this.data[index];
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
        final EntityMetadataEntry<?> entry = this.data[index];
        if (entry == null)
        {
            return false;
        }
        if (! (entry instanceof EntityMetadataBooleanEntry))
        {
            throw new IllegalArgumentException("Metadata type mismatch excepted boolean but found: " + entry);
        }

        if (entry instanceof EntityMetadataByteEntry)
        {
            return ((EntityMetadataByteEntry) entry).getValue() == 1;
        }

        return ((EntityMetadataBooleanEntry) entry).getValue();
    }

    public boolean getBoolean(final byte index, final int flagIndex)
    {
        final EntityMetadataEntry<?> entry = this.data[index];
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
        throw new IllegalArgumentException("Metadata type mismatch excepted byte or int but found: " + entry);
    }

    public int getNumberPart(final byte index, final int mask)
    {
        final EntityMetadataEntry<?> entry = this.data[index];
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
        throw new IllegalArgumentException("Metadata type mismatch excepted byte, short or int but found: " + entry);
    }

    public String getString(final byte index)
    {
        final EntityMetadataEntry<?> entry = this.data[index];
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
        final EntityMetadataEntry<?> entry = this.data[index];
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
        final EntityMetadataEntry<?> entry = this.data[index];
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

    public Material getMaterial(final byte index)
    {
        final EntityMetadataEntry<?> entry = this.data[index];
        if (entry == null)
        {
            return null;
        }
        if (! (entry instanceof EntityMetadataMaterialEntry))
        {
            throw new IllegalArgumentException("Metadata type mismatch excepted Material but found: " + entry);
        }
        //noinspection OverlyStrongTypeCast instanceof will block it anyway.
        return ((EntityMetadataMaterialEntry) entry).getData();
    }

    public UUID getUUID(final byte index)
    {
        final EntityMetadataEntry<?> entry = this.data[index];
        if (entry == null)
        {
            return null;
        }
        if (! (entry instanceof EntityMetadataUUIDEntry))
        {
            throw new IllegalArgumentException("Metadata type mismatch excepted UUID but found: " + entry);
        }
        //noinspection OverlyStrongTypeCast instanceof will block it anyway.
        return ((EntityMetadataUUIDEntry) entry).getData();
    }

    public BaseComponent getChatComponent(final byte index)
    {
        final EntityMetadataEntry<?> entry = this.data[index];
        if (entry == null)
        {
            return null;
        }
        if (! (entry instanceof EntityMetadataChatEntry))
        {
            throw new IllegalArgumentException("Metadata type mismatch excepted Chat but found: " + entry);
        }
        //noinspection OverlyStrongTypeCast instanceof will block it anyway.
        return ((EntityMetadataChatEntry) entry).getData();
    }

    public Vector3f getVector3F(final byte index)
    {
        final EntityMetadataEntry<?> entry = this.data[index];
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

    public boolean getBoolean(final int index, int flagIndex)
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

    public UUID getUUID(final int index)
    {
        return this.getUUID((byte) index);
    }

    public BaseComponent getChatComponent(final int index)
    {
        return this.getChatComponent((byte) index);
    }

    public Material getMaterial(final int index)
    {
        return this.getMaterial((byte) index);
    }

    public Vector3f getVector3F(final int index)
    {
        return this.getVector3F((byte) index);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("data", this.data).toString();
    }
}
