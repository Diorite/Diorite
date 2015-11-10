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

import org.diorite.BlockFace;
import org.diorite.material.Material;
import org.diorite.material.StairsMat;
import org.diorite.material.WoodType;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Dark Oak Stairs' block material in minecraft. <br>
 * ID of block: 164 <br>
 * String ID of block: minecraft:dark_oak_stairs <br>
 * Hardness: 2 <br>
 * Blast Resistance 15
 */
@SuppressWarnings("JavaDoc")
public class DarkOakStairsMat extends WoodenStairsMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 8;

    public static final DarkOakStairsMat DARK_OAK_STAIRS_EAST  = new DarkOakStairsMat();
    public static final DarkOakStairsMat DARK_OAK_STAIRS_WEST  = new DarkOakStairsMat(BlockFace.WEST, false);
    public static final DarkOakStairsMat DARK_OAK_STAIRS_SOUTH = new DarkOakStairsMat(BlockFace.SOUTH, false);
    public static final DarkOakStairsMat DARK_OAK_STAIRS_NORTH = new DarkOakStairsMat(BlockFace.NORTH, false);

    public static final DarkOakStairsMat DARK_OAK_STAIRS_EAST_UPSIDE_DOWN  = new DarkOakStairsMat(BlockFace.EAST, true);
    public static final DarkOakStairsMat DARK_OAK_STAIRS_WEST_UPSIDE_DOWN  = new DarkOakStairsMat(BlockFace.WEST, true);
    public static final DarkOakStairsMat DARK_OAK_STAIRS_SOUTH_UPSIDE_DOWN = new DarkOakStairsMat(BlockFace.SOUTH, true);
    public static final DarkOakStairsMat DARK_OAK_STAIRS_NORTH_UPSIDE_DOWN = new DarkOakStairsMat(BlockFace.NORTH, true);

    private static final Map<String, DarkOakStairsMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DarkOakStairsMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected DarkOakStairsMat()
    {
        super("DARK_OAK_STAIRS", 164, "minecraft:dark_oak_stairs", WoodType.DARK_OAK, BlockFace.EAST, false, 2, 15);
    }

    protected DarkOakStairsMat(final BlockFace face, final boolean upsideDown)
    {
        super(DARK_OAK_STAIRS_EAST.name(), DARK_OAK_STAIRS_EAST.ordinal(), DARK_OAK_STAIRS_EAST.getMinecraftId(), WoodType.DARK_OAK, face, upsideDown, DARK_OAK_STAIRS_EAST.getHardness(), DARK_OAK_STAIRS_EAST.getBlastResistance());
    }

    protected DarkOakStairsMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodType woodType, final BlockFace face, final boolean upsideDown, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType, face, upsideDown, hardness, blastResistance);
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return DARK_OAK_STAIRS;
    }

    @Override
    public DarkOakStairsMat getBlockFacing(final BlockFace face)
    {
        return getByID(StairsMat.combine(face, this.upsideDown));
    }

    @Override
    public DarkOakStairsMat getUpsideDown(final boolean upsideDown)
    {
        return getByID(StairsMat.combine(this.face, upsideDown));
    }

    @Override
    public DarkOakStairsMat getType(final BlockFace face, final boolean upsideDown)
    {
        return getByID(StairsMat.combine(face, upsideDown));
    }

    @Override
    public DarkOakStairsMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DarkOakStairsMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of DarkOakStairs sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of DarkOakStairs or null
     */
    public static DarkOakStairsMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of DarkOakStairs sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DarkOakStairs or null
     */
    public static DarkOakStairsMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of DarkOakStairs sub-type based on facing direction and upside-down state.
     * It will never return null.
     *
     * @param blockFace  facing direction of stairs.
     * @param upsideDown if stairs should be upside-down.
     *
     * @return sub-type of DarkOakStairs
     */
    public static DarkOakStairsMat getDarkOakStairs(final BlockFace blockFace, final boolean upsideDown)
    {
        return getByID(StairsMat.combine(blockFace, upsideDown));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DarkOakStairsMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public DarkOakStairsMat[] types()
    {
        return DarkOakStairsMat.darkOakStairsTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static DarkOakStairsMat[] darkOakStairsTypes()
    {
        return byID.values(new DarkOakStairsMat[byID.size()]);
    }

    static
    {
        DarkOakStairsMat.register(DARK_OAK_STAIRS_EAST);
        DarkOakStairsMat.register(DARK_OAK_STAIRS_WEST);
        DarkOakStairsMat.register(DARK_OAK_STAIRS_SOUTH);
        DarkOakStairsMat.register(DARK_OAK_STAIRS_NORTH);
        DarkOakStairsMat.register(DARK_OAK_STAIRS_EAST_UPSIDE_DOWN);
        DarkOakStairsMat.register(DARK_OAK_STAIRS_WEST_UPSIDE_DOWN);
        DarkOakStairsMat.register(DARK_OAK_STAIRS_SOUTH_UPSIDE_DOWN);
        DarkOakStairsMat.register(DARK_OAK_STAIRS_NORTH_UPSIDE_DOWN);
    }
}
