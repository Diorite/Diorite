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
import org.diorite.material.Material;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Standing Sign' block material in minecraft. <br>
 * ID of block: 63 <br>
 * String ID of block: minecraft:standing_sign <br>
 * This block can't be used in inventory, valid material for this block: 'Sign' (minecraft:sign(323):0) <br>
 * Hardness: 1 <br>
 * Blast Resistance 5 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * SOUTH_SOUTH_EAST:
 * Type name: 'South South East' <br>
 * SubID: 15 <br>
 * Hardness: 1 <br>
 * Blast Resistance 5 <br>
 * </li>
 * <li>
 * SOUTH_EAST:
 * Type name: 'South East' <br>
 * SubID: 14 <br>
 * Hardness: 1 <br>
 * Blast Resistance 5 <br>
 * </li>
 * <li>
 * EAST_SOUTH_EAST:
 * Type name: 'East South East' <br>
 * SubID: 13 <br>
 * Hardness: 1 <br>
 * Blast Resistance 5 <br>
 * </li>
 * <li>
 * EAST:
 * Type name: 'East' <br>
 * SubID: 12 <br>
 * Hardness: 1 <br>
 * Blast Resistance 5 <br>
 * </li>
 * <li>
 * EAST_NORTH_EAST:
 * Type name: 'East North East' <br>
 * SubID: 11 <br>
 * Hardness: 1 <br>
 * Blast Resistance 5 <br>
 * </li>
 * <li>
 * NORTH_EAST:
 * Type name: 'North East' <br>
 * SubID: 10 <br>
 * Hardness: 1 <br>
 * Blast Resistance 5 <br>
 * </li>
 * <li>
 * NORTH_NORTH_EAST:
 * Type name: 'North North East' <br>
 * SubID: 9 <br>
 * Hardness: 1 <br>
 * Blast Resistance 5 <br>
 * </li>
 * <li>
 * NORTH:
 * Type name: 'North' <br>
 * SubID: 8 <br>
 * Hardness: 1 <br>
 * Blast Resistance 5 <br>
 * </li>
 * <li>
 * NORTH_NORTH_WEST:
 * Type name: 'North North West' <br>
 * SubID: 7 <br>
 * Hardness: 1 <br>
 * Blast Resistance 5 <br>
 * </li>
 * <li>
 * NORTH_WEST:
 * Type name: 'North West' <br>
 * SubID: 6 <br>
 * Hardness: 1 <br>
 * Blast Resistance 5 <br>
 * </li>
 * <li>
 * WEST_NORTH_WEST:
 * Type name: 'West North West' <br>
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
 * WEST_SOUTH_WEST:
 * Type name: 'West South West' <br>
 * SubID: 3 <br>
 * Hardness: 1 <br>
 * Blast Resistance 5 <br>
 * </li>
 * <li>
 * SOUTH_WEST:
 * Type name: 'South West' <br>
 * SubID: 2 <br>
 * Hardness: 1 <br>
 * Blast Resistance 5 <br>
 * </li>
 * <li>
 * SOUTH_SOUTH_WEST:
 * Type name: 'South South West' <br>
 * SubID: 1 <br>
 * Hardness: 1 <br>
 * Blast Resistance 5 <br>
 * </li>
 * <li>
 * SOUTH:
 * Type name: 'South' <br>
 * SubID: 0 <br>
 * Hardness: 1 <br>
 * Blast Resistance 5 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class StandingSignMat extends SignBlockMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 16;

    public static final StandingSignMat STANDING_SIGN_SOUTH            = new StandingSignMat();
    public static final StandingSignMat STANDING_SIGN_SOUTH_SOUTH_WEST = new StandingSignMat(BlockFace.SOUTH_SOUTH_WEST);
    public static final StandingSignMat STANDING_SIGN_SOUTH_WEST       = new StandingSignMat(BlockFace.SOUTH_WEST);
    public static final StandingSignMat STANDING_SIGN_WEST_SOUTH_WEST  = new StandingSignMat(BlockFace.WEST_SOUTH_WEST);
    public static final StandingSignMat STANDING_SIGN_WEST             = new StandingSignMat(BlockFace.WEST);
    public static final StandingSignMat STANDING_SIGN_WEST_NORTH_WEST  = new StandingSignMat(BlockFace.WEST_NORTH_WEST);
    public static final StandingSignMat STANDING_SIGN_NORTH_WEST       = new StandingSignMat(BlockFace.NORTH_WEST);
    public static final StandingSignMat STANDING_SIGN_NORTH_NORTH_WEST = new StandingSignMat(BlockFace.NORTH_NORTH_WEST);
    public static final StandingSignMat STANDING_SIGN_NORTH            = new StandingSignMat(BlockFace.NORTH);
    public static final StandingSignMat STANDING_SIGN_NORTH_NORTH_EAST = new StandingSignMat(BlockFace.NORTH_NORTH_EAST);
    public static final StandingSignMat STANDING_SIGN_NORTH_EAST       = new StandingSignMat(BlockFace.NORTH_EAST);
    public static final StandingSignMat STANDING_SIGN_EAST_NORTH_EAST  = new StandingSignMat(BlockFace.EAST_NORTH_EAST);
    public static final StandingSignMat STANDING_SIGN_EAST             = new StandingSignMat(BlockFace.EAST);
    public static final StandingSignMat STANDING_SIGN_EAST_SOUTH_EAST  = new StandingSignMat(BlockFace.EAST_SOUTH_EAST);
    public static final StandingSignMat STANDING_SIGN_SOUTH_EAST       = new StandingSignMat(BlockFace.SOUTH_EAST);
    public static final StandingSignMat STANDING_SIGN_SOUTH_SOUTH_EAST = new StandingSignMat(BlockFace.SOUTH_SOUTH_EAST);

    private static final Map<String, StandingSignMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<StandingSignMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final BlockFace face;

    @SuppressWarnings("MagicNumber")
    protected StandingSignMat()
    {
        super("STANDING_SIGN", 63, "minecraft:standing_sign", 16, "SOUTH", (byte) 0x00, 1, 5);
        this.face = BlockFace.SOUTH;
    }

    protected StandingSignMat(final BlockFace face)
    {
        super(STANDING_SIGN_SOUTH.name(), STANDING_SIGN_SOUTH.ordinal(), STANDING_SIGN_SOUTH.getMinecraftId(), STANDING_SIGN_SOUTH.getMaxStack(), face.name(), combine(face), STANDING_SIGN_SOUTH.getHardness(), STANDING_SIGN_SOUTH.getBlastResistance());
        this.face = face;
    }

    protected StandingSignMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final float hardness, final float blastResistance)
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
    public StandingSignMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face));
    }

    @Override
    public StandingSignMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public StandingSignMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).toString();
    }

    @SuppressWarnings("MagicNumber")
    private static byte combine(final BlockFace face)
    {
        switch (face)
        {
            case SOUTH_SOUTH_WEST:
                return 0x1;
            case SOUTH_WEST:
                return 0x2;
            case WEST_SOUTH_WEST:
                return 0x3;
            case WEST:
                return 0x4;
            case WEST_NORTH_WEST:
                return 0x5;
            case NORTH_WEST:
                return 0x6;
            case NORTH_NORTH_WEST:
                return 0x7;
            case NORTH:
                return 0x8;
            case NORTH_NORTH_EAST:
                return 0x9;
            case NORTH_EAST:
                return 0xA;
            case EAST_NORTH_EAST:
                return 0xB;
            case EAST:
                return 0xC;
            case EAST_SOUTH_EAST:
                return 0xD;
            case SOUTH_EAST:
                return 0xE;
            case SOUTH_SOUTH_EAST:
                return 0xF;
            default:
                return 0x0;
        }
    }

    /**
     * Returns one of StandingSign sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of StandingSign or null
     */
    public static StandingSignMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of StandingSign sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of StandingSign or null
     */
    public static StandingSignMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of StandingSign sub-type based on {@link BlockFace}.
     * It will never return null;
     *
     * @param face facing of StandingSign
     *
     * @return sub-type of StandingSign
     */
    public static StandingSignMat getStandingSign(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final StandingSignMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public StandingSignMat[] types()
    {
        return StandingSignMat.standingSignTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static StandingSignMat[] standingSignTypes()
    {
        return byID.values(new StandingSignMat[byID.size()]);
    }

    static
    {
        StandingSignMat.register(STANDING_SIGN_SOUTH);
        StandingSignMat.register(STANDING_SIGN_SOUTH_SOUTH_WEST);
        StandingSignMat.register(STANDING_SIGN_SOUTH_WEST);
        StandingSignMat.register(STANDING_SIGN_WEST_SOUTH_WEST);
        StandingSignMat.register(STANDING_SIGN_WEST);
        StandingSignMat.register(STANDING_SIGN_WEST_NORTH_WEST);
        StandingSignMat.register(STANDING_SIGN_NORTH_WEST);
        StandingSignMat.register(STANDING_SIGN_NORTH_NORTH_WEST);
        StandingSignMat.register(STANDING_SIGN_NORTH);
        StandingSignMat.register(STANDING_SIGN_NORTH_NORTH_EAST);
        StandingSignMat.register(STANDING_SIGN_NORTH_EAST);
        StandingSignMat.register(STANDING_SIGN_EAST_NORTH_EAST);
        StandingSignMat.register(STANDING_SIGN_EAST);
        StandingSignMat.register(STANDING_SIGN_EAST_SOUTH_EAST);
        StandingSignMat.register(STANDING_SIGN_SOUTH_EAST);
        StandingSignMat.register(STANDING_SIGN_SOUTH_SOUTH_EAST);
    }
}
