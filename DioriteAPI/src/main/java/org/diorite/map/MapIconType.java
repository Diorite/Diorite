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

package org.diorite.map;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;

import gnu.trove.map.TIntObjectMap;

/**
 * Represent map icons, icons are stored in /textures/map/map_icons.png
 */
@SuppressWarnings("ClassHasNoToStringMethod")
public class MapIconType extends ASimpleEnum<MapIconType>
{
    static
    {
        init(MapIconType.class, 7);
    }

    private final byte type;

    /**
     * White cursor used to mark player on map.
     */
    public static final MapIconType WHITE_CURSOR = new MapIconType("WHITE_CURSOR", 0);
    /**
     * Green cursor used to mark player on map.
     */
    public static final MapIconType GREEN_CURSOR = new MapIconType("GREEN_CURSOR", 1);
    /**
     * Red cursor used to mark player on map.
     */
    public static final MapIconType RED_CURSOR   = new MapIconType("RED_CURSOR", 2);
    /**
     * Blue cursor used to mark player on map.
     */
    public static final MapIconType BLUE_CURSOR  = new MapIconType("BLUE_CURSOR", 3);
    /**
     * White cross, unknown usage in vanilla.
     */
    public static final MapIconType WHITE_CROSS  = new MapIconType("WHITE_CROSS", 4);
    /**
     * Red arrow, unknown usage in vanilla.
     */
    public static final MapIconType RED_ARROW    = new MapIconType("RED_ARROW", 5);
    /**
     * White dot, unknown usage in vanilla.
     */
    public static final MapIconType WHITE_DOT    = new MapIconType("WHITE_DOT", 6);

    /**
     * Construct new MapIconType with given type.
     *
     * @param enumName enum name of type.
     * @param enumId   enum id of type.
     * @param type     id of type.
     */
    public MapIconType(final String enumName, final int enumId, final int type)
    {
        super(enumName, enumId);
        this.type = (byte) type;
    }

    /**
     * Construct new MapIconType with given type.
     *
     * @param enumName enum name of type.
     * @param type     id of type.
     */
    public MapIconType(final String enumName, final int type)
    {
        super(enumName);
        this.type = (byte) type;
    }

    /**
     * Returns id of map icon type.
     *
     * @return id of map icon type.
     */
    public int getType()
    {
        return this.type;
    }

    /**
     * Register new {@link MapIconType} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final MapIconType element)
    {
        ASimpleEnum.register(MapIconType.class, element);
    }

    /**
     * Get one of {@link MapIconType} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static MapIconType getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(MapIconType.class, ordinal);
    }

    /**
     * Get one of MapIconType entry by its type id.
     *
     * @param type type of entry.
     *
     * @return one of map icon types or null.
     */
    public static MapIconType getByType(final int type)
    {
        for (final MapIconType mapIconType : values())
        {
            if (mapIconType.type == type)
            {
                return mapIconType;
            }
        }
        return null;
    }

    /**
     * Get one of MapIconType entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static MapIconType getByEnumName(final String name)
    {
        return getByEnumName(MapIconType.class, name);
    }

    /**
     * @return all values in array.
     */
    public static MapIconType[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(MapIconType.class);
        return (MapIconType[]) map.values(new MapIconType[map.size()]);
    }

    static
    {
        MapIconType.register(WHITE_CURSOR);
        MapIconType.register(GREEN_CURSOR);
        MapIconType.register(RED_CURSOR);
        MapIconType.register(BLUE_CURSOR);
        MapIconType.register(WHITE_CROSS);
        MapIconType.register(RED_ARROW);
        MapIconType.register(WHITE_DOT);
    }
}