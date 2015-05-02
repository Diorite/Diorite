package org.diorite.material.blocks.liquid;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Water" and all its subtypes.
 */
public class WaterMat extends LiquidMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES       = 16;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE       = MagicNumbers.MATERIAL__WATER__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS               = MagicNumbers.MATERIAL__WATER__HARDNESS;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE_STILL = MagicNumbers.MATERIAL__WATER_STILL__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS_STILL         = MagicNumbers.MATERIAL__WATER_STILL__HARDNESS;

    public static final WaterMat WATER_SOURCE  = new WaterMat(LiquidTypeMat.NORMAL);
    public static final WaterMat WATER_STAGE_1 = new WaterMat(LiquidStageMat.STAGE_1, LiquidTypeMat.NORMAL);
    public static final WaterMat WATER_STAGE_2 = new WaterMat(LiquidStageMat.STAGE_2, LiquidTypeMat.NORMAL);
    public static final WaterMat WATER_STAGE_3 = new WaterMat(LiquidStageMat.STAGE_3, LiquidTypeMat.NORMAL);
    public static final WaterMat WATER_STAGE_4 = new WaterMat(LiquidStageMat.STAGE_4, LiquidTypeMat.NORMAL);
    public static final WaterMat WATER_STAGE_5 = new WaterMat(LiquidStageMat.STAGE_5, LiquidTypeMat.NORMAL);
    public static final WaterMat WATER_STAGE_6 = new WaterMat(LiquidStageMat.STAGE_6, LiquidTypeMat.NORMAL);
    public static final WaterMat WATER_STAGE_7 = new WaterMat(LiquidStageMat.STAGE_7, LiquidTypeMat.NORMAL);

    public static final WaterMat WATER_SOURCE_FALLING  = new WaterMat(LiquidStageMat.SOURCE_FALLING, LiquidTypeMat.NORMAL);
    public static final WaterMat WATER_STAGE_1_FALLING = new WaterMat(LiquidStageMat.STAGE_1_FALLING, LiquidTypeMat.NORMAL);
    public static final WaterMat WATER_STAGE_2_FALLING = new WaterMat(LiquidStageMat.STAGE_2_FALLING, LiquidTypeMat.NORMAL);
    public static final WaterMat WATER_STAGE_3_FALLING = new WaterMat(LiquidStageMat.STAGE_3_FALLING, LiquidTypeMat.NORMAL);
    public static final WaterMat WATER_STAGE_4_FALLING = new WaterMat(LiquidStageMat.STAGE_4_FALLING, LiquidTypeMat.NORMAL);
    public static final WaterMat WATER_STAGE_5_FALLING = new WaterMat(LiquidStageMat.STAGE_5_FALLING, LiquidTypeMat.NORMAL);
    public static final WaterMat WATER_STAGE_6_FALLING = new WaterMat(LiquidStageMat.STAGE_6_FALLING, LiquidTypeMat.NORMAL);
    public static final WaterMat WATER_STAGE_7_FALLING = new WaterMat(LiquidStageMat.STAGE_7_FALLING, LiquidTypeMat.NORMAL);


    public static final WaterMat WATER_SOURCE_STILL  = new WaterStill();
    public static final WaterMat WATER_STAGE_1_STILL = new WaterStill(LiquidStageMat.STAGE_1);
    public static final WaterMat WATER_STAGE_2_STILL = new WaterStill(LiquidStageMat.STAGE_2);
    public static final WaterMat WATER_STAGE_3_STILL = new WaterStill(LiquidStageMat.STAGE_3);
    public static final WaterMat WATER_STAGE_4_STILL = new WaterStill(LiquidStageMat.STAGE_4);
    public static final WaterMat WATER_STAGE_5_STILL = new WaterStill(LiquidStageMat.STAGE_5);
    public static final WaterMat WATER_STAGE_6_STILL = new WaterStill(LiquidStageMat.STAGE_6);
    public static final WaterMat WATER_STAGE_7_STILL = new WaterStill(LiquidStageMat.STAGE_7);

    public static final WaterMat WATER_SOURCE_FALLING_STILL  = new WaterStill(LiquidStageMat.SOURCE_FALLING);
    public static final WaterMat WATER_STAGE_1_FALLING_STILL = new WaterStill(LiquidStageMat.STAGE_1_FALLING);
    public static final WaterMat WATER_STAGE_2_FALLING_STILL = new WaterStill(LiquidStageMat.STAGE_2_FALLING);
    public static final WaterMat WATER_STAGE_3_FALLING_STILL = new WaterStill(LiquidStageMat.STAGE_3_FALLING);
    public static final WaterMat WATER_STAGE_4_FALLING_STILL = new WaterStill(LiquidStageMat.STAGE_4_FALLING);
    public static final WaterMat WATER_STAGE_5_FALLING_STILL = new WaterStill(LiquidStageMat.STAGE_5_FALLING);
    public static final WaterMat WATER_STAGE_6_FALLING_STILL = new WaterStill(LiquidStageMat.STAGE_6_FALLING);
    public static final WaterMat WATER_STAGE_7_FALLING_STILL = new WaterStill(LiquidStageMat.STAGE_7_FALLING);

    private static final Map<String, WaterMat>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES << 1, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<WaterMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES << 1, SMALL_LOAD_FACTOR);

    protected WaterMat(final LiquidTypeMat liquidType)
    {
        super("WATER" + (liquidType.isStill() ? "_STILL" : ""), liquidType.isStill() ? 9 : 8, liquidType.isStill() ? "minecraft:flowing_water" : "minecraft:water", "SOURCE", LiquidStageMat.SOURCE, liquidType);
    }

    protected WaterMat(final LiquidStageMat stage, final LiquidTypeMat liquidType)
    {
        super(liquidType.isStill() ? WATER_SOURCE_STILL.name() : WATER_SOURCE.name(), WATER_SOURCE.getId() + ((liquidType == LiquidTypeMat.STILL) ? 1 : 0), liquidType.isNormal() ? WATER_SOURCE.getMinecraftId() : WATER_SOURCE_STILL.getMinecraftId(), WATER_SOURCE.getMaxStack(), stage.name() + (liquidType == LiquidTypeMat.STILL ? "_STILL" : ""), stage, liquidType);
    }

    public WaterMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final LiquidStageMat stage, final LiquidTypeMat liquidType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, stage, liquidType);
    }

    @Override
    public WaterMat getStage(final LiquidStageMat stage)
    {
        return get(this.liquidType, stage);
    }

    @Override
    public WaterMat getLiquidType(final LiquidTypeMat type)
    {
        return get(type, this.stage);
    }

    @Override
    public WaterMat getNormalType()
    {
        return (WaterMat) super.getNormalType();
    }

    @Override
    public WaterMat getStillType()
    {
        return (WaterMat) super.getStillType();
    }

    @Override
    public WaterMat getOtherType()
    {
        return (WaterMat) super.getOtherType();
    }

    @Override
    public WaterMat nextStage()
    {
        return (WaterMat) super.nextStage();
    }

    @Override
    public WaterMat previousStage()
    {
        return (WaterMat) super.previousStage();
    }

    @Override
    public WaterMat falling()
    {
        return (WaterMat) super.falling();
    }

    @Override
    public WaterMat normal()
    {
        return (WaterMat) super.normal();
    }

    @Override
    public WaterMat switchFalling()
    {
        return (WaterMat) super.switchFalling();
    }

    @Override
    public WaterMat source()
    {
        return (WaterMat) super.source();
    }

    @Override
    public float getBlastResistance()
    {
        return this.isStill() ? BLAST_RESISTANCE_STILL : BLAST_RESISTANCE;
    }

    @Override
    public float getHardness()
    {
        return this.isStill() ? HARDNESS_STILL : HARDNESS;
    }

    @Override
    public WaterMat getType(final String name)
    {
        return byName.get(name);
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public WaterMat getType(final int id)
    {
        return this.isStill() ? byID.get((byte) (id + 16)) : byID.get((byte) id);
    }

    @SuppressWarnings("MagicNumber")
    protected byte getFixedDataValue()
    {
        return (byte) (this.getType() + ((this.liquidType.isStill()) ? 16 : 0));
    }

    @SuppressWarnings("MagicNumber")
    public static WaterMat get(final LiquidTypeMat type, final LiquidStageMat stage)
    {
        return getByID(stage.getDataValue() + ((type.isStill()) ? 16 : 0));
    }

    /**
     * Returns one of Water sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Water or null
     */
    public static WaterMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Water sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Water or null
     */
    public static WaterMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final WaterMat element)
    {
        byID.put(element.getFixedDataValue(), element);
        byName.put(element.name(), element);
    }

    /**
     * Helper class for second water (water still) ID
     */
    public static class WaterStill extends WaterMat
    {
        public WaterStill()
        {
            super(LiquidTypeMat.STILL);
        }

        public WaterStill(final LiquidStageMat stage)
        {
            super(stage, LiquidTypeMat.STILL);
        }

        @SuppressWarnings("MagicNumber")
        /**
         * Returns one of Water sub-type based on sub-id, may return null
         * @param id sub-type id
         * @return sub-type of Water or null
         */
        public static WaterMat getByID(final int id)
        {
            return byID.get((byte) (id + 16));
        }
    }

    static
    {
        WaterMat.register(WATER_SOURCE);
        WaterMat.register(WATER_STAGE_1);
        WaterMat.register(WATER_STAGE_2);
        WaterMat.register(WATER_STAGE_3);
        WaterMat.register(WATER_STAGE_4);
        WaterMat.register(WATER_STAGE_5);
        WaterMat.register(WATER_STAGE_6);
        WaterMat.register(WATER_STAGE_7);
        WaterMat.register(WATER_SOURCE_FALLING);
        WaterMat.register(WATER_STAGE_1_FALLING);
        WaterMat.register(WATER_STAGE_2_FALLING);
        WaterMat.register(WATER_STAGE_3_FALLING);
        WaterMat.register(WATER_STAGE_4_FALLING);
        WaterMat.register(WATER_STAGE_5_FALLING);
        WaterMat.register(WATER_STAGE_6_FALLING);
        WaterMat.register(WATER_STAGE_7_FALLING);
        WaterMat.register(WATER_SOURCE_STILL);
        WaterMat.register(WATER_STAGE_1_STILL);
        WaterMat.register(WATER_STAGE_2_STILL);
        WaterMat.register(WATER_STAGE_3_STILL);
        WaterMat.register(WATER_STAGE_4_STILL);
        WaterMat.register(WATER_STAGE_5_STILL);
        WaterMat.register(WATER_STAGE_6_STILL);
        WaterMat.register(WATER_STAGE_7_STILL);
        WaterMat.register(WATER_SOURCE_FALLING_STILL);
        WaterMat.register(WATER_STAGE_1_FALLING_STILL);
        WaterMat.register(WATER_STAGE_2_FALLING_STILL);
        WaterMat.register(WATER_STAGE_3_FALLING_STILL);
        WaterMat.register(WATER_STAGE_4_FALLING_STILL);
        WaterMat.register(WATER_STAGE_5_FALLING_STILL);
        WaterMat.register(WATER_STAGE_6_FALLING_STILL);
        WaterMat.register(WATER_STAGE_7_FALLING_STILL);
    }
}
