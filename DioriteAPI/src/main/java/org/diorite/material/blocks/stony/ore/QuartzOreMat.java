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

package org.diorite.material.blocks.stony.ore;

import java.util.Map;

import org.diorite.material.Material;
import org.diorite.material.blocks.stony.oreblocks.OreBlockMat;
import org.diorite.material.items.OreItemMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Quartz Ore' block material in minecraft. <br>
 * ID of block: 153 <br>
 * String ID of block: minecraft:quartz_ore <br>
 * Hardness: 3 <br>
 * Blast Resistance 15
 */
@SuppressWarnings("JavaDoc")
public class QuartzOreMat extends OreMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final QuartzOreMat QUARTZ_ORE = new QuartzOreMat();

    private static final Map<String, QuartzOreMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<QuartzOreMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected QuartzOreMat()
    {
        super("QUARTZ_ORE", 153, "minecraft:quartz_ore", "QUARTZ_ORE", (byte) 0x00, Material.QUARTZ, Material.QUARTZ_BLOCK, 3, 15);
    }

    protected QuartzOreMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final OreItemMat item, final OreBlockMat block, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, item, block, hardness, blastResistance);
    }

    @Override
    public QuartzOreMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public QuartzOreMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of QuartzOre sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of QuartzOre or null
     */
    public static QuartzOreMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of QuartzOre sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of QuartzOre or null
     */
    public static QuartzOreMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final QuartzOreMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public QuartzOreMat[] types()
    {
        return QuartzOreMat.quartzOreTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static QuartzOreMat[] quartzOreTypes()
    {
        return byID.values(new QuartzOreMat[byID.size()]);
    }

    static
    {
        QuartzOreMat.register(QUARTZ_ORE);
    }
}
