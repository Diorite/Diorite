package org.diorite.material.blocks.liquid;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class Water extends Liquid
{
    public static final byte USED_DATA_VALUES = 16;

    public static final Water WATER_SOURCE  = new Water();
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


    public static final Water WATER_SOURCE_STILL  = new Water();
    public static final Water WATER_STAGE_1_STILL = new Water(LiquidStage.STAGE_1, LiquidType.STILL);
    public static final Water WATER_STAGE_2_STILL = new Water(LiquidStage.STAGE_2, LiquidType.STILL);
    public static final Water WATER_STAGE_3_STILL = new Water(LiquidStage.STAGE_3, LiquidType.STILL);
    public static final Water WATER_STAGE_4_STILL = new Water(LiquidStage.STAGE_4, LiquidType.STILL);
    public static final Water WATER_STAGE_5_STILL = new Water(LiquidStage.STAGE_5, LiquidType.STILL);
    public static final Water WATER_STAGE_6_STILL = new Water(LiquidStage.STAGE_6, LiquidType.STILL);
    public static final Water WATER_STAGE_7_STILL = new Water(LiquidStage.STAGE_7, LiquidType.STILL);

    public static final Water WATER_SOURCE_FALLING_STILL  = new Water(LiquidStage.SOURCE_FALLING, LiquidType.STILL);
    public static final Water WATER_STAGE_1_FALLING_STILL = new Water(LiquidStage.STAGE_1_FALLING, LiquidType.STILL);
    public static final Water WATER_STAGE_2_FALLING_STILL = new Water(LiquidStage.STAGE_2_FALLING, LiquidType.STILL);
    public static final Water WATER_STAGE_3_FALLING_STILL = new Water(LiquidStage.STAGE_3_FALLING, LiquidType.STILL);
    public static final Water WATER_STAGE_4_FALLING_STILL = new Water(LiquidStage.STAGE_4_FALLING, LiquidType.STILL);
    public static final Water WATER_STAGE_5_FALLING_STILL = new Water(LiquidStage.STAGE_5_FALLING, LiquidType.STILL);
    public static final Water WATER_STAGE_6_FALLING_STILL = new Water(LiquidStage.STAGE_6_FALLING, LiquidType.STILL);
    public static final Water WATER_STAGE_7_FALLING_STILL = new Water(LiquidStage.STAGE_7_FALLING, LiquidType.STILL);

    private static final Map<String, Water>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES << 1, SLOW_GROW);
    private static final TByteObjectMap<Water> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES << 1, SLOW_GROW);

    protected Water()
    {
        super("WATER", 8, "SOURCE", LiquidStage.SOURCE, LiquidType.NORMAL);
    }

    public Water(final LiquidStage stage, final LiquidType liquidType)
    {
        super(WATER_SOURCE.name(), WATER_SOURCE.getId() + ((liquidType == LiquidType.STILL) ? 1 : 0), WATER_SOURCE.getMaxStack(), stage.name() + (liquidType == LiquidType.STILL ? "_STILL" : ""), stage, liquidType);
    }

    @Override
    public Water getStage(final LiquidStage stage)
    {
        for (final Water water : byName.values())
        {
            if ((water.getLiquidType() == this.liquidType) && (water.stage == stage))
            {
                return water;
            }
        }
        return null;
    }

    @Override
    public Water getLiquidType(final LiquidType type)
    {
        for (final Water water : byName.values())
        {
            if ((water.getStage() == this.stage) && (water.liquidType == type))
            {
                return water;
            }
        }
        return null;
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
        return this.isStill() ? MagicNumbers.MATERIAL__WATER_STILL__BLAST_RESISTANCE : MagicNumbers.MATERIAL__WATER__BLAST_RESISTANCE;
    }

    @Override
    public float getHardness()
    {
        return this.isStill() ? MagicNumbers.MATERIAL__WATER_STILL__HARDNESS : MagicNumbers.MATERIAL__WATER__HARDNESS;
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
        return (byte) (this.getType() + ((this.liquidType == LiquidType.STILL) ? 16 : 0));
    }

    @SuppressWarnings("MagicNumber")
    public static Water get(final LiquidType type, final LiquidStage stage)
    {
        return getByID(stage.getDataValue() + ((type == LiquidType.STILL) ? 16 : 0));
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
}
