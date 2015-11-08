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

package org.diorite.inventory.item.meta;

import java.util.Collection;
import java.util.List;

import org.diorite.effect.StatusEffect;
import org.diorite.effect.StatusEffectType;

/**
 * Represents a potion ({@link org.diorite.material.Material#POTION}) that can have custom potion effects.
 */
public interface PotionMeta extends ItemMeta
{
    /**
     * Contains possible potion type ids.
     */
    interface PotionTypes
    {
        /**
         * One of potion type, represent potion without type.
         */
        String WATER_BOTTLE                  = "minecraft:water";
        /**
         * One of potion type, represent mundane potion without type.
         */
        String MUNDANE_POTION                = "minecraft:mundane";
        /**
         * One of potion type, represent thick potion without type.
         */
        String THICK_POTION                  = "minecraft:thick";
        /**
         * One of potion type, represent awkward potion without type.
         */
        String AWKWARD_POTION                = "minecraft:awkward";
        /**
         * One of potion type, represent potion of {@link StatusEffectType#NIGHT_VISION} type.
         */
        String NIGHT_VISION_POTION           = "minecraft:night_vision";
        /**
         * One of potion type, represent potion of {@link StatusEffectType#NIGHT_VISION} type. (extended, long version)
         */
        String LONG_NIGHT_VISION_POTION      = "minecraft:long_night_vision";
        /**
         * One of potion type, represent potion of {@link StatusEffectType#INVISIBILITY} type.
         */
        String INVISIBILITY_POTION           = "minecraft:invisibility";
        /**
         * One of potion type, represent potion of {@link StatusEffectType#INVISIBILITY} type. (extended, long version)
         */
        String LONG_INVISIBILITY_POTION      = "minecraft:long_invisibility";
        /**
         * One of potion type, represent potion of {@link StatusEffectType#JUMP_BOOST} type.
         */
        String LEAPING_POTION                = "minecraft:leaping";
        /**
         * One of potion type, represent potion of {@link StatusEffectType#JUMP_BOOST} type. (stronger, level 2 version)
         */
        String STRONG_LEAPING_POTION         = "minecraft:strong_leaping";
        /**
         * One of potion type, represent potion of {@link StatusEffectType#JUMP_BOOST} type. (extended, long version)
         */
        String LONG_LEAPING_POTION           = "minecraft:long_leaping";
        /**
         * One of potion type, represent potion of {@link StatusEffectType#FIRE_RESISTANCE} type.
         */
        String FIRE_RESISTANCE_POTION        = "minecraft:fire_resistance";
        /**
         * One of potion type, represent potion of {@link StatusEffectType#FIRE_RESISTANCE} type. (extended, long version)
         */
        String LONG_FIRE_RESISTANCE_POTION   = "minecraft:long_fire_resistance";
        /**
         * One of potion type, represent potion of {@link StatusEffectType#SPEED} type.
         */
        String SWIFTNESS_POTION              = "minecraft:swiftness";
        /**
         * One of potion type, represent potion of {@link StatusEffectType#SPEED} type. (stronger, level 2 version)
         */
        String STRONG_SWIFTNESS_POTION       = "minecraft:strong_swiftness";
        /**
         * One of potion type, represent potion of {@link StatusEffectType#SPEED} type. (extended, long version)
         */
        String LONG_SWIFTNESS_POTION         = "minecraft:long_swiftness";
        /**
         * One of potion type, represent potion of {@link StatusEffectType#SLOWNESS} type.
         */
        String SLOWNESS_POTION               = "minecraft:slowness";
        /**
         * One of potion type, represent potion of {@link StatusEffectType#SLOWNESS} type. (extended, long version)
         */
        String LONG_SLOWNESS_POTION          = "minecraft:long_slowness";
        /**
         * One of potion type, represent potion of {@link StatusEffectType#WATER_BREATHING} type.
         */
        String WATER_BREATHING_POTION        = "minecraft:water_breathing";
        /**
         * One of potion type, represent potion of {@link StatusEffectType#WATER_BREATHING} type. (extended, long version)
         */
        String LONG_WATER_BREATHING_POTION   = "minecraft:long_water_breathing";
        /**
         * One of potion type, represent potion of {@link StatusEffectType#INSTANT_HEAL} type.
         */
        String INSTANT_HEALTH_POTION         = "minecraft:healing";
        /**
         * One of potion type, represent potion of {@link StatusEffectType#INSTANT_HEAL} type. (stronger, level 2 version)
         */
        String STRONG_INSTANT_HEALTH_POTION  = "minecraft:strong_healing";
        /**
         * One of potion type, represent potion of {@link StatusEffectType#INSTANT_DAMAGE} type.
         */
        String INSTANT_HARMING_POTION        = "minecraft:harming";
        /**
         * One of potion type, represent potion of {@link StatusEffectType#INSTANT_DAMAGE} type. (stronger, level 2 version)
         */
        String STRONG_INSTANT_HARMING_POTION = "minecraft:strong_harming";
        /**
         * One of potion type, represent potion of {@link StatusEffectType#POISON} type.
         */
        String POISON_POTION                 = "minecraft:poison";
        /**
         * One of potion type, represent potion of {@link StatusEffectType#POISON} type. (stronger, level 2 version)
         */
        String STRONG_POISON_POTION          = "minecraft:strong_poison";
        /**
         * One of potion type, represent potion of {@link StatusEffectType#POISON} type. (extended, long version)
         */
        String LONG_POISON_POTION            = "minecraft:long_poison";
        /**
         * One of potion type, represent potion of {@link StatusEffectType#REGENERATION} type.
         */
        String REGENERATION_POTION           = "minecraft:regeneration";
        /**
         * One of potion type, represent potion of {@link StatusEffectType#REGENERATION} type. (stronger, level 2 version)
         */
        String STRONG_REGENERATION_POTION    = "minecraft:strong_regeneration";
        /**
         * One of potion type, represent potion of {@link StatusEffectType#REGENERATION} type. (extended, long version)
         */
        String LONG_REGENERATION_POTION      = "minecraft:long_regeneration";
        /**
         * One of potion type, represent potion of {@link StatusEffectType#STRENGTH} type.
         */
        String STRENGTH_POTION               = "minecraft:strength";
        /**
         * One of potion type, represent potion of {@link StatusEffectType#STRENGTH} type. (stronger, level 2 version)
         */
        String STRONG_STRENGTH_POTION        = "minecraft:strong_strength";
        /**
         * One of potion type, represent potion of {@link StatusEffectType#STRENGTH} type. (extended, long version)
         */
        String LONG_STRENGTH_POTION          = "minecraft:long_strength";
        /**
         * One of potion type, represent potion of {@link StatusEffectType#WEAKNESS} type.
         */
        String WEAKNESS_POTION               = "minecraft:weakness";
        /**
         * One of potion type, represent potion of {@link StatusEffectType#WEAKNESS} type. (extended, long version)
         */
        String LONG_WEAKNESS_POTION          = "minecraft:long_weakness";
        /**
         * One of potion type, represent potion of {@link StatusEffectType#LUCK} type.
         */
        String LUCK_POTION                   = "minecraft:luck";
    }

