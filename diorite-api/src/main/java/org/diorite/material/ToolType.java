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

package org.diorite.material;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;

import gnu.trove.map.TIntObjectMap;

/**
 * Represent type of tool.
 */
public class ToolType extends ASimpleEnum<ToolType>
{
    static
    {
        init(ToolType.class, 5);
    }

    public static final ToolType PICKAXE = new ToolType("PICKAXE");
    public static final ToolType SHOVEL  = new ToolType("SHOVEL");
    public static final ToolType AXE     = new ToolType("AXE");
    public static final ToolType HOE     = new ToolType("HOE");
    public static final ToolType SWORD   = new ToolType("SWORD");

    public ToolType(final String enumName, final int enumId)
    {
        super(enumName, enumId);
    }

    public ToolType(final String enumName)
    {
        super(enumName);
    }

    /**
     * Register new {@link ToolType} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final ToolType element)
    {
        ASimpleEnum.register(ToolType.class, element);
    }

    /**
     * Get one of {@link ToolType} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static ToolType getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(ToolType.class, ordinal);
    }

    /**
     * Get one of ToolType entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static ToolType getByEnumName(final String name)
    {
        return getByEnumName(ToolType.class, name);
    }

    /**
     * @return all values in array.
     */
    public static ToolType[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(ToolType.class);
        return (ToolType[]) map.values(new ToolType[map.size()]);
    }

    static
    {
        ToolType.register(PICKAXE);
        ToolType.register(SHOVEL);
        ToolType.register(AXE);
        ToolType.register(HOE);
        ToolType.register(SWORD);
    }
}