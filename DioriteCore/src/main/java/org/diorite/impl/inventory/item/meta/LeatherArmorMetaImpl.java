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

import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.item.meta.LeatherArmorMeta;
import org.diorite.nbt.NbtTagCompound;
import org.diorite.utils.Color;

public class LeatherArmorMetaImpl extends SimpleItemMetaImpl implements LeatherArmorMeta
{
    protected static final String COLOR = "display.color";

    public LeatherArmorMetaImpl(final NbtTagCompound tag)
    {
        super(tag);
    }

    public LeatherArmorMetaImpl(final NbtTagCompound tag, final ItemStack itemStack)
    {
        super(tag, itemStack);
    }

    @Override
    public boolean hasColor()
    {
        return (this.tag != null) && this.tag.containsTag(COLOR);
    }

    @Override
    public Color getColor()
    {
        if (this.tag == null)
        {
            return null;
        }
        final Integer i = this.tag.getInteger(COLOR);
        if (i == null)
        {
            return null;
        }
        return Color.fromRGB(i);
    }

    @Override
    public void setColor(final Color color)
    {
        if (this.removeIfNeeded(COLOR, color))
        {
            return;
        }
        this.checkTag(true);
        this.tag.setInt(COLOR, color.asRGB());
        this.setDirty();
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public LeatherArmorMetaImpl clone()
    {
        return new LeatherArmorMetaImpl((this.tag == null) ? null : this.tag.clone(), this.itemStack);
    }
}
