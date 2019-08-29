/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.core.world.io.anvil.parallel;

import javax.annotation.Nullable;

import java.io.File;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.core.world.WorldImpl;
import org.diorite.core.world.io.ParallelChunkIOService;
import org.diorite.core.world.io.requests.Request;

public class AnvilParallelIOService implements ParallelChunkIOService
{
    private int maxThreads = 3;

    @Override
    public int getMaxThreads()
    {
        return this.maxThreads;
    }

    @Override
    public void setMaxThreads(int threads)
    {
        this.maxThreads = threads;
    }

    @Override
    public void start(WorldImpl world)
    {

    }

    @Override
    public <OUT, T extends Request<OUT>> T queue(T request, @Nullable Consumer<Request<OUT>> callback)
    {
        throw new AssertionError();
    }

    @Override
    public void await(@Nullable IntConsumer rest, int timer)
    {
        // TODO
    }

    @Override
    public File getWorldDataFolder()
    {
        throw new AssertionError();
    }

    @Override
    public void close(IntConsumer rest)
    {

    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("maxThreads", this.maxThreads).toString();
    }
}