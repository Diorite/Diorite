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

package org.diorite.impl.world.generator.structures;

import java.util.Random;

import org.diorite.BlockLocation;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.world.chunk.ChunkPos;
import org.diorite.world.generator.structures.Structure;

@SuppressWarnings("MagicNumber")
public class TestStructure implements Structure
{
    @Override
    public boolean generate(final ChunkPos pos, final Random random, final BlockLocation location)
    {
        BlockMaterialData wool1;
        BlockMaterialData wool2;
        final Material[] values = BlockMaterialData.values();
        do
        {
            wool1 = (BlockMaterialData) values[random.nextInt(values.length)];
            wool2 = (BlockMaterialData) values[random.nextInt(values.length)];
            final BlockMaterialData[] w1t = wool1.types();
            final BlockMaterialData[] w2t = wool2.types();
            if (w1t.length > 1)
            {
                wool1 = w1t[random.nextInt(w1t.length)];
            }
            if (w2t.length > 1)
            {
                wool2 = w2t[random.nextInt(w2t.length)];
            }
        } while ((wool1 == null) || (wool2 == null) || ! wool1.isSolid() || ! wool2.isSolid() || wool1.equals(wool2));
        boolean s = false;
        for (int x = - 1; x <= 16; x++)
        {
            this.setBlock(location, x, 0, - 1, (s = ! s) ? wool1 : wool2);
        }
        for (int z = 0; z <= 15; z++)
        {
            this.setBlock(location, 16, 0, z, (s = ! s) ? wool1 : wool2);
        }
        for (int x = 16; x >= - 1; x--)
        {
            this.setBlock(location, x, 0, 16, (s = ! s) ? wool1 : wool2);
        }
        for (int z = 15; z >= 0; z--)
        {
            this.setBlock(location, - 1, 0, z, (s = ! s) ? wool1 : wool2);
        }
        return true;
    }
}
