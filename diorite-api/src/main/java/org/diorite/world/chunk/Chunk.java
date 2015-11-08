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

package org.diorite.world.chunk;

import org.diorite.material.BlockMaterialData;
import org.diorite.scheduler.Synchronizable;
import org.diorite.world.Biome;
import org.diorite.world.Block;
import org.diorite.world.World;

public interface Chunk extends Synchronizable
{
    int CHUNK_SIZE        = 16;
    int CHUNK_PARTS       = 16;
    int CHUNK_PART_HEIGHT = 16;
    int CHUNK_FULL_HEIGHT = 256;
    int CHUNK_BIOMES_SIZE = CHUNK_SIZE * CHUNK_SIZE;

    Biome getBiome(int x, int y, int z) // y is ignored, added for future possible changes.
    ;

    boolean isLoaded();

    boolean load();

    boolean load(boolean generate);

    boolean unload();

    boolean unload(boolean save);

    boolean unload(boolean save, boolean safe);

    boolean isPopulated();

    boolean populate();

    void initHeightMap();

//    BlockMaterialData setBlock(int x, int y, int z, BlockMaterialData materialData);
//
//    BlockMaterialData setBlock(int x, int y, int z, int id, int meta);

    BlockMaterialData getBlockType(int x, int y, int z);

    BlockMaterialData getHighestBlockType(int x, int z);

    Block getBlock(int x, int y, int z);

    Block getHighestBlock(int x, int z);

    int getHighestBlockY(int x, int z);

    World getWorld();

    ChunkPos getPos();

    int getX();

    int getZ();

//    void recalculateBlockCounts();
}
