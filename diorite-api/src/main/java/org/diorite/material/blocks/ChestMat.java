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
import org.diorite.material.FuelMat;
import org.diorite.material.Material;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Chest' block material in minecraft. <br>
 * ID of block: 54 <br>
 * String ID of block: minecraft:chest <br>
 * Hardness: 12,5 <br>
 * Blast Resistance 2,5 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * EAST:
 * Type name: 'East' <br>
 * SubID: 5 <br>
 * Hardness: 12,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * WEST:
 * Type name: 'West' <br>
 * SubID: 4 <br>
 * Hardness: 12,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * SOUTH:
 * Type name: 'South' <br>
 * SubID: 3 <br>
 * Hardness: 12,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * NORTH:
 * Type name: 'North' <br>
 * SubID: 0 <br>
 * Hardness: 12,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class ChestMat extends BlockMaterialData implements DirectionalMat, FuelMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 4;

    public static final ChestMat CHEST_NORTH = new ChestMat();
    public static final ChestMat CHEST_SOUTH = new ChestMat(BlockFace.SOUTH);
    public static final ChestMat CHEST_WEST  = new ChestMat(BlockFace.WEST);
    public static final ChestMat CHEST_EAST  = new ChestMat(BlockFace.EAST);

    private static final Map<String, ChestMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<ChestMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final BlockFace face;

    @SuppressWarnings("MagicNumber")
    protected ChestMat()
    {
        super("CHEST", 54, "minecraft:chest", "NORTH", (byte) 0x00, 12.5f, 2.5f);
        this.face = BlockFace.NORTH;
    }

    protected ChestMat(final BlockFace face)
    {
        super(CHEST_NORTH.name(), CHEST_NORTH.ordinal(), CHEST_NORTH.getMinecraftId(), face.name(), combine(face), CHEST_NORTH.getHardness(), CHEST_NORTH.getBlastResistance());
        this.face = face;
    }

    protected ChestMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.face = face;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.CHEST;
    }

    @Override
    public ChestMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public ChestMat getType(final int id)
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
    public ChestMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face));
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public int getFuelPower()
    {
        return 1500;
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
     * Returns one of Chest sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Chest or null
     */
    public static ChestMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Chest sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Chest or null
     */
    public static ChestMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Chest sub-type based on {@link BlockFace}
     * It will never return null.
     *
     * @param face facing of Chest.
     *
     * @return sub-type of Chest
     */
    public static ChestMat getChest(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final ChestMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public ChestMat[] types()
    {
        return ChestMat.chestTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static ChestMat[] chestTypes()
    {
        return byID.values(new ChestMat[byID.size()]);
    }

    static
    {
        ChestMat.register(CHEST_NORTH);
        ChestMat.register(CHEST_SOUTH);
        ChestMat.register(CHEST_WEST);
        ChestMat.register(CHEST_EAST);
    }
}
