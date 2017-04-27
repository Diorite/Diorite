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

package org.diorite.impl.entity.diorite;

import org.diorite.ImmutableLocation;
import org.diorite.Location;
import org.diorite.impl.entity.IEntity;
import org.diorite.impl.entity.metadata.EntityMetadata;
import org.diorite.impl.entity.metadata.entry.EntityMetadataByteEntry;
import org.diorite.impl.entity.metadata.entry.EntityMetadataIntEntry;
import org.diorite.impl.entity.metadata.entry.EntityMetadataStringEntry;

import javax.annotation.Nullable;

public class EntityImpl implements IEntity
{
    private int id;
    protected EntityMetadata metadata;

    private Location location;

    //TODO: core and uuid arg
    EntityImpl(final int id, final ImmutableLocation location)
    {
        //TODO: game object

        this.id = id;
        this.location = location; //TODO: check

        //Metadata
        this.metadata = new EntityMetadata(METADATA_SIZE);
        this.metadata.store(new EntityMetadataByteEntry((byte) 0, 0));
        this.metadata.store(new EntityMetadataIntEntry((byte) 1, 0));
        this.metadata.store(new EntityMetadataStringEntry((byte) 2, ""));

        //TODO: lazy entity, metadata
    }

    @Nullable
    @Override
    public String getCustomName()
    {
        return (String) this.metadata.get((byte) 2).getValue();
    }

    @Override
    public void setCustomName(@Nullable String name)
    {
        ((EntityMetadataStringEntry)this.metadata.get((byte) 2)).setValue(name);
    }

    @Override
    public boolean isCustomNameVisible()
    {
        return false; //TODO: metadata
    }

    @Override
    public void setCustomNameVisible(boolean visible)
    {
        //TODO: metadata
    }
}
