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

package org.diorite.entity;

import java.util.Collection;
import java.util.UUID;

import org.diorite.Particle;
import org.diorite.effect.StatusEffect;
import org.diorite.effect.StatusEffectType;
import org.diorite.utils.Color;
import org.diorite.utils.others.Colorable;

/**
 * Represent area effect entity. <br>
 * Area effect entity is a area filled up with selected type of particles
 * that can interact with other entites, like by giving them a status effect.
 */
public interface AreaEffectCloud extends ObjectEntity, Colorable
{
    /**
     * Returns duration of effect, in 1/100 of second. <br>
     * Entity will be removed when {@link #getAge()} hit this number entity will be removed.
     *
     * @return duration of effect, in 1/100 of second.
     */
    int getDuration();

    /**
     * Set duration of effect, in 1/100 of second. <br>
     * Entity will be removed when {@link #getAge()} hit this number entity will be removed.
     *
     * @param duration duration of effect, in 1/100 of second.
     */
    void setDuration(int duration); // in 1/100 of second

    /**
     * Returns how often (in 1/100th of second) effect should be appiled to entites in area.
     *
     * @return how often (in 1/100th of second) effect should be appiled to entites in area.
     */
    int getReapplicationDelay();

    /**
     * Sets how often (in 1/100th of second) effect should be appiled to entites in area.
     *
     * @param delay dealy in 1/100th of second.
     */
    void setReapplicationDelay(int delay);

    int getWaitTime();

    void setWaitTime(int waitTime);

    UUID getOwner();

    void setOwner(UUID owner);

    double getDurationOnUse();

    void setDurationOnUse(double durationOnUse);

    double getRadiusOnUse();

    void setRadiusOnUse(double radiusOnUse);

    double getRadiusPerCentisecond();

    void setRadiusPerCentisecond(double radius);

    /**
     * Returns default potion effect of this effect cloud, see {@link org.diorite.inventory.item.meta.PotionMeta.PotionTypes}
     *
     * @return default potion effect of this effect cloud.
     */
    String getDefaultPotionEffect();

    /**
     * Sets default potion effect of this effect cloud, see {@link org.diorite.inventory.item.meta.PotionMeta.PotionTypes}
     *
     * @param effect name of effect.
     */
    void setDefaultPotionEffect(String effect);

    /**
     * Returns radius of effect cloud.
     *
     * @return radius of effect cloud.
     */
    double getRadius();

    /**
     * Sets new radius of effect, bigger radius may affect performance of server.
     *
     * @param r new radius of effect.
     */
    void setRadius(double r);

    /**
     * Returns color of effect, used only for {@link Particle#SPELL_MOB}
     *
     * @return color of effect, used only for {@link Particle#SPELL_MOB}
     */
    @Override
    Color getColor();

    /**
     * Set color of effect,  used only for {@link Particle#SPELL_MOB}
     *
     * @param color new color to be used.
     */
    @Override
    void setColor(Color color);

    /**
     * Returns if effect is visible only as single point and not area. <br>
     * Effect will still apply to all entites in radius.
     *
     * @return if effect is visible only as single point and not area.
     */
    boolean isPointEffect();

    /**
     * Sets if effect is visible only as single point and not area. <br>
     * Effect will still apply to all entites in radius.
     *
     * @param pointEffect if effect should be a point.
     */
    void setPointEffect(boolean pointEffect);

    /**
     * Returns particle used by this effect cloud. {@link Particle#SPELL_MOB} is default one.
     *
     * @return particle used by this effect cloud.
     */
    Particle getParticle();

    /**
     * Sets partcile of this effect cloud, {@link Particle#SPELL_MOB} is default one.
     *
     * @param particle new particle of effect cloud.
     */
    void setParticle(Particle particle);

    /**
     * Returns collection of status effect used by this effect cloud.
     *
     * @return collection of status effect used by this effect cloud.
     */
    Collection<StatusEffect> getStatusEffects();

    /**
     * Adds new effect to this effect cloud,
     * if effect of given type already exists it will be replaced with new one.
     *
     * @param effect new status effect to add.
     */
    default void addStatusEffect(final StatusEffect effect)
    {
        this.addStatusEffect(effect, false);
    }

    /**
     * Adds new effect to this effect cloud.
     *
     * @param effect          new status effect to add.
     * @param allowDuplicates if true effect will be added even if there is already effect of this same type, if false then old effect will be replaced with new one.
     */
    void addStatusEffect(StatusEffect effect, boolean allowDuplicates);

    /**
     * Removes all effects of given type.
     *
     * @param effectType effect to remove.
     *
     * @return true if any effect was removed.
     */
    boolean removeStatusEffect(StatusEffectType effectType);

    /**
     * Clear/Removes all effects.
     */
    void clearStatusEffects();

    /**
     * Returns entites in area of this area effect cloud.
     *
     * @return entites in area of this area effect cloud.
     */
    default Collection<? extends Entity> getEntitesInRadius()
    {
        return this.getNearbyEntities(0, 0, 0);
    }
}
