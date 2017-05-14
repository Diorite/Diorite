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
import org.diorite.core.metadata.entity.entry.EntityMetadataBooleanValue;
import org.diorite.impl.entity.IEntity;
import org.diorite.core.metadata.entity.EntityMetadata;
import org.diorite.core.metadata.entity.entry.EntityMetadataByteValue;
import org.diorite.core.metadata.entity.entry.EntityMetadataIntegerValue;
import org.diorite.core.metadata.entity.entry.EntityMetadataStringValue;

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
        this.metadata.store(new EntityMetadataByteValue(METADATA_ENTITY_BASIC_FLAGS, ENTITY_BASIC_FLAGS));
        this.metadata.store(new EntityMetadataIntegerValue(METADATA_ENTITY_AIR_LEVEL, ENTITY_AIR_LEVEL));
        this.metadata.store(new EntityMetadataStringValue(METADATA_ENTITY_NAME, ENTITY_NAME));
        this.metadata.store(new EntityMetadataBooleanValue(METADATA_ENTITY_SOUND, ENTITY_SOUND));
        this.metadata.store(new EntityMetadataBooleanValue(METADATA_ENTITY_NAME_TAG_VISIBILITY, ENTITY_NAME_TAG_VISIBILITY));
        this.metadata.store(new EntityMetadataBooleanValue(METADATA_ENTITY_GRAVITY, ENTITY_GRAVITY));

        //TODO: lazy entity
    }

    @Nullable
    @Override
    public String getCustomName()
    {
        return this.metadata.getString(METADATA_ENTITY_NAME);
    }

    @Override
    public void setCustomName(@Nullable String name)
    {
        this.metadata.setString(METADATA_ENTITY_NAME, name);
    }

    @Override
    public boolean isCustomNameVisible()
    {
        return this.metadata.getBoolean(METADATA_ENTITY_NAME_TAG_VISIBILITY);
    }

    @Override
    public void setCustomNameVisible(boolean visibility)
    {
        this.metadata.setBoolean(METADATA_ENTITY_NAME_TAG_VISIBILITY, visibility);
    }
}
