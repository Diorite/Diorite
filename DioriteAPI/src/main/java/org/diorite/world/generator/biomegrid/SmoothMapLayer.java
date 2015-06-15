package org.diorite.world.generator.biomegrid;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SmoothMapLayer extends MapLayer
{
    protected final MapLayer belowLayer;

    public SmoothMapLayer(final long seed, final MapLayer belowLayer)
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
                // This applies smoothing using Von Neumann neighborhood
                // it takes a 3x3 grid with a cross shape and analyzes values as follow
                // 0X0
                // XxX
                // 0X0
                // it is required that we use the same shape that was used for what we
                // want to smooth
                final int upperVal = values[(j + 1 + (i * gridSizeX))];
                final int lowerVal = values[(j + 1 + ((i + 2) * gridSizeX))];
                final int leftVal = values[(j + ((i + 1) * gridSizeX))];
                final int rightVal = values[(j + 2 + ((i + 1) * gridSizeX))];
                int centerVal = values[(j + 1 + ((i + 1) * gridSizeX))];
                if ((upperVal == lowerVal) && (leftVal == rightVal))
                {
                    this.setCoordsSeed(x + j, z + i);
                    centerVal = (this.nextInt(2) == 0) ? upperVal : leftVal;
                }
                else if (upperVal == lowerVal)
                {
                    centerVal = upperVal;
                }
                else if (leftVal == rightVal)
                {
                    centerVal = leftVal;
                }
                finalValues[(j + (i * sizeX))] = centerVal;
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
