package org.diorite.tileentity;

public interface TileEntityNoteBlock extends TileEntity
{
    byte getNote();

    void setNote(byte note);
}
