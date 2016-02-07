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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.Diorite;
import org.diorite.inventory.item.meta.FireworkEffectMeta;

/**
 * Represent builder of firework effect item meta data.
 */
public class FireworkEffectMetaBuilder implements IFireworkEffectMetaBuilder<FireworkEffectMetaBuilder, FireworkEffectMeta>
{
    /**
     * Wrapped item meta used by builder.
     */
    protected final FireworkEffectMeta meta;

    /**
     * Construct new meta builder, based on given meta.
     *
     * @param meta source meta to copy to this builder.
     */
    protected FireworkEffectMetaBuilder(final FireworkEffectMeta meta)
    {
        this.meta = meta.clone();
    }

    /**
     * Construct new meta builder.
     */
    protected FireworkEffectMetaBuilder()
    {
        this.meta = Diorite.getCore().getItemFactory().construct(FireworkEffectMeta.class);
    }

    @Override
    public FireworkEffectMeta meta()
    {
        return this.meta;
    }

    @Override
    public FireworkEffectMetaBuilder getBuilder()
    {
        return this;
    }

    /**
     * Returns new builder of item meta data.
     *
     * @return new builder of item meta data.
     */
    public FireworkEffectMetaBuilder start()
    {
        return new FireworkEffectMetaBuilder();
    }

    /**
     * Returns new builder of item meta data, based on given one.
     *
     * @param meta source meta to copy to new builder.
     *
     * @return new builder of item meta data.
     */
    public FireworkEffectMetaBuilder start(final FireworkEffectMeta meta)
    {
        return new FireworkEffectMetaBuilder(meta);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("meta", this.meta).toString();
    }
}
