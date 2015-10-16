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

package org.diorite;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TIntObjectMap;

public class Sound extends ASimpleEnum<Sound>
{
    static
    {
        //noinspection MagicNumber
        init(Sound.class, 12);
    }

    public static final Sound RECORD_13      = new Sound("RECORD_13", "records.13");
    public static final Sound RECORD_CAT     = new Sound("RECORD_CAT", "records.cat");
    public static final Sound RECORD_BLOCKS  = new Sound("RECORD_BLOCKS", "records.blocks");
    public static final Sound RECORD_CHRIP   = new Sound("RECORD_CHRIP", "records.chrip");
    public static final Sound RECORD_FAR     = new Sound("RECORD_FAR", "records.far");
    public static final Sound RECORD_MALL    = new Sound("RECORD_MALL", "records.mall");
    public static final Sound RECORD_MELLOHI = new Sound("RECORD_MELLOHI", "records.mellohi");
    public static final Sound RECORD_STAL    = new Sound("RECORD_STAL", "records.stal");
    public static final Sound RECORD_STRAD   = new Sound("RECORD_STRAD", "records.strad");
    public static final Sound RECORD_WARD    = new Sound("RECORD_WARD", "records.ward");
    public static final Sound RECORD_11      = new Sound("RECORD_11", "records.11");
    public static final Sound RECORD_WAIT    = new Sound("RECORD_WAIT", "records.wait");

    private static final CaseInsensitiveMap<Sound> byName = new CaseInsensitiveMap<>(4, SMALL_LOAD_FACTOR);

    private final String name;

    public Sound(final String enumName, final int enumId, final String name)
    {
        super(enumName, enumId);
        this.name = name;
    }

    public Sound(final String enumName, final String name)
    {
        super(enumName);
        this.name = name;
    }

    public String getSoundName()
    {
        return this.name;
    }

    public static Sound getByName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new {@link Sound} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final Sound element)
    {
        ASimpleEnum.register(Sound.class, element);
        byName.put(element.getSoundName(), element);
    }

    /**
     * Get one of {@link Sound} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static Sound getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(Sound.class, ordinal);
    }

    /**
     * Get one of Difficulty entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static Sound getByEnumName(final String name)
    {
        return getByEnumName(Sound.class, name);
    }

    /**
     * @return all values in array.
     */
    public static Sound[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(Sound.class);
        return (Sound[]) map.values(new Sound[map.size()]);
    }

    static
    {
        Sound.register(RECORD_13);
        Sound.register(RECORD_CAT);
        Sound.register(RECORD_BLOCKS);
        Sound.register(RECORD_CHRIP);
        Sound.register(RECORD_FAR);
        Sound.register(RECORD_MALL);
        Sound.register(RECORD_MELLOHI);
        Sound.register(RECORD_STAL);
        Sound.register(RECORD_STRAD);
        Sound.register(RECORD_WARD);
        Sound.register(RECORD_11);
        Sound.register(RECORD_WAIT);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).toString();
    }
}
