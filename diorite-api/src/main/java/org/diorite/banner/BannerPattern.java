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

package org.diorite.banner;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.DyeColor;
import org.diorite.nbt.NbtSerializable;
import org.diorite.nbt.NbtTagCompound;

/**
 * Represent pattern used by banners.
 */
public class BannerPattern implements NbtSerializable
{
    /**
     * Color of pattern.
     */
    protected final DyeColor          color;
    /**
     * Type of pattern.
     */
    protected final BannerPatternType pattern;

    /**
     * Construct new BannerPattern.
     *
     * @param color   color of pattern.
     * @param pattern Type of pattern.
     */
    public BannerPattern(final DyeColor color, final BannerPatternType pattern)
    {
        this.color = color;
        this.pattern = pattern;
    }

    /**
     * Construct new BannerPattern from nbt tags.
     *
     * @param tag nbt to be deserialized.
     */
    public BannerPattern(final NbtTagCompound tag)
    {
        this.color = DyeColor.getByItemFlag(tag.getInt("Color"));
        this.pattern = BannerPatternType.getByIdentifier(tag.getString("Pattern", "b"));
    }

    /**
     * Returns color of pattern.
     *
     * @return color of pattern.
     */
    public DyeColor getColor()
    {
        return this.color;
    }

    /**
     * Returns type of pattern.
     *
     * @return type of pattern.
     */
    public BannerPatternType getPattern()
    {
        return this.pattern;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof BannerPattern))
        {
            return false;
        }

        final BannerPattern that = (BannerPattern) o;

        return this.color.equals(that.color) && this.pattern.equals(that.pattern);
    }

    @Override
    public int hashCode()
    {
        int result = this.color.hashCode();
        result = (31 * result) + this.pattern.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("color", this.color).append("pattern", this.pattern).toString();
    }

    @Override
    public NbtTagCompound serializeToNBT()
    {
        final NbtTagCompound tag = new NbtTagCompound();
        tag.setInt("Color", this.color.getItemFlag());
        tag.setString("Pattern", this.pattern.identifier);
        return tag;
    }
}
