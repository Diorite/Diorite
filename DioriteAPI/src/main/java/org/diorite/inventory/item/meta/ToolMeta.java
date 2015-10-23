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

package org.diorite.inventory.item.meta;

import java.util.Set;

import org.diorite.material.BlockMaterialData;

/**
 * Represent {@link ItemMeta} used by tool items, like pickaxes.
 */
public interface ToolMeta
{
    /**
     * Set if this tool is unbreakable.
     *
     * @param unbreakable if tool should be unbreakable.
     */
    void setUnbreakable(boolean unbreakable);

    /**
     * Returns true if tool is unbreakable.
     *
     * @return true if tool is unbreakable.
     */
    boolean isUnbreakable();

    /**
     * Returns true if this tool is using CanDestory tag.
     *
     * @return true if this tool is using CanDestory tag.
     */
    boolean useCanDestoryTag();

    /**
     * Set if this tool is using CanDestory tag,
     * when you set it to false, add saved materials will be removed.
     *
     * @param useCanDestoryTag if this tool should use CanDestory tag.
     */
    void setUseCanDestoryTag(boolean useCanDestoryTag);

    /**
     * Returns set of {@link BlockMaterialData}, materials that can be destroyed by this tool. <br>
     * Minecraft don't support subtypes here, so default type will be returned. <br>
     * If set is empty and {@link #useCanDestoryTag()} is true, then this tool can't destory any block. <br>
     * If set is empty and {@link #useCanDestoryTag()} is false, then this tool can destory every block.
     *
     * @return set of {@link BlockMaterialData}, materials that can be destroyed by this tool if CanDestory tag is used.
     */
    Set<BlockMaterialData> getCanDestoryMaterials();

    /**
     * Add new material that can be destroyed by this tool,
     * this method will automatically enable CanDestory tag if needed. <br>
     * Method will ignore sub-type of material and use default type instead.
     *
     * @param material material to add.
     */
    void addCanDestoryMaterial(BlockMaterialData material);

    /**
     * Remove material that can be destroyed by this tool,
     * this method will automatically enable CanDestory tag if needed. <br>
     * Method will ignore sub-type of material and use default type instead.
     *
     * @param material material to add.
     */
    void removeCanDestoryMaterial(BlockMaterialData material);

    /**
     * Clear list of materials that can be destroyed by this tool,
     * this method will automatically enable CanDestory tag if needed. <br>
     * Use {@link #setUseCanDestoryTag(boolean)} if you want disable CanDestory tag.
     */
    void removeCanDestoryMaterials();
}
