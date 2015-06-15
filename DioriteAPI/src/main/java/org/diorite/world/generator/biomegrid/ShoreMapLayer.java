package org.diorite.world.generator.biomegrid;

import static org.diorite.world.Biome.*;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;

@SuppressWarnings("MagicNumber")
public class ShoreMapLayer extends MapLayer
{
    protected static final TIntSet    OCEANS         = new TIntHashSet(2);
    protected static final TIntIntMap SPECIAL_SHORES = new TIntIntHashMap(18);
    protected final MapLayer belowLayer;

    public static TIntSet getOceans()
    {
        return OCEANS;
    }

    public static TIntIntMap getSpecialShores()
    {
        return SPECIAL_SHORES;
    }

    public ShoreMapLayer(final long seed, final MapLayer belowLayer)
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
                // This applies shores using Von Neumann neighborhood
                // it takes a 3x3 grid with a cross shape and analyzes values as follow
                // 0X0
                // XxX
                // 0X0
                // the grid center value decides how we are proceeding:
                // - if it's not ocean and it's surrounded by at least 1 ocean cell
                // it turns the center value into beach.
                final int upperVal = values[(j + 1 + (i * gridSizeX))];
                final int lowerVal = values[(j + 1 + ((i + 2) * gridSizeX))];
                final int leftVal = values[(j + ((i + 1) * gridSizeX))];
                final int rightVal = values[(j + 2 + ((i + 1) * gridSizeX))];
                final int centerVal = values[(j + 1 + ((i + 1) * gridSizeX))];
                if (! OCEANS.contains(centerVal) && (OCEANS.contains(upperVal) || OCEANS.contains(lowerVal) || OCEANS.contains(leftVal) || OCEANS.contains(rightVal)))
                {
                    finalValues[(j + (i * sizeX))] = SPECIAL_SHORES.containsKey(centerVal) ? SPECIAL_SHORES.get(centerVal) : BEACH.getBiomeId();
                }
                else
                {
                    finalValues[(j + (i * sizeX))] = centerVal;
                }
            }
        }
        return finalValues;
    }

    static
    {
        OCEANS.add(OCEAN.getBiomeId());
        OCEANS.add(DEEP_OCEAN.getBiomeId());

        SPECIAL_SHORES.put(EXTREME_HILLS.getBiomeId(), STONE_BEACH.getBiomeId());
        SPECIAL_SHORES.put(EXTREME_HILLS_PLUS.getBiomeId(), STONE_BEACH.getBiomeId());
        SPECIAL_SHORES.put(EXTREME_HILLS_MOUNTAINS.getBiomeId(), STONE_BEACH.getBiomeId());
        SPECIAL_SHORES.put(EXTREME_HILLS_PLUS_MOUNTAINS.getBiomeId(), STONE_BEACH.getBiomeId());
        SPECIAL_SHORES.put(ICE_PLAINS.getBiomeId(), COLD_BEACH.getBiomeId());
        SPECIAL_SHORES.put(ICE_MOUNTAINS.getBiomeId(), COLD_BEACH.getBiomeId());
        SPECIAL_SHORES.put(ICE_PLAINS_SPIKES.getBiomeId(), COLD_BEACH.getBiomeId());
        SPECIAL_SHORES.put(COLD_TAIGA.getBiomeId(), COLD_BEACH.getBiomeId());
        SPECIAL_SHORES.put(COLD_TAIGA_HILLS.getBiomeId(), COLD_BEACH.getBiomeId());
        SPECIAL_SHORES.put(COLD_TAIGA_MOUNTAINS.getBiomeId(), COLD_BEACH.getBiomeId());
        SPECIAL_SHORES.put(MUSHROOM_ISLAND.getBiomeId(), MUSHROOM_SHORE.getBiomeId());
        SPECIAL_SHORES.put(SWAMPLAND.getBiomeId(), SWAMPLAND.getBiomeId());
        SPECIAL_SHORES.put(MESA.getBiomeId(), MESA.getBiomeId());
        SPECIAL_SHORES.put(MESA_PLATEAU_FOREST.getBiomeId(), MESA_PLATEAU_FOREST.getBiomeId());
        SPECIAL_SHORES.put(MESA_PLATEAU_FOREST_MOUNTAINS.getBiomeId(), MESA_PLATEAU_FOREST_MOUNTAINS.getBiomeId());
        SPECIAL_SHORES.put(MESA_PLATEAU.getBiomeId(), MESA_PLATEAU.getBiomeId());
        SPECIAL_SHORES.put(MESA_PLATEAU_MOUNTAINS.getBiomeId(), MESA_PLATEAU_MOUNTAINS.getBiomeId());
        SPECIAL_SHORES.put(MESA_BRYCE.getBiomeId(), MESA_BRYCE.getBiomeId());
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("belowLayer", this.belowLayer).toString();
    }
}
