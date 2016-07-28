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
import org.diorite.impl.entity.IHorse;
import org.diorite.impl.entity.meta.EntityMetadata;
import org.diorite.impl.entity.meta.entry.EntityMetadataIntEntry;
import org.diorite.ImmutableLocation;
import org.diorite.entity.EntityType;

class HorseImpl extends AnimalEntityImpl implements IHorse
{
    private static final int MARKINGS       = 256;
    private static final int MARKINGS_SHIFT = MARKINGS / 32;

    HorseImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid, core, id, location);
        this.setBoundingBox(BASE_SIZE.create(this));
    }

    @Override
    protected void createMetadata()
    {
        this.metadata = new EntityMetadata(IHorse.META_KEYS);
    }

    @Override
    public void initMetadata()
    {
        super.initMetadata();
        this.metadata.add(new EntityMetadataIntEntry(META_KEY_HORSE_TYPE, HorseType.HORSE.ordinal()));
        this.metadata.add(new EntityMetadataIntEntry(META_KEY_HORSE_VARIANT, HorseColor.WHITE.ordinal() + (HorseMarkings.NONE.ordinal() << MARKINGS_SHIFT)));
    }

    @Override
    public EntityType getType()
    {
        return EntityType.HORSE;
    }

    @Override
    public HorseType getHorseType()
    {
        return HorseType.values()[this.metadata.getInt(META_KEY_HORSE_TYPE)];
    }

    @Override
    public void setHorseType(final HorseType type)
    {
        this.metadata.setInt(META_KEY_HORSE_TYPE, type.ordinal());
    }

    @Override
    public HorseColor getHorseColor()
    {
        return HorseColor.values()[this.getVariant() % MARKINGS];
    }

    @Override
    public void setHorseColor(final HorseColor color)
    {
        this.setVariant(color.ordinal() + (this.getHorseMarkings().ordinal() << MARKINGS_SHIFT));
    }

    @Override
    public HorseMarkings getHorseMarkings()
    {
        final int variant = this.getVariant();
        final int color = variant % MARKINGS;
        return HorseMarkings.values()[(variant % (MARKINGS - 1)) - color];
    }

    @Override
    public void setHorseMarkings(final HorseMarkings markings)
    {
        this.setVariant(this.getHorseColor().ordinal() + (markings.ordinal() << MARKINGS_SHIFT));
    }

    @Override
    public int getVariant()
    {
        return this.metadata.getInt(META_KEY_HORSE_VARIANT);
    }

    @Override
    public void setVariant(final int variant)
    {
        this.metadata.setInt(META_KEY_HORSE_VARIANT, variant);
    }
}

