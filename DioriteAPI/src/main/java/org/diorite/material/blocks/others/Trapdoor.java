package org.diorite.material.blocks.others;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.Directional;
import org.diorite.material.blocks.Openable;

public abstract class Trapdoor extends BlockMaterialData implements Openable, Directional
{
    /**
     * Bit flag defining if trapdoor is closed.
     * If bit is set to 0, then trapdror is closed
     */
    public static final byte OPEN_FLAG = 0x04;
    /**
     * Bit flag defining if trapdoor is on top part of block.
     * If bit is set to 0, then trapdror is on bottom part of block.
     */
    public static final byte TOP_FLAG  = 0x08;

    protected final BlockFace face;
    protected final boolean   open;
    protected final boolean   onTop;

    public Trapdoor(final String enumName, final int id, final String minecraftId, final BlockFace face, final boolean open, final boolean onTop)
    {
        super(enumName, id, minecraftId, face.name() + (onTop ? "_TOP" : "_BOTTOM") + (open ? "_OPEN" : ""), combine(face, open, onTop));
        this.face = face;
        this.open = open;
        this.onTop = onTop;
    }

    /**
     * @return true if trapdoor is on top of the block.
     */
    public boolean isOnTop()
    {
        return this.onTop;
    }

    /**
     * Returns sub-type of trapdoor with selected on top state.
     *
     * @param onTop if trapdoor should be on top of the block.
     *
     * @return sub-type of trapdoor.
     */
    public abstract Trapdoor getOnTop(boolean onTop);

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public boolean isOpen()
    {
        return this.open;
    }

    /**
     * Returns one of IronTrapdoor sub-type based on facing direction, open state and on top state.
     * It will never return null.
     *
     * @param blockFace facing direction of trapdoor.
     * @param open      if trapdoor should be open.
     * @param onTop     if trapdoor should be on top of the block.
     *
     * @return sub-type of IronTrapdoor
     */
    public abstract Trapdoor getType(BlockFace face, boolean open, boolean onTop);

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).append("open", this.open).append("onTop", this.onTop).toString();
    }

    protected static byte combine(final BlockFace face, final boolean open, final boolean onTop)
    {
        byte result = onTop ? TOP_FLAG : 0x0;
        switch (face)
        {
            case SOUTH:
                result |= 0x1;
                break;
            case EAST:
                result |= 0x2;
                break;
            case NORTH:
                result |= 0x3;
                break;
            default:
                break;
        }
        if (open)
        {
            result |= OPEN_FLAG;
        }
        return result;
    }
}
