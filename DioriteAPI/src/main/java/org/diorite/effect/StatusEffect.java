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

package org.diorite.effect;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.nbt.NbtSerializable;
import org.diorite.nbt.NbtTagCompound;

/**
 * Represent status effect of some type, power and duration.
 */
public class StatusEffect implements NbtSerializable
{
    private final StatusEffectType type;
    private final int              amplifier;
    private final int              duration;
    private final boolean          ambient;
    private final boolean          showParticles;

    /**
     * Construct new StatusEffect of given type.
     *
     * @param type          type of effect.
     * @param amplifier     power/strength of effect. {@link #getAmplifier()}
     * @param duration      duration of effect (in 1/100 of second).
     * @param ambient       makes potion effect produce more, translucent, particles.
     * @param showParticles if effect should produce any particles.
     */
    public StatusEffect(final StatusEffectType type, final int amplifier, final int duration, final boolean ambient, final boolean showParticles)
    {
        this.type = type;
        this.amplifier = amplifier;
        this.duration = duration;
        this.ambient = ambient;
        this.showParticles = showParticles;
    }

    /**
     * Deserialize StatusEffect from {@link NbtTagCompound}.
     *
     * @param tag data to deserialize.
     */
    public StatusEffect(final NbtTagCompound tag)
    {
        this.type = StatusEffectType.getByTypeID(tag.getByte("Id", 1));
        this.amplifier = tag.getByte("Amplifier");
        this.duration = tag.getInt("Duration") * 5;
        this.ambient = tag.getBoolean("Ambient");
        this.showParticles = tag.getBoolean("ShowParticles");
    }

    /**
     * Returns type of this status effect.
     *
     * @return type of this status effect.
     */
    public StatusEffectType getType()
    {
        return this.type;
    }

    /**
     * Returns the amplifier of this effect. A higher amplifier means the
     * potion effect happens more often over its duration and in some cases
     * has more effect on its target.
     *
     * @return The effect amplifier
     */
    public int getAmplifier()
    {
        return this.amplifier;
    }

    /**
     * Returns duration of effect in 1/100 of second.
     *
     * @return duration of effect in 1/100 of second.
     */
    public int getDuration()
    {
        return this.duration;
    }

    /**
     * Makes potion effect produce more, translucent, particles.
     *
     * @return if this effect is ambient
     */
    public boolean isAmbient()
    {
        return this.ambient;
    }

    /**
     * Returns if effect should produce any particles.
     *
     * @return if effect should produce any particles.
     */
    public boolean isShowParticles()
    {
        return this.showParticles;
    }

    @Override
    public NbtTagCompound serializeToNBT()
    {
        final NbtTagCompound tag = new NbtTagCompound();
        tag.setByte("Id", this.type.getTypeID());
        tag.setByte("Amplifier", this.amplifier);
        tag.setInt("Duration", this.duration / 5);
        tag.setBoolean("Ambient", this.ambient);
        tag.setBoolean("ShowParticles", this.showParticles);
        return tag;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("type", this.type).append("amplifier", this.amplifier).append("duration", this.duration).append("ambient", this.ambient).append("showParticles", this.showParticles).toString();
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof StatusEffect))
        {
            return false;
        }

        final StatusEffect that = (StatusEffect) o;

        return (this.amplifier == that.amplifier) && (this.duration == that.duration) && (this.ambient == that.ambient) && (this.showParticles == that.showParticles) && this.type.equals(that.type);
    }

    @Override
    public int hashCode()
    {
        int result = this.type.hashCode();
        result = (31 * result) + this.amplifier;
        result = (31 * result) + this.duration;
        result = (31 * result) + (this.ambient ? 1 : 0);
        result = (31 * result) + (this.showParticles ? 1 : 0);
        return result;
    }
}
