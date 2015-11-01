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

import org.diorite.material.items.EdibleItemMat;
import org.diorite.material.items.ToolMat;

/**
 * Abstract class for all items.
 */
@SuppressWarnings("JavaDoc")
public abstract class ItemMaterialData extends Material
{
    protected ItemMaterialData(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected ItemMaterialData(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }

    /**
     * Check if item is tool-based item, tool-based item is an item that
     * have some durability limit. <br>
     * Most of them are sublcasses of {@link ToolMat}
     *
     * @return true if item is tool-based.
     */
    public boolean isTool()
    {
        return false;
    }

    /**
     * Check if item is an armor. <br>
     *
     * @return true if item is an Armor.
     */
    public boolean isArmor()
    {
        return false;
    }

    /**
     * Check if item can be placed, so it will change to the block. Like doors. <br>
     * Item like that should implements {@link PlaceableMat}
     *
     * @return if item is placeable.
     */
    public boolean canBePlaced()
    {
        return false;
    }

    /**
     * Check if item can be eaten by player. <br>
     * Item like that should implements {@link EdibleItemMat}
     *
     * @return if item can be eaten by player
     */
    public boolean isEdible()
    {
        return false;
    }

    @Override
    public boolean isBlock()
    {
        return false;
    }

    @Override
    public abstract ItemMaterialData[] types();

    @Override
    public abstract ItemMaterialData getType(final String type);

    @Override
    public abstract ItemMaterialData getType(final int type);
}
