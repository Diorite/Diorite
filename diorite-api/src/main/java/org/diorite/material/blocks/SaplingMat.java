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

import org.diorite.material.FuelMat;
import org.diorite.material.Material;
import org.diorite.material.WoodType;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Sapling' block material in minecraft. <br>
 * ID of block: 6 <br>
 * String ID of block: minecraft:sapling <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * DARK_OAK_OLDER:
 * Type name: 'Dark Oak Older' <br>
 * SubID: 13 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * ACACIA_OLDER:
 * Type name: 'Acacia Older' <br>
 * SubID: 12 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * JUNGLE_OLDER:
 * Type name: 'Jungle Older' <br>
 * SubID: 11 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * BIRCH_OLDER:
 * Type name: 'Birch Older' <br>
 * SubID: 10 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * SPRUCE_OLDER:
 * Type name: 'Spruce Older' <br>
 * SubID: 9 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * OAK_OLDER:
 * Type name: 'Oak Older' <br>
 * SubID: 8 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * DARK_OAK:
 * Type name: 'Dark Oak' <br>
 * SubID: 5 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * ACACIA:
 * Type name: 'Acacia' <br>
 * SubID: 4 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * JUNGLE:
 * Type name: 'Jungle' <br>
 * SubID: 3 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * BIRCH:
 * Type name: 'Birch' <br>
 * SubID: 2 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * SPRUCE:
 * Type name: 'Spruce' <br>
 * SubID: 1 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * OAK:
 * Type name: 'Oak' <br>
 * SubID: 0 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class SaplingMat extends WoodMat implements FuelMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 12;

    public static final SaplingMat SAPLING_OAK      = new SaplingMat();
    public static final SaplingMat SAPLING_SPRUCE   = new SaplingMat(WoodType.SPRUCE, SaplingStage.NEW);
    public static final SaplingMat SAPLING_BIRCH    = new SaplingMat(WoodType.BIRCH, SaplingStage.NEW);
    public static final SaplingMat SAPLING_JUNGLE   = new SaplingMat(WoodType.JUNGLE, SaplingStage.NEW);
    public static final SaplingMat SAPLING_ACACIA   = new SaplingMat(WoodType.ACACIA, SaplingStage.NEW);
    public static final SaplingMat SAPLING_DARK_OAK = new SaplingMat(WoodType.DARK_OAK, SaplingStage.NEW);

    public static final SaplingMat SAPLING_OAK_OLDER      = new SaplingMat(WoodType.OAK, SaplingStage.OLDER);
    public static final SaplingMat SAPLING_SPRUCE_OLDER   = new SaplingMat(WoodType.SPRUCE, SaplingStage.OLDER);
    public static final SaplingMat SAPLING_BIRCH_OLDER    = new SaplingMat(WoodType.BIRCH, SaplingStage.OLDER);
    public static final SaplingMat SAPLING_JUNGLE_OLDER   = new SaplingMat(WoodType.JUNGLE, SaplingStage.OLDER);
    public static final SaplingMat SAPLING_ACACIA_OLDER   = new SaplingMat(WoodType.ACACIA, SaplingStage.OLDER);
    public static final SaplingMat SAPLING_DARK_OAK_OLDER = new SaplingMat(WoodType.DARK_OAK, SaplingStage.OLDER);

    private static final Map<String, SaplingMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<SaplingMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final SaplingStage stage;

    protected SaplingMat()
    {
        super("SAPLING", 6, "minecraft:sapling", "OAK", (byte) 0x00, WoodType.OAK, 0, 0);
        this.stage = SaplingStage.NEW;
    }

    protected SaplingMat(final WoodType woodType, final SaplingStage stage)
    {
        super(SAPLING_OAK.name(), SAPLING_OAK.ordinal(), SAPLING_OAK.getMinecraftId(), woodType + (stage.getFlag() == 0 ? "" : "_" + stage.name()), combine(woodType, stage), woodType, SAPLING_OAK.getHardness(), SAPLING_OAK.getBlastResistance());
        this.stage = stage;
    }

    protected SaplingMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodType woodType, final SaplingStage stage, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType, hardness, blastResistance);
        this.stage = stage;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return this.getType(this.woodType, SaplingStage.NEW);
    }

    public SaplingMat getOtherStage()
    {
        return this.getType(this.woodType, (this.stage == SaplingStage.NEW) ? SaplingStage.OLDER : SaplingStage.NEW);
    }

    public SaplingMat normalize() // make sure that sappling is from first stage (NEW)
    {
        return this.getType(this.woodType, SaplingStage.NEW);
    }

    public SaplingStage getStage()
    {
        return this.stage;
    }

    public SaplingMat getType(final WoodType woodType, final SaplingStage stage)
    {
        return getByID(combine(woodType, stage));
    }

    @Override
    public WoodMat getWoodType(final WoodType woodType)
    {
        return getByID(combine(woodType, this.stage));
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("stage", this.stage).toString();
    }

    @Override
    public SaplingMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public SaplingMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isSolid()
    {
        return false;
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public int getFuelPower()
    {
        return 500;
    }

    public enum SaplingStage
    {
        NEW(0x0),
        OLDER(0x8);

        private final byte flag;

        SaplingStage(final int flag)
        {
            this.flag = (byte) flag;
        }

        public byte getFlag()
        {
            return this.flag;
        }
    }

    private static byte combine(final WoodType woodType, final SaplingStage stage)
    {
        byte result = woodType.getPlanksMeta();
        result |= stage.getFlag();
        return result;
    }

    /**
     * Returns one of Sapling sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Sapling or null
     */
    public static SaplingMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Sapling sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Sapling or null
     */
    public static SaplingMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static SaplingMat getSapling(final WoodType woodType, final SaplingStage stage)
    {
        return getByID(combine(woodType, stage));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final SaplingMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public SaplingMat[] types()
    {
        return SaplingMat.saplingTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static SaplingMat[] saplingTypes()
    {
        return byID.values(new SaplingMat[byID.size()]);
    }

    static
    {
        SaplingMat.register(SAPLING_OAK);
        SaplingMat.register(SAPLING_SPRUCE);
        SaplingMat.register(SAPLING_BIRCH);
        SaplingMat.register(SAPLING_JUNGLE);
        SaplingMat.register(SAPLING_ACACIA);
        SaplingMat.register(SAPLING_DARK_OAK);

        SaplingMat.register(SAPLING_OAK_OLDER);
        SaplingMat.register(SAPLING_SPRUCE_OLDER);
        SaplingMat.register(SAPLING_BIRCH_OLDER);
        SaplingMat.register(SAPLING_JUNGLE_OLDER);
        SaplingMat.register(SAPLING_ACACIA_OLDER);
        SaplingMat.register(SAPLING_DARK_OAK_OLDER);
    }
}
