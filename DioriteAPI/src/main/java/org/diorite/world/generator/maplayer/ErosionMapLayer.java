package org.diorite.world.generator.maplayer;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ErosionMapLayer extends MapLayer
{
    protected final MapLayer belowLayer;

    public ErosionMapLayer(final long seed, final MapLayer belowLayer)
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
                // This applies erosion using Rotated Von Neumann neighborhood
                // it takes a 3x3 grid with a cross shape and analyzes values as follow
                // X0X
                // 0X0
                // X0X
                // the grid center value decides how we are proceeding:
                // - if it's land and it's surrounded by at least 1 ocean cell there are 4/5 chances
                // to proceed to land weathering, and 1/5 chance to spread some land,                
                // - if it's ocean and it's surrounded by at least 1 land cell, there are 2/3 chances
                // to proceed to land weathering, and 1/3 chance to spread some land.
                final int upperLeftVal = values[(j + (i * gridSizeX))];
                final int lowerLeftVal = values[(j + ((i + 2) * gridSizeX))];
                final int upperRightVal = values[(j + 2 + (i * gridSizeX))];
                final int lowerRightVal = values[(j + 2 + ((i + 2) * gridSizeX))];
                final int centerVal = values[(j + 1 + ((i + 1) * gridSizeX))];

                this.setCoordsSeed(x + j, z + i);
                if ((centerVal != 0) && ((upperLeftVal == 0) || (upperRightVal == 0) || (lowerLeftVal == 0) || (lowerRightVal == 0)))
                {
                    finalValues[(j + (i * sizeX))] = (this.nextInt(5) == 0) ? 0 : centerVal;
                }
                else if ((centerVal == 0) && ((upperLeftVal != 0) || (upperRightVal != 0) || (lowerLeftVal != 0) || (lowerRightVal != 0)))
                {
                    if (this.nextInt(3) == 0)
                    {
                        finalValues[(j + (i * sizeX))] = upperLeftVal;
                    }
                    else
                    {
                        finalValues[(j + (i * sizeX))] = 0;
                    }
                }
                else
                {
                    finalValues[(j + (i * sizeX))] = centerVal;
                }
            }
        }
        return finalValues;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("belowLayer", this.belowLayer).toString();
    }
}
