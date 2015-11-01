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
import org.diorite.material.RotatableMat;
import org.diorite.material.RotateAxisMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Anvil' block material in minecraft. <br>
 * ID of block: 145 <br>
 * String ID of block: minecraft:anvil <br>
 * Hardness: 5 <br>
 * Blast Resistance 6000 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * SOUTH_NORTH_VERY_DAMAGED:
 * Type name: 'South North Very Damaged' <br>
 * SubID: 11 <br>
 * Hardness: 5 <br>
 * Blast Resistance 6000 <br>
 * </li>
 * <li>
 * WEST_EAST_VERY_DAMAGED:
 * Type name: 'West East Very Damaged' <br>
 * SubID: 10 <br>
 * Hardness: 5 <br>
 * Blast Resistance 6000 <br>
 * </li>
 * <li>
 * EAST_WEST_VERY_DAMAGED:
 * Type name: 'East West Very Damaged' <br>
 * SubID: 9 <br>
 * Hardness: 5 <br>
 * Blast Resistance 6000 <br>
 * </li>
 * <li>
 * NORTH_SOUTH_VERY_DAMAGED:
 * Type name: 'North South Very Damaged' <br>
 * SubID: 8 <br>
 * Hardness: 5 <br>
 * Blast Resistance 6000 <br>
 * </li>
 * <li>
 * SOUTH_NORTH_SLIGHTLY_DAMAGED:
 * Type name: 'South North Slightly Damaged' <br>
 * SubID: 7 <br>
 * Hardness: 5 <br>
 * Blast Resistance 6000 <br>
 * </li>
 * <li>
 * WEST_EAST_SLIGHTLY_DAMAGED:
 * Type name: 'West East Slightly Damaged' <br>
 * SubID: 6 <br>
 * Hardness: 5 <br>
 * Blast Resistance 6000 <br>
 * </li>
 * <li>
 * EAST_WEST_SLIGHTLY_DAMAGED:
 * Type name: 'East West Slightly Damaged' <br>
 * SubID: 5 <br>
 * Hardness: 5 <br>
 * Blast Resistance 6000 <br>
 * </li>
 * <li>
 * NORTH_SOUTH_SLIGHTLY_DAMAGED:
 * Type name: 'North South Slightly Damaged' <br>
 * SubID: 4 <br>
 * Hardness: 5 <br>
 * Blast Resistance 6000 <br>
 * </li>
 * <li>
 * SOUTH_NORTH_NEW:
 * Type name: 'South North New' <br>
 * SubID: 3 <br>
 * Hardness: 5 <br>
 * Blast Resistance 6000 <br>
 * </li>
 * <li>
 * WEST_EAST_NEW:
 * Type name: 'West East New' <br>
 * SubID: 2 <br>
 * Hardness: 5 <br>
 * Blast Resistance 6000 <br>
 * </li>
 * <li>
 * EAST_WEST_NEW:
 * Type name: 'East West New' <br>
 * SubID: 1 <br>
 * Hardness: 5 <br>
 * Blast Resistance 6000 <br>
 * </li>
 * <li>
 * NORTH_SOUTH_NEW:
 * Type name: 'North South New' <br>
 * SubID: 0 <br>
 * Hardness: 5 <br>
 * Blast Resistance 6000 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class AnvilMat extends BlockMaterialData implements RotatableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 12;

    public static final AnvilMat ANVIL_NORTH_SOUTH_NEW              = new AnvilMat();
    public static final AnvilMat ANVIL_EAST_WEST_NEW                = new AnvilMat(AnvilBlockDamage.NEW, RotateAxisMat.EAST_WEST, false);
    public static final AnvilMat ANVIL_SOUTH_NORTH_NEW              = new AnvilMat(AnvilBlockDamage.NEW, RotateAxisMat.NORTH_SOUTH, true);
    public static final AnvilMat ANVIL_WEST_EAST_NEW                = new AnvilMat(AnvilBlockDamage.NEW, RotateAxisMat.EAST_WEST, true);
    public static final AnvilMat ANVIL_NORTH_SOUTH_SLIGHTLY_DAMAGED = new AnvilMat(AnvilBlockDamage.SLIGHTLY_DAMAGED, RotateAxisMat.NORTH_SOUTH, false);
    public static final AnvilMat ANVIL_EAST_WEST_SLIGHTLY_DAMAGED   = new AnvilMat(AnvilBlockDamage.SLIGHTLY_DAMAGED, RotateAxisMat.EAST_WEST, false);
    public static final AnvilMat ANVIL_WEST_EAST_SLIGHTLY_DAMAGED   = new AnvilMat(AnvilBlockDamage.SLIGHTLY_DAMAGED, RotateAxisMat.EAST_WEST, true);
    public static final AnvilMat ANVIL_SOUTH_NORTH_SLIGHTLY_DAMAGED = new AnvilMat(AnvilBlockDamage.SLIGHTLY_DAMAGED, RotateAxisMat.NORTH_SOUTH, true);
    public static final AnvilMat ANVIL_NORTH_SOUTH_VERY_DAMAGED     = new AnvilMat(AnvilBlockDamage.VERY_DAMAGED, RotateAxisMat.NORTH_SOUTH, false);
    public static final AnvilMat ANVIL_EAST_WEST_VERY_DAMAGED       = new AnvilMat(AnvilBlockDamage.VERY_DAMAGED, RotateAxisMat.EAST_WEST, false);
    public static final AnvilMat ANVIL_WEST_EAST_VERY_DAMAGED       = new AnvilMat(AnvilBlockDamage.VERY_DAMAGED, RotateAxisMat.EAST_WEST, true);
    public static final AnvilMat ANVIL_SOUTH_NORTH_VERY_DAMAGED     = new AnvilMat(AnvilBlockDamage.VERY_DAMAGED, RotateAxisMat.NORTH_SOUTH, true);

    private static final Map<String, AnvilMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<AnvilMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final AnvilBlockDamage damage;
    protected final RotateAxisMat    axis;
    protected final boolean          rotated;

    @SuppressWarnings("MagicNumber")
    protected AnvilMat()
    {
        super("ANVIL", 145, "minecraft:anvil", "NORTH_SOUTH_NEW", (byte) 0x00, 5, 6_000);
        this.damage = AnvilBlockDamage.NEW;
        this.axis = RotateAxisMat.NORTH_SOUTH;
        this.rotated = false;
    }

    protected AnvilMat(final AnvilBlockDamage damage, final RotateAxisMat axis, final boolean rotated)
    {
        super(ANVIL_NORTH_SOUTH_NEW.name(), ANVIL_NORTH_SOUTH_NEW.ordinal(), ANVIL_NORTH_SOUTH_NEW.getMinecraftId(), combineName(damage, axis, rotated), combine(damage, axis, rotated), ANVIL_NORTH_SOUTH_NEW.getHardness(), ANVIL_NORTH_SOUTH_NEW.getBlastResistance());
        this.damage = damage;
        this.axis = axis;
        this.rotated = rotated;
    }

    protected AnvilMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final AnvilBlockDamage damage, final RotateAxisMat axis, final boolean rotated, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.damage = damage;
        this.axis = axis;
        this.rotated = rotated;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.ANVIL;
    }

    private static String combineName(final AnvilBlockDamage damage, final RotateAxisMat axis, final boolean rotated)
    {
        final String result;
        switch (axis)
        {
            case EAST_WEST:
                if (rotated)
                {
                    result = "WEST_EAST";
                }
                else
                {
                    result = axis.name();
                }
                break;
            case NORTH_SOUTH:
                if (rotated)
                {
                    result = "SOUTH_NORTH";
                    break;
                }
            default:
                result = RotateAxisMat.NORTH_SOUTH.name();
        }
        return result + "_" + damage.name();
    }


    private static byte combine(final AnvilBlockDamage damage, final RotateAxisMat axis, final boolean rotated)
    {
        byte result = damage.getFlag();
        switch (axis)
        {
            case EAST_WEST:
                if (rotated)
                {
                    result |= 2;
                }
                else
                {
                    result |= 1;
                }
                break;
            case NORTH_SOUTH:
                if (rotated)
                {
                    result |= 3;
                }
                break;
            default:
                break;
        }
        return result;
    }

    @Override
    public RotateAxisMat getRotateAxis()
    {
        return this.axis;
    }

    @Override
    public AnvilMat getRotated(final RotateAxisMat axis)
    {
        return getByID(combine(this.damage, axis, this.rotated));
    }

    /**
     * @return true if block is mirrored verion of axis, like SOUTH_NORTH and NORTH_SOUTH
     */
    public boolean isRotated()
    {
        return this.rotated;
    }

    /**
     * Returns one of Anvil sub-type based on rotated state.
     *
     * @param rotated if anvil should be rotated, so NORTH_SOUTH {@literal ->} SOUTH_NORTH
     *
     * @return sub-type of Anvil
     */
    public AnvilMat getRotated(final boolean rotated)
    {
        return getByID(combine(this.damage, this.axis, rotated));
    }

    /**
     * @return {@link AnvilMat.AnvilBlockDamage} stage of damage.
     */
    public AnvilBlockDamage getDamage()
    {
        return this.damage;
    }

    /**
     * Returns one of Anvil sub-type based on {@link AnvilMat.AnvilBlockDamage} stage.
     *
     * @param damage damage stage of Anvil.
     *
     * @return sub-type of Anvil
     */
    public AnvilMat getDamage(final AnvilBlockDamage damage)
    {
        return getByID(combine(damage, this.axis, this.rotated));
    }

    @Override
    public AnvilMat getBlockFacing(final BlockFace face)
    {
        switch (face)
        {
            case NORTH:
                return getByID(combine(this.damage, RotateAxisMat.NORTH_SOUTH, false));
            case SOUTH:
                return getByID(combine(this.damage, RotateAxisMat.NORTH_SOUTH, true));
            case EAST:
                return getByID(combine(this.damage, RotateAxisMat.EAST_WEST, false));
            case WEST:
                return getByID(combine(this.damage, RotateAxisMat.EAST_WEST, true));
            default:
                return getByID(combine(this.damage, RotateAxisMat.NORTH_SOUTH, this.rotated));
        }
    }

    @Override
    public AnvilMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public AnvilMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of Anvil sub-type based on {@link AnvilMat.AnvilBlockDamage} stage, {@link RotateAxisMat} and rotated state.
     *
     * @param damage  damage stage of Anvil.
     * @param axis    axis of placment.
     * @param rotated if anvil should be rotated, so NORTH_SOUTH {@literal ->} SOUTH_NORTH
     *
     * @return sub-type of Anvil
     */
    public AnvilMat getType(final AnvilBlockDamage damage, final RotateAxisMat axis, final boolean rotated)
    {
        return getByID(combine(damage, axis, rotated));
    }

    /**
     * Returns one of Anvil sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Anvil or null
     */
    public static AnvilMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Anvil sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Anvil or null
     */
    public static AnvilMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Anvil sub-type based on {@link AnvilMat.AnvilBlockDamage} stage, {@link RotateAxisMat} and rotated state.
     * It will never return null.
     *
     * @param damage  damage stage of Anvil.
     * @param axis    axis of placment.
     * @param rotated if anvil should be rotated, so NORTH_SOUTH {@literal ->} SOUTH_NORTH
     *
     * @return sub-type of Anvil
     */
    public static AnvilMat getAnvil(final AnvilBlockDamage damage, final RotateAxisMat axis, final boolean rotated)
    {
        return getByID(combine(damage, axis, rotated));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final AnvilMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public AnvilMat[] types()
    {
        return AnvilMat.anvilTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static AnvilMat[] anvilTypes()
    {
        return byID.values(new AnvilMat[byID.size()]);
    }

    static
    {
        AnvilMat.register(ANVIL_NORTH_SOUTH_NEW);
        AnvilMat.register(ANVIL_EAST_WEST_NEW);
        AnvilMat.register(ANVIL_SOUTH_NORTH_NEW);
        AnvilMat.register(ANVIL_WEST_EAST_NEW);
        AnvilMat.register(ANVIL_NORTH_SOUTH_SLIGHTLY_DAMAGED);
        AnvilMat.register(ANVIL_EAST_WEST_SLIGHTLY_DAMAGED);
        AnvilMat.register(ANVIL_WEST_EAST_SLIGHTLY_DAMAGED);
        AnvilMat.register(ANVIL_SOUTH_NORTH_SLIGHTLY_DAMAGED);
        AnvilMat.register(ANVIL_NORTH_SOUTH_VERY_DAMAGED);
        AnvilMat.register(ANVIL_EAST_WEST_VERY_DAMAGED);
        AnvilMat.register(ANVIL_WEST_EAST_VERY_DAMAGED);
        AnvilMat.register(ANVIL_SOUTH_NORTH_VERY_DAMAGED);
    }

    /**
     * Enum to representing anvil damage stage.
     */
    public enum AnvilBlockDamage
    {
        /**
         * New anvil.
         */
        NEW(0x0),
        /**
         * Slightly damaged anvil.
         */
        SLIGHTLY_DAMAGED(0x4),
        /**
         * Very damaged anvil.
         */
        VERY_DAMAGED(0x8);

        private final byte flag;

        AnvilBlockDamage(final int i)
        {
            this.flag = (byte) i;
        }

        /**
         * @return byte-flag for this damage.
         */
        public byte getFlag()
        {
            return this.flag;
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("damage", this.damage).append("axis", this.axis).append("rotated", this.rotated).toString();
    }
}
