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

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.collect.ImmutableList;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.nbt.NbtSerializable;
import org.diorite.nbt.NbtTagCompound;
import org.diorite.utils.Color;

/**
 * Represents a single firework effect. <br>
 * Based on bukkit project.
 */
public class FireworkEffect implements NbtSerializable
{
    private final boolean              flicker;
    private final boolean              trail;
    private final ImmutableList<Color> colors;
    private final ImmutableList<Color> fadeColors;
    private final FireworkEffectType   type;

    FireworkEffect(final boolean flicker, final boolean trail, final ImmutableList<Color> colors, final ImmutableList<Color> fadeColors, final FireworkEffectType type)
    {
        if (colors.isEmpty())
        {
            throw new IllegalStateException("Cannot make FireworkEffect without any color");
        }
        this.flicker = flicker;
        this.trail = trail;
        this.colors = colors;
        this.fadeColors = fadeColors;
        this.type = type;
    }

    /**
     * Construct new FireworkEffect.
     *
     * @param flicker    if the firework has flicker effect.
     * @param trail      if the firework has trail effect.
     * @param colors     the primary colors of the firework effect.
     * @param fadeColors the fade colors of the firework effect.
     * @param type       type of firework effect.
     */
    public FireworkEffect(final boolean flicker, final boolean trail, final Collection<Color> colors, final Collection<Color> fadeColors, final FireworkEffectType type)
    {
        Validate.notNull(colors, "Colors can't be null");
        Validate.notNull(type, "Type can't be null");
        if (colors.isEmpty())
        {
            throw new IllegalStateException("Cannot make FireworkEffect without any color");
        }
        this.flicker = flicker;
        this.trail = trail;
        this.colors = ImmutableList.copyOf(colors);
        this.fadeColors = (fadeColors == null) ? ImmutableList.of() : ImmutableList.copyOf(fadeColors);
        this.type = type;
    }

    /**
     * Deserialize FireworkEffect from {@link NbtTagCompound}.
     *
     * @param tag data to deserialize.
     */
    public FireworkEffect(final NbtTagCompound tag)
    {
        this.flicker = tag.getBoolean("Flicker");
        this.trail = tag.getBoolean("Trail");
        this.colors = ImmutableList.copyOf(IntStream.of(tag.getIntArray("Colors")).mapToObj(Color::fromRGB).collect(Collectors.toList()));
        this.fadeColors = tag.containsTag("FadeColors") ? ImmutableList.copyOf(IntStream.of(tag.getIntArray("FadeColors")).mapToObj(Color::fromRGB).collect(Collectors.toList())) : ImmutableList.of();
        this.type = FireworkEffectType.getByTypeID(tag.getByte("Type"));
    }

    /**
     * Get if the firework has flicker effect.
     *
     * @return true if it flickers.
     */
    public boolean hasFlicker()
    {
        return this.flicker;
    }

    /**
     * Get if the firework has trail effect.
     *
     * @return true if it has a trail.
     */
    public boolean hasTrail()
    {
        return this.trail;
    }

    /**
     * Get the primary colors of the firework effect.
     *
     * @return An immutable list of the primary colors
     */
    public List<Color> getColors()
    {
        return this.colors;
    }

    /**
     * Get the fade colors of the firework effect.
     *
     * @return An immutable list of the fade colors
     */
    public List<Color> getFadeColors()
    {
        return this.fadeColors;
    }

    /**
     * Get the type of the firework effect.
     *
     * @return The effect type
     */
    public FireworkEffectType getType()
    {
        return this.type;
    }


    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("flicker", this.flicker).append("trail", this.trail).append("colors", this.colors).append("fadeColors", this.fadeColors).append("type", this.type).toString();
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof FireworkEffect))
        {
            return false;
        }

        final FireworkEffect that = (FireworkEffect) o;

        return (this.flicker == that.flicker) && (this.trail == that.trail) && this.colors.equals(that.colors) && this.fadeColors.equals(that.fadeColors) && this.type.equals(that.type);
    }

    @Override
    public int hashCode()
    {
        int result = (this.flicker ? 1 : 0);
        result = (31 * result) + (this.trail ? 1 : 0);
        result = (31 * result) + this.colors.hashCode();
        result = (31 * result) + this.fadeColors.hashCode();
        result = (31 * result) + this.type.hashCode();
        return result;
    }

    @Override
    public NbtTagCompound serializeToNBT()
    {
        final NbtTagCompound nbt = new NbtTagCompound("Explosion");
        nbt.setBoolean("Flicker", this.flicker);
        nbt.setBoolean("Trail", this.trail);
        nbt.setByte("Type", this.type.getTypeID());
        nbt.setIntArray("Colors", this.colors.stream().mapToInt(Color::asRGB).toArray());
        if ((this.fadeColors != null) && ! this.fadeColors.isEmpty())
        {
            nbt.setIntArray("FadeColors", this.fadeColors.stream().mapToInt(Color::asRGB).toArray());
        }
        return nbt;
    }

    /**
     * Construct a firework effect.
     *
     * @param type The effect type
     *
     * @return A utility object for building a firework effect
     */
    public static FireworkEffectBuilder builder(final FireworkEffectType type)
    {
        return new FireworkEffectBuilder(type);
    }

    /**
     * Construct a firework effect.
     *
     * @return A utility object for building a firework effect
     */
    public static FireworkEffectBuilder builder()
    {
        return new FireworkEffectBuilder(FireworkEffectType.BALL);
    }
}
