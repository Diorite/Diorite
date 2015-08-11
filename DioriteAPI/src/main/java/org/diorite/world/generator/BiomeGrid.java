package org.diorite.world.generator;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.world.Biome;
import org.diorite.world.chunk.Chunk;

public class BiomeGrid
{
    protected final byte[] biomes = new byte[Chunk.CHUNK_BIOMES_SIZE];

    @SuppressWarnings("MagicNumber")
    public Biome getBiome(final int x, final int z)
    {
        return Biome.getByBiomeId(this.biomes[x | (z << 4)] & 0xFF);
    }

    public void setBiome(final int x, final int z, final Biome biome)
    {
        this.biomes[x | (z << 4)] = (byte) biome.getBiomeId();
    }

    public byte[] rawData()
    {
        return this.biomes;
    }

    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("biomes", this.biomes).toString();
    }
}
