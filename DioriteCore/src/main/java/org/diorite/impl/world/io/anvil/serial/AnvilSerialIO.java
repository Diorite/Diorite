package org.diorite.impl.world.io.anvil.serial;

import java.io.File;

import org.diorite.impl.world.io.anvil.AnvilIO;

public class AnvilSerialIO extends AnvilIO
{
    AnvilSerialIO(final File basePath, final String extension, final int maxCacheSize)
    {
        super(basePath, extension, maxCacheSize);
    }
}
