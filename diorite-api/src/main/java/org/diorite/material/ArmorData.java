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

package org.diorite.material;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represents armor properties.
 */
public class ArmorData
{
    /**
     * material of armor.
     */
    protected final ArmorMaterial armorMaterial;
    /**
     * type of armor.
     */
    protected final ArmorType     armorType;
    /**
     * base durability of armor.
     */
    protected final int           baseDurability;
    /**
     * defence points of armor.
     */
    protected final int           defense;

    /**
     * Construct new armor properties.
     *
     * @param armorMaterial  material of armor.
     * @param armorType      type of armor.
     * @param baseDurability base durability of armor.
     * @param defense        defence points of armor.
     */
    public ArmorData(final ArmorMaterial armorMaterial, final ArmorType armorType, final int baseDurability, final int defense)
    {
        Validate.notNull(armorMaterial, "Armor material can't be null.");
        Validate.notNull(armorType, "Armor type can't be null.");
        this.armorMaterial = armorMaterial;
        this.armorType = armorType;
        this.baseDurability = baseDurability;
        this.defense = defense;
    }

    /**
     * Get {@link ArmorMaterial} defined by this properties.
     *
     * @return {@link ArmorMaterial} defined by this properties.
     */
    public ArmorMaterial getArmorMaterial()
    {
        return this.armorMaterial;
    }

    /**
     * Get {@link ArmorType} defined by this properties.
     *
     * @return {@link ArmorType} defined by this properties.
     */
    public ArmorType getArmorType()
    {
        return this.armorType;
    }

    /**
     * Returns base durability of this armor.
     *
     * @return base durability of this armor.
     */
    public int getBaseDurability()
    {
        return this.baseDurability;
    }

    /**
     * Returns defense points added by this armor.
     *
     * @return defense points added by this armor.
     */
    public int getDefense()
    {
        return this.defense;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof ArmorData))
        {
            return false;
        }

        final ArmorData that = (ArmorData) o;

        return (this.baseDurability == that.baseDurability) && (this.defense == that.defense) && this.armorMaterial.equals(that.armorMaterial) && this.armorType.equals(that.armorType);

    }

    @Override
    public int hashCode()
    {
        int result = this.armorMaterial.hashCode();
        result = (31 * result) + this.armorType.hashCode();
        result = (31 * result) + this.baseDurability;
        result = (31 * result) + this.defense;
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("armorMaterial", this.armorMaterial).append("armorType", this.armorType).append("baseDurability", this.baseDurability).append("defense", this.defense).toString();
    }
}
