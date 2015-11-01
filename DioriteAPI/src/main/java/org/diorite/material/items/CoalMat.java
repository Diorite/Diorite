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

package org.diorite.material.items;

import java.util.Map;

import org.diorite.material.FuelMat;
import org.diorite.material.OreItemMat.OreItemMatExt;
import org.diorite.material.blocks.OreBlockMat;
import org.diorite.material.blocks.OreMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

/**
 * Class representing 'Coal' item material in minecraft. <br>
 * ID of material: 263 <br>
 * String ID of material: minecraft:coal <br>
 * Max item stack size: 64 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * CHARCOAL:
 * Type name: 'Charcoal' <br>
 * SubID: 1 <br>
 * Max item stack size: 64
 * </li>
 * <li>
 * COAL:
 * Type name: 'Coal' <br>
 * SubID: 0 <br>
 * Max item stack size: 64
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class CoalMat extends OreItemMatExt implements FuelMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 2;

    public static final CoalMat COAL     = new CoalMat();
    public static final CoalMat CHARCOAL = new CoalMat("CHARCOAL", 0x01, null, COAL_BLOCK);

    private static final Map<String, CoalMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<CoalMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected CoalMat()
    {
        super("COAL", 263, "minecraft:coal", "COAL", (short) 0x00, COAL_ORE, COAL_BLOCK);
    }

    protected CoalMat(final String name, final int type, final OreMat oreType, final OreBlockMat blockType)
    {
        super(COAL.name(), COAL.getId(), COAL.getMinecraftId(), name, (short) type, oreType, blockType);
    }

    protected CoalMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final OreMat oreType, final OreBlockMat blockType)
    {
        super(enumName, id, minecraftId, typeName, type, oreType, blockType);
    }

    protected CoalMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final OreMat oreType, final OreBlockMat blockType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, oreType, blockType);
    }

    @Override
    public CoalMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public CoalMat getType(final String type)
    {
        return getByEnumName(type);
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public int getFuelPower()
    {
        return 8000;
    }

    /**
     * Returns one of Coal sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Coal or null
     */
    public static CoalMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of Coal sub-type based on name (selected by diorite team), may return null
     * If item contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Coal or null
     */
    public static CoalMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final CoalMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public CoalMat[] types()
    {
        return CoalMat.coalTypes();
    }

    /**
     * @return array that contains all sub-types of this item.
     */
    public static CoalMat[] coalTypes()
    {
        return byID.values(new CoalMat[byID.size()]);
    }

    static
    {
        CoalMat.register(COAL);
        CoalMat.register(CHARCOAL);
    }
}

