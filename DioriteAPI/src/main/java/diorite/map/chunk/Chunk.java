package diorite.map.chunk;

public interface Chunk
{
    int CHUNK_SIZE        = 16;
    int CHUNK_PARTS       = 16;
    int CHUNK_PART_HEIGHT = 16;
    int CHUNK_FULL_HEIGHT = 256;
    int CHUNK_BIOMES_SIZE = CHUNK_SIZE * CHUNK_SIZE;
}
