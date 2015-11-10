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

import org.diorite.inventory.item.meta.RepairableMeta;
import org.diorite.material.ArmorData;
import org.diorite.material.ArmorMaterial;
import org.diorite.material.ArmorType;
import org.diorite.material.BreakableItemMat;
import org.diorite.material.ItemMaterialData;

/**
 * Represents an armor item that have durability and can break when it go above {@link #getBaseDurability()} <br>
 * Armor durability types should be cached.
 */
@SuppressWarnings("JavaDoc")
public abstract class ArmorMat extends ItemMaterialData implements BreakableItemMat
{
    /**
     * Material of armor.
     */
    protected final ArmorMaterial armorMaterial;
    /**
     * Type of armor.
     */
    protected final ArmorType     armorType;

    protected ArmorMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, 1, typeName, type);
        this.armorMaterial = armorMaterial;
        this.armorType = armorType;
    }

    protected ArmorMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
        this.armorMaterial = armorMaterial;
        this.armorType = armorType;
    }

    {
        this.metaType = RepairableMeta.class;
    }

    /**
     * Retruns {@link ArmorMaterial} defined by this item.
     *
     * @return {@link ArmorMaterial} defined by this item.
     */
    public ArmorMaterial getArmorMaterial()
    {
        return this.armorMaterial;
    }

    /**
     * Retruns {@link ArmorType} defined by this item.
     *
     * @return {@link ArmorType} defined by this item.
     */
    public ArmorType getArmorType()
    {
        return this.armorType;
    }

    /**
     * Returns properties of armor defined by this item.
     *
     * @return properties of this armor.
     */
    public ArmorData getProperties()
    {
        return this.armorMaterial.getProperties(this.armorType);
    }

    @Override
    public int getBaseDurability()
    {
        return this.getProperties().getBaseDurability();
    }

    @Override
    public int getDurability()
    {
        return this.getType();
    }

    @Override
    public abstract ArmorMat[] types();

    @Override
    public abstract ArmorMat getType(final String type);

    @Override
    public abstract ArmorMat getType(final int type);

    @Override
    public abstract ArmorMat increaseDurability();

    @Override
    public abstract ArmorMat decreaseDurability();

    @Override
    public abstract ArmorMat setDurability(final int durability);

    @Override
    public boolean isArmor()
    {
        return true;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("properties", this.getProperties()).toString();
    }
}
