package org.diorite.world.generator.maplayer;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.math.noise.SimplexOctaveGenerator;

@SuppressWarnings("MagicNumber")
public class NoiseMapLayer extends MapLayer
{
    public static final double FREQUENCY = 0.175D;
    public static final double AMPLITUDE = 0.8D;

    protected final SimplexOctaveGenerator noiseGen;

    public NoiseMapLayer(final long seed)
    {
        super(seed);
        this.noiseGen = new SimplexOctaveGenerator(seed, 2);
    }

    @Override
    public int[] generateValues(final int x, final int z, final int sizeX, final int sizeZ)
    {
        final int[] values = new int[sizeX * sizeZ];
        for (int i = 0; i < sizeZ; i++)
        {
            for (int j = 0; j < sizeX; j++)
            {
                final double noise = this.noiseGen.noise(x + j, z + i, FREQUENCY, AMPLITUDE, true) * 4.0D;
                final int val;
                if (noise >= 0.05D)
                {
                    val = (noise <= 0.2D) ? 3 : 2;
                }
                else
                {
                    this.setCoordsSeed(x + j, z + i);
                    val = (this.nextInt(2) == 0) ? 3 : 0;
                }
                values[(j + (i * sizeX))] = val;
            }
        }
        return values;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("noiseGen", this.noiseGen).toString();
    }
}
