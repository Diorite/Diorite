package diorite.impl.map.chunk;

public class ChunkPartImpl // part of chunk 16x16x16
{
    private byte yPos; // from 0 to 15
    private char[] blocks; // id and sub-id(0-15) of every block

    public ChunkPartImpl(final byte yPos)
    {
        this.yPos = yPos;
        this.blocks = new char[16* 16 *16];
    }

    public ChunkPartImpl(final byte yPos, final char[][][] blocks)
    {
        this.yPos = yPos;
        this.blocks = blocks;
    }

    public void setBlock(int x, int y, int z, byte id, byte meta)
    {
        this.blocks[x][y][z] = (char) ((id << 4) | meta);
    }

    public byte getyPos()
    {
        return this.yPos;
    }

    public void setyPos(final byte yPos)
    {
        this.yPos = yPos;
    }

    public boolean isEmpty()
    {
        return false;
    }
}
