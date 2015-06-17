package org.diorite.world.generator.maplayer;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.world.Biome;

import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;

public class RarePlainsMapLayer extends MapLayer
{
    protected static final TIntIntMap RARE_PLAINS = new TIntIntHashMap(1);
    public static final int CHANCE = 57;

    protected final MapLayer belowLayer;

    public static TIntIntMap getRarePlains()
    {
        return RARE_PLAINS;
    }

    public RarePlainsMapLayer(final long seed, final MapLayer belowLayer)
    {
        super(seed);
        this.belowLayer = belowLayer;
    }

    @Override
    public int[] generateValues(final int x, final int z, final int sizeX, final int sizeZ)
    {
        final int gridX = x - 1;
        final int gridZ = z - 1;
        final int gridSizeX = sizeX + 2;
        final int gridSizeZ = sizeZ + 2;

        final int[] values = this.belowLayer.generateValues(gridX, gridZ, gridSizeX, gridSizeZ);

        final int[] finalValues = new int[sizeX * sizeZ];
        for (int i = 0; i < sizeZ; i++)
        {
            for (int j = 0; j < sizeX; j++)
            {
                this.setCoordsSeed(x + j, z + i);
                int centerValue = values[(j + 1 + ((i + 1) * gridSizeX))];
                if ((this.nextInt(CHANCE) == 0) && RARE_PLAINS.containsKey(centerValue))
                {
                    centerValue = RARE_PLAINS.get(centerValue);
                }
                finalValues[(j + (i * sizeX))] = centerValue;
            }
        }
        return finalValues;
    }

    static
    {
        RARE_PLAINS.put(Biome.PLAINS.getBiomeId(), Biome.SUNFLOWER_PLAINS.getBiomeId());
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("belowLayer", this.belowLayer).toString();
    }
}
