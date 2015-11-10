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

package org.diorite.material.blocks;

import java.util.Map;

import org.diorite.material.FuelMat;
import org.diorite.material.WoodType;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Planks' block material in minecraft. <br>
 * ID of block: 5 <br>
 * String ID of block: minecraft:planks <br>
 * Hardness: 2 <br>
 * Blast Resistance 15 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * DARK_OAK:
 * Type name: 'Dark Oak' <br>
 * SubID: 5 <br>
 * Hardness: 2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * ACACIA:
 * Type name: 'Acacia' <br>
 * SubID: 4 <br>
 * Hardness: 2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * JUNGLE:
 * Type name: 'Jungle' <br>
 * SubID: 3 <br>
 * Hardness: 2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * BIRCH:
 * Type name: 'Birch' <br>
 * SubID: 2 <br>
 * Hardness: 2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * SPRUCE:
 * Type name: 'Spruce' <br>
 * SubID: 1 <br>
 * Hardness: 2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * QAK:
 * Type name: 'Qak' <br>
 * SubID: 0 <br>
 * Hardness: 2 <br>
 * Blast Resistance 15 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class PlanksMat extends WoodMat implements FuelMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 6;

    public static final PlanksMat PLANKS_OAK      = new PlanksMat();
    public static final PlanksMat PLANKS_SPRUCE   = new PlanksMat(WoodType.SPRUCE);
    public static final PlanksMat PLANKS_BIRCH    = new PlanksMat(WoodType.BIRCH);
    public static final PlanksMat PLANKS_JUNGLE   = new PlanksMat(WoodType.JUNGLE);
    public static final PlanksMat PLANKS_ACACIA   = new PlanksMat(WoodType.ACACIA);
    public static final PlanksMat PLANKS_DARK_OAK = new PlanksMat(WoodType.DARK_OAK);

    private static final Map<String, PlanksMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<PlanksMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected PlanksMat()
    {
        super("PLANKS", 5, "minecraft:planks", "QAK", WoodType.OAK.getPlanksMeta(), WoodType.OAK, 2, 15);
    }

    protected PlanksMat(final WoodType woodType)
    {
        super(PLANKS_OAK.name(), PLANKS_OAK.ordinal(), PLANKS_OAK.getMinecraftId(), woodType.name(), woodType.getPlanksMeta(), woodType, PLANKS_OAK.getHardness(), PLANKS_OAK.getBlastResistance());
    }

    protected PlanksMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodType woodType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType, hardness, blastResistance);
    }

    @Override
    public PlanksMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public PlanksMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public PlanksMat getWoodType(final WoodType woodType)
    {
        return getPlanks(woodType);
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public int getFuelPower()
    {
        return 1500;
    }

    /**
     * Returns one of Planks sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Planks or null
     */
    public static PlanksMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Planks sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Planks or null
     */
    public static PlanksMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns sub-type of {@link PlanksMat}, based on {@link WoodType}.
     *
     * @param type {@link WoodType} of Planks
     *
     * @return sub-type of {@link PlanksMat}.
     */
    public static PlanksMat getPlanks(final WoodType type)
    {
        for (final PlanksMat mat : planksTypes())
        {
            if (mat.getWoodType().ordinal() == type.ordinal())
            {
                return mat;
            }
        }
        return null;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final PlanksMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public PlanksMat[] types()
    {
        return PlanksMat.planksTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static PlanksMat[] planksTypes()
    {
        return byID.values(new PlanksMat[byID.size()]);
    }

    static
    {
        PlanksMat.register(PLANKS_OAK);
        PlanksMat.register(PLANKS_SPRUCE);
        PlanksMat.register(PLANKS_BIRCH);
        PlanksMat.register(PLANKS_JUNGLE);
        PlanksMat.register(PLANKS_ACACIA);
        PlanksMat.register(PLANKS_DARK_OAK);
    }
}
