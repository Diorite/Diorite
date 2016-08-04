package org.diorite.impl.tileentity;

import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.block.Block;
import org.diorite.block.BlockLocation;
import org.diorite.inventory.item.ItemStack;
import org.diorite.nbt.NbtTagCompound;
import org.diorite.tileentity.TileEntityNoteBlock;
import org.diorite.utils.math.DioriteRandom;

public class TileEntityNoteBlockImpl extends TileEntityImpl implements TileEntityNoteBlock
{
    private final Block block;
    private       byte  note;

    public TileEntityNoteBlockImpl(final BlockLocation location, final Block block)
    {
        super(location);
        this.block = block;
        this.note = 0;
    }


    @Override
    public byte getNote()
    {
        return this.note;
    }

    @Override
    public void setNote(final byte note)
    {
        this.note = note;
    }

    @Override
    public void doTick(final int tps)
    {
        // TODO
    }

    @Override
    public Block getBlock()
    {
        return this.block;
    }

    @Override
    public void simulateDrop(final DioriteRandom rand, final Set<ItemStack> drops)
    {
        // TODO
    }

    @Override
    public void loadFromNbt(final NbtTagCompound nbtTileEntity)
    {
        super.loadFromNbt(nbtTileEntity);
        // TODO
    }

    @Override
    public void saveToNbt(final NbtTagCompound nbtTileEntity)
    {
        super.saveToNbt(nbtTileEntity);
        // TODO
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("block", this.block).append("note", this.note).toString();
    }
}
