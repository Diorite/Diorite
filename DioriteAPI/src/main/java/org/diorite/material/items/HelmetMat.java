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

import org.diorite.material.ArmorMaterial;
import org.diorite.material.ArmorType;

/**
 * Abstract class for all helmet-based armors.
 */
@SuppressWarnings("JavaDoc")
public abstract class HelmetMat extends ArmorMat
{
    protected HelmetMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ArmorMaterial armorMaterial)
    {
        super(enumName, id, minecraftId, typeName, type, armorMaterial, ArmorType.HELMET);
    }

    protected HelmetMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ArmorMaterial armorMaterial)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, armorMaterial, ArmorType.HELMET);
    }

    protected HelmetMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, typeName, type, armorMaterial, armorType);
    }

    protected HelmetMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ArmorMaterial armorMaterial, final ArmorType armorType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, armorMaterial, armorType);
    }

    @Override
    public abstract HelmetMat[] types();

    @Override
    public abstract HelmetMat getType(final String type);

    @Override
    public abstract HelmetMat getType(final int type);

    @Override
    public abstract HelmetMat increaseDurability();

    @Override
    public abstract HelmetMat decreaseDurability();

    @Override
    public abstract HelmetMat setDurability(final int durability);
}
