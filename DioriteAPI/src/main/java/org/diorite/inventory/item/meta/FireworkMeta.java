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

import java.util.List;

import org.diorite.firework.FireworkEffect;

/**
 * Represents a {@link org.diorite.material.Material#FIREWORKS} and its effects.
 */
public interface FireworkMeta extends ItemMeta
{
    /**
     * Add effect to this firework.
     *
     * @param effect effect to be added.
     *
     * @throws IllegalArgumentException If effect is null
     */
    void addEffect(FireworkEffect effect) throws IllegalArgumentException;

    /**
     * Add effects to this firework.
     *
     * @param effects effects to be added.
     *
     * @throws IllegalArgumentException If effects is null.
     */
    void addEffects(FireworkEffect... effects) throws IllegalArgumentException;

    /**
     * Add effects to this firework.
     *
     * @param effects effects to be added.
     *
     * @throws IllegalArgumentException If effects is null.
     */
    void addEffects(Iterable<FireworkEffect> effects) throws IllegalArgumentException;

    /**
     * Returns the effects in this firework.
     *
     * @return An immutable list of the firework effects
     */
    List<FireworkEffect> getEffects();

    /**
     * Returns the number of effects in this firework.
     *
     * @return the number of effects
     */
    int getEffectsSize();

    /**
     * Remove an effect from this firework.
     *
     * @param index the index of the effect to remove
     *
     * @throws IndexOutOfBoundsException If index {@literal < 0 or index >} {@link #getEffectsSize()}
     */
    void removeEffect(int index) throws IndexOutOfBoundsException;

    /**
     * Remove all effects from this firework.
     */
    void removeEffects();

    /**
     * Returns true if this firework has any effects.
     *
     * @return true if this firework has any effects.
     */
    boolean hasEffects();

    /**
     * Returns the power of firework. <br>
     * Each level of power is half  a second of flight time.
     *
     * @return the power of firework.
     */
    int getPower();

    /**
     * Sets the power of the firework. <br>
     * Each level of power is half  a second of flight time.
     *
     * @param power the power of the firework, from 0-128.
     */
    void setPower(int power);

    @Override
    FireworkMeta clone();
}