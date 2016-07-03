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

package org.diorite.scoreboard;

import org.diorite.utils.SimpleEnum.ASimpleEnum;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

public class ScoreboardPosition extends ASimpleEnum<ScoreboardPosition>
{
    static
    {
        init(ScoreboardPosition.class, 3);
    }

    public static final ScoreboardPosition LIST       = new ScoreboardPosition("LIST", 0);
    public static final ScoreboardPosition SIDEBAR    = new ScoreboardPosition("SIDEBAR", 1);
    public static final ScoreboardPosition BELOW_NAME = new ScoreboardPosition("BELOW_NAME", 2);

    public ScoreboardPosition(final String enumName, final int ordinal)
    {
        super(enumName, ordinal);
    }

    /**
     * Register new {@link ScoreboardPosition} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final ScoreboardPosition element)
    {
        ASimpleEnum.register(ScoreboardPosition.class, element);
    }

    /**
     * Get one of {@link ScoreboardPosition} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static ScoreboardPosition getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(ScoreboardPosition.class, ordinal);
    }

    /**
     * Get one of {@link ScoreboardPosition} entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static ScoreboardPosition getByEnumName(final String name)
    {
        return getByEnumName(ScoreboardPosition.class, name);
    }

    /**
     * @return all values in array.
     */
    public static ScoreboardPosition[] values()
    {
        final Int2ObjectMap<ScoreboardPosition> map = getByEnumOrdinal(ScoreboardPosition.class);
        return map.values().toArray(new ScoreboardPosition[map.size()]);
    }

    static
    {
        ScoreboardPosition.register(LIST);
        ScoreboardPosition.register(SIDEBAR);
        ScoreboardPosition.register(BELOW_NAME);
    }
}
