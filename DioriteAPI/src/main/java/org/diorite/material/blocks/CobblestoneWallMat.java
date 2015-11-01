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

import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Cobblestone Wall' block material in minecraft. <br>
 * ID of block: 139 <br>
 * String ID of block: minecraft:cobblestone_wall <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * MOSSY:
 * Type name: 'Mossy' <br>
 * SubID: 1 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * COBBLESTONE_WALL:
 * Type name: 'Cobblestone Wall' <br>
 * SubID: 0 <br>
 * Hardness: 2 <br>
 * Blast Resistance 30 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class CobblestoneWallMat extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 2;

    public static final CobblestoneWallMat COBBLESTONE_WALL       = new CobblestoneWallMat();
    public static final CobblestoneWallMat COBBLESTONE_WALL_MOSSY = new CobblestoneWallMat("MOSSY", 0x1);

    private static final Map<String, CobblestoneWallMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<CobblestoneWallMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected CobblestoneWallMat()
    {
        super("COBBLESTONE_WALL", 139, "minecraft:cobblestone_wall", "COBBLESTONE_WALL", (byte) 0x00, 2, 30);
    }

    protected CobblestoneWallMat(final String enumName, final int type)
    {
        super(COBBLESTONE_WALL.name(), COBBLESTONE_WALL.ordinal(), COBBLESTONE_WALL.getMinecraftId(), enumName, (byte) type, COBBLESTONE_WALL.getHardness(), COBBLESTONE_WALL.getBlastResistance());
    }

    protected CobblestoneWallMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public CobblestoneWallMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public CobblestoneWallMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of CobblestoneWall sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of CobblestoneWall or null
     */
    public static CobblestoneWallMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of CobblestoneWall sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of CobblestoneWall or null
     */
    public static CobblestoneWallMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final CobblestoneWallMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public CobblestoneWallMat[] types()
    {
        return CobblestoneWallMat.cobblestoneWallTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static CobblestoneWallMat[] cobblestoneWallTypes()
    {
        return byID.values(new CobblestoneWallMat[byID.size()]);
    }

    static
    {
        CobblestoneWallMat.register(COBBLESTONE_WALL);
        CobblestoneWallMat.register(COBBLESTONE_WALL_MOSSY);
    }
}
