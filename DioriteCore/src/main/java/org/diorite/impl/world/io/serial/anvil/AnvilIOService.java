package org.diorite.impl.world.io.serial.anvil;

import org.diorite.impl.world.io.ChunkIO;
import org.diorite.impl.world.io.requests.Request;
import org.diorite.impl.world.io.serial.SerialChunkIOService;

public class AnvilIOService implements SerialChunkIOService
{
    @Override
    public <OUT, T extends Request<OUT>> T queue(final T request)
    {
        return null;
    }

    @Override
    public ChunkIO getImplementation()
    {
        return null;
    }
}
