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

package org.diorite.cfg.magic;

@SuppressWarnings({"MagicNumber"})
public final class MagicNumbers
{
    /*
     * ==================
     * ...
     * ==================
     */

    @MagicNumber("items.defaultStackSize")
    public static final int ITEMS__DEFAULT_STACK_SIZE = 64;

    @MagicNumber("player.moveSpeed")
    public static double PLAYER__MOVE_SPEED = 0.10000000149011612;

    /*
     * ==================
     * Attributes section
     * ==================
     */

    @MagicNumber("attributes.modifiers.sprint")
    public static double ATTRIBUTES__MODIFIERS__SPRINT = 0.30000001192092896;

    // attributes/generic.maxHealth
    @MagicNumber("attributes.types.generic.maxHealth.default")
    public static double ATTRIBUTES__GENERIC_MAX_HEALTH__DEFAULT = 20;
    @MagicNumber("attributes.types.generic.maxHealth.min")
    public static double ATTRIBUTES__GENERIC_MAX_HEALTH__MIN     = 0;
    @MagicNumber("attributes.types.generic.maxHealth.max")
    public static double ATTRIBUTES__GENERIC_MAX_HEALTH__MAX     = Integer.MAX_VALUE;

    // attributes/generic.followRange
    @MagicNumber("attributes.types.generic.followRange.default")
    public static double ATTRIBUTES__GENERIC_FOLLOW_RANGE__DEFAULT = 32;
    @MagicNumber("attributes.types.generic.followRange.min")
    public static double ATTRIBUTES__GENERIC_FOLLOW_RANGE__MIN     = 0;
    @MagicNumber("attributes.types.generic.followRange.max")
    public static double ATTRIBUTES__GENERIC_FOLLOW_RANGE__MAX     = 2048;

    // attributes/generic.knockbackResistance
    @MagicNumber("attributes.types.generic.knockbackResistance.default")
    public static double ATTRIBUTES__GENERIC_KNOCKBACK_RESISTANCE__DEFAULT = 0;
    @MagicNumber("attributes.types.generic.knockbackResistance.min")
    public static double ATTRIBUTES__GENERIC_KNOCKBACK_RESISTANCE__MIN     = 0;
    @MagicNumber("attributes.types.generic.knockbackResistance.max")
    public static double ATTRIBUTES__GENERIC_KNOCKBACK_RESISTANCE__MAX     = 1;

    // attributes/generic.movementSpeed
    @MagicNumber("attributes.types.generic.movementSpeed.default")
    public static double ATTRIBUTES__GENERIC_MOVEMENT_SPEED__DEFAULT = 0.699999988079071;
    @MagicNumber("attributes.types.generic.movementSpeed.min")
    public static double ATTRIBUTES__GENERIC_MOVEMENT_SPEED__MIN     = 0;
    @MagicNumber("attributes.types.generic.movementSpeed.max")
    public static double ATTRIBUTES__GENERIC_MOVEMENT_SPEED__MAX     = Double.MAX_VALUE;

    // attributes/generic.attackDamage
    @MagicNumber("attributes.types.generic.attackDamage.default")
    public static double ATTRIBUTES__GENERIC_ATTACK_DAMAGE__DEFAULT = 2;
    @MagicNumber("attributes.types.generic.attackDamage.min")
    public static double ATTRIBUTES__GENERIC_ATTACK_DAMAGE__MIN     = 0;
    @MagicNumber("attributes.types.generic.attackDamage.max")
    public static double ATTRIBUTES__GENERIC_ATTACK_DAMAGE__MAX     = Double.MAX_VALUE;

    // attributes/horse.jumpStrength
    @MagicNumber("attributes.types.horse.jumpStrength.default")
    public static double ATTRIBUTES__HORSE_JUMP_STRENGTH__DEFAULT = 0.7;
    @MagicNumber("attributes.types.horse.jumpStrength.min")
    public static double ATTRIBUTES__HORSE_JUMP_STRENGTH__MIN     = 0;
    @MagicNumber("attributes.types.horse.jumpStrength.max")
    public static double ATTRIBUTES__HORSE_JUMP_STRENGTH__MAX     = 2;

    // attributes/zombie.spawnReinforcements
    @MagicNumber("attributes.types.zombie.spawnReinforcements.default")
    public static double ATTRIBUTES__ZOMBIE_SPAWN_REINFORCMENTS__DEFAULT = 0;
    @MagicNumber("attributes.types.zombie.spawnReinforcements.min")
    public static double ATTRIBUTES__ZOMBIE_SPAWN_REINFORCMENTS__MIN     = 0;
    @MagicNumber("attributes.types.zombie.spawnReinforcements.max")
    public static double ATTRIBUTES__ZOMBIE_SPAWN_REINFORCMENTS__MAX     = 1;


    private MagicNumbers()
    {
    }
}
