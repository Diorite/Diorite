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

package org.diorite.core.material;

import javax.annotation.Nullable;

import org.diorite.material.BlockRegistry;
import org.diorite.registry.AbstractRegistry;
import org.diorite.registry.GameId;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

@SuppressWarnings("MagicNumber")
public class InternalBlockRegistry extends AbstractRegistry<SimpleBlockType> implements BlockRegistry<SimpleBlockType>
{
    private final Int2ObjectMap<SimpleBlockType> minecraftMappings = new Int2ObjectOpenHashMap<>(200);

    public int getInternalSize()
    {
        return this.minecraftMappings.size();
    }

    @Nullable
    public InternalBlockType getByMinecraftIdAndData(int id)
    {
        return this.minecraftMappings.get(id);
    }

    @Nullable
    public InternalBlockType getByMinecraftIdAndData(int id, int data)
    {
        return this.getByMinecraftIdAndData((id << 4) | data);
    }

    @Override
    protected void onRegister(GameId key, SimpleBlockType value)
    {
        // TODO: register subtypes
        this.minecraftMappings.put(value.getMinecraftIdAndData(), value);
    }
}
