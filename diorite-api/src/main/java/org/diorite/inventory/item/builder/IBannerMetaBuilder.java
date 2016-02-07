/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.inventory.item.builder;

import java.util.Collection;
import java.util.List;

import org.diorite.DyeColor;
import org.diorite.banner.BannerPattern;
import org.diorite.inventory.item.meta.BannerMeta;

/**
 * Interface of builder of banner item meta data.
 *
 * @param <B> type of builder.
 * @param <M> type of item meta.
 */
public interface IBannerMetaBuilder<B extends IBannerMetaBuilder<B, M>, M extends BannerMeta> extends IMetaBuilder<B, M>
{
    /**
     * Sets the base color for this banner
     *
     * @param color the base color
     *
     * @return builder for method chains.
     */
    default B baseColor(final DyeColor color)
    {
        this.meta().setBaseColor(color);
        return this.getBuilder();
    }

    /**
     * Sets the base color for this banner
     *
     * @param src source item meta to copy data from it.
     *
     * @return builder for method chains.
     */
    default B baseColor(final BannerMeta src)
    {
        this.meta().setBaseColor(src.getBaseColor());
        return this.getBuilder();
    }

    /**
     * Sets the patterns used on this banner
     *
     * @param patterns the new list of patterns
     *
     * @return builder for method chains.
     */
    default B setPatterns(final List<BannerPattern> patterns)
    {
        this.meta().setPatterns(patterns);
        return this.getBuilder();
    }

    /**
     * Sets the patterns used on this banner
     *
     * @param src source item meta to copy data from it.
     *
     * @return builder for method chains.
     */
    default B setPatterns(final BannerMeta src)
    {
        this.meta().setPatterns(src.getPatterns());
        return this.getBuilder();
    }

    /**
     * Adds a new pattern on top of the existing
     * patterns
     *
     * @param pattern the new pattern to add
     *
     * @return builder for method chains.
     */
    default B addPattern(final BannerPattern pattern)
    {
        this.meta().addPattern(pattern);
        return this.getBuilder();
    }

    /**
     * Adds new patterns on top of the existing
     * patterns
     *
     * @param patterns new patterns to add
     *
     * @return builder for method chains.
     */
    default B addPatterns(final Collection<BannerPattern> patterns)
    {
        this.meta().addPatterns(patterns);
        return this.getBuilder();
    }

    /**
     * Adds new patterns on top of the existing
     * patterns
     *
     * @param src source item meta to copy data from it.
     *
     * @return builder for method chains.
     */
    default B addPatterns(final BannerMeta src)
    {
        final List<BannerPattern> effects = src.getPatterns();
        if ((effects == null) || effects.isEmpty())
        {
            return this.getBuilder();
        }
        this.meta().addPatterns(effects);
        return this.getBuilder();
    }

    /**
     * Sets the pattern at the specified index
     *
     * @param i       the index
     * @param pattern the new pattern
     *
     * @return builder for method chains.
     */
    default B setPattern(final int i, final BannerPattern pattern)
    {
        this.meta().setPattern(i, pattern);
        return this.getBuilder();
    }
}
