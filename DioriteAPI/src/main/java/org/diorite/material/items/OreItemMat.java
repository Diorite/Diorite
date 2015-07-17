package org.diorite.material.items;

import org.diorite.material.ItemMaterialData;
import org.diorite.material.blocks.stony.ore.OreMat;
import org.diorite.material.blocks.stony.oreblocks.OreBlockMat;

@SuppressWarnings("ClassHasNoToStringMethod")
public abstract class OreItemMat extends ItemMaterialData
{
    protected final OreMat      blockOreType;
    protected final OreBlockMat blockType;

    protected OreItemMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final OreMat oreType, final OreBlockMat blockType)
    {
        super(enumName, id, minecraftId, typeName, type);
        this.blockOreType = oreType;
        this.blockType = blockType;
    }

    protected OreItemMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final OreMat oreType, final OreBlockMat blockType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
        this.blockOreType = oreType;
        this.blockType = blockType;
    }

    /**
     * Returns related {@link OreMat} for this item, may return null.
     *
     * @return related {@link OreMat} for this item.
     */
    public OreMat getBlockOreType()
    {
        return this.blockOreType;
    }

    /**
     * Returns related {@link OreBlockMat} for this item, may return null.
     *
     * @return related {@link OreBlockMat} for this item.
     */
    public OreBlockMat getBlockType()
    {
        return this.blockType;
    }
}
