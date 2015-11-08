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

package org.diorite.impl.plugin;

import org.diorite.impl.DioriteCore;
import org.diorite.plugin.DioritePlugin;

@SuppressWarnings("RedundantMethodOverride")
public class DioriteMod extends DioritePlugin
{
    /**
     * Invokes very early, in constructor of {@link DioriteCore} <br>
     * It is possible to change init pipeline here: {@link DioriteCore#getInitPipeline()}
     * that contains all actions done when loading basics of diorite.
     */
    @Override
    public void onLoad()
    {
    }

    /**
     * Invokes before start method, still before creating logger, worlds or loading plugins. <br>
     * It is possible to change start pipeline here: {@link DioriteCore#getStartPipeline()} ()}
     * that contains all actions done when starting diorite.
     */
    @Override
    public void onEnable()
    {
    }

    @Override
    public boolean isCoreMod()
    {
        return true;
    }
}
