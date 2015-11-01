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

import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;
import org.diorite.utils.math.DioriteMathUtils;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Cauldron Block' block material in minecraft. <br>
 * ID of block: 118 <br>
 * String ID of block: minecraft:cauldron <br>
 * This block can't be used in inventory, valid material for this block: 'Cauldron' (minecraft:cauldron(380):0) <br>
 * Hardness: 2 <br>
 * Blast Resistance 10 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * FULL:
 * Type name: 'Full' <br>
 * SubID: 3 <br>
 * Hardness: 2 <br>
 * Blast Resistance 10 <br>
 * </li>
 * <li>
 * 2:
 * Type name: '2' <br>
 * SubID: 2 <br>
 * Hardness: 2 <br>
 * Blast Resistance 10 <br>
 * </li>
 * <li>
 * 1:
 * Type name: '1' <br>
 * SubID: 1 <br>
 * Hardness: 2 <br>
 * Blast Resistance 10 <br>
 * </li>
 * <li>
 * EMPTY:
 * Type name: 'Empty' <br>
 * SubID: 0 <br>
 * Hardness: 2 <br>
 * Blast Resistance 10 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class CauldronBlockMat extends BlockMaterialData
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 4;

    public static final CauldronBlockMat CAULDRON_BLOCK_EMPTY = new CauldronBlockMat();
    public static final CauldronBlockMat CAULDRON_BLOCK_1     = new CauldronBlockMat("1", 0x1, 1);
    public static final CauldronBlockMat CAULDRON_BLOCK_2     = new CauldronBlockMat("2", 0x2, 2);
    public static final CauldronBlockMat CAULDRON_BLOCK_FULL  = new CauldronBlockMat("FULL", 0x3, 3);

    private static final Map<String, CauldronBlockMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<CauldronBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final int waterLevel;

    @SuppressWarnings("MagicNumber")
    protected CauldronBlockMat()
    {
        super("CAULDRON_BLOCK", 118, "minecraft:cauldron", "EMPTY", (byte) 0x00, 2, 10);
        this.waterLevel = 0;
    }

    protected CauldronBlockMat(final String enumName, final int type, final int waterLevel)
    {
        super(CAULDRON_BLOCK_EMPTY.name(), CAULDRON_BLOCK_EMPTY.ordinal(), CAULDRON_BLOCK_EMPTY.getMinecraftId(), enumName, (byte) type, CAULDRON_BLOCK_EMPTY.getHardness(), CAULDRON_BLOCK_EMPTY.getBlastResistance());
        this.waterLevel = waterLevel;
    }

    protected CauldronBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final int waterLevel, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.waterLevel = waterLevel;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.CAULDRON;
    }

    /**
     * Returns water level of cauldron.
     * Every level can be used to fill one water bottle.
     *
     * @return water level.
     */
    public int getWaterLevel()
    {
        return this.waterLevel;
    }

    /**
     * Returns one of Cauldron sub-type based on water-level.
     * It will never return null.
     *
     * @param level water level.
     *
     * @return sub-type of Cauldron
     */
    public CauldronBlockMat getWaterLevel(final int level)
    {
        return getByID(DioriteMathUtils.getInRange(level, 0, 3));
    }

    @Override
    public CauldronBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public CauldronBlockMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("waterLevel", this.waterLevel).toString();
    }

    /**
     * Returns one of Cauldron sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Cauldron or null
     */
    public static CauldronBlockMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Cauldron sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Cauldron or null
     */
    public static CauldronBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Cauldron sub-type based on water-level.
     * It will never return null.
     *
     * @param level water level.
     *
     * @return sub-type of Cauldron
     */
    public static CauldronBlockMat getCauldron(final int level)
    {
        return getByID(DioriteMathUtils.getInRange(level, 0, 3));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final CauldronBlockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public CauldronBlockMat[] types()
    {
        return CauldronBlockMat.cauldronTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static CauldronBlockMat[] cauldronTypes()
    {
        return byID.values(new CauldronBlockMat[byID.size()]);
    }

    static
    {
        CauldronBlockMat.register(CAULDRON_BLOCK_EMPTY);
        CauldronBlockMat.register(CAULDRON_BLOCK_1);
        CauldronBlockMat.register(CAULDRON_BLOCK_2);
        CauldronBlockMat.register(CAULDRON_BLOCK_FULL);
    }
}
