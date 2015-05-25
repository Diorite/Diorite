package org.diorite.world.generator;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import org.diorite.world.World;

public final class WorldGenerators
{
    private static final Collection<WorldGeneratorInitializer<?>> generators = new HashSet<>(5);

    private WorldGenerators()
    {
    }

    public static WorldGenerator getGenerator(final String name, final World world, final Map<String, Object> options)
    {
        for (final WorldGeneratorInitializer<?> gen : generators)
        {
            if (gen.getName().equalsIgnoreCase(name))
            {
                return gen.init(world, options);
            }
        }
        return null;
    }

    public static void registerGenerator(final WorldGeneratorInitializer<?> generator)
    {
        generators.add(generator);
    }

    public static Collection<WorldGeneratorInitializer<?>> getGeneratorInitializers()
    {
        return new HashSet<>(generators);
    }
}

