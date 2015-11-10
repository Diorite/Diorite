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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.item.meta.ToolMeta;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.nbt.NbtTagCompound;
import org.diorite.nbt.NbtTagList;
import org.diorite.nbt.NbtTagString;

public class ToolMetaImpl extends RepairableMetaImpl implements ToolMeta
{
    protected static final String CAN_DESTROY = "CanDestroy";

    public ToolMetaImpl(final NbtTagCompound tag, final ItemStack itemStack)
    {
        super(tag, itemStack);
    }

    public ToolMetaImpl(final NbtTagCompound tag)
    {
        super(tag);
    }

    @Override
    public boolean useCanDestoryTag()
    {
        return (this.tag != null) && this.tag.containsTag(CAN_DESTROY);
    }

    @Override
    public void setUseCanDestoryTag(final boolean useCanDestoryTag)
    {
        if (this.removeIfNeeded(CAN_DESTROY, ! useCanDestoryTag))
        {
            return;
        }
        this.setCanDestoryMaterials(new ArrayList<>(1));
    }

    @Override
    public Set<BlockMaterialData> getCanDestoryMaterials()
    {
        if (this.tag == null)
        {
            return null;
        }
        final NbtTagList list = this.tag.getTag(CAN_DESTROY, NbtTagList.class);
        if (list == null)
        {
            return null;
        }
        return list.getTags(NbtTagString.class).stream().map(s -> Material.getByMinecraftId(s.getValue())).filter(m -> m instanceof BlockMaterialData).map(m -> (BlockMaterialData) m).collect(Collectors.toSet());
    }

    @Override
    public void setCanDestoryMaterials(final Collection<BlockMaterialData> materials)
    {
        if (this.removeIfNeeded(CAN_DESTROY, materials))
        {
            return;
        }
        this.checkTag(true);
        final NbtTagList list = new NbtTagList(CAN_DESTROY, materials.size() + 1);
        materials.stream().map(m -> new NbtTagString(null, m.getMinecraftId())).forEach(list::add);
        this.tag.addTag(list);
        this.setDirty();
    }

    @Override
    public void addCanDestoryMaterial(final BlockMaterialData material)
    {
        this.checkTag(true);
        NbtTagList list = this.tag.getTag(CAN_DESTROY, NbtTagList.class);
        if (list == null)
        {
            list = new NbtTagList(CAN_DESTROY, 3);
            this.tag.addTag(list);
        }
        list.add(new NbtTagString(null, material.getMinecraftId()));
        this.setDirty();
    }

    @Override
    public boolean containsCanDestoryMaterial(final BlockMaterialData material)
    {
        if (this.tag == null)
        {
            return false;
        }
        final NbtTagList list = this.tag.getTag(CAN_DESTROY, NbtTagList.class);
        return (list != null) && list.stream().anyMatch(t -> ((NbtTagString) t).getValue().equalsIgnoreCase(material.getMinecraftId()));
    }

    @Override
    public void removeCanDestoryMaterial(final BlockMaterialData material)
    {
        if (this.tag == null)
        {
            return;
        }
        final NbtTagList list = this.tag.getTag(CAN_DESTROY, NbtTagList.class);
        if (list == null)
        {
            return;
        }
        list.removeIf(t -> ((NbtTagString) t).getValue().equalsIgnoreCase(material.getMinecraftId()));
        this.setDirty();
    }

    @Override
    public void removeCanDestoryMaterials()
    {
        if (this.tag == null)
        {
            return;
        }
        this.tag.removeTag(CAN_DESTROY);
        this.setDirty();
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public ToolMetaImpl clone()
    {
        return new ToolMetaImpl((this.tag == null) ? null : this.tag.clone(), this.itemStack);
    }
}
