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

package org.diorite.material.items;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.ItemMaterialData;

/**
 * Represents an item that can be eaten by player.
 */
@SuppressWarnings("JavaDoc")
public abstract class EdibleItemMat extends ItemMaterialData
{
    /**
     * How much hunger bar should be restored by eating this food.
     */
    protected final int   foodLevelIncrease;
    /**
     * How much saturation should increase by eating this food.
     */
    protected final float saturationIncrease;

    protected EdibleItemMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, typeName, type);
        this.foodLevelIncrease = foodLevelIncrease;
        this.saturationIncrease = saturationIncrease;
    }

    protected EdibleItemMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
        this.foodLevelIncrease = foodLevelIncrease;
        this.saturationIncrease = saturationIncrease;
    }

    /**
     * Returns how much player food level will increase
     * after eating this.
     *
     * @return player food level increase.
     */
    public int getFoodLevelIncrease()
    {
        return this.foodLevelIncrease;
    }

    /**
     * Returns how much player food saturation level will increase
     * after eating this.
     *
     * @return player food saturation level increase.
     */
    public float getSaturationIncrease()
    {
        return this.saturationIncrease;
    }

    // TODO: implement when potion effect will be added
//    /**
//     * Returns (immutable) set of possible potion effects, each entry of this set contains
//     * percent chance to get this effect, type and power of effect, and duration of it.
//     * @return set of possible potion effects.
//     */
//     Set<PossiblePotionEffect> getPossibleEffects();


    @Override
    public abstract EdibleItemMat getType(final int type);

    @Override
    public abstract EdibleItemMat getType(final String type);

    @Override
    public abstract EdibleItemMat[] types();

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("foodLevelIncrease", this.foodLevelIncrease).append("saturationIncrease", this.saturationIncrease).toString();
    }
}
