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

import org.diorite.material.Material;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Lava" and all its subtypes.
 * <br>
 * NOTE: Will crash game when in inventory.
 */
@SuppressWarnings("JavaDoc")
public class LavaMat extends LiquidMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 16;

    public static final LavaMat LAVA_SOURCE  = new LavaMat(LiquidTypeMat.NORMAL);
    public static final LavaMat LAVA_STAGE_1 = new LavaMat(LiquidStageMat.STAGE_1, LiquidTypeMat.NORMAL);
    public static final LavaMat LAVA_STAGE_2 = new LavaMat(LiquidStageMat.STAGE_2, LiquidTypeMat.NORMAL);
    public static final LavaMat LAVA_STAGE_3 = new LavaMat(LiquidStageMat.STAGE_3, LiquidTypeMat.NORMAL);
    public static final LavaMat LAVA_STAGE_4 = new LavaMat(LiquidStageMat.STAGE_4, LiquidTypeMat.NORMAL);
    public static final LavaMat LAVA_STAGE_5 = new LavaMat(LiquidStageMat.STAGE_5, LiquidTypeMat.NORMAL);
    public static final LavaMat LAVA_STAGE_6 = new LavaMat(LiquidStageMat.STAGE_6, LiquidTypeMat.NORMAL);
    public static final LavaMat LAVA_STAGE_7 = new LavaMat(LiquidStageMat.STAGE_7, LiquidTypeMat.NORMAL);

    public static final LavaMat LAVA_SOURCE_FALLING  = new LavaMat(LiquidStageMat.SOURCE_FALLING, LiquidTypeMat.NORMAL);
    public static final LavaMat LAVA_STAGE_1_FALLING = new LavaMat(LiquidStageMat.STAGE_1_FALLING, LiquidTypeMat.NORMAL);
    public static final LavaMat LAVA_STAGE_2_FALLING = new LavaMat(LiquidStageMat.STAGE_2_FALLING, LiquidTypeMat.NORMAL);
    public static final LavaMat LAVA_STAGE_3_FALLING = new LavaMat(LiquidStageMat.STAGE_3_FALLING, LiquidTypeMat.NORMAL);
    public static final LavaMat LAVA_STAGE_4_FALLING = new LavaMat(LiquidStageMat.STAGE_4_FALLING, LiquidTypeMat.NORMAL);
    public static final LavaMat LAVA_STAGE_5_FALLING = new LavaMat(LiquidStageMat.STAGE_5_FALLING, LiquidTypeMat.NORMAL);
    public static final LavaMat LAVA_STAGE_6_FALLING = new LavaMat(LiquidStageMat.STAGE_6_FALLING, LiquidTypeMat.NORMAL);
    public static final LavaMat LAVA_STAGE_7_FALLING = new LavaMat(LiquidStageMat.STAGE_7_FALLING, LiquidTypeMat.NORMAL);


    public static final LavaMat LAVA_SOURCE_STILL  = new LavaStill();
    public static final LavaMat LAVA_STAGE_1_STILL = new LavaStill(LiquidStageMat.STAGE_1);
    public static final LavaMat LAVA_STAGE_2_STILL = new LavaStill(LiquidStageMat.STAGE_2);
    public static final LavaMat LAVA_STAGE_3_STILL = new LavaStill(LiquidStageMat.STAGE_3);
    public static final LavaMat LAVA_STAGE_4_STILL = new LavaStill(LiquidStageMat.STAGE_4);
    public static final LavaMat LAVA_STAGE_5_STILL = new LavaStill(LiquidStageMat.STAGE_5);
    public static final LavaMat LAVA_STAGE_6_STILL = new LavaStill(LiquidStageMat.STAGE_6);
    public static final LavaMat LAVA_STAGE_7_STILL = new LavaStill(LiquidStageMat.STAGE_7);

    public static final LavaMat LAVA_SOURCE_FALLING_STILL  = new LavaStill(LiquidStageMat.SOURCE_FALLING);
    public static final LavaMat LAVA_STAGE_1_FALLING_STILL = new LavaStill(LiquidStageMat.STAGE_1_FALLING);
    public static final LavaMat LAVA_STAGE_2_FALLING_STILL = new LavaStill(LiquidStageMat.STAGE_2_FALLING);
    public static final LavaMat LAVA_STAGE_3_FALLING_STILL = new LavaStill(LiquidStageMat.STAGE_3_FALLING);
    public static final LavaMat LAVA_STAGE_4_FALLING_STILL = new LavaStill(LiquidStageMat.STAGE_4_FALLING);
    public static final LavaMat LAVA_STAGE_5_FALLING_STILL = new LavaStill(LiquidStageMat.STAGE_5_FALLING);
    public static final LavaMat LAVA_STAGE_6_FALLING_STILL = new LavaStill(LiquidStageMat.STAGE_6_FALLING);
    public static final LavaMat LAVA_STAGE_7_FALLING_STILL = new LavaStill(LiquidStageMat.STAGE_7_FALLING);

    private static final Map<String, LavaMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES << 1, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<LavaMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES << 1, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected LavaMat(final LiquidTypeMat liquidType, final float hardness, final float blastResistance)
    {
        super("LAVA" + (liquidType.isStill() ? "_STILL" : ""), liquidType.isStill() ? 11 : 10, liquidType.isStill() ? "minecraft:flowing_lava" : "minecraft:lava", "SOURCE", LiquidStageMat.SOURCE, liquidType, hardness, blastResistance);
    }

    @SuppressWarnings("MagicNumber")
    protected LavaMat(final LiquidTypeMat liquidType)
    {
        super("LAVA" + (liquidType.isStill() ? "_STILL" : ""), liquidType.isStill() ? 11 : 10, liquidType.isStill() ? "minecraft:flowing_lava" : "minecraft:lava", "SOURCE", LiquidStageMat.SOURCE, liquidType, 100, liquidType.isStill() ? 500 : 0);
    }

    protected LavaMat(final LiquidStageMat stage, final LiquidTypeMat liquidType, final float hardness, final float blastResistance)
    {
        super(liquidType.isStill() ? LAVA_SOURCE_STILL.name() : LAVA_SOURCE.name(), LAVA_SOURCE.ordinal() + ((liquidType.isStill()) ? 1 : 0), liquidType.isNormal() ? LAVA_SOURCE.getMinecraftId() : LAVA_SOURCE_STILL.getMinecraftId(), LAVA_SOURCE.getMaxStack(), stage.name() + (liquidType == LiquidTypeMat.STILL ? "_STILL" : ""), stage, liquidType, hardness, blastResistance);
    }

    protected LavaMat(final LiquidStageMat stage, final LiquidTypeMat liquidType)
    {
        super(liquidType.isStill() ? LAVA_SOURCE_STILL.name() : LAVA_SOURCE.name(), LAVA_SOURCE.ordinal() + ((liquidType.isStill()) ? 1 : 0), liquidType.isNormal() ? LAVA_SOURCE.getMinecraftId() : LAVA_SOURCE_STILL.getMinecraftId(), LAVA_SOURCE.getMaxStack(), stage.name() + (liquidType == LiquidTypeMat.STILL ? "_STILL" : ""), stage, liquidType, liquidType.isStill() ? LAVA_SOURCE_STILL.getHardness() : LAVA_SOURCE.getHardness(), liquidType.isStill() ? LAVA_SOURCE_STILL.getBlastResistance() : LAVA_SOURCE.getBlastResistance());
    }

    protected LavaMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final LiquidStageMat stage, final LiquidTypeMat liquidType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, stage, liquidType, hardness, blastResistance);
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return LAVA_BUCKET;
    }

    @Override
    public LavaMat getStage(final LiquidStageMat stage)
    {
        return get(this.liquidType, stage);
    }

    @Override
    public LavaMat getLiquidType(final LiquidTypeMat type)
    {
        return get(type, this.stage);
    }

    @Override
    public LavaMat getNormalType()
    {
        return (LavaMat) super.getNormalType();
    }

    @Override
    public LavaMat getStillType()
    {
        return (LavaMat) super.getStillType();
    }

    @Override
    public LavaMat getOtherType()
    {
        return (LavaMat) super.getOtherType();
    }

    @Override
    public LavaMat nextStage()
    {
        return (LavaMat) super.nextStage();
    }

    @Override
    public LavaMat previousStage()
    {
        return (LavaMat) super.previousStage();
    }

    @Override
    public LavaMat falling()
    {
        return (LavaMat) super.falling();
    }

    @Override
    public LavaMat normal()
    {
        return (LavaMat) super.normal();
    }

    @Override
    public LavaMat switchFalling()
    {
        return (LavaMat) super.switchFalling();
    }

    @Override
    public LavaMat getType(final String name)
    {
        return byName.get(name);
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public LavaMat getType(final int id)
    {
        return this.isStill() ? byID.get((byte) (id + 16)) : byID.get((byte) id);
    }

    /**
     * Returns unique data value for flowing and still lava. Used by map.
     *
     * @return unique data value for flowing and still lava.
     */
    @SuppressWarnings("MagicNumber")
    protected byte getFixedDataValue()
    {
        return (byte) (this.getType() + ((this.liquidType.isStill()) ? 16 : 0));
    }

    /**
     * Returns one of Lava sub-type based on type and stage.
     *
     * @param type  type of liquid.
     * @param stage stage of liquid.
     *
     * @return sub-type of Lava or null.
     */
    @SuppressWarnings("MagicNumber")
    public static LavaMat get(final LiquidTypeMat type, final LiquidStageMat stage)
    {
        return getByID(stage.getDataValue() + ((type == LiquidTypeMat.STILL) ? 16 : 0));
    }

    /**
     * Returns one of Lava sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Lava or null
     */
    public static LavaMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Lava sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Lava or null
     */
    public static LavaMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final LavaMat element)
    {
        byID.put(element.getFixedDataValue(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public LavaMat[] types()
    {
        return LavaMat.lavaTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static LavaMat[] lavaTypes()
    {
        return byID.values(new LavaMat[byID.size()]);
    }

    /**
     * Helper class for second lava (lava still) ID
     */
    @SuppressWarnings("JavaDoc")
    public static class LavaStill extends LavaMat
    {
        @SuppressWarnings("MagicNumber")
        protected LavaStill()
        {
            super(LiquidTypeMat.STILL, 100, 500);
        }

        protected LavaStill(final LiquidStageMat stage)
        {
            super(stage, LiquidTypeMat.STILL, LAVA_SOURCE_STILL.getHardness(), LAVA_SOURCE_STILL.getBlastResistance());
        }

        protected LavaStill(final LiquidStageMat stage, final float hardness, final float blastResistance)
        {
            super(stage, LiquidTypeMat.STILL, hardness, blastResistance);
        }

        /**
         * Returns one of Lava sub-type based on sub-id, may return null
         *
         * @param id sub-type id
         *
         * @return sub-type of Lava or null
         */
        @SuppressWarnings("MagicNumber")
        public static LavaMat getByID(final int id)
        {
            return byID.get((byte) (id + 16));
        }
    }

    static
    {
        LavaMat.register(LAVA_SOURCE);
        LavaMat.register(LAVA_STAGE_1);
        LavaMat.register(LAVA_STAGE_2);
        LavaMat.register(LAVA_STAGE_3);
        LavaMat.register(LAVA_STAGE_4);
        LavaMat.register(LAVA_STAGE_5);
        LavaMat.register(LAVA_STAGE_6);
        LavaMat.register(LAVA_STAGE_7);
        LavaMat.register(LAVA_SOURCE_FALLING);
        LavaMat.register(LAVA_STAGE_1_FALLING);
        LavaMat.register(LAVA_STAGE_2_FALLING);
        LavaMat.register(LAVA_STAGE_3_FALLING);
        LavaMat.register(LAVA_STAGE_4_FALLING);
        LavaMat.register(LAVA_STAGE_5_FALLING);
        LavaMat.register(LAVA_STAGE_6_FALLING);
        LavaMat.register(LAVA_STAGE_7_FALLING);
        LavaMat.register(LAVA_SOURCE_STILL);
        LavaMat.register(LAVA_STAGE_1_STILL);
        LavaMat.register(LAVA_STAGE_2_STILL);
        LavaMat.register(LAVA_STAGE_3_STILL);
        LavaMat.register(LAVA_STAGE_4_STILL);
        LavaMat.register(LAVA_STAGE_5_STILL);
        LavaMat.register(LAVA_STAGE_6_STILL);
        LavaMat.register(LAVA_STAGE_7_STILL);
        LavaMat.register(LAVA_SOURCE_FALLING_STILL);
        LavaMat.register(LAVA_STAGE_1_FALLING_STILL);
        LavaMat.register(LAVA_STAGE_2_FALLING_STILL);
        LavaMat.register(LAVA_STAGE_3_FALLING_STILL);
        LavaMat.register(LAVA_STAGE_4_FALLING_STILL);
        LavaMat.register(LAVA_STAGE_5_FALLING_STILL);
        LavaMat.register(LAVA_STAGE_6_FALLING_STILL);
        LavaMat.register(LAVA_STAGE_7_FALLING_STILL);
    }
}
