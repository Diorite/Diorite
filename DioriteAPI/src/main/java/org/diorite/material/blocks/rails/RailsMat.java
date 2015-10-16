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

package org.diorite.material.blocks.rails;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.BlockMaterialData;

public abstract class RailsMat extends BlockMaterialData
{
    protected final RailTypeMat railType;

    protected RailsMat(final String enumName, final int id, final String minecraftId, final String typeName, final RailTypeMat railType, final byte flags, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, typeName, (byte) (railType.getFlag() | flags), hardness, blastResistance);
        this.railType = railType;
    }

    protected RailsMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final RailTypeMat railType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.railType = railType;
    }

    /**
     * @return type of Rails.
     */
    public RailTypeMat getRailType()
    {
        return this.railType;
    }

    /**
     * Returns sub-type of Rails based on {@link RailTypeMat} state.
     *
     * @param railType {@link RailTypeMat} of Rails,
     *
     * @return sub-type of Rails
     */
    public abstract RailsMat getRailType(RailTypeMat railType);

    @Override
    public abstract RailsMat getType(final int type);

    @Override
    public abstract RailsMat getType(final String type);

    @Override
    public abstract RailsMat[] types();

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("railType", this.railType).toString();
    }
}
