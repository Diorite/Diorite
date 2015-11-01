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

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Possible stages of liquid, liquid can be in two stages: normal and falling, each stage can have 7 levels and source level of liquid in it.
 */
public enum LiquidStageMat
{
    SOURCE(0x00),
    STAGE_1(0x01),
    STAGE_2(0x02),
    STAGE_3(0x03),
    STAGE_4(0x04),
    STAGE_5(0x05),
    STAGE_6(0x06),
    STAGE_7(0x07),
    SOURCE_FALLING(SOURCE.dataValue | LiquidMat.FALLING_FLAG),
    STAGE_1_FALLING(STAGE_1.dataValue | LiquidMat.FALLING_FLAG),
    STAGE_2_FALLING(STAGE_2.dataValue | LiquidMat.FALLING_FLAG),
    STAGE_3_FALLING(STAGE_3.dataValue | LiquidMat.FALLING_FLAG),
    STAGE_4_FALLING(STAGE_4.dataValue | LiquidMat.FALLING_FLAG),
    STAGE_5_FALLING(STAGE_5.dataValue | LiquidMat.FALLING_FLAG),
    STAGE_6_FALLING(STAGE_6.dataValue | LiquidMat.FALLING_FLAG),
    STAGE_7_FALLING(STAGE_7.dataValue | LiquidMat.FALLING_FLAG);

    @SuppressWarnings("MagicNumber")
    private static final TByteObjectMap<LiquidStageMat> byID = new TByteObjectHashMap<>(16);

    private final byte dataValue;

    LiquidStageMat(final byte dataValue)
    {
        this.dataValue = dataValue;
    }

    LiquidStageMat(final int dataValue)
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

    public LiquidStageMat getSource()
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

    public LiquidStageMat getNormal()
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

    public LiquidStageMat switchFalling()
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

    public LiquidStageMat getFalling()
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

    public LiquidStageMat getNextStage()
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

    public LiquidStageMat getPreviousStage()
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

    public static LiquidStageMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    static
    {
        for (final LiquidStageMat stage : values())
        {
            byID.put(stage.dataValue, stage);
        }
    }
}
