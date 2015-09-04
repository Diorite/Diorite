package org.diorite.world.chunk;

import java.util.List;

public interface ChunkManager
{
    /**
     * Gets a chunk object representing the specified coordinates, which might
     * not yet be loaded.
     *
     * @param pos chunk pos.
     *
     * @return The chunk.
     */
    Chunk getChunk(final ChunkPos pos);

    /**
     * Gets a chunk object representing the specified coordinates, which might
     * not yet be loaded.
     *
     * @param x The X coordinate.
     * @param z The Z coordinate.
     *
     * @return The chunk.
     */
    Chunk getChunk(int x, int z);

    /**
     * Checks if the Chunk at the specified coordinates is loaded.
     *
     * @param x The X coordinate.
     * @param z The Z coordinate.
     *
     * @return true if the chunk is loaded, otherwise false.
     */
    boolean isChunkLoaded(int x, int z);

    /**
     * Check whether a chunk has locks on it preventing it from being unloaded.
     *
     * @param x The X coordinate.
     * @param z The Z coordinate.
     *
     * @return Whether the chunk is in use.
     */
    boolean isChunkInUse(int x, int z);

    /**
     * Call the ChunkIOService to load a chunk, optionally generating the chunk.
     *
     * @param x        The X coordinate of the chunk to load.
     * @param z        The Y coordinate of the chunk to load.
     * @param generate Whether to generate the chunk if needed.
     *
     * @return True on success, false on failure.
     */
    boolean loadChunk(int x, int z, boolean generate);

    /**
     * Unload chunks with no locks on them.
     */
    void unloadOldChunks();

    /**
     * Populate a single chunk if needed.
     */
    void populateChunk(int x, int z, boolean force);

    /**
     * Force a chunk to be populated by loading the chunks in an area around it. Used when streaming chunks to players
     * so that they do not have to watch chunks being populated.
     *
     * @param x The X coordinate.
     * @param z The Z coordinate.
     */
    void forcePopulation(int x, int z);

    /**
     * Initialize a single chunk from the chunk generator.
     *
     * @param chunk chunk to generate.
     * @param x     chunk x.
     * @param x     chunk z.
     */
    void generateChunk(Chunk chunk, int x, int z);

    /**
     * Forces generation of the given chunk.
     *
     * @param x The X coordinate.
     * @param z The Z coordinate.
     *
     * @return Whether the chunk was successfully regenerated.
     */
    boolean forceRegeneration(int x, int z);

    /**
     * Gets a list of loaded chunks.
     *
     * @return The currently loaded chunks.
     */
    List<? extends Chunk> getLoadedChunks();

    /**
     * Performs the save for the given chunk using the storage provider.
     *
     * @param chunk The chunk to save.
     *
     * @return true if chunk was added to save queue
     */
    boolean save(Chunk chunk);

    /**
     * Performs the save for the given chunk using the storage provider.
     *
     * @param chunk    The chunk to save.
     * @param priority priority of save.
     *
     * @return true if chunk was added to save queue
     */
    boolean save(Chunk chunk, int priority);
}