    /**
     * Checks for the presence of custom potion effects.
     *
     * @return true if custom potion effects are applied
     */
    boolean hasCustomEffects();

    /**
     * Gets an immutable list containing all custom potion effects applied to this potion.
     *
     * @return the immutable list of custom potion effects
     */
    List<StatusEffect> getCustomEffects();

    /**
     * Set custom effects of this potion.
     *
     * @param effects collection of effects to set.
     */
    void setCustomEffects(Collection<StatusEffect> effects);

    /**
     * Adds a custom potion effect to this potion.
     *
     * @param effect    the potion effect to add
     * @param overwrite true if any existing effect of the same type should be overwritten
     *
     * @return true if the potion meta changed as a result of this call
     */
    boolean addCustomEffect(StatusEffect effect, boolean overwrite);

    /**
     * Removes a custom potion effect from this potion.
     *
     * @param type the potion effect type to remove
     *
     * @return true if the potion meta changed as a result of this call
     */
    boolean removeCustomEffect(StatusEffectType type);

    /**
     * Checks for a specific custom potion effect type on this potion.
     *
     * @param type the potion effect type to check for
     *
     * @return true if the potion has this effect
     */
    boolean hasCustomEffect(StatusEffectType type);

//    /**
//     * Moves a potion effect to the top of the potion effect list.
//     * <br>
//     * This causes the client to display the potion effect in the potion's name.
//     *
//     * @param type the potion effect type to move
//     *
//     * @return true if the potion meta changed as a result of this call
//     */
//    boolean setMainEffect(StatusEffectType type);

    /**
     * Removes all custom potion effects from this potion.
     *
     * @return true if the potion meta changed as a result of this call
     */
    boolean clearCustomEffects();

    /**
     * Returns string id of potion, used to set display name etc...
     *
     * @return string id of potion.
     */
    String getPotionID();

    /**
     * Set string id of potion, used to set display name etc... <br>
     * This class contains variables with possible potion ids. {@link PotionTypes}
     *
     * @param id new id of potion.
     */
    void setPotionID(String id);

    @Override
    PotionMeta clone();
}
