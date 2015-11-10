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

import org.diorite.BlockFace;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.DirectionalMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Burning Furnace' block material in minecraft. <br>
 * ID of block: 62 <br>
 * String ID of block: minecraft:lit_furnace <br>
 * Hardness: 3,5 <br>
 * Blast Resistance 17,5 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * EAST:
 * Type name: 'East' <br>
 * SubID: 5 <br>
 * Hardness: 3,5 <br>
 * Blast Resistance 17,5 <br>
 * </li>
 * <li>
 * WEST:
 * Type name: 'West' <br>
 * SubID: 4 <br>
 * Hardness: 3,5 <br>
 * Blast Resistance 17,5 <br>
 * </li>
 * <li>
 * SOUTH:
 * Type name: 'South' <br>
 * SubID: 3 <br>
 * Hardness: 3,5 <br>
 * Blast Resistance 17,5 <br>
 * </li>
 * <li>
 * NORTH:
 * Type name: 'North' <br>
 * SubID: 0 <br>
 * Hardness: 3,5 <br>
 * Blast Resistance 17,5 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class BurningFurnaceMat extends BlockMaterialData implements DirectionalMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 4;

    public static final BurningFurnaceMat BURNING_FURNACE_NORTH = new BurningFurnaceMat();
    public static final BurningFurnaceMat BURNING_FURNACE_SOUTH = new BurningFurnaceMat(BlockFace.SOUTH);
    public static final BurningFurnaceMat BURNING_FURNACE_WEST  = new BurningFurnaceMat(BlockFace.WEST);
    public static final BurningFurnaceMat BURNING_FURNACE_EAST  = new BurningFurnaceMat(BlockFace.EAST);

    private static final Map<String, BurningFurnaceMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<BurningFurnaceMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final BlockFace face;

    @SuppressWarnings("MagicNumber")
    protected BurningFurnaceMat()
    {
        super("BURNING_FURNACE", 62, "minecraft:lit_furnace", "NORTH", (byte) 0x00, 3.5f, 17.5f);
        this.face = BlockFace.NORTH;
    }

    protected BurningFurnaceMat(final BlockFace face)
    {
        super(BURNING_FURNACE_NORTH.name(), BURNING_FURNACE_NORTH.ordinal(), BURNING_FURNACE_NORTH.getMinecraftId(), face.name(), combine(face), BURNING_FURNACE_NORTH.getHardness(), BURNING_FURNACE_NORTH.getBlastResistance());
        this.face = face;
    }

    protected BurningFurnaceMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.face = face;
    }

    @Override
    public BurningFurnaceMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public BurningFurnaceMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).toString();
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public BurningFurnaceMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face));
    }

    private static byte combine(final BlockFace face)
    {
        switch (face)
        {
            case SOUTH:
                return 0x3;
            case WEST:
                return 0x4;
            case EAST:
                return 0x5;
            default:
                return 0x2;
        }
    }

    /**
     * Returns one of BurningFurnace sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of BurningFurnace or null
     */
    public static BurningFurnaceMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of BurningFurnace sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of BurningFurnace or null
     */
    public static BurningFurnaceMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of BurningFurnace sub-type based on {@link BlockFace}
     * It will never return null.
     *
     * @param face facing of BurningFurnace.
     *
     * @return sub-type of BurningFurnace
     */
    public static BurningFurnaceMat getBurningFurnace(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final BurningFurnaceMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public BurningFurnaceMat[] types()
    {
        return BurningFurnaceMat.burningFurnaceTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static BurningFurnaceMat[] burningFurnaceTypes()
    {
        return byID.values(new BurningFurnaceMat[byID.size()]);
    }

    static
    {
        BurningFurnaceMat.register(BURNING_FURNACE_NORTH);
        BurningFurnaceMat.register(BURNING_FURNACE_SOUTH);
        BurningFurnaceMat.register(BURNING_FURNACE_WEST);
        BurningFurnaceMat.register(BURNING_FURNACE_EAST);
    }
}
