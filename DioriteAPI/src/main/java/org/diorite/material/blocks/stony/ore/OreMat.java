package org.diorite.material.blocks.stony.ore;

import org.diorite.material.blocks.stony.StonyMat;
import org.diorite.material.blocks.stony.oreblocks.OreBlockMat;
import org.diorite.material.items.OreItemMat;

/**
 * Abstract class for all ore-based blocks
 */
@SuppressWarnings("ClassHasNoToStringMethod")
public abstract class OreMat extends StonyMat
{
    protected final OreItemMat  item;
    protected final OreBlockMat block;

    protected OreMat(final String enumName, final int id, final String minecraftId, final String typeName, final byte type, final OreItemMat item, final OreBlockMat block, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, typeName, type, hardness, blastResistance);
        this.item = item;
        this.block = block;
    }

    protected OreMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final OreItemMat item, final OreBlockMat block, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.item = item;
        this.block = block;
    }
}
