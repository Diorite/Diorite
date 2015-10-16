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

package org.diorite.world.generator;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.world.World;

/**
 * Helper class used to initialize chunk generator for selected world, with selected options
 *
 * @param <T> {@link WorldGenerator} to initialize
 */
public abstract class WorldGeneratorInitializer<T extends WorldGenerator>
{
    /**
     * Queue of all default populators that will be used by generator in valid order
     */
    protected final Queue<ChunkPopulator> populators = new ConcurrentLinkedQueue<>();
    /**
     * Name of world generator, must be unique
     */
    protected final String name;

    /**
     * Create new WorldGeneratorInitializer with selected name
     *
     * @param name Name of world generator, must be unique, pluginname:generator should be used.
     */
    protected WorldGeneratorInitializer(final String name)
    {
        this.name = name;
    }

    /**
     * @return name of this generator
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * This method must return generator for selected world, should not contains any populators.
     * All default populator will be added by {@link #init} method
     *
     * @param world   world that will be used by generator
     * @param options map of options.
     *
     * @return generator without populators
     */
    protected abstract T baseInit(World world, Map<String, Object> options);

    /**
     * This method return ready to use generator, with all default populators.
     * {@link WorldGenerator} is created by {@link #baseInit} method
     *
     * @param world   world that will be used by generator
     * @param options map of options.
     *
     * @return generator with populators
     */
    public T init(final World world, final Map<String, Object> options)
    {
        final T gen = this.baseInit(world, options);
        gen.populators.addAll(this.populators);
        return gen;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof WorldGeneratorInitializer))
        {
            return false;
        }

        //noinspection rawtypes
        final WorldGeneratorInitializer that = (WorldGeneratorInitializer) o;

        return this.name.equals(that.name);

    }

    @Override
    public int hashCode()
    {
        return this.name.hashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).toString();
    }
}
