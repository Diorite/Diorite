package org.diorite.material.blocks.others;

import org.diorite.material.BlockMaterialData;

public abstract class BannerBlock extends BlockMaterialData
{
    public BannerBlock(final String enumName, final int id, final String minecraftId, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    public BannerBlock(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }
}
