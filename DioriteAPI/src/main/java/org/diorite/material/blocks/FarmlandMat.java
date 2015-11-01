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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Farmland' block material in minecraft. <br>
 * ID of block: 60 <br>
 * String ID of block: minecraft:farmland <br>
 * Hardness: 0,6 <br>
 * Blast Resistance 3 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * HYDRATED:
 * Type name: 'Hydrated' <br>
 * SubID: 7 <br>
 * Hardness: 0,6 <br>
 * Blast Resistance 3 <br>
 * </li>
 * <li>
 * MOISTURE_6:
 * Type name: 'Moisture 6' <br>
 * SubID: 6 <br>
 * Hardness: 0,6 <br>
 * Blast Resistance 3 <br>
 * </li>
 * <li>
 * MOISTURE_5:
 * Type name: 'Moisture 5' <br>
 * SubID: 5 <br>
 * Hardness: 0,6 <br>
 * Blast Resistance 3 <br>
 * </li>
 * <li>
 * MOISTURE_4:
 * Type name: 'Moisture 4' <br>
 * SubID: 4 <br>
 * Hardness: 0,6 <br>
 * Blast Resistance 3 <br>
 * </li>
 * <li>
 * MOISTURE_3:
 * Type name: 'Moisture 3' <br>
 * SubID: 3 <br>
 * Hardness: 0,6 <br>
 * Blast Resistance 3 <br>
 * </li>
 * <li>
 * MOISTURE_2:
 * Type name: 'Moisture 2' <br>
 * SubID: 2 <br>
 * Hardness: 0,6 <br>
 * Blast Resistance 3 <br>
 * </li>
 * <li>
 * MOISTURE_1:
 * Type name: 'Moisture 1' <br>
 * SubID: 1 <br>
 * Hardness: 0,6 <br>
 * Blast Resistance 3 <br>
 * </li>
 * <li>
 * UNHYDRATED:
 * Type name: 'Unhydrated' <br>
 * SubID: 0 <br>
 * Hardness: 0,6 <br>
 * Blast Resistance 3 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class FarmlandMat extends EarthMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 8;

    public static final FarmlandMat FARMLAND_UNHYDRATED = new FarmlandMat();
    public static final FarmlandMat FARMLAND_MOISTURE_1 = new FarmlandMat("MOISTURE_1", 1);
    public static final FarmlandMat FARMLAND_MOISTURE_2 = new FarmlandMat("MOISTURE_2", 2);
    public static final FarmlandMat FARMLAND_MOISTURE_3 = new FarmlandMat("MOISTURE_3", 3);
    public static final FarmlandMat FARMLAND_MOISTURE_4 = new FarmlandMat("MOISTURE_4", 4);
    public static final FarmlandMat FARMLAND_MOISTURE_5 = new FarmlandMat("MOISTURE_5", 5);
    public static final FarmlandMat FARMLAND_MOISTURE_6 = new FarmlandMat("MOISTURE_6", 6);
    public static final FarmlandMat FARMLAND_HYDRATED   = new FarmlandMat("HYDRATED", 7);

    private static final Map<String, FarmlandMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<FarmlandMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    private final int moisture;

    @SuppressWarnings("MagicNumber")
    protected FarmlandMat()
    {
        super("FARMLAND", 60, "minecraft:farmland", "UNHYDRATED", (byte) 0x00, 0.6f, 3);
        this.moisture = 0;
    }

    protected FarmlandMat(final String enumName, final int moisture)
    {
        super(FARMLAND_UNHYDRATED.name(), FARMLAND_UNHYDRATED.ordinal(), FARMLAND_UNHYDRATED.getMinecraftId(), enumName, (byte) moisture, FARMLAND_UNHYDRATED.getHardness(), FARMLAND_UNHYDRATED.getBlastResistance());
        this.moisture = moisture;
    }

    protected FarmlandMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final int moisture, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.moisture = moisture;
    }

    @Override
    public FarmlandMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public FarmlandMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("moisture", this.moisture).toString();
    }

    /**
     * @return moisture level, 7 {@literal ->} full hydrated
     */
    public int getMoistured()
    {
        return this.moisture;
    }

    /**
     * @param moisture moisture level.
     *
     * @return selected sub-type of Farmland
     */
    public FarmlandMat getHydrated(final int moisture)
    {
        return getFarmland(moisture);
    }

    /**
     * Returns one of Farmland sub-type based on sub-id, may return null
     * {@link #getFarmland}
     *
     * @param id sub-type id
     *
     * @return sub-type of Farmland or null
     */
    public static FarmlandMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Farmland sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Farmland or null
     */
    public static FarmlandMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Farmland sub-types based on moisture.
     * It will never return null. (unhydrated version by default)
     *
     * @param moisture if sub-type should be hydrated sub-type.
     *
     * @return selected sub-type of Farmland
     */
    public static FarmlandMat getFarmland(final int moisture)
    {
        final FarmlandMat f = getByID(moisture);
        if (f == null)
        {
            return FARMLAND_UNHYDRATED;
        }
        return f;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final FarmlandMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public FarmlandMat[] types()
    {
        return FarmlandMat.farmlandTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static FarmlandMat[] farmlandTypes()
    {
        return byID.values(new FarmlandMat[byID.size()]);
    }

    static
    {
        FarmlandMat.register(FARMLAND_UNHYDRATED);
        FarmlandMat.register(FARMLAND_MOISTURE_1);
        FarmlandMat.register(FARMLAND_MOISTURE_2);
        FarmlandMat.register(FARMLAND_MOISTURE_3);
        FarmlandMat.register(FARMLAND_MOISTURE_4);
        FarmlandMat.register(FARMLAND_MOISTURE_5);
        FarmlandMat.register(FARMLAND_MOISTURE_6);
        FarmlandMat.register(FARMLAND_HYDRATED);
    }
}
