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

package org.diorite.firework;

import com.google.common.collect.ImmutableList;

import org.apache.commons.lang3.Validate;

import org.diorite.utils.Color;

/**
 * This is a builder for FireworkEffects.
 *
 * @see FireworkEffect#builder()
 */
public class FireworkEffectBuilder
{
    private       boolean                      flicker    = false;
    private       boolean                      trail      = false;
    private final ImmutableList.Builder<Color> colors     = ImmutableList.builder();
    private final ImmutableList.Builder<Color> fadeColors = ImmutableList.builder();
    private final FireworkEffectType type;

    /**
     * Start new builder with given firework effect type.
     *
     * @param type The effect type
     */
    protected FireworkEffectBuilder(final FireworkEffectType type)
    {
        this.type = type;
    }

    /**
     * Add a primary color to the firework effect.
     *
     * @param color The color to add
     *
     * @return This object, for method chains.
     *
     * @throws IllegalArgumentException If color is null
     */
    public FireworkEffectBuilder withColor(final Color color) throws IllegalArgumentException
    {
        Validate.notNull(color, "Color can't be null");
        this.colors.add(color);
        return this;
    }

    /**
     * Add a flicker to the firework effect.
     *
     * @return This object, for method chains.
     */
    public FireworkEffectBuilder withFlicker()
    {
        this.flicker = true;
        return this;
    }

    /**
     * Set whether the firework effect should flicker.
     *
     * @param flicker true if it should flicker.
     *
     * @return This object, for method chains.
     */
    public FireworkEffectBuilder flicker(final boolean flicker)
    {
        this.flicker = flicker;
        return this;
    }

    /**
     * Add a trail to the firework effect.
     *
     * @return This object, for method chains.
     */
    public FireworkEffectBuilder withTrail()
    {
        this.trail = true;
        return this;
    }

    /**
     * Set whether the firework effect should have a trail.
     *
     * @param trail true if it should have a trail.
     *
     * @return This object, for method chains.
     */
    public FireworkEffectBuilder trail(final boolean trail)
    {
        this.trail = trail;
        return this;
    }

    /**
     * Add several primary colors to the firework effect.
     *
     * @param colors The colors to add
     *
     * @return This object, for method chains.
     *
     * @throws IllegalArgumentException If colors is null.
     */
    public FireworkEffectBuilder withColor(final Color... colors) throws IllegalArgumentException
    {
        Validate.notNull(colors, "Colors can't be null");
        for (final Color color : colors)
        {
            this.colors.add(color);
        }
        return this;
    }

    /**
     * Add primary colors to the firework effect.
     *
     * @param colors colors to be added.
     *
     * @return This object, for method chains.
     *
     * @throws IllegalArgumentException If colors is null
     */
    public FireworkEffectBuilder withColor(final Iterable<Color> colors) throws IllegalArgumentException
    {
        Validate.notNull(colors, "Colors can't be null");
        for (final Color color : colors)
        {
            this.colors.add(color);
        }
        return this;
    }

    /**
     * Add a fade color to the firework effect.
     *
     * @param color color to be added.
     *
     * @return This object, for method chains.
     *
     * @throws IllegalArgumentException If colors is null.
     */
    public FireworkEffectBuilder withFade(final Color color) throws IllegalArgumentException
    {
        Validate.notNull(color, "Color can't be null");
        this.fadeColors.add(color);
        return this;
    }

    /**
     * Add fade colors to the firework effect.
     *
     * @param colors colors to be added.
     *
     * @return This object, for method chains.
     *
     * @throws IllegalArgumentException If colors is null.
     */
    public FireworkEffectBuilder withFade(final Color... colors) throws IllegalArgumentException
    {
        Validate.notNull(colors, "Colors can't be null");
        for (final Color color : colors)
        {
            this.fadeColors.add(color);
        }
        return this;
    }

    /**
     * Add fade colors to the firework effect.
     *
     * @param colors colors to be added.
     *
     * @return This object, for method chains.
     *
     * @throws IllegalArgumentException If colors is null.
     */
    public FireworkEffectBuilder withFade(final Iterable<Color> colors) throws IllegalArgumentException
    {
        Validate.notNull(colors, "Colors can't be null");
        for (final Color color : colors)
        {
            this.fadeColors.add(color);
        }
        return this;
    }

    /**
     * Create a {@link FireworkEffect} from the current contents of this builder.
     * <br>
     * Builder mus contains at least one color.
     *
     * @return The representative firework effect
     */
    public FireworkEffect build()
    {
        return new FireworkEffect(this.flicker, this.trail, this.colors.build(), this.fadeColors.build(), this.type);
    }
}
