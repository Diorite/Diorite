package org.diorite.material.blocks.liquid;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public enum LiquidStage
{
    SOURCE(0x00),
    STAGE_1(0x01),
    STAGE_2(0x02),
    STAGE_3(0x03),
    STAGE_4(0x04),
    STAGE_5(0x05),
    STAGE_6(0x06),
    STAGE_7(0x07),
    SOURCE_FALLING(SOURCE.dataValue | Liquid.FALLING_FLAG),
    STAGE_1_FALLING(STAGE_1.dataValue | Liquid.FALLING_FLAG),
    STAGE_2_FALLING(STAGE_2.dataValue | Liquid.FALLING_FLAG),
    STAGE_3_FALLING(STAGE_3.dataValue | Liquid.FALLING_FLAG),
    STAGE_4_FALLING(STAGE_4.dataValue | Liquid.FALLING_FLAG),
    STAGE_5_FALLING(STAGE_5.dataValue | Liquid.FALLING_FLAG),
    STAGE_6_FALLING(STAGE_6.dataValue | Liquid.FALLING_FLAG),
    STAGE_7_FALLING(STAGE_7.dataValue | Liquid.FALLING_FLAG);

    @SuppressWarnings("MagicNumber")
    private static final TByteObjectMap<LiquidStage> byID = new TByteObjectHashMap<>(16);

    private final byte dataValue;

    LiquidStage(final byte dataValue)
    {
        this.dataValue = dataValue;
    }

    LiquidStage(final int dataValue)
    {
        this((byte) dataValue);
    }

    public byte getDataValue()
    {
        return this.dataValue;
    }

    public boolean isFalling()
    {
        switch (this)
        {
            case SOURCE:
            case STAGE_1:
            case STAGE_2:
            case STAGE_3:
            case STAGE_4:
            case STAGE_5:
            case STAGE_6:
            case STAGE_7:
                return false;
            case SOURCE_FALLING:
            case STAGE_1_FALLING:
            case STAGE_2_FALLING:
            case STAGE_3_FALLING:
            case STAGE_4_FALLING:
            case STAGE_5_FALLING:
            case STAGE_6_FALLING:
            case STAGE_7_FALLING:
                return true;
            default:
                return false;
        }
    }

    public boolean isNormal()
    {
        switch (this)
        {
            case SOURCE:
            case STAGE_1:
            case STAGE_2:
            case STAGE_3:
            case STAGE_4:
            case STAGE_5:
            case STAGE_6:
            case STAGE_7:
                return true;
            case SOURCE_FALLING:
            case STAGE_1_FALLING:
            case STAGE_2_FALLING:
            case STAGE_3_FALLING:
            case STAGE_4_FALLING:
            case STAGE_5_FALLING:
            case STAGE_6_FALLING:
            case STAGE_7_FALLING:
                return false;
            default:
                return false;
        }
    }

    public boolean isSource()
    {
        switch (this)
        {
            case SOURCE:
            case SOURCE_FALLING:
                return true;
            case STAGE_1:
            case STAGE_2:
            case STAGE_3:
            case STAGE_4:
            case STAGE_5:
            case STAGE_6:
            case STAGE_7:
            case STAGE_1_FALLING:
            case STAGE_2_FALLING:
            case STAGE_3_FALLING:
            case STAGE_4_FALLING:
            case STAGE_5_FALLING:
            case STAGE_6_FALLING:
            case STAGE_7_FALLING:
                return false;
            default:
                return false;
        }
    }

    public LiquidStage getSource()
    {
        switch (this)
        {
            case SOURCE:
            case SOURCE_FALLING:
                return this;
            case STAGE_1:
            case STAGE_2:
            case STAGE_3:
            case STAGE_4:
            case STAGE_5:
            case STAGE_6:
            case STAGE_7:
                return SOURCE;
            case STAGE_1_FALLING:
            case STAGE_2_FALLING:
            case STAGE_3_FALLING:
            case STAGE_4_FALLING:
            case STAGE_5_FALLING:
            case STAGE_6_FALLING:
            case STAGE_7_FALLING:
                return SOURCE_FALLING;
            default:
                return null;
        }
    }

    public LiquidStage getNormal()
    {
        switch (this)
        {
            case SOURCE:
                return SOURCE;
            case STAGE_1:
                return STAGE_1;
            case STAGE_2:
                return STAGE_2;
            case STAGE_3:
                return STAGE_3;
            case STAGE_4:
                return STAGE_4;
            case STAGE_5:
                return STAGE_5;
            case STAGE_6:
                return STAGE_6;
            case STAGE_7:
                return STAGE_7;
            case SOURCE_FALLING:
                return SOURCE;
            case STAGE_1_FALLING:
                return STAGE_1;
            case STAGE_2_FALLING:
                return STAGE_2;
            case STAGE_3_FALLING:
                return STAGE_3;
            case STAGE_4_FALLING:
                return STAGE_4;
            case STAGE_5_FALLING:
                return STAGE_5;
            case STAGE_6_FALLING:
                return STAGE_6;
            case STAGE_7_FALLING:
                return STAGE_7;
            default:
                return null;
        }
    }

    public LiquidStage switchFalling()
    {
        switch (this)
        {
            case SOURCE:
                return SOURCE_FALLING;
            case STAGE_1:
                return STAGE_1_FALLING;
            case STAGE_2:
                return STAGE_2_FALLING;
            case STAGE_3:
                return STAGE_3_FALLING;
            case STAGE_4:
                return STAGE_4_FALLING;
            case STAGE_5:
                return STAGE_5_FALLING;
            case STAGE_6:
                return STAGE_6_FALLING;
            case STAGE_7:
                return STAGE_7_FALLING;
            case SOURCE_FALLING:
                return SOURCE;
            case STAGE_1_FALLING:
                return STAGE_1;
            case STAGE_2_FALLING:
                return STAGE_2;
            case STAGE_3_FALLING:
                return STAGE_3;
            case STAGE_4_FALLING:
                return STAGE_4;
            case STAGE_5_FALLING:
                return STAGE_5;
            case STAGE_6_FALLING:
                return STAGE_6;
            case STAGE_7_FALLING:
                return STAGE_7;
            default:
                return null;
        }
    }

    public LiquidStage getFalling()
    {
        switch (this)
        {
            case SOURCE:
                return SOURCE_FALLING;
            case STAGE_1:
                return STAGE_1_FALLING;
            case STAGE_2:
                return STAGE_2_FALLING;
            case STAGE_3:
                return STAGE_3_FALLING;
            case STAGE_4:
                return STAGE_4_FALLING;
            case STAGE_5:
                return STAGE_5_FALLING;
            case STAGE_6:
                return STAGE_6_FALLING;
            case STAGE_7:
                return STAGE_7_FALLING;
            case SOURCE_FALLING:
                return SOURCE_FALLING;
            case STAGE_1_FALLING:
                return STAGE_1_FALLING;
            case STAGE_2_FALLING:
                return STAGE_2_FALLING;
            case STAGE_3_FALLING:
                return STAGE_3_FALLING;
            case STAGE_4_FALLING:
                return STAGE_4_FALLING;
            case STAGE_5_FALLING:
                return STAGE_5_FALLING;
            case STAGE_6_FALLING:
                return STAGE_6_FALLING;
            case STAGE_7_FALLING:
                return STAGE_7_FALLING;
            default:
                return null;
        }
    }

    public LiquidStage getNextStage()
    {
        switch (this)
        {
            case SOURCE:
                return STAGE_1;
            case STAGE_1:
                return STAGE_2;
            case STAGE_2:
                return STAGE_3;
            case STAGE_3:
                return STAGE_4;
            case STAGE_4:
                return STAGE_5;
            case STAGE_5:
                return STAGE_6;
            case STAGE_6:
                return STAGE_7;
            case STAGE_7:
                return STAGE_7;
            case SOURCE_FALLING:
                return STAGE_1_FALLING;
            case STAGE_1_FALLING:
                return STAGE_2_FALLING;
            case STAGE_2_FALLING:
                return STAGE_3_FALLING;
            case STAGE_3_FALLING:
                return STAGE_4_FALLING;
            case STAGE_4_FALLING:
                return STAGE_5_FALLING;
            case STAGE_5_FALLING:
                return STAGE_6_FALLING;
            case STAGE_6_FALLING:
                return STAGE_7_FALLING;
            case STAGE_7_FALLING:
                return STAGE_7_FALLING;
            default:
                return null;
        }
    }

    public LiquidStage getPreviousStage()
    {
        switch (this)
        {
            case SOURCE:
                return SOURCE;
            case STAGE_1:
                return SOURCE;
            case STAGE_2:
                return STAGE_1;
            case STAGE_3:
                return STAGE_2;
            case STAGE_4:
                return STAGE_3;
            case STAGE_5:
                return STAGE_4;
            case STAGE_6:
                return STAGE_5;
            case STAGE_7:
                return STAGE_6;
            case SOURCE_FALLING:
                return SOURCE_FALLING;
            case STAGE_1_FALLING:
                return SOURCE_FALLING;
            case STAGE_2_FALLING:
                return STAGE_1_FALLING;
            case STAGE_3_FALLING:
                return STAGE_2_FALLING;
            case STAGE_4_FALLING:
                return STAGE_3_FALLING;
            case STAGE_5_FALLING:
                return STAGE_4_FALLING;
            case STAGE_6_FALLING:
                return STAGE_5_FALLING;
            case STAGE_7_FALLING:
                return STAGE_6_FALLING;
            default:
                return null;
        }
    }

    public static LiquidStage getByID(final int id)
    {
        return byID.get((byte) id);
    }

    static
    {
        for (final LiquidStage stage : values())
        {
            byID.put(stage.dataValue, stage);
        }
    }
}
