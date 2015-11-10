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

import org.diorite.material.Material;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Redstone Lamp On' block material in minecraft. <br>
 * ID of block: 124 <br>
 * String ID of block: minecraft:lit_redstone_lamp <br>
 * This block can't be used in inventory, valid material for this block: 'Redstone Lamp Off' (minecraft:redstone_lamp(123):0) <br>
 * Hardness: 0,3 <br>
 * Blast Resistance 1,5
 */
@SuppressWarnings("JavaDoc")
public class RedstoneLampOnMat extends RedstoneLampMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final RedstoneLampOnMat REDSTONE_LAMP_ON = new RedstoneLampOnMat();

    private static final Map<String, RedstoneLampOnMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedstoneLampOnMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected RedstoneLampOnMat()
    {
        super("REDSTONE_LAMP_ON", 124, "minecraft:lit_redstone_lamp", "REDSTONE_LAMP_ON", (byte) 0x00, 0.3f, 1.5f);
    }

    protected RedstoneLampOnMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return REDSTONE_LAMP_OFF;
    }

    @Override
    public RedstoneLampOnMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RedstoneLampOnMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isPowered()
    {
        return true;
    }

    /**
     * Returns one of RedstoneLampOn sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RedstoneLampOn or null
     */
    public static RedstoneLampOnMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of RedstoneLampOn sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of RedstoneLampOn or null
     */
    public static RedstoneLampOnMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RedstoneLampOnMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public RedstoneLampOnMat[] types()
    {
        return RedstoneLampOnMat.redstoneLampOnTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static RedstoneLampOnMat[] redstoneLampOnTypes()
    {
        return byID.values(new RedstoneLampOnMat[byID.size()]);
    }

    static
    {
        RedstoneLampOnMat.register(REDSTONE_LAMP_ON);
    }
}
