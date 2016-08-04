package org.diorite.block;

public interface NoteBlock extends BlockState
{
    byte getNote();

    void setNote(byte note);
}
