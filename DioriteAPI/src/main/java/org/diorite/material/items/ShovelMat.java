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

import org.diorite.material.ToolMaterial;
import org.diorite.material.ToolType;

public abstract class ShovelMat extends ToolMat
{
    protected ShovelMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ToolMaterial toolMaterial)
    {
        super(enumName, id, minecraftId, typeName, type, toolMaterial, ToolType.SHOVEL);
    }

    protected ShovelMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ToolMaterial toolMaterial)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, toolMaterial, ToolType.SHOVEL);
    }

    protected ShovelMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, typeName, type, toolMaterial, toolType);
    }

    protected ShovelMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final ToolMaterial toolMaterial, final ToolType toolType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, toolMaterial, toolType);
    }

    @Override
    public abstract ShovelMat[] types();

    @Override
    public abstract ShovelMat getType(final String type);

    @Override
    public abstract ShovelMat getType(final int type);

    @Override
    public abstract ShovelMat increaseDurability();

    @Override
    public abstract ShovelMat decreaseDurability();

    @Override
    public abstract ShovelMat setDurability(final int durability);
}
