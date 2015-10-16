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

package org.diorite.world;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;

import gnu.trove.map.TIntObjectMap;

@SuppressWarnings("ClassHasNoToStringMethod")
public class WorldType extends ASimpleEnum<WorldType>
{
    static
    {
        init(WorldType.class, 7);
    }

    public static final WorldType NORMAL                 = new WorldType("NORMAL", "default", 1);
    public static final WorldType FLAT                   = new WorldType("FLAT", "flat");
    public static final WorldType LARGE_BIOMES           = new WorldType("LARGE_BIOMES", "largeBiomes");
    public static final WorldType AMPLIFIED              = new WorldType("AMPLIFIED", "amplified");
    public static final WorldType CUSTOMIZED             = new WorldType("CUSTOMIZED", "customized");
    public static final WorldType DEBUG_ALL_BLOCK_STATES = new WorldType("DEBUG_ALL_BLOCK_STATES", "debug_all_block_states");
    public static final WorldType NORMAL_1_1             = new WorldType("NORMAL_1_1", "default_1_1", 0);

    private final String name;
    private final int    version;

    public WorldType(final String enumName, final int enumId, final String name, final int version)
    {
        super(enumName, enumId);
        this.name = name;
        this.version = version;
    }

    public WorldType(final String enumName, final String name, final int version)
    {
        super(enumName);
        this.name = name;
        this.version = version;
    }

    public WorldType(final String enumName, final String name)
    {
        this(enumName, name, 0);
    }

    public int getVersion()
    {
        return this.version;
    }

    public String getName()
    {
        return this.name;
    }

    /**
     * Register new {@link WorldType} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final WorldType element)
    {
        ASimpleEnum.register(WorldType.class, element);
    }

    /**
     * Get one of {@link WorldType} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static WorldType getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(WorldType.class, ordinal);
    }

    /**
     * Get one of WorldType entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static WorldType getByEnumName(final String name)
    {
        return getByEnumName(WorldType.class, name);
    }

    /**
     * @return all values in array.
     */
    public static WorldType[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(WorldType.class);
        return (WorldType[]) map.values(new WorldType[map.size()]);
    }

    static
    {
        WorldType.register(NORMAL);
        WorldType.register(FLAT);
        WorldType.register(LARGE_BIOMES);
        WorldType.register(AMPLIFIED);
        WorldType.register(CUSTOMIZED);
        WorldType.register(DEBUG_ALL_BLOCK_STATES);
        WorldType.register(NORMAL_1_1);
    }

    public static WorldType getType(final String name)
    {
        for (final WorldType type : values())
        {
            if (type.getName().equals(name))
            {
                return type;
            }
        }
        return null;
    }
}
