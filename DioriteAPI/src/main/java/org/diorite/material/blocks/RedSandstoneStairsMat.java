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
import org.diorite.material.Material;
import org.diorite.material.StairsMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Red Sandstone Stairs' block material in minecraft. <br>
 * ID of block: 180 <br>
 * String ID of block: minecraft:red_sandstone_stairs <br>
 * Hardness: 0,8 <br>
 * Blast Resistance 4 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * NORTH_UPSIDE_DOWN:
 * Type name: 'North Upside Down' <br>
 * SubID: 7 <br>
 * Hardness: 0,8 <br>
 * Blast Resistance 4 <br>
 * </li>
 * <li>
 * SOUTH_UPSIDE_DOWN:
 * Type name: 'South Upside Down' <br>
 * SubID: 6 <br>
 * Hardness: 0,8 <br>
 * Blast Resistance 4 <br>
 * </li>
 * <li>
 * WEST_UPSIDE_DOWN:
 * Type name: 'West Upside Down' <br>
 * SubID: 5 <br>
 * Hardness: 0,8 <br>
 * Blast Resistance 4 <br>
 * </li>
 * <li>
 * EAST_UPSIDE_DOWN:
 * Type name: 'East Upside Down' <br>
 * SubID: 4 <br>
 * Hardness: 0,8 <br>
 * Blast Resistance 4 <br>
 * </li>
 * <li>
 * NORTH:
 * Type name: 'North' <br>
 * SubID: 3 <br>
 * Hardness: 0,8 <br>
 * Blast Resistance 4 <br>
 * </li>
 * <li>
 * SOUTH:
 * Type name: 'South' <br>
 * SubID: 2 <br>
 * Hardness: 0,8 <br>
 * Blast Resistance 4 <br>
 * </li>
 * <li>
 * WEST:
 * Type name: 'West' <br>
 * SubID: 1 <br>
 * Hardness: 0,8 <br>
 * Blast Resistance 4 <br>
 * </li>
 * <li>
 * EAST:
 * Type name: 'East' <br>
 * SubID: 0 <br>
 * Hardness: 0,8 <br>
 * Blast Resistance 4 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class RedSandstoneStairsMat extends BlockMaterialData implements StairsMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 8;

    public static final RedSandstoneStairsMat RED_SANDSTONE_STAIRS_EAST  = new RedSandstoneStairsMat();
    public static final RedSandstoneStairsMat RED_SANDSTONE_STAIRS_WEST  = new RedSandstoneStairsMat(BlockFace.WEST, false);
    public static final RedSandstoneStairsMat RED_SANDSTONE_STAIRS_SOUTH = new RedSandstoneStairsMat(BlockFace.SOUTH, false);
    public static final RedSandstoneStairsMat RED_SANDSTONE_STAIRS_NORTH = new RedSandstoneStairsMat(BlockFace.NORTH, false);

    public static final RedSandstoneStairsMat RED_SANDSTONE_STAIRS_EAST_UPSIDE_DOWN  = new RedSandstoneStairsMat(BlockFace.EAST, true);
    public static final RedSandstoneStairsMat RED_SANDSTONE_STAIRS_WEST_UPSIDE_DOWN  = new RedSandstoneStairsMat(BlockFace.WEST, true);
    public static final RedSandstoneStairsMat RED_SANDSTONE_STAIRS_SOUTH_UPSIDE_DOWN = new RedSandstoneStairsMat(BlockFace.SOUTH, true);
    public static final RedSandstoneStairsMat RED_SANDSTONE_STAIRS_NORTH_UPSIDE_DOWN = new RedSandstoneStairsMat(BlockFace.NORTH, true);

    private static final Map<String, RedSandstoneStairsMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedSandstoneStairsMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final BlockFace face;
    protected final boolean   upsideDown;

    @SuppressWarnings("MagicNumber")
    protected RedSandstoneStairsMat()
    {
        super("RED_SANDSTONE_STAIRS", 180, "minecraft:red_sandstone_stairs", "EAST", (byte) 0x00, 0.8f, 4);
        this.face = BlockFace.EAST;
        this.upsideDown = false;
    }

    protected RedSandstoneStairsMat(final BlockFace face, final boolean upsideDown)
    {
        super(RED_SANDSTONE_STAIRS_EAST.name(), RED_SANDSTONE_STAIRS_EAST.ordinal(), RED_SANDSTONE_STAIRS_EAST.getMinecraftId(), face.name() + (upsideDown ? "_UPSIDE_DOWN" : ""), StairsMat.combine(face, upsideDown), RED_SANDSTONE_STAIRS_EAST.getHardness(), RED_SANDSTONE_STAIRS_EAST.getBlastResistance());
        this.face = face;
        this.upsideDown = upsideDown;
    }

    protected RedSandstoneStairsMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final boolean upsideDown, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.face = face;
        this.upsideDown = upsideDown;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.RED_SANDSTONE_STAIRS;
    }

    @Override
    public boolean isUpsideDown()
    {
        return this.upsideDown;
    }

    @Override
    public RedSandstoneStairsMat getUpsideDown(final boolean upsideDown)
    {
        return getByID(StairsMat.combine(this.face, upsideDown));
    }

    @Override
    public RedSandstoneStairsMat getType(final BlockFace face, final boolean upsideDown)
    {
        return getByID(StairsMat.combine(face, upsideDown));
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public RedSandstoneStairsMat getBlockFacing(final BlockFace face)
    {
        return getByID(StairsMat.combine(face, this.upsideDown));
    }

    @Override
    public RedSandstoneStairsMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RedSandstoneStairsMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).append("upsideDown", this.upsideDown).toString();
    }

    /**
     * Returns one of RedSandstoneStairs sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RedSandstoneStairs or null
     */
    public static RedSandstoneStairsMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of RedSandstoneStairs sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of RedSandstoneStairs or null
     */
    public static RedSandstoneStairsMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of RedSandstoneStairs sub-type based on facing direction and upside-down state.
     * It will never return null.
     *
     * @param blockFace  facing direction of stairs.
     * @param upsideDown if stairs should be upside-down.
     *
     * @return sub-type of RedSandstoneStairs
     */
    public static RedSandstoneStairsMat getRedSandstoneStairs(final BlockFace blockFace, final boolean upsideDown)
    {
        return getByID(StairsMat.combine(blockFace, upsideDown));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RedSandstoneStairsMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public RedSandstoneStairsMat[] types()
    {
        return RedSandstoneStairsMat.redSandstoneStairsTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static RedSandstoneStairsMat[] redSandstoneStairsTypes()
    {
        return byID.values(new RedSandstoneStairsMat[byID.size()]);
    }

    static
    {
        RedSandstoneStairsMat.register(RED_SANDSTONE_STAIRS_EAST);
        RedSandstoneStairsMat.register(RED_SANDSTONE_STAIRS_WEST);
        RedSandstoneStairsMat.register(RED_SANDSTONE_STAIRS_SOUTH);
        RedSandstoneStairsMat.register(RED_SANDSTONE_STAIRS_NORTH);
        RedSandstoneStairsMat.register(RED_SANDSTONE_STAIRS_EAST_UPSIDE_DOWN);
        RedSandstoneStairsMat.register(RED_SANDSTONE_STAIRS_WEST_UPSIDE_DOWN);
        RedSandstoneStairsMat.register(RED_SANDSTONE_STAIRS_SOUTH_UPSIDE_DOWN);
        RedSandstoneStairsMat.register(RED_SANDSTONE_STAIRS_NORTH_UPSIDE_DOWN);
    }
}
