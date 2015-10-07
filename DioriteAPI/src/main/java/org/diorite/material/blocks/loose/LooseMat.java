package org.diorite.material.blocks.loose;

import org.diorite.material.BlockMaterialData;

public abstract class LooseMat extends BlockMaterialData
{
    protected LooseMat(final String enumName, final int id, final String minecraftId, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, typeName, type, hardness, blastResistance);
    }

    protected LooseMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public abstract LooseMat getType(final int type);

    @Override
    public abstract LooseMat getType(final String type);

    @Override
    public abstract LooseMat[] types();
}
