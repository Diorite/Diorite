package diorite.impl.map.chunk;

public class ChunkImpl
{
    private ChunkPartImpl[] chunkParts; // size of 16, parts can be null

    public ChunkImpl(final ChunkPartImpl[] chunkParts)
    {
        this.chunkParts = chunkParts;
    }

    public ChunkImpl()
    {
        this.chunkParts = new ChunkPartImpl[16];
    }

    public void setBlock(int x, int y, int z, byte id, byte meta)
    {
        final byte chunkPosY = (byte) (y / 16);
        ChunkPartImpl chunkPart = this.chunkParts[chunkPosY];
        if (chunkPart == null)
        {
            chunkPart = new ChunkPartImpl(chunkPosY);
            this.chunkParts[chunkPosY] = chunkPart;
        }
        chunkPart.setBlock(x, y, z, id, meta);
    }

    public void setBlock(int x, int y, int z, int id, int meta)
    {
        this.setBlock(x, y, z, (byte) id, (byte) meta);
    }

    public int getMask()
    {
        for (ChunkPartImpl impl:chunkParts)
        {

        }
    }
}
