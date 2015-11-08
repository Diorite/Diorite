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
import org.diorite.material.AttachableMat;
import org.diorite.material.Material;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Wall Banner' block material in minecraft. <br>
 * ID of block: 177 <br>
 * String ID of block: minecraft:wall_banner <br>
 * This block can't be used in inventory, valid material for this block: 'Banner' (minecraft:banner(425):0) <br>
 * Hardness: 1 <br>
 * Blast Resistance 5 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * EAST:
 * Type name: 'East' <br>
 * SubID: 5 <br>
 * Hardness: 1 <br>
 * Blast Resistance 5 <br>
 * </li>
 * <li>
 * WEST:
 * Type name: 'West' <br>
 * SubID: 4 <br>
 * Hardness: 1 <br>
 * Blast Resistance 5 <br>
 * </li>
 * <li>
 * SOUTH:
 * Type name: 'South' <br>
 * SubID: 3 <br>
 * Hardness: 1 <br>
 * Blast Resistance 5 <br>
 * </li>
 * <li>
 * NORTH:
 * Type name: 'North' <br>
 * SubID: 2 <br>
 * Hardness: 1 <br>
 * Blast Resistance 5 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class WallBannerMat extends BannerBlockMat implements AttachableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 4;

    public static final WallBannerMat WALL_BANNER_NORTH = new WallBannerMat();
    public static final WallBannerMat WALL_BANNER_SOUTH = new WallBannerMat(BlockFace.SOUTH);
    public static final WallBannerMat WALL_BANNER_WEST  = new WallBannerMat(BlockFace.WEST);
    public static final WallBannerMat WALL_BANNER_EAST  = new WallBannerMat(BlockFace.EAST);

    private static final Map<String, WallBannerMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<WallBannerMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final BlockFace face;

    @SuppressWarnings("MagicNumber")
    protected WallBannerMat()
    {
        super("WALL_BANNER", 177, "minecraft:wall_banner", 16, "NORTH", (byte) 0x02, 1, 5);
        this.face = BlockFace.NORTH;
    }

    protected WallBannerMat(final BlockFace face)
    {
        super(WALL_BANNER_NORTH.name(), WALL_BANNER_NORTH.ordinal(), WALL_BANNER_NORTH.getMinecraftId(), WALL_BANNER_NORTH.getMaxStack(), face.name(), combine(face), WALL_BANNER_NORTH.getHardness(), WALL_BANNER_NORTH.getBlastResistance());
        this.face = face;
    }

    protected WallBannerMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.face = face;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return BANNER;
    }

    @Override
    public WallBannerMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public WallBannerMat getType(final int id)
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
    public WallBannerMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face));
    }

    @Override
    public WallBannerMat getAttachedFace(final BlockFace face)
    {
        return getByID(combine(face.getOppositeFace()));
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
     * Returns one of WallBanner sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of WallBanner or null
     */
    public static WallBannerMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of WallBanner sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of WallBanner or null
     */
    public static WallBannerMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of WallBanner sub-type based on {@link BlockFace}.
     * It will never return null;
     *
     * @param face facing of WallBanner
     *
     * @return sub-type of WallBanner
     */
    public static WallBannerMat getWallBanner(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final WallBannerMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public WallBannerMat[] types()
    {
        return WallBannerMat.wallBannerTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static WallBannerMat[] wallBannerTypes()
    {
        return byID.values(new WallBannerMat[byID.size()]);
    }

    static
    {
        WallBannerMat.register(WALL_BANNER_NORTH);
        WallBannerMat.register(WALL_BANNER_SOUTH);
        WallBannerMat.register(WALL_BANNER_WEST);
        WallBannerMat.register(WALL_BANNER_EAST);
    }
}
