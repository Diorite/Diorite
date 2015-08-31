package org.diorite.impl.world.io_old;

import java.io.File;
import java.io.IOException;

import org.diorite.impl.world.chunk.ChunkImpl;


/**
 * Provider of chunk I/O services. Implemented by classes to provide a way of
 * saving and loading chunks to external storage.
 */
public interface ChunkIoService
{

    /**
     * Reads a single chunk. The provided chunk must not yet be initialized.
     *
     * @param chunk The ChunkImpl to read into.
     *
     * @throws IOException if an I/O error occurs.
     */
    boolean read(ChunkImpl chunk) throws IOException;

    /**
     * Writes a single chunk.
     *
     * @param chunk The {@link ChunkImpl} to write from.
     *
     * @throws IOException if an I/O error occurs.
     */
    void write(ChunkImpl chunk) throws IOException;

    /**
     * Unload the service, performing any cleanup necessary.
     *
     * @throws IOException if an I/O error occurs.
     */
    void unload() throws IOException;

    /**
     * @return folder with world data (if used).
     */
    File getWorldFile();

}
