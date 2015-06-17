package org.diorite.world.generator.maplayer;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.world.Biome;

public class ConstantBiomeMapLayer extends MapLayer
{
    protected final Biome biome;

    public ConstantBiomeMapLayer(final long seed, final Biome biome)
    {
        super(seed);
        this.biome = biome;
    }

    @Override
    public int[] generateValues(final int x, final int z, final int sizeX, final int sizeZ)
    {
        final int[] values = new int[sizeX * sizeZ];
        for (int i = 0; i < sizeZ; i++)
        {
            for (int j = 0; j < sizeX; j++)
            {
                values[(j + (i * sizeX))] = this.biome.getBiomeId();
            }
        }
        return values;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("biome", this.biome).toString();
    }
}
