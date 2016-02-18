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

package org.diorite.utils.math;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.Validate;

public class DioriteRandom extends Random
{
    private static final long serialVersionUID = 0;

    protected DioriteRandom()
    {
    }

    protected DioriteRandom(final long seed)
    {
        super(seed);
    }

    public <T> T getRand(final T[] array)
    {
        if (array.length == 0)
        {
            return null;
        }
        return array[DioriteRandomUtils.nextInt(array.length)];
    }

    public <T> T getRand(final List<T> coll)
    {
        return coll.get(this.nextInt(coll.size()));
    }

    public <T, E extends Collection<T>> E getRand(final Collection<T> coll, final E target, int amount, final boolean noRepeat)
    {
        if (coll.isEmpty())
        {
            return target;
        }
        final List<T> list = new ArrayList<>(coll);
        if (noRepeat)
        {
            while (! list.isEmpty() && (amount-- > 0))
            {
                target.add(list.remove(this.nextInt(list.size())));
            }
        }
        else
        {
            while (! list.isEmpty() && (amount-- > 0))
            {
                target.add(list.get(this.nextInt(list.size())));
            }
        }
        return target;
    }

    public <T> T getRand(final Collection<T> coll)
    {
        if (coll.isEmpty())
        {
            return null;
        }

        final int index = this.nextInt(coll.size());
        if (coll instanceof List)
        {
            return ((List<? extends T>) coll).get(index);
        }
        else
        {
            final Iterator<? extends T> iter = coll.iterator();
            for (int i = 0; i < index; i++)
            {
                iter.next();
            }
            return iter.next();
        }
    }

    public long getRandLongSafe(final long a, final long b)
    {
        if (a > b)
        {
            return this.getRandLong(b, a);
        }
        return this.getRandLong(a, b);
    }

    public int getRandIntSafe(final int a, final int b)
    {
        return (int) this.getRandLongSafe(a, b);
    }

    public double getRandDoubleSafe(final double a, final double b)
    {
        if (a > b)
        {
            return this.getRandDouble(b, a);
        }
        return this.getRandDouble(a, b);
    }

    public float getRandFloatSafe(final float a, final float b)
    {
        if (a > b)
        {
            return this.getRandFloat(b, a);
        }
        return this.getRandFloat(a, b);
    }

    public long getRandLong(final long min, final long max) throws IllegalArgumentException
    {
        if (min == max)
        {
            return max;
        }
        Validate.isTrue(max > min, "Max can't be smaller than min!");
        return (Math.abs(this.nextLong()) % ((max - min) + 1)) + min;
    }

    public int getRandInt(final int min, final int max) throws IllegalArgumentException
    {
        if (min == max)
        {
            return max;
        }
        Validate.isTrue(max > min, "Max can't be smaller than min!");
        return (int) this.getRandLong(min, max);
    }

    public double getRandDouble(final double min, final double max) throws IllegalArgumentException
    {
        if (Double.compare(min, max) == 0)
        {
            return max;
        }
        Validate.isTrue(max > min, "Max can't be smaller than min!");
        return (this.nextDouble() * (max - min)) + min;
    }

    public float getRandFloat(final float min, final float max) throws IllegalArgumentException
    {
        if (Float.compare(min, max) == 0)
        {
            return max;
        }
        Validate.isTrue(max > min, "Max can't be smaller than min!");
        return (this.nextFloat() * (max - min)) + min;
    }

    public boolean getChance(final double chance)
    {
        return (chance > 0) && ((chance >= 100) || (chance >= this.getRandDouble(0, 100)));
    }

    public int sumWeight(final Iterable<? extends IWeightedRandomChoice> choices)
    {
        int i = 0;
        for (final IWeightedRandomChoice choice : choices)
        {
            i += choice.getWeight();
        }
        return i;
    }

    public <T extends IWeightedRandomChoice> T getWeightedRandom(final Iterable<? extends T> choices, final int weight)
    {
        if (weight <= 0)
        {
            throw new IllegalArgumentException("Weight must be greater than 0.");
        }
        return this.getWeightedRandomElement(choices, this.nextInt(weight));
    }

    public <T extends IWeightedRandomChoice> T getWeightedRandomElement(final Iterable<? extends T> choices, int weight)
    {
        for (final T choice : choices)
        {
            weight -= choice.getWeight();
            if (weight < 0)
            {
                return choice;
            }
        }
        return null;
    }

    public <T extends IWeightedRandomChoice> T getWeightedRandom(final Iterable<? extends T> choices)
    {
        return this.getWeightedRandom(choices, this.sumWeight(choices));
    }
}
