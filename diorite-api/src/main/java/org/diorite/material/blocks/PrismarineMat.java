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
 * Class representing 'Prismarine' block material in minecraft. <br>
 * ID of block: 168 <br>
 * String ID of block: minecraft:prismarine <br>
 * Hardness: 1,5 <br>
 * Blast Resistance 30 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * DARK:
 * Type name: 'Dark' <br>
 * SubID: 2 <br>
 * Hardness: 1,5 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * BRICKS:
 * Type name: 'Bricks' <br>
 * SubID: 1 <br>
 * Hardness: 1,5 <br>
 * Blast Resistance 30 <br>
 * </li>
 * <li>
 * PRISMARINE:
 * Type name: 'Prismarine' <br>
 * SubID: 0 <br>
 * Hardness: 1,5 <br>
 * Blast Resistance 30 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class PrismarineMat extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 3;

    public static final PrismarineMat PRISMARINE        = new PrismarineMat();
    public static final PrismarineMat PRISMARINE_BRICKS = new PrismarineMat("BRICKS", 0x1);
    public static final PrismarineMat PRISMARINE_DARK   = new PrismarineMat("DARK", 0x2);

    private static final Map<String, PrismarineMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<PrismarineMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected PrismarineMat()
    {
        super("PRISMARINE", 168, "minecraft:prismarine", "PRISMARINE", (byte) 0x00, 1.5f, 30);
    }

    protected PrismarineMat(final String enumName, final int type)
    {
        super(PRISMARINE.name(), PRISMARINE.ordinal(), PRISMARINE.getMinecraftId(), enumName, (byte) type, PRISMARINE.getHardness(), PRISMARINE.getBlastResistance());
    }

    protected PrismarineMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public PrismarineMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public PrismarineMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of Prismarine sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Prismarine or null
     */
    public static PrismarineMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Prismarine sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Prismarine or null
     */
    public static PrismarineMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final PrismarineMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public PrismarineMat[] types()
    {
        return PrismarineMat.prismarineTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static PrismarineMat[] prismarineTypes()
    {
        return byID.values(new PrismarineMat[byID.size()]);
    }

    static
    {
        PrismarineMat.register(PRISMARINE);
        PrismarineMat.register(PRISMARINE_BRICKS);
        PrismarineMat.register(PRISMARINE_DARK);
    }
}
