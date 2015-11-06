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

package org.diorite.auth;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.google.common.collect.ForwardingMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.nbt.NbtAnonymousTagContainer;
import org.diorite.nbt.NbtNamedTagContainer;
import org.diorite.nbt.NbtSerializable;
import org.diorite.nbt.NbtSerialization;
import org.diorite.nbt.NbtTag;
import org.diorite.nbt.NbtTagCompound;
import org.diorite.nbt.NbtTagList;

/**
 * Represent {@link Multimap} for Properties mapped by string key.
 *
 * @see Property
 */
public class PropertyMap extends ForwardingMultimap<String, Property> implements NbtSerializable
{
    private final Multimap<String, Property> properties;

    /**
     * Construct new property map.
     */
    public PropertyMap()
    {
        this.properties = LinkedHashMultimap.create();
    }

    /**
     * Deserialize PropertyMap from {@link NbtTagCompound}.
     *
     * @param tag data to deserialize.
     */
    public PropertyMap(final NbtNamedTagContainer tag)
    {
        this.properties = LinkedHashMultimap.create();
        for (final Entry<String, NbtTag> entry : tag.getTags().entrySet())
        {
            if (! (entry.getValue() instanceof NbtTagList))
            {
                return;
            }
            final NbtAnonymousTagContainer list = (NbtAnonymousTagContainer) entry.getValue();
            this.properties.putAll(entry.getKey(), list.getTags(NbtTagCompound.class).stream().map(t -> NbtSerialization.deserialize(Property.class, t)).collect(Collectors.toList()));
        }
    }

    @Override
    protected Multimap<String, Property> delegate()
    {
        return this.properties;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("properties", this.properties).toString();
    }

    @Override
    public NbtTagCompound serializeToNBT()
    {
        final NbtTagCompound result = new NbtTagCompound();
        for (final Entry<String, Collection<Property>> entry : this.properties.asMap().entrySet())
        {
            final Collection<Property> col = entry.getValue();
            final NbtTagList list = new NbtTagList(entry.getKey(), col.size());
            col.stream().map(NbtSerializable::serializeToNBT).forEach(list::add);
            result.addTag(list);
        }
        return result;
    }
}
