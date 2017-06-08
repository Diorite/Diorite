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

package org.diorite.impl.material;

import org.diorite.impl.DioriteServer;
import org.diorite.core.material.InternalBlockRegistry;
import org.diorite.core.material.SimpleBlockType;
import org.diorite.registry.GameId;

public class BlocksInit
{
    private final DioriteServer         dioriteServer;
    private final InternalBlockRegistry registry;

    public BlocksInit(DioriteServer dioriteServer, InternalBlockRegistry registry)
    {
        this.dioriteServer = dioriteServer;
        this.registry = registry;
    }

    public void init()
    {
        this.dioriteServer.debug("Started initialization of blocks...");
        this.registry.register(new SimpleBlockType(GameId.ofMinecraft("air"), 0, 0));
        this.registry.register(new SimpleBlockType(GameId.ofMinecraft("stone"), 1, 0));
        this.registry.register(new SimpleBlockType(GameId.ofMinecraft("grass"), 2, 0));
        this.registry.register(new SimpleBlockType(GameId.ofMinecraft("dirt"), 3, 0));
    }
}
