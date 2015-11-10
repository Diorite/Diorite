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

import java.util.List;

import org.diorite.DyeColor;
import org.diorite.banner.BannerPattern;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.item.meta.BannerMeta;
import org.diorite.nbt.NbtTagCompound;

public class BannerMetaImpl extends SimpleItemMetaImpl implements BannerMeta
{
    // TODO banner meta data is stored under 'BlockEntityTag' tag, so it should extends BlockItemMeta
    protected static final String BASE_COLOR ="Base";
    protected static final String PATTERNS ="Patterns";
    protected static final String PATTERN_COLOR ="Color";
    protected static final String PATTERN_TYPE ="Pattern";

    public BannerMetaImpl(final NbtTagCompound tag)
    {
        super(tag);
    }

    public BannerMetaImpl(final NbtTagCompound tag, final ItemStack itemStack)
    {
        super(tag, itemStack);
    }

    @Override
    public DyeColor getBaseColor()
    {
        return null;
    }

    @Override
    public void setBaseColor(final DyeColor color)
    {

    }

    @Override
    public List<BannerPattern> getPatterns()
    {
        return null;
    }

    @Override
    public void setPatterns(final List<BannerPattern> patterns)
    {

    }

    @Override
    public void addPattern(final BannerPattern pattern)
    {

    }

    @Override
    public BannerPattern getPattern(final int i)
    {
        return null;
    }

    @Override
    public BannerPattern removePattern(final int i)
    {
        return null;
    }

    @Override
    public void setPattern(final int i, final BannerPattern pattern)
    {

    }

    @Override
    public int numberOfPatterns()
    {
        return 0;
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public BannerMetaImpl clone()
    {
        return new BannerMetaImpl((this.tag == null) ? null : this.tag.clone(), this.itemStack);
    }
}
