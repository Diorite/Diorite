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

package org.diorite;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.SimpleEnum;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

public class SoundCategory extends SimpleEnum.ASimpleEnum<SoundCategory>
{
    static
    {
        init(SoundCategory.class, 10);
    }

    public static final SoundCategory MASTER  = new SoundCategory("MASTER");
    public static final SoundCategory MUSIC   = new SoundCategory("MUSIC");
    public static final SoundCategory RECORDS = new SoundCategory("RECORDS");
    public static final SoundCategory WEATHER = new SoundCategory("WEATHER");
    public static final SoundCategory BLOCKS  = new SoundCategory("BLOCKS");
    public static final SoundCategory HOSTILE = new SoundCategory("HOSTILE");
    public static final SoundCategory NEUTRAL = new SoundCategory("NEUTRAL");
    public static final SoundCategory PLAYERS = new SoundCategory("PLAYERS");
    public static final SoundCategory AMBIENT = new SoundCategory("AMBIENT");
    public static final SoundCategory VOICE   = new SoundCategory("VOICE");

    public SoundCategory(final String enumName)
    {
        super(enumName);
    }

    /**
     * Register new {@link SoundCategory} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final SoundCategory element)
    {
        ASimpleEnum.register(SoundCategory.class, element);
    }

    /**
     * Get one of {@link SoundCategory} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static SoundCategory getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(SoundCategory.class, ordinal);
    }

    /**
     * Get one of SoundCategory entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static SoundCategory getByEnumName(final String name)
    {
        return getByEnumName(SoundCategory.class, name);
    }

    /**
     * @return all values in array.
     */
    public static SoundCategory[] values()
    {
        final Int2ObjectMap<SoundCategory> map = getByEnumOrdinal(SoundCategory.class);
        return map.values().toArray(new SoundCategory[map.size()]);
    }

    static
    {
        register(MASTER);
        register(MUSIC);
        register(RECORDS);
        register(WEATHER);
        register(BLOCKS);
        register(HOSTILE);
        register(NEUTRAL);
        register(PLAYERS);
        register(AMBIENT);
        register(VOICE);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).toString();
    }
}
