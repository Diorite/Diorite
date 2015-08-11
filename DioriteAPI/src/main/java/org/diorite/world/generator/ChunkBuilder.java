package org.diorite.world.generator;

import org.diorite.material.BlockMaterialData;
import org.diorite.world.chunk.Chunk;
import org.diorite.world.generator.maplayer.MapLayer;

public interface ChunkBuilder
{
    MapLayer[] getBiomesInput();

    void setBiomesInput(MapLayer[] biomes);

    BiomeGrid getBiomeGrid();

    void setBiomeGrid(BiomeGrid biomeGrid);

    void setBlock(int x, int y, int z, BlockMaterialData materialData);

    void setBlock(int x, int y, int z, int id, int meta);

    BlockMaterialData getBlockType(int x, int y, int z);

    void init(Chunk chunk);
}
