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
 * Class representing 'Wall Sign' block material in minecraft. <br>
 * ID of block: 68 <br>
 * String ID of block: minecraft:wall_sign <br>
 * This block can't be used in inventory, valid material for this block: 'Sign' (minecraft:sign(323):0) <br>
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
public class WallSignMat extends SignBlockMat implements AttachableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 4;

    public static final WallSignMat WALL_SIGN_NORTH = new WallSignMat();
    public static final WallSignMat WALL_SIGN_SOUTH = new WallSignMat(BlockFace.SOUTH);
    public static final WallSignMat WALL_SIGN_WEST  = new WallSignMat(BlockFace.WEST);
    public static final WallSignMat WALL_SIGN_EAST  = new WallSignMat(BlockFace.EAST);

    private static final Map<String, WallSignMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<WallSignMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final BlockFace face;

    @SuppressWarnings("MagicNumber")
    protected WallSignMat()
    {
        super("WALL_SIGN", 68, "minecraft:wall_sign", 16, "NORTH", (byte) 0x02, 1, 5);
        this.face = BlockFace.NORTH;
    }

    protected WallSignMat(final BlockFace face)
    {
        super(WALL_SIGN_NORTH.name(), WALL_SIGN_NORTH.ordinal(), WALL_SIGN_NORTH.getMinecraftId(), WALL_SIGN_NORTH.getMaxStack(), face.name(), combine(face), WALL_SIGN_NORTH.getHardness(), WALL_SIGN_NORTH.getBlastResistance());
        this.face = face;
    }

    protected WallSignMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.face = face;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return SIGN;
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public WallSignMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face));
    }

    @Override
    public WallSignMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public WallSignMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).toString();
    }

    @Override
    public WallSignMat getAttachedFace(final BlockFace face)
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
     * Returns one of WallSign sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of WallSign or null
     */
    public static WallSignMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of WallSign sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of WallSign or null
     */
    public static WallSignMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of WallSign sub-type based on {@link BlockFace}.
     * It will never return null;
     *
     * @param face facing of WallBanner
     *
     * @return sub-type of WallSign
     */
    public static WallSignMat getWallSign(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final WallSignMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public WallSignMat[] types()
    {
        return WallSignMat.wallSignTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static WallSignMat[] wallSignTypes()
    {
        return byID.values(new WallSignMat[byID.size()]);
    }

    static
    {
        WallSignMat.register(WALL_SIGN_NORTH);
        WallSignMat.register(WALL_SIGN_SOUTH);
        WallSignMat.register(WALL_SIGN_WEST);
        WallSignMat.register(WALL_SIGN_EAST);
    }
}
