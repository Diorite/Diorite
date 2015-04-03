package org.diorite.material.blocks;

import org.diorite.material.BlockMaterialData;

public interface Activatable
{
    boolean isActivated();

    BlockMaterialData getActivated(boolean activate);
}
