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

package org.diorite.impl.entity.diorite;

import java.util.UUID;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.entity.IVillager;
import org.diorite.impl.entity.meta.EntityMetadata;
import org.diorite.impl.entity.meta.entry.EntityMetadataIntEntry;
import org.diorite.ImmutableLocation;
import org.diorite.entity.EntityType;

class VillagerImpl extends CreatureEntityImpl implements IVillager
{
    VillagerImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
        this.setBoundingBox(BASE_SIZE.create(this));
    }

    @Override
    protected void createMetadata()
    {
        this.metadata = new EntityMetadata(IVillager.META_KEYS);
    }

    @Override
    public void initMetadata()
    {
        super.initMetadata();
        this.metadata.add(new EntityMetadataIntEntry(META_KEY_VILLAGER_TYPE, Profession.FARMER.ordinal())); // FARMER is 0
    }

    @Override
    public EntityType getType()
    {
        return EntityType.VILLAGER;
    }

    @Override
    public Profession getProfession()
    {
        return Profession.values()[this.metadata.getInt(META_KEY_VILLAGER_TYPE)];
    }

    @Override
    public void setProfession(final Profession profession)
    {
        this.metadata.setInt(META_KEY_VILLAGER_TYPE, profession.ordinal());
    }
}

