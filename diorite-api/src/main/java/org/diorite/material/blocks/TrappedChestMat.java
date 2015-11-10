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
 * Class representing 'Trapped Chest' block material in minecraft. <br>
 * ID of block: 146 <br>
 * String ID of block: minecraft:trapped_chest <br>
 * Hardness: 2,5 <br>
 * Blast Resistance 12,5 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * EAST:
 * Type name: 'East' <br>
 * SubID: 5 <br>
 * Hardness: 2,5 <br>
 * Blast Resistance 12,5 <br>
 * </li>
 * <li>
 * WEST:
 * Type name: 'West' <br>
 * SubID: 4 <br>
 * Hardness: 2,5 <br>
 * Blast Resistance 12,5 <br>
 * </li>
 * <li>
 * SOUTH:
 * Type name: 'South' <br>
 * SubID: 3 <br>
 * Hardness: 2,5 <br>
 * Blast Resistance 12,5 <br>
 * </li>
 * <li>
 * NORTH:
 * Type name: 'North' <br>
 * SubID: 0 <br>
 * Hardness: 2,5 <br>
 * Blast Resistance 12,5 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class TrappedChestMat extends BlockMaterialData implements DirectionalMat, FuelMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 4;

    public static final TrappedChestMat TRAPPED_CHEST_NORTH = new TrappedChestMat();
    public static final TrappedChestMat TRAPPED_CHEST_SOUTH = new TrappedChestMat(BlockFace.SOUTH);
    public static final TrappedChestMat TRAPPED_CHEST_WEST  = new TrappedChestMat(BlockFace.WEST);
    public static final TrappedChestMat TRAPPED_CHEST_EAST  = new TrappedChestMat(BlockFace.EAST);

    private static final Map<String, TrappedChestMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<TrappedChestMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final BlockFace face;

    @SuppressWarnings("MagicNumber")
    protected TrappedChestMat()
    {
        super("TRAPPED_CHEST", 146, "minecraft:trapped_chest", "NORTH", (byte) 0x00, 2.5f, 12.5f);
        this.face = BlockFace.NORTH;
    }

    protected TrappedChestMat(final BlockFace face)
    {
        super(TRAPPED_CHEST_NORTH.name(), TRAPPED_CHEST_NORTH.ordinal(), TRAPPED_CHEST_NORTH.getMinecraftId(), face.name(), combine(face), TRAPPED_CHEST_NORTH.getHardness(), TRAPPED_CHEST_NORTH.getBlastResistance());
        this.face = face;
    }

    protected TrappedChestMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.face = face;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.TRAPPED_CHEST;
    }

    @Override
    public TrappedChestMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public TrappedChestMat getType(final int id)
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
    public TrappedChestMat getBlockFacing(final BlockFace face)
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
     * Returns one of TrappedChest sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of TrappedChest or null
     */
    public static TrappedChestMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of TrappedChest sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of TrappedChest or null
     */
    public static TrappedChestMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of TrappedChest sub-type based on {@link BlockFace}
     * It will never return null.
     *
     * @param face facing of TrappedChest.
     *
     * @return sub-type of TrappedChest
     */
    public static TrappedChestMat getTrappedChest(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final TrappedChestMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public TrappedChestMat[] types()
    {
        return TrappedChestMat.trappedChestTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static TrappedChestMat[] trappedChestTypes()
    {
        return byID.values(new TrappedChestMat[byID.size()]);
    }

    static
    {
        TrappedChestMat.register(TRAPPED_CHEST_NORTH);
        TrappedChestMat.register(TRAPPED_CHEST_SOUTH);
        TrappedChestMat.register(TRAPPED_CHEST_WEST);
        TrappedChestMat.register(TRAPPED_CHEST_EAST);
    }
}
