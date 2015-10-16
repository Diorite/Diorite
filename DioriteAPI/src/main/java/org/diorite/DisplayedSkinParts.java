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

package org.diorite;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class DisplayedSkinParts
{
    public static final byte CAPE_FLAG            = 0x01;
    public static final byte JACKET_FLAG          = 0x02;
    public static final byte LEFT_SLEEVE_FLAG     = 0x04;
    public static final byte RIGHT_SLEEVE_FLAG    = 0x08;
    public static final byte LEFT_PANTS_LEG_FLAG  = 0x10;
    public static final byte RIGHT_PANTS_LEG_FLAG = 0x20;
    public static final byte HAT_FLAG             = 0x40;

    private boolean capeEnabled;
    private boolean jacketEnabled;
    private boolean leftSleeveEnabled;
    private boolean rightSleeveEnabled;
    private boolean leftPantsLegEnabled;
    private boolean rightPantsLegEnabled;
    private boolean hatEnabled;

    public DisplayedSkinParts()
    {
    }

    public DisplayedSkinParts(final boolean capeEnabled, final boolean jacketEnabled, final boolean leftSleeveEnabled, final boolean rightSleeveEnabled, final boolean leftPantsLegEnabled, final boolean rightPantsLegEnabled, final boolean hatEnabled)
    {
        this.capeEnabled = capeEnabled;
        this.jacketEnabled = jacketEnabled;
        this.leftSleeveEnabled = leftSleeveEnabled;
        this.rightSleeveEnabled = rightSleeveEnabled;
        this.leftPantsLegEnabled = leftPantsLegEnabled;
        this.rightPantsLegEnabled = rightPantsLegEnabled;
        this.hatEnabled = hatEnabled;
    }

    public boolean isCapeEnabled()
    {
        return this.capeEnabled;
    }

    public void setCapeEnabled(final boolean capeEnabled)
    {
        this.capeEnabled = capeEnabled;
    }

    public boolean isJacketEnabled()
    {
        return this.jacketEnabled;
    }

    public void setJacketEnabled(final boolean jacketEnabled)
    {
        this.jacketEnabled = jacketEnabled;
    }

    public boolean isLeftSleeveEnabled()
    {
        return this.leftSleeveEnabled;
    }

    public void setLeftSleeveEnabled(final boolean leftSleeveEnabled)
    {
        this.leftSleeveEnabled = leftSleeveEnabled;
    }

    public boolean isRightSleeveEnabled()
    {
        return this.rightSleeveEnabled;
    }

    public void setRightSleeveEnabled(final boolean rightSleeveEnabled)
    {
        this.rightSleeveEnabled = rightSleeveEnabled;
    }

    public boolean isLeftPantsLegEnabled()
    {
        return this.leftPantsLegEnabled;
    }

    public void setLeftPantsLegEnabled(final boolean leftPantsLegEnabled)
    {
        this.leftPantsLegEnabled = leftPantsLegEnabled;
    }

    public boolean isRightPantsLegEnabled()
    {
        return this.rightPantsLegEnabled;
    }

    public void setRightPantsLegEnabled(final boolean rightPantsLegEnabled)
    {
        this.rightPantsLegEnabled = rightPantsLegEnabled;
    }

    public boolean isHatEnabled()
    {
        return this.hatEnabled;
    }

    public void setHatEnabled(final boolean hatEnabled)
    {
        this.hatEnabled = hatEnabled;
    }

    public byte toByteFlag()
    {
        byte flags = 0x00;
        if (this.capeEnabled)
        {
            flags |= CAPE_FLAG;
        }
        if (this.jacketEnabled)
        {
            flags |= CAPE_FLAG;
        }
        if (this.leftSleeveEnabled)
        {
            flags |= CAPE_FLAG;
        }
        if (this.rightSleeveEnabled)
        {
            flags |= CAPE_FLAG;
        }
        if (this.leftPantsLegEnabled)
        {
            flags |= CAPE_FLAG;
        }
        if (this.rightPantsLegEnabled)
        {
            flags |= CAPE_FLAG;
        }
        if (this.hatEnabled)
        {
            flags |= HAT_FLAG;
        }
        return flags;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("capeEnabled", this.capeEnabled).append("jacketEnabled", this.jacketEnabled).append("leftSleeveEnabled", this.leftSleeveEnabled).append("rightSleeveEnabled", this.rightSleeveEnabled).append("leftPantsLegEnabled", this.leftPantsLegEnabled).append("rightPantsLegEnabled", this.rightPantsLegEnabled).append("hatEnabled", this.hatEnabled).toString();
    }

    public static DisplayedSkinParts fromByteFlag(final int flags)
    {
        final boolean cape = (flags & CAPE_FLAG) != 0;
        final boolean jacket = (flags & JACKET_FLAG) != 0;
        final boolean leftSleeve = (flags & LEFT_SLEEVE_FLAG) != 0;
        final boolean rightSleeve = (flags & RIGHT_SLEEVE_FLAG) != 0;
        final boolean leftPantsLeg = (flags & LEFT_PANTS_LEG_FLAG) != 0;
        final boolean rightPantsLeg = (flags & RIGHT_PANTS_LEG_FLAG) != 0;
        final boolean hat = (flags & HAT_FLAG) != 0;
        return new DisplayedSkinParts(cape, jacket, leftSleeve, rightSleeve, leftPantsLeg, rightPantsLeg, hat);
    }
}
