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

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;

import gnu.trove.map.TIntObjectMap;

@SuppressWarnings("ClassHasNoToStringMethod")
public class GameMode extends ASimpleEnum<GameMode>
{
    static
    {
        init(GameMode.class, 4);
    }

    public static final GameMode SURVIVAL  = new GameMode("SURVIVAL", "survival");
    public static final GameMode CREATIVE  = new GameMode("CREATIVE", "creative");
    public static final GameMode ADVENTURE = new GameMode("ADVENTURE", "adventure");
    public static final GameMode SPECTATOR = new GameMode("SPECTATOR", "spectator");

    private final String name;

    public GameMode(final String enumName, final int enumId, final String name)
    {
        super(enumName, enumId);
        this.name = name;
    }

    public GameMode(final String enumName, final String name)
    {
        super(enumName);
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    /**
     * Register new {@link GameMode} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final GameMode element)
    {
        ASimpleEnum.register(GameMode.class, element);
    }

    /**
     * Get one of {@link GameMode} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static GameMode getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(GameMode.class, ordinal);
    }

    /**
     * Get one of GameMode entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static GameMode getByEnumName(final String name)
    {
        return getByEnumName(GameMode.class, name);
    }

    /**
     * @return all values in array.
     */
    public static GameMode[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(GameMode.class);
        return (GameMode[]) map.values(new GameMode[map.size()]);
    }

    static
    {
        GameMode.register(SURVIVAL);
        GameMode.register(CREATIVE);
        GameMode.register(ADVENTURE);
        GameMode.register(SPECTATOR);
    }
}
