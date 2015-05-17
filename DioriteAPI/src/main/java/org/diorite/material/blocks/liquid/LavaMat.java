package org.diorite.material.blocks.liquid;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Lava" and all its subtypes.
 */
public class LavaMat extends LiquidMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES       = 16;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE       = MagicNumbers.MATERIAL__LAVA__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS               = MagicNumbers.MATERIAL__LAVA__HARDNESS;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE_STILL = MagicNumbers.MATERIAL__LAVA_STILL__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS_STILL         = MagicNumbers.MATERIAL__LAVA_STILL__HARDNESS;

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
    protected LavaMat(final LiquidTypeMat liquidType)
    {
        super("LAVA" + (liquidType.isStill() ? "_STILL" : ""), liquidType.isStill() ? 11 : 10, liquidType.isStill() ? "minecraft:flowing_lava" : "minecraft:lava", "SOURCE", LiquidStageMat.SOURCE, liquidType);
    }

    protected LavaMat(final LiquidStageMat stage, final LiquidTypeMat liquidType)
    {
        super(liquidType.isStill() ? LAVA_SOURCE_STILL.name() : LAVA_SOURCE.name(), LAVA_SOURCE.getId() + ((liquidType.isStill()) ? 1 : 0), liquidType.isNormal() ? LAVA_SOURCE.getMinecraftId() : LAVA_SOURCE_STILL.getMinecraftId(), LAVA_SOURCE.getMaxStack(), stage.name() + (liquidType == LiquidTypeMat.STILL ? "_STILL" : ""), stage, liquidType);
    }

    protected LavaMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final LiquidStageMat stage, final LiquidTypeMat liquidType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, stage, liquidType);
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
    public LavaMat source()
    {
        return (LavaMat) super.source();
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

    @SuppressWarnings("MagicNumber")
    protected byte getFixedDataValue()
    {
        return (byte) (this.getType() + ((this.liquidType.isStill()) ? 16 : 0));
    }

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
        byName.put(element.name(), element);
    }

    /**
     * Helper class for second lava (lava still) ID
     */
    public static class LavaStill extends LavaMat
    {
        public LavaStill()
        {
            super(LiquidTypeMat.STILL);
        }

        public LavaStill(final LiquidStageMat stage)
        {
            super(stage, LiquidTypeMat.STILL);
        }

        @SuppressWarnings("MagicNumber")
        /**
         * Returns one of Lava sub-type based on sub-id, may return null
         * @param id sub-type id
         * @return sub-type of Lava or null
         */
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
