package org.diorite.material.blocks.liquid;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Water" and all its subtypes.
 */
public class Water extends Liquid
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

    public static final Water WATER_SOURCE  = new Water(LiquidType.NORMAL);
    public static final Water WATER_STAGE_1 = new Water(LiquidStage.STAGE_1, LiquidType.NORMAL);
    public static final Water WATER_STAGE_2 = new Water(LiquidStage.STAGE_2, LiquidType.NORMAL);
    public static final Water WATER_STAGE_3 = new Water(LiquidStage.STAGE_3, LiquidType.NORMAL);
    public static final Water WATER_STAGE_4 = new Water(LiquidStage.STAGE_4, LiquidType.NORMAL);
    public static final Water WATER_STAGE_5 = new Water(LiquidStage.STAGE_5, LiquidType.NORMAL);
    public static final Water WATER_STAGE_6 = new Water(LiquidStage.STAGE_6, LiquidType.NORMAL);
    public static final Water WATER_STAGE_7 = new Water(LiquidStage.STAGE_7, LiquidType.NORMAL);

    public static final Water WATER_SOURCE_FALLING  = new Water(LiquidStage.SOURCE_FALLING, LiquidType.NORMAL);
    public static final Water WATER_STAGE_1_FALLING = new Water(LiquidStage.STAGE_1_FALLING, LiquidType.NORMAL);
    public static final Water WATER_STAGE_2_FALLING = new Water(LiquidStage.STAGE_2_FALLING, LiquidType.NORMAL);
    public static final Water WATER_STAGE_3_FALLING = new Water(LiquidStage.STAGE_3_FALLING, LiquidType.NORMAL);
    public static final Water WATER_STAGE_4_FALLING = new Water(LiquidStage.STAGE_4_FALLING, LiquidType.NORMAL);
    public static final Water WATER_STAGE_5_FALLING = new Water(LiquidStage.STAGE_5_FALLING, LiquidType.NORMAL);
    public static final Water WATER_STAGE_6_FALLING = new Water(LiquidStage.STAGE_6_FALLING, LiquidType.NORMAL);
    public static final Water WATER_STAGE_7_FALLING = new Water(LiquidStage.STAGE_7_FALLING, LiquidType.NORMAL);


    public static final Water WATER_SOURCE_STILL  = new WaterStill();
    public static final Water WATER_STAGE_1_STILL = new WaterStill(LiquidStage.STAGE_1);
    public static final Water WATER_STAGE_2_STILL = new WaterStill(LiquidStage.STAGE_2);
    public static final Water WATER_STAGE_3_STILL = new WaterStill(LiquidStage.STAGE_3);
    public static final Water WATER_STAGE_4_STILL = new WaterStill(LiquidStage.STAGE_4);
    public static final Water WATER_STAGE_5_STILL = new WaterStill(LiquidStage.STAGE_5);
    public static final Water WATER_STAGE_6_STILL = new WaterStill(LiquidStage.STAGE_6);
    public static final Water WATER_STAGE_7_STILL = new WaterStill(LiquidStage.STAGE_7);

    public static final Water WATER_SOURCE_FALLING_STILL  = new WaterStill(LiquidStage.SOURCE_FALLING);
    public static final Water WATER_STAGE_1_FALLING_STILL = new WaterStill(LiquidStage.STAGE_1_FALLING);
    public static final Water WATER_STAGE_2_FALLING_STILL = new WaterStill(LiquidStage.STAGE_2_FALLING);
    public static final Water WATER_STAGE_3_FALLING_STILL = new WaterStill(LiquidStage.STAGE_3_FALLING);
    public static final Water WATER_STAGE_4_FALLING_STILL = new WaterStill(LiquidStage.STAGE_4_FALLING);
    public static final Water WATER_STAGE_5_FALLING_STILL = new WaterStill(LiquidStage.STAGE_5_FALLING);
    public static final Water WATER_STAGE_6_FALLING_STILL = new WaterStill(LiquidStage.STAGE_6_FALLING);
    public static final Water WATER_STAGE_7_FALLING_STILL = new WaterStill(LiquidStage.STAGE_7_FALLING);

    private static final Map<String, Water>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES << 1, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Water> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES << 1, SMALL_LOAD_FACTOR);

    protected Water(final LiquidType liquidType)
    {
        super("WATER" + (liquidType.isStill() ? "_STILL" : ""), liquidType.isStill() ? 9 : 8, liquidType.isStill() ? "minecraft:flowing_water" : "minecraft:water", "SOURCE", LiquidStage.SOURCE, liquidType);
    }

    public Water(final LiquidStage stage, final LiquidType liquidType)
    {
        super(liquidType.isStill() ? WATER_SOURCE_STILL.name() : WATER_SOURCE.name(), WATER_SOURCE.getId() + ((liquidType == LiquidType.STILL) ? 1 : 0), liquidType.isNormal() ? WATER_SOURCE.getMinecraftId() : WATER_SOURCE_STILL.getMinecraftId(), WATER_SOURCE.getMaxStack(), stage.name() + (liquidType == LiquidType.STILL ? "_STILL" : ""), stage, liquidType);
    }

    @Override
    public Water getStage(final LiquidStage stage)
    {
        return get(this.liquidType, stage);
    }

    @Override
    public Water getLiquidType(final LiquidType type)
    {
        return get(type, this.stage);
    }

    @Override
    public Water getNormalType()
    {
        return (Water) super.getNormalType();
    }

    @Override
    public Water getStillType()
    {
        return (Water) super.getStillType();
    }

    @Override
    public Water getOtherType()
    {
        return (Water) super.getOtherType();
    }

    @Override
    public Water nextStage()
    {
        return (Water) super.nextStage();
    }

    @Override
    public Water previousStage()
    {
        return (Water) super.previousStage();
    }

    @Override
    public Water falling()
    {
        return (Water) super.falling();
    }

    @Override
    public Water normal()
    {
        return (Water) super.normal();
    }

    @Override
    public Water switchFalling()
    {
        return (Water) super.switchFalling();
    }

    @Override
    public Water source()
    {
        return (Water) super.source();
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
    public Water getType(final String name)
    {
        return byName.get(name);
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public Water getType(final int id)
    {
        return this.isStill() ? byID.get((byte) (id + 16)) : byID.get((byte) id);
    }

    @SuppressWarnings("MagicNumber")
    protected byte getFixedDataValue()
    {
        return (byte) (this.getType() + ((this.liquidType.isStill()) ? 16 : 0));
    }

    @SuppressWarnings("MagicNumber")
    public static Water get(final LiquidType type, final LiquidStage stage)
    {
        return getByID(stage.getDataValue() + ((type.isStill()) ? 16 : 0));
    }

    public static Water getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Water getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Water element)
    {
        byID.put(element.getFixedDataValue(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Water.register(WATER_SOURCE);
        Water.register(WATER_STAGE_1);
        Water.register(WATER_STAGE_2);
        Water.register(WATER_STAGE_3);
        Water.register(WATER_STAGE_4);
        Water.register(WATER_STAGE_5);
        Water.register(WATER_STAGE_6);
        Water.register(WATER_STAGE_7);
        Water.register(WATER_SOURCE_FALLING);
        Water.register(WATER_STAGE_1_FALLING);
        Water.register(WATER_STAGE_2_FALLING);
        Water.register(WATER_STAGE_3_FALLING);
        Water.register(WATER_STAGE_4_FALLING);
        Water.register(WATER_STAGE_5_FALLING);
        Water.register(WATER_STAGE_6_FALLING);
        Water.register(WATER_STAGE_7_FALLING);
        Water.register(WATER_SOURCE_STILL);
        Water.register(WATER_STAGE_1_STILL);
        Water.register(WATER_STAGE_2_STILL);
        Water.register(WATER_STAGE_3_STILL);
        Water.register(WATER_STAGE_4_STILL);
        Water.register(WATER_STAGE_5_STILL);
        Water.register(WATER_STAGE_6_STILL);
        Water.register(WATER_STAGE_7_STILL);
        Water.register(WATER_SOURCE_FALLING_STILL);
        Water.register(WATER_STAGE_1_FALLING_STILL);
        Water.register(WATER_STAGE_2_FALLING_STILL);
        Water.register(WATER_STAGE_3_FALLING_STILL);
        Water.register(WATER_STAGE_4_FALLING_STILL);
        Water.register(WATER_STAGE_5_FALLING_STILL);
        Water.register(WATER_STAGE_6_FALLING_STILL);
        Water.register(WATER_STAGE_7_FALLING_STILL);
    }

    /**
     * Helper class for second water (water still) ID
     */
    public static class WaterStill extends Water
    {
        public WaterStill()
        {
            super(LiquidType.STILL);
        }

        public WaterStill(final LiquidStage stage)
        {
            super(stage, LiquidType.STILL);
        }

        @SuppressWarnings("MagicNumber")
        public static Water getByID(final int id)
        {
            return byID.get((byte) (id + 16));
        }
    }
}
