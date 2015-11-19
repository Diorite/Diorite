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

package org.diorite.impl.inventory.item.meta;

import java.util.Collection;
import java.util.Set;

import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.item.meta.BlockItemMeta;
import org.diorite.material.BlockMaterialData;
import org.diorite.nbt.NbtTagCompound;

public class BlockItemMetaImpl extends SimpleItemMetaImpl implements BlockItemMeta
{
    public BlockItemMetaImpl(final NbtTagCompound tag)
    {
        super(tag);
    }

    public BlockItemMetaImpl(final NbtTagCompound tag, final ItemStack itemStack)
    {
        super(tag, itemStack);
    }

    @Override
    public boolean hasBlockState()
    {
        return false;
    }

    @Override
    public boolean useCanPlaceOnTag()
    {
        return false;
    }

    @Override
    public void setUseCanPlaceOnTag(final boolean useCanPlaceOnTag)
    {

    }

    @Override
    public Set<BlockMaterialData> getCanPlaceOnMaterials()
    {
        return null;
    }

    @Override
    public void setCanPlaceOnMaterials(final Collection<BlockMaterialData> materials)
    {

    }

    @Override
    public void addCanPlaceOnMaterial(final BlockMaterialData material)
    {

    }

    @Override
    public void removeCanPlaceOnMaterial(final BlockMaterialData material)
    {

    }

    @Override
    public void removeCanPlaceOnMaterials()
    {

    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public BlockItemMetaImpl clone()
    {
        return new BlockItemMetaImpl((this.tag == null) ? null : this.tag.clone(), this.itemStack);
    }
}
