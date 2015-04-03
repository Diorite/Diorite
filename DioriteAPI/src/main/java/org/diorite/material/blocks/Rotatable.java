package org.diorite.material.blocks;

import org.diorite.material.BlockMaterialData;

public interface Rotatable
{
    RotateAxis getRotateAxis();

    BlockMaterialData getRotated(RotateAxis axis);
}
