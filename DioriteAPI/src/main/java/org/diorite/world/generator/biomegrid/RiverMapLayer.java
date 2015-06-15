package org.diorite.world.generator.biomegrid;

import static org.diorite.world.Biome.DEEP_OCEAN;
import static org.diorite.world.Biome.FROZEN_RIVER;
import static org.diorite.world.Biome.ICE_PLAINS;
import static org.diorite.world.Biome.MUSHROOM_ISLAND;
import static org.diorite.world.Biome.MUSHROOM_SHORE;
import static org.diorite.world.Biome.OCEAN;
import static org.diorite.world.Biome.RIVER;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;

public class RiverMapLayer extends MapLayer
{
    protected static final TIntSet    OCEANS         = new TIntHashSet(2);
    protected static final TIntIntMap SPECIAL_RIVERS = new TIntIntHashMap(3);
    public static final    int        CLEAR_VALUE    = 0;
    public static final    int        RIVER_VALUE    = 1;

    protected final MapLayer belowLayer;
    protected final MapLayer mergeLayer;

    public static TIntSet getOceans()
    {
        return OCEANS;
    }

    public static TIntIntMap getSpecialRivers()
    {
        return SPECIAL_RIVERS;
    }

    public RiverMapLayer(final long seed, final MapLayer belowLayer)
    {
        this(seed, belowLayer, null);
    }

    public RiverMapLayer(final long seed, final MapLayer belowLayer, final MapLayer mergeLayer)
    {
        super(seed);
        this.belowLayer = belowLayer;
        this.mergeLayer = mergeLayer;
    }

    @Override
    public int[] generateValues(final int x, final int z, final int sizeX, final int sizeZ)
    {
        if (this.mergeLayer == null)
        {
            return this.generateRivers(x, z, sizeX, sizeZ);
        }
        return this.mergeRivers(x, z, sizeX, sizeZ);
    }

    private int[] generateRivers(final int x, final int z, final int sizeX, final int sizeZ)
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
                // This applies rivers using Von Neumann neighborhood
                final int centerVal = values[(j + 1 + ((i + 1) * gridSizeX))] & 1;
                final int upperVal = values[(j + 1 + (i * gridSizeX))] & 1;
                final int lowerVal = values[(j + 1 + ((i + 2) * gridSizeX))] & 1;
                final int leftVal = values[(j + ((i + 1) * gridSizeX))] & 1;
                final int rightVal = values[(j + 2 + ((i + 1) * gridSizeX))] & 1;
                int val = CLEAR_VALUE;
                if ((centerVal != upperVal) || (centerVal != lowerVal) || (centerVal != leftVal) || (centerVal != rightVal))
                {
                    val = RIVER_VALUE;
                }
                finalValues[(j + (i * sizeX))] = val;
            }
        }
        return finalValues;
    }

    private int[] mergeRivers(final int x, final int z, final int sizeX, final int sizeZ)
    {
        final int[] values = this.belowLayer.generateValues(x, z, sizeX, sizeZ);
        final int[] mValues = this.mergeLayer.generateValues(x, z, sizeX, sizeZ);

        final int[] finalValues = new int[sizeX * sizeZ];
        for (int i = 0; i < (sizeX * sizeZ); i++)
        {
            int val = mValues[i];
            if (OCEANS.contains(mValues[i]))
            {
                val = mValues[i];
            }
            else if (values[i] == RIVER_VALUE)
            {
                if (SPECIAL_RIVERS.containsKey(mValues[i]))
                {
                    val = SPECIAL_RIVERS.get(mValues[i]);
                }
                else
                {
                    val = RIVER.getBiomeId();
                }
            }
            finalValues[i] = val;
        }

        return finalValues;
    }

    static
    {
        OCEANS.add(OCEAN.getBiomeId());
        OCEANS.add(DEEP_OCEAN.getBiomeId());

        SPECIAL_RIVERS.put(ICE_PLAINS.getBiomeId(), FROZEN_RIVER.getBiomeId());
        SPECIAL_RIVERS.put(MUSHROOM_ISLAND.getBiomeId(), MUSHROOM_SHORE.getBiomeId());
        SPECIAL_RIVERS.put(MUSHROOM_SHORE.getBiomeId(), MUSHROOM_SHORE.getBiomeId());
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("belowLayer", this.belowLayer).append("mergeLayer", this.mergeLayer).toString();
    }
}
