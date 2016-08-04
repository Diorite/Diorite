package org.diorite.impl.block;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.tileentity.TileEntityNoteBlockImpl;
import org.diorite.block.Block;
import org.diorite.block.NoteBlock;

public class NoteBlockImpl extends BlockStateImpl implements NoteBlock
{
    private final TileEntityNoteBlockImpl tileEntity;

    public NoteBlockImpl(final Block block)
    {
        super(block);
        this.tileEntity = (TileEntityNoteBlockImpl) block.getWorld().getTileEntity(block.getLocation());
    }

    @Override
    public byte getNote()
    {
        return this.tileEntity.getNote();
    }

    @Override
    public void setNote(final byte note)
    {
        this.tileEntity.setNote(note);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("tileEntity", this.tileEntity).toString();
    }
}
