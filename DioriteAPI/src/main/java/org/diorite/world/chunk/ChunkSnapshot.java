package org.diorite.world.chunk;

import org.diorite.material.BlockMaterialData;
import org.diorite.world.Biome;

public interface ChunkSnapshot
{
    int getX();

    int getZ();

    String getWorldName();

    long getCaptureFullTime();

    boolean isSectionEmpty(int sy);

    int getBlockTypeId(int x, int y, int z);

    int getBlockData(int x, int y, int z);

    BlockMaterialData getBlockType(int x, int y, int z);

    int getBlockSkyLight(int x, int y, int z);

    int getBlockEmittedLight(int x, int y, int z);

    int getHighestBlockYAt(int x, int z);

    Biome getBiome(int x, int z);

    double getRawBiomeTemperature(int x, int z);

    double getRawBiomeRainfall(int x, int z);
}
