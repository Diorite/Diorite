package org.diorite.material.blocks;

import org.diorite.material.BlockMaterialData;

public interface Powerable
{
    boolean isPowered();

    BlockMaterialData getPowered(boolean powered);
}
