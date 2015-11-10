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
 * Class representing 'Quartz Stairs' block material in minecraft. <br>
 * ID of block: 156 <br>
 * String ID of block: minecraft:quartz_brick_stairs <br>
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
public class QuartzStairsMat extends BlockMaterialData implements StairsMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 8;

    public static final QuartzStairsMat QUARTZ_STAIRS_EAST  = new QuartzStairsMat();
    public static final QuartzStairsMat QUARTZ_STAIRS_WEST  = new QuartzStairsMat(BlockFace.WEST, false);
    public static final QuartzStairsMat QUARTZ_STAIRS_SOUTH = new QuartzStairsMat(BlockFace.SOUTH, false);
    public static final QuartzStairsMat QUARTZ_STAIRS_NORTH = new QuartzStairsMat(BlockFace.NORTH, false);

    public static final QuartzStairsMat QUARTZ_STAIRS_EAST_UPSIDE_DOWN  = new QuartzStairsMat(BlockFace.EAST, true);
    public static final QuartzStairsMat QUARTZ_STAIRS_WEST_UPSIDE_DOWN  = new QuartzStairsMat(BlockFace.WEST, true);
    public static final QuartzStairsMat QUARTZ_STAIRS_SOUTH_UPSIDE_DOWN = new QuartzStairsMat(BlockFace.SOUTH, true);
    public static final QuartzStairsMat QUARTZ_STAIRS_NORTH_UPSIDE_DOWN = new QuartzStairsMat(BlockFace.NORTH, true);

    private static final Map<String, QuartzStairsMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<QuartzStairsMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final BlockFace face;
    protected final boolean   upsideDown;

    @SuppressWarnings("MagicNumber")
    protected QuartzStairsMat()
    {
        super("QUARTZ_STAIRS", 156, "minecraft:quartz_brick_stairs", "EAST", (byte) 0x00, 0.8f, 4);
        this.face = BlockFace.EAST;
        this.upsideDown = false;
    }

    protected QuartzStairsMat(final BlockFace face, final boolean upsideDown)
    {
        super(QUARTZ_STAIRS_EAST.name(), QUARTZ_STAIRS_EAST.ordinal(), QUARTZ_STAIRS_EAST.getMinecraftId(), face.name() + (upsideDown ? "_UPSIDE_DOWN" : ""), StairsMat.combine(face, upsideDown), QUARTZ_STAIRS_EAST.getHardness(), QUARTZ_STAIRS_EAST.getBlastResistance());
        this.face = face;
        this.upsideDown = upsideDown;
    }

    protected QuartzStairsMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final boolean upsideDown, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.face = face;
        this.upsideDown = upsideDown;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.QUARTZ_STAIRS;
    }

    @Override
    public boolean isUpsideDown()
    {
        return this.upsideDown;
    }

    @Override
    public QuartzStairsMat getUpsideDown(final boolean upsideDown)
    {
        return getByID(StairsMat.combine(this.face, upsideDown));
    }

    @Override
    public QuartzStairsMat getType(final BlockFace face, final boolean upsideDown)
    {
        return getByID(StairsMat.combine(face, upsideDown));
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public QuartzStairsMat getBlockFacing(final BlockFace face)
    {
        return getByID(StairsMat.combine(face, this.upsideDown));
    }

    @Override
    public QuartzStairsMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public QuartzStairsMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).append("upsideDown", this.upsideDown).toString();
    }

    /**
     * Returns one of QuartzStairs sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of QuartzStairs or null
     */
    public static QuartzStairsMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of QuartzStairs sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of QuartzStairs or null
     */
    public static QuartzStairsMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of QuartzStairs sub-type based on facing direction and upside-down state.
     * It will never return null.
     *
     * @param blockFace  facing direction of stairs.
     * @param upsideDown if stairs should be upside-down.
     *
     * @return sub-type of QuartzStairs
     */
    public static QuartzStairsMat getQuartzStairs(final BlockFace blockFace, final boolean upsideDown)
    {
        return getByID(StairsMat.combine(blockFace, upsideDown));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final QuartzStairsMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public QuartzStairsMat[] types()
    {
        return QuartzStairsMat.quartzStairsTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static QuartzStairsMat[] quartzStairsTypes()
    {
        return byID.values(new QuartzStairsMat[byID.size()]);
    }

    static
    {
        QuartzStairsMat.register(QUARTZ_STAIRS_EAST);
        QuartzStairsMat.register(QUARTZ_STAIRS_WEST);
        QuartzStairsMat.register(QUARTZ_STAIRS_SOUTH);
        QuartzStairsMat.register(QUARTZ_STAIRS_NORTH);
        QuartzStairsMat.register(QUARTZ_STAIRS_EAST_UPSIDE_DOWN);
        QuartzStairsMat.register(QUARTZ_STAIRS_WEST_UPSIDE_DOWN);
        QuartzStairsMat.register(QUARTZ_STAIRS_SOUTH_UPSIDE_DOWN);
        QuartzStairsMat.register(QUARTZ_STAIRS_NORTH_UPSIDE_DOWN);
    }
}
