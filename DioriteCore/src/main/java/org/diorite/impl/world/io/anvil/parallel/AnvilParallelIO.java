package org.diorite.impl.world.io.anvil.parallel;

import java.io.File;

import org.diorite.impl.world.io.anvil.AnvilIO;

public class AnvilParallelIO extends AnvilIO
{
    AnvilParallelIO(final File basePath, final String extension, final int maxCacheSize, final int regionSize)
    {
        super(basePath, extension, maxCacheSize, regionSize);
    }
}
