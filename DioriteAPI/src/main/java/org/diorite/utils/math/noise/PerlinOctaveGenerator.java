package org.diorite.utils.math.noise;

import java.util.Random;

import org.diorite.world.World;

/**
 * Creates perlin noise through unbiased octaves
 * From Bukkit project https://github.com/Bukkit/Bukkit
 */
@SuppressWarnings("MagicNumber")
public class PerlinOctaveGenerator extends OctaveGenerator
{

    /**
     * Creates a perlin octave generator for the given world
     *
     * @param world   World to construct this generator for
     * @param octaves Amount of octaves to create
     */
    public PerlinOctaveGenerator(final World world, final int octaves)
    {
        this(new Random(world.getSeed()), octaves);
    }

    /**
     * Creates a perlin octave generator for the given world
     *
     * @param seed    Seed to construct this generator for
     * @param octaves Amount of octaves to create
     */
    public PerlinOctaveGenerator(final long seed, final int octaves)
    {
        this(new Random(seed), octaves);
    }

    /**
     * Creates a perlin octave generator for the given {@link Random}
     *
     * @param rand    Random object to construct this generator for
     * @param octaves Amount of octaves to create
     */
    public PerlinOctaveGenerator(final Random rand, final int octaves)
    {
        super(createOctaves(rand, octaves));
    }

    private static NoiseGenerator[] createOctaves(final Random rand, final int octaves)
    {
        final NoiseGenerator[] result = new NoiseGenerator[octaves];

        for (int i = 0; i < octaves; i++)
        {
            result[i] = new PerlinNoiseGenerator(rand);
        }

        return result;
    }
}