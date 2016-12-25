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

package org.diorite.commons.math;

import javax.annotation.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;

import it.unimi.dsi.fastutil.doubles.Double2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

public class DioriteRandom extends Random
{
    private static final long serialVersionUID = 0;

    protected DioriteRandom()
    {
    }

    protected DioriteRandom(long seed)
    {
        super(seed);
    }

    @Nullable
    public <T> T getRandom(T[] array)
    {
        return DioriteRandomUtils.getRandom(this, array);
    }

    @Nullable
    public <T> T getRandom(List<T> coll)
    {
        return DioriteRandomUtils.getRandom(this, coll);
    }

    public <T, E extends Collection<T>> E getRandom(Collection<T> coll, E target, int amount, boolean noRepeat)
    {
        return DioriteRandomUtils.getRandom(this, coll, target, amount, noRepeat);
    }

    @Nullable
    public <T> T getRandom(Collection<T> coll)
    {
        return DioriteRandomUtils.getRandom(this, coll);
    }

    public long getRandomLongSafe(long a, long b)
    {
        return DioriteRandomUtils.getRandomLongSafe(this, a, b);
    }

    public int getRandomIntSafe(int a, int b)
    {
        return DioriteRandomUtils.getRandomIntSafe(this, a, b);
    }

    public double getRandomDoubleSafe(double a, double b)
    {
        return DioriteRandomUtils.getRandomDoubleSafe(this, a, b);
    }

    public float getRandomFloatSafe(float a, float b)
    {
        return DioriteRandomUtils.getRandomFloatSafe(this, a, b);
    }

    public long getRandomLong(long min, long max) throws IllegalArgumentException
    {
        return DioriteRandomUtils.getRandomLong(this, min, max);
    }

    public int getRandomInt(int min, int max) throws IllegalArgumentException
    {
        return DioriteRandomUtils.getRandomInt(this, min, max);
    }

    public double getRandomDouble(double min, double max) throws IllegalArgumentException
    {
        return DioriteRandomUtils.getRandomDouble(this, min, max);
    }

    public float getRandomFloat(float min, float max) throws IllegalArgumentException
    {
        return DioriteRandomUtils.getRandomFloat(this, min, max);
    }

    public boolean getChance(double chance)
    {
        return DioriteRandomUtils.getChance(this, chance);
    }

    @Nullable
    public <T extends IWeightedRandomChoice> T getWeightedRandom(Iterable<? extends T> choices)
    {
        return DioriteRandomUtils.getWeightedRandom(this, choices);
    }

    @Nullable
    public <T> T getWeightedRandomReversed(Map<? extends Number, ? extends T> choices)
    {
        return DioriteRandomUtils.getWeightedRandomReversed(this, choices);
    }

    @Nullable
    public <T> T getWeightedRandomReversed(Double2ObjectMap<T> choices)
    {
        return DioriteRandomUtils.getWeightedRandomReversed(this, choices);
    }

    @Nullable
    public <T> T getWeightedRandomReversed(Int2ObjectMap<T> choices)
    {
        return DioriteRandomUtils.getWeightedRandomReversed(this, choices);
    }

    @Nullable
    public <T> T getWeightedRandom(Map<? extends T, ? extends Number> choices)
    {
        return DioriteRandomUtils.getWeightedRandom(this, choices);
    }

    @Nullable
    public <T> T getWeightedRandom(Object2DoubleMap<T> choices)
    {
        return DioriteRandomUtils.getWeightedRandom(this, choices);
    }

    @Nullable
    public <T> T getWeightedRandom(Object2IntMap<T> choices)
    {
        return DioriteRandomUtils.getWeightedRandom(this, choices);
    }
}
